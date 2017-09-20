package equipmentTestSuites.equipmentDetach.Detach;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.getAnyCategoryCardsCount;
import static common.actions.CommonActions.getAnyCategoryCardsCountAfter;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 2/9/2017.
 */
public class TEST011_Minerva4545_04_With_Down_Condition extends AbstractStartWebDriver {


    /***************************************************************
     *Scenario 1 - Detach A Single Bin Equipment From Available Group
     ****************************************************************/
    //private WebDriver wdriver;
    //Test Data
    // private String url = LoginCredentials.url;
    //private String equipmentId = "25CW-857";
    private String location = "QW01";   //EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(-1, "yyyyMMdd");
    private String downDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDownDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String downCode1 = "DN87";
    private String serviceLocation1 = "MN02";
    private String downHour1 = Utilities.get24HourFormat(0);
    private String downMinute1 = "00";
    private String mechanic1 = "mechanic001";
    private String reporter1 = "reporter001";
    private String remarks1 = "remarks001";
    private String downCode2 = "DN39";
    private String serviceLocation2 = "MN02";
    private String downHour2 = Utilities.get24HourFormat(0);
    private String downMinute2 = "00";
    private String mechanic2 = "mechanic002";
    private String reporter2 = "reporter002";
    private String remarks2 = "remarks002";
    private String downCode3 = "DN59";
    private String serviceLocation3 = "MN02";
    private String downHour3 = Utilities.get24HourFormat(0);
    private String downMinute3 = "00";
    private String mechanic3 = "mechanic003";
    private String reporter3 = "reporter003";
    private String remarks3 = "remarks003";
    private String url = LoginData.getLoginData(getUrl());
    private String shiftStartHour = Utilities.get24HourFormat(-4);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(4);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String shift1 = "1200-2000";
    private String shift2 = "1300-2100";
    private String shift3 = "1400-2200";
    private String shift4 = "1500-2300";
    private String shift5 = "1600-0000";
    private String shift6 = "1700-0100";
    private String shift7 = "1800-0200";
    private String shift8 = "1900-0300";
    private String shift9 = "2000-0400";
    private String shift10 = "2100-0500";
    private String garage = null;
    private String equipmentId = null;
    private String toLocation = EquipmentData.equipmentReceivingLocation;
    private String exDetachDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");

    @Test(description = "Equipment Detach")
    public void equipmentDetach_Minerva4545_04_With_Down_Condition() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available", "RearLoaders", 1);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Smoke", "Detach Equipment" );
        System.out.println( "**************************************************************************************" );
        extentTest.log( LogStatus.INFO, "Detach a equipment which was initially down on an active task" );
        System.out.println( "**************************************************************************************" );
        //line below will open sending location board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );
        //line below will close personnel menu to expand equipment panel
        //CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        //CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will get card from shift 4 on Relays Collection
        equipmentId = taskModeUtilities().GetCardFromTask( null, "Equipment", shift5, "COLLECT", "RELAYS", 0 );
        System.out.println( "Equipment Id is " + equipmentId );
        //line below will update equipment to Empty before Down
        equipmentUpdateLoadActions().updateLoad( equipmentId, "Empty", null, "Empty", null );
        //line below will down a equipment
        equipmentDownActions().downEquipment( equipmentId, downCode1, serviceLocation1, downHour1, downMinute1, reporter1, mechanic1, remarks1, downCode2, serviceLocation2, downHour2, downMinute2, reporter2, mechanic2, remarks2, downCode3, serviceLocation3, downHour3, downMinute3, reporter3, mechanic3, remarks3 );

        //line below will get pending attach count before detach
        int pendingDetachBefore = getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);

        //line below will get down count before detach
        int downBefore = getAnyCategoryCardsCount("Equipment","Down",null,null);


        //line below will go to receving location board
        //loginPage().goToBoardLocationByDate(url, toLocation + "/", boardDate);

        //line below will get pending attach count before
        //int pendingAttachBefore = getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);


        //line below will go to sending location board
        //loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        String detachHour= Utilities.get24HourFormat(-1);
        equipmentDetachActions().detachEquipment(equipmentId,toLocation,detachHour,"00","Driver01");
        //line below will return down count after
        getAnyCategoryCardsCountAfter("-1",downBefore,"Equipment","Down",null,null);
        //line below will verify equipment doesn't appear on down
        equipmentPanelUtilities().verifyEquipmentPresent("Down", null, null, equipmentId, "false");
        //line below will verify equipment appears on pending detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "true");
        //line below will return pending detach count after
        getAnyCategoryCardsCountAfter("+1",pendingDetachBefore,"Equipment","Pending","PendingDetach",null);
        //line below will verify both equipment for shit 5 detachment history as initiated detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":00", location, toLocation, LoginData.getLoginData("username"), "Driver01", "");
        //line below will verify Eqiupment not present in Collect Relays Task for shift 5
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Equipment",equipmentId,shift5,"COLLECT","RELAYS",0);
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","limeGreen");
        //line below will check if equipment text color is as expected in task after down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present in task after down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E","E");

        //line below will open current day board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        //line below will verify equipment appears on pending detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "true");
        //line below will verify both equipment for shit 5 detachment history as initiated detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":00", location, toLocation, LoginData.getLoginData("username"), "Driver01", "");
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","limeGreen");
        //line below will check if equipment text color is as expected in task after down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present in task after down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E","E");



    }






    }
