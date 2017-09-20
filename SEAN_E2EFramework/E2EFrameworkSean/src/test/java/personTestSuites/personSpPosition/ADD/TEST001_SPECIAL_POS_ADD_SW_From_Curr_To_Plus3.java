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
import static person.expectedResults.panels.PersonHistoryUtilities.getDetailHistoryRowData;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;

/**
 * Created by sdas on 9/24/2016.
 */
public class TEST001_SPECIAL_POS_ADD_SW_From_Curr_To_Plus3 extends AbstractStartWebDriver {

    //Test Data //
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKN05";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String specialPos = "Hardship-HS";
    public static String specialPosCode = "HS";
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String remarks = "SPECIAL_POS_ADD_SW" + Utilities.getUUID();

    String[] expectedDetails = {"HS", "ADD", "Active", "Hardship", exStartDate, exEndDate, remarks};

    @Test(priority = 1, description = "SPECIAL_POS_ADD_SW_From_Curr_To_Plus3 ")
    public void SPECIAL_POS_ADD_SW_From_Curr_To_Plus3() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SW", 3);
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        //swCardName = getFreshCardForTest("Avail", "SW", "Special");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        deleteAllPersonHistory(swCardName);

        Reporter.log("Person Card Name is: " + swCardName, true);

        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW", null);
        int availableSUPCountBefore = getAnyCategoryHeaderCount("Available", "SUP", null);
        SpecialPosActions.specialPositionAdd(swCardName, specialPos, startDate, endDate, remarks);
        Thread.sleep(2000);
        String[] actualDetails = getDetailHistoryRowData(swCardName, "Special", remarks);
        personDetailPanelPage().closePersonCardDetailPanel();
        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW", null);
        int availableSUPCountAfter = getAnyCategoryHeaderCount("Available", "SUP", null);
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        String spIndicatorAfter = getIndicatorCode(swCardName, "SpecialPosition");
        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations.....");
        try {
            exStartDate = Utilities.getDesiredDateInFormat(0, "yyyy-M-d");
            exEndDate = Utilities.getDesiredDateInFormat(3, "yyyy-M-d");
            //recentActivityValidator("added", exStartDate, exEndDate);
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
            //Assert.assertEquals(actualDetails, expectedDetails, "History Details");
            validateDetailRowHistory(swCardName, "Special", remarks, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }


    }


}