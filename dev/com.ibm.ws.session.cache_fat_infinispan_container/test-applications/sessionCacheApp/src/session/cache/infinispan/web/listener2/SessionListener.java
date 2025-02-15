/*******************************************************************************
 * Copyright (c) 2018, 2019 IBM Corporation and others.
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
package session.cache.infinispan.web.listener2;

import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    public static final LinkedBlockingQueue<String> created = new LinkedBlockingQueue<String>();
    public static final LinkedBlockingQueue<String> destroyed = new LinkedBlockingQueue<String>();

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        String sessionId = event.getSession().getId();
        System.out.println("SessionListener2 notified of sessionCreated for " + sessionId);
        created.add(sessionId);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String sessionId = event.getSession().getId();
        System.out.println("SessionListener2 notified of sessionDestroyed for " + sessionId);
        destroyed.add(sessionId);
    }
}
