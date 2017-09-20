package equipment.equipmentUp.actions;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.CommonActions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.closeDetailPanel;
import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.equipmentUpModal;


/**
 * Created by ccollapally on 10/5/2016.
 */
public class UpActions extends AbstractStartWebDriver {

    ExtentTest extentTest;
    public UpActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    public void upEquipment(String equipmentId, String mechanic, String reporter, String upHour, String upMinute) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo( wDriver, "Navigating to Card Detail Panel...");
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            logTestInfo( wDriver, "Navigating to Up Modal Window...");
            equipmentDetailPanelPage().getUpPanel();
            logTestInfo( wDriver, "Entering Values into Up Equipment Modal...");
            equipmentUpModal()
                    .setMechanic(mechanic)
                    .setReporter(reporter)
                    .setUpHour(upHour)
                    .setUpMinute(upMinute);
            equipmentUpModal().clickSubmit();
            equipmentUpModal().upCondition();

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Up modal window not found");
            getScreenShot(wDriver);
            Assert.fail();
        }

    }


    public void upEquipmentRightClick(String panelName,String equipmentId, String mechanic, String reporter, String upHour, String upMinute) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo( wDriver, "Right Click on Up Action and Navigating to Up Modal Window...");
            CommonActions.rightClickOnCard(panelName,"Equipment",equipmentId,"Up");
            logTestInfo( wDriver, "Entering Values into Up Equipment Modal...");
            equipmentUpModal()
                    .setMechanic(mechanic)
                    .setReporter(reporter)
                    .setUpHour(upHour)
                    .setUpMinute(upMinute);
            equipmentUpModal().clickSubmit();
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(wDriver.findElements( By.cssSelector(".detailspane equipment > equipment-details")).size() > 0) {
                logTestFailure(wDriver,"Equipment details panel appeared after perfroming Up action from Right Click menu");
            }
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            equipmentUpModal().upCondition();
            closeDetailPanel("Equipment");

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Up modal window not found");
            getScreenShot(wDriver);
            Assert.fail();
        }

    }

    public void upEquipment_Negative(String equipmentId, String mechanic, String reporter, String upHour, String upMinute) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo( wDriver, "Navigating to Card Detail Panel...");
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            logTestInfo( wDriver, "Navigating to Up Modal Window...");
            equipmentDetailPanelPage().getUpPanel();
            logTestInfo( wDriver, "Entering Values into Up Equipment Modal...");
            equipmentUpModal()
                    .setMechanic(mechanic)
                    .setReporter(reporter)
                    .setUpHour(upHour)
                    .setUpMinute(upMinute);
            equipmentUpModal().clickSubmit_Negative();

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Up modal window not found");
            getScreenShot(wDriver);
            Assert.fail();
        }

    }

}
