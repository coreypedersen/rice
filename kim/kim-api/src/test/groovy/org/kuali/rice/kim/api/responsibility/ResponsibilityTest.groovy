/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.rice.kim.api.responsibility

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import org.junit.Assert
import org.junit.Test
import org.kuali.rice.kim.api.common.attribute.KimAttribute
import org.kuali.rice.kim.api.common.attribute.KimAttributeData
import org.kuali.rice.kim.api.common.attribute.KimAttributeDataContract
import org.kuali.rice.kim.api.permission.Permission
import org.kuali.rice.kim.api.permission.PermissionContract
import org.kuali.rice.kim.api.type.KimType

class ResponsibilityTest {

	private static final String OBJECT_ID = UUID.randomUUID()
	private static final Long VERSION_NUMBER = new Long(1)
	private static final boolean ACTIVE = "true"
	
	private static final String ID = "50"
	private static final String NAMESPACE_CODE = "KUALI"
	private static final String NAME = "PermissionName"
	private static final String DESCRIPTION = "Some Permission Description"
	private static final String TEMPLATE_ID = "7317791873"

	private static final String ATTRIBUTES_1_ID = "1"
	private static final String ATTRIBUTES_1_PERMISSION_ID = "50"
	private static final String ATTRIBUTES_1_VALUE = "Some Attribute Value 1"
	private static final Long ATTRIBUTES_1_VER_NBR = new Long(1)
	private static final String ATTRIBUTES_1_OBJ_ID = UUID.randomUUID()
	
	private static final KimType KIM_TYPE_1
	private static final String KIM_TYPE_1_ID = "1"
	private static final String KIM_TYPE_1_OBJ_ID = UUID.randomUUID()
	static {
		KimType.Builder builder = KimType.Builder.create()
		builder.setId(KIM_TYPE_1_ID)
		builder.setNamespaceCode(NAMESPACE_CODE)
		builder.setActive(ACTIVE)
		builder.setVersionNumber(VERSION_NUMBER)
		builder.setObjectId(KIM_TYPE_1_OBJ_ID)
		KIM_TYPE_1 = builder.build()
	}
		
	private static final KimAttribute KIM_ATTRIBUTE_1
	private static final String KIM_ATTRIBUTE_1_ID = "1"
	private static final String KIM_ATTRIBUTE_1_COMPONENT_NAME = "the_component1"
	private static final String KIM_ATTRIBUTE_1_NAME = "the_attribute1"
	static {
		KimAttribute.Builder builder = KimAttribute.Builder.create(KIM_ATTRIBUTE_1_COMPONENT_NAME, KIM_ATTRIBUTE_1_NAME, NAMESPACE_CODE)
		builder.setId(KIM_ATTRIBUTE_1_ID)
		KIM_ATTRIBUTE_1 = builder.build()
	}
	
	private static final String XML = """
		<responsibility xmlns="http://rice.kuali.org/kim/v2_0">
			<id>${ID}</id>
			<namespaceCode>${NAMESPACE_CODE}</namespaceCode>
			<name>${NAME}</name>
			<description>${DESCRIPTION}</description>
			<templateId>${TEMPLATE_ID}</templateId>
			<attributes>
			    <attribute>
	                <id>${ATTRIBUTES_1_ID}</id>
	                <attributeValue>${ATTRIBUTES_1_VALUE}</attributeValue>
	                <assignedToId>${ATTRIBUTES_1_PERMISSION_ID}</assignedToId>
	                <versionNumber>${VERSION_NUMBER}</versionNumber>
	                <objectId>${ATTRIBUTES_1_OBJ_ID}</objectId>
	                <kimType>
	                    <id>${KIM_TYPE_1_ID}</id>
	                    <namespaceCode>${NAMESPACE_CODE}</namespaceCode>
	                    <active>${ACTIVE}</active>
	                    <versionNumber>${VERSION_NUMBER}</versionNumber>
	                    <objectId>${KIM_TYPE_1_OBJ_ID}</objectId>
	                </kimType>
	                <kimAttribute>
                    	<id>${KIM_ATTRIBUTE_1_ID}</id>
                    	<componentName>${KIM_ATTRIBUTE_1_COMPONENT_NAME}</componentName>
						<attributeName>${KIM_ATTRIBUTE_1_NAME}</attributeName>
						<namespaceCode>${NAMESPACE_CODE}</namespaceCode>
						<versionNumber>${VERSION_NUMBER}</versionNumber>
					</kimAttribute>					
				</attribute>  
			</attributes>
			<active>${ACTIVE}</active>
			<versionNumber>${VERSION_NUMBER}</versionNumber>
        	<objectId>${OBJECT_ID}</objectId>
		</responsibility>
		"""
	
