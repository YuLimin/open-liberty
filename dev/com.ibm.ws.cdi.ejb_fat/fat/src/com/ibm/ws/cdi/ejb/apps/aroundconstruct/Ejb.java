/*******************************************************************************
 * Copyright (c) 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.cdi.ejb.apps.aroundconstruct;

import static com.ibm.ws.cdi.ejb.apps.aroundconstruct.AroundConstructLogger.ConstructorType.INJECTED;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import com.ibm.ws.cdi.ejb.apps.aroundconstruct.interceptors.DirectlyIntercepted;
import com.ibm.ws.cdi.ejb.apps.aroundconstruct.interceptors.InterceptorOneBinding;
import com.ibm.ws.cdi.ejb.apps.aroundconstruct.interceptors.InterceptorTwoBinding;
import com.ibm.ws.cdi.ejb.apps.aroundconstruct.interceptors.NonCdiInterceptor;
import com.ibm.ws.cdi.ejb.utils.Intercepted;

@Stateful
@RequestScoped
@Intercepted
@InterceptorOneBinding
@InterceptorTwoBinding
@Interceptors({ NonCdiInterceptor.class })
public class Ejb {
    public Ejb() {} // necessary to be proxyable

    @DirectlyIntercepted
    @Inject
    public Ejb(AroundConstructLogger logger) {
        logger.setConstructorType(INJECTED);
    }

    public void doSomething() {}
}
