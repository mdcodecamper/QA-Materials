package equipmentTestSuites.equipmentDetach.Detach;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.getAnyCategoryCardsCount;
import static common.actions.CommonActions.getAnyCategoryCardsCountAfter;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 10/25/2016.
 */
public class TEST010_Minerva4545_04 extends AbstractStartWebDriver {

    private String location = "BKS14";   //EquipmentData.equipmentSendingLocation;
    private String toLocation = EquipmentData.equipmentReceivingLocation;
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(-1, "yyyyMMdd");
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
    private String equipmentId2Shift6 = null;

    @Test(priority = 1,description = "Equipment Detach")
    public void equipmentDetach_Minerva4545_04() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","RearLoaders", 14);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke", "Assign Equipment To Task");
        System.out.println("**************************************************************************************");
        System.out.println("Assign dual bins equipment to a collection and recycling for a over night shift and initiate detach");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Assign dual bins equipment to a collection and recycling for a over night shift and initiate detach");

        //line below will go to receving location board on current day
        /*loginPage().goToBoardLocationByDate(url, toLocation + "/", boardDate);

        //line below will get pending attach count before
        Thread.sleep( 1000 );
        int pendintAttachBefore = getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);*/

        //line below will open today's board on sending location
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        Thread.sleep(5000);
        //line below will close person menu
        //CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        //line below will get equipment from pending load
        equipmentId1Shift3 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders");
        System.out.println("Equipment Id is " + equipmentId1Shift3);

                //taskModeUtilities().GetCardFromTask( null,"Equipment",shift3,"COLLECT","RELAYS",0);
        //System.out.println(equipmentId1Shift3);
        //line below will get pending detach count before
        int pendintDetachBefore = getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);
        //line below will perform Upload Action on both equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId1Shift3,"Empty",null,null,null);
        //line below will perform initiated detach on both equipment
        String detachHourShift2 = Utilities.get24HourFormat(-1);
        equipmentDetachActions().detachEquipment(equipmentId1Shift3,toLocation,detachHourShift2,"00","Driver01");
        //line below will get card from shift 2 on Relays Recycling
        String equipmentId2Shift3 = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders");
        equipmentUpdateLoadActions().updateLoad(equipmentId2Shift3,"Relay","02",null,null);
        equipmentDetachActions().detachEquipment(equipmentId2Shift3,toLocation,detachHourShift2,"00","Driver02");
        //line below will verify both equipment for shift 2 doesn't appear on Rear Loaders
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1Shift3, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId2Shift3, "false");
        //line below will verify both equipment for shift 2 appears on pending detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId1Shift3, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId2Shift3, "true");
        //line below will verify both equipment for shit 2 detachment history as initiated detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift3, "1", "Initiated Detach", exDetachDate + " " + detachHourShift2 + ":00", EquipmentData.verifyLocationIsDualGarage(location), toLocation, LoginData.getLoginData("username"), "Driver01", "");
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId2Shift3, "1", "Initiated Detach", exDetachDate + " " + detachHourShift2 + ":00", EquipmentData.verifyLocationIsDualGarage(location), toLocation, LoginData.getLoginData("username"), "Driver02", "");

        //line below will get card from shift 7 on Relays Collection
        String equipmentId1Shift6 = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders");
        //taskModeUtilities().GetCardFromTask( null,"Equipment",shift6,"COLLECT","RELAYS",0);
        //line below will perform Upload Action on both equipment for shift 7
        equipmentUpdateLoadActions().updateLoad(equipmentId1Shift6,"Empty",null,null,null);
        //line below will perform initiated detach on both equipment for shift 7
        String detachHourShift6 = Utilities.get24HourFormat(-2);
        equipmentDetachActions().detachEquipment(equipmentId1Shift6,toLocation,detachHourShift6,"00","Driver03");
        //line below will get card from shift 7 on Relays Recycling
        equipmentId2Shift6 = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders" );
        //taskModeUtilities().GetCardFromTask( null,"Equipment",shift6,"RECYCLE","RELAYS_RCY",0);
        equipmentUpdateLoadActions().updateLoad(equipmentId2Shift6,"Rollover","02",null,null);
        equipmentDetachActions().detachEquipment(equipmentId2Shift6,toLocation,detachHourShift6,"00","Driver04");
        //line below will verify both equipment for shift 7 doesn't appears on Rear Loaders
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1Shift6, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId2Shift6, "false");
        //line below will verify both equipment is in pending detach category for shift 7
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId1Shift6, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId2Shift6, "true");
        //line below will return pending detach count after
        getAnyCategoryCardsCountAfter("+4",pendintDetachBefore,"Equipment","Pending","PendingDetach",null);
        //line below will verify both equipment for shit 7 detachment history as initiated detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift6, "1", "Initiated Detach", exDetachDate + " " + detachHourShift6 + ":00", EquipmentData.verifyLocationIsDualGarage(location), toLocation, LoginData.getLoginData("username"), "Driver03", "");
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId2Shift6, "1", "Initiated Detach", exDetachDate + " " + detachHourShift6 + ":00", EquipmentData.verifyLocationIsDualGarage(location), toLocation, LoginData.getLoginData("username"), "Driver04", "");

      /*  //line below will go to receving location board after detach
        loginPage().goToBoardLocationByDate(url, toLocation + "/", boardDate);

        //line below will return pending attach count after detach
        getAnyCategoryCardsCountAfter("+4",pendintAttachBefore,"Equipment","Pending","PendingAttach",null);
        //line below will verify all equipment on shift 2 & 7 detachment history as initiated detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift2, "1", "Initiated Detach", exDetachDate + " " + detachHourShift2 + ":00", location, toLocation, LoginData.getLoginData("username"), "Driver01", "");
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId2Shift2, "1", "Initiated Detach", exDetachDate + " " + detachHourShift2 + ":00", location, toLocation, LoginData.getLoginData("username"), "Driver02", "");
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift7, "1", "Initiated Detach", exDetachDate + " " + detachHourShift7 + ":00", location, toLocation, LoginData.getLoginData("username"), "Driver03", "");
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId2Shift7, "1", "Initiated Detach", exDetachDate + " " + detachHourShift7 + ":00", location, toLocation, LoginData.getLoginData("username"), "Driver04", "");*/


        //line below will go to sending location board on current day
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));

        //line below will verify all equipment for shift 2 & 7 appears on pending detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId1Shift3, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId2Shift3, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId1Shift6, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId2Shift6, "true");

        //line below will verify all equipment on shift 2 & 7 detachment history as initiated detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift3, "1", "Initiated Detach", exDetachDate + " " + detachHourShift2 + ":00", EquipmentData.verifyLocationIsDualGarage(location), toLocation, LoginData.getLoginData("username"), "Driver01", "");
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId2Shift3, "1", "Initiated Detach", exDetachDate + " " + detachHourShift2 + ":00", EquipmentData.verifyLocationIsDualGarage(location), toLocation, LoginData.getLoginData("username"), "Driver02", "");
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift6, "1", "Initiated Detach", exDetachDate + " " + detachHourShift6 + ":00", EquipmentData.verifyLocationIsDualGarage(location), toLocation, LoginData.getLoginData("username"), "Driver03", "");
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId2Shift6, "1", "Initiated Detach", exDetachDate + " " + detachHourShift6 + ":00", EquipmentData.verifyLocationIsDualGarage(location), toLocation, LoginData.getLoginData("username"), "Driver04", "");


    }


    @Test(priority = 2,description = "Cancel Detach")
    public void equipmentCancelDetach_Minerva4545_PreviousDay() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","RearLoaders", 14);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Regression", "Cancel Detach" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Cancel Equipment on previous day which has active tasks" );
        System.out.println( "**************************************************************************************" );


        //line below will go to receving location board on previous day
       /* loginPage().goToBoardLocationByDate(url, toLocation + "/", boardDate);

        //line below will get pending attach count before
        Thread.sleep( 2500 );
        int pendintAttachBefore = getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId1Shift3, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId2Shift6, "true");*/

        //line below will open previous day board on sending location
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );
        Thread.sleep( 5000 );
        int pendintDetachBefore = getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId1Shift3, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId2Shift6, "true");

        //line below will perform cancel detachment by right click on equipment
        String detachHour1 = Utilities.get24HourFormat(0);
        equipmentCancelDetachActions().cancelEquipmentDetachmentRightClick(equipmentId1Shift3);
        String detachHour2 = Utilities.get24HourFormat(0);
        equipmentCancelDetachActions().cancelEquipmentDetachmentRightClick(equipmentId2Shift6);

        //line below will verify equipment present on Rear Loaders category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1Shift3, "true");
        //line below will verify equipment present on Relay Miscellaneous category
        equipmentPanelUtilities().verifyEquipmentPresent("Rollover", "MISCELLANEOUS", null, equipmentId2Shift6, "true");
        //line below will check equipment material number
        equipmentPanelUtilities().equipmentBinType(equipmentId2Shift6,"02",null);
        //line below will check equipment rear loader material color
        equipmentColorUtilities().equipmentBinColor(equipmentId2Shift6,"white",null);

        //line below will return pending detach count after cancel detach on sending location
       getAnyCategoryCardsCountAfter("-2",pendintDetachBefore,"Equipment","Pending","PendingDetach",null);
        //line below will verify equipment is not present on Pending Detach category
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId1Shift3, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId2Shift6, "false");

        //line below will verify equipment detachment history after cancel detach on sending location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId1Shift3, "1", "Cancelled", exDetachDate + " " + detachHour1 + ":" + "", "", "", LoginData.getLoginData("username"), "", "");
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId2Shift6, "1", "Cancelled", exDetachDate + " " + detachHour2 + ":" + "", "", "", LoginData.getLoginData("username"), "", "");

        //line below will go to receving location board on previous day after cancel detach
        loginPage().goToBoardLocationByDate(url, toLocation + "/", boardDate);

        //line below will get pending attach count before
      /*  Thread.sleep( 2000 );
        getAnyCategoryCardsCountAfter("-2",pendintAttachBefore,"Equipment","Pending","PendingAttach",null);
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId1Shift3, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId2Shift6, "false");*/

        //line below will open previous day board on sending location
        loginPage().goToBoardLocationByDate( url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd") );
        //line below will verify equipment present on Rear Loaders category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1Shift3, "true");
        //line below will verify equipment present on Relay Miscellaneous category
        equipmentPanelUtilities().verifyEquipmentPresent("Rollover", "MISCELLANEOUS", null, equipmentId2Shift6, "true");
        //line below will check equipment material number
        equipmentPanelUtilities().equipmentBinType(equipmentId2Shift6,"02",null);
        //line below will check equipment rear loader material color
        equipmentColorUtilities().equipmentBinColor(equipmentId2Shift6,"white",null);

    }




}
