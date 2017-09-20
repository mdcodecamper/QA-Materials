package person.personUnavailable.actions;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import person.abstractBase.PersonBasePage;
import person.expectedResults.panels.PersonPanelUtilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static person.expectedResults.panels.PersonHistoryUtilities.getUnavailHistEditablePage;


public class UnavailableActions extends PersonBasePage {
    public UnavailableActions(WebDriver wDriver) {
        super(wDriver);
        PageFactory.initElements(wDriver, this);
    }


    public static void addUnavailable(String personCardName, String unavailbleCode, String effectiveDate, String effectiveHour, String effectiveMinute, String endDate, String endHour, String endMinute, String remarks, String[] medicalFieldValues, String[] tempFieldValues, String trialDate) {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            extentTest.log(LogStatus.INFO, "Performing Add Unavailable for person: " + personCardName);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            personDetailPanelPage().getUnavailableAddPanel();
            personUnavailableAddModal()
                    .setUnavailableCode(unavailbleCode)
                    .setEffectiveDate(effectiveDate)
                    .setEffectiveTime(effectiveHour, effectiveMinute)
                    .setMedicalDetailsFields(medicalFieldValues)
                    .setTempAddressFields(tempFieldValues)
                    .setEndDate(endDate)
                    .setEndTime(endHour, endMinute)
                    .setRemarks(remarks);
            personUnavailableAddModal().clickSubmit();
            logTestPass(wDriver, "Add Unavailable Completed successfully for: " + personCardName);
        } catch (Exception e) {
            logTestFailure(wDriver, "Failed to enter all unavailable details for: " + personCardName);
            Assert.fail();
        }

    }


    public static void editUnavailable(String personCardName, int rowNumber, String unavaialbleCode, String effectiveDate, String effectiveHour, String effectiveMinute, String endDate, String endHour, String endMinute, String remarks/*, String[] medicalFieldValues, String[] tempFieldValues, String trialDate*/) throws IOException, InterruptedException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            extentTest.log(LogStatus.INFO, "Performing Edit Unavailable for person: " + personCardName);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            getUnavailHistEditablePage(personCardName);
            personDetailPanelPage().getUnavailableEditModal(rowNumber);
            personUnavailableEditModal()
                    .setUnavailableCode(unavaialbleCode)
                    .setEffectiveDate(effectiveDate)
                    .setEffectiveTime(effectiveHour, effectiveMinute)
                    .setEndDate(endDate)
                    .setEndTime(endHour, endMinute)
                    .setRemarks(remarks);
            personUnavailableEditModal().clickSubmit();
            logTestPass(wDriver, "Edit Unavailable Completed successfully for: " + personCardName);
        } catch (Exception e) {
            logTestFailure(wDriver, "Failed to Edit unavailable details for: " + personCardName);
            Assert.fail();
        }

    }

    public static void deleteUnavailable(String personCardName, int rowIndex, String reason) throws IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        try {
            logTestInfo(wDriver, "Performing Delete Unavailable for person: " + personCardName);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            getUnavailHistEditablePage(personCardName);
            personDetailPanelPage().getUnavailableDeleteModal(rowIndex)
                    .setDeleteUnavailableReason(reason)
                    .clickSubmit();
            logTestPass(wDriver, "Delete Unavailability Completed successfully for: " + personCardName);
        } catch (Exception e) {
            logTestFailure(wDriver, "Failed to Delete Unavailability for: " + personCardName);

            Assert.fail();
        }
    }

    public static void cancelChart(String personCardName) {
        try {
            logTestInfo(wDriver, "Performing Cancel Chart for person: " + personCardName);
            cancelUnavailable(personCardName);
            logTestPass(wDriver, "Cancel Chart Completed successfully for: " + personCardName);
        } catch (Exception e) {
            logTestFailure(wDriver, "Failed to Cancel Chart for: " + personCardName);
            Assert.fail();
        }
    }

    public static void reverseCancelChart(String personCardName) {
        try {
            logTestInfo(wDriver, "Performing Reverse Cancel Chart for person: " + personCardName);
            cancelUnavailable(personCardName);
            logTestPass(wDriver, "REVERSE Cancel Chart Completed successfully for: " + personCardName);
        } catch (Exception e) {
            logTestFailure(wDriver, "Failed to Reverse Cancel Chart for: " + personCardName);
            Assert.fail();
        }
    }

    public static void cancelUnavailable(String personCardName) throws InterruptedException {
        try {
            personPanelModal().clickCancelChart();
            PersonPanelUtilities.getPersonCard(personCardName).click();
            Thread.sleep(1500);
            personPanelModal().clickConfirmCancelChart();
            Thread.sleep(1500);
        } catch (Exception e) {
            logTestFailure(wDriver, "Error Occurred in cancelUnavailable method while Cancelling Unavailable for: " + personCardName);
            Assert.fail();
        }
    }

}

