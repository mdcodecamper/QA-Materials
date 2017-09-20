package person.personMda.actions;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import person.abstractBase.PersonBasePage;

import java.io.IOException;

import static common.actions.CommonActions.getRawElementColor;
import static common.data.RawColorCodes.getRGBAColorName;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;

/**
 * Created by sdas on 10/31/2016.
 */
public class AddMdaWithValidation extends PersonBasePage{

    public AddMdaWithValidation(WebDriver wDriver) {
        super(wDriver);
        PageFactory.initElements(wDriver, this);
    }

    public static void addMda(String personCardName, String startDate, String mdaType, String appointmentDate, String endDate, String endHour, String endMinute, String remarks) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            extentTest.log(LogStatus.INFO, "Performing Add Mda.....");
            String cardColorBefore = getRGBAColorName(getRawElementColor(personCardName, "Personnel"));
            int availableCountBefore = getAnyCategoryHeaderCount("Available", null, null);
            int availableSWCountBefore = getAnyCategoryHeaderCount("Available", "SW", null);
            int mdaCountBefore = getAnyCategoryHeaderCount("Mda", null, null);
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
            String cardColorAfter = getRGBAColorName(getRawElementColor(personCardName, "Personnel"));
            int availableCountAfter = getAnyCategoryHeaderCount("Available", null, null);
            int availableSWCountAfter = getAnyCategoryHeaderCount("Available", "SW", null);
            int mdaCountAfter = getAnyCategoryHeaderCount("Mda", null, null);
        } catch (Exception e) {
            Reporter.log("Test Failed: " + e.getMessage(), true);
            extentTest.log(LogStatus.FAIL, "ADD Mda Failed!!! ERROR: "  + e.getMessage());
            getScreenShot(wDriver);
            Reporter.log("Test Failed!!! Taking Screenshot: " + e.getMessage(), true);
            Assert.fail();
        }

    }

    public static void panelCountValidator(String cardName){

    }
}
