package equipmentTestSuites.equipmentDown.Add;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.equipmentDownActions;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentUpActions;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/28/2016.
 */
public class TEST002_Down_and_Up_History extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String upDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exUpDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String upHour = Utilities.get24HourFormat(0);
    private String upMinute = "00";
    private String mechanic = "mechanic001";
    private String reporter = "reporter001";
    private String url = LoginData.getLoginData(getUrl());
    private String downCountBefore;
    private String rearLoadersCountBefore;
    private String availableCountBefore;


    @Test(description = "Equipment Down and Up history")
    public void equipmentDownUp_History() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "RearLoaders", 4 );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Regression", "Equipment Down and Up history" );
        logTestInfo( wDriver, "Scenario 2 - Ref#MINERVA-3768 - Equipment Down and Up history to verify 5 records appear per page" );

        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Available", "RearLoaders", "Down" );
        //line below will down an equipment
        equipmentDownActions().downEquipment( equipmentId, "DN87", "MN01", Utilities.get24HourFormat( -1 ), "00", "reporter01", "mechanic01", "remarks01", null, null, null, null, null, null, null, null, null, null, null, null, null, null );
        upHour = Utilities.get24HourFormat(0);
        //line below will up a equipment
        equipmentUpActions().upEquipment( equipmentId, mechanic, reporter, upHour, upMinute );
        //line below will down an equipment
        equipmentDownActions().downEquipment( equipmentId, "DN39", "MN02", Utilities.get24HourFormat( -1 ), "00", "reporter02", "mechanic02", "remarks02", "DN67", "MN02", Utilities.get24HourFormat( -1 ), "00", "reporter03", "mechanic03", "remarks03", null, null, null, null, null, null, null );
        upHour = Utilities.get24HourFormat(0);
        //line below will up a equipment
        equipmentUpActions().upEquipment( equipmentId, "mechanic02", "reporter02", upHour, upMinute );
        //line below will down an equipment
        equipmentDownActions().downEquipment( equipmentId, "D108", "MN05", Utilities.get24HourFormat(0), "00", "reporter04", "mechanic04", "remarks04", null, null, null, null, null, null, null, null, null, null, null, null, null, null );

        //line below will verify up/down history pagination
        CommonActions.paginationVerification("Equipment",equipmentId,"UpDown");


    }
















}
