package equipment.equipmentUpdateLoad.actions;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.utils.ExceptionUtil;
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
import static equipment.abstractBase.EquipmentBasePage.equipmentUpModal;
import static equipment.abstractBase.EquipmentBasePage.equipmentUpdateLoadModal;

/**
 * Created by skashem on 10/14/2016.
 */
public class UpdateLoadActions extends AbstractStartWebDriver {

    ExtentTest extentTest;
    public UpdateLoadActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }


    public void updateLoad(String equipmentId, String newStatus1, String material1, String newStatus2, String material2) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        //wDriver.manage().timeouts().implicitlyWait( 2, TimeUnit.SECONDS );
        try {
            logTestInfo( wDriver, "Navigating to Card Detail Panel...");
            executorClick(wDriver,waitForElementThenDo(wDriver,wDriver.findElement( By.xpath("//*[contains(text(),'"+ equipmentId +"')]")),10));
            //smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            logTestInfo( wDriver, "Navigating to Update Load Modal Window...");
            equipmentDetailPanelPage().getUpdateLoadPanel();
            logTestInfo( wDriver, "Entering Values into Update Load Modal...");
            equipmentUpdateLoadModal()
                    .setNewStatus1(newStatus1)
                    .setMaterial1(material1)
                    .setNewStatus2(newStatus2)
                    .setMaterial2(material2);
            equipmentUpdateLoadModal().clickSubmit();

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Update Load modal window not found");
            Assert.fail();
        }

    }

    public void updateLoadRightClick(String panelName,String equipmentId, String newStatus1, String material1, String newStatus2, String material2) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        //wDriver.manage().timeouts().implicitlyWait( 2, TimeUnit.SECONDS );
        try {
            logTestInfo( wDriver, "Navigating to Update Load Modal Window...");
            waitForElement( wDriver,wDriver.findElement(By.xpath("//*[contains(@data-id,'"+ equipmentId +"')]")), 10 );
            CommonActions.rightClickOnCard( panelName,"Equipment",equipmentId,"Update Load" );
            logTestInfo( wDriver, "Entering Values into Update Load Modal...");
            equipmentUpdateLoadModal()
                    .setNewStatus1(newStatus1)
                    .setMaterial1(material1)
                    .setNewStatus2(newStatus2)
                    .setMaterial2(material2);
            equipmentUpdateLoadModal().clickSubmit();

            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(wDriver.findElements( By.cssSelector(".detailspane equipment > equipment-details")).size() > 0) {
                logTestFailure(wDriver,"Equipment details panel appeared after performing Update Load action from Right Click menu");
            } else {
                logTestPass(wDriver,"Equipment details panel didn't appear after performing Update Load action from Right Click menu as expected");
            }


        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Update Load modal window not found");
            Assert.fail();
        }

    }


}
