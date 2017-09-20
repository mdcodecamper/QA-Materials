package personTestSuites.personUnavailable.ADD;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import person.personUnavailable.actions.UnavailableActions;
import person.specialPosition.actions.SpecialPosActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static person.expectedResults.panels.PersonCardUtilities.getIndicatorCode;
import static person.expectedResults.panels.PersonHistoryUtilities.getDetailHistoryRowData;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


/**
 * Created by sdas on 11/22/2016.
 */
public class TEST004_ADD_Unavailable_AND_SPECIAL_POS_MINERVA_3820 extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKS11";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String unavailableCode = "CHART";
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String effectiveHour = null;
    public static String effectiveMinute = null;
    public static String endDate = null;
    public static String endHour = null;
    public static String endMinute = null;
    public static String remarks;
    public static String action = "ADD";
    public static String status = "Active";
    public static String exStartDate = getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = getDesiredDateInFormat(0, "M/d/yyyy");
    public static String specialPos = "Hardship-HS";
    public static String specialPosCode = "HS";
    public static String[] actualUnavailDetails;
    public static String[] expectedUnavailDetails;

    @Test(priority = 1, description = "UNAVAILABLE_AND_SPECIAL_POS_MINERVA_3820")
    public void UNAVAILABLE_ADD_SW_Curr_To_Curr() throws InterruptedException, IOException {
        extentTest.assignCategory("Regression", "Unavailable");
        location = setPersonLocationForTest("Available", "SW", 3);
        remarks = "UNAVAILABLE_ADD_SW" + Utilities.getUUID();
        String[] medicalFieldValues = null;
        String[] tempFieldValues = null;
        String [] expUnavailDetails = {unavailableCode, action, status, expectedEffectiveDate, expectedEffectiveDate, remarks};
        expectedUnavailDetails = expUnavailDetails;
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        swCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available", "SW", null);
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);
        int availableSWCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Available", "SW", null);
        int unavailableChartCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Unavailable", unavailableCode, null);
        UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks, medicalFieldValues, tempFieldValues, "");
        Thread.sleep(2000);
        actualUnavailDetails = getDetailHistoryRowData(swCardName, "Unavail", remarks);
        //validateDetailRowHistory(swCardName, "Unavail", remarks, expectedUnavailDetails);
        personDetailPanelPage().closePersonCardDetailPanel();
        int availableSWCountAfter = CommonActions.getAnyCategoryCardsCount("Personnel", "Available", "SW", null);
        int unavailableChartCountAfter = CommonActions.getAnyCategoryCardsCount("Personnel", "Unavailable", unavailableCode, null);
        try {
            logTestInfo(wDriver, "Verifying card in Unavailable Chart");
            Assert.assertTrue(verifyCardPresenceInCategory("Unavailable", "CHART", null, swCardName), "Card in Unavailable and Chart");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card NOT in Available");
            // Validate Counts
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availableSWCountAfter, availableSWCountBefore - 1, "Available Counts");
            Assert.assertEquals(unavailableChartCountAfter, unavailableChartCountBefore + 1, "Available Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            Assert.assertEquals(actualUnavailDetails, expectedUnavailDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 2, description = "UNAVAILABLE_AND_SPECIAL_POS_MINERVA_3820")
    public void SP_POS_ADD_SW_Curr_To_Curr() throws InterruptedException, IOException {
        extentTest.assignCategory("Regression", "Unavailable");
        remarks = "SP_POS_ADD_SW" + Utilities.getUUID();
        String[] expectedSpPosDetails = {specialPosCode, "ADD", "Active", "Hardship", exStartDate, remarks};
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        Reporter.log("Person Card Name is: " + swCardName, true);
        int availableSWCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Available", "SW", null);
        int unavailableChartCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Unavailable", unavailableCode, null);
        String startDate = effectiveDate;
        SpecialPosActions.specialPositionAdd(swCardName, specialPos, startDate, endDate, remarks);
        Thread.sleep(2000);
        String[] actualSpPosDetails = getDetailHistoryRowData(swCardName, "Special", remarks);
        validateDetailRowHistory(swCardName, "Special", remarks, expectedSpPosDetails);
        personDetailPanelPage().closePersonCardDetailPanel();
        int availableSWCountAfter = CommonActions.getAnyCategoryCardsCount("Personnel", "Available", "SW", null);
        int unavailableChartCountAfter = CommonActions.getAnyCategoryCardsCount("Personnel", "Unavailable", unavailableCode, null);
        String spIndicatorAfter = getIndicatorCode(swCardName, "SpecialPosition");
        try {
            logTestInfo(wDriver, "Verifying Indicator Value is: " + expectedSpPosDetails[0]);
            Assert.assertEquals(spIndicatorAfter, expectedSpPosDetails[0], "Special Position Indicator");
            logTestInfo(wDriver, "Verifying card Still in Unavailable Chart");
            Assert.assertTrue(verifyCardPresenceInCategory("Unavailable", "CHART", null, swCardName), "Card in Unavailable and Chart");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card NOT in Available");
            // Validate Counts
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availableSWCountAfter, availableSWCountBefore, "Available Counts");
            Assert.assertEquals(unavailableChartCountAfter, unavailableChartCountBefore, "Available Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            Assert.assertEquals(actualSpPosDetails, expectedSpPosDetails);
            Assert.assertEquals(actualUnavailDetails, expectedUnavailDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

    }
}
