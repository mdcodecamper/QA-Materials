package personTestSuites.personUnavailable.DELETE;

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
public class TEST003_NEW_DELETE_Unavailable_SICK_Long_Term_ML_Home extends AbstractStartWebDriver {
    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKN01";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String supCardName;
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
    public static String action = "REMOVE";
    public static String status = "Deleted";
    public int rowNumber = 0;


    @Test(priority = 1, description = "NEW_DELETE_Unavailable_SICK_Long_Term_ML_Home")
    public void NEW_DELETE_Unavailable_SICK_Long_Term_ML_Home() throws InterruptedException, IOException {
        location = setPersonLocationForTest("Available", "SW", 3);
        unavailableCode = "SICK";
        String[] medicalFieldValues = {"Flu", "No", "Yes", "2", "Home"};
        String[] tempFieldValues = null;
        remarks = "SICK_ADD" + Utilities.getUUID();
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        swCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available", "SW", null);
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);
        personDetailPanelPage().closePersonCardDetailPanel();
        UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, null, null, null, remarks, medicalFieldValues, tempFieldValues, endDate);
        Thread.sleep(2000);

        int availCountBefore = getAnyCategoryHeaderCount("Available", "SW");
        int unavailCountBefore = getAnyCategoryHeaderCount("Unavailable", unavailableCode);

        remarks = "SICK_DELETE" + Utilities.getUUID();
        String[] expectedDetailsSW = {unavailableCode, action, status, expEffectiveDate, effectiveHour + ":" + effectiveMinute, remarks};
        UnavailableActions.deleteUnavailable(swCardName, 0, remarks);
        Thread.sleep(2000);
        personDetailPanelPage().closePersonCardDetailPanel();
        int availCountAfter = getAnyCategoryHeaderCount("Available", "SW");
        int unavailCountAfter = getAnyCategoryHeaderCount("Unavailable", unavailableCode);

        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations After Delete SICK Unavailability on current board date.....");
        try{
            logTestInfo(wDriver, "Verifying card IS IN Unavailable SICK");
            //Assert.assertFalse(checkIfCardPresentInCategory(swCardName, "Unavailable","SICK"),"Card IS NOT Present in SICK");
            Assert.assertFalse(verifyCardPresenceInCategory("Unavailable", "SICK", null, swCardName), "Card IS in Available");
            logTestInfo(wDriver, "Verifying card is in Available SW");
            Assert.assertTrue(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card IS in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availCountAfter, availCountBefore + 1, "Available SW Counts" );
            Assert.assertEquals(unavailCountAfter, unavailCountBefore - 1, "Unavailable SW Counts" );
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", remarks, expectedDetailsSW);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }


}

