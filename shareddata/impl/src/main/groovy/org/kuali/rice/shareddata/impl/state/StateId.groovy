package org.kuali.rice.shareddata.impl.state

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.persistence.Column
import javax.persistence.Id
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import org.apache.commons.lang.builder.ToStringBuilder

class StateId {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "POSTAL_CNTRY_CD")
    def final String countryCode;
    @Id
    @Column(name = "POSTAL_STATE_CD")
    def final String code;

    StateId() { }

    StateId(String countryCode, String code) {
        this.countryCode = countryCode;
        this.code = code;
    }

    @Override
    int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj, this);
    }

    @Override
    String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

