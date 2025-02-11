/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/

package com.ibm.ws.jaxws.ejbjndi.web.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "WebMilkProviderService", targetNamespace = "http://web.ejbjndi.jaxws.ws.ibm.com/", wsdlLocation = "META-INF/WebMilkProviderService.wsdl")
public class WebMilkProviderService extends Service
{

    private final static URL WEBMILKPROVIDERSERVICE_WSDL_LOCATION;
    private final static WebServiceException WEBMILKPROVIDERSERVICE_EXCEPTION;
    private final static QName WEBMILKPROVIDERSERVICE_QNAME = new QName("http://web.ejbjndi.jaxws.ws.ibm.com/", "WebMilkProviderService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://ivan-pc:8010/EJBJndiWeb/WebMilkProviderService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WEBMILKPROVIDERSERVICE_WSDL_LOCATION = url;
        WEBMILKPROVIDERSERVICE_EXCEPTION = e;
    }

    public WebMilkProviderService() {
        super(__getWsdlLocation(), WEBMILKPROVIDERSERVICE_QNAME);
    }

    public WebMilkProviderService(WebServiceFeature... features) {
        super(__getWsdlLocation(), WEBMILKPROVIDERSERVICE_QNAME, features);
    }

    public WebMilkProviderService(URL wsdlLocation) {
        super(wsdlLocation, WEBMILKPROVIDERSERVICE_QNAME);
    }

    public WebMilkProviderService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WEBMILKPROVIDERSERVICE_QNAME, features);
    }

    public WebMilkProviderService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WebMilkProviderService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *         returns WebMilkProvider
     */
    @WebEndpoint(name = "WebMilkProviderPort")
    public WebMilkProvider getWebMilkProviderPort() {
        return super.getPort(new QName("http://web.ejbjndi.jaxws.ws.ibm.com/", "WebMilkProviderPort"), WebMilkProvider.class);
    }

    /**
     * 
     * @param features
     *            A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy. Supported features not in the <code>features</code> parameter will have their default
     *            values.
     * @return
     *         returns WebMilkProvider
     */
    @WebEndpoint(name = "WebMilkProviderPort")
    public WebMilkProvider getWebMilkProviderPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://web.ejbjndi.jaxws.ws.ibm.com/", "WebMilkProviderPort"), WebMilkProvider.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WEBMILKPROVIDERSERVICE_EXCEPTION != null) {
            throw WEBMILKPROVIDERSERVICE_EXCEPTION;
        }
        return WEBMILKPROVIDERSERVICE_WSDL_LOCATION;
    }

}
