/*
 * Copyright 2005-2007 The Kuali Foundation.
 * 
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
package edu.iu.uis.eden.xml.export;

import org.junit.Test;
import org.kuali.workflow.test.WorkflowTestCase;

public abstract class XmlExporterTestCase extends WorkflowTestCase {

	@Test public void testExportActionConfig() throws Exception { 
        loadXmlFile("edu/iu/uis/eden/actions/ActionsConfig.xml");
        assertExport();
    }
    
	@Test public void testExportEngineConfig() throws Exception {
        loadXmlFile("edu/iu/uis/eden/engine/EngineConfig.xml");
        assertExport();
    }
        
    protected abstract void assertExport() throws Exception;

}
