package personTestSuites.personUnavailable.ADD;

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
import static person.expectedResults.panels.PersonPanelUtilities.checkIfCardPresentInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;

/**
 * Created by sdas on 11/22/2016.
 */
public class TEST003_ADD_Unavailable_SUP_Curr_To_Plus3 extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BX01";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String date2 = Utilities.getDesiredDateInFormat(4, "yyyyMMdd");
    public static String supCardName;
    public static String unavailableCode = "XWP";
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String effectiveHour = "11";
    public static String effectiveMinute = "33";
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    ;
    public static String expectedEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = "22";
    public static String endMinute = "01";
    public static String remarks;
    public static String action = "ADD";
    public static String status = "Active";

    @Test(description = "UNAVAILABLE_ADD_SUP_Curr_To_Plus3")
    public void UNAVAILABLE_ADD_SUP_Curr_To_Plus3() throws InterruptedException, IOException {
        extentTest.assignCategory("Smoke", "Regression", "Unavailable");
        location = setPersonLocationForTest("Available", "SUP", 3);
        remarks = "UNAVAILABLE_ADD_SUP" + Utilities.getUUID();
        String[] medicalFieldValues = null;
        String[] tempFieldValues = null;
        String[] expectedDetailsSUP = {unavailableCode, action, status, expectedEffectiveDate, effectiveHour + ":" + effectiveMinute, expectedEndDate, endHour + ":" + endMinute, remarks};
        loginPage().goToBoardLocationByDate(url, location+"/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        //supCardName = getPersonName("Available", "SUP");
        supCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SUP",null);

        String cardColorBefore = getRGBAColorName(getRawElementColor(supCardName, "Personnel"));;

        Reporter.log("Person Card Name is: " + supCardName, true);
        deleteAllPersonHistory(supCardName);
        personDetailPanelPage().closePersonCardDetailPanel();
        int availableSUPCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel","Available","SUP",null);
        int unavailableXWPCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel","Unavailable",unavailableCode,null);
        UnavailableActions.addUnavailable(supCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks, medicalFieldValues, tempFieldValues, "");
        Thread.sleep(2000);
        personDetailPanelPage().closePersonCardDetailPanel();

        String cardColorAfter = getRGBAColorName(getRawElementColor(supCardName, "Personnel"));;
        logTestInfo(wDriver, "Now Performing Test Validations for current board date.....");
        logTestInfo(wDriver, "Asserting Before and After Counts.............");
        CommonActions.getAnyCategoryCardsCountAfter("+1",unavailableXWPCountBefore,"Personnel","Unavailable",unavailableCode,null);
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableSUPCountBefore,"Personnel","Available","SUP",null);
        logTestInfo(wDriver, "Verifying card in Unavailable and NOT in Available");
        //check if person is present in Unavailable XWP category after unavailable
        PersonPanelUtilities.checkIfCardPresentInCategory(supCardName, "Unavailable",unavailableCode);
        //check if person is not present in Available SUP category after unavailable
        PersonPanelUtilities.checkIfCardNotPresentInCategory(supCardName, "Available","SUP");
        logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
        Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
        //Validate Detail History
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(supCardName, "Unavail", remarks, expectedDetailsSUP);

        logTestInfo(wDriver, "Now Performing Test Validations for current day + 4 board date.....");
        loginPage().goToBoardLocationByDate(url, location+"/", date2);
        String[] expectedDetailsSUP2 = {unavailableCode, action, "Completed", expectedEffectiveDate, effectiveHour + ":" + effectiveMinute, expectedEndDate, endHour + ":" + endMinute, remarks};
        logTestInfo(wDriver, "Verifying card in Available SUP");
        checkIfCardPresentInCategory(supCardName, "Available",null,"SUP");
        //Validate Detail History
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(supCardName, "Unavail", remarks, expectedDetailsSUP2);

    }

}
