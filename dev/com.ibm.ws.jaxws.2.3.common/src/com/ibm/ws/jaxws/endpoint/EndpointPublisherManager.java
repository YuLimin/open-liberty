/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
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
package com.ibm.ws.jaxws.endpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ibm.websphere.ras.Tr;
import com.ibm.websphere.ras.TraceComponent;

public class EndpointPublisherManager {

    private static final TraceComponent tc = Tr.register(EndpointPublisherManager.class);

    public Map<String, EndpointPublisher> typeEndpointPublisherManagerMap = new ConcurrentHashMap<String, EndpointPublisher>();

    public void registerEndpointPublisher(EndpointPublisher publisher) {
        if (TraceComponent.isAnyTracingEnabled() && tc.isDebugEnabled()) {
            Tr.debug(this, tc, "Register EndpointPublisher support " + publisher.getType());
        }
        typeEndpointPublisherManagerMap.put(publisher.getType(), publisher);
    }

    public void unregisterEndpointPublisher(EndpointPublisher publisher) {
        if (TraceComponent.isAnyTracingEnabled() && tc.isDebugEnabled()) {
            Tr.debug(this, tc, "unregister EndpointPublisher support " + publisher.getType());
        }
        typeEndpointPublisherManagerMap.remove(publisher.getType());
    }

    public EndpointPublisher getEndpointPublisher(String type) {
        return typeEndpointPublisherManagerMap.get(type);
    }
}
