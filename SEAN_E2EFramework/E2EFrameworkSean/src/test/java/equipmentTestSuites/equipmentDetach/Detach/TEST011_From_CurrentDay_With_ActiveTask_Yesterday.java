package equipmentTestSuites.equipmentDetach.Detach;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static equipment.abstractBase.EquipmentBasePage.*;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 10/25/2016.
 */
public class TEST011_From_CurrentDay_With_ActiveTask_Yesterday extends AbstractStartWebDriver {

    private String location = "SI01";   //EquipmentData.equipmentSendingLocation;
    private String toLocation = EquipmentData.equipmentReceivingLocation;
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour1 = "12"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute1 = "00";
    private String shiftEndHour1 = "20"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute1 = "00";
    private String shiftStartHour2 = "13"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute2 = "00";
    private String shiftEndHour2 = "21"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute2 = "00";
    private String shiftStartHour3 = "14"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute3 = "00";
    private String shiftEndHour3 = "22"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute3 = "00";
    private String shiftStartHour4 = "15"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute4 = "00";
    private String shiftEndHour4 = "23"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute4 = "00";
    private String shiftStartHour5 = "16"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute5 = "00";
    private String shiftEndHour5 = "00"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute5 = "00";
    private String shiftStartHour6 = "17"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute6 = "00";
    private String shiftEndHour6 = "01"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute6 = "00";
    private String shiftStartHour7 = "18"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute7 = "00";
    private String shiftEndHour7 = "02"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute7 = "00";
    private String shiftStartHour8 = "19"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute8 = "00";
    private String shiftEndHour8 = "03"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute8 = "00";
    private String shiftStartHour9 = "20"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute9 = "00";
    private String shiftEndHour9 = "04"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute9 = "00";
    private String shiftStartHour10 = "21"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute10 = "00";
    private String shiftEndHour10 = "05"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute10 = "00";
    private String url = LoginData.getLoginData(getUrl());
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
    private String exDetachDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String equipmentId1Shift3 = null;

