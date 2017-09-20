package person.personMda.modals;


import _driverScript.AbstractStartWebDriver;
import common.pages.PersonDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.io.IOException;

import static common.actions.CommonActions.logTestPass;
import static common.actions.PersonActions.personSubmitAndVerify;


public class PersonMdaEditModal extends AbstractStartWebDriver {
    public PersonMdaEditModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);

    }

    @FindBy(css = ".auMDAPanel .auHeaderTitle")
    @CacheLookup
    public static WebElement PanelHeader;
    @FindBy(css = ".auMDAPanel .auHeaderName")
    @CacheLookup
    public static WebElement PersonCardName;
    @FindBy(css = ".auMDAPanel .auStartDate")
    @CacheLookup
    public static WebElement StartDate;
    @FindBy(id = "mdaType")
    @CacheLookup
    public static WebElement MdaType;
    @FindBy(id = "appointmentDate")
    @CacheLookup
    public static WebElement AppointmentDate;
    @FindBy(id = "endDate")
    @CacheLookup
    public static WebElement EndDate;
    @FindBy(xpath = "//input[contains(@class,'auHour')]")
    @CacheLookup
    public static WebElement EndHour;
    @FindBy(xpath = "//input[contains(@class,'auMinute')]")
    @CacheLookup
    public static WebElement EndMinute;
    @FindBy(id = "comments")
    @CacheLookup
    public static WebElement Remarks;
    @FindBy(xpath = "//*[text()='Update']")
    @CacheLookup
    public static WebElement Submit;
    @FindBy(xpath = "//*[text()='Cancel']")
    @CacheLookup
    public static WebElement Cancel;

    //=======================================================================

    public PersonMdaEditModal setMdaType(String mdaType) {
        if (mdaType != null) {
            clickThenSelectDropDownByText(waitForElementThenDo(wDriver, MdaType), mdaType);
            logTestPass(wDriver, "Selected MDA TYPE: " + mdaType);
        }
        return new PersonMdaEditModal(wDriver);
    }

    public PersonMdaEditModal setAppointmentDate(String appointDate) throws IOException {
        if (appointDate != null) {
            setDatePropertyTrue("appointmentDate");
            waitForElementThenDo(wDriver, AppointmentDate).sendKeys(appointDate);
            logTestPass(wDriver, "Entered Appointment Date: " + appointDate);
        }
        return new PersonMdaEditModal(wDriver);
    }

    public PersonMdaEditModal setStartDate(String startDate) throws IOException {
        Reporter.log("Editing End Date....." + startDate, true);
        if (startDate != null) {
            setDatePropertyTrue("startDate");
            waitForElementThenDo(wDriver, EndDate).sendKeys(startDate);
            logTestPass(wDriver, "Entered End Date: " + startDate);
        }
        return new PersonMdaEditModal(wDriver);
    }

    public PersonMdaEditModal setEndDate(String endDate) throws IOException {
        if (endDate != null) {
            setDatePropertyTrue("endDate");
            waitForElementThenDo(wDriver, EndDate).sendKeys(endDate);
            logTestPass(wDriver, "Entered End Date: " + endDate);
        }
        return new PersonMdaEditModal(wDriver);
    }

    public PersonMdaEditModal setEndTime(String endHour, String endMinute) throws InterruptedException {
          if (endHour != null) {
            waitForElementThenDo(wDriver, EndHour).clear();
            waitForElementThenDo(wDriver, EndHour).sendKeys(endHour);
            logTestPass(wDriver, "Entered End Hour: " + endHour);
            Thread.sleep(300);
        }
        if (endMinute != null) {
            waitForElementThenDo(wDriver, EndMinute).clear();
            waitForElementThenDo(wDriver, EndMinute).sendKeys(endMinute);
            logTestPass(wDriver, "Entered End Minute: " + endMinute);
        }
        return new PersonMdaEditModal(wDriver);
    }

    public PersonMdaEditModal setRemarks(String remarks) {
        Reporter.log("Editing Remarks....." + remarks, true);
        if (remarks != null) {
            waitForElementThenDo(wDriver, Remarks).clear();
            waitForElementThenDo(wDriver, Remarks).sendKeys(remarks);
            logTestPass(wDriver, "Entered Remark: " + remarks);
        }
        return new PersonMdaEditModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        personSubmitAndVerify(wDriver, Submit);
        return new PersonDetailPage(wDriver);

    }

}



























