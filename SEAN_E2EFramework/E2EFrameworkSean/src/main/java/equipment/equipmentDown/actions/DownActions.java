package equipment.equipmentDown.actions;

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
import static equipment.abstractBase.EquipmentBasePage.equipmentDownModal;
import static equipment.abstractBase.EquipmentBasePage.equipmentUpModal;

/**
 * Created by skashem on 10/12/2016.
 */
public class DownActions extends AbstractStartWebDriver{

    ExtentTest extentTest;
    public DownActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    public void downEquipment(String equipmentId, String downCode1, String seriveLocation1, String downHour1, String downMinute1, String reporter1, String mechanic1, String remarks1, String downCode2, String seriveLocation2, String downHour2, String downMinute2, String reporter2, String mechanic2, String remarks2, String downCode3, String seriveLocation3, String downHour3, String downMinute3, String reporter3, String mechanic3, String remarks3) throws IOException {
        Reporter.log( "==================================================================", true );
        Reporter.log( "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true );

        try {
            logTestInfo( wDriver, "Navigating to Card Detail Panel..." );
            smartBoardPage().openEquipmentCardDetailPanel( equipmentId );
            logTestInfo( wDriver, "Navigating to Down Modal Window..." );
            //CommonActions.rightClickOnCard("Equipment","Equipment",equipmentId,"Down");
            equipmentDetailPanelPage().getDownPanel();
            logTestInfo( wDriver, "Entering Values into Down Equipment Modal..." );
            equipmentDownModal()
                    .setDownCode1( downCode1 )
                    .setRepairLocation1( seriveLocation1 )
                    .setDownHour1( downHour1 )
                    .setDownMinute1( downMinute1 )
                    .setReporter1( reporter1 )
                    .setMechanic1( mechanic1 )
                    .setRemarks1( remarks1 )
                    .setDownCode2( downCode2 )
                    .setRepairLocation2( seriveLocation2 )
                    .setDownHour2( downHour2 )
                    .setDownMinute2( downMinute2 )
                    .setReporter2( reporter2 )
                    .setMechanic2( mechanic2 )
                    .setRemarks2( remarks2 )
                    .setDownCode3( downCode3 )
                    .setRepairLocation3( seriveLocation3 )
                    .setDownHour3( downHour3 )
                    .setDownMinute3( downMinute3 )
                    .setReporter3( reporter3 )
                    .setMechanic3( mechanic3 )
                    .setRemarks3( remarks3 );
            equipmentDownModal().clickSubmit();
            equipmentDownModal().downCondition( equipmentId );
/*            equipmentDownModal().lastUpdate();
            equipmentDownModal().lastUpdatedBy();*/

        } catch (Exception e) {
            logTestFailure( wDriver, "Equipment Down modal window not found" );
            Assert.fail();
        }
    }

    public void downEquipmentRightClick(String panelName,String equipmentId, String downCode1, String seriveLocation1, String downHour1, String downMinute1, String reporter1, String mechanic1, String remarks1, String downCode2, String seriveLocation2, String downHour2, String downMinute2, String reporter2, String mechanic2, String remarks2, String downCode3, String seriveLocation3, String downHour3, String downMinute3, String reporter3, String mechanic3, String remarks3) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo( wDriver, "Right Click on Down Action and Navigating to Down Modal Window...");
            CommonActions.rightClickOnCard(panelName,"Equipment",equipmentId,"Down");
            logTestInfo( wDriver, "Entering Values into Down Equipment Modal...");
            equipmentDownModal()
                    .setDownCode1(downCode1)
                    .setRepairLocation1(seriveLocation1)
                    .setDownHour1(downHour1)
                    .setDownMinute1(downMinute1)
                    .setReporter1(reporter1)
                    .setMechanic1(mechanic1)
                    .setRemarks1(remarks1)
                    .setDownCode2(downCode2)
                    .setRepairLocation2(seriveLocation2)
                    .setDownHour2(downHour2)
                    .setDownMinute2(downMinute2)
                    .setReporter2(reporter2)
                    .setMechanic2(mechanic2)
                    .setRemarks2(remarks2)
                    .setDownCode3(downCode3)
                    .setRepairLocation3(seriveLocation3)
                    .setDownHour3(downHour3)
                    .setDownMinute3(downMinute3)
                    .setReporter3(reporter3)
                    .setMechanic3(mechanic3)
                    .setRemarks3(remarks3);
            equipmentDownModal().clickSubmit();
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(wDriver.findElements( By.cssSelector(".detailspane equipment > equipment-details")).size() > 0) {
                logTestFailure(wDriver,"Equipment details panel appeared after perfroming Down action from Right Click menu");
            } else {
                logTestPass(wDriver,"Equipment details panel didn't appear after perfroming Down action from Right Click menu");

            }

            equipmentDownModal().downCondition(equipmentId);
/*            equipmentDownModal().lastUpdate();
            equipmentDownModal().lastUpdatedBy();*/


        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Down modal window not found");
            Assert.fail();
        }

    }

    public void downEquipment_Negative(String equipmentId, String downCode1, String seriveLocation1, String downHour1, String downMinute1, String reporter1, String mechanic1, String remarks1, String downCode2, String seriveLocation2, String downHour2, String downMinute2, String reporter2, String mechanic2, String remarks2, String downCode3, String seriveLocation3, String downHour3, String downMinute3, String reporter3, String mechanic3, String remarks3) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo( wDriver, "Navigating to Card Detail Panel...");
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            logTestInfo( wDriver, "Navigating to Down Modal Window...");
            //CommonActions.rightClickOnCard("Equipment","Equipment",equipmentId,"Down");
            equipmentDetailPanelPage().getDownPanel();
            logTestInfo( wDriver, "Entering Values into Down Equipment Modal...");
            equipmentDownModal()
                    .setDownCode1(downCode1)
                    .setRepairLocation1(seriveLocation1)
                    .setDownHour1(downHour1)
                    .setDownMinute1(downMinute1)
                    .setReporter1(reporter1)
                    .setMechanic1(mechanic1)
                    .setRemarks1(remarks1)
                    .setDownCode2(downCode2)
                    .setRepairLocation2(seriveLocation2)
                    .setDownHour2(downHour2)
                    .setDownMinute2(downMinute2)
                    .setReporter2(reporter2)
                    .setMechanic2(mechanic2)
                    .setRemarks2(remarks2)
                    .setDownCode3(downCode3)
                    .setRepairLocation3(seriveLocation3)
                    .setDownHour3(downHour3)
                    .setDownMinute3(downMinute3)
                    .setReporter3(reporter3)
                    .setMechanic3(mechanic3)
                    .setRemarks3(remarks3);
            equipmentDownModal().clickSubmit_Negative();
        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Down modal window not found");
            Assert.fail();
        }

    }

    public void editDownEquipment(String equipmentId, String downCode1, String seriveLocation1, String downHour1, String downMinute1, String reporter1, String mechanic1, String remarks1, String downCode2, String seriveLocation2, String downHour2, String downMinute2, String reporter2, String mechanic2, String remarks2, String downCode3, String seriveLocation3, String downHour3, String downMinute3, String reporter3, String mechanic3, String remarks3) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo( wDriver, "Navigating to Card Detail Panel...");
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            logTestInfo( wDriver, "Navigating to Down Modal Window...");
            equipmentDetailPanelPage().getEditDownPanel();
            logTestInfo( wDriver, "Entering Values into Down Equipment Modal...");
            equipmentDownModal()
                    .setDownCode1(downCode1)
                    .setRepairLocation1(seriveLocation1)
                    .setDownHour1(downHour1)
                    .setDownMinute1(downMinute1)
                    .setReporter1(reporter1)
                    .setMechanic1(mechanic1)
                    .setRemarks1(remarks1)
                    .setDownCode2(downCode2)
                    .setRepairLocation2(seriveLocation2)
                    .setDownHour2(downHour2)
                    .setDownMinute2(downMinute2)
                    .setReporter2(reporter2)
                    .setMechanic2(mechanic2)
                    .setRemarks2(remarks2)
                    .setDownCode3(downCode3)
                    .setRepairLocation3(seriveLocation3)
                    .setDownHour3(downHour3)
                    .setDownMinute3(downMinute3)
                    .setReporter3(reporter3)
                    .setMechanic3(mechanic3)
                    .setRemarks3(remarks3);
            equipmentDownModal().clickSubmit();
            equipmentDownModal().downCondition(equipmentId);

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Edit Down modal window not found");
            Assert.fail();
        }

    }

    public void editDownEquipmentRightClicki(String panelName, String equipmentId, String downCode1, String seriveLocation1, String downHour1, String downMinute1, String reporter1, String mechanic1, String remarks1, String downCode2, String seriveLocation2, String downHour2, String downMinute2, String reporter2, String mechanic2, String remarks2, String downCode3, String seriveLocation3, String downHour3, String downMinute3, String reporter3, String mechanic3, String remarks3) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            //logTestInfo( wDriver, "Navigating to Card Detail Panel...");
            //smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            logTestInfo( wDriver, "Right Click on Edit Down Action and Navigating to Edit Down Modal Window...");
            CommonActions.rightClickOnCard(panelName,"Equipment",equipmentId,"Edit Down");
            logTestInfo( wDriver, "Entering Values into Down Equipment Modal...");
            equipmentDownModal()
                    .setDownCode1(downCode1)
                    .setRepairLocation1(seriveLocation1)
                    .setDownHour1(downHour1)
                    .setDownMinute1(downMinute1)
                    .setReporter1(reporter1)
                    .setMechanic1(mechanic1)
                    .setRemarks1(remarks1)
                    .setDownCode2(downCode2)
                    .setRepairLocation2(seriveLocation2)
                    .setDownHour2(downHour2)
                    .setDownMinute2(downMinute2)
                    .setReporter2(reporter2)
                    .setMechanic2(mechanic2)
                    .setRemarks2(remarks2)
                    .setDownCode3(downCode3)
                    .setRepairLocation3(seriveLocation3)
                    .setDownHour3(downHour3)
                    .setDownMinute3(downMinute3)
                    .setReporter3(reporter3)
                    .setMechanic3(mechanic3)
                    .setRemarks3(remarks3);
            equipmentDownModal().clickSubmit();
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(wDriver.findElements( By.cssSelector(".detailspane equipment > equipment-details")).size() > 0) {
                logTestFailure(wDriver,"Equipment details panel appeared after perfroming Down action from Right Click menu");
            } else {
                logTestPass(wDriver,"Equipment details panel didn't appear after perfroming Edit Down action from Right Click menu");

            }

            equipmentDownModal().downCondition(equipmentId);

        } catch (Exception e) {
            logTestFailure(wDriver, "Equipment Edit Down modal window not found");
            Assert.fail();
        }

    }



}
