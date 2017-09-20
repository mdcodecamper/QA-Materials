package equipment.equipmentDetach.actions;

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
import static equipment.abstractBase.EquipmentBasePage.equipmentDetachModal;

/**
 * Created by sdas on 10/3/2016.
 */
public class DetachActions extends AbstractStartWebDriver {

    ExtentTest extentTest;
    public DetachActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    public void detachEquipment(String equipmentId, String toLocation, String detachHour, String detachMinute, String driver) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {

            //Reporter.log("Navigating to Card Detail Panel...", true);

            logTestInfo(wDriver, "Navigating to Card Detail Panel...");
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            //Reporter.log("Navigating to Detach Modal Window...", true);
            logTestInfo(wDriver, "Navigating to Detach Modal Window...");
            equipmentDetailPanelPage().getDetachAddPanel();
            logTestInfo(wDriver, "Entering Values into Detach Equipment Modal...");
            //Reporter.log("Entering Values into Detach Equipment Modal...", true);
            equipmentDetachModal()
                    .setToLocation(toLocation)
                    .setDetachHour(detachHour)
                    .setDetachMinute(detachMinute)
                    .setDriver(driver);
            equipmentDetachModal().clickSubmit();

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Detach modal window not found");
            Assert.fail();
        }

    }


    public void detachEquipmentRightClick(String equipmentId, String toLocation, String detachHour, String detachMinute, String driver) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {

            //Reporter.log("Navigating to Card Detail Panel...", true);

            logTestInfo(wDriver, "Performing right click operation for Detach action...");
            CommonActions.rightClickOnCard( "Equipment","Equipment",equipmentId,"Detach" );
            //Reporter.log("Navigating to Detach Modal Window...", true);
            logTestInfo(wDriver, "Navigating to Detach Modal Window...");
            logTestInfo(wDriver, "Entering Values into Detach Equipment Modal...");
            //Reporter.log("Entering Values into Detach Equipment Modal...", true);
            equipmentDetachModal()
                    .setToLocation(toLocation)
                    .setDetachHour(detachHour)
                    .setDetachMinute(detachMinute)
                    .setDriver(driver);
            equipmentDetachModal().clickSubmit();

            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(wDriver.findElements( By.cssSelector(".detailspane equipment > equipment-details")).size() > 0) {
                logTestFailure(wDriver,"Equipment details panel appeared after performing Detach action from Right Click menu");
            } else {
                logTestPass(wDriver,"Equipment details panel didn't appear after performing Detach action from Right Click menu as expected");
            }

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Detach modal window not found");
            Assert.fail();
        }

    }

    public void detachEquipment_Negative(String equipmentId, String toLocation, String detachHour, String detachMinute, String driver) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {

            //Reporter.log("Navigating to Card Detail Panel...", true);

            logTestInfo(wDriver, "Navigating to Card Detail Panel...");
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            //Reporter.log("Navigating to Detach Modal Window...", true);
            logTestInfo(wDriver, "Navigating to Detach Modal Window...");
            equipmentDetailPanelPage().getDetachAddPanel();
            logTestInfo(wDriver, "Entering Values into Detach Equipment Modal...");
            //Reporter.log("Entering Values into Detach Equipment Modal...", true);
            equipmentDetachModal()
                    .setToLocation(toLocation)
                    .setDetachHour(detachHour)
                    .setDetachMinute(detachMinute)
                    .setDriver(driver);
            equipmentDetachModal().clickSubmit_Negative();

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Detach modal window not found");
            Assert.fail();
        }

    }


}
