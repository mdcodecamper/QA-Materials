package equipmentTestSuites.multiActions;


import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.closeDetailPanel;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 10/4/2016.
 */

//@Listeners(TestNGTestListener.class)
public class TEST001_Down_Up_RightClick_DetailPanel extends AbstractStartWebDriver {


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


    @Test(description = "Equipment Up")
    public void equipmentDown_Up_RightClick_DetailsPanel() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","RearLoaders", 5);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Down & Up Equipment by Right Click" );
        logTestInfo(wDriver, "Equipment Panel shouldn't appear after performing down/up action by right click menu" );

        //line below will open sending location board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Available", "RearLoaders", "Down" );
        System.out.println( "Equipment Id is " + equipmentId );
        //line below will down an equipment
        equipmentDownActions().downEquipmentRightClick( "Equipment", equipmentId, "DN87", "MN01", Utilities.get24HourFormat( -1 ), "00", "reporter01", "mechanic01", "remarks01", "DN39", "MN01", Utilities.get24HourFormat( -1 ) , "00", "reporter02", "mechanic02", "remarks02", null, null, null, null, null, null, null );
        closeDetailPanel( "Equipment" );
        equipmentUpActions().upEquipmentRightClick("Equipment",equipmentId, mechanic, reporter, upHour, upMinute );

    }

    }



