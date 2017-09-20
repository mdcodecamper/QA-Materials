package person.personUnavailable.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;

/**
 * Created by srreddy on 10/19/2016.
 */
public class PersonPanelModal extends AbstractStartWebDriver {

    ExtentTest extentTest;

    public PersonPanelModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    @FindBy(css = ".CHART .cancelchartsbutton")
    @CacheLookup
    public static WebElement CancelCharts;
    @FindBy(css = ".CHART .cancelchartsbutton.confirm")
    @CacheLookup
    public static WebElement ConfirmCancelCharts;

    public static void clickCancelChart() {
        try {
            logTestInfo(wDriver, "Clicking on Cancel Charts button");
            waitForElementThenDo(wDriver, CancelCharts).click();
        } catch (Exception e) {
            logTestFailure(wDriver, "Error occured while clicking Cancel Charts button ");
            e.printStackTrace();
        }
    }

    public static void clickConfirmCancelChart() {
        try {
            logTestInfo(wDriver, "Clicking on CONFIRM Cancel Charts button");
            waitForElementThenDo(wDriver, ConfirmCancelCharts).click();
        } catch (Exception e) {
            logTestFailure(wDriver, "Error occured while clicking CONFIRM Cancel Charts button ");
            e.printStackTrace();
        }
    }

}
