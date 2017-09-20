package personTestSuites.personMda.ADD;

import _driverScript.AbstractStartWebDriver;
import common.actions.PersonActions;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personDetach.actions.DetachActions;
import person.personMda.actions.MdaActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static person.expectedResults.panels.PersonCardUtilities.getAPersonWithoutNextDayAssigned;
import static person.expectedResults.panels.PersonCardUtilities.getIndicatorCode;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.*;


public class TEST003_PERSONNEL_MDA_and_DETACH_MINERVA3825 extends AbstractStartWebDriver {
    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    public static String location;
    public static  String toLocation;
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String swMdaType = "3 - MDA 3 - LIMITED PHYSICAL DUTY";
    public static String swMdaCode = "3";
    public static String appointmentDate = Utilities.getDesiredDateInFormat(1, "MM/dd/yyyy");
    public static String exAppointmentDate = Utilities.getDesiredDateInFormat(1, "M/d/yyyy");
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");

    @Test(priority = 1, description = "MDA_ADD_DETACH_SW")
    public void MDA_ADD_DETACH_SW () throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        String remarks = "TEST003_Person_MDA_DETACH" + Utilities.getUUID();
        location = setPersonLocationForTest("Available", "SW", 3);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Task");
        clickOnMenuIcon(wDriver, "Equipment");
        //Test Data
        String[] expectedDetails = {"3", "Add", "Active", exStartDate, exAppointmentDate, remarks};
        //swCardName = getFreshCardForTest("Avail", "SW", "Mda");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);

        int availableCountBefore = PersonActions.get_cards_count("auPersonnelAvailablePanel", null, null);
        int availableSWCountBefore = PersonActions.get_cards_count("auPersonnelAvailablePanel", "auSanitationWorkersHeader",null);
        int mdaCountBefore = PersonActions.get_cards_count("auPersonnelMdaPanel", null,null);

        MdaActions.addMda(swCardName, startDate, swMdaType, appointmentDate, "", "", "", remarks);
        Thread.sleep(4000);
        String mdaCodeAfter = getIndicatorCode(swCardName, "Mda");
        personDetailPanelPage().closePersonCardDetailPanel();

        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations.....");
        try {
            logTestInfo(wDriver, "Verifying Indicator Value: " + swMdaCode);
            Assert.assertEquals(swMdaCode, mdaCodeAfter);
            logTestInfo(wDriver, "Verifying card in MDA and NOT in Available");

            int availableCountAfter = PersonActions.get_cards_count("auPersonnelAvailablePanel", null, null);
            int availableSWCountAfter = PersonActions.get_cards_count("auPersonnelAvailablePanel", "auSanitationWorkersHeader",null);
            int mdaCountAfter = PersonActions.get_cards_count("auPersonnelMdaPanel", null,null);

            Assert.assertTrue(verifyCardPresenceInCategory("Mda", null, null, swCardName), "Card in MDA");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card NOT in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availableCountAfter, availableCountBefore - 1, "Available Card Counts");
            Assert.assertEquals(availableSWCountAfter, availableSWCountBefore - 1, "Available SW Card Counts");
            Assert.assertEquals(mdaCountAfter, mdaCountBefore + 1, "Available MDA Card Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Mda", remarks, expectedDetails);
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
        //detach and verify MDA indicator
        int mdaCountBeforeDetach = PersonActions.get_cards_count("auPersonnelMdaPanel", null,null);
        int detachCountBefore = PersonActions.get_cards_count("auPersonnelDetachmentPanel", "auSanitationWorkersHeader",null);
        toLocation = getNonCurrentLocation(location);
        logTestInfo(wDriver, "Now Performing  Detach Add Action....");
        DetachActions.addDetach(swCardName, toLocation, startDate, null, null, null, remarks);
        Thread.sleep(3000);
        personDetailPanelPage().closePersonCardDetailPanel();

        logTestInfo(wDriver, "Now Performing Test Validations after Detach.....");
        int mdaCountAfterDetach = PersonActions.get_cards_count("auPersonnelMdaPanel", null,null);
        int detachCountAfter = PersonActions.get_cards_count("auPersonnelDetachmentPanel", "auSanitationWorkersHeader",null);

        try {
            logTestInfo(wDriver, "Verifying card in Detach and NOT in MDA");
            Assert.assertTrue(verifyCardPresenceInCategory("Detached", null, null, swCardName), "Card in Detach");
            logTestInfo(wDriver, "Verifying Indicator Value in Detached: " + swMdaCode);
            Assert.assertEquals(swMdaCode, mdaCodeAfter);
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(mdaCountAfterDetach, mdaCountBeforeDetach - 1, "MDA Card Counts");
            Assert.assertEquals(detachCountAfter, detachCountBefore + 1, "Detach SW Card Counts");
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");

        }
        catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();

        }
    }


}
