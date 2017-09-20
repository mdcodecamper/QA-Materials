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
import static person.expectedResults.panels.UnavailableModalDetails.getDetachLocFromUnvailModal;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST012_NEW_ADD_Unavailable_SICK_Single_Day_Detached_Person_A_To_B_ML_Temporary extends AbstractStartWebDriver {

    //Test Data //
    private String url = LoginData.getLoginData(getUrl());
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String locationA = "MN05";
    private String locationB = "BKN05";
    private String swCardName;
    private String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    private String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String exEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    private String endHour = "12";
    private String endMinute = "15";
    private String shift = "1215-2015";
    private String unavailableCode;
    private String action = "ADD";
    private String status = "Active";
    private String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String expModalStartDate = Utilities.getDesiredDateInFormat(0, "yyyy-MM-dd");
    public static String effectiveHour = "11";
    public static String effectiveMinute = "33";


    @Test(priority = 1, description = "ADD_Unavailable_SICK_On_Long_Term_Detached_Person_A_To_B_ML_Temporary")
    public void ADD_Unavailable_SICK_On_Long_Term_Detached_Person_A_To_B_ML_Temporary() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        locationA = setPersonLocationForTest("Available", "SW", 3);
        String detRemarks ="Detach_Perm" + Utilities.getUUID();
        String[] expDetachDetails = {"Detach", "Active", "Temp", exStartDate, locationA, locationB, exEndDate, detRemarks, shift};
        loginPage().goToBoardLocationByDate(url, locationA + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        deleteAllPersonHistory(swCardName);
        int detachCountBefore = getAnyCategoryHeaderCount("Detach", "SW", null);
        int unavailSickBefore = getAnyCategoryHeaderCount("Unavailable", "SICK", null);
        logTestInfo(wDriver, "SW Detach Count Before : " + detachCountBefore + " Unavailable Count Before : " + unavailSickBefore);
        logTestInfo(wDriver, "Now Performing  Detach Add Action....");
        DetachActions.addDetach(swCardName, locationB, startDate, endDate, endHour, endMinute, detRemarks);
        Thread.sleep(3000);
        //personDetailPanelPage().closePersonCardDetailPanel();
        unavailableCode = "SICK";
        String sickRemarks ="Person_SICK" + Utilities.getUUID();
        String[] medicalFieldValues = {"High Fever", "Yes", "Yes", "2", "Temporary"};
        String[] expMedicalFieldValues = {"High Fever", "true", "true", "2", "", "true", expModalStartDate};
        String[] tempFieldValues = {"122 Main Street", "F21", "Brooklyn", "MI", "12345", "123-456-7890", "BBMBO", "11"};
        String[] expUnavailDetails = {unavailableCode, action, status, expectedEffectiveDate, effectiveHour + ":" + effectiveMinute, sickRemarks};

        UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, null, null, null, sickRemarks, medicalFieldValues, tempFieldValues, "01292017");
        int detachCountAfter = getAnyCategoryHeaderCount("Detach", "SW", null);
        int unavailSickAfter = getAnyCategoryHeaderCount("Unavailable", "SICK", null);
        logTestInfo(wDriver, "SW Detach Count After : " + detachCountAfter + " Unavailable Count After : " + unavailSickAfter);

        //Validate count before and after
        try {
            logTestInfo(wDriver, "++++++++++++ Validating From Sending Location A ++++++++++++");
            logTestInfo(wDriver, "Verifying Card IS in Detached");
            Assert.assertTrue(verifyCardPresenceInCategory("Detached", "SW", null, swCardName), "Card IS in Detached");
            logTestInfo(wDriver, "Verifying card NOT in Available SW");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card NOT in Available");
            logTestInfo(wDriver, "Verifying Card NOT in SICK");
            PersonPanelUtilities.checkIfCardNotPresentInCategory(swCardName, "Unavailable","SICK", "Card NOT Present in SICK");

            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(detachCountAfter, detachCountBefore + 1, "Detach Card Counts");
            Assert.assertEquals(unavailSickAfter, unavailSickBefore, "Available Card Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Detach Location NOT Displayed on Unavailable Modal....");
            Assert.assertFalse(getDetachLocFromUnvailModal(swCardName).contains(locationB), "NO Detach Location");
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", sickRemarks, expUnavailDetails);
            logTestInfo(wDriver, "Validating Detach Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Detach", detRemarks, expDetachDetails);
            //////////////////////////////////////////////////////////////////////////
            logTestInfo(wDriver, "++++++++++++ Validating From Receiving Location B ++++++++++++");
            loginPage().goToBoardLocationByDate(url, locationB + "/", date);
            logTestInfo(wDriver, "Verifying card IS IN LODI");
            Assert.assertTrue(PersonPanelUtilities.checkCardPresence(swCardName, "Unavailable", unavailableCode), "Card Present in LODI");
            logTestInfo(wDriver, "Verifying card IS NOT IN Available");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card IS NOT in Available");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Detach Location NOT Displayed on Unavailable Modal....");
            Assert.assertFalse(getDetachLocFromUnvailModal(swCardName).contains(locationB), "NO Detach Location");
            logTestInfo(wDriver, "Validating Unavailable Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", sickRemarks, expUnavailDetails);
            logTestInfo(wDriver, "Validating Detach Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Detach", detRemarks, expDetachDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }

}