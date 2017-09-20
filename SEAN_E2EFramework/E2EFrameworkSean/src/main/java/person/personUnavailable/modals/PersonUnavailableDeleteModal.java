package person.personUnavailable.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import common.pages.PersonDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static common.actions.PersonActions.personSubmitAndVerify;

/**
 * Created by srreddy on 10/19/2016.
 */
public class PersonUnavailableDeleteModal extends AbstractStartWebDriver {


    ExtentTest extentTest;

    public PersonUnavailableDeleteModal (WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    @FindBy(css = ".auPersonnelUnavailableRemovePanel .form-title")
    @CacheLookup public static WebElement PanelHeader;
    @FindBy(css = ".auPersonnelUnavailableRemovePanel .form-personnel-name")
    @CacheLookup public static WebElement PersonName;
    @FindBy(css = ".auPersonnelUnavailableRemovePanel .auRemarks")
    @CacheLookup public static WebElement Remarks;
    @FindBy(css = ".auPersonnelUnavailableRemovePanel .auError")
    @CacheLookup public static WebElement Error;
    @FindBy(css = ".auPersonnelUnavailableRemovePanel .auSubmit")
    @CacheLookup public static WebElement Submit;
    @FindBy(css = ".auPersonnelUnavailableRemovePanel .auCancel")
    @CacheLookup public static WebElement Cancel;

//    @FindBy(id = "comments")
//    @CacheLookup public static WebElement Remarks;
//    @FindBy(xpath = "//button[@type='submit']")
//    @CacheLookup public static WebElement Submit;
//    @FindBy(xpath = "//*[text()='Cancel']")
//    @CacheLookup public static WebElement Cancel;

    public PersonUnavailableDeleteModal setDeleteUnavailableReason(String reason) {
        if (reason != null) {
            logTestInfo(wDriver, "Entering Reason: " + reason);
            waitForElementThenDo(wDriver, Remarks).sendKeys(reason);
        }
        return new PersonUnavailableDeleteModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {

        logTestInfo(wDriver,"Clicking DELETE UNAVAILABLE Submit Button");
        personSubmitAndVerify(wDriver, Submit);
        return new PersonDetailPage(wDriver);

    }
}
