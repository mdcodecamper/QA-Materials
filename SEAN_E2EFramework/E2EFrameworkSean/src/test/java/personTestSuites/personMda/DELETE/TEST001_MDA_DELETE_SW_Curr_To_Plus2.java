package personTestSuites.personMda.DELETE;

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


//@Listeners(TestNGTestListener.class)
public class TEST001_MDA_DELETE_SW_Curr_To_Plus2 extends AbstractStartWebDriver {

    //Test Data //
    private String url = LoginData.getLoginData(getUrl());
    private static String location;
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    ;
    private static String swCardName = null;
    public static String swMdaType = "4 - MDA 4 – LIGHT DUTY";
    public static String appointmentDate = Utilities.getDesiredDateInFormat(1, "MM/dd/yyyy");
    public static String exAppointmentDate = Utilities.getDesiredDateInFormat(1, "M/d/yyyy");
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(2, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String endHour = "11";
    ;
    public static String endMinute = "30";
    public static String reason = "TEST001_Person_MDA_DELETE" + Utilities.getUUID();
    public static int rowIndex = 0;
    String[] expectedDetails;

    @Test(priority = 1, description = "MDA_DELETE_SW_Curr_To_Plus2 ")
    public void MDA_DELETE_SW_Curr_To_Plus2() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = "BKS13";
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Task");
        clickOnMenuIcon(wDriver, "Equipment");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        deleteAllPersonHistory(swCardName);
        MdaActions.addMda(swCardName, startDate, swMdaType, appointmentDate, endDate, endHour, endMinute, reason);
        Thread.sleep(2000);

        expectedDetails = getDetailHistoryRowData(swCardName, "Mda", reason);
        expectedDetails[1] = "Remove";
        expectedDetails[2] = "Deleted";

        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW", null);
        int mdaCountBefore = getAnyCategoryHeaderCount("Mda", null, null);

        MdaActions.deleteMda(swCardName, rowIndex, reason);
        Thread.sleep(5000);
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        String mdaCodeAfter = getIndicatorCode(swCardName, "Mda");
        personDetailPanelPage().closePersonCardDetailPanel();

        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW", null);
        int mdaCountAfter = getAnyCategoryHeaderCount("Mda", null, null);
        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations.....");
        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorBefore, cardColorAfter, "Card Color");
            logTestInfo(wDriver, "Verifying Indicator Value is REMOVED");
            Assert.assertEquals(mdaCodeAfter, "", "MDA Code");
            logTestInfo(wDriver, "Verifying card is NOT in MDA and is IN Available");
            Assert.assertFalse(verifyCardPresenceInCategory("Mda", null, null, swCardName), "Card NOT in MDA");
            Assert.assertTrue(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(mdaCountAfter, mdaCountBefore-1,"MDA Count");
            Assert.assertEquals(availableCountAfter, availableCountBefore + 1, "Available Count");
            Assert.assertEquals(availableSWCountAfter, availableSWCountBefore + 1, "Available SW Count");

            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Mda", reason, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

    }


}
