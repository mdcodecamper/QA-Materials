package taskTestSuites.taskAssign.taskEquipmentAssign;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentUpdateLoadActions;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 10/25/2016.
 */
public class TEST006_Minerva4606_3 extends AbstractStartWebDriver {

    private String location = "QW01";   //EquipmentData.equipmentSendingLocation;
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


    @Test(description = "Assign Equipment To Task")
    public void taskAssignEquipment_Minerva4606_Verification() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","DualBins", 14);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke", "Assign Equipment To Task");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Assign dual bins equipment to a collection and recycling for a over night shift");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - Assign dual bins equipment to a collection and recycling for a over night shift");

        //line below will open previous board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close person menu
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");

        //line below will verify  shift 1 is still active after midnight
        taskModeUtilities().verifyShiftStatus(null,shift1,"Active");
        //line below will get card from shift 1 on Relays Collection
        String equipmentId1Shift1 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift1,"COLLECT","RELAYS",0);
        //line below will get card from shift 1 on Relays Recycling
        String equipmentId2Shift1 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift1,"RECYCLE","RELAYS_RCY",0);
        //line below will verify both equipment for shift 1 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift1, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift1, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift1, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift1, "false");

        //line below will verify  shift 2 is still active after midnight
        taskModeUtilities().verifyShiftStatus(null,shift2,"Active");
        //line below will get card from shift 2 on Relays Collection
        String equipmentId1Shift2 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift2,"COLLECT","RELAYS",0);
        //line below will get card from shift 2 on Relays Recycling
        String equipmentId2Shift2 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift2,"RECYCLE","RELAYS_RCY",0);
        //line below will verify both equipment for shift 2 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift2, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift2, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift2, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift2, "false");

        //line below will verify  shift 3 is still active after midnight
        taskModeUtilities().verifyShiftStatus(null,shift3,"Active");
        //line below will get card from shift 3 on Relays Collection
        String equipmentId1Shift3 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift3,"COLLECT","RELAYS",0);
        //line below will get card from shift 3 on Relays Recycling
        String equipmentId2Shift3 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift3,"RECYCLE","RELAYS_RCY",0);
        //line below will verify both equipment for shift 3 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift3, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift3, "true");
        //line below will verify both equipment is not in relay and rollover collection category
        equipmentPanelUtilities().verifyEquipmentPresent("Relay", "COLLECTION", null, equipmentId1Shift3, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Rollover", "COLLECTION", null, equipmentId2Shift3, "false");

        //line below will verify  shift 4 is still active after midnight
        taskModeUtilities().verifyShiftStatus(null,shift4,"Active");
        //line below will get card from shift 4 on Relays Collection
        String equipmentId1Shift4 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift4,"COLLECT","RELAYS",0);
        //line below will get card from shift 4 on Relays Recycling
        String equipmentId2Shift4 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift4,"RECYCLE","RELAYS_RCY",0);
        //line below will verify both equipment for shift 4 doesn't appear for equipment 1 and appears for equipment 2 pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift4, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift4, "true");
        //line below will verify equipment not in mechbroom and equipment is in dual bins
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "MechanicalBrooms", null, equipmentId1Shift4, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift4, "false");

        //line below will verify  shift 5 is still active after midnight
        taskModeUtilities().verifyShiftStatus(null,shift5,"Active");
        //line below will get card from shift 5 on Relays Collection
        String equipmentId1Shift5 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift5,"COLLECT","RELAYS",0);
        //line below will get card from shift 5 on Relays Recycling
        String equipmentId2Shift5 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift5,"RECYCLE","RELAYS_RCY",0);
        //line below will verify both equipment for shift 5 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift5, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift5, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift5, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift5, "false");

        //line below will verify  shift 6 is still active after midnight
        taskModeUtilities().verifyShiftStatus(null,shift6,"Active");
        //line below will get card from shift 6 on Relays Collection
        String equipmentId1Shift6 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift6,"COLLECT","RELAYS",0);
        //line below will get card from shift 6 on Relays Recycling
        String equipmentId2Shift6 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift6,"RECYCLE","RELAYS_RCY",0);
        //line below will verify both equipment for shift 6 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift6, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift6, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift6, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift6, "false");

        //line below will verify  shift 7 is still active after midnight
        taskModeUtilities().verifyShiftStatus(null,shift7,"Active");
        //line below will get card from shift 7 on Relays Collection
        String equipmentId1Shift7 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift7,"COLLECT","RELAYS",0);
        //line below will get card from shift 7 on Relays Recycling
        String equipmentId2Shift7 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift7,"RECYCLE","RELAYS_RCY",0);
        //line below will verify both equipment for shift 7 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift7, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift7, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift7, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift7, "false");

        //line below will verify  shift 8 is still active after midnight
        taskModeUtilities().verifyShiftStatus(null,shift8,"Active");
        //line below will get card from shift 8 on Relays Collection
        String equipmentId1Shift8 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift8,"COLLECT","RELAYS",0);
        //line below will get card from shift 8 on Relays Recycling
        String equipmentId2Shift8 = taskModeUtilities().GetCardFromTask( null,"Equipment",shift8,"RECYCLE","RELAYS_RCY",0);
        //line below will verify both equipment for shift 8 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift8, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift8, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift8, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift8, "false");


        //line below will open current board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));

        //line below will close person menu
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");

        //line below will verify both equipment for shift 1 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift1, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift1, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift1, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift1, "false");

        //line below will verify both equipment for shift 2 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift2, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift2, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift2, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift2, "false");

        //line below will verify both equipment for shift 3 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift3, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift3, "true");
        //line below will verify both equipment is not in relay and rollover collection category
        equipmentPanelUtilities().verifyEquipmentPresent("Relay", "COLLECTION", null, equipmentId1Shift3, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Rollover", "COLLECTION", null, equipmentId2Shift3, "false");

        //line below will verify equipment 1 not in pending load nd equip 2 is in pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift4, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift4, "true");
        //line below will verify equip 1 is in mech broom and equip is not in dual bins
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "MechanicalBrooms", null, equipmentId1Shift4, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift4, "false");

        //line below will verify both equipment for shift 5 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift5, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift5, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift5, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift5, "false");

        //line below will verify both equipment for shift 6 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift6, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift6, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift6, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift6, "false");

        //line below will verify both equipment for shift 7 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift7, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift7, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift7, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift7, "false");

        //line below will verify both equipment for shift 8 appears on pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1Shift8, "true");
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2Shift8, "true");
        //line below will verify both equipment is not in dual bins category
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1Shift8, "false");
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2Shift8, "false");






    }


}
