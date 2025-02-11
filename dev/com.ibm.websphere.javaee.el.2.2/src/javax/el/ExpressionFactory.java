/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.el;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * @since 2.1
 */
public abstract class ExpressionFactory {
    
    private static final boolean IS_SECURITY_ENABLED =
        (System.getSecurityManager() != null);

    private static final String PROPERTY_NAME = "javax.el.ExpressionFactory";

    private static final String SEP;
    private static final String PROPERTY_FILE;

    //PM78648 start
    private static final CacheValue nullTcclFactory = new CacheValue();
    private static ConcurrentMap<CacheKey, CacheValue> factoryCache = new ConcurrentHashMap<CacheKey, CacheValue>();
    //PM78648 end

    static {
        if (IS_SECURITY_ENABLED) {
            SEP = AccessController.doPrivileged(
                    new PrivilegedAction<String>(){
                        @Override
                        public String run() {
                            return System.getProperty("file.separator");
                        }

                    }
            );
            PROPERTY_FILE = AccessController.doPrivileged(
                    new PrivilegedAction<String>(){
                        @Override
                        public String run() {
                            return System.getProperty("java.home") + SEP +
                                    "lib" + SEP + "el.properties";
                        }

                    }
            );
        } else {
            SEP = System.getProperty("file.separator");
            PROPERTY_FILE = System.getProperty("java.home") + SEP + "lib" +
                    SEP + "el.properties";
        }
    }

    public abstract Object coerceToType(Object obj, Class<?> expectedType)
            throws ELException;

    public abstract ValueExpression createValueExpression(ELContext context,
            String expression, Class<?> expectedType)
            throws NullPointerException, ELException;

    public abstract ValueExpression createValueExpression(Object instance,
            Class<?> expectedType);

    public abstract MethodExpression createMethodExpression(ELContext context,
            String expression, Class<?> expectedReturnType,
            Class<?>[] expectedParamTypes) throws ELException,
            NullPointerException;

    /**
     * Create a new {@link ExpressionFactory}. The class to use is determined by
     * the following search order:
     * <ol>
     * <li>services API (META-INF/services/javax.el.ExpressionFactory)</li>
     * <li>$JRE_HOME/lib/el.properties - key javax.el.ExpressionFactory</li>
     * <li>javax.el.ExpressionFactory</li>
     * <li>Platform default implementation -
     *     org.apache.el.ExpressionFactoryImpl</li>
     * </ol>
     * @return the new ExpressionFactory
     */
    public static ExpressionFactory newInstance() {
        return newInstance(null);
    }

