package personTestSuites.personUnavailable.ADD.NEW;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonPanelUtilities;
import person.personDetach.actions.DetachActions;
import person.personUnavailable.actions.UnavailableActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.data.RawColorCodes.getRGBAColorName;
import static person.expectedResults.panels.PersonCardUtilities.getAPersonWithoutNextDayAssigned;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static person.expectedResults.panels.UnavailableModalDetails.*;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST016_NEW_ADD_Unavailable_SICK_On_Long_Term_Detach_Ended_Yesterday_MINERVA_4736 extends AbstractStartWebDriver {

    //Test Data //
    private String url = LoginData.getLoginData(getUrl());
    private String date1 = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String date2 = Utilities.getDesiredDateInFormat(15, "yyyyMMdd");
    private String locationA = "MN05";
    private String locationB = "BKN05";
    private String swCardName;
    private String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String  expModalStartDate = Utilities.getDesiredDateInFormat(0, "yyyy-MM-dd");
    private String endDate = Utilities.getDesiredDateInFormat(14, "MM/dd/yyyy");
    private String exEndDate = Utilities.getDesiredDateInFormat(14, "M/d/yyyy");
    private String effectiveDate = Utilities.getDesiredDateInFormat(15, "MM/dd/yyyy");
    private String exEffectiveDate = Utilities.getDesiredDateInFormat(15, "M/d/yyyy");
    private String endHour = "12";
    private String endMinute = "15";
    private String shift = "1215-2015";
    private String unavailableCode;
    private String action = "ADD";
    private String status = "Active";
    public static String effectiveHour = "11";
    public static String effectiveMinute = "33";
    public static String expInfoMsg = "You cannot change the Effective Date for this record. Please delete this and re-enter a new record with the desired Effective Date.";

    @Test(priority = 1, description = "ADD_Unavailable_SICK_On_Long_Term_Detach_Ended_Yesterday_MINERVA_4736")
    public void ADD_Unavailable_SICK_On_Long_Term_Detach_Ended_Yesterday_MINERVA_4736() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        locationA = setPersonLocationForTest("Available", "SW", 3);
        String detRemarks ="Detach_2Wks" + Utilities.getUUID();
        String[] expDetachDetails = {"Detach", "Active", "Temp", exStartDate, locationA, locationB, exEndDate, detRemarks, shift};
        loginPage().goToBoardLocationByDate(url, locationA + "/", date1);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        deleteAllPersonHistory(swCardName);
        logTestInfo(wDriver, "Now Performing  Detach Add Action....");
        DetachActions.addDetach(swCardName, locationB, startDate, endDate, endHour, endMinute, detRemarks);
        Thread.sleep(3000);
        //personDetailPanelPage().closePersonCardDetailPanel();
        loginPage().goToBoardLocationByDate(url, locationA + "/", date2);
        unavailableCode = "SICK";
        String sickRemarks ="Person_SICK" + Utilities.getUUID();
        String[] medicalFieldValues = {"High Fever", "Yes", "Yes", "2", "Temporary"};
        String[] expMedicalFieldValues = {"High Fever", "true", "true", "2", "", "true", expModalStartDate};
        String[] tempFieldValues = {"122 Main Street", "$D2_", "Brooklyn", "IA", "12345", "123-456-7890", "BBMBO", "11"};
        String[] expTempFieldValues = {"122 Main Street", "$D2_", "Brooklyn", "IA", "12345", "123-456-7890", "BBMBO", "11"};
        String[] expUnavailDetails = {unavailableCode, action, status, exEffectiveDate, effectiveHour + ":" + effectiveMinute, sickRemarks};
        int unavailSickBefore = getAnyCategoryHeaderCount("Unavailable", "SICK", null);
        UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, null, null, null, sickRemarks, medicalFieldValues, tempFieldValues, "01292017");
        Thread.sleep(2000);
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        int unavailSickAfter = getAnyCategoryHeaderCount("Unavailable", "SICK", null);

        //Validate count before and after
        try {
            logTestInfo(wDriver, "++++++++++++ Validating From Location A ++++++++++++");
            logTestInfo(wDriver, "Validating Card Color Before and After....");
            Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying Card IS NOT in Detached");
            Assert.assertFalse(verifyCardPresenceInCategory("Detached", "SW", null, swCardName), "Card IS in Detached");
            logTestInfo(wDriver, "Verifying card IS in Available SW");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card NOT in Available");
            logTestInfo(wDriver, "Verifying Card IN SICK");
            Assert.assertTrue(verifyCardPresenceInCategory("Unavailable", "Sick", null, swCardName), "Card IS IN Sick");
            logTestInfo(wDriver, "Asserting Before Sick: "+ unavailSickBefore +" and After Counts: " + unavailSickAfter);
            Assert.assertEquals(unavailSickAfter, unavailSickBefore + 1, "Sick Card Counts");
            logTestInfo(wDriver, "Validating Detach Location NOT Displayed on Unavailable Modal....");
            Assert.assertFalse(getDetachLocFromUnvailModal(swCardName).contains(locationB), "NO Detach Location");
            //Validate Info Msg
            logTestInfo(wDriver, "Validating Information Icon Message....");
            Assert.assertEquals(getInformationIconMsg(swCardName,0), expInfoMsg, "Information Icon Message");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", sickRemarks, expUnavailDetails);
            logTestInfo(wDriver, "Validating Medical Details History with Expected Details....");
            Assert.assertEquals(getMedicalDetails(swCardName), expMedicalFieldValues);
            logTestInfo(wDriver, "Validating Temporary Address Details with Expected Details....");
            Assert.assertEquals(tempAddressDetails(swCardName), expTempFieldValues);
        } catch (Error e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

    }

}