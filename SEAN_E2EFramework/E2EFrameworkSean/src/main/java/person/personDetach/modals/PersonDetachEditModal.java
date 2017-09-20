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

public class PersonDetachEditModal extends AbstractStartWebDriver {
    public PersonDetachEditModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);

    }

    @FindBy(css = ".auPersonnelDetachmentPanel .form-title")
    static WebElement PanelHeader;
    @FindBy(css = "auPersonnelDetachmentPanel .form-personnel-name")
    static WebElement PersonCardName;
    @FindBy(css = "#endDate")
    static WebElement EndDate;
    @FindBy(css = ".auPersonnelDetachmentPanel .auRemarks")
    WebElement Remarks;
    @FindBy(css = ".auSubmit")
    WebElement Submit;
    @FindBy(xpath = "//*[text()='Cancel']")
    WebElement Cancel;

    public static WebElement getEndDate() {
        return EndDate;
    }

    // =======================================================================


    public PersonDetachEditModal setEndDate(String endDate) throws IOException {
        Reporter.log("Editing Detach End Date....." + endDate, true);
        if (endDate != null) {
            setDatePropertyTrue("endDate");
            waitForElementThenDo(wDriver, EndDate, 3).sendKeys(endDate);
            logTestPass(wDriver,  "Edited Detach End Date: " + endDate);
        }
        return new PersonDetachEditModal(wDriver);
    }

    public PersonDetachEditModal setRemarks(String remarks) {
        Reporter.log("Entering Detach Remarks....." + remarks, true);
        if (remarks != null) {
            waitForElementThenDo(wDriver, Remarks, 3).clear();
            waitForElementThenDo(wDriver, Remarks, 3).sendKeys(remarks);
            logTestPass(wDriver,  "Entered Detach Remarks: " + remarks);
        }
        return new PersonDetachEditModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        //logTestPass(wDriver,  "Clicked ADD DETACH Submit Button");
        personSubmitAndVerify(wDriver, Submit);
        return new PersonDetailPage(wDriver);

    }

}

