package personTestSuites.personSpPosition.EDIT;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.specialPosition.actions.SpecialPosActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
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
public class TEST002_SPECIAL_POS_EDIT_SW_OpenDate_To_AnyValidDate extends AbstractStartWebDriver {

    //Test Data //
    private String url = LoginData.getLoginData(getUrl());
    private String location = "BKS11";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String swCardName = null;
    public static String specialPos = "Hardship-HS";
    public static String specialPosCode = "HS";
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate;
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate;
    public static String remarks = "SPECIAL_POS_EDIT_SW" + Utilities.getUUID();
    public static int rowIndex = 0;
    String[] expectedDetails;

    @Test(priority = 1, description = "SPECIAL_POS_EDIT_SW_OpenDate_To_AnyValidDate")
    public void SPECIAL_POS_EDIT_SW_OpenDate_To_AnyValidDate() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SW", 15);
        endDate = null;
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        //Test Data
        endDate = Utilities.getDesiredDateInFormat(4, "MM/dd/yyyy");
        exEndDate = Utilities.getDesiredDateInFormat(4, "M/d/yyyy");
            swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
            deleteAllPersonHistory(swCardName);
            SpecialPosActions.specialPositionAdd(swCardName, specialPos, startDate, null, remarks);
            Thread.sleep(2000);

        String[] expectedDetails = {specialPosCode, "EDIT", "Active", "Hardship", exStartDate, exEndDate, remarks};
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW", null);
        SpecialPosActions.specialPositionEdit(swCardName, rowIndex, specialPos, startDate, endDate, remarks);
        Thread.sleep(2000);
        String[] actualDetails = getDetailHistoryRowData(swCardName, "Special", remarks);
        personDetailPanelPage().closePersonCardDetailPanel();
        Thread.sleep(2000);
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW", null);
        String spIndicatorAfter = getIndicatorCode(swCardName, "SpecialPosition");
        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations.....");
        try {
            logTestInfo(wDriver, "Verifying Indicator Value: " + specialPosCode);
            Assert.assertEquals(spIndicatorAfter, specialPosCode, "Special Position Indicator");
            logTestInfo(wDriver, "Verifying SW Card Count.... ");
            Assert.assertEquals(availableSWCountAfter, availableSWCountBefore, "SW Card Count");
            logTestInfo(wDriver, "Verifying Card is Still in SW.... ");
            Assert.assertTrue(verifyCardPresenceInCategory("Available", "SW", "", swCardName), "Card in Available SW");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(actualDetails, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }


    }
}

