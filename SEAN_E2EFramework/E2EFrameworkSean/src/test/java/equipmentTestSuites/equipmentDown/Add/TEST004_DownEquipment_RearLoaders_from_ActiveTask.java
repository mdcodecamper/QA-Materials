package equipmentTestSuites.equipmentDown.Add;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.closeDetailPanel;
import static common.actions.CommonActions.getAnyCategoryCardsCountAfter;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 10/12/2016.
 */
public class TEST004_DownEquipment_RearLoaders_from_ActiveTask extends AbstractStartWebDriver{


    /***************************************************************
     *Scenario 1 - Detach A Single Bin Equipment From Available Group
     ****************************************************************/
    //private WebDriver wdriver;
    //Test Data
    // private String url = LoginCredentials.url;
    //private String equipmentId = "25CW-857";
    private String location ="SI01";    // EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
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
    private String shiftStartHour = Utilities.get24HourFormat(-6);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(2);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String garage = null;
    private String shift1 = "1200-2000";
    private String shift2 = "1300-2100";
    private String shift3 = "1400-2200";
    private String shift4 = "1500-2300";
    private String shift5 = "1600-0000";
    private String shift6 = "1700-0100";
    private String shift7 = "1800-0200";
    private String shift8 = "1900-0300";

    @Test(description = "Equipment Down")
    public void equipmentDown_RearLoaders_ActiveTask_3Codes() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available", "RearLoaders", 1);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke","Down Equipment");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 4 - Down a equipment from an Active Task with 3 down codes");
        System.out.println("**************************************************************************************");
        extentTest.log( LogStatus.INFO, "Scenario 4 - Down a equipment from an Active Task with 3 down codes");

        //line below will go to sending location board on previous day board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(-1, "yyyyMMdd"));
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        CommonActions.clickOnMenuIcon(wDriver, "Equipment");
        //line below will get card from shift 2 on Relays Collection
        String equipmentId = taskModeUtilities().GetCardFromTask( null,"Equipment",shift1,"COLLECT","RELAYS",0);

        //line below will open today's board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        Thread.sleep( 2000 );
        equipmentUpdateLoadActions().updateLoad(equipmentId,"Empty",null,null,null);

        //line below will delete any shift that has task(s) before adding a new shift
        //TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will close task menu to expand equipment panel
       // CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        //String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available","RearLoaders","Down" );
       // String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available","RearLoaders","Down" );
        System.out.println("Equipment Id is " + equipmentId);

        //line below will re open task menu
       // CommonActions.clickOnMenuIcon( wDriver, "Task" );

        logTestInfo(wDriver, "verification started on location " + location + " before assigning equipment to task");
        //line below will return pending load category count before assigning equipment to task
        int pendingLoadCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending", "PendingLoad", null);
        //line below will return available count before assigning equipment to task
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available", null, null);
        //line below will return available rear loaders count before assigning equipment to task
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available", "RearLoaders", null);
        //line below will return down count before assigning equipment to task and before down
        int downCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Down", null, null);
        //line below will check if equipment is present in rear loaders before assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");
        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute);
        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks(garage, shift, "SanitationWorker", "Collection", "Relays", "3",1);
        //line below will assign equipment to task
        taskAssignActions().assignEquipmentToTask(garage, equipmentId, shift, "COLLECT", "RELAYS",null, 0);
        //line below will make the shift active
        taskModeUtilities().shiftStatus( null,shift,"Active" );
        //line below will check if equipment color is as expected in task before down
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Task","navyBlue");
        //line below will check if equipment text color is as expected in task before down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present in task before down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
         logTestInfo(wDriver,"verification started on location " + location + " after assign & before down");
        //wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //line below will return available count before down
        getAnyCategoryCardsCountAfter("-1",availableCountBefore,"Equipment","Available",null,null);
        //line below will return rear loaders count before down
        getAnyCategoryCardsCountAfter("-1",rearLoadersCountBefore,"Equipment","Available","RearLoaders",null);
        //line below will return pending load count before down
        getAnyCategoryCardsCountAfter("+1",pendingLoadCountBefore,"Equipment","Pending","PendingLoad",null);
        //line below will check if equipment is not present in rear loaders before down
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId, "true");
        //line below will verify equipment present in household refuse subcategory on task
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId, shift, "COLLECT", "RELAYS",0);
        //line below will update equipment to Empty before Down
        equipmentUpdateLoadActions().updateLoad(equipmentId,"Empty",null,null,null);
        //line below will down a equipment
        equipmentDownActions().downEquipment(equipmentId,downCode1,serviceLocation1,downHour1,downMinute1,reporter1,mechanic1,remarks1,downCode2,serviceLocation2,downHour2,downMinute2,reporter2,mechanic2,remarks2,downCode3,serviceLocation3,downHour3,downMinute3,reporter3,mechanic3,remarks3);
        //line below will return down count count before down
        getAnyCategoryCardsCountAfter("+1",downCountBefore,"Equipment","Down",null,null);
        //line below will verify equipment is in down category after down
        equipmentPanelUtilities().verifyEquipmentPresent("Down", null, null, equipmentId, "true");
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Task","darkGray");
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","darkGray");
        //line below will check if equipment text color is as expected in task before down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"red");
        //line below will check equipment rear loader bin type is present in task before down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will verify equipment not present in household refuse subcategory on task after down
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId, shift, "COLLECT", "RELAYS",0);
        //Line below will verify up/down history after down for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","1","Set Down",exDownDate,downHour1 + ":"  + downMinute1,"DN87-ACCIDENT",serviceLocation1,reporter1,mechanic1,remarks1);
        //Line below will verify up/down history after down for down code 2
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","2","Set Down",exDownDate,downHour2 + ":"  + downMinute2,"DN39-AIR LEAK",serviceLocation2,reporter2,mechanic2,remarks2);
        //Line below will verify up/down history after down for down code 3
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","3","Set Down",exDownDate,downHour3 + ":"  + downMinute3,"DN59-BATTERIES",serviceLocation3,reporter3,mechanic3,remarks3);


         //line below will open previous day board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(-1, "yyyyMMdd"));

        //line below will verify equipment is in down category after down on previous day board
        equipmentPanelUtilities().verifyEquipmentPresent("Down", null, null, equipmentId, "true");
        //equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Task","darkGray");
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","darkGray");
        //line below will check if equipment text color is as expected in task before down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"red");
        //line below will check equipment rear loader bin type is present in task before down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //Line below will verify up/down history after down for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","1","Set Down",exDownDate,downHour1 + ":"  + downMinute1,"DN87-ACCIDENT",serviceLocation1,reporter1,mechanic1,remarks1);
        //Line below will verify up/down history after down for down code 2
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","2","Set Down",exDownDate,downHour2 + ":"  + downMinute2,"DN39-AIR LEAK",serviceLocation2,reporter2,mechanic2,remarks2);
        //Line below will verify up/down history after down for down code 3
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","3","Set Down",exDownDate,downHour3 + ":"  + downMinute3,"DN59-BATTERIES",serviceLocation3,reporter3,mechanic3,remarks3);

    }



}
