package personTestSuites.personDetach.EDIT;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personDetach.actions.DetachActions;
import personTestSuites.personDetach.ADD.TEST002_DETACH_ADD_SW_Curr_To_Plus3;
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


public class TEST002_DETACH_EDIT_SW_From_B_Curr_To_Plus2 extends AbstractStartWebDriver {
    //Test Data //
    private String url = LoginData.getLoginData(getUrl());
    public static String location;
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String toLocation = "MN08";
    private String swCardName;
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(2, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(2, "M/d/yyyy");
    public static String endHour = "10";
    public static String endMinute = "15";
    public static String shift = "1215-2015";
    public static String remarks = "Person_Detach_EDIT" + Utilities.getUUID();
    private static int rowIndex = 0;
    String[] expectedDetails = {"Detach", "Active", "Temp", exStartDate, location, toLocation, exEndDate, remarks, shift};

    @Test(priority = 1, description = "Detach_Edit_SW_From_B_Curr_To_Plus2")
    public void Detach_Edit_SW_From_B_Curr_To_Plus2() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = TEST002_DETACH_ADD_SW_Curr_To_Plus3.location;
        swCardName = TEST002_DETACH_ADD_SW_Curr_To_Plus3.swCardName;
        location = location== null ? setPersonLocationForTest("Available", "SW", 5) : location;
        String[] expectedDetails = {"Detach", "Active", "Temp", exStartDate, location, toLocation, exEndDate, remarks, shift};
        if(swCardName == null){
            loginPage().goToBoardLocationByDate(url, location+"/", date);
            swCardName = getAPersonWithoutNextDayAssigned("Available", "SUP", null);
            deleteAllPersonHistory(swCardName);
            String endDate2 = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
            DetachActions.addDetach(swCardName, toLocation, startDate, endDate2, endHour, endMinute, remarks);
            expectedDetails = getDetailHistoryRowData(swCardName, "Detach", remarks);
            Thread.sleep(1000);
            expectedDetails[6] = exEndDate;

        }
        loginPage().goToBoardLocationByDate(url, toLocation+"/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");

        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName,"Personnel"));
        int detachCountBefore = getAnyCategoryHeaderCount("Detach", "SW", null);
        int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW", null);
        logTestInfo(wDriver, "SW Detach Count Before : " + detachCountBefore + " Available Count Before : " + availableCountBefore);
        logTestInfo(wDriver, "Performing  Detach Add Action....");
        DetachActions.editDetach(swCardName, rowIndex, endDate, remarks);
        Thread.sleep(5000);
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName,"Personnel"));
        personDetailPanelPage().closePersonCardDetailPanel();
        int detachCountAfter = getAnyCategoryHeaderCount("Detach", "SW", null);
        int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
        int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW", null);
        logTestInfo(wDriver, "Validating From Sending Location A....");
        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorBefore, cardColorAfter);
            logTestInfo(wDriver, "Verifying card in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("Available", "SW", "", swCardName));
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(detachCountAfter, detachCountBefore);
            Assert.assertEquals(availableCountAfter, availableCountBefore);
            Assert.assertEquals(availableSWCountAfter, availableSWCountBefore);
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory( swCardName, "Detach", remarks, expectedDetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }


}
