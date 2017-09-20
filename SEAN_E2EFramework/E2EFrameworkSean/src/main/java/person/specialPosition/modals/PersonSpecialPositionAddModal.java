package person.specialPosition.modals;

import _driverScript.AbstractStartWebDriver;
import common.pages.PersonDetailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

import static common.actions.CommonActions.logTestPass;
import static common.actions.PersonActions.personSubmitAndVerify;

public class PersonSpecialPositionAddModal extends AbstractStartWebDriver {
    public PersonSpecialPositionAddModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);

    }

    @FindBy(css = ".auPersonnelSpecialPositionAddPanel .auHeaderTitle")
    WebElement PanelHeader;
    @FindBy(css = ".auPersonnelSpecialPositionAddPanel .auHeaderName")
    WebElement PersonCardName;
    @FindBy(css = ".auPersonnelSpecialPositionAddPanel .ui-select-toggle")
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

    //=======================================================================
    public PersonSpecialPositionAddModal setSpecialPosition(String specialPosition) {
        // Reporter.log("Setting Special Position....." + specialPosition, true);
        if (specialPosition != null) {
            waitForElementThenDo(wDriver, SpecialPosition).click();
            moveToAnElement(wDriver, wDriver.findElement(By.partialLinkText(specialPosition)));
            waitForElementThenDo(wDriver, wDriver.findElement(By.partialLinkText(specialPosition))).click();
            logTestPass(wDriver,   "Set Special Position: " + specialPosition);
        } else {
            // Reporter.log("Data is NULL!");
        }
        return new PersonSpecialPositionAddModal(wDriver);
    }

    public PersonSpecialPositionAddModal setStartDate(String startDate) throws IOException {
        // Reporter.log("Entering Start Date....." + startDate, true);
        if (startDate != null) {
            setDatePropertyTrue("startDate");
            waitForElementThenDo(wDriver, StartDate).sendKeys(startDate);
            logTestPass(wDriver,   "Entering Start Date: " + startDate);
        }
        return new PersonSpecialPositionAddModal(wDriver);
    }

    public PersonSpecialPositionAddModal setEndDate(String endDate) throws IOException {
        // Reporter.log("Entering End Date....." + endDate, true);
        if (endDate != null) {
            setDatePropertyTrue("endDate");
            waitForElementThenDo(wDriver, EndDate).sendKeys(endDate);
            logTestPass(wDriver,   "Entering End Date: " + endDate);
        }
        return new PersonSpecialPositionAddModal(wDriver);
    }

    public PersonSpecialPositionAddModal setRemarks(String remarks) {
        // Reporter.log("Entering Remarks....." + remarks, true);
        if (remarks != null) {
            waitForElementThenDo(wDriver, Remarks).sendKeys(remarks);
            logTestPass(wDriver, "Entered Remarks: " + remarks);
        }

        return new PersonSpecialPositionAddModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        // Reporter.log("Clicking SpecialPosition Submit Button.....", true);
        personSubmitAndVerify(wDriver, Submit);
        return new PersonDetailPage(wDriver);

    }


}




























