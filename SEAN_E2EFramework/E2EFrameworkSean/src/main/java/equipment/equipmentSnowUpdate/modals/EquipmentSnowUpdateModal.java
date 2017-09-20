package equipment.equipmentSnowUpdate.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.pages.EquipmentDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestInfo;
import static common.actions.EquipmentActions.equipmentSubmitAndVerify;

/**
 * Created by skashem on 10/20/2016.
 */
public class EquipmentSnowUpdateModal extends AbstractStartWebDriver {
    ExtentTest extentTest;
    public EquipmentSnowUpdateModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    @FindBy(css = ".modal-header.auHeader .form-title")
    @CacheLookup public static WebElement PanelHeader;
    @FindBy(css = ".modal-header.auHeader .form-equipment-name")
    @CacheLookup public static WebElement EquipmentId;
    @FindBy(xpath = "//*[contains(@class,'auPlowType')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup public static WebElement PlowType;
    @FindBy(xpath = "//*[contains(@class,'auPlowType')]//*[contains(@class,'search auText')]")
    @CacheLookup public static WebElement PlowTypeSearch;
    @FindBy(xpath = "//*[contains(@class,'auplowDirections')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup public static WebElement PlowDirections;
    @FindBy(xpath = "//*[contains(@class,'auplowDirections')]//*[contains(@class,'search auText')]")
    @CacheLookup public static WebElement PlowDirectionsSearch;
    @FindBy(xpath = "//*[contains(@class,'auChain')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup public static WebElement Chain;
    @FindBy(xpath = "//*[contains(@class,'auChain')]//*[contains(@class,'search auText')]")
    @CacheLookup public static WebElement ChainSearch;
    @FindBy(xpath = "//*[contains(@class,'auLoad')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup public static WebElement Load;
    @FindBy(xpath = "//*[contains(@class,'auLoad')]//*[contains(@class,'search auText')]")
    @CacheLookup public static WebElement LoadSearch;
    @FindBy(xpath = "//*[contains(@class,'auWorkingDown')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup public static WebElement SnowAvailability;
    @FindBy(xpath = "//*[contains(@class,'auWorkingDown')]//*[contains(@class,'search auText')]")
    @CacheLookup public static WebElement SnowAvailabilitySearch;
    @FindBy(xpath = "//*[contains(@class,'auSnowAssignment')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup public static WebElement SnowAssign;
    @FindBy(xpath = "//*[contains(@class,'auSnowAssignment')]//*[contains(@class,'search auText')]")
    @CacheLookup public static WebElement SnowAssignSearch;
    @FindAll(@FindBy(css = "ng-select > div > ul"))
    @CacheLookup public static List<WebElement> ElementClick;
    @FindBy(css = "button.btn.btn-primary.auSubmit")
    @CacheLookup public static WebElement Submit;
    @FindBy(css = "button.btn.btn-secondary.auCancel")
    @CacheLookup public static WebElement Cancel;


    public EquipmentSnowUpdateModal setPlowType(String plowType) {
        Reporter.log("Entering Plow Type....." + plowType, true);
        if (plowType != null) {
            logTestInfo( wDriver, "Plow Type is set to " + plowType);
            PlowType.click();
            PlowTypeSearch.sendKeys(plowType);
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(ElementClick.size() > 0) {
                ElementClick.get(0).click();
            }
        }
        return new EquipmentSnowUpdateModal(wDriver);
    }

    public EquipmentSnowUpdateModal setPlowDirection(String plowDirection) {
        Reporter.log("Entering Plow DIrections....." + plowDirection, true);
        if (plowDirection != null) {
            logTestInfo( wDriver, "Plow Directions is set to " + plowDirection);
            PlowDirections.click();
            PlowDirectionsSearch.sendKeys(plowDirection);
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(ElementClick.size() > 0) {
                ElementClick.get(0).click();
            }
        }
        return new EquipmentSnowUpdateModal(wDriver);
    }

    public EquipmentSnowUpdateModal setChain(String chain) {
        Reporter.log("Entering Chain....." + chain, true);
        if (chain != null) {
            logTestInfo( wDriver, "Chain is set to " + chain);
            Chain.click();
            ChainSearch.sendKeys(chain);
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(ElementClick.size() > 0) {
                ElementClick.get(0).click();
            }
        }
        return new EquipmentSnowUpdateModal(wDriver);
    }

    public EquipmentSnowUpdateModal setLoad(String load) {
        Reporter.log("Entering Load....." + load, true);
        if (load != null) {
            logTestInfo( wDriver, "Load is set to " + load);
            Load.click();
            LoadSearch.sendKeys(load);
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(ElementClick.size() > 0) {
                ElementClick.get(0).click();
            }
        }
        return new EquipmentSnowUpdateModal(wDriver);
    }

    public EquipmentSnowUpdateModal setSnowAvailable(String snowAvailability) {
        Reporter.log("Entering Snow Availability....." + snowAvailability, true);
        if (snowAvailability != null) {
            logTestInfo( wDriver, "Snow Availability is set to " + snowAvailability);
            SnowAvailability.click();
            SnowAvailabilitySearch.sendKeys(snowAvailability);
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(ElementClick.size() > 0) {
                ElementClick.get(0).click();
            }
        }
        return new EquipmentSnowUpdateModal(wDriver);
    }

    public EquipmentSnowUpdateModal setSnowAssignment(String snowAssign) {
        Reporter.log("Entering Snow Assignment....." + snowAssign, true);
        if (snowAssign != null) {
            logTestInfo( wDriver, "Snow Assignment is set to " + snowAssign);
            SnowAssign.click();
            SnowAssignSearch.sendKeys(snowAssign);
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(ElementClick.size() > 0) {
                ElementClick.get(0).click();
            }
        }
        return new EquipmentSnowUpdateModal(wDriver);
    }

    public EquipmentDetailPage clickSubmit() throws InterruptedException, IOException {
        logTestInfo( wDriver, "Clicking on Snow Update Submit Button.....");
        equipmentSubmitAndVerify( wDriver,Submit,Cancel);

        return new EquipmentDetailPage(wDriver);
    }


}
