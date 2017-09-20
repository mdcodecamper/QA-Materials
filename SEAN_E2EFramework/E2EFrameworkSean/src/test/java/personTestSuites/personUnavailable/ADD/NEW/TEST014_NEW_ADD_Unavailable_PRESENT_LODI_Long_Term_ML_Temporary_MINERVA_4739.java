package personTestSuites.personUnavailable.ADD.NEW;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import person.personUnavailable.actions.UnavailableActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.data.RawColorCodes.getRGBAColorName;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.checkCardPresence;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static person.expectedResults.panels.UnavailableModalDetails.getMedicalDetails;
import static person.expectedResults.panels.UnavailableModalDetails.tempAddressDetails;


/**
 * Created by sdas on 11/22/2016.
 */
public class TEST014_NEW_ADD_Unavailable_PRESENT_LODI_Long_Term_ML_Temporary_MINERVA_4739 extends AbstractStartWebDriver {
    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKN01";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String unavailableCode;
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String expEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String expModalStartDate = Utilities.getDesiredDateInFormat(0, "yyyy-MM-dd");
    public static String effectiveHour = "00";
    public static String effectiveMinute = "00";
    public static String endDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String expectedEndDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String endHour = "23";
    public static String endMinute = "59";
    public static String remarks;
    public int rowNumber = 0;

