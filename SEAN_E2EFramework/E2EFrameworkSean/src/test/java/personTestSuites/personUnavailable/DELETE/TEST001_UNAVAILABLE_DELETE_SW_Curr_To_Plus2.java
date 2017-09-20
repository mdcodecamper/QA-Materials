package personTestSuites.personUnavailable.DELETE;

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

/*
 * Created by sdas on 11/22/2016.
 */

public class TEST001_UNAVAILABLE_DELETE_SW_Curr_To_Plus2 extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKN03";
    private String date = Utilities.getDesiredDateInFormat(2, "yyyyMMdd");
    public static String swCardName;
    public static String unavailbleCode = "CHART";
    public static String effectiveDate = Utilities.getDesiredDateInFormat(2, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String effectiveHour = null;
    public static String effectiveMinute = null;
    public static String endDate = null;
    public static String expectedEndDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String endHour = null;
    public static String endMinute = null;
    public static String remarks;
    public static String action = "REMOVE";
    public static String status = "Deleted";
    String cardColorAfter = null;
    String cardColorBefore = null;
    int rowNumber = 0;



    @Test(priority = 1, description = "UNAVAILABLE_DELETE_SW_Curr_To_Plus2")
    public void UNAVAILABLE_DELETE_SW_CurrPlus1_To_Plus2() throws InterruptedException, IOException {
        extentTest.assignCategory("Smoke", "Regression", "Unavailable");
        location = setPersonLocationForTest("Available", "SW", 3);
        remarks = "UNAVAILABLE_DELETE_SW" + Utilities.getUUID();
        String[] medicalFieldValues = null;
        String[] tempFieldValues = null;
        String[] expectedDetailsSW = {unavailbleCode, action, status, expectedEffectiveDate,expectedEndDate, remarks};
        loginPage().goToBoardLocationByDate(url, location+"/", date);
        //line below will get a person from available san worker catgory
        String swCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deleteAllPersonHistory(swCardName);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        Reporter.log("Person Card Name is: " + swCardName, true);
        UnavailableActions.addUnavailable(swCardName, unavailbleCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, "UNAVAILABLE_ADD_SW" + Utilities.getUUID(), medicalFieldValues, tempFieldValues, "");
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW");
        int unavailableChartCountBefore = getAnyCategoryHeaderCount("Unavailable", unavailbleCode);
        Reporter.log("unavailableChartCountBefore: " + unavailableChartCountBefore, true);
        Thread.sleep(1000);
        //deletePersonHistoryByTable(swCardName, "Unavail");
        UnavailableActions.deleteUnavailable(swCardName, rowNumber, remarks);
        Thread.sleep(2000);
        //personDetailPanelPage().closePersonCardDetailPanel();
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW");
        int unavailableChartCountAfter = getAnyCategoryHeaderCount("Unavailable", unavailbleCode);
        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations.....");
        //logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
        //Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
        logTestInfo(wDriver, "Verifying card is not in Unavailable Chart");
        checkIfCardNotPresentInCategory(swCardName, "Unavailable","CHART");
        logTestInfo(wDriver, "Verifying card is in Available SW");
        checkIfCardPresentInCategory(swCardName, "Available",null,"SW");
        logTestInfo(wDriver, "Asserting Before and After Counts.............");
        verifyCategoryCountAfter(+1, availableSWCountBefore, "Available", "SW");
        verifyCategoryCountAfter(-1, unavailableChartCountBefore, "Unavailable", unavailbleCode);
        //Validate Detail History
        logTestInfo(wDriver, "Validating Details History with Expected Details....");
        validateDetailRowHistory(swCardName, "Unavail", remarks, expectedDetailsSW);

    }

}

