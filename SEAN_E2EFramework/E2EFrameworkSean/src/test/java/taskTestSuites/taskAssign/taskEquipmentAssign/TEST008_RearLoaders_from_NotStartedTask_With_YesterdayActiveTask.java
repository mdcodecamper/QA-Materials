package taskTestSuites.taskAssign.taskEquipmentAssign;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.CommonActions;
import common.data.LoginData;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static equipment.abstractBase.EquipmentBasePage.*;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 10/12/2016.
 */
public class TEST008_RearLoaders_from_NotStartedTask_With_YesterdayActiveTask extends AbstractStartWebDriver{


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
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
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
        System.out.println("assign a bin equipment to not started task on current day which is already assigned to active task on previous day");
        System.out.println("**************************************************************************************");
        extentTest.log( LogStatus.INFO, "assign a bin equipment to not started task on current day which is already assigned to active task on previous day");

        //line below will go to sending location board on previous day board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(-1, "yyyyMMdd"));
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        CommonActions.clickOnMenuIcon(wDriver, "Equipment");
        //line below will get card from shift 2 on Relays Collection
        String equipmentId = taskModeUtilities().GetCardFromTask( null,"Equipment",shift6,"COLLECT","RELAYS",0);

        //line below will open sending location board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

        //line below will delete any shift that has task(s) before adding a new shift
        //TaskActions.deleteAllShiftWithTask(wDriver,null);

        System.out.println("Equipment Id is " + equipmentId);

        //line below will perform upload load action
        equipmentUpdateLoadActions().updateLoad( equipmentId,"Empty",null,null,null );

        closeDetailPanel( "Equipment" );

        //line below will go to live mode to get equipment count before
        //taskModeUtilities().clickonEditModeButton();

        logTestInfo(wDriver, "verification started on location " + location + " before assigning equipment to task");
        //line below will return pending load category count before assigning equipment to task
        int pendingLoadCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending", "PendingLoad", null);
        //line below will return available count before assigning equipment to task
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available", null, null);
        //line below will return available rear loaders count before assigning equipment to task
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available", "RearLoaders", null);
        //line below will check if equipment is present in rear loaders before assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");
        taskAssignActions().assignEquipmentToTask(garage, equipmentId, shift6, "COLLECT", "RELAYS",null, 0);
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
        getAnyCategoryCardsCountAfter("0",pendingLoadCountBefore,"Equipment","Pending","PendingLoad",null);
        //line below will check if equipment is not present in rear loaders before down
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "false");
        //line below will check if equipment is not present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId, "false");
        //line below will verify equipment present in household refuse subcategory on task
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId, shift6, "COLLECT", "RELAYS",0);

        //line below will verify equipment ripples on not started task on current board
        boolean taskBlink = wDriver.findElements( By.xpath("//*[contains(@class,'taskpanel blink') and contains(@data-id,'"+ equipmentId +"')]")).size() > 0;
        if(taskBlink == true){
            executorScrollIntoView(wDriver,wDriver.findElements( By.xpath("//*[contains(@class,'taskpanel blink') and contains(@data-id,'"+ equipmentId +"')]")).get(0));
            logTestPass( wDriver,"Equipment " + equipmentId + " is blinking on Current board for not started task which is already assiged to active task on previous day" );
            getRegularScreenShot(wDriver);
        } else {
            executorScrollIntoView(wDriver,wDriver.findElements( By.xpath("//*[contains(@class,'taskpanel blink') and contains(@data-id,'"+ equipmentId +"')]")).get(0));
            logTestFailure( wDriver,"Equipment " + equipmentId + " is not blinking on Current board for not started task which is already assiged to active task on previous day" );
        }


        //line below will open previous day board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(-1, "yyyyMMdd"));

        //line below will verify equipment present in household refuse subcategory on task
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId, shift6, "COLLECT", "RELAYS",0);



    }



}
