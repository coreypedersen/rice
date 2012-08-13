package edu.samplu.mainmenu.test;

import edu.samplu.common.UpgradedSeleniumITBase;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertTrue;

/**
 * test that after doc search, navigating to people flow maintenance view does not cause Javascript errors
 * and therefore interfere with JS functionality like validation
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class DocSearchToAnotherViewIT extends UpgradedSeleniumITBase {
    @Override
    public String getTestUrl() {
        return PORTAL;
    }

	@Test
    /**
     * test that after doc search, navigating to people flow maintenance view does not cause Javascript errors
     * and therefore interfere with JS functionality like validation
     */
	public void testDocSearchToAnotherView() throws Exception {
		selenium.click("css=img[alt=\"doc search\"]");
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("iframeportlet");
		selenium.click("css=td.infoline > input[name=\"methodToCall.search\"]");
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.click("link=Main Menu");
		selenium.waitForPageToLoad("30000");
        selenium.setSpeed("2000");
		selenium.click("link=People Flow");
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("iframeportlet");
		selenium.click("link=Create New");
		selenium.waitForPageToLoad("30000");
		focusAndType("name=document.documentHeader.documentDescription", "sample description");
		focusAndType("name=document.documentHeader.explanation", "sample explanation");		
        selenium.focus("link=Cancel");
		selenium.click("link=Cancel");
        //assertTrue(selenium.getConfirmation().matches("^Form has unsaved data\\. Do you want to leave anyway[\\s\\S]$")); //Removed Confirmation Panel From the page itself
	}
	   

    private void focusAndType(String fieldLocator, String typeText) {
        selenium.focus(fieldLocator);
        selenium.type(fieldLocator, typeText);
    }
}
