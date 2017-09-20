package person.personDetach.modals;

import _driverScript.AbstractStartWebDriver;
import common.pages.PersonDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.io.IOException;

import static common.actions.CommonActions.logTestPass;
import static common.actions.PersonActions.personSubmitAndVerify;

public class PersonDetachAddModal extends AbstractStartWebDriver {
    public PersonDetachAddModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);

    }

    @FindBy(css = ".auPersonnelDetachmentPanel .form-title")
    WebElement PanelHeader;
    @FindBy(css = "auPersonnelDetachmentPanel .form-personnel-name")
    WebElement PersonCardName;
    @FindBy(id = "to")
    WebElement ToLocation;
    @FindBy(id = "startDate")
    WebElement StartDate;
    @FindBy(id = "endDate")
    WebElement EndDate;
    @FindBy(xpath = "//select[@ngcontrol='startTimeHour']")
    WebElement StartHour;
    @FindBy(xpath = "//select[@ngcontrol='startTimeMinute']")
    WebElement StartMinute;
    @FindBy(xpath = "//select[@ngcontrol='endTimeHour']")
    WebElement EndHour;
    @FindBy(css = ".auShiftEndTime .shift-minute .form-control")
    WebElement EndMinute;
    @FindBy(css = ".auPersonnelDetachmentPanel .auRemarks")
    WebElement Remarks;
    @FindBy(css = ".auSubmit")
    WebElement Submit;
    @FindBy(xpath = "//*[text()='Cancel']")
    WebElement Cancel;

    // =======================================================================

    public PersonDetachAddModal detachToLocation(String toLocation) {
        Reporter.log("Setting Location....." + toLocation, true);
        if (toLocation != null) {
            clickThenSelectDropDownByText(waitForElementThenDo(wDriver, ToLocation), toLocation);
           logTestPass(wDriver,  "Set Location: " + toLocation);
        }
        return new PersonDetachAddModal(wDriver);
    }

    public PersonDetachAddModal setStartDate(String startDate) throws IOException {
        if (startDate != null) {
            setDatePropertyTrue("startDate");
            waitForElementThenDo(wDriver, StartDate).sendKeys(startDate);
           logTestPass(wDriver,  "Entered StartDate: " + startDate);
        }
        return new PersonDetachAddModal(wDriver);
    }

    public PersonDetachAddModal setEndDate(String endDate) throws IOException {
        if (endDate != null) {
            setDatePropertyTrue("endDate");
            waitForElementThenDo(wDriver, EndDate).sendKeys(endDate);
           logTestPass(wDriver,  "Entered End Date: " + endDate);
        }
        return new PersonDetachAddModal(wDriver);
    }

    public PersonDetachAddModal setStartTime(String startHour, String startMinute) {
        Reporter.log("Entering Start Hour and Start Minute....." + startHour + ":" + startMinute, true);
        if (startHour != null && StartHour.isEnabled()) {            
            selectFromDropDownByVisibleText(waitForElementThenDo(wDriver, StartHour), startHour);
           logTestPass(wDriver,  "Entered Start Hour: " + startHour);
        }
        if (startMinute != null && StartMinute.isEnabled()) {           
            selectFromDropDownByVisibleText(waitForElementThenDo(wDriver, StartMinute), startMinute);
           logTestPass(wDriver,  "Entered Start Minute: " + startMinute);
        }
        return new PersonDetachAddModal(wDriver);
    }

    //Not Editable currently
    public PersonDetachAddModal setEndTime(String endHour, String endMinute) {
        // These fields are NOT currently editable
        Reporter.log("Entering End Hour and End Minute....." + endHour + ":" + endMinute, true);
        if (endHour != null && EndHour.isEnabled()) {
            selectFromDropDownByVisibleText(waitForElementThenDo(wDriver, EndHour), endHour);
           logTestPass(wDriver,  "Entered Start Hour: " + endHour);
        }
        if (endMinute != null && EndMinute.isEnabled()) {
            selectFromDropDownByVisibleText(waitForElementThenDo(wDriver, EndMinute), endMinute);
           logTestPass(wDriver,  "Entered Start Hour: " + endMinute);
        }
        return new PersonDetachAddModal(wDriver);
    }

    public PersonDetachAddModal setRemarks(String remarks) {
        Reporter.log("Entering Remarks....." + remarks, true);
        if (remarks != null) {
            waitForElementThenDo(wDriver, Remarks).clear();
            waitForElementThenDo(wDriver, Remarks).sendKeys(remarks);
           logTestPass(wDriver,  "Entered Remarks: " + remarks);
        }
        return new PersonDetachAddModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        personSubmitAndVerify(wDriver, Submit);
        //extentTest.log(LogStatus.INFO, "Clicked ADD DETACH Submit Button");
        return new PersonDetailPage(wDriver);

    }

}
