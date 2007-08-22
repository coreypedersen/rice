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
/*
 * Created on Jan 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.iu.uis.eden.responsibility;

import edu.iu.uis.eden.responsibility.dao.ResponsibilityIdDAO;

/**
 * @author rkirkend
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResponsibilityIdServiceImpl implements ResponsibilityIdService {

	private ResponsibilityIdDAO responsibilityIdDAO;
	
	public Long getNewResponsibilityId() {
		return getResponsibilityIdDAO().getNewResponsibilityId();
	}

	/**
	 * @return Returns the responsibilityIdDAO.
	 */
	public ResponsibilityIdDAO getResponsibilityIdDAO() {
		return responsibilityIdDAO;
	}
	/**
	 * @param responsibilityIdDAO The responsibilityIdDAO to set.
	 */
	public void setResponsibilityIdDAO(ResponsibilityIdDAO responsibilityIdDAO) {
		this.responsibilityIdDAO = responsibilityIdDAO;
	}
}
