/**
 * Copyright 2005-2013 The Kuali Foundation
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
package edu.samplu.krad.demo.travelapplication.accountmultivalue.lookup;

import edu.samplu.common.SmokeTestBase;
import org.junit.Test;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class DemoTravelAccountMultivalueLookUpSmokeTest extends SmokeTestBase {

    /**
     * /kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.rice.krad.demo.travel.dataobject.TravelAccount&hideReturnLink=true&multipleValuesSelect=true&suppressActions=true&conversionFields=number:foo,name:foo
     */
    public static final String BOOKMARK_URL = "/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.rice.krad.demo.travel.dataobject.TravelAccount&hideReturnLink=true&multipleValuesSelect=true&suppressActions=true&conversionFields=number:foo,name:foo";
   
    /**
     * lookupCriteria[accountTypeCode]
     */
    public static final String ACCOUNT_TYPE_CODE_NAME = "lookupCriteria[accountTypeCode]";

    @Override
    public String getBookmarkUrl() {
        return BOOKMARK_URL;
    }

    @Override
    protected void navigate() throws Exception {
        waitAndClickById("Demo-DemoLink", "");
        waitAndClickByLinkText("Account Multi-Value Lookup");
    }

    private void testSearchSelect() throws InterruptedException {
        waitAndClickByValue("CAT");
        waitAndClickButtonByText(SEARCH);
        waitAndClickByName("selectedCollectionLines['lookupResults']");
        assertTextPresent("a14");
        assertTextPresent("a6");
        assertTextPresent("a9");
        assertButtonEnabledByText(RETURN_SELECTED_BUTTON_TEXT);
        waitAndClickByName("selectedCollectionLines['lookupResults']");
        assertButtonDisabledByText(RETURN_SELECTED_BUTTON_TEXT);

        assertMultiValueSelectAllThisPage();
        assertMultiValueDeselectAllThisPage();

        waitAndClickByName("selectedCollectionLines['lookupResults']");
        waitAndClickButtonByText(SEARCH);
        checkForIncidentReport();
    }

    @Test
    public void testTravelAccountMultivalueLookUpSearchSelectBookmark() throws Exception {
        testSearchSelect();
        passed();
    }

    @Test
    public void testTravelAccountMultivalueLookUpSearchSelectNav() throws Exception {
        testSearchSelect();
        passed();
    }

    @Test
    public void testTravelAccountMultivalueLookUpSelectThisPageBookmark() throws Exception {
        testMultiValueSelectAllThisPage();
        passed();
    }

    @Test
    public void testTravelAccountMultivalueLookUpSelectThisPageNav() throws Exception {
        testMultiValueSelectAllThisPage();
        passed();
    }

    @Test
    public void testTravelAccountMultivalueLookUpSelectAllPagesBookmark() throws Exception {
        testMultiValueSelectAllPages();
        passed();
    }

    @Test
    public void testTravelAccountMultivalueLookUpSelectAllPagesNav() throws Exception {
        testMultiValueSelectAllPages();
        passed();
    }
}