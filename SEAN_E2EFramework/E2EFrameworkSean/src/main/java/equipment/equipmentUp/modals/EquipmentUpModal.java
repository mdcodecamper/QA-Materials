package equipment.equipmentUp.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.pages.EquipmentDetailPage;
import equipment.equipmentDown.modals.EquipmentDownModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.io.IOException;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;
import static common.actions.EquipmentActions.equipmentSubmitAndVerify;
import static common.actions.EquipmentActions.equipmentSubmitAndVerify_Negative;

/**
 * Created by ccollapally on 10/3/2016.
 */
public class EquipmentUpModal extends AbstractStartWebDriver {
    ExtentTest extentTest;
    public EquipmentUpModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    @FindBy(css = ".auEquipmentUpPanel .form-title")
    @CacheLookup public static WebElement PanelHeader;
    @FindBy(css = "auEquipmentUpPanel .form-equipment-name")
    @CacheLookup public static WebElement EquipmentId;
    @FindBy(xpath = "//input[contains(@placeholder,'Mechanic')]")
    @CacheLookup public static WebElement Mechanic;
    @FindBy(xpath = "//input[contains(@placeholder,'Reporter')]")
    @CacheLookup public static WebElement Report;
    @FindBy(xpath = "//input[contains(@class,'auHour')]")
    @CacheLookup public static WebElement UpHour;
    @FindBy(xpath = "//input[contains(@class,'auMinute')]")
    @CacheLookup public static WebElement UpMinute;
    @FindBy(css = "button.btn.btn-primary.auSubmit")
    @CacheLookup public static WebElement Submit;
    @FindBy(css = "button.btn.btn-secondary.auCancel")
    @CacheLookup public static WebElement Cancel;
    @FindBy(css = ".auConditionLabel")
    @CacheLookup public static WebElement ConditionLabel;
    @FindBy(css = ".auCondition.up")
    @CacheLookup public static WebElement ConditionValue;


    public EquipmentUpModal setMechanic(String mechanic) {
        Reporter.log("Entering Mechanic....." + mechanic, true);
        if (mechanic != null) {
            logTestInfo( wDriver, "Mechanic is set to " + mechanic);
            waitForElementThenDo(wDriver, Mechanic, 10).clear();
            waitForElementThenDo(wDriver, Mechanic).sendKeys(mechanic);
        }
        return new EquipmentUpModal(wDriver);
    }

    public EquipmentUpModal setReporter(String reporter) {
        Reporter.log("Entering Reporter....." + reporter, true);
        if (reporter != null) {
            logTestInfo( wDriver, "Reporter is set to " + reporter);
            waitForElementThenDo(wDriver, Report, 10).clear();
            waitForElementThenDo(wDriver, Report).sendKeys(reporter);
        }
        return new EquipmentUpModal(wDriver);
    }

    public EquipmentUpModal setUpHour(String upHour) throws InterruptedException {
        Reporter.log("Entering Up Hour....." + upHour, true);
        if (upHour != null) {
            logTestInfo( wDriver, "up hour is set to " + upHour);
            waitForElementThenDo(wDriver, UpHour, 10).clear();
            //Thread.sleep(200);
            waitForElementThenDo(wDriver, UpHour).sendKeys(upHour);
        }
        return new EquipmentUpModal(wDriver);
    }

    public EquipmentUpModal setUpMinute(String upMinute) throws InterruptedException {
        Reporter.log("Entering Up Minute....." + upMinute, true);
        if (upMinute != null) {
            logTestInfo( wDriver, "up minute is set to " + upMinute);
            waitForElementThenDo(wDriver, UpMinute, 10).clear();
            //Thread.sleep(200);
            waitForElementThenDo(wDriver, UpMinute).sendKeys(upMinute);
        }
        return new EquipmentUpModal(wDriver);
    }

    public EquipmentDetailPage clickSubmit() throws InterruptedException, IOException {
        wDriver.findElement(By.cssSelector(".modal-header.auHeader")).click();
        logTestInfo(wDriver, "Clicking Up Equipment Submit Button.....");
        equipmentSubmitAndVerify( wDriver,Submit,Cancel);

        return new EquipmentDetailPage(wDriver);

    }

    public EquipmentDetailPage clickSubmit_Negative() throws InterruptedException, IOException {
        wDriver.findElement(By.cssSelector(".modal-header.auHeader")).click();
        logTestInfo(wDriver, "Clicking Up Equipment Submit Button.....");
        equipmentSubmitAndVerify_Negative( wDriver,Submit,Cancel);

        return new EquipmentDetailPage(wDriver);

    }

    public EquipmentDownModal upCondition() {
        logTestInfo( wDriver, "Verifying Up Condition label and value" );
        String conditionLabel = ConditionLabel.getText();
        //System.out.println("down condition label " + conditionLabel);
        if (conditionLabel.equals( "Condition" )) {
            logTestPass( wDriver, "Condition label '" + conditionLabel + "' appears as Expected" );
        } else {
            logTestFailure( wDriver, "Condition label is inaccurate" );
        }
        String conditionValue = ConditionValue.getText();
        //System.out.println("down condition value " + conditionValue);
        if (conditionValue.equals( "Up" )) {
            logTestPass( wDriver, "Equipment Condition value 'Up' appears as Expected" );
        } else {
            logTestFailure( wDriver, "Equipment Condition value is inaccurate" );
        }
        return new EquipmentDownModal( wDriver );

    }

}
