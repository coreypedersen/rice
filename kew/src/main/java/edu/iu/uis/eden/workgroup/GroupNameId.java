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
package edu.iu.uis.eden.workgroup;

/**
 * A {@link GroupId} which identifies the name of a {@link Workgroup}.
 * 
 * @see Workgroup
 *
 * @author rkirkend
 */
public final class GroupNameId implements GroupId {
    
	private static final long serialVersionUID = -4625193242111678434L;

	private String nameId;
    
    public GroupNameId(String nameId) {
        this.nameId = nameId;
    }
    
    public String getNameId() {
        return nameId;
    }
    
    public boolean isEmpty() {
      return (nameId == null) || (nameId.trim().length() == 0);
  }
    
    /**
     * If you make this class non-final, you must rewrite equals to work for subclasses.
     */
    public boolean equals(Object obj) {
        boolean isEqual = false;

        if (obj != null && (obj instanceof GroupNameId)) {
            GroupNameId w = (GroupNameId) obj;

            if (w.getNameId() != null && getNameId() != null) {
                return w.getNameId().equals(getNameId());
            } else {
                return false;
            }
        }

        return isEqual;
    }

    public int hashCode() {
        if (nameId == null) {
            return 0;
        }
        return nameId.hashCode();
    }

    public String toString() {
        if (nameId != null) {
            return nameId;
        }
        return "null";
    }
    
}
