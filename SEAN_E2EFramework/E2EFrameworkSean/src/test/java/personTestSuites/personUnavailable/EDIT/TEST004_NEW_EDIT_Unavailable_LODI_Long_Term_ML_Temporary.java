package personTestSuites.personUnavailable.EDIT;

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
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.*;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


/**
 * Created by sdas on 11/22/2016.
 */
public class TEST004_NEW_EDIT_Unavailable_LODI_Long_Term_ML_Temporary extends AbstractStartWebDriver {
    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKN01";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String unavailableCode;
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String expEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String effectiveHour = "00";
    public static String effectiveMinute = "00";
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String expectedEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = "23";
    public static String endMinute = "59";
    public static String remarks;
    public static String action = "EDIT";
    public static String status = "Active";
    String cardColorAfter = null;
    String cardColorBefore = null;
    public int rowNumber = 0;

    @Test(priority = 1, description = "NEW_EDIT_Unavailable_LODI_Long_Term_ML_Temporary")
    public void NEW_EDIT_Unavailable_LODI_Long_Term_ML_Temporary() throws InterruptedException, IOException {
        location = setPersonLocationForTest("Available", "SUP", 3);
        unavailableCode = "LODI";
        String[] medicalFieldValues = {"Flu", "No", "Yes", "3", "Temporary"};
        String[] tempFieldValues = {"122 Main Street", "2ZDCB", "Brooklyn", "MI", "12345", "1234567890", "MN10", "11"};
        remarks = "LODI_ADD_SUP" + Utilities.getUUID();

        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        swCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available", "SUP", null);
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);
        personDetailPanelPage().closePersonCardDetailPanel();

        UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, null, null, null, remarks, medicalFieldValues, tempFieldValues, endDate);
        Thread.sleep(2000);

        int availCountBefore = getAnyCategoryHeaderCount("Available", "SUP");
        int unavailCountBefore = getAnyCategoryHeaderCount("Unavailable", unavailableCode);

        remarks = "LODI_EDIT_SUP" + Utilities.getUUID();
        String[] expectedDetailsSUP = {unavailableCode, action, status, expEffectiveDate, effectiveHour + ":" + effectiveMinute, expectedEndDate, endHour + ":" + endMinute, remarks};
        UnavailableActions.editUnavailable(swCardName, rowNumber, null, null, null, null, endDate, null, null, remarks);
        Thread.sleep(3000);
        personDetailPanelPage().closePersonCardDetailPanel();
        int availCountAfter = getAnyCategoryHeaderCount("Available", "SUP");
        int unavailCountAfter = getAnyCategoryHeaderCount("Unavailable", unavailableCode);


        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations After Edit LODI Unavailability on current board date.....");
        try {
            logTestInfo(wDriver, "Verifying card IS IN Unavailable LODI");
            Assert.assertTrue(checkCardPresence(swCardName, "Unavailable", "LODI"), "Card IS Present in LODI");
            logTestInfo(wDriver, "Verifying card is in Available SUP");
            Assert.assertFalse(checkCardPresence(swCardName, "AvailableUn", "SUP"), "Card NOT Present in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availCountBefore, availCountAfter, "Available SUP Counts");
            Assert.assertEquals(unavailCountBefore, unavailCountAfter, "Unavailable SUP Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", remarks, expectedDetailsSUP);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }
}