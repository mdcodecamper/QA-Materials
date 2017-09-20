package personTestSuites.personMda.ADD;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
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
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST001_MDA_ADD_SW_Curr_To_Plus3 extends AbstractStartWebDriver {
    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    public static String location;
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String swMdaType = "4 - MDA 4 – LIGHT DUTY";
    public static String appointmentDate = Utilities.getDesiredDateInFormat(1, "MM/dd/yyyy");
    public static String exAppointmentDate = Utilities.getDesiredDateInFormat(1, "M/d/yyyy");
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = "11";
    public static String endMinute = "30";


    @Test(priority = 1, description = "MDA_ADD_SW_Curr_To_Plus3 ")
    public void MDA_ADD_SW_Curr_To_Plus3 () throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        String remarks = "TEST001_Person_MDA_ADD" + Utilities.getUUID();
        location = setPersonLocationForTest("Available", "SW", 3);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Task");
        clickOnMenuIcon(wDriver, "Equipment");
        //Test Data
        String[] expectedDetails = {"4", "Add", "Active", exStartDate, exAppointmentDate, exEndDate, endHour + ":" + endMinute, remarks};
        //swCardName = getFreshCardForTest("Avail", "SW", "Mda");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName,"Personnel"));
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);

        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Available", null,null);
        int availableSWCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Available", "SW",null);
        int mdaCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Mda", null,null);

        MdaActions.addMda(swCardName, startDate, swMdaType, appointmentDate, endDate, endHour, endMinute, remarks);
        //Thread.sleep(1000);
        //isCardAnimated(swCardName);
        Thread.sleep(4000);
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName,"Personnel"));
        String mdaCodeAfter = getIndicatorCode(swCardName, "Mda");
        personDetailPanelPage().closePersonCardDetailPanel();

        //Validate count before and after
        logTestInfo(wDriver, "Now Performing Test Validations.....");
        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorBefore, cardColorAfter, "Card Color");
            logTestInfo(wDriver, "Verifying Indicator Value: " + expectedDetails[0]);
            Assert.assertEquals(expectedDetails[0], mdaCodeAfter);
            logTestInfo(wDriver, "Verifying card in MDA and NOT in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("Mda", null, null, swCardName), "Card in MDA");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card NOT in Available");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            //Assert.assertEquals(mdaCountAfter, mdaCountBefore + 1, "MDA Counts");
            CommonActions.getAnyCategoryCardsCountAfter("+1",mdaCountBefore,"Personnel","Mda",null,null);
            //Assert.assertEquals(availableCountAfter, availableCountBefore - 1, "Available Counts");
            CommonActions.getAnyCategoryCardsCountAfter("-1",availableCountBefore,"Personnel","Available",null,null);
            //Assert.assertEquals(availableSWCountAfter, availableSWCountBefore - 1,"Available SW Counts");
            CommonActions.getAnyCategoryCardsCountAfter("-1",availableSWCountBefore,"Personnel","Available","SW",null);
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
