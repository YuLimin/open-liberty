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
package io.openliberty.xmlbinding.tools.internal;

import java.util.ResourceBundle;

public class JaxbToolsConstants {
    public static final String PARAM_HELP = "-help";

    public static final String PARAM_VERSION = "-version";

    public static final String PARAM_TARGET = "-target";

    public static final String TR_GROUP = "JaxbTools";

    public static final String TR_RESOURCE_BUNDLE = "io.openliberty.xmlbinding.tools.internal.resources.JaxbToolsMessages";

    public static final String ERROR_PARAMETER_TARGET_MISSED_KEY = "error.parameter.target.missed";

    public static final ResourceBundle messages = ResourceBundle.getBundle(TR_RESOURCE_BUNDLE);
}
