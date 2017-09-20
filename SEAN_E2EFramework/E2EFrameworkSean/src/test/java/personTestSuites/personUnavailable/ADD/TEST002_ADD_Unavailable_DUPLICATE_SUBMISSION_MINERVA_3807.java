package personTestSuites.personUnavailable.ADD;

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
import static common.actions.PersonActions.getErrorMessage;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;

/**
 * Created by sdas on 11/22/2016.
 */
public class TEST002_ADD_Unavailable_DUPLICATE_SUBMISSION_MINERVA_3807 extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKN05";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String unavailableCode = "XWOP";
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String effectiveHour = "11";
    public static String effectiveMinute = "33";
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String expectedEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = "22";
    public static String endMinute = "01";
    public static String remarks;
    public static String action = "ADD";
    public static String status = "Active";


    @Test(description = "UNAVAILABLE_ADD_DUPLICATE_SUBMISSION_MINERVA_3807")
    public void UNAVAILABLE_ADD_SUP_Curr_To_Plus3() throws InterruptedException, IOException {
        remarks = "UNAVAILABLE_ADD_SUP" + Utilities.getUUID();
        String[] medicalFieldValues = null;
        String[] tempFieldValues = null;
        location = setPersonLocationForTest("Available","SW", 5);
        String errMsg = "Unavailable Code for this calendar date range already exists.";
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        swCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available", "SW", null);
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);
        personDetailPanelPage().closePersonCardDetailPanel();
        UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks,medicalFieldValues, tempFieldValues, "");
        Thread.sleep(1000);
        UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks, medicalFieldValues, tempFieldValues, "");
        Thread.sleep(1000);
        String actualMsg = getErrorMessage();
        try {
            logTestInfo(wDriver, "Validating Error Msg: " + actualMsg);
            Assert.assertEquals(actualMsg,errMsg, "Error Message");
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }


    }

}
