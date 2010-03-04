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
package org.kuali.rice.kim.bo.ui;

import javax.persistence.Id;

import org.kuali.rice.kns.bo.CompositePrimaryKeyBase;

/**
 * This is a description of what this class does - jksmith don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
public class KimDocumentRoleResponsibilityId extends CompositePrimaryKeyBase {
	@Id
    //@Column(name="FDOC_NBR")
    protected String documentNumber;
	
	@Id
	protected String roleResponsibilityId;

	/**
	 * @return the documentNumber
	 */
	public String getDocumentNumber() {
		return this.documentNumber;
	}

	/**
	 * @return the delegationId
	 */
	public String getRoleResponsibilityId() {
		return this.roleResponsibilityId;
	}
}
