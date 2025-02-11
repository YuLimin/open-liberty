/*******************************************************************************
 * Copyright (c) 2013, 2021 IBM Corporation and others.
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

package com.ibm.ws.ejbcontainer.fat.rar.activationSpec;

/**
 * This class is for verifying if null object or a String
 * is being associated to the instance of ActivationSpecCompMsgDestStringImpl.
 */
// 313344.1 extend with ActivationSpecImpl
public class ActivationSpecCompMsgDestStringVerifyImpl extends ActivationSpecImpl {
    static String destinationStringNullDest = null;
    static String destinationStringValidDest = null;

    /**
     * This method will set the destination object of type String which is referenced globally
     * by the destination property value in the ActivationSpec in resources.xml.
     * The signature of setDestination method in the AS is of type String.
     *
     * @param String
     */
    public static void setDestinationNullDest(String destString) {
        destinationStringNullDest = destString;
    }

    /**
     * This method will get the destination object of type String which is referenced globally
     * by the destination property value in the ActivationSpec in resources.xml.
     * The signature of setDestination method in the AS is of type String.
     *
     * @return String
     */
    public static String getDestinationNullDest() {
        return destinationStringNullDest;
    }

    /**
     * This method will set the destination object of type String which is referenced globally
     * by the destinationJndiName in the ActivationSpec in resources.xml.
     * The signature of setDestination method in the AS is of type String.
     *
     * @param String
     */
    public static void setDestinationValidDest(String destString) {
        destinationStringValidDest = destString;
    }

    /**
     * This method will get the destination object of type String which is referenced globally
     * by the destinationJndiName in the ActivationSpec in resources.xml.
     * The signature of setDestination method in the AS is of type String.
     *
     * @return String
     */
    public static String getDestinationValidDest() {
        return destinationStringValidDest;
    }
}