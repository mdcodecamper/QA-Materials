package personTestSuites.personUnavailable.ADD;

import _driverScript.AbstractStartWebDriver;
import common.actions.PersonActions;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personDetach.actions.DetachActions;
import person.personMda.actions.MdaActions;
import person.personUnavailable.actions.UnavailableActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static person.expectedResults.panels.PersonCardUtilities.getAPersonWithoutNextDayAssigned;
import static person.expectedResults.panels.PersonCardUtilities.getIndicatorCode;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.getNonCurrentLocation;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST005_ADD_Unavailable_AND_MDA_MINERVA_3819 extends AbstractStartWebDriver {
    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    public static String location;
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String swCardName;
    public static String swMdaType = "3 - MDA 3 - LIMITED PHYSICAL DUTY";
    public static String swMdaCode = "3";
    public static String appointmentDate = Utilities.getDesiredDateInFormat(1, "MM/dd/yyyy");
    public static String exAppointmentDate = Utilities.getDesiredDateInFormat(1, "M/d/yyyy");
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String unavailableCode="CHART";
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String effectiveHour = null;
    public static String effectiveMinute = null;
    public static String endDate = null;    ;
    public static String endHour = null;
    public static String endMinute = null;
    public static String remarks= "TEST0017_PERSONNEL_UNAVAILABLE_and_MDA" + Utilities.getUUID();
    public static String action = "ADD";
    public static String status = "Active";

    String[] medicalFieldValues = null;
    String[] tempFieldValues = null;
    String[] expectedMDADetails = {"3", "Add", "Active", exStartDate, exAppointmentDate, remarks};
    String[] expUnavailDetails = {unavailableCode, action, status, expectedEffectiveDate, expectedEffectiveDate, remarks};


    @Test(priority = 1, description = "MDA_ADD_UNAVAILABLE_SW")
    public void MDA_ADD_UNAVAILABLE_SW () throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        location = setPersonLocationForTest("Available", "SW", 3);
        //loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Task");
        clickOnMenuIcon(wDriver, "Equipment");
        swCardName = getAPersonWithoutNextDayAssigned("Available", "SW", null);
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);
        try {

            int availableCountBefore = PersonActions.get_cards_count("auPersonnelAvailablePanel", null, null);
            int availableSWCountBefore = PersonActions.get_cards_count("auPersonnelAvailablePanel", "auSanitationWorkersHeader",null);
            int unavailableCountBefore = PersonActions.get_cards_count("auPersonnelUnavailablePanel", null, null);
            int unavailableChartCountBefore = PersonActions.get_cards_count("auPersonnelUnavailablePanel", "auCHARTHeader", null);

            logTestInfo(wDriver, "Adding Chart for Card " + swCardName);
            UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks, medicalFieldValues, tempFieldValues, "");

            Thread.sleep(2000);

            personDetailPanelPage().closePersonCardDetailPanel();
            logTestInfo(wDriver, "Now Performing Test Validations for current board date.....");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            int availableCountAfter = PersonActions.get_cards_count("auPersonnelAvailablePanel", null, null);
            int availableSWCountAfter = PersonActions.get_cards_count("auPersonnelAvailablePanel", "auSanitationWorkersHeader",null);
            int unavailableCountAfter = PersonActions.get_cards_count("auPersonnelUnavailablePanel",null,null);
            int unavailableChartCountAfter = PersonActions.get_cards_count("auPersonnelUnavailablePanel","auCHARTHeader",null);

            Assert.assertEquals(availableCountAfter, availableCountBefore - 1, "Available Card Counts");
            Assert.assertEquals(availableSWCountAfter, availableSWCountBefore - 1, "Available SW Card Counts");
            Assert.assertEquals(unavailableCountAfter, unavailableCountBefore + 1, "Unavailable Card Counts");
            Assert.assertEquals(unavailableChartCountAfter, unavailableChartCountBefore + 1, "Unavailable Chart Counts");

            logTestPass(wDriver, "Counters after adding Chard are as expected: availableCountAfter=" + availableCountAfter +
                    " availableSWCountAfter=" + availableSWCountAfter + " unavailableCountAfter=" + unavailableCountAfter +
                    " unavailableChartCountAfter=" + unavailableChartCountAfter);
            logTestInfo(wDriver, "Verifying card in Unavailable and NOT in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("auPersonnelUnavailablePanel", "CHARTpersonnel-Unavailable", null, swCardName), "Card in CHART");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card NOT in Available");

            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", remarks, expUnavailDetails);

            //===============================================================================
            logTestInfo(wDriver, "Adding MDA to card " + swCardName);
            MdaActions.addMda(swCardName, startDate, swMdaType, appointmentDate, "", "", "", remarks);
            Thread.sleep(4000);
            String mdaCodeAfter = getIndicatorCode(swCardName, "Mda");
            personDetailPanelPage().closePersonCardDetailPanel();

            //Validate count before and after
            logTestInfo(wDriver, "Now Performing Test Validations after adding MDA.....");
            logTestInfo(wDriver, "Verifying card in Unavailable/Chart with MDA");
            int unavailableCountAfterMDA = PersonActions.get_cards_count("auPersonnelUnavailablePanel",null,null);
            int unavailableChartCountAfterMDA = PersonActions.get_cards_count("auPersonnelUnavailablePanel","auCHARTHeader",null);

            Assert.assertTrue(verifyCardPresenceInCategory("auPersonnelUnavailablePanel", "CHARTpersonnel-Unavailable", null, swCardName), "Card in CHART");
            logTestInfo(wDriver, "Verifying Indicator Value: " + swMdaCode);
            Assert.assertEquals(swMdaCode, mdaCodeAfter);

            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(unavailableChartCountAfterMDA, unavailableChartCountAfter, "Unavailable Chart Card Counts");
            Assert.assertEquals(unavailableCountAfterMDA, unavailableCountAfter, "Unavailable Card Count");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Mda", remarks, expectedMDADetails);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }

    }


}
