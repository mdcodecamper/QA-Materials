package recentActivities.person;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personMda.actions.MdaActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.pages.RecentActivities.getRecActivityContent;
import static person.expectedResults.panels.PersonPanelUtilities.getPersonName;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST001_PERSON_MDA_Recent_Activities extends AbstractStartWebDriver {

    //Test Data //
    private String url = LoginData.getLoginData(getUrl());
    public static String location;
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String swMdaType = "4 - MDA 4 – LIGHT DUTY";
    public static String appointmentDate = Utilities.getDesiredDateInFormat(1, "MM/dd/yyyy");
    public static String exAppointmentDate = Utilities.getDesiredDateInFormat(1, "yyyy-MM-dd");
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(11, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "yyyy-MM-dd");
    public static String exEndDate = Utilities.getDesiredDateInFormat(11, "yyyy-MM-dd");
    public static String endHour = "11";
    public static String endMinute = "30";
    public static String remarks = "TEST001_Person_MDA_ADD" + Utilities.getUUID();


    @Test(priority = 1, description = "PERSON_MDA_Recent_Activities")
    public void PERSON_MDA_Recent_Activities() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SW", 5);
        loginPage().goToBoardLocationByDate(url, location+"/", date);
        swCardName = getPersonName("Available", "SW");
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);
        deletePersonHistoryByTable(swCardName, "Mda");
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        MdaActions.addMda(swCardName, startDate, swMdaType, appointmentDate, endDate, endHour, endMinute, remarks);
        Thread.sleep(3000);
        String recentActivityLog = getRecActivityContent(0);
        logTestInfo(wDriver, ">>>>> Most Recent Activity Entry Content in the Log is:");
        logTestInfo(wDriver, recentActivityLog);

        //Validate count before and after
        logTestInfo(wDriver, ">>>>> Now Validating Recent Activity Log.......");
        //recentActivityValidator("added MDA", exStartDate, exEndDate, remarks);

        try {
            //Assert.assertEquals(activityCountAfter, activityCountBefore + 1, "Recent Activity Row Count");
            logTestInfo(wDriver, ">>>>> Validating Presence of added MDA.......");
            Assert.assertTrue(recentActivityLog.contains("added MDA"), "added MDA");
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