package personTestSuites.personDetach.ADD;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personDetach.actions.DetachActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.data.RawColorCodes.getRGBAColorName;
import static person.expectedResults.panels.PersonCardUtilities.getAPersonWithoutNextDayAssigned;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST001_DETACH_ADD_SUP_Curr_To_Plus3_Validate_Present_Past_Future_MINERVA_3789 extends AbstractStartWebDriver {

    //Test Data //
    public static String url = LoginData.getLoginData(getUrl());
    public static String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String pastDate = Utilities.getDesiredDateInFormat(-1, "yyyyMMdd");
    public static String futureDate = Utilities.getDesiredDateInFormat(4, "yyyyMMdd");
    public static String toLocation = "MN08";
    public static String location;
    public static String supCardName;
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = "12";
    public static String endMinute = "15";
    public static String shift = "1215-2015";
    public static String remarks = "Person_Detach_ADD" + Utilities.getUUID();


    @Test(priority = 1, description = "DETACH_ADD_SUP_Curr_To_Plus3_Validate_Past_Future_Board")
    public void DETACH_ADD_SUP_Curr_To_Plus3_Validate_Past_Future_Board() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SUP", 3);
        String[] expectedDetails = {"Detach", "Active", "Temp", exStartDate, location, toLocation, exEndDate, remarks, shift};

        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");

        //supCardName = getFreshCardForTest("Avail", "SUP", "Detach");
        supCardName = getAPersonWithoutNextDayAssigned("Available", "SUP", null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(supCardName, "Personnel"));
        Reporter.log("Person Card Name is: " + supCardName, true);
        //deleteAllPersonHistory(supCardName);
        deletePersonHistoryByTable(supCardName, "Detach");
        deletePersonHistoryByTable(supCardName, "Unavail");

        int detachCountBefore = getAnyCategoryHeaderCount("Detach", "SUP", null);
        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSUPCountBefore = getAnyCategoryHeaderCount("Available", "SUP", null);
        logTestInfo(wDriver, "SUP Detach Count Before : " + detachCountBefore + " Available Count Before : " + availableCountBefore);
        logTestInfo(wDriver, "Now Performing  Detach Add Action....");
        DetachActions.addDetach(supCardName, toLocation, startDate, endDate, endHour, endMinute, remarks);
        Thread.sleep(3000);
        personDetailPanelPage().closePersonCardDetailPanel();
        int detachCountAfter = getAnyCategoryHeaderCount("Detach", "SUP", null);
        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSUPCountAfter = getAnyCategoryHeaderCount("Available", "SUP", null);
        String cardColorAfter = getRGBAColorName(getRawElementColor(supCardName, "Personnel"));
        //Validate count before and after
        logTestInfo(wDriver, "Validating From **** CURRENT *** Board in Sending Location A....");
        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying card in Detach and NOT in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("Detach", "SUP", "", supCardName), "Card in Detached SUP");
            Assert.assertFalse(verifyCardPresenceInCategory("Available", "SUP", "", supCardName), "Card NOT in Available SUP");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(detachCountAfter, detachCountBefore + 1, "Detach Card Counts");
            Assert.assertEquals(availableCountAfter, availableCountBefore - 1, "Available Card Counts");
            Assert.assertEquals(availableSUPCountAfter, availableSUPCountBefore - 1, "Available SUP Card Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(supCardName, "Detach", remarks, expectedDetails);

            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
        //Validate From Past Board
        logTestInfo(wDriver, "Validating From **** PAST *** Board in Sending Location A....");
        try {
            loginPage().goToBoardLocationByDate(url, location + "/", pastDate);
            expectedDetails[1] = "Future";
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(supCardName, "Detach", remarks, expectedDetails);

            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");

        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
        //Validate From Future Board
        logTestInfo(wDriver, "Validating From **** FUTURE *** Board in Sending Location A....");
        try {
            loginPage().goToBoardLocationByDate(url, location + "/", futureDate);
            expectedDetails[1] = "Completed";
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(supCardName, "Detach", remarks, expectedDetails);

            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");

        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }


}