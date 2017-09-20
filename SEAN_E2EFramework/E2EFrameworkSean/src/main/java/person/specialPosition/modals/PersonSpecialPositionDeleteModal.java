package person.specialPosition.modals;

import _driverScript.AbstractStartWebDriver;
import common.pages.PersonDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

import static common.actions.CommonActions.logTestPass;
import static common.actions.PersonActions.personSubmitAndVerify;

public class PersonSpecialPositionDeleteModal extends AbstractStartWebDriver {
    public PersonSpecialPositionDeleteModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);

    }


    @FindBy(css = ".auPersonnelSpecialPositionRemovePanel .auRemarks")
    WebElement Reason;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement Submit;
    @FindBy(xpath = "//*[text()='Cancel']")
    WebElement Cancel;
    //=======================================================================

    public PersonSpecialPositionDeleteModal setDeleteSpecialPositionReason(String reason) {
        if (reason != null) {
            waitForElementThenDo(wDriver, Reason).sendKeys(reason);
            logTestPass(wDriver,   "Entered Reason: " + reason);
        }
        return new PersonSpecialPositionDeleteModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        personSubmitAndVerify(wDriver, Submit);
        return new PersonDetailPage(wDriver);

    }



}



























