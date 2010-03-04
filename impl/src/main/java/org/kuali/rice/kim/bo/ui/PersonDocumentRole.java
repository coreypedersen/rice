/*
 * Copyright 2007-2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kim.bo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.bo.impl.RoleImpl;
import org.kuali.rice.kim.bo.role.impl.RoleResponsibilityImpl;
import org.kuali.rice.kim.bo.types.dto.AttributeDefinitionMap;
import org.kuali.rice.kim.bo.types.dto.KimTypeInfo;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.service.support.KimTypeService;
import org.kuali.rice.kim.util.KimCommonUtils;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.util.TypedArrayList;

/**
 * This is a description of what this class does - shyu don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */

@Entity
@IdClass(org.kuali.rice.kim.bo.ui.PersonDocumentRoleId.class)
@Table(name="KRIM_PND_ROLE_MT")
public class PersonDocumentRole extends KimDocumentBoBase {
    private static final Logger LOG = Logger.getLogger(PersonDocumentRole.class);
	private static final long serialVersionUID = 4908044213007222739L;
	@Id
	@Column(name="ROLE_ID")
	protected String roleId;
	@Column(name="KIM_TYP_ID")
	protected String kimTypeId;
	@Column(name="ROLE_NM")
	protected String roleName;
	@Transient
	protected RoleImpl roleImpl;
	@Column(name="NMSPC_CD")
	protected String namespaceCode;
	@Transient
	protected KimTypeInfo kimRoleType;
	@Transient
	protected List<? extends KimAttributes> attributes;
	@Transient
	protected transient AttributeDefinitionMap definitions;
	@Transient
	protected transient Map<String,Object> attributeEntry;
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinColumns({
		@JoinColumn(name="ROLE_ID",insertable=false,updatable=false),
		@JoinColumn(name="FDOC_NBR", insertable=false, updatable=false)
	})
	protected List<KimDocumentRoleMember> rolePrncpls;
	@Transient
    protected KimDocumentRoleMember newRolePrncpl;
	//currently mapped as manyToMany even though it is technically a OneToMany
	//The reason for this is it is linked with a partial Composite-id, which technically can't 
	//guarantee uniqueness.  
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinColumn(name="ROLE_ID",insertable=false,updatable=false)
	protected List<RoleResponsibilityImpl> assignedResponsibilities = new TypedArrayList(RoleResponsibilityImpl.class);

	@Transient
    protected boolean isEditable = true;
    
	public PersonDocumentRole() {
		attributes = new ArrayList<KimAttributes>();	
		rolePrncpls = new ArrayList<KimDocumentRoleMember>();	
		attributeEntry = new HashMap<String,Object>();
	}
	
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap m = new LinkedHashMap();
		m.put( "roleId", roleId );
		return m;
	}

	public String getKimTypeId() {
		return this.kimTypeId;
	}

	public void setKimTypeId(String kimTypeId) {
		this.kimTypeId = kimTypeId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<? extends KimAttributes> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(List<? extends KimAttributes> attributes) {
		this.attributes = attributes;
	}

	public KimTypeInfo getKimRoleType() {
		if ( kimRoleType == null ) {
			kimRoleType = KIMServiceLocator.getTypeInfoService().getKimType(kimTypeId);
		}
		return kimRoleType;
	}

	@Deprecated // for testing only
	public void setKimRoleType(KimTypeInfo kimRoleType) {
		this.kimRoleType = kimRoleType;
	}

	public AttributeDefinitionMap getDefinitionsKeyedByAttributeName() {
		AttributeDefinitionMap definitionsKeyedBySortCode = getDefinitions();
		AttributeDefinitionMap returnValue = new AttributeDefinitionMap();
		if (definitionsKeyedBySortCode != null) {
			for (AttributeDefinition definition : definitionsKeyedBySortCode.values()) {
				returnValue.put(definition.getName(), definition);
			}
		}
		return returnValue;
	}

	public AttributeDefinitionMap getDefinitions() {
		if (definitions == null || definitions.isEmpty()) {
	        KimTypeService kimTypeService = KimCommonUtils.getKimTypeService( this.getKimRoleType() );
	        //it is possible that the the roleTypeService is coming from a remote application 
	        // and therefore it can't be guarenteed that it is up and working, so using a try/catch to catch this possibility.
	        try {
    	        if ( kimTypeService != null ) {
    	        	definitions = kimTypeService.getAttributeDefinitions(getKimTypeId());
    	        } else {
    	        	definitions = new AttributeDefinitionMap();
    	        }
    		} catch (Exception ex) {
                LOG.warn("Not able to retrieve KimTypeService from remote system for KIM Role Type: " + this.getKimRoleType(), ex);
            }
		}
		
		return definitions;
	}

	public void setDefinitions(AttributeDefinitionMap definitions) {
		this.definitions = definitions;
	}

	public Map<String,Object> getAttributeEntry() {
		if (attributeEntry == null || attributeEntry.isEmpty()) {
			attributeEntry = KIMServiceLocator.getUiDocumentService().getAttributeEntries(getDefinitions());
		}
		
		return this.attributeEntry;
	}

	public void setAttributeEntry(Map<String,Object> attributeEntry) {
		this.attributeEntry = attributeEntry;
	}

	public List<KimDocumentRoleMember> getRolePrncpls() {
		return this.rolePrncpls;
	}

	public void setRolePrncpls(List<KimDocumentRoleMember> rolePrncpls) {
		this.rolePrncpls = rolePrncpls;
	}

	public KimDocumentRoleMember getNewRolePrncpl() {
		return this.newRolePrncpl;
	}

	public void setNewRolePrncpl(KimDocumentRoleMember newRolePrncpl) {
		this.newRolePrncpl = newRolePrncpl;
	}

	public String getNamespaceCode() {
		return this.namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

	public List<RoleResponsibilityImpl> getAssignedResponsibilities() {
		return this.assignedResponsibilities;
	}

	public void setAssignedResponsibilities(
			List<RoleResponsibilityImpl> assignedResponsibilities) {
		this.assignedResponsibilities = assignedResponsibilities;
	}

	@Override
	public boolean isActive(){
		return this.active;
	}

	/**
	 * @return the roleImpl
	 */
	public RoleImpl getRoleImpl() {
		return this.roleImpl;
	}

	/**
	 * @param roleImpl the roleImpl to set
	 */
	public void setRoleImpl(RoleImpl roleImpl) {
		this.roleImpl = roleImpl;
	}

	/**
	 * @return the isEditable
	 */
	public boolean isEditable() {
		return this.isEditable;
	}

	/**
	 * @param isEditable the isEditable to set
	 */
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

}
