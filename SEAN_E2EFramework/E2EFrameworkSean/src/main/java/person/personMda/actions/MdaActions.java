package person.personMda.actions;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import person.abstractBase.PersonBasePage;

import java.io.IOException;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;

/**
 * Created by sdas on 9/27/2016.
 */
public class MdaActions extends PersonBasePage {
    public MdaActions(WebDriver wDriver) {
        super(wDriver);
        PageFactory.initElements(wDriver, this);
    }


    public static void addMda(String personCardName, String startDate, String mdaType, String appointmentDate, String endDate, String endHour, String endMinute, String remarks) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo(wDriver, "Performing Add Mda.....");
            logTestInfo(wDriver, "Current Card is: " + personCardName);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            personDetailPanelPage().getMdaAddPanel();
            personMdaAddModal()
                    .setMdaType(mdaType)
                    .setStartDate(startDate)
                    .setAppointmentDate(appointmentDate)
                    .setEndDate(endDate)
                    .setEndTime(endHour, endMinute)
                    .setRemarks(remarks);
            personMdaAddModal().clickSubmit();
        } catch (Exception e) {
            logTestFailure(wDriver,  "ADD Mda Failed!!! ERROR: " + e.getMessage());
        }

    }

    public static void editMda(String personCardName, int rowIndex, String startDate, String mdaType, String appointmentDate, String endDate, String endHour, String endMinute, String remarks) throws IOException {

        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo(wDriver, "Performing Edit Mda.....");
            logTestInfo(wDriver, "Current Card is: " + personCardName);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            personDetailPanelPage().getMdaEditPanel(rowIndex);
            personMdaEditModal()
                    .setMdaType(mdaType)
                    .setStartDate(startDate)
                    .setAppointmentDate(appointmentDate)
                    .setEndDate(endDate)
                    .setEndTime(endHour, endMinute)
                    .setRemarks(remarks);
            personMdaEditModal().clickSubmit();

        } catch (Exception e) {
            logTestFailure(wDriver,  "Edit Mda Failed!!! ERROR: " + e.getMessage());
        }
    }


    public static void deleteMda(String personCardName, int rowIndex, String reason) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo(wDriver, "Performing Delete Mda.....");
            logTestInfo(wDriver, "Current Card is: " + personCardName);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            personDetailPanelPage().getMdaDeletePanel(rowIndex);
            personMdaDeleteModal().setDeleteMdaReason(reason);
            personMdaDeleteModal().clickSubmit();
        } catch (Exception e) {
            logTestFailure(wDriver, "Delete Mda Failed!!! ERROR: " + e.getMessage());
        }
    }
}