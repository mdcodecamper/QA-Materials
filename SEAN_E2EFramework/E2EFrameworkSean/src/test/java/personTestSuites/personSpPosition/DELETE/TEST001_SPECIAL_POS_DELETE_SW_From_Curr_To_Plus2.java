package personTestSuites.personSpPosition.DELETE;


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
public class TEST001_SPECIAL_POS_DELETE_SW_From_Curr_To_Plus2 extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKN05";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(2, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    private String swCardName = null;
    public static String specialPos = "Hardship-HS";
    public static String specialPosCode = "HS";
    public static String reason = "SPECIAL_POS_DELETE" + Utilities.getUUID();
    public static int rowIndex = 0;
    String[] expectedDetails;

    @Test(description = "SPECIAL_POS_DELETE_SW_From_Curr_To_Plus2 ")
    public void SPECIAL_POS_DELETE_SW_From_Curr_To_Plus2() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SW", 15);
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        //Test Data
        if(swCardName == null){
            swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
            deleteAllPersonHistory(swCardName);
            SpecialPosActions.specialPositionAdd(swCardName, specialPos, startDate, endDate, reason);
            Thread.sleep(2000);
            expectedDetails = getDetailHistoryRowData(swCardName, "Special", reason);
            expectedDetails[1] = "REMOVE";
            expectedDetails[2] = "Deleted";
        }

        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName,"Personnel"));
        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW", null);
        int availableSUPCountBefore = getAnyCategoryHeaderCount("Available", "SUP", null);

        SpecialPosActions.specialPositionDelete(swCardName, rowIndex, reason);
        Thread.sleep(2000);
        personDetailPanelPage().closePersonCardDetailPanel();

        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW", null);
        int availableSUPCountAfter = getAnyCategoryHeaderCount("Available", "SUP", null);
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName,"Personnel"));
        String spIndicatorAfter = getIndicatorCode(swCardName, "SpecialPosition");
        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations.....");
        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorBefore, cardColorAfter, "Card Color");
            logTestInfo(wDriver, "Verifying Indicator Value: " + specialPosCode);
            Assert.assertEquals(spIndicatorAfter, "", "No Special Position Indicator");
            logTestInfo(wDriver, "Verifying Card is NOT Still in SW....");
            Assert.assertTrue(verifyCardPresenceInCategory("Available", "SW", "", swCardName), "Card in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(availableCountAfter, availableCountBefore, "Available Count");
            Assert.assertEquals(availableSWCountBefore, availableSWCountAfter, "Available SW Count");
            Assert.assertEquals(availableSUPCountBefore, availableSUPCountAfter, "Available SUP Count");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Special", reason, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }


    }



}