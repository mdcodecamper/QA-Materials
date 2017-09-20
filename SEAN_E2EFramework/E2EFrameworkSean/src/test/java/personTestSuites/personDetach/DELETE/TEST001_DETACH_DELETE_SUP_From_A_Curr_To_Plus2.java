package personTestSuites.personDetach.DELETE;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personDetach.actions.DetachActions;
import personTestSuites.personDetach.ADD.TEST001_DETACH_ADD_SUP_Curr_To_Plus3_Validate_Present_Past_Future_MINERVA_3789;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.data.RawColorCodes.getRGBAColorName;
import static person.expectedResults.panels.PersonCardUtilities.getAPersonWithoutNextDayAssigned;
import static person.expectedResults.panels.PersonHistoryUtilities.getDetailHistoryRowData;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST001_DETACH_DELETE_SUP_From_A_Curr_To_Plus2 extends AbstractStartWebDriver {
    //Test Data //
    private String url = LoginData.getLoginData(getUrl());
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String location;
    private String toLocation = "MN08";
    private String supCardName;
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(2, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String endHour = "12";
    public static String endMinute = "15";
    public static String shift = "1215-2015";
    public static String reason = "Person_Detach_DELETE" + Utilities.getUUID();
    public static int rowIndex = 0;


    @Test(priority = 1, description = "DETACH_DELETE_SUP_From_A_Curr_To_Plus2")
    public void DETACH_DELETE_SUP_From_A_Curr_To_Plus2() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        supCardName = TEST001_DETACH_ADD_SUP_Curr_To_Plus3_Validate_Present_Past_Future_MINERVA_3789.supCardName;
        location = TEST001_DETACH_ADD_SUP_Curr_To_Plus3_Validate_Present_Past_Future_MINERVA_3789.location;
        location = location== null ? setPersonLocationForTest("Available", "SUP", 3) : location;
        String[] expectedDetails = {"Detach", "Deleted", "Temp", exStartDate, location, toLocation, exEndDate, reason, shift};
        loginPage().goToBoardLocationByDate(url, location+"/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        if(supCardName == null){
            supCardName = getAPersonWithoutNextDayAssigned("Available", "SUP", null);
            deleteAllPersonHistory(supCardName);
            DetachActions.addDetach(supCardName, toLocation, startDate, endDate, endHour, endMinute, reason);
            expectedDetails = getDetailHistoryRowData(supCardName, "Detach", reason);
            Thread.sleep(1000);
            expectedDetails[1] = "Deleted";
        }

        String cardColorBefore = getRGBAColorName(getRawElementColor(supCardName, "Personnel"));
        int detachCountBefore = getAnyCategoryHeaderCount("Detach", "SUP", null);
        int detachSUPCountBefore = getAnyCategoryHeaderCount("Detach", "SUP", null);
        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSUPCountBefore = getAnyCategoryHeaderCount("Available", "SUP", null);
        logTestInfo(wDriver, "Performing  Detach Add Action....");
        DetachActions.deleteDetach(supCardName, rowIndex, reason);
        Thread.sleep(3000);
        personDetailPanelPage().closePersonCardDetailPanel();
        int detachCountAfter = getAnyCategoryHeaderCount("Detach", "SUP", null);
        int detachSUPCountAfter = getAnyCategoryHeaderCount("Detach", "SUP", null);
        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSUPCountAfter = getAnyCategoryHeaderCount("Available", "SUP", null);
        String cardColorAfter = getRGBAColorName(getRawElementColor(supCardName, "Personnel"));
        logTestInfo(wDriver, "Validating From Sending Location A....");
        //PersonPanelUtilities.checkIfCardPresentInCategory(supCardName, "Detach", null, null);
        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorBefore, cardColorAfter, "Card Color");
            logTestInfo(wDriver, "Verifying card in Available and NOT in Detach....");
            Assert.assertTrue(verifyCardPresenceInCategory("Available", "SUP", "", supCardName), "Card in Available SUP");
            Assert.assertFalse(verifyCardPresenceInCategory("Detach", "SUP", "", supCardName), "Card NOT in Detach SUP");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(detachSUPCountAfter, detachSUPCountBefore - 1, "Detached SUP Card Count");
            Assert.assertEquals(detachCountAfter, detachCountBefore - 1, "Detached Card Count");
            Assert.assertEquals(availableSUPCountAfter, availableSUPCountBefore + 1, "Available SUP Card Count");
            Assert.assertEquals(availableCountAfter, availableCountBefore + 1, "Available Count");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(supCardName, "Detach", reason, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

    }


}