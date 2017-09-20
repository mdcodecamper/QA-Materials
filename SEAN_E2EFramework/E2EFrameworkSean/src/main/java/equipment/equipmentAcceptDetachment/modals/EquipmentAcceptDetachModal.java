package equipment.equipmentAcceptDetachment.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.pages.EquipmentDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static common.actions.EquipmentActions.equipmentSubmitAndVerify;

/**
 * Created by skashem on 10/17/2016.
 */
public class EquipmentAcceptDetachModal extends AbstractStartWebDriver {

    ExtentTest extentTest;
    public EquipmentAcceptDetachModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    @FindBy(css = ".row.modal-header .form-title")
    @CacheLookup
    public static WebElement PanelHeader;
    @FindBy(css = ".row.modal-header .form-equipment-name")
    @CacheLookup public static WebElement EquipmentId;
    @FindBy(css = "#receiver")
    @CacheLookup public static WebElement ReceivedBy;
    @FindBy(css = "#comments")
    @CacheLookup public static WebElement Remarks;
    @FindBy(xpath = "//*[contains(@class,'auHour')]")
    @CacheLookup public static WebElement ReceivingHour;
    @FindBy(xpath = "//*[contains(@class,'auMinute')]")
    @CacheLookup public static WebElement ReceivingMinute;
    @FindBy(css = ".btn-primary.btn-submit.auSubmit")
    @CacheLookup public static WebElement Submit;
    @FindBy(css = ".btn-secondary.btn-cancel.auCancel")
    @CacheLookup public static WebElement Cancel;

    public EquipmentAcceptDetachModal setReceiver(String receivedBy) {
        Reporter.log("Entering Receiver....." + receivedBy, true);
        if (receivedBy != null) {
            logTestInfo( wDriver, "Receiver is set to " + receivedBy);
            waitForElementThenDo(wDriver, ReceivedBy).clear();
            waitForElementThenDo(wDriver, ReceivedBy).sendKeys(receivedBy);
        }
        return new EquipmentAcceptDetachModal(wDriver);
    }

    public EquipmentAcceptDetachModal setRemarks(String remarks) {
        Reporter.log("Entering Remarks....." + remarks, true);
        if (remarks != null) {
            logTestInfo( wDriver, "Remarks is set to " + remarks);
            waitForElementThenDo(wDriver, Remarks).clear();
            waitForElementThenDo(wDriver, Remarks).sendKeys(remarks);
        }
        return new EquipmentAcceptDetachModal(wDriver);
    }

    public EquipmentAcceptDetachModal setHour(String hour) {
        Reporter.log("Entering Receiving Hour....." + hour, true);
        if (hour != null) {
            logTestInfo( wDriver, "Receiving Hour is set to " + hour);
            ReceivingHour.clear();
            waitForElementThenDo(wDriver, ReceivingHour).sendKeys(hour);
        }
        return new EquipmentAcceptDetachModal(wDriver);
    }

    public EquipmentAcceptDetachModal setMinute(String minute) {
        Reporter.log("Entering Receiving Minute....." + minute, true);
        if (minute != null) {
            logTestInfo( wDriver, "Receiving Minute is set to " + minute);
            ReceivingMinute.clear();
            waitForElementThenDo(wDriver, ReceivingMinute).sendKeys(minute);
        }
        return new EquipmentAcceptDetachModal(wDriver);
    }

    public EquipmentDetailPage clickSubmit() throws InterruptedException, IOException {
        logTestInfo( wDriver, "Clicking on Accept Equipment Detachment Submit Button.....");
        equipmentSubmitAndVerify( wDriver,Submit,Cancel);
        return new EquipmentDetailPage(wDriver);

    }
}
