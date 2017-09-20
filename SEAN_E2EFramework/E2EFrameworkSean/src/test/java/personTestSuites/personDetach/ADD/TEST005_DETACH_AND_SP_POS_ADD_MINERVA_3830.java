package personTestSuites.personDetach.ADD;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personDetach.actions.DetachActions;
import person.specialPosition.actions.SpecialPosActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.data.RawColorCodes.getRGBAColorName;
import static person.expectedResults.panels.PersonCardUtilities.getAPersonWithoutNextDayAssigned;
import static person.expectedResults.panels.PersonCardUtilities.getIndicatorCode;
import static person.expectedResults.panels.PersonHistoryUtilities.getDetailHistoryRowData;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST005_DETACH_AND_SP_POS_ADD_MINERVA_3830 extends AbstractStartWebDriver {

    //Test Data //
    public static String url = LoginData.getLoginData(getUrl());
    public static String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String location;
    public static String toLocation = "MN08";
    public static String swCardName;
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = "12";
    public static String endMinute = "15";
    public static String shift = "1215-2015";
    public static String remarks;
    ;
    public static String specialPos = "Hardship-HS";
    public static String specialPosCode = "HS";

    @Test(priority = 1, description = "DETACH_AND_SP_POS_ADD_MINERVA_3830")
    public void DETACH_AND_SP_POS_ADD_MINERVA_3830() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SW", 5);
        remarks = "Person_Detach_ADD" + Utilities.getUUID();
        String[] expDetachHistory = {"Detach", "Active", "Temp", exStartDate, location, toLocation, exEndDate, remarks, shift};
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        deleteAllPersonHistory(swCardName);
        int detachCountBefore = getAnyCategoryHeaderCount("Detach", "SW", null);
        int spPosCountBefore = getAnyCategoryHeaderCount("Special", "SW", null);
        logTestInfo(wDriver, "Now Performing  Detach Add Action....");
        // Detach
        DetachActions.addDetach(swCardName, toLocation, startDate, endDate, endHour, endMinute, remarks);
        Thread.sleep(3000);
        String[] actualDetachHist = getDetailHistoryRowData(swCardName, "Detach", remarks);
        // Special Position
        remarks = "Person_Special_ADD" + Utilities.getUUID();
        String[] expSpecHistory = {"HS", "ADD", "Active", "Hardship", exStartDate, exEndDate, remarks};
        SpecialPosActions.specialPositionAdd(swCardName, specialPos, startDate, endDate, remarks);
        Thread.sleep(3000);
        String[] actualSpecHist = getDetailHistoryRowData(swCardName, "Special", remarks);
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        personDetailPanelPage().closePersonCardDetailPanel();
        int detachCountAfter = getAnyCategoryHeaderCount("Detach", "SW", null);
        String spIndicatorAfter = getIndicatorCode(swCardName, "SpecialPosition");
        //Validation
        logTestInfo(wDriver, "Validating From Sending Location A....");
        try {
            logTestInfo(wDriver, "Verifying Indicator Value: " + specialPosCode);
            Assert.assertEquals(spIndicatorAfter, specialPosCode, "Special Position Indicator");
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying card in Detach and NOT in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("Detach", "SW", "", swCardName), "Card in Detached SW");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(detachCountAfter, detachCountBefore + 1, "Detach Count");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(actualDetachHist, expDetachHistory);
            validateDetailRowHistory(actualSpecHist, expSpecHistory);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

        logTestInfo(wDriver, "Validating From Receiving Location B....");
        try {
            loginPage().goToBoardLocationByDate(url, toLocation + "/", date);
            cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
            logTestInfo(wDriver, "Verifying Card Color After in Receiving Location: " + cardColorAfter);
            Assert.assertEquals(cardColorAfter, "salmon", "Card Color");
            Assert.assertTrue(verifyCardPresenceInCategory("Available", "SW", "", swCardName), "Card in Available SW");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(actualDetachHist, expDetachHistory);
            validateDetailRowHistory(actualSpecHist, expSpecHistory);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }

}