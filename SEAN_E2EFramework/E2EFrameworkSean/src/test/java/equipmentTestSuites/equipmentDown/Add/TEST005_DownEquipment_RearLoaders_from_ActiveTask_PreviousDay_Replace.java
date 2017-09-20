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

import static common.actions.CommonActions.getAnyCategoryCardsCountAfter;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 10/12/2016.
 */
public class TEST005_DownEquipment_RearLoaders_from_ActiveTask_PreviousDay_Replace extends AbstractStartWebDriver{


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

    @Test(description = "Equipment Down")
    public void equipmentDown_Minerva4545_02() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available", "RearLoaders", 1);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke","Down Equipment");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 5 - Down a equipment from an Active Task with 3 down codes on previous day board then replace it");
        System.out.println("**************************************************************************************");
        extentTest.log( LogStatus.INFO, "Scenario 5 - Down a equipment from an Active Task with 3 down codes on previous day board then replace it");

        //line below will open sending location board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        //line below will close personnel menu to expand equipment panel
        //CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

        //line below will close task menu to expand equipment panel
        //CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will get card from shift 4 on Relays Collection
        Thread.sleep( 2000 );
        //equipmentId = taskModeUtilities().GetCardFromTask( null,"Equipment",shift7,"COLLECT","RELAYS",0);
        equipmentId = taskModeUtilities().GetCardFromTask( null,"Equipment",shift7,"COLLECT","RELAYS",0);
        System.out.println("Equipment Id is " + equipmentId);

        //line below will go to live mode to get equipment count before
        //taskModeUtilities().clickonEditModeButton();
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        logTestInfo(wDriver, "verification started on location " + location + " before assigning equipment to task");
        //line below will return pending load category count before assigning equipment to task
        int pendingLoadCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending", "PendingLoad", null);
        //line below will return down count before assigning equipment to task and before down
        int downCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Down", null, null);
        //line below will check if equipment is present in pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId, "true");

        //line below will check if equipment color is as expected in task before down
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Task","limeGreen");
        //line below will check if equipment text color is as expected in task before down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present in task before down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E","E");
         logTestInfo(wDriver,"verification started on location " + location + " after assign & before down");
        Thread.sleep( 1600 );
        //line below will update equipment to Empty before Down
        equipmentUpdateLoadActions().updateLoadRightClick("Equipment",equipmentId,"Empty",null,"Empty",null);
        //line below will down a equipment
        equipmentDownActions().downEquipmentRightClick("Task",equipmentId,downCode1,serviceLocation1,downHour1,downMinute1,reporter1,mechanic1,remarks1,downCode2,serviceLocation2,downHour2,downMinute2,reporter2,mechanic2,remarks2,downCode3,serviceLocation3,downHour3,downMinute3,reporter3,mechanic3,remarks3);
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Task","darkGray");
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","darkGray");
        //line below will check if equipment text color is as expected in task after down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"red");
        //line below will check equipment rear loader bin type is present in task after down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E","E");
        //line below will return down count count after down
        getAnyCategoryCardsCountAfter("+1",downCountBefore,"Equipment","Down",null,null);
        //line below will return pending load count after down
        getAnyCategoryCardsCountAfter("-1",pendingLoadCountBefore,"Equipment","Pending","PendingLoad",null);
        //line below will check if equipment is not present in pending load after down
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId, "false");
        //line below will verify equipment is in down category after down
        equipmentPanelUtilities().verifyEquipmentPresent("Down", null, null, equipmentId, "true");
        //line below will verify equipment present in household refuse subcategory on task after down
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId, shift7, "COLLECT", "RELAYS",0);
        //Line below will verify up/down history after down for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","1","Set Down",exDownDate,downHour1 + ":"  + downMinute1,"DN87-ACCIDENT",serviceLocation1,reporter1,mechanic1,remarks1);
        //Line below will verify up/down history after down for down code 2
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","2","Set Down",exDownDate,downHour2 + ":"  + downMinute2,"DN39-AIR LEAK",serviceLocation2,reporter2,mechanic2,remarks2);
        //Line below will verify up/down history after down for down code 3
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","3","Set Down",exDownDate,downHour3 + ":"  + downMinute3,"DN59-BATTERIES",serviceLocation3,reporter3,mechanic3,remarks3);

        //String equipmentId2 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available","RearLoaders","Down" );

         //line below will open current day board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));

        //line below will verify equipment is in down category after down on current day board
        equipmentPanelUtilities().verifyEquipmentPresent("Down", null, null, equipmentId, "true");
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","limeGreen");
        //line below will check if equipment text color is as expected in task after down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present in task after down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E","E");
        //Line below will verify up/down history after down for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","1","Set Down",exDownDate,downHour1 + ":"  + downMinute1,"DN87-ACCIDENT",serviceLocation1,reporter1,mechanic1,remarks1);
        //Line below will verify up/down history after down for down code 2
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","2","Set Down",exDownDate,downHour2 + ":"  + downMinute2,"DN39-AIR LEAK",serviceLocation2,reporter2,mechanic2,remarks2);
        //Line below will verify up/down history after down for down code 3
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","3","Set Down",exDownDate,downHour3 + ":"  + downMinute3,"DN59-BATTERIES",serviceLocation3,reporter3,mechanic3,remarks3);


    }

    @Test(description = "Equipment Up")
    public void equipmentUp_Minerva4545_01() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available", "RearLoaders", 1);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );
        //equipmentId = "25CZ-509";
        extentTest.assignCategory( "Regression", "up Equipment" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "up a equipment from an Active Task on previous day board" );
        System.out.println( "**************************************************************************************" );
        extentTest.log( LogStatus.INFO, "up a equipment from an Active Task on previous day board" );

        //line below will open current day board to get dual bins coutn before
        loginPage().goToBoardLocationByDate( url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd") );
        //line below will close personnel menu to expand equipment panel
       // CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will return available category count before up
        String pendingLoadCountBeforeCurrent = equipmentPanelUtilities().getEquipmentCategoryCount( "Pending", "PendingLoad", null );
        //line below will return dual bins category count before up
        String availableCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Available", null, null );

        //line below will open previous day board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );
        //line below will close personnel menu to expand equipment panel
       // CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

        //line below will return down category count before up
        String downCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Down", null, null );
        //line below will return down category count before up
         String pendingLoadCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Pending", "PendingLoad", null );
         Thread.sleep( 2000 );
        //line below will get up hour before up action
        String upHour = Utilities.get24HourFormat(0);
        //line below will up a equipment
        equipmentUpActions().upEquipmentRightClick("Task",equipmentId, "mechanic", "reporter", upHour, null);

        logTestInfo(wDriver, "verification started on location " + location + " after up" );
        //line below will return down count after up
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(-1,downCountBefore, "Down", null, null );
        //line below will return pending load count after up
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(+1,pendingLoadCountBefore, "Pending", "PendingLoad", null );
        //line below will check if equipment is not present in down after up
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "false" );
        //line below will check if equipment is present in pending Load after up on active task
        equipmentPanelUtilities().verifyEquipmentPresent( "Pending", "PendingLoad", null, equipmentId, "true" );
        //line below verifies card still present on Relays task for Collection
        taskModeUtilities().verifyCardPresentOnTask( true,null,"Equipment",equipmentId,shift7,"COLLECT","RELAYS",0 );
        //line below will check if equipment color is as expected in task after up
        equipmentColorUtilities().verifyEquipmentCardColor( equipmentId, "Task","limeGreen" );
        //line below will check if equipment text color is as expected in down category after up
        equipmentColorUtilities().verifyEquipmentTextColor( equipmentId, "black" );
        //line below will check equipment rear loader bin type is present in down category after up
        equipmentPanelUtilities().equipmentBinType( equipmentId, "E", "E" );
        //line below will return up history expected result after up
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", null, "Set Up", exDownDate, upHour + ":", "", "", "reporter", "mechanic", "" );

        //line below will open current day board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        //line below will return available count after up
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(0,availableCountBefore, "Available", null, null );
        //line below will return pending Load count after up
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(+1,pendingLoadCountBeforeCurrent, "Pending", "DualBins", null );
        //line below will verify equipment available in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent( "Available", "DualBins", null, equipmentId, "true" );
        //line below will check if equipment color is as expected in task after up
        equipmentColorUtilities().verifyEquipmentCardColor( equipmentId, "Equipment","limeGreen" );
        //line below will check if equipment text color is as expected in down category after up
        equipmentColorUtilities().verifyEquipmentTextColor( equipmentId, "black" );
        //line below will check equipment rear loader bin type is present in down category after up
        equipmentPanelUtilities().equipmentBinType( equipmentId, "E", "E" );

    }






    }
