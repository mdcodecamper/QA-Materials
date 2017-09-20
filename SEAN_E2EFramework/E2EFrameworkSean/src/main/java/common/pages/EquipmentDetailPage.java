package common.pages;


import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import equipment.equipmentAcceptDetachment.modals.EquipmentAcceptDetachModal;
import equipment.equipmentCancelDetach.modals.EquipmentCancelDetachModal;
import equipment.equipmentDetach.modals.EquipmentDetachModal;
import equipment.equipmentDown.modals.EquipmentDownModal;
import equipment.equipmentSnowUpdate.modals.EquipmentSnowUpdateModal;
import equipment.equipmentUp.modals.EquipmentUpModal;
import equipment.equipmentUpdateLoad.modals.EquipmentUpdateLoadModal;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import _driverScript.AbstractStartWebDriver;
import org.testng.Reporter;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
//import utilities.Utilities;


/**
 * Created by sdas on 9/23/2016.
 */
public class EquipmentDetailPage extends AbstractStartWebDriver {
    ExtentTest extentTest;
    public EquipmentDetailPage(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    // LOCATORS
    @FindBy(css = ".auActionButton")
    WebElement ActionButton;
    @FindBy(css = ".auActionMenu .auActionDetach")
    WebElement AddDetach;
    @FindBy(css = ".auActionMenu .auActionCancelDetach")
    WebElement cancelDetach;
    @FindBy(css = ".auActionMenu .auActionUpdateLoad")
    WebElement UpdateLoad;
    @FindBy(css = ".auActionMenu .auActionDown")
    WebElement Down;
    @FindBy(css = ".auActionMenu .auActionEditDown")
    WebElement editDown;
    @FindBy(css = ".auActionMenu .auActionUp")
    WebElement Up;
    @FindBy(css = ".auActionMenu .auActionAcceptAttach")
    WebElement AcceptAttach;
    @FindBy(css = ".auActionMenu .auActionSnow")
    WebElement SnowUpdate;
    @FindBy(css = ".auEquipmentDetail .auActionClose")
    WebElement DetailClose;

    //======================================================================================
    public EquipmentDetachModal getDetachAddPanel() throws IOException {
        Reporter.log("Opening Detach Equipment Modal Window..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, ActionButton).click();
            Utilities.waitForElementThenDo(wDriver, AddDetach).click();

        } catch (Exception e) {
            extentTest.log(LogStatus.FAIL,"Action button is not found for Detach Equipment");
            getScreenShot(wDriver);
            e.toString();
            Assert.fail();
            //return null;
        }
        wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return new EquipmentDetachModal(wDriver);
    }

    public EquipmentCancelDetachModal getCancelDetachPanel() throws IOException {
        Reporter.log("Opening Cancel Equipment Detachment Modal Window..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, ActionButton).click();
            Utilities.waitForElementThenDo(wDriver, cancelDetach).click();

        } catch (Exception e) {
            extentTest.log(LogStatus.FAIL,"Action button is not found for Cancel Equipment Detachment");
            getScreenShot(wDriver);
            e.toString();
            Assert.fail();
            //return null;
        }
        wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return new EquipmentCancelDetachModal(wDriver);
    }

    public EquipmentSnowUpdateModal getSnowUpdatePanel() throws IOException {
        Reporter.log("Opening Snow Updatet Modal Window..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, ActionButton).click();
            Utilities.waitForElementThenDo(wDriver, SnowUpdate).click();

        } catch (Exception e) {
            extentTest.log(LogStatus.FAIL,"Action button is not found for Snow Update");
            getScreenShot(wDriver);
            e.toString();
            Assert.fail();
            //return null;
        }
        wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return new EquipmentSnowUpdateModal(wDriver);
    }

    public EquipmentAcceptDetachModal getAcceptDetachPanel() throws IOException {
        Reporter.log("Opening Accept Equipment Detachment Modal Window..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, ActionButton).click();
            Utilities.waitForElementThenDo(wDriver, AcceptAttach).click();

        } catch (Exception e) {
            extentTest.log(LogStatus.FAIL,"Action button is not found for Accept Equipment Detachment");
            getScreenShot(wDriver);
            e.toString();
            Assert.fail();
            //return null;
        }
        wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return new EquipmentAcceptDetachModal(wDriver);
    }

    public EquipmentUpModal getUpPanel() throws IOException {
        Reporter.log("Opening Up Equipment Modal Window..... ", true);
        try {

            Utilities.waitForElementThenDo(wDriver, ActionButton).click();
            Thread.sleep(1000);
            if(wDriver.findElements( By.cssSelector( ".auActionMenu .auActionUp" )).size() > 0 ) {
                Up.click();
            } else {
                logTestFailure( wDriver,"Up Link Text is not found on Action Menu dropdown" );
                Assert.fail();
            }
        } catch (Exception e) {
            logTestFailure(wDriver,"Action button is not found for Up Equipment");
            e.toString();
            Assert.fail();
            //return null;
        }
        wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return new EquipmentUpModal(wDriver);
    }
    public SmartBoardPage closeEquipmentCardDetailPanel() throws IOException {
        Reporter.log("Closing Detail Panel..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, DetailClose, 10).click();
        } catch (Exception e) {
            extentIconNotFound();
            e.toString();
            Assert.fail();
        }
        wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return new SmartBoardPage(wDriver);
    }

    public EquipmentDownModal getDownPanel() throws IOException {
        Reporter.log("Opening down Equipment Modal Window..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, ActionButton).click();
            Thread.sleep(1000);
            if(wDriver.findElements( By.cssSelector( ".auActionMenu .auActionDown" )).size() > 0 ) {
                Down.click();
            } else {
                logTestFailure( wDriver,"Down Link Text is not found on Action Menu dropdown" );
                Assert.fail();
            }

        } catch (Exception e) {
            logTestFailure(wDriver,"Action button is not found for Down Equipment");
            Assert.fail();
        }
        wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return new EquipmentDownModal(wDriver);
    }



    public EquipmentDownModal getEditDownPanel() throws IOException {
        Reporter.log("Opening down Equipment Modal Window..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, ActionButton).click();
            Thread.sleep(1000);
            if(wDriver.findElements( By.cssSelector( ".auActionMenu .auActionEditDown" )).size() > 0 ) {
                editDown.click();
            } else {
                logTestFailure( wDriver,"Edit Down Link Text is not found on Action Menu dropdown" );
                Assert.fail();
            }


        } catch (Exception e) {
            logTestFailure(wDriver,"Action button is not found for Edit Down Equipment");
            Assert.fail();
        }
        wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return new EquipmentDownModal(wDriver);
    }

    public EquipmentUpdateLoadModal getUpdateLoadPanel() throws IOException {
        Reporter.log("Opening Update Load Modal Window..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, ActionButton).click();
            Utilities.waitForElementThenDo(wDriver, UpdateLoad).click();

        } catch (Exception e) {
            extentTest.log(LogStatus.FAIL,"Action button is not found for Update Load");
            getScreenShot(wDriver);
            e.toString();
            Assert.fail();
            //return null;
        }
        wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return new EquipmentUpdateLoadModal(wDriver);
    }


    public void extentIconNotFound() throws IOException {
        getScreenShot(wDriver);
        extentTest.log(LogStatus.FAIL, "NO Clickable Icon is found at the location!!!");
        Reporter.log("NO Clickable Icon is found at the location!!!", true);
        Assert.fail();
    }
}
