/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
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

package com.ibm.tra.ann;

import javax.resource.ResourceException;
import javax.resource.spi.Activation;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;

import com.ibm.tra.inbound.impl.TRAMessageListener3;

@Activation(
            messageListeners = { TRAMessageListener3.class })
public class ConfigPropertyValidationActivationAnn3 implements javax.resource.spi.ActivationSpec {

    public ConfigPropertyValidationActivationAnn3() {
        super();
    }

    private String password;

    private String userName;

    private Integer x;

    public Integer getX() {
        return x;
    }

    @ConfigProperty(
                    supportsDynamicUpdates = false)
    public void setX(Integer x) {
        this.x = x;
    }

    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    private void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

/*
 * (non-Javadoc)
 *
 * @see javax.resource.spi.ResourceAdapterAssociation#getResourceAdapter()
 */
    @Override
    public ResourceAdapter getResourceAdapter() {
        return null;
    }

/*
 * (non-Javadoc)
 *
 * @see javax.resource.spi.ResourceAdapterAssociation#setResourceAdapter(javax.resource.spi.ResourceAdapter)
 */
    @Override
    public void setResourceAdapter(ResourceAdapter arg0) throws ResourceException {}

/*
 * (non-Javadoc)
 *
 * @see javax.resource.spi.ActivationSpec#validate()
 */
    @Override
    public void validate() throws InvalidPropertyException {}

}
