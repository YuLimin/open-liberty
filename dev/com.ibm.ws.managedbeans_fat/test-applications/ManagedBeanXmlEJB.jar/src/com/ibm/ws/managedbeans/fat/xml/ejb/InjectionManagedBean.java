/*******************************************************************************
 * Copyright (c) 2012, 2020 IBM Corporation and others.
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
package com.ibm.ws.managedbeans.fat.xml.ejb;

import java.util.logging.Logger;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;

/**
 * Simple Managed Bean that has been named and has simple injection.
 **/
@ManagedBean("InjectionManagedBean")
public class InjectionManagedBean {
    private static final String CLASS_NAME = InjectionManagedBean.class.getName();
    private static final Logger svLogger = Logger.getLogger(CLASS_NAME);
    private static int svNextID = 1;

    private final int ivID;
    private String ivValue = null;

    @Resource
    UserTransaction ivUserTran;

    public InjectionManagedBean() {
        // Use a unique id so it is easy to tell which instance is in use.
        synchronized (this) {
            svLogger.info("-- ejb.InjectionManagedBean.<init>:" + svNextID);
            ivID = svNextID++;
        }
    }

    /**
     * Returns the unique identifier of this instance.
     */
    public int getIdentifier() {
        svLogger.info("-- getIdentifier : " + this);
        return ivID;
    }

    /**
     * Returns the value.. to verify object is 'stateful'
     */
    public String getValue() {
        svLogger.info("-- getValue : " + this);
        return ivValue;
    }

    /**
     * Sets the value.. to verify object is 'stateful'
     */
    public void setValue(String value) {
        svLogger.info("-- setValue : " + ivValue + "->" + value + " : " + this);
        ivValue = value;
    }

    /**
     * Returns the injected UserTransaction.
     */
    public UserTransaction getUserTransaction() {
        return ivUserTran;
    }

    @Override
    public String toString() {
        return "ejb.InjectionManagedBean(ID=" + ivID + "," + ivValue + ")";
    }

}
