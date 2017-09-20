package recentActivities.person;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personDetach.actions.DetachActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.pages.RecentActivities.getRecActivityContent;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.*;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST001_PERSON_DETACH_Recent_Activities_MINERVA_3916 extends AbstractStartWebDriver {

    //Test Data //
    public static String url = LoginData.getLoginData(getUrl());
    public static String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String toLocation = "MN08";
    public static String location = "SI02";
    public static String swCardName;
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "yyyy-MM-dd");
    public static String exEndDate = Utilities.getDesiredDateInFormat(3, "yyyy-MM-dd");
    public static String endHour = "12";
    public static String endMinute = "15";
    public static String shift = "1215-2015";
    public static String remarks = "Person_Detach_ADD" + Utilities.getUUID();


    @Test(priority = 1, description = "PERSON_DETACH_Recent_Activities ")
    public void PERSON_DETACH_Recent_Activities() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name MINERVA-3916:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SW", 10);
        loginPage().goToBoardLocationByDate(url, location+"/", date);
        swCardName = getPersonName("Available", "SW");
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        DetachActions.addDetach(swCardName, toLocation, startDate, endDate, endHour, endMinute, remarks);
        Thread.sleep(3000);
        String recentActivityLog = getRecActivityContent(0);
        logTestInfo(wDriver, ">>>>> Most Recent Activity Entry Content in the Log is:");
        logTestInfo(wDriver, recentActivityLog);

        //Validate count before and after
        logTestInfo(wDriver, ">>>>> Now Validating Recent Activity Log.......");
        try {
            //Assert.assertEquals(activityCountAfter, activityCountBefore + 1, "Recent Activity Row Count");
            logTestInfo(wDriver, ">>>>> Validating Presence of added personnel detachment.......");
            Assert.assertTrue(recentActivityLog.contains("added personnel detachment"), "Added Detach");
            logTestInfo(wDriver, ">>>>> Validating Presence of  From Location:" + location);
            Assert.assertTrue(recentActivityLog.contains("from :  " + location), "From Location");
            logTestInfo(wDriver, ">>>>> Validating Presence of  To Location: " + toLocation);
            Assert.assertTrue(recentActivityLog.contains("to :  " + toLocation), "To Location");
            logTestInfo(wDriver, ">>>>> Validating Presence of  startDate: " + exStartDate);
            Assert.assertTrue(recentActivityLog.contains("startDate :  " + exStartDate), "Start Date");
            logTestInfo(wDriver, ">>>>> Validating Presence of  endDate: " + exEndDate);
            Assert.assertTrue(recentActivityLog.contains("endDate :  " + exEndDate), "End Date");
            logTestInfo(wDriver, ">>>>> Validating Presence of  Remarks: " + remarks);
            Assert.assertTrue(recentActivityLog.contains(remarks), "Contains Remarks" + remarks);

            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }

    



}