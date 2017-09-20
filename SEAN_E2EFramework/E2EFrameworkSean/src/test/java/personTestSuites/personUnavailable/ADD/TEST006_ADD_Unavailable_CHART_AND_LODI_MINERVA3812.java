package personTestSuites.personUnavailable.ADD;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import person.expectedResults.panels.PersonPanelUtilities;
import person.personUnavailable.actions.UnavailableActions;
import common.actions.PersonActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static person.expectedResults.panels.UnavailableModalDetails.getMedicalDetails;
import static personTestSuites.testData.PersonData.*;

/**
 * Created by sdas on 11/22/2016.
 */
public class TEST006_ADD_Unavailable_CHART_AND_LODI_MINERVA3812 extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location; //"MN04";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String expModalStartDate = Utilities.getDesiredDateInFormat(0, "yyyy-MM-dd");
    public static String swCardName;
    public static String unavailableCode;
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String effectiveHour = null;
    public static String effectiveMinute = null;
    public static String endDate = null;    ;
    //public static String expectedEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = null;
    public static String endMinute = null;
    public static String remarks;
    public static String action = "ADD";
    public static String status = "Active";
    String[] medicalFieldValues = null;
    String[] tempFieldValues = null;

    @Test(priority = 1, description = "ADD_Unavailable_Chart_LODI")
    public void ADD_Unavailable_Chart_LODI() throws InterruptedException, IOException {
        location = setPersonLocationForTest("Available", "SW", 3);
        remarks = "CHART_ADD_SW" + Utilities.getUUID();
        unavailableCode = "CHART";
        String[] expUnavailDetails = {unavailableCode, action, status, expectedEffectiveDate, expectedEffectiveDate, remarks};
        //loginPage().goToBoardLocationByDate(url, location +"/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        swCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        Reporter.log("Person Card Name is: " + swCardName, true);
        deleteAllPersonHistory(swCardName);
        personDetailPanelPage().closePersonCardDetailPanel();
        try {
            int availableCountBefore = PersonActions.get_cards_count("auPersonnelAvailablePanel", null, null);
            int availableSWCountBefore = PersonActions.get_cards_count("auPersonnelAvailablePanel", "auSanitationWorkersHeader", null);
            int unavailableCountBefore = PersonActions.get_cards_count("auPersonnelUnavailablePanel", null, null);
            int unavailableChartCountBefore = PersonActions.get_cards_count("auPersonnelUnavailablePanel", "auCHARTHeader", null);
            int unavailableCMultiCountBefore = PersonActions.get_cards_count("auPersonnelUnavailablePanel", "auMultipleHeader", null);
            logTestInfo(wDriver, "Counters before adding Chard: availableCountBefore=" + availableCountBefore +
                    " availableSWCountBefore=" + availableSWCountBefore + " unavailableCountBefore=" + unavailableCountBefore +
                    " unavailableChartCountBefore=" + unavailableChartCountBefore + " unavailableCMultiCountBefore=" + unavailableCMultiCountBefore);
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

            logTestPass(wDriver, "Counters after adding Chard: availableCountAfter=" + availableCountAfter +
                    " availableSWCountAfter=" + availableSWCountAfter + " unavailableCountAfter=" + unavailableCountAfter +
                    " unavailableChartCountAfter=" + unavailableChartCountAfter);
            logTestInfo(wDriver, "Verifying card in Unavailable and NOT in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("auPersonnelUnavailablePanel", "CHARTpersonnel-Unavailable", null, swCardName), "Card in CHART");
            Assert.assertFalse(verifyCardPresenceInCategory("AvailableUn", "SW", null, swCardName), "Card NOT in Available");

            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Unavail", remarks, expUnavailDetails);

            logTestInfo(wDriver, "Adding LODI to CHART for card " + swCardName);
            unavailableCode = "LODI";
            UnavailableActions.addUnavailable(swCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks, medicalFieldValues, tempFieldValues, "");
            Thread.sleep(2000);
            personDetailPanelPage().closePersonCardDetailPanel();
            logTestInfo(wDriver, "Now Performing Test Validations after adding LODI .....");
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            int unavailableCMultiCountAfter = PersonActions.get_cards_count("auPersonnelUnavailablePanel","auMultipleHeader",null);
            int unavailableCountAfterLODI = PersonActions.get_cards_count("auPersonnelUnavailablePanel",null,null);
            int unavailableChartCountAfterLODI = PersonActions.get_cards_count("auPersonnelUnavailablePanel","auCHARTHeader",null);
            Assert.assertEquals(unavailableCountAfterLODI, unavailableCountAfter, "Unavailable Card Counts");
            Assert.assertEquals(unavailableChartCountAfterLODI, unavailableChartCountAfter - 1, "Unavailable Chart Counts");
            Assert.assertEquals(unavailableCMultiCountAfter, unavailableCMultiCountBefore + 1, "Unavailable Multi Counts");
            logTestPass(wDriver, "Counters after adding LODI: unavailableCountAfterLODI=" + unavailableCountAfterLODI +
                    " unavailableChartCountAfterLODI=" + unavailableChartCountAfterLODI + " unavailableCMultiCountAfter=" + unavailableCMultiCountAfter);
            logTestInfo(wDriver, "Verifying card in Multiple and NOT in Chart");
            Assert.assertTrue(verifyCardPresenceInCategory("auPersonnelUnavailablePanel", "Multiplepersonnel-Unavailable", null, swCardName), "Card in Multi");
            Assert.assertFalse(verifyCardPresenceInCategory("auPersonnelUnavailablePanel", "CHARTpersonnel-Unavailable", null, swCardName), "Card NOT in Chart");
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");

        }
        catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();

        }


    }

}