    @Test
    void happy_path() {
        Permission.Builder.create(ID, NAMESPACE_CODE, NAME, TEMPLATE_ID)
    }

	@Test(expected = IllegalArgumentException.class)
	void test_Builder_fail_ver_num_null() {
		Permission.Builder.create(ID, NAMESPACE_CODE, NAME, TEMPLATE_ID).setVersionNumber(null);
	}

	@Test(expected = IllegalArgumentException.class)
	void test_Builder_fail_ver_num_less_than_1() {
		Permission.Builder.create(ID, NAMESPACE_CODE, NAME, TEMPLATE_ID).setVersionNumber(-1);
	}
	
	@Test
	void test_copy() {
		def o1b = Permission.Builder.create(ID, NAMESPACE_CODE, NAME, TEMPLATE_ID)
		o1b.description = DESCRIPTION

		def o1 = o1b.build()

		def o2 = Permission.Builder.create(o1).build()

		Assert.assertEquals(o1, o2)
	}
	
	@Test
	public void test_Xml_Marshal_Unmarshal() {
	  JAXBContext jc = JAXBContext.newInstance(Responsibility.class)
	  Marshaller marshaller = jc.createMarshaller()
	  StringWriter sw = new StringWriter()

	  Responsibility responsibility = this.create()
	  marshaller.marshal(responsibility,sw)
	  String xml = sw.toString()

	  Unmarshaller unmarshaller = jc.createUnmarshaller();
	  Object actual = unmarshaller.unmarshal(new StringReader(xml))
	  Object expected = unmarshaller.unmarshal(new StringReader(XML))
	  Assert.assertEquals(expected,actual)
	}
	
	private create() {
		Responsibility responsibility = Responsibility.Builder.create(new ResponsibilityContract() {
			String getId() {ResponsibilityTest.ID}
			String getNamespaceCode() {ResponsibilityTest.NAMESPACE_CODE}
			String getName() {ResponsibilityTest.NAME}
			String getDescription() {ResponsibilityTest.DESCRIPTION}
			String getTemplateId() {ResponsibilityTest.TEMPLATE_ID}
			List<KimAttributeData> getAttributes() {[
				KimAttributeData.Builder.create(new KimAttributeDataContract() {
					 String getId() {ResponsibilityTest.ATTRIBUTES_1_ID}
					 String getAttributeValue() {ResponsibilityTest.ATTRIBUTES_1_VALUE}
                     String getAssignedToId() {ResponsibilityTest.ATTRIBUTES_1_PERMISSION_ID}
					 Long getVersionNumber() {ResponsibilityTest.ATTRIBUTES_1_VER_NBR}
					 KimType getKimType() {ResponsibilityTest.KIM_TYPE_1}
                     KimAttribute getKimAttribute() {ResponsibilityTest.KIM_ATTRIBUTE_1}
                     String getObjectId() {ResponsibilityTest.ATTRIBUTES_1_OBJ_ID}}).build() ]}
			boolean isActive() {ResponsibilityTest.ACTIVE.toBoolean()}
			Long getVersionNumber() {ResponsibilityTest.VERSION_NUMBER}
			String getObjectId() {ResponsibilityTest.OBJECT_ID}
		}).build()
		
		return responsibility
	}
}
