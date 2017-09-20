package equipment.equipmentDetach.modals;


import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.pages.EquipmentDetailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static common.actions.EquipmentActions.equipmentSubmitAndVerify;
import static common.actions.EquipmentActions.equipmentSubmitAndVerify_Negative;

/**
 * Created by sdas on 10/3/2016.
 */
public class EquipmentDetachModal extends AbstractStartWebDriver {

    ExtentTest extentTest;
    public EquipmentDetachModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    @FindBy(css = ".auEquipmentDetachmentPanel .form-title")
    @CacheLookup public static WebElement PanelHeader;
    @FindBy(css = "auEquipmentDetachmentPanel .form-equipment-name")
    @CacheLookup public static WebElement EquipmentId;
    @FindBy(css = "#to")
    @CacheLookup public static WebElement ToLocation;
    @FindBy(xpath = "//input[contains(@class,'auHour')]")
    @CacheLookup public static WebElement DetachHour;
    @FindBy(xpath = "//input[contains(@class,'auMinute')]")
    @CacheLookup public static WebElement DetachMinute;
    @FindBy(css = "#driver")
    @CacheLookup public static WebElement Driver;

    @FindBy(css = "button.btn.btn-primary.auSubmit")
    @CacheLookup public static WebElement Submit;
    @FindBy(css = "button.btn.btn-secondary.auCancel")
    @CacheLookup public static WebElement Cancel;


    public EquipmentDetachModal setToLocation(String toLocation) throws IOException {
        Reporter.log("Setting Location....." + toLocation, true);
        if (toLocation != null) {
                // DetachType.click();
                logTestInfo(wDriver, "Detach to location " + toLocation + " is selected");
                Utilities.clickThenSelectDropDownByText(waitForElementThenDo(wDriver, ToLocation), toLocation);
        }
        return new EquipmentDetachModal(wDriver);
    }

    public EquipmentDetachModal setDetachHour(String detachHour) throws IOException {
        Reporter.log("Entering Detach Hour....." + detachHour, true);
        if (detachHour != null) {
                logTestInfo(wDriver, "Detach Hour is set to " + detachHour);
                DetachHour.clear();
                waitForElementThenDo(wDriver, DetachHour).sendKeys(detachHour);
        }
        return new EquipmentDetachModal(wDriver);
    }

    public EquipmentDetachModal setDetachMinute(String detachMinute) throws IOException {
        Reporter.log("Entering Detach Minute....." + detachMinute, true);
        if (detachMinute != null) {
                logTestInfo(wDriver, "Detach Minute is set to " + detachMinute);
                DetachMinute.clear();
                waitForElementThenDo(wDriver, DetachMinute).sendKeys(detachMinute);
        }
        return new EquipmentDetachModal(wDriver);
    }

    public EquipmentDetachModal setDriver(String driver) throws IOException {
        Reporter.log("Entering Driver....." + driver, true);
        if (driver != null) {
                logTestInfo(wDriver, "Driver is set to " + driver);
                waitForElementThenDo(wDriver, Driver, 10).sendKeys(driver);
        }
        return new EquipmentDetachModal(wDriver);
    }

    public EquipmentDetailPage clickSubmit() throws InterruptedException, IOException {
        wDriver.findElement( By.cssSelector(".modal-header.auHeader")).click();
        logTestInfo(wDriver, "Clicking Detach Equipment Submit Button.....");
        equipmentSubmitAndVerify( wDriver,Submit,Cancel);

        return new EquipmentDetailPage(wDriver);

    }

    public EquipmentDetailPage clickSubmit_Negative() throws InterruptedException, IOException {
        wDriver.findElement( By.cssSelector(".modal-header.auHeader")).click();
        logTestInfo(wDriver, "Clicking Detach Equipment Submit Button.....");
        equipmentSubmitAndVerify_Negative( wDriver,Submit,Cancel);

        return new EquipmentDetailPage(wDriver);

    }



}
