package person.specialPosition.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import person.abstractBase.PersonBasePage;

import java.io.IOException;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;


public class SpecialPosActions extends PersonBasePage {

    public SpecialPosActions(WebDriver wDriver) {
        super(wDriver);
        PageFactory.initElements(wDriver, this);
    }

    public static void specialPositionAdd(String personCardName, String specialPos, String startDate, String endDate, String remarks) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        try {
            logTestInfo(wDriver, "Performing Special Position Add on Card: " + personCardName);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            personDetailPanelPage().getSpecialPositionAddPanel();
            personSpecialPostionAddModal().setSpecialPosition(specialPos)
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .setRemarks(remarks)
                    .clickSubmit();

        } catch (Exception e) {
            logTestFailure(wDriver,  "SpecialPosition Add Failed!!! REASON: " + e.getMessage());
        }
    }


    public static void specialPositionEdit(String personCardName, int rowIndex, String specialPos, String startDate, String endDate, String remarks) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        try {
            logTestInfo(wDriver, "Performing Special Position Edit on Card: " + personCardName);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            personDetailPanelPage().getSpecialPositionEditPanel(rowIndex);
            personSpecialPositionEditModal().setStartDate(startDate)
                    .setEndDate(endDate)
                    .setRemarks(remarks)
                    .clickSubmit();

        } catch (Exception e) {
            logTestFailure(wDriver,  "SpecialPosition Edit Failed!!! REASON: " + e.getMessage());
        }
    }

    public static void specialPositionDelete(String personCardName, int rowIndex, String reason) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo(wDriver, "Performing Special Position Delete on Card: " + personCardName);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            personDetailPanelPage().getSpecialPositionDeletePanel(rowIndex);
            personSpecialPositionDeleteModal().setDeleteSpecialPositionReason(reason);
            personSpecialPositionDeleteModal().clickSubmit();

        } catch (Exception e) {
            logTestFailure(wDriver,  "SpecialPosition Delete Failed!!! REASON: " + e.getMessage());
        }
    }

}
