package person.personMda.modals;


import _driverScript.AbstractStartWebDriver;
import common.pages.PersonDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

import static common.actions.CommonActions.logTestPass;
import static common.actions.PersonActions.personSubmitAndVerify;


public class PersonMdaDeleteModal extends AbstractStartWebDriver {
    public PersonMdaDeleteModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);

    }


    @FindBy(id = "comments")
    @CacheLookup public static WebElement Reason;
    @FindBy(xpath = "//button[@type='submit']")
    @CacheLookup public static WebElement Submit;
    @FindBy(xpath = "//*[text()='Cancel']")
    @CacheLookup public static WebElement Cancel;
    //=======================================================================

    public PersonMdaDeleteModal setDeleteMdaReason(String reason) {
        if (reason != null) {
            waitForElementThenDo(wDriver, Reason).sendKeys(reason);
            logTestPass(wDriver,  "Entered End Mda Delete Reason: " + reason);
        }
        return new PersonMdaDeleteModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        personSubmitAndVerify(wDriver, Submit);
        return new PersonDetailPage(wDriver);

    }

}



























