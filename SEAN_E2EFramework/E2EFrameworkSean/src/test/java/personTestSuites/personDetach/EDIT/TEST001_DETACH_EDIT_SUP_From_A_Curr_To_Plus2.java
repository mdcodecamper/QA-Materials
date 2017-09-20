package personTestSuites.personDetach.EDIT;

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


public class TEST001_DETACH_EDIT_SUP_From_A_Curr_To_Plus2 extends AbstractStartWebDriver {

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
    public static String endHour = "10";
    public static String endMinute = "15";
    public static String shift = "1215-2015";
    public static String remarks = "Person_Detach_EDIT" + Utilities.getUUID();
    private static int rowIndex = 0;


    @Test(priority = 1, description = "DETACH_EDIT_SUP_From_A_Curr_To_Plus2 ")
    public void DETACH_EDIT_SUP_From_A_Curr_To_Plus2() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = TEST001_DETACH_ADD_SUP_Curr_To_Plus3_Validate_Present_Past_Future_MINERVA_3789.location;
        supCardName = TEST001_DETACH_ADD_SUP_Curr_To_Plus3_Validate_Present_Past_Future_MINERVA_3789.supCardName;
        location = location== null ? setPersonLocationForTest("Available", "SUP", 3) : location;
        String[] expectedDetails = {"Detach", "Active", "Temp", exStartDate, location, toLocation, exEndDate, remarks, shift};
        loginPage().goToBoardLocationByDate(url, location+"/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        if(supCardName == null){
            supCardName = getAPersonWithoutNextDayAssigned("Available", "SUP", null);
            deleteAllPersonHistory(supCardName);
            String endDate2 = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
            DetachActions.addDetach(supCardName, toLocation, startDate, endDate2, endHour, endMinute, remarks);
            expectedDetails = getDetailHistoryRowData(supCardName, "Detach", remarks);
            Thread.sleep(1000);
        }
        Reporter.log("Person Card Name is: " + supCardName, true);
        expectedDetails[6] = exEndDate;
        String cardColorBefore = getRGBAColorName(getRawElementColor(supCardName,"Personnel"));
        int detachCountBefore = getAnyCategoryHeaderCount("Detach", "SUP", null);
        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSUPCountBefore = getAnyCategoryHeaderCount("Available", "SUP", null);
        logTestInfo(wDriver, "SUP Detach Count Before : " + detachCountBefore + " Available Count Before : " + availableCountBefore);
        logTestInfo(wDriver, "Performing  Detach Add Action....");
        DetachActions.editDetach(supCardName, rowIndex, endDate, remarks);
        Thread.sleep(2000);
        personDetailPanelPage().closePersonCardDetailPanel();
        int detachCountAfter = getAnyCategoryHeaderCount("Detach", "SUP", null);
        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSUPCountAfter = getAnyCategoryHeaderCount("Available", "SUP", null);
        String cardColorAfter = getRGBAColorName(getRawElementColor(supCardName,"Personnel"));
        logTestInfo(wDriver, "Validating From Sending Location A....");
        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorBefore, cardColorAfter, "Card Color");
            logTestInfo(wDriver, "Verifying card in Detach and NOT in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("Detach", "SUP", "", supCardName), "Card in Detached SUP");
            Assert.assertFalse(verifyCardPresenceInCategory("Available", "SUP", "", supCardName), "Card NOT in Available SUP");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(detachCountAfter, detachCountBefore, "Detached Card Count");
            Assert.assertEquals(availableCountAfter, availableCountBefore, "Available Card Count");
            Assert.assertEquals(availableSUPCountAfter, availableSUPCountBefore, "Available SUP Card Count");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory( supCardName, "Detach", remarks, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }


}