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
import static common.data.RawColorCodes.getRGBAColorName;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.checkCardPresence;
import static person.expectedResults.panels.UnavailableModalDetails.getMedicalDetails;
import static person.expectedResults.panels.UnavailableModalDetails.tempAddressDetails;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;

/**
 * Created by sdas on 11/22/2016.
 */
public class TEST005_NEW_ADD_Unavailable_SICK_ML_Temporary_Current_To_Future extends AbstractStartWebDriver {

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
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");    ;
    public static String expectedEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = "22";
    public static String endMinute = "01";
    public static String remarks;
    public static String action = "ADD";
    public static String status = "Active";
    String cardColorAfter = null;
    String cardColorBefore = null;


    @Test(priority = 1, description = "ADD_Unavailable_SICK_ML_Temporary")
    public void ADD_Unavailable_SICK_ML_Temporary() throws InterruptedException, IOException {
        locationA = setPersonLocationForTest("Available", "SW", 3);
        unavailableCode = "SICK";
        String[] medicalFieldValues = {"High Fever","Yes","Yes","2","Temporary"};
        String[] expMedicalFieldValues = {"High Fever","true","true","2","", "true", expModalStartDate};
        String[] tempFieldValues = {"122 Main Street", "F21", "Brooklyn", "MI", "12345", "123-456-7890", "BBMBO", "11"};
        remarks = "SICK_ADD_SW" + Utilities.getUUID();
        String[] expUnavailDetails = {unavailableCode, action, status, expectedEffectiveDate, effectiveHour + ":" + effectiveMinute, remarks};
        loginPage().goToBoardLocationByDate(url, locationA+"/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        //SWCardName = getPersonName("Available", "SW");
        SWCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(SWCardName, "Personnel"));
        Reporter.log("Person Card Name is: " + SWCardName, true);
        deleteAllPersonHistory(SWCardName);
        personDetailPanelPage().closePersonCardDetailPanel();
        int availableSWCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel","Available","SW",null);
        int unavailableXWPCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel","Unavailable",unavailableCode,null);
        UnavailableActions.addUnavailable(SWCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, null, null, null, remarks, medicalFieldValues ,tempFieldValues, "01292017");
        Thread.sleep(2000);
        personDetailPanelPage().closePersonCardDetailPanel();
        String cardColorAfter = getRGBAColorName(getRawElementColor(SWCardName, "Personnel"));
        logTestInfo(wDriver, "Now Performing Test Validations for current board date.....");
        logTestInfo(wDriver, "Asserting Before and After Counts.............");
        CommonActions.getAnyCategoryCardsCountAfter("+1",unavailableXWPCountBefore,"Personnel","Unavailable",unavailableCode,null);
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableSWCountBefore,"Personnel","Available","SW",null);
        logTestInfo(wDriver, "Verifying Card in Unavailable and NOT in Available");
        Assert.assertTrue(checkCardPresence(SWCardName, "Unavailable",unavailableCode), "Card Present in Unavailable Code");
        Assert.assertFalse(checkCardPresence(SWCardName, "Available","SW"), "Card Not Present in Available");
        //Validate Detail History
        logTestInfo(wDriver, "Validating Card Color Before and After....");
        Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(SWCardName, "Unavail", remarks, expUnavailDetails);
        logTestInfo(wDriver, "Validating Medical Details History with Expected Details....");
        Assert.assertEquals(getMedicalDetails(SWCardName), expMedicalFieldValues);
        logTestInfo(wDriver, "Validating Temporary Address Details with Expected Details....");
        Assert.assertEquals(tempAddressDetails(SWCardName), tempFieldValues);

    }


}
