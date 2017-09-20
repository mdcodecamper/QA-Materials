package personTestSuites.personUnavailable.EDIT;

import _driverScript.AbstractStartWebDriver;
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
public class TEST001_UNAVAILABLE_EDIT_SW_Curr_To_Plus2 extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "SI02";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String unavailbleCode = "CHART";
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String effectiveDate2 = Utilities.getDesiredDateInFormat(2, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String effectiveHour = null;
    public static String effectiveMinute = null;
    public static String endDate = null;
    public static String expectedEndDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String endHour = null;
    public static String endMinute = null;
    public static String remarks;
    public static String action = "EDIT";
    public static String status = "Future";
    //String cardColorAfter = null;
    //String cardColorBefore = null;
    int rowNumber = 0;


    @Test(priority = 1, description = "UNAVAILABLE_EDIT_SW_Curr_To_Plus2")
    public void UNAVAILABLE_EDIT_SW_Curr_To_Plus2() throws InterruptedException, IOException {
        location = setPersonLocationForTest("Available", "SW", 3);
        remarks = "UNAVAILABLE_EDIT_SW" + Utilities.getUUID();
        String[] medicalFieldValues = null;
        String[] tempFieldValues = null;
        String[] expectedDetailsSW = {unavailbleCode, action, status, expectedEffectiveDate,expectedEndDate, remarks};
        loginPage().goToBoardLocationByDate(url, location+"/", date);
        //line below will get a person from available san worker catgory
        String swCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deleteAllPersonHistory(swCardName);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        //Thread.sleep(2000);
        //swCardName = (TEST001_UNAVAILABLE_ADD_SW_Curr_To_Curr.swCardName);
        //swCardName = !(swCardName == null) ? swCardName : getPersonName("Unavailable", "SW", unavailbleCode);
        Reporter.log("Person Card Name is: " + swCardName, true);
        UnavailableActions.addUnavailable(swCardName, unavailbleCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, "UNAVAILABLE_ADD_SW" + Utilities.getUUID(), medicalFieldValues,tempFieldValues, "");
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW");
        int unavailableChartCountBefore = getAnyCategoryHeaderCount("Unavailable", unavailbleCode);
        UnavailableActions.editUnavailable(swCardName, rowNumber,null,effectiveDate2, null, null, null, null, null, remarks);
        Thread.sleep(2000);
        //personDetailPanelPage().closePersonCardDetailPanel();
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW");
        int unavailableChartCountAfter = getAnyCategoryHeaderCount("Unavailable", unavailbleCode);
        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations After Edit Unavailability on current board date.....");
        //logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
        //Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
        logTestInfo(wDriver, "Verifying card is not in Unavailable Chart");
        softAssert.assertFalse(checkIfCardNotPresentInCategory(swCardName, "Unavailable","CHART"),"Card is not in Unavailable 'CHART'");
        logTestInfo(wDriver, "Verifying card is in Available SW");
        softAssert.assertTrue(checkIfCardPresentInCategory(swCardName, "Available",null,"SW"),"Card is in Available 'SW'");
        logTestInfo(wDriver, "Asserting Before and After Counts.............");
        softAssert.assertEquals(availableSWCountAfter, availableSWCountBefore + 1, "Available SW Counts" );
        verifyCategoryCountAfter(+1, availableSWCountBefore, "Available", "SW");
        softAssert.assertEquals(unavailableChartCountAfter, unavailableChartCountBefore -1, "Unavailable Chart Counts" );
        verifyCategoryCountAfter(-1, unavailableChartCountBefore, "Unavailable", unavailbleCode);
        //Validate Detail History
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(swCardName, "Unavail", remarks, expectedDetailsSW);

        logTestInfo(wDriver, "Now Performing Test Validations After Edit Unavailability on current board date + 2.....");
        String[] expectedDetailsSW01 = {unavailbleCode, action, "Active", expectedEffectiveDate,expectedEndDate, remarks};
        //line below will navigate to current day + 2 board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(2, "yyyyMMdd"));
        //logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
        //Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
        logTestInfo(wDriver, "Verifying card is in Unavailable Chart");
        softAssert.assertTrue(checkIfCardPresentInCategory(swCardName,"Unavailable","CHART"),"Card is in Unavailable 'CHART");
        logTestInfo(wDriver, "Verifying card is not in Available SW");
        softAssert.assertFalse(checkIfCardNotPresentInCategory(swCardName, "Available","SW"),"Card is not in Available 'SW");
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(swCardName, "Unavail", remarks, expectedDetailsSW01);

        logTestInfo(wDriver, "Now Performing Test Validations After Edit Unavailability on current board date + 3.....");
        String[] expectedDetailsSW2 = {unavailbleCode, action, "Completed", expectedEffectiveDate,expectedEndDate, remarks};
        //line below will navigate to current day + 3 board
        loginPage().goToBoardLocationByDate(url, location+"/", Utilities.getDesiredDateInFormat(3, "yyyyMMdd"));
        //logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
        //Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
        logTestInfo(wDriver, "Verifying card is not in Unavailable");
        softAssert.assertFalse(checkIfCardNotPresentInCategory(swCardName, "Unavailable","CHART"),"Card is not in Unavailable 'CHART");
        logTestInfo(wDriver, "Verifying card is in Available SW");
        softAssert.assertTrue(checkIfCardPresentInCategory(swCardName, "Available",null,"SW"),"Card is in Available 'SW");
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(swCardName, "Unavail", remarks, expectedDetailsSW2);

        softAssert.assertAll();

    }
}
