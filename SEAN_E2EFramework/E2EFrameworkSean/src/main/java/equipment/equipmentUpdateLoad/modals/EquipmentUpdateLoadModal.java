package equipment.equipmentUpdateLoad.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.pages.EquipmentDetailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestInfo;
import static common.actions.EquipmentActions.equipmentMouseClick;
import static common.actions.EquipmentActions.equipmentSubmitAndVerify;

/**
 * Created by skashem on 10/14/2016.
 */
public class EquipmentUpdateLoadModal extends AbstractStartWebDriver {
    ExtentTest extentTest;
    public EquipmentUpdateLoadModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }
    @FindBy(css = ".modal-header.auHeader .form-title")
    @CacheLookup public static WebElement PanelHeader;
    @FindBy(css = ".modal-header.auHeader .form-equipment-name")
    @CacheLookup public static WebElement EquipmentId;
    @FindAll(@FindBy(css = "div.ui-select-match > span"))
    @CacheLookup public static List<WebElement> DropDown;
    @FindBy(css = "ng-select > div > input")
    @CacheLookup public static WebElement SearchCodeField;
    @FindAll(@FindBy(css = "ng-select > div > ul"))
    @CacheLookup public static List <WebElement> ElementClick;
    @FindBy(css = ".btn-primary.auSubmit")
    @CacheLookup public static WebElement Submit;
    @FindBy(css = "button.btn.btn-secondary.auCancel")
    @CacheLookup public static WebElement Cancel;
    Actions mouseMethod = new Actions(wDriver);
    public EquipmentUpdateLoadModal setNewStatus1(String newStatus1) {
        Reporter.log("Entering new status 1....." + newStatus1, true);
        if (newStatus1 != null) {
            logTestInfo( wDriver, "New status 1 is set to " + newStatus1);
            //wDriver.findElement(By.cssSelector("fieldset:nth-child(1) > div:nth-child(4) > ng-select > div > div.ui-select-match > span")).click();

            //DropDown.get(0).click();

            //SearchCodeField.sendKeys(newStatus1);
           // ElementClick.get(0).click();
            mouseMethod.moveToElement(wDriver.findElement(By.cssSelector("fieldset:nth-child(1) > div:nth-child(4) > ng-select > div > div.ui-select-match > span"))).perform();
            mouseMethod.click().perform();
            mouseMethod.moveToElement(SearchCodeField).perform();
            mouseMethod.sendKeys( newStatus1 ).perform();
            mouseMethod.moveToElement(ElementClick.get(0)).perform();
            mouseMethod.click().perform();

        }
        return new EquipmentUpdateLoadModal(wDriver);
    }

    public EquipmentUpdateLoadModal setMaterial1(String material1) {
        Reporter.log("Entering material 1....." + material1, true);
        if (material1 != null) {
            logTestInfo( wDriver, "materail 1 is set to " + material1);
            //wDriver.findElement(By.cssSelector("fieldset:nth-child(1) > div:nth-child(5) > ng-select > div > div.ui-select-match > span")).click();
            //DropDown.get(1).click();
            //SearchCodeField.sendKeys(material1);
            //wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            //if(ElementClick.size() > 0) {
                //ElementClick.get(0).click();
            //}
            mouseMethod.moveToElement(wDriver.findElement(By.cssSelector("fieldset:nth-child(1) > div:nth-child(5) > ng-select > div > div.ui-select-match > span"))).perform();
            mouseMethod.click().perform();
            mouseMethod.moveToElement(SearchCodeField).perform();
            mouseMethod.sendKeys( material1 ).perform();
            mouseMethod.moveToElement(ElementClick.get(0)).perform();
            mouseMethod.click().perform();
        }
        return new EquipmentUpdateLoadModal(wDriver);
    }

    public EquipmentUpdateLoadModal setNewStatus2(String newStatus2) {
        Reporter.log("Entering new status 2....." + newStatus2, true);
        if (newStatus2 != null) {
            logTestInfo( wDriver, "New status 2 is set to " + newStatus2);
            //wDriver.findElement(By.cssSelector("fieldset:nth-child(2) > div:nth-child(4) > ng-select > div > div.ui-select-match > span")).click();
            //DropDown.get(2).click();
            //SearchCodeField.sendKeys(newStatus2);
           //wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            //if(ElementClick.size() > 0) {
                //ElementClick.get(0).click();
            //}
            mouseMethod.moveToElement(wDriver.findElement(By.cssSelector("fieldset:nth-child(2) > div:nth-child(4) > ng-select > div > div.ui-select-match > span"))).perform();
            mouseMethod.click().perform();
            mouseMethod.moveToElement(SearchCodeField).perform();
            mouseMethod.sendKeys( newStatus2 ).perform();
            mouseMethod.moveToElement(ElementClick.get(0)).perform();
            mouseMethod.click().perform();
        }
        return new EquipmentUpdateLoadModal(wDriver);
    }

    public EquipmentUpdateLoadModal setMaterial2(String material2) {
        Reporter.log("Entering material 2....." + material2, true);
        if (material2 != null) {
            logTestInfo( wDriver, "materail 2 is set to " + material2);
            //wDriver.findElement(By.cssSelector("fieldset:nth-child(2) > div:nth-child(5) > ng-select > div > div.ui-select-match > span")).click();
            //DropDown.get(3).click();
            //SearchCodeField.sendKeys(material2);
            //wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            //if(ElementClick.size() > 0) {
                //ElementClick.get(0).click();
            //}
            mouseMethod.moveToElement(wDriver.findElement(By.cssSelector("fieldset:nth-child(2) > div:nth-child(5) > ng-select > div > div.ui-select-match > span"))).perform();
            mouseMethod.click().perform();
            mouseMethod.moveToElement(SearchCodeField).perform();
            mouseMethod.sendKeys( material2 ).perform();
            mouseMethod.moveToElement(ElementClick.get(0)).perform();
            mouseMethod.click().perform();
        }
        return new EquipmentUpdateLoadModal(wDriver);
    }


    public EquipmentDetailPage clickSubmit() throws InterruptedException, IOException {
        logTestInfo( wDriver, "Clicking on Update Load Equipment Submit Button.....");
        equipmentMouseClick( wDriver,Submit,Cancel);

        return new EquipmentDetailPage(wDriver);
    }


}