    @Test(priority = 1, description = "MINERVA_4739 : Single Day Lodi ML Temporary Starting and Ending Today")
    public void ADD_Unavailable_PRESENT_LODI_Long_Term_ML_Temporary_MINERVA_4739() throws InterruptedException, IOException {
        //location = setPersonLocationForTest("Available", "SW", 5);
        logTestInfo(wDriver, "MINERVA_4739: Single Day Lodi ML Temporary Starting and Ending in the Present");
        location = "BKN01";
        unavailableCode = "LODI";
        String[] medicalFieldValues = {"Stomach Flu", "No", "Yes", "3", "Temporary"};
        String[] expMedicalFieldValues = {"Stomach Flu","false","true","3","", "true", expModalStartDate};
        String[] tempFieldValues = {"122 Main Street", "$D2_", "Brooklyn", "IA", "12345", "1234567890", "MN10", "11"};
        String[] expTempFieldValues = {"122 Main Street", "$D2_", "Brooklyn", "IA", "12345", "123-456-7890", "MN10", "11"};
        remarks = "ADD_Lodi_" + Utilities.getUUID();
        String[] expectedAddDetails = {unavailableCode, "ADD", "Active", expEffectiveDate, effectiveHour + ":" + effectiveMinute, remarks};
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        swCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available", "SW", null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);
        personDetailPanelPage().closePersonCardDetailPanel();
        int availCountBefore = getAnyCategoryHeaderCount("Available", "SW");
        int unavailCountBefore = getAnyCategoryHeaderCount("Unavailable", unavailableCode);
        UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, null, null, null, remarks, medicalFieldValues, tempFieldValues, endDate);
        Thread.sleep(2000);
        int availCountAfter = getAnyCategoryHeaderCount("Available", "SW");
        int unavailCountAfter = getAnyCategoryHeaderCount("Unavailable", unavailableCode);
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        logTestInfo(wDriver, "TEST VALIDATION DAY 0: After ADD LODI Unavailability From Board Date: " + boardDate);
        logTestInfo(wDriver, "=========================================================================");
        try {
            logTestInfo(wDriver, "Validating Card Color Before and After....");
            Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying card IS NOT Available  IN LODI Unavailable.....");
            Assert.assertTrue(checkCardPresence(swCardName, "Unavailable", "LODI"), "Card IS Present in LODI");
            logTestInfo(wDriver, "Verifying card is in Available SW");
            Assert.assertFalse(checkCardPresence(swCardName, "AvailableUn", "SW"), "Card NOT Present in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availCountAfter, availCountBefore - 1 , "Available SW Counts");
            Assert.assertEquals(unavailCountAfter,unavailCountBefore + 1, "Unavailable SW Counts");

            //Validate Detail History
            logTestInfo(wDriver, "Validating ADD Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", remarks, expectedAddDetails);
            logTestInfo(wDriver, "Validating Medical Details History with Expected Details....");
            Assert.assertEquals(getMedicalDetails(swCardName), expMedicalFieldValues);
            logTestInfo(wDriver, "Validating Temporary Address Details with Expected Details....");
            Assert.assertEquals(tempAddressDetails(swCardName), expTempFieldValues);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
        remarks = "Edit_Lodi_" + Utilities.getUUID();
        String[] expectedEditDetails= {unavailableCode, "EDIT", "Active", expEffectiveDate, effectiveHour + ":" + effectiveMinute, expectedEndDate, endHour + ":" + endMinute, remarks};
        UnavailableActions.editUnavailable(swCardName, rowNumber, null, null, null, null, endDate, null, null, remarks);
        Thread.sleep(3000);
        personDetailPanelPage().closePersonCardDetailPanel();
        cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        availCountAfter = getAnyCategoryCardsCount("Personnel","Available","SW",null);
        unavailCountAfter = getAnyCategoryCardsCount("Personnel","Unavailable",unavailableCode,null);
        //Validate count before and after
        logTestInfo(wDriver, "TEST VALIDATION DAY 0: After EDIT LODI with End Date Unavailability From Board Date: " + boardDate);
        logTestInfo(wDriver, "=========================================================================");
        try {
            logTestInfo(wDriver, "Validating Card Color Before and After....");
            Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying card IS IN Available NOT LODI Unavailable.....");
            Assert.assertTrue(checkCardPresence(swCardName, "Unavailable", "LODI"), "Card Present in LODI");
            logTestInfo(wDriver, "Verifying card is in Available SW");
            Assert.assertFalse(checkCardPresence(swCardName, "AvailableUn", "SW"), "Card NOT Present in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availCountAfter, availCountBefore - 1 , "Available SW Counts");
            Assert.assertEquals(unavailCountAfter, unavailCountBefore + 1, "Unavailable SW Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating ADD Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", remarks, expectedEditDetails);
            logTestInfo(wDriver, "Validating Medical Details History with Expected Details....");
            Assert.assertEquals(getMedicalDetails(swCardName), expMedicalFieldValues);
            logTestInfo(wDriver, "Validating Temporary Address Details with Expected Details....");
            Assert.assertEquals(tempAddressDetails(swCardName), expTempFieldValues);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
        

        logTestInfo(wDriver, "TEST VALIDATION DAY +1: After ADD LODI Unavailability From Board Date: " + boardDate);
        logTestInfo(wDriver, "=========================================================================");
        boardDate = Utilities.getDesiredDateInFormat(1, "yyyyMMdd");
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        expectedEditDetails[2] = "Completed";
        cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));

        try {
            logTestInfo(wDriver, "Validating Card Color Before and After....");
            Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying card IS IN Unavailable LODI");
            Assert.assertFalse(checkCardPresence(swCardName, "Unavailable", "LODI"), "Card IS Present in LODI");
            logTestInfo(wDriver, "Verifying card is in Available SW");
            //Assert.assertTrue(checkCardPresence(swCardName, "Available", "SW"), "Card Present in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availCountAfter, availCountBefore-1, "Available SW Counts");
            Assert.assertEquals(unavailCountAfter, unavailCountBefore + 1, "Unavailable SW Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", remarks, expectedEditDetails);

            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

        logTestInfo(wDriver, "TEST VALIDATION DAY +2: After ADD LODI Unavailability From Board Date: " + boardDate);
        logTestInfo(wDriver, "=========================================================================");

        boardDate = Utilities.getDesiredDateInFormat(2, "yyyyMMdd");
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        expectedEditDetails[2] = "Completed";
        try {
            logTestInfo(wDriver, "Validating Card Color Before and After....");
            Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying card IS IN Unavailable LODI");
            Assert.assertFalse(checkCardPresence(swCardName, "Unavailable", "LODI"), "Card IS Present in LODI");
            logTestInfo(wDriver, "Verifying card is in Available SW");
            //Assert.assertTrue(checkCardPresence(swCardName, "Available", "SW"), "Card Present in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availCountAfter, availCountBefore-1, "Available SW Counts");
            Assert.assertEquals(unavailCountAfter, unavailCountBefore + 1, "Unavailable SW Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", remarks, expectedEditDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }
}