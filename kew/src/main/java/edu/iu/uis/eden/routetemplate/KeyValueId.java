/*
 * Copyright 2005-2006 The Kuali Foundation.
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
package edu.iu.uis.eden.routetemplate;

import edu.iu.uis.eden.lookupable.Field;
import edu.iu.uis.eden.routetemplate.web.WebRuleBaseValues;

/**
 * Used by the {@link WebRuleBaseValues} to hold key-value-id data for {@link Field}s.
 *
 * @author jhopf
 */
public class KeyValueId {
	private String key;
	private String value;
	private String id;

	public KeyValueId() {

	}

	public KeyValueId(String key, String value, String id) {
		this.key = key;
		this.value = value;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
