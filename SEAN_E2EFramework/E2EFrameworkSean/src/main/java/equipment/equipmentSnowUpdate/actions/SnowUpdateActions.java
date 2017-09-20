package equipment.equipmentSnowUpdate.actions;

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

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;
import static equipment.abstractBase.EquipmentBasePage.equipmentSnowUpdateModal;
import static equipment.abstractBase.EquipmentBasePage.equipmentUpdateLoadModal;

/**
 * Created by skashem on 10/20/2016.
 */
public class SnowUpdateActions extends AbstractStartWebDriver {

    ExtentTest extentTest;
    public SnowUpdateActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }


    public void snowUpdate(String equipmentId, String plowType, String plowDirections, String chain, String load, String snowAvailability, String snowAssignment) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo( wDriver, "Navigating to Card Detail Panel...");
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            logTestInfo( wDriver, "Navigating to Snow Update Modal Window...");
            equipmentDetailPanelPage().getSnowUpdatePanel();
            logTestInfo( wDriver, "Entering Values into Snow Update Modal...");
            equipmentSnowUpdateModal()
                    .setPlowType(plowType)
                    .setPlowDirection(plowDirections)
                    .setChain(chain)
                    .setLoad(load)
                    .setSnowAvailable(snowAvailability)
                    .setSnowAssignment(snowAssignment);
            equipmentSnowUpdateModal().clickSubmit();

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Snow Update modal window not found");
            getScreenShot(wDriver);
            Assert.fail();
        }

    }

    public void snowUpdateRightClick(String panelName, String equipmentId, String plowType, String plowDirections, String chain, String load, String snowAvailability, String snowAssignment) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo( wDriver, "Navigating to Snow Update Modal Window...");
            CommonActions.rightClickOnCard( panelName,"Equipment",equipmentId,"Snow Update" );
            logTestInfo( wDriver, "Entering Values into Snow Update Modal...");
            equipmentSnowUpdateModal()
                    .setPlowType(plowType)
                    .setPlowDirection(plowDirections)
                    .setChain(chain)
                    .setLoad(load)
                    .setSnowAvailable(snowAvailability)
                    .setSnowAssignment(snowAssignment);
            equipmentSnowUpdateModal().clickSubmit();

            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(wDriver.findElements( By.cssSelector(".detailspane equipment > equipment-details")).size() > 0) {
                logTestFailure(wDriver,"Equipment details panel appeared after performing Snow Update action from Right Click menu");
            } else {
                logTestPass(wDriver,"Equipment details panel didn't appear after performing Snow Update action from Right Click menu as expected");
            }

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Snow Update modal window not found");
            getScreenShot(wDriver);
            Assert.fail();
        }

    }


}

