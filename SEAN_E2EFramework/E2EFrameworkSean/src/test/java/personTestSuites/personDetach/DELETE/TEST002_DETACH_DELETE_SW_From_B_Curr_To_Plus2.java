package personTestSuites.personDetach.DELETE;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personDetach.actions.DetachActions;
import personTestSuites.personDetach.ADD.TEST002_DETACH_ADD_SW_Curr_To_Plus3;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static person.expectedResults.panels.PersonCardUtilities.getAPersonWithoutNextDayAssigned;
import static person.expectedResults.panels.PersonHistoryUtilities.getDetailHistoryRowData;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST002_DETACH_DELETE_SW_From_B_Curr_To_Plus2 extends AbstractStartWebDriver {
    //Test Data //
    private String url = LoginData.getLoginData(getUrl());
    public static String location;
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String toLocation = "MN08";
    private String swCardName;
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(2, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String endHour = "12";
    public static String endMinute = "15";
    public static String shift = "1215-2015";
    public static String reason = "Person_Detach_DELETE" + Utilities.getUUID();
    public static int rowIndex = 0;

    @Test(priority = 1, description = "Detach_Delete_SW_From_B_Curr_To_Plus2")
    public void Detach_Delete_SW_From_B_Curr_To_Plus2() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = TEST002_DETACH_ADD_SW_Curr_To_Plus3.location;
        swCardName = TEST002_DETACH_ADD_SW_Curr_To_Plus3.swCardName;
        location = location== null ? setPersonLocationForTest("Available", "SW", 5) : location;
        String[] expectedDetails = {"Detach", "Deleted", "Temp", exStartDate, location, toLocation, exEndDate, reason, shift};
        if(swCardName == null){
            loginPage().goToBoardLocationByDate(url, location+"/", date);
            swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
            deleteAllPersonHistory(swCardName);
            DetachActions.addDetach(swCardName, toLocation, startDate, endDate, endHour, endMinute, reason);
            expectedDetails = getDetailHistoryRowData(swCardName, "Detach", reason);
            Thread.sleep(1000);
            expectedDetails[1] = "Deleted";
        }
        loginPage().goToBoardLocationByDate(url, toLocation+"/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");

        int detachCountBefore = getAnyCategoryHeaderCount("Detach", "SW", null);
        int detachSWCountBefore = getAnyCategoryHeaderCount("Detach", "SW", null);
        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW", null);
        logTestInfo(wDriver, "Performing  Detach Add Action....");
        DetachActions.deleteDetach(swCardName, rowIndex, reason);
        Thread.sleep(2000);
        personDetailPanelPage().closePersonCardDetailPanel();
        int detachCountAfter = getAnyCategoryHeaderCount("Detach", "SW", null);
        int detachSWCountAfter = getAnyCategoryHeaderCount("Detach", "SW", null);
        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW", null);
        //PersonPanelUtilities.checkIfCardPresentInCategory(swCardName, "Detach", null, null);
        logTestInfo(wDriver, "Validating From Receiving Location B....");
        try {
            logTestInfo(wDriver, "Verifying card is NOT in Available and NOT in Detach....");
            Assert.assertFalse(verifyCardPresenceInCategory("Available", "SW", "", swCardName));
            Assert.assertFalse(verifyCardPresenceInCategory("Detach", "SW", "", swCardName));
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(detachSWCountAfter, detachSWCountBefore);
            Assert.assertEquals(detachCountAfter, detachCountBefore);
            Assert.assertEquals(availableCountAfter, availableCountBefore - 1);
            Assert.assertEquals(availableSWCountAfter, availableSWCountBefore - 1);
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
        logTestInfo(wDriver, "Validating From Sending Location A....");
        loginPage().goToBoardLocationByDate(url, location+"/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        //clickOnMenuIcon(wDriver, "Task");
        try {
            logTestInfo(wDriver, "Verifying card in Available and NOT in Detach....");
            Assert.assertTrue(verifyCardPresenceInCategory("Available", "SW", "", swCardName));
            Assert.assertFalse(verifyCardPresenceInCategory("Detach", "SW", "", swCardName));
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Detach", reason, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

    }



}