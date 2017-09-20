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
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


/**
 * Created by sdas on 11/22/2016.
 */
public class TEST004_NEW_DELETE_Unavailable_LODI_Long_Term_ML_Temporary extends AbstractStartWebDriver {
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



    @Test(priority = 1, description = "NEW_DELETE_Unavailable_LODI_Long_Term_ML_Temporary")
    public void NEW_DELETE_Unavailable_LODI_Long_Term_ML_Temporary() throws InterruptedException, IOException {
        location = setPersonLocationForTest("Available", "SUP", 3);
        unavailableCode = "LODI";
        String[] medicalFieldValues = {"High Fever","Yes","Yes","2","Temporary"};
        String[] tempFieldValues = {"33-45 Main Street", "ABC1234", "Brooklyn", "MI", "12345", "123-456-7890", "BBMBO", "11"};
        remarks = "LODI_ADD_SUP" + Utilities.getUUID();
        loginPage().goToBoardLocationByDate(url, location+"/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        //supCardName = getPersonName("Available", "SUP");
        supCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SUP",null);
        Reporter.log("Person Card Name is: " + supCardName, true);
        //deleteAllPersonHistory(supCardName);
        //personDetailPanelPage().closePersonCardDetailPanel();

        UnavailableActions.addUnavailable(supCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, null, null, null, remarks, medicalFieldValues ,tempFieldValues, "01292017");
        Thread.sleep(2000);
        int availCountBefore = getAnyCategoryHeaderCount("Available", "SUP");
        int unavailCountBefore = getAnyCategoryHeaderCount("Unavailable", unavailableCode);
        remarks = "LODI_DELETE_SUP" + Utilities.getUUID();
        String[] expectedDetails = {unavailableCode, action, status, expEffectiveDate, effectiveHour + ":" + effectiveMinute, remarks};
        UnavailableActions.deleteUnavailable(supCardName, rowNumber, remarks);
        Thread.sleep(2000);
        //personDetailPanelPage().closePersonCardDetailPanel();
        int availCountAfter = getAnyCategoryHeaderCount("Available", "SUP");
        int unavailCountAfter = getAnyCategoryHeaderCount("Unavailable", unavailableCode);
        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations After Delete LODI Unavailability on current board date.....");
        try{
            logTestInfo(wDriver, "Verifying card IS IN Unavailable LODI");
            Assert.assertFalse(verifyCardPresenceInCategory("Unavailable", "LODI", null, supCardName), "Card IS NOT Present in LODI");
            logTestInfo(wDriver, "Verifying card is in Available SUP");
            Assert.assertTrue(verifyCardPresenceInCategory("AvailableUn", "SUP", null, supCardName), "Card IS in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availCountAfter, availCountBefore + 1, "Available SUP Counts" );
            Assert.assertEquals(unavailCountAfter, unavailCountBefore - 1, "Unavailable SUP Counts" );
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(supCardName, "Unavail", remarks, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

    }
}

