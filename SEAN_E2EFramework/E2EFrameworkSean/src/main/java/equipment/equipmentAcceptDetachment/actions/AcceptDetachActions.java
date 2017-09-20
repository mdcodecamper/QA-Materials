package equipment.equipmentAcceptDetachment.actions;

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
import static equipment.abstractBase.EquipmentBasePage.equipmentAcceptModal;
import static equipment.abstractBase.EquipmentBasePage.equipmentCancelDetachModal;

/**
 * Created by skashem on 10/17/2016.
 */
public class AcceptDetachActions extends AbstractStartWebDriver {


    ExtentTest extentTest;
    public AcceptDetachActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }



    public void acceptEquipmentDetachment(String equipmentId,String receivedBy, String remarks, String receivingHour, String receivingMinute) throws IOException {
        Reporter.log( "==================================================================", true );
        Reporter.log( "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true );

        try {

            //Reporter.log("Navigating to Card Detail Panel...", true);

            logTestInfo( wDriver, "Navigating to Card Detail Panel..." );
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            //Reporter.log("Navigating to Detach Modal Window...", true);
            logTestInfo( wDriver, "Navigating to Accept Detach Modal Window..." );
            equipmentDetailPanelPage().getAcceptDetachPanel();
            //Reporter.log("Entering Values into Detach Equipment Modal...", true);
            equipmentAcceptModal()
                    .setReceiver(receivedBy)
                    .setRemarks(remarks)
                    .setHour( receivingHour )
                    .setMinute( receivingMinute );
            equipmentAcceptModal().clickSubmit();

        } catch (Exception e) {
            logTestFailure( wDriver, "Equipment Accept Detach modal window not found" );
            Assert.fail();
        }

    }


    public void acceptEquipmentDetachmentRightClick(String equipmentId,String receivedBy, String remarks, String receivingHour, String receivingMinute) throws IOException {
        Reporter.log( "==================================================================", true );
        Reporter.log( "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true );

        try {

            //Reporter.log("Navigating to Card Detail Panel...", true);

            logTestInfo( wDriver, "Performing right click operation on Accept Attach action..." );
            CommonActions.rightClickOnCard( "Equipment","Equipment",equipmentId,"Accept Attach" );
            //Reporter.log("Navigating to Detach Modal Window...", true);
            logTestInfo( wDriver, "Navigating to Accept Detach Modal Window..." );
            logTestInfo( wDriver, "Entering value into Detach Modal Window..." );
            //Reporter.log("Entering Values into Detach Equipment Modal...", true);
            equipmentAcceptModal()
                    .setReceiver(receivedBy)
                    .setRemarks(remarks)
                    .setHour( receivingHour )
                    .setMinute( receivingMinute );
            equipmentAcceptModal().clickSubmit();

            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(wDriver.findElements( By.cssSelector(".detailspane equipment > equipment-details")).size() > 0) {
                logTestFailure(wDriver,"Equipment details panel appeared after performing Accept Attach action from Right Click menu");
            } else {
                logTestPass(wDriver,"Equipment details panel didn't appear after performing Accept Attach action from Right Click menu as expected");
            }

        } catch (Exception e) {
            logTestFailure( wDriver, "Equipment Accept Detach modal window not found" );
            Assert.fail();
        }

    }
}
