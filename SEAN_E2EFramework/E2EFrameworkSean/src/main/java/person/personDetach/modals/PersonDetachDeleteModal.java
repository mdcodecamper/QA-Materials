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


public class PersonDetachDeleteModal extends AbstractStartWebDriver {
    public PersonDetachDeleteModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);

    }


    //@FindBy(css = ".auPersonnelDetachmentPanel .auRemarks")
    @FindBy(id = "comment")
    WebElement Reason;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement Submit;
    @FindBy(xpath = "//*[text()='Cancel']")
    WebElement Cancel;
    //=======================================================================

    public PersonDetachDeleteModal setDetachDeleteReason(String reason) {
        Reporter.log("Entering Detach Reason....." + reason, true);
        if (reason != null) {
            waitForElementThenDo(wDriver, Reason).sendKeys(reason);
            //wDriver.findElement(By.id("comment")).sendKeys(reason);
          logTestPass(wDriver, "Entered: " + reason);
        }
        return new PersonDetachDeleteModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        personSubmitAndVerify(wDriver, Submit);
        //logTestPass(wDriver, "Clicked ADD DETACH Submit Button");
        return new PersonDetailPage(wDriver);
    }


}



























