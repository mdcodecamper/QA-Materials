package personTestSuites.personUnavailable.EDIT;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
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
public class TEST002_UNAVAILABLE_EDIT_SUP_CurrPlus1_To_Plus2 extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKN02";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String supCardName;
    public static String unavailbleCode = "XWP";
    public static String effectiveDate = Utilities.getDesiredDateInFormat(1, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(1, "M/d/yyyy");
    public static String effectiveHour = "12";
    public static String effectiveMinute = "59";
    public static String endDate = Utilities.getDesiredDateInFormat(2, "MM/dd/yyyy");    ;
    public static String expectedEndDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String endHour = "02";
    public static String endMinute = "22";
    public static String remarks;
    public static String action = "EDIT";
    public static String status = "Future";
    //String cardColorAfter = null;
    //String cardColorBefore = null;
    int rowNumber = 0;


    @Test(priority = 1, description = "UNAVAILABLE_EDIT_SUP_CurrPlus1_To_Plus2")
    public void UNAVAILABLE_EDIT_SUP_CurrPlus1_To_Plus2() throws InterruptedException, IOException {
        extentTest.assignCategory("Smoke", "Regression", "Unavailable");
        location = setPersonLocationForTest("Available", "SUP", 3);
        remarks = "UNAVAILABLE_EDIT_SUP" + Utilities.getUUID();
        String[] medicalFieldValues = null;
        String[] tempFieldValues = null;
        String[] expectedDetailsSUP = {unavailbleCode, action, status, expectedEffectiveDate, effectiveHour + ":" + effectiveMinute, expectedEndDate, endHour + ":" + endMinute, remarks};
        loginPage().goToBoardLocationByDate(url, location+"/", date);
        supCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SUP",null);
        deletePersonHistoryByTable(supCardName,"Unavail");
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        Reporter.log("Person Card Name is: " + supCardName, true);
        UnavailableActions.addUnavailable(supCardName, unavailbleCode, Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy"), effectiveHour, effectiveMinute, Utilities.getDesiredDateInFormat(1, "MM/dd/yyyy"), endHour, endMinute, "UNAVAILABLE_ADD_SUP" + Utilities.getUUID(), medicalFieldValues, tempFieldValues, "");
        int availableSUPCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel","Available", "SUP",null);
        int unavailableXWPCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel","Unavailable", unavailbleCode,null);
        UnavailableActions.editUnavailable(supCardName, rowNumber, unavailbleCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks);
        //personDetailPanelPage().closePersonCardDetailPanel();
        int availableSUPCountAfter = CommonActions.getAnyCategoryCardsCount("Personnel","Available", "SUP",null);
        int unavailableXWPCountAfter = CommonActions.getAnyCategoryCardsCount("Personnel","Unavailable", unavailbleCode,null);
        logTestInfo(wDriver, "Now Performing Test Validations After Edit Unavailability SUP on current board date + 1.....");
        logTestInfo(wDriver, "Verifying card is not in XWP");
        softAssert.assertFalse(checkIfCardNotPresentInCategory(supCardName, "Unavailable","XWP"),"Card is not in Unavailable 'XWP'");
        logTestInfo(wDriver, "Verifying card is in Available SUP");
        softAssert.assertTrue(checkIfCardPresentInCategory(supCardName, "Available",null,"SUP"),"Card is in Available 'SUP'");
        logTestInfo(wDriver, "Asserting Before and After Counts.............");
        softAssert.assertEquals(availableSUPCountAfter, availableSUPCountBefore + 1, "Available SUP Counts" );
        CommonActions.getAnyCategoryCardsCountAfter("+1", availableSUPCountBefore, "Personnel", "Available", "SUP", null);
        softAssert.assertEquals(unavailableXWPCountAfter, unavailableXWPCountBefore, "Unavailable Chart Counts" );
        CommonActions.getAnyCategoryCardsCountAfter("0", unavailableXWPCountBefore, "Personnel", "unavailable", unavailbleCode, null);
        //Validate Detail History
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(supCardName, "Unavail", remarks, expectedDetailsSUP);

        logTestInfo(wDriver, "Now Performing Test Validations After Edit Unavailability on current board date + 1.....");
        String[] expectedDetailsSUP01 = {unavailbleCode, action, "Active", expectedEffectiveDate,expectedEndDate, remarks};
        //line below will navigate to current day + 1 board
        loginPage().goToBoardLocationByDate(url, location, Utilities.getDesiredDateInFormat(2, "yyyyMMdd"));
        //logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
        //Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
        logTestInfo(wDriver, "Verifying card is in Unavailable XWP");
        softAssert.assertTrue(checkIfCardPresentInCategory(supCardName,"Unavailable","XWP"),"Card is in Unavailable 'XWP");
        logTestInfo(wDriver, "Verifying card is not in Available SUP");
        softAssert.assertFalse(checkIfCardNotPresentInCategory(supCardName, "Available","SUP"),"Card is not in Available 'SUP");
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(supCardName, "Unavail", remarks, expectedDetailsSUP01);

        logTestInfo(wDriver, "Now Performing Test Validations After Edit Unavailability SUP on current board date + 3.....");
        String[] expectedDetailsSUP2 = {unavailbleCode, action, "Completed", expectedEffectiveDate,expectedEndDate, remarks};
        //line below will navigate to current day + 3 board
        loginPage().goToBoardLocationByDate(url, location, Utilities.getDesiredDateInFormat(3, "yyyyMMdd"));
        //logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
        //Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
        logTestInfo(wDriver, "Verifying card is not in Unavailable XWP");
        softAssert.assertFalse(checkIfCardNotPresentInCategory(supCardName, "Unavailable","XWP"),"Card is not in Unavailable 'XWP");
        logTestInfo(wDriver, "Verifying card is in Available SUP");
        softAssert.assertTrue(checkIfCardPresentInCategory(supCardName, "Available",null,"SUP"),"Card is in Available 'SUP");
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(supCardName, "Unavail", remarks, expectedDetailsSUP2);

        softAssert.assertAll();

    }

}
