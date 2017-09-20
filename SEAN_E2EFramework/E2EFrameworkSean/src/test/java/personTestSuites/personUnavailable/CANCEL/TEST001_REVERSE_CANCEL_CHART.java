package personTestSuites.personUnavailable.CANCEL;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import person.expectedResults.panels.PersonHistoryUtilities;
import person.expectedResults.panels.PersonPanelUtilities;
import person.personUnavailable.actions.UnavailableActions;
import utilities.Utilities;

import java.io.IOException;

import static person.personUnavailable.actions.UnavailableActions.addUnavailable;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;

public class TEST001_REVERSE_CANCEL_CHART extends AbstractStartWebDriver {

    //Test Data
    private String url = LoginData.getLoginData(getUrl());
    private String location = "MN09";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");

    public String personCardName;
    public String unavailableCode;
    public String subCategory;
    public String effectiveDate = null;
    public String expectedEffectiveDate = null;
    public String effectiveHour = null;
    public String effectiveMinute = null;
    public String endDate = null;
    public String endHour = null;
    public String endMinute = null;
    public String remarks;
    public String action;
    public String status;

    @Test(priority = 1, description = "Cancel Chart Person for One SW")
    public void CancelChart_SW_Today() throws InterruptedException, IOException {
        extentTest.assignCategory("Regression", "Unavailable");
        location = setPersonLocationForTest("Available", "SW", 3);
        unavailableCode = "CHART";
        //setPersonLocationForTest("Unavailable", unavailbleCode,4);
        //location = PersonData.testLocation;
        subCategory = unavailableCode;
        effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
        expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
        remarks = "AddUnavilable_SW_" + Utilities.getUUID();
        String[] medicalFieldValues = null;
        String[] tempFieldValues = null;
        action = "EDIT";
        status = "Cancelled";
        loginPage().goToBoardLocationByDate(url, location+"/" , date);
        Thread.sleep(1500);
        for(int i = 0; i <= 3; i++) {
            personCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available", "SW", null);
            addUnavailable(personCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks, medicalFieldValues, tempFieldValues, "");
        }

        int getUnavailableChartCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Unavailable", subCategory);
        System.out.println("chart count before is " + getUnavailableChartCountBefore);
        if((getUnavailableChartCountBefore >= 4)) {
            personCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
            //personCardName = PersonPanelUtilities.getPersonName("Available", "SW");
            smartBoardPage().openPersonCardDetailPanel(personCardName);

            addUnavailable(personCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks, medicalFieldValues, tempFieldValues, "");
        }else
        {
            personCardName = PersonPanelUtilities.getPersonName("Unavailable","SW",subCategory);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
        }
        remarks = "CANCELLED CHART";

        int getAvailableSWCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Available", "SW");
        int getUnavailableCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Unavailable");
        getUnavailableChartCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Unavailable", subCategory);

        UnavailableActions.cancelChart(personCardName);

        PersonPanelUtilities.verifyCategoryCountAfter(+1, getAvailableSWCountBefore, "Available", "SW");
        PersonPanelUtilities.verifyCategoryCountAfter(-1, getUnavailableCountBefore, "Unavailable");
        PersonPanelUtilities.verifyCategoryCountAfter(-1, getUnavailableChartCountBefore, "Unavailable", subCategory);

        PersonPanelUtilities.checkIfCardNotPresentInCategory(personCardName, "Unavailable", subCategory);
        PersonPanelUtilities.checkIfCardPresentInCategory(personCardName, "Available");


        PersonHistoryUtilities.verifyUnavailableHistory(personCardName, unavailableCode, action, status, expectedEffectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks);

        softAssert.assertAll();
    }


    @Test(priority = 2, description = "Reverse Cancel Chart Person for One SW")
    //P_UN_03_ReverseCancelChart_SW_Today_010
    public void ReverseCancelChart_SW_Today() throws InterruptedException {
        extentTest.assignCategory("Regression", "Unavailable");

        unavailableCode = "CHART";
        subCategory = unavailableCode;
        effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
        expectedEffectiveDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
        remarks = "CHART REINSTATED";
        action = "EDIT";
        status = "Active";

        loginPage().goToBoardLocationByDate(url, location +"/", date);
        Thread.sleep(1500);
        smartBoardPage().openPersonCardDetailPanel(personCardName);

        int getAvailableSWCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Available", "SW");
        int getUnavailableCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Unavailable");
        int getUnavailableChartCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Unavailable", subCategory);

        UnavailableActions.reverseCancelChart(personCardName);

        PersonPanelUtilities.verifyCategoryCountAfter(-1, getAvailableSWCountBefore, "Available", "SW");
        PersonPanelUtilities.verifyCategoryCountAfter(+1, getUnavailableCountBefore, "Unavailable");
        PersonPanelUtilities.verifyCategoryCountAfter(+1, getUnavailableChartCountBefore, "Unavailable", subCategory);

        PersonPanelUtilities.checkIfCardPresentInCategory(personCardName, "Unavailable", subCategory);
        PersonPanelUtilities.checkIfCardNotPresentInCategory(personCardName, "Available");


        PersonHistoryUtilities.verifyUnavailableHistory(personCardName, unavailableCode, action, status, expectedEffectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks);

        softAssert.assertAll();
    }


}
