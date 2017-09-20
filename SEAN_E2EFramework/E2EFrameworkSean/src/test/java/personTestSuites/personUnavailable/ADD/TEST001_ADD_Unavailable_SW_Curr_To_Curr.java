package personTestSuites.personUnavailable.ADD;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import person.expectedResults.panels.PersonPanelUtilities;
import person.personUnavailable.actions.UnavailableActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.deleteAllPersonHistory;
import static common.actions.CommonActions.logTestInfo;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


/**
 * Created by sdas on 11/22/2016.
 */
public class TEST001_ADD_Unavailable_SW_Curr_To_Curr extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKS10";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String date2 = Utilities.getDesiredDateInFormat(1, "yyyyMMdd");
    public static String swCardName;
    public static String unavailableCode = "CHART";
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String effectiveHour = null;
    public static String effectiveMinute = null;
    public static String endDate = null;
    public static String expectedEndDate = null;
    public static String endHour = null;
    public static String endMinute = null;
    public static String remarks;
    public static String action = "ADD";
    public static String status = "Active";
    public static String exStartDate = getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = getDesiredDateInFormat(0, "M/d/yyyy");
    //String cardColorAfter = null;
    //String cardColorBefore = null;


    @Test(priority = 1, description = "UNAVAILABLE_ADD_SW_Curr_To_Curr")
    public void UNAVAILABLE_ADD_SW_Curr_To_Curr() throws InterruptedException, IOException {
        extentTest.assignCategory("Smoke", "Regression", "Unavailable");
        location = setPersonLocationForTest("Available", "SW", 3);
        remarks = "UNAVAILABLE_ADD_SW" + Utilities.getUUID();
        String[] medicalFieldValues = null;
        String[] tempFieldValues = null;
        String[] expectedDetailsSW = {unavailableCode, action, status, expectedEffectiveDate, expectedEffectiveDate, remarks};
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        swCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);
        int availableSWCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel","Available","SW",null);
        int unavailableChartCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel","Unavailable",unavailableCode,null);
        UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks, medicalFieldValues, tempFieldValues, "");
        Thread.sleep(2000);
        validateDetailRowHistory(swCardName, "Unavail", remarks, expectedDetailsSW);
        personDetailPanelPage().closePersonCardDetailPanel();
        logTestInfo( wDriver, "verification started after Add Unavailable on current board date" );
        //line below verifies person sw available count after
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableSWCountBefore,"Personnel","Available","SW",null);
        //line below verifies person unavailable CHART count after
        CommonActions.getAnyCategoryCardsCountAfter("+1",unavailableChartCountBefore,"Personnel","Unavailable",unavailableCode,null);
        //Validate count before and after
        //check if person is present in Unavailable Chart category after unavailable
        PersonPanelUtilities.checkIfCardPresentInCategory(swCardName, "Unavailable","CHART");
        //check if person is not present in Available SW category after unavailable
        PersonPanelUtilities.checkIfCardNotPresentInCategory(swCardName, "Available","SW");

        //line below will open current day + 1 board
        loginPage().goToBoardLocationByDate( url, location + "/", date2 );
        logTestInfo( wDriver, "verification started after Add Unavailable on next day board" );
        String[] expectedDetailsSWnextDate = {unavailableCode, action, "Completed", expectedEffectiveDate, expectedEffectiveDate, remarks};
        validateDetailRowHistory(swCardName, "Unavail", remarks, expectedDetailsSWnextDate);
        //check if person is present in Available SW category after unavailable action on next day board
        PersonPanelUtilities.checkIfCardPresentInCategory(swCardName, "Available",null,"SW");
        //check if person is not present in Unavailable CHART category after unavailable on next day board
        PersonPanelUtilities.checkIfCardNotPresentInCategory(swCardName, "Unavailable","CHART");


    }


}