    /**
     * Create a new {@link ExpressionFactory} passing in the provided
     * {@link Properties}. Search order is the same as {@link #newInstance()}.
     * 
     * @param properties the properties to be passed to the new instance (may be null)
     * @return the new ExpressionFactory
     */
    public static ExpressionFactory newInstance(Properties properties) {
        ExpressionFactory result = null;
        
        ClassLoader tccl;
        if (IS_SECURITY_ENABLED) {
            tccl = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                @Override
                public ClassLoader run() {
                    return Thread.currentThread().getContextClassLoader();
                }
            });
        } else {
            tccl = Thread.currentThread().getContextClassLoader();
        }

        //PM78648
        CacheValue cacheValue;
        Class<?> clazz;

        if (tccl == null) {
            cacheValue = nullTcclFactory;
        } else {
            CacheKey key = new CacheKey(tccl);
            cacheValue = factoryCache.get(key);
            if (cacheValue == null) {
                CacheValue newCacheValue = new CacheValue();
                cacheValue = factoryCache.putIfAbsent(key, newCacheValue);
                if (cacheValue == null) {
                    cacheValue = newCacheValue;
                }
            }
        }

        final Lock readLock = cacheValue.getLock().readLock();
        readLock.lock();
        try {
            clazz = cacheValue.getFactoryClass();
        } finally {
            readLock.unlock();
        }

        if (clazz == null) {
            String className = null;
            try {
                final Lock writeLock = cacheValue.getLock().writeLock();
                writeLock.lock();
                try {
                    className = cacheValue.getFactoryClassName();
                    if (className == null) {
                        className = discoverClassName(tccl);
                        cacheValue.setFactoryClassName(className);
                    }
                    if (tccl == null) {
                        clazz = Class.forName(className);
                    } else {
                        clazz = tccl.loadClass(className);
                    }
                    cacheValue.setFactoryClass(clazz);
                } finally {
                    writeLock.unlock();
                }
            } catch (ClassNotFoundException e) {
                throw new ELException(
                                "Unable to find ExpressionFactory of type: " + className,
                                e);
            }
        }

        try {
            Constructor<?> constructor = null;
            // Do we need to look for a constructor that will take properties?
            if (properties != null) {
                try {
                    constructor = clazz.getConstructor(Properties.class);
                } catch (SecurityException se) {
                    throw new ELException(se);
                } catch (NoSuchMethodException nsme) {
                    // This can be ignored
                    // This is OK for this constructor not to exist
                }
            }
            if (constructor == null) {
                result = (ExpressionFactory) clazz.newInstance();
            } else {
                result =
                    (ExpressionFactory) constructor.newInstance(properties);
            }

        } catch (InstantiationException e) {
            throw new ELException(
                    "Unable to create ExpressionFactory of type: " + clazz.getName(),
                    e);
        } catch (IllegalAccessException e) {
            throw new ELException(
                    "Unable to create ExpressionFactory of type: " + clazz.getName(),
                    e);
        } catch (IllegalArgumentException e) {
            throw new ELException(
                    "Unable to create ExpressionFactory of type: " + clazz.getName(),
                    e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ThreadDeath) {
                throw (ThreadDeath) cause;
            }
            if (cause instanceof VirtualMachineError) {
                throw (VirtualMachineError) cause;
            }
            throw new ELException(
                    "Unable to create ExpressionFactory of type: " + clazz.getName(),
                    e);
        }
        
        return result;
    }

    /**
     * Key used to cache ExpressionFactory discovery information per class
     * loader. The class loader reference is never {@code null}, because {@code null} tccl is handled separately.
     */
    private static class CacheKey {
        private final int hash;
        private final WeakReference<ClassLoader> ref;

        public CacheKey(ClassLoader cl) {
            hash = cl.hashCode();
            ref = new WeakReference<ClassLoader>(cl);
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CacheKey)) {
                return false;
            }
            ClassLoader thisCl = ref.get();
            if (thisCl == null) {
                return false;
            }
            return thisCl == ((CacheKey) obj).ref.get();
        }
    }

    private static class CacheValue {
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private String className;
        private WeakReference<Class<?>> ref;

        public CacheValue() {}

        public ReadWriteLock getLock() {
            return lock;
        }

        public String getFactoryClassName() {
            return className;
        }

        public void setFactoryClassName(String className) {
            this.className = className;
        }

        public Class<?> getFactoryClass() {
            return ref != null ? ref.get() : null;
        }

        public void setFactoryClass(Class<?> clazz) {
            ref = new WeakReference<Class<?>>(clazz);
        }
    }

    /**
     * Discover the name of class that implements ExpressionFactory.
     * 
     * @param tccl
     *            {@code ClassLoader}
     * @return Class name. There is default, so it is never {@code null}.
     */
    private static String discoverClassName(ClassLoader tccl) {
        String className = null;

        // First services API
        className = getClassNameServices(tccl);
        if (className == null) {
            if (IS_SECURITY_ENABLED) {
                className = AccessController.doPrivileged(
                                new PrivilegedAction<String>() {
                                    @Override
                                    public String run() {
                                        return getClassNameJreDir();
                                    }
                                }
                                );
            } else {
                // Second el.properties file
                className = getClassNameJreDir();
            }
        }
        if (className == null) {
            if (IS_SECURITY_ENABLED) {
                className = AccessController.doPrivileged(
                                new PrivilegedAction<String>() {
                                    @Override
                                    public String run() {
                                        return getClassNameSysProp();
                                    }
                                }
                                );
            } else {
                // Third system property
                className = getClassNameSysProp();
            }
        }
        if (className == null) {
            // Fourth - default
            className = "org.apache.el.ExpressionFactoryImpl";
        }
        return className;
    }

    private static String getClassNameServices(ClassLoader tccl) {

        ExpressionFactory result = null;

        ServiceLoader<ExpressionFactory> serviceLoader = ServiceLoader.load(ExpressionFactory.class, tccl);
        Iterator<ExpressionFactory> iter = serviceLoader.iterator();
        while (result == null && iter.hasNext()) {
            result = iter.next();
        }

        if (result == null) {
            return null;
        }

        return result.getClass().getName();
    }
    
    private static String getClassNameJreDir() {
        File file = new File(PROPERTY_FILE);
        if (file.canRead()) {
            InputStream is = null;
            try {
                is = new FileInputStream(file);
                Properties props = new Properties();
                props.load(is);
                String value = props.getProperty(PROPERTY_NAME);
                if (value != null && value.trim().length() > 0) {
                    return value.trim();
                }
            } catch (FileNotFoundException e) {
                // Should not happen - ignore it if it does
            } catch (IOException e) {
                throw new ELException("Failed to read " + PROPERTY_FILE, e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }
            }
        }
        return null;
    }
    
    private static final String getClassNameSysProp() {
        String value = System.getProperty(PROPERTY_NAME);
        if (value != null && value.trim().length() > 0) {
            return value.trim();
        }
        return null;
    }

}
