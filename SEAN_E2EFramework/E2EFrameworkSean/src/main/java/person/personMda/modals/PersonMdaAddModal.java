package person.personMda.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import common.pages.PersonDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

import static common.actions.CommonActions.logTestPass;
import static common.actions.PersonActions.personSubmitAndVerify;

public class PersonMdaAddModal extends AbstractStartWebDriver {

    ExtentTest extentTest;

    public PersonMdaAddModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    @FindBy(css = "personnel-add-mda-form .auHeaderTitle")
    @CacheLookup
    public static WebElement PanelHeader;
    @FindBy(css = "personnel-add-mda-form .auHeaderName")
    @CacheLookup
    public static WebElement PersonCardName;
    @FindBy(css = "personnel-add-mda-form .auAppointmentDate")
    @CacheLookup
    public static WebElement AppointmentDate;
    @FindBy(css = "personnel-add-mda-form .auMDAType")
    @CacheLookup
    public static WebElement MdaType;
    @FindBy(css = "personnel-add-mda-form .auStartDate")
    @CacheLookup
    public static WebElement StartDate;
    @FindBy(css = "personnel-add-mda-form .auEndDate")
    @CacheLookup
    public static WebElement EndDate;
    @FindBy(css = "personnel-add-mda-form .auHour")
    @CacheLookup
    public static WebElement EndHour;
    @FindBy(css = "personnel-add-mda-form .auMinute")
    @CacheLookup
    public static WebElement EndMinute;
    @FindBy(css = "#comments")
    @CacheLookup
    public static WebElement Remarks;
    @FindBy(css = ".auSubmit")
    @CacheLookup
    public static WebElement Submit;
    @FindBy(xpath = "//*[text()='Cancel']")
    @CacheLookup
    public static WebElement Cancel;

    // =======================================================================
    public PersonMdaAddModal setStartDate(String startDate) throws IOException {
        // Reporter.log("Entering Start Date: " + startDate, true);
        if (startDate != null) {
            setDatePropertyTrue("startDate");
            waitForElementThenDo(wDriver, StartDate).sendKeys(startDate);
            logTestPass(wDriver,  "Entered Start Date: " + startDate);
            //getScreenShot(wDriver);
        }
        return new PersonMdaAddModal(wDriver);
    }

    public PersonMdaAddModal setMdaType(String mdaType) throws IOException {
        // Reporter.log("Setting MDA Type: " + mdaType, true);
        if (mdaType != null) {
            clickThenSelectDropDownByText(waitForElementThenDo(wDriver, MdaType), mdaType);
            logTestPass(wDriver,  "Selected MDA TYPE: " + mdaType);
        }
        return new PersonMdaAddModal(wDriver);
    }

    public PersonMdaAddModal setAppointmentDate(String appointDate) throws IOException {
        // Reporter.log("Entering Appointment Date: " + appointDate, true);
        if (appointDate != null) {
            setDatePropertyTrue("appointmentDate");
            waitForElementThenDo(wDriver, AppointmentDate).sendKeys(appointDate);
            logTestPass(wDriver,  "Entered Appointment Date: " + appointDate);
        }
        return new PersonMdaAddModal(wDriver);
    }

    public PersonMdaAddModal setEndDate(String endDate) throws IOException {
        // Reporter.log("Entering End Date: " + endDate, true);
        if (endDate != null) {
            setDatePropertyTrue("endDate");
            waitForElementThenDo(wDriver, EndDate).sendKeys(endDate);
            logTestPass(wDriver,  "Entered End Date: " + endDate);
        }
        return new PersonMdaAddModal(wDriver);
    }

    public PersonMdaAddModal setEndTime(String endHour, String endMinute) throws IOException, InterruptedException {
        // Reporter.log("Entering End Hour and End Minute: " + endHour + ":" + endMinute, true);
        if (endHour != null) {
            waitForElementThenDo(wDriver, EndHour).clear();
            waitForElementThenDo(wDriver, EndHour).sendKeys(endHour);
            logTestPass(wDriver,  "Entered End Hour: " + endHour);
            Thread.sleep(300);
        }
        if (endMinute != null) {
            waitForElementThenDo(wDriver, EndMinute).clear();
            waitForElementThenDo(wDriver, EndMinute).sendKeys(endMinute);
            logTestPass(wDriver,  "Entered End Minute: " + endMinute);
        }
        return new PersonMdaAddModal(wDriver);
    }

    public PersonMdaAddModal setRemarks(String remarks) throws IOException {
        if (remarks != null) {
            // Reporter.log("Entering Remarks: " + remarks, true);
            waitForElementThenDo(wDriver, Remarks).sendKeys(remarks);
            logTestPass(wDriver,  "Entered Remark: " + remarks);
        }
        return new PersonMdaAddModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        personSubmitAndVerify(wDriver, Submit);
        return new PersonDetailPage(wDriver);

    }

}
