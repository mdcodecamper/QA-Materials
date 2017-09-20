package equipment.equipmentCancelDetach.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.pages.EquipmentDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static common.actions.EquipmentActions.equipmentSubmitAndVerify;

/**
 * Created by skashem on 10/14/2016.
 */
public class EquipmentCancelDetachModal extends AbstractStartWebDriver {

    ExtentTest extentTest;
    public EquipmentCancelDetachModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    @FindBy(css = ".row.modal-header .form-title")
    @CacheLookup public static WebElement PanelHeader;
    @FindBy(css = ".row.modal-header .form-equipment-name")
    @CacheLookup public static WebElement EquipmentId;
    @FindBy(css = ".btn-primary.btn-submit.auSubmit")
    @CacheLookup public static WebElement Submit;
    @FindBy(css = ".btn-secondary.btn-cancel.auCancel")
    @CacheLookup public static WebElement Cancel;

    public EquipmentDetailPage clickSubmit() throws InterruptedException, IOException {
        logTestInfo( wDriver, "Clicking on Cancel Equipment Detachment Submit Button.....");
        equipmentSubmitAndVerify( wDriver,Submit,Cancel);

        return new EquipmentDetailPage(wDriver);

    }



}
