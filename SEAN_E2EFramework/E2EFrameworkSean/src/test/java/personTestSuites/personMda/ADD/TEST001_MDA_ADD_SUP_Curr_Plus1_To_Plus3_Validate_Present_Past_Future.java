package personTestSuites.personMda.ADD;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personMda.actions.MdaActions;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.data.RawColorCodes.getRGBAColorName;
import static person.expectedResults.panels.PersonCardUtilities.getAPersonWithoutNextDayAssigned;
import static person.expectedResults.panels.PersonCardUtilities.getIndicatorCode;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.*;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST001_MDA_ADD_SUP_Curr_Plus1_To_Plus3_Validate_Present_Past_Future extends AbstractStartWebDriver {
    //Test Data
    public static String url = LoginData.getLoginData(getUrl());
    public static String location;
    public static String date = getDesiredDateInFormat(0, "yyyyMMdd");
    public static String datePlus1 = getDesiredDateInFormat(1, "yyyyMMdd");
    public static String datePlus4 = getDesiredDateInFormat(4, "yyyyMMdd");
    public static String supCardName;
    public static String supMdaType = "7A - MDA 7(A) - LIMITED DUTY";
    public static String appointmentDate = getDesiredDateInFormat(1, "MM/dd/yyyy");
    public static String exAppointmentDate = getDesiredDateInFormat(1, "M/d/yyyy");
    public static String startDate = getDesiredDateInFormat(1, "MM/dd/yyyy");
    public static String endDate = getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String exStartDate = getDesiredDateInFormat(1, "M/d/yyyy");
    public static String exEndDate = getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = null;
    public static String endMinute = null;
    public static String remarks = "TEST001_Person_MDA_ADD" + getUUID();
    //***********************************
    static String cardColorBefore;
    static int availableCountBefore;
    static int availableSUPCountBefore;
    static int mdaCountBefore;
    static String cardColorAfter;
    static int availableCountAfter;
    static int availableSUPCountAfter;
    static int mdaCountAfter;
    static String mdaCode;


    @Test(priority = 1, description = "MDA_ADD_SUP_Curr_Plus1_To_Plus3_Validate_Present_Past_Future")
    public void MDA_ADD_SUP_Curr_Plus1_To_Plus3_Validate_Present_Past_Future() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SUP", 3);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Task");
        clickOnMenuIcon(wDriver, "Equipment");
        //Test Data
        String[] expectedDetails0 = {"7A", "Add", "Future", exStartDate, exAppointmentDate, exEndDate,  "23:59",remarks};
        String[] expectedDetailsPlus1 = {"7A", "Add", "Active", exStartDate, exAppointmentDate, exEndDate,"23:59", remarks};
        String[] expectedDetailsPlus4 = {"7A", "Add", "Completed", exStartDate, exAppointmentDate, exEndDate,"23:59", remarks};

        //supCardName = getFreshCardForTest("Avail", "SUP", "Mda");
        supCardName = getAPersonWithoutNextDayAssigned("Available", "SUP", null);
        cardColorBefore = getRGBAColorName(getRawElementColor(supCardName, "Personnel"));
        Reporter.log("Person Card Name is: " + supCardName, true);
        deleteAllPersonHistory(supCardName);
        availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        availableSUPCountBefore = getAnyCategoryHeaderCount("Available", "SUP", null);
        mdaCountBefore = getAnyCategoryHeaderCount("Mda", null, null);
        //mdaCountBefore = getAnyCategoryCardsCount("Personnel", "Mda", null,null);
        Thread.sleep(1000);
        MdaActions.addMda(supCardName, startDate, supMdaType, appointmentDate, endDate, endHour, endMinute, remarks);
        //isCardAnimated(supCardName);
        Thread.sleep(4000);
        personDetailPanelPage().closePersonCardDetailPanel();
        availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        availableSUPCountAfter = getAnyCategoryHeaderCount("Available", "SUP", null);
        mdaCountAfter = getAnyCategoryHeaderCount("Mda", null, null);
        mdaCode = getIndicatorCode(supCardName, "Mda");
        cardColorAfter = getRGBAColorName(getRawElementColor(supCardName, "Personnel"));

        // ******************** Validating From Current Day(Yesterday's Board) ********************
        try {
            logTestInfo(wDriver, " ------------ Validating From Current Day(Yesterday's Board)------------ ");
            Assert.assertEquals(cardColorBefore, cardColorAfter, "Card Color");
            logTestInfo(wDriver, "Verifying Indicator Value: " + expectedDetails0[0]);
            Assert.assertEquals(mdaCode, "", "Mda Code");
            logTestInfo(wDriver, "Verifying card in MDA and NOT in Available");
            Assert.assertFalse(verifyCardPresenceInCategory("Mda", null, null, supCardName), "Card NOT in MDA");
            Assert.assertTrue(verifyCardPresenceInCategory("AvailableUn", "SUP", null, supCardName), "Card in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(mdaCountAfter, mdaCountBefore, "MDA Counts");
            Assert.assertEquals(availableCountAfter, availableCountBefore, "Available Counts");
            Assert.assertEquals(availableSUPCountAfter, availableSUPCountBefore, "Available SUP Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(supCardName, "Mda", remarks, expectedDetails0);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

        // ******************** Validating From Current Plus 1 Day(Today's Board) ********************
        logTestInfo(wDriver, " ------------ Validating From Current Plus 1 Day(Today's Board)  ------------ ");
        loginPage().goToBoardLocationByDate(url, location + "/", datePlus1);
        clickOnMenuIcon(wDriver, "Task");
        //clickOnMenuIcon(wDriver, "Equipment");
        //cardColorAfter = getRGBAColorName(getRawElementColor(supCardName));
        mdaCode = getIndicatorCode(supCardName, "Mda");
        try {
            //Assert.assertEquals(cardColorBefore, cardColorAfter, "Card Color");
            logTestInfo(wDriver, "Verifying Indicator Value: " + expectedDetailsPlus1[0]);
            Assert.assertEquals(mdaCode, expectedDetailsPlus1[0], "Mda Code");
            logTestInfo(wDriver, "Verifying card in MDA and NOT in Available");
            clickOnMenuIcon(wDriver, "Task");
            clickOnMenuIcon(wDriver, "Equipment");
            //scrollPageByHeightY(wDriver, getCardYAxis(supCardName));
            Assert.assertTrue(verifyCardPresenceInCategory("Mda", null, null, supCardName), "Card in MDA");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SUP", null, supCardName), "Card NOT in Available");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(supCardName, "Mda", remarks, expectedDetailsPlus1);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }


        // ******************** Validating From Current Plus 4 Days(Future Board) ********************
        logTestInfo(wDriver, " ------------ Validating From Current Plus 4 Days(Future Board) ------------ ");
        loginPage().goToBoardLocationByDate(url, location + "/", datePlus4);
        //clickOnMenuIcon(wDriver, "Task");
        clickOnMenuIcon(wDriver, "Equipment");
        //cardColorAfter = getRGBAColorName(getRawElementColor(supCardName));
        //mdaCode = getIndicatorCode(supCardName, "Mda");
        try {
            Assert.assertEquals(cardColorBefore, cardColorAfter, "Card Color");
            logTestInfo(wDriver, "Verifying Indicator Value: " + expectedDetailsPlus4[0]);
            Assert.assertEquals(mdaCode, expectedDetailsPlus4[0], "Mda Code");
            logTestInfo(wDriver, "Verifying card NOT in MDA");
            Assert.assertFalse(verifyCardPresenceInCategory("Mda", null, null, supCardName), "Card NOT in MDA");
            //Assert.assertTrue(verifyCardPresenceInCategory("AvailableUn", "SUP", null, supCardName), "Card in Available");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(supCardName, "Mda", remarks, expectedDetailsPlus4);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }




    }
}
