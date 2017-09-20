package person.specialPosition.modals;

import _driverScript.AbstractStartWebDriver;
import common.pages.PersonDetailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

import static common.actions.CommonActions.logTestPass;
import static common.actions.PersonActions.personSubmitAndVerify;

public class PersonSpecialPositionEditModal extends AbstractStartWebDriver {
    public PersonSpecialPositionEditModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);

    }

    @FindBy(css = ".auPersonnelSpecialPositionUpdatePanel .auHeaderTitle")
    WebElement PanelHeader;
    @FindBy(css = ".auPersonnelSpecialPositionUpdatePanel .auHeaderName")
    WebElement PersonCardName;
    @FindBy(css = ".auPersonnelSpecialPositionUpdatePanel .ui-select-toggle")
    WebElement SpecialPosition;
    @FindBy(id = "startDate")
    WebElement StartDate;
    @FindBy(id = "endDate")
    WebElement EndDate;
    @FindBy(css = "#comments")
    WebElement Remarks;
    @FindBy(css = ".auSubmit")
    WebElement Submit;
    @FindBy(xpath = "//*[text()='Cancel']")
    WebElement Cancel;

    // =======================================================================
    public PersonSpecialPositionEditModal setSpecialPosition(String specialPosition) {
        // Reporter.log("Setting Special Position....." + specialPosition, true);
        if (specialPosition != null) {
            waitForElementThenDo(wDriver, SpecialPosition).click();
            moveToAnElement(wDriver, wDriver.findElement(By.partialLinkText(specialPosition)));
            waitForElementThenDo(wDriver, wDriver.findElement(By.partialLinkText(specialPosition))).click();
            logTestPass(wDriver, "Set Special Position: " + specialPosition);
        } else {
            // Reporter.log("Data is NULL!");
        }
        return new PersonSpecialPositionEditModal(wDriver);
    }

    public PersonSpecialPositionEditModal setStartDate(String startDate) throws IOException {
        // Reporter.log("Editing Start Date....." + startDate, true);
        if (startDate != null && StartDate.isEnabled()) {
            setDatePropertyTrue("startDate");
            waitForElementThenDo(wDriver, StartDate).sendKeys(startDate);
            logTestPass(wDriver, "Entered Start Date: " + startDate);
        } else {

        }
        return new PersonSpecialPositionEditModal(wDriver);
    }

    public PersonSpecialPositionEditModal setEndDate(String endDate) throws InterruptedException, IOException {
        setDatePropertyTrue("endDate");
        if (endDate != null) {
            if (endDate.toUpperCase() == "REMOVE") {
                waitForElementThenDo(wDriver, EndDate).click();
                Thread.sleep(5000);
                EndDate.sendKeys(Keys.BACK_SPACE);
                Thread.sleep(5000);
                EndDate.sendKeys(Keys.TAB);
                Thread.sleep(5000);
                EndDate.sendKeys(Keys.DELETE);
                Thread.sleep(5000);
                EndDate.sendKeys(Keys.TAB);
                Thread.sleep(5000);
                EndDate.sendKeys(Keys.DELETE);
                logTestPass(wDriver, "Removed End Date to Open Date");

            } else {
                // waitForElementThenDo(wDriver, EndDate).clear();
                setDatePropertyTrue("endDate");
                waitForElementThenDo(wDriver, EndDate).sendKeys(endDate);
                logTestPass(wDriver, "Entered End Date: " + endDate);
            }

        }
        return new PersonSpecialPositionEditModal(wDriver);
    }

    public PersonSpecialPositionEditModal setRemarks(String remarks) {
        // Reporter.log("Editing Remarks....." + remarks, true);
        if (remarks != null) {
            waitForElementThenDo(wDriver, Remarks).clear();
            waitForElementThenDo(wDriver, Remarks).sendKeys(remarks);
            logTestPass(wDriver, "Entered Remarks: " + remarks);
        }
        return new PersonSpecialPositionEditModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        personSubmitAndVerify(wDriver, Submit);
        return new PersonDetailPage(wDriver);
    }
}
