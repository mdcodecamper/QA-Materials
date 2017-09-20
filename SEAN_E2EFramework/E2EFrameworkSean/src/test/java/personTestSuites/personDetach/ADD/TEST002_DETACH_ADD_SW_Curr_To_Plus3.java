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


public class TEST002_DETACH_ADD_SW_Curr_To_Plus3 extends AbstractStartWebDriver {

    //Test Data //
    public static String url = LoginData.getLoginData(getUrl());
    public static String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String location;
    public static String toLocation = "MN08";
    public static String swCardName;
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = "12";
    public static String endMinute = "15";
    public static String shift = "1215-2015";
    public static String remarks = "Person_Detach_ADD" + Utilities.getUUID();;

    @Test(priority = 1, description = "DETACH_ADD_SW_Curr_To_Plus3")
    public void DETACH_ADD_SW_Curr_To_Plus3() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SW", 5);
        String[] expectedDetails = {"Detach", "Active", "Temp", exStartDate, location, toLocation, exEndDate, remarks, shift};
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        //swCardName = getFreshCardForTest("Avail", "SW", "Detach");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName,"Personnel"));
        //Reporter.log("Person Card Name is: " + swCardName, true);
        //deleteAllPersonHistory(swCardName);
        deletePersonHistoryByTable(swCardName, "Detach");
        deletePersonHistoryByTable(swCardName, "Unavail");

        int detachCountBefore = getAnyCategoryHeaderCount("Detach", "SW", null);
        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW", null);
        logTestInfo(wDriver, "SW Detach Count Before : " + detachCountBefore + " Available Count Before : " + availableCountBefore);
        logTestInfo(wDriver, "Now Performing  Detach Add Action....");
        DetachActions.addDetach(swCardName, toLocation, startDate, endDate, endHour, endMinute, remarks);
        Thread.sleep(3000);

        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName,"Personnel"));
        personDetailPanelPage().closePersonCardDetailPanel();
        int detachCountAfter = getAnyCategoryHeaderCount("Detach", "SW", null);
        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW", null);

        //Validate count before and after
        logTestInfo(wDriver, "Validating From Sending Location A....");
        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying card in Detach and NOT in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("Detach", "SW", "", swCardName), "Card in Detached SW");

            Assert.assertFalse(verifyCardPresenceInCategory("Available", "SW", "", swCardName), "Card in Available SW");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(detachCountAfter, detachCountBefore + 1, "Detach Card Counts");
            Assert.assertEquals(availableCountAfter, availableCountBefore - 1, "Available Card Counts");
            Assert.assertEquals(availableSWCountAfter, availableSWCountBefore - 1, "Available SW Card Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Detach", remarks, expectedDetails);

            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

        logTestInfo(wDriver, "Validating From Receiving Location B....");
        try {
            loginPage().goToBoardLocationByDate(url, toLocation + "/", date);
            cardColorAfter = getRGBAColorName(getRawElementColor(swCardName,"Personnel"));
            logTestInfo(wDriver, "Verifying Card Color After in Receiving Location: " + cardColorAfter);
            Assert.assertEquals(cardColorAfter, "salmon", "Card Color");

            Assert.assertTrue(verifyCardPresenceInCategory("Available", "SW", "", swCardName), "Card in Available SW");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Detach", remarks, expectedDetails);

            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }

}