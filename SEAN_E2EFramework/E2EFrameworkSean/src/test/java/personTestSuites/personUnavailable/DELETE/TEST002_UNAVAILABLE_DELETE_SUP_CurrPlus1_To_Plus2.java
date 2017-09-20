package personTestSuites.personUnavailable.DELETE;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personUnavailable.actions.UnavailableActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.*;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;

/**
 * Created by sdas on 11/22/2016.
 */
public class TEST002_UNAVAILABLE_DELETE_SUP_CurrPlus1_To_Plus2 extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKN02";
    private String date = Utilities.getDesiredDateInFormat(1, "yyyyMMdd");
    public static String supCardName;
    public static String unavailbleCode = "XWP";
    public static String effectiveDate = Utilities.getDesiredDateInFormat(1, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(1, "M/d/yyyy");
    public static String effectiveHour = "12";
    public static String effectiveMinute = "59";
    public static String endDate = Utilities.getDesiredDateInFormat(2, "MM/dd/yyyy");
    ;
    public static String expectedEndDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String endHour = "02";
    public static String endMinute = "22";
    public static String remarks;
    public static String action = "REMOVE";
    public static String status = "Deleted";
    String cardColorAfter = null;
    String cardColorBefore = null;
    int rowNumber = 0;


    @Test(priority = 1, description = "UNAVAILABLE_DELETE_SUP_CurrPlus1_To_Plus2")
    public void UNAVAILABLE_DELETE_SUP_CurrPlus1_To_Plus2() throws InterruptedException, IOException {
        extentTest.assignCategory("Smoke", "Regression", "Unavailable");
        location = setPersonLocationForTest("Available", "SUP", 3);
        remarks = "UNAVAILABLE_DELETE_SUP" + Utilities.getUUID();
        String[] expectedDetailsSUP = {unavailbleCode, action, status, expectedEffectiveDate, effectiveHour + ":" + effectiveMinute, expectedEndDate, endHour + ":" + endMinute, remarks};
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        //Thread.sleep(2000);
        supCardName = !(supCardName==null) ? supCardName: getPersonName("Unavailable", "SUP", unavailbleCode);
        Reporter.log("Person Card Name is: " + supCardName, true);
        int availableSUPCountBefore = getAnyCategoryHeaderCount("Available", "SUP");
        int unavailableChartCountBefore = getAnyCategoryHeaderCount("Unavailable", unavailbleCode);
        UnavailableActions.deleteUnavailable(supCardName, rowNumber, remarks);
        Thread.sleep(2000);
        //personDetailPanelPage().closePersonCardDetailPanel();
        int availableSUPCountAfter = getAnyCategoryHeaderCount("Available", "SUP");
        int unavailableChartCountAfter = getAnyCategoryHeaderCount("Unavailable", unavailbleCode);
        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations.....");
        try {
            //logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            //Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying card in Unaavailable and NOT in Available");
            Assert.assertFalse(verifyCardPresenceInCategory("Unavail", null, null, supCardName), "Card NOT in Unavailable");
            Assert.assertTrue(verifyCardPresenceInCategory("AvailableUn", "SUP", null, supCardName), "Card IN Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(unavailableChartCountAfter, unavailableChartCountBefore - 1, "Unavailable Chart Counts");
            Assert.assertEquals(availableSUPCountAfter, availableSUPCountBefore + 1, "Available SUP Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(supCardName, "Unavail", remarks, expectedDetailsSUP);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

    }

}