    @Test(priority = 1,description = "Assign Equipment To Task")
    public void equipmentDetach_Minerva4545_04_CurrentDay_ActiveTask_Yesterday() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","DualBins", 14);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke", "Assign Equipment To Task");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - detach rar loaders equipment on current day which has active tasks yesterday");
        System.out.println("**************************************************************************************");

        //line below will go to sending location board on previous day board
        /*loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(-1, "yyyyMMdd"));
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        CommonActions.clickOnMenuIcon(wDriver, "Equipment");
        //line below will get card from shift 2 on Relays Collection
        String equipmentId1Shift3 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift2,"COLLECT","RELAYS",0);*/

        //line below will go to receving location board on current day
        loginPage().goToBoardLocationByDate(url, toLocation + "/", boardDate);

        //line below will get pending attach count before
        Thread.sleep( 2000 );
        int pendintAttachBefore = getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);

        //line below will open today's board on sending location
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        Thread.sleep(3500);
        //line below will close person menu
        //CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        //line below will get equipment from pending load
        equipmentId1Shift3 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders");
        //System.out.println(equipmentId1Shift3);
        //line below will get pending detach count before
        int pendingDetachBefore = getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);
        //line below will get available count before
        int availableBefore = getAnyCategoryCardsCount("Equipment","Available",null,null);
        //line below will get rear loaders count before
        int rearLoadersBefore = getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);
        //line below will perform Upload Action on equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId1Shift3,"Empty",null,null,null);
        closeDetailPanel( "Equipment" );
        //line below will perform initiated detach on equipment
        String detachHourShift2 = Utilities.get24HourFormat(-1);
        equipmentDetachActions().detachEquipment(equipmentId1Shift3,toLocation,detachHourShift2,"00","Driver01");

        //line below will verify equipment doesn't appear on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift3, "false");
        //line below will verify equipment appears on pending detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId1Shift3, "true");
        //line below will verify equipment doesn't appear on rear loaders
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1Shift3, "false");
        //line below will verify equipment detachment history as initiated detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift3, "1", "Initiated Detach", exDetachDate + " " + detachHourShift2 + ":00", location, toLocation, LoginData.getLoginData("username"), "Driver01", "");

        //line below will return pending detach count after detach
        getAnyCategoryCardsCountAfter("+1",pendingDetachBefore,"Equipment","Pending","PendingDetach",null);
        //line below will return available count after detach
        getAnyCategoryCardsCountAfter("-1",availableBefore,"Equipment","Available",null,null);
        //line below will return rear loaders count after detach
        getAnyCategoryCardsCountAfter("-1",rearLoadersBefore,"Equipment","Available","RearLoaders",null);

        //line below will go to receving location board after detach
        loginPage().goToBoardLocationByDate(url, toLocation + "/", boardDate);

        //line below will return pending attach count after detach
        getAnyCategoryCardsCountAfter("+1",pendintAttachBefore,"Equipment","Pending","PendingAttach",null);
        //line below will verify equipment appears on pending attach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId1Shift3, "true");
        //line below will verify all equipment detachment history as initiated detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift3, "1", "Initiated Detach", exDetachDate + " " + detachHourShift2 + ":00", EquipmentData.verifyLocationIsDualGarage(location), toLocation, LoginData.getLoginData("username"), "Driver01", "");




        //line below will go to sending location board on previous day board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(-1, "yyyyMMdd"));

        //CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        Thread.sleep(2000);
        //line below will verify equipment appears on pending detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId1Shift3, "true");

        //line below will verify equipment detachment history as initiated detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift3, "1", "Initiated Detach", exDetachDate + " " + detachHourShift2 + ":00", EquipmentData.verifyLocationIsDualGarage(location), toLocation, LoginData.getLoginData("username"), "Driver01", "");

        //closeDetailPanel("Equipment");

        //line below will verify equipment present in Relays subcategory on task 1 for shift 2
       // taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId1Shift3, shift2, "COLLECT", "RELAYS", 0);

        //line below will verify equipment ripples on active task yesterday's board
       /* boolean taskBlink = wDriver.findElements( By.xpath("/*//*[contains(@class,'taskpanel blink') and contains(@data-id,'"+ equipmentId1Shift3 +"')]")).size() > 0;
        if(taskBlink == true){
            executorScrollIntoView(wDriver,wDriver.findElements( By.xpath("/*//*[contains(@class,'taskpanel blink') and contains(@data-id,'"+ equipmentId1Shift3 +"')]")).get(0));
            logTestPass( wDriver,"Equipment " + equipmentId1Shift3 + " is blinking on previous board on task after it was detached on current board" );
            getRegularScreenShot(wDriver);
        } else {
            logTestFailure( wDriver,"Equipment " + equipmentId1Shift3 + " is nt blinking on previous board on task after it was detached on current board" );
        }*/

    }


    @Test(priority = 2,description = "Cancel Detach")
    public void equipmentCancelDetach_Minerva4545_04_CurrentDay_ActiveTask_Yesterday() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","RearLoaders", 14);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Regression", "Cancel Detach" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Cancel Equipment on current day which has active tasks yesterday" );
        System.out.println( "**************************************************************************************" );


        //line below will go to receving location board on current day
        loginPage().goToBoardLocationByDate(url, toLocation + "/", boardDate);

        //line below will get pending attach count before
        Thread.sleep( 2500 );
        int pendintAttachBefore = getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId1Shift3, "true");

        //line below will open current day board on sending location
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );
        Thread.sleep( 5000 );
        int pendintDetachBefore = getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId1Shift3, "true");

        //line below will perform cancel detachment by right click on equipment
        String detachHour1 = Utilities.get24HourFormat(0);
        equipmentCancelDetachActions().cancelEquipmentDetachmentRightClick(equipmentId1Shift3);

        //line below will verify equipment present on Rear Loaders category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1Shift3, "true");

        //line below will return pending detach count after cancel detach on sending location
        getAnyCategoryCardsCountAfter("-1",pendintDetachBefore,"Equipment","Pending","PendingDetach",null);
        //line below will verify equipment is not present on Pending Detach category
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId1Shift3, "false");

        //line below will verify equipment detachment history after cancel detach on sending location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift3, "1", "Cancelled", exDetachDate + " " + detachHour1 + ":" + "", "", "", LoginData.getLoginData("username"), "", "");


        //line below will go to receving location board on current day after cancel detach
        loginPage().goToBoardLocationByDate(url, toLocation + "/", boardDate);

        //line below will get pending attach count before
        Thread.sleep( 2000 );
        getAnyCategoryCardsCountAfter("-1",pendintAttachBefore,"Equipment","Pending","PendingAttach",null);
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId1Shift3, "false");

        //line below will open previous day board on sending location
        loginPage().goToBoardLocationByDate( url, location + "/", Utilities.getDesiredDateInFormat(-1, "yyyyMMdd") );
        //line below will verify equipment present on Rear Loaders category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1Shift3, "true");
        //line below will check equipment material number
        equipmentPanelUtilities().equipmentBinType(equipmentId1Shift3,"E",null);
        //line below will check equipment rear loader material color
       // equipmentColorUtilities().equipmentBinColor(equipmentId1Shift3,"navyBlue",null);

    }

}



