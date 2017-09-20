package personTestSuites.personMda.EDIT;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personMda.actions.MdaActions;
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

//@Listeners(TestNGTestListener.class)
public class TEST001_MDA_EDIT_SW_Curr_To_Plus2 extends AbstractStartWebDriver {
    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location;
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String swCardName = null;
    public static String swMdaType = "4 - MDA 4 – LIGHT DUTY";
    public static String supMdaType = "7 - MDA 7 - NO DRIVING";
    public static String appointmentDate = Utilities.getDesiredDateInFormat(1, "MM/dd/yyyy");
    public static String exAppointmentDate = Utilities.getDesiredDateInFormat(1, "M/d/yyyy");
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(2, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String endHour = "11";
    public static String endMinute = "30";
    public static String remarks;
    public static int rowIndex = 0;
    public static String[] expectedDetails;

    @Test(priority = 1, description = "MDA_EDIT_SW_Curr_To_Plus2")
    public void MDA_EDIT_SW_Curr_To_Plus2() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SW", 3);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        remarks = "Person_MDA_ADD" + Utilities.getUUID();
        clickOnMenuIcon(wDriver, "Task");
        clickOnMenuIcon(wDriver, "Equipment");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        deleteAllPersonHistory(swCardName);
        MdaActions.addMda(swCardName, startDate, swMdaType, appointmentDate, endDate, endHour, endMinute, remarks);
        Thread.sleep(2000);
        expectedDetails = getDetailHistoryRowData(swCardName, "Mda", remarks);
        remarks = "Person_MDA_EDIT" + Utilities.getUUID();
        expectedDetails[1] = "Updated";
        expectedDetails[2] = "Active";
        expectedDetails[7] = remarks;

        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW", null);
        int mdaCountBefore = getAnyCategoryHeaderCount("Mda", null, null);

        MdaActions.editMda(swCardName, rowIndex, null, swMdaType, appointmentDate, endDate, endHour, endMinute, remarks);
        Thread.sleep(5000);
        personDetailPanelPage().closePersonCardDetailPanel();
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        String mdaCodeAfter = getIndicatorCode(swCardName, "Mda");
        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW", null);
        int mdaCountAfter = getAnyCategoryHeaderCount("Mda", null, null);



        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations.....");
        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying Indicator Value: " + expectedDetails[0]);
            Assert.assertEquals(mdaCodeAfter, expectedDetails[0], "MDA Code");
            logTestInfo(wDriver, "Verifying card in MDA and NOT in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("Mda", null, null, swCardName), "Card STILL in MDA");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card NOT in Available SW");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(getAnyCategoryHeaderCount("Mda", null, null), mdaCountBefore, "MDA Count");
            Assert.assertEquals(availableCountAfter, availableCountBefore, "Available Count");
            Assert.assertEquals(availableSWCountAfter, availableSWCountBefore, "Available SW Count");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Mda", remarks, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

    }


}

