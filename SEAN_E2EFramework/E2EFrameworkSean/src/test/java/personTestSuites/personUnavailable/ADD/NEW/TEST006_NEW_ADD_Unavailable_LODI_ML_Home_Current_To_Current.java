package personTestSuites.personUnavailable.ADD.NEW;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import person.expectedResults.panels.PersonPanelUtilities;
import person.personUnavailable.actions.UnavailableActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.checkCardPresence;
import static person.expectedResults.panels.UnavailableModalDetails.getMedicalDetails;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;

/**
 * Created by sdas on 11/22/2016.
 */
public class TEST006_NEW_ADD_Unavailable_LODI_ML_Home_Current_To_Current extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String locationA = "MN04";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String expModalStartDate = Utilities.getDesiredDateInFormat(0, "yyyy-MM-dd");
    public static String SWCardName;
    public static String SUPCardName;
    public static String unavailableCode;
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String effectiveHour = "11";
    public static String effectiveMinute = "33";
    public static String endDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");    ;
    public static String expectedEndDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String endHour = "22";
    public static String endMinute = "01";
    public static String remarks;
    public static String action = "ADD";
    public static String status = "Active";
    String cardColorAfter = null;
    String cardColorBefore = null;


    @Test(priority = 1, description = "ADD_Unavailable_LODI_ML_Home")
    public void ADD_Unavailable_LODI_ML_Home() throws InterruptedException, IOException {
        locationA = setPersonLocationForTest("Available", "SUP", 3);
        unavailableCode = "LODI";
        String[] medicalFieldValues = {"High Fever","No","No","2","Home"};
        String[] expMedicalFieldValues = {"High Fever","false","false","2","", "false", expModalStartDate};
        String[] tempFieldValues = null;
        remarks = "LODI_ADD_SUP" + Utilities.getUUID();
        String[] expUnavailDetails = {unavailableCode, action, status, expectedEffectiveDate, effectiveHour + ":" + effectiveMinute, remarks};
        loginPage().goToBoardLocationByDate(url, locationA +"/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        //SUPCardName = getPersonName("Available", "SUP");
        SUPCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SUP",null);
        Reporter.log("Person Card Name is: " + SUPCardName, true);
        deleteAllPersonHistory(SUPCardName);
        personDetailPanelPage().closePersonCardDetailPanel();
        int availableSUPCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel","Available","SUP",null);
        int unavailableXWPCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel","Unavailable",unavailableCode,null);
        UnavailableActions.addUnavailable(SUPCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, null, null, null, remarks, medicalFieldValues ,tempFieldValues, "01292017");
        Thread.sleep(2000);

        personDetailPanelPage().closePersonCardDetailPanel();
        logTestInfo(wDriver, "Now Performing Test Validations for current board date.....");
        logTestInfo(wDriver, "Asserting Before and After Counts.............");
        CommonActions.getAnyCategoryCardsCountAfter("+1",unavailableXWPCountBefore,"Personnel","Unavailable",unavailableCode,null);
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableSUPCountBefore,"Personnel","Available","SUP",null);
        logTestInfo(wDriver, "Verifying Card in Unavailable");
        Assert.assertTrue(checkCardPresence(SUPCardName, "Unavailable",unavailableCode), "Card Present in Unavailable Code");
        logTestInfo(wDriver, "Verifying Card NOT in Available");
        Assert.assertFalse(checkCardPresence(SUPCardName, "Available","SUP"), "Card Not Present in Available");
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(SUPCardName, "Unavail", remarks, expUnavailDetails);
        logTestInfo(wDriver, "Validating Medical Details History with Expected Details....");
        Assert.assertEquals(getMedicalDetails(SUPCardName), expMedicalFieldValues);


    }




}
