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

package com.ibm.ws.jpa.fvt.jpa20.querylockmode.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>Id class of the Common Datamodel (which uses all the possible JPA 2.0 Annotations as described in the
 * <a href="http://www.j2ee.me/javaee/6/docs/api/javax/persistence/package-summary.html">javax.persistence documentation</a>)
 *
 *
 * <p><b>Notes:</b>
 * <ol>
 * <li>Per the JSR-317 spec (page 28), the primary key class:
 * <ul>
 * <li>Must be serializable
 * <li>Must define equals and hashCode methods
 * </ul>
 * </ol>
 */
public class IdClass0316 implements Serializable {

    private BigDecimal entity0316_id1;

    private BigInteger entity0316_id2;

    private java.util.Date entity0316_id3;

    public IdClass0316() {}

    public IdClass0316(BigDecimal id1,
                       BigInteger id2,
                       java.util.Date id3) {
        this.entity0316_id1 = id1;
        this.entity0316_id2 = id2;
        this.entity0316_id3 = id3;
    }

    @Override
    public String toString() {
        return (" IdClass0316: " +
                " entity0316_id1: " + getEntity0316_id1() +
                " entity0316_id2: " + getEntity0316_id2() +
                " entity0316_id3: " + getEntity0316_id3());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof IdClass0316))
            return false;
        if (o == this)
            return true;
        IdClass0316 idClass = (IdClass0316) o;
        return (idClass.entity0316_id1 == entity0316_id1 &&
                idClass.entity0316_id2 == entity0316_id2 &&
                idClass.entity0316_id3 == entity0316_id3);
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = result + entity0316_id1.hashCode();
        result = result + entity0316_id2.hashCode();
        result = result + entity0316_id3.hashCode();
        return result;
    }

    //----------------------------------------------------------------------------------------------
    // Persisent property accessor(s)
    //----------------------------------------------------------------------------------------------
    public BigDecimal getEntity0316_id1() {
        return entity0316_id1;
    }

    public BigInteger getEntity0316_id2() {
        return entity0316_id2;
    }

    public java.util.Date getEntity0316_id3() {
        return entity0316_id3;
    }
}
