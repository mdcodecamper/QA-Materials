package personTestSuites.personSpPosition.ADD;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.specialPosition.actions.SpecialPosActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.data.RawColorCodes.getRGBAColorName;
import static person.expectedResults.panels.PersonCardUtilities.getAPersonWithoutNextDayAssigned;
import static person.expectedResults.panels.PersonCardUtilities.getIndicatorCode;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;

/**
 * Created by sdas on 9/24/2016.
 */
public class TEST002_SPECIAL_POS_ADD_Validate_Present_Past_Future extends AbstractStartWebDriver {

    //Test Data //
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKN05";
    private String boardDate = Utilities.getDesiredDateInFormat(5, "yyyyMMdd");
    public static String swCardName;
    public static String specialPos = "Hardship-HS";
    public static String specialPosCode = "HS";
    public static String startDate = Utilities.getDesiredDateInFormat(5, "MM/dd/yyyy");
    public static String pastBoardDate = Utilities.getDesiredDateInFormat(4, "yyyyMMdd");
    public static String futureBoardDate = Utilities.getDesiredDateInFormat(8, "yyyyMMdd");
    public static String endDate = Utilities.getDesiredDateInFormat(7, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(5, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(7, "M/d/yyyy");
    public static String remarks = "SPECIAL_POS_ADD_SW" + Utilities.getUUID();

    @Test(priority = 1, description = "SPECIAL_POS_ADD_Validate_Present_Past_Future")
    public void SPECIAL_POS_ADD_Validate_Present_Past_Future() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SW", 3);
        String[] expectedDetails = {"HS", "ADD", "Active", "Hardship", exStartDate, exEndDate, remarks};
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        deleteAllPersonHistory(swCardName);

        //swCardName = getFreshCardForTest("Avail", "SW", "Special");

        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW", null);
        int availableSUPCountBefore = getAnyCategoryHeaderCount("Available", "SUP", null);
        SpecialPosActions.specialPositionAdd(swCardName, specialPos, startDate, endDate, remarks);
        Thread.sleep(2000);
        personDetailPanelPage().closePersonCardDetailPanel();
        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW", null);
        int availableSUPCountAfter = getAnyCategoryHeaderCount("Available", "SUP", null);
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        String spIndicatorAfter = getIndicatorCode(swCardName, "SpecialPosition");
        //Validate count before and after
        logTestInfo(wDriver, "************************* VALIDATE FROM CURRENT BOARD *************************");
        try {

            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorBefore, cardColorAfter, "Card Color");
            logTestInfo(wDriver, "Verifying Indicator Value: " + expectedDetails[0]);
            Assert.assertEquals(spIndicatorAfter, expectedDetails[0], "Special Position Indicator");
            logTestInfo(wDriver, "Verifying Card is Still in SW....");
            Assert.assertTrue(verifyCardPresenceInCategory("Available", "SW", "", swCardName), "Card in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availableCountAfter, availableCountBefore, "Available Count");
            Assert.assertEquals(availableSWCountBefore, availableSWCountAfter, "Available SW Count");
            Assert.assertEquals(availableSUPCountBefore, availableSUPCountAfter, "Available SUP Count");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Special", remarks, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

        logTestInfo(wDriver, "************************* VALIDATE FROM PAST BOARD *************************");
        loginPage().goToBoardLocationByDate(url, location+"/", pastBoardDate);
        String[] expectedPastDetails = {"HS", "ADD", "Future", "Hardship", exStartDate, exEndDate, remarks};
        spIndicatorAfter = getIndicatorCode(swCardName, "SpecialPosition");
        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorBefore, cardColorAfter, "Card Color");
            logTestInfo(wDriver, "Verifying Indicator Value: " + expectedDetails[0]);
            Assert.assertEquals(spIndicatorAfter, "", "Special Position Indicator");

            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Special", remarks, expectedPastDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }


        logTestInfo(wDriver, "************************* VALIDATE FROM FUTURE BOARD *************************");
        loginPage().goToBoardLocationByDate(url, location+"/", futureBoardDate);
        String[] expectedFutureDetails = {"HS", "ADD", "Completed", "Hardship", exStartDate, exEndDate, remarks};
        spIndicatorAfter = getIndicatorCode(swCardName, "SpecialPosition");
        try {
            logTestInfo(wDriver, "Verifying Indicator Value: " + expectedDetails[0]);
            Assert.assertEquals(spIndicatorAfter, "", "Special Position Indicator");
            logTestInfo(wDriver, "Verifying Card is Still in SW....");
            Assert.assertTrue(verifyCardPresenceInCategory("Available", "SW", "", swCardName), "Card in Available");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Special", remarks, expectedFutureDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }


    }


}