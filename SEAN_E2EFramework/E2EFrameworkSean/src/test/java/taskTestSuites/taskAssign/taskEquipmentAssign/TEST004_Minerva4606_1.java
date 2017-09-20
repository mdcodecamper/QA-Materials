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
public class TEST004_Minerva4606_1 extends AbstractStartWebDriver {

    private String location = "QW01";   //EquipmentData.equipmentSendingLocation;
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

    @Test(description = "Assign Equipment To Task")
    public void taskAssignEquipment_Minerva4606_Assign() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","DualBins", 16);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke", "Assign Equipment To Task");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Assign dual bins equipment to a collection and RECYCLE for a over night shift");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - Assign dual bins equipment to a collection and RECYCLE for a over night shift");

        //line below will open current board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close person menu
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");

        //line below will close equipment menu
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );

        //line below will delete any shift that has task(s) before adding a new shift
       // TaskActions.deleteAllShiftWithTask(wDriver,null);

       /* //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour1, shiftStartMinute1, shiftEndHour1, shiftEndMinute1);

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour2, shiftStartMinute2, shiftEndHour2, shiftEndMinute2);

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour3, shiftStartMinute3, shiftEndHour3, shiftEndMinute3);

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour4, shiftStartMinute4, shiftEndHour4, shiftEndMinute4);

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour5, shiftStartMinute5, shiftEndHour5, shiftEndMinute5);

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour6, shiftStartMinute6, shiftEndHour6, shiftEndMinute6);

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour7, shiftStartMinute7, shiftEndHour7, shiftEndMinute7);

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour8, shiftStartMinute8, shiftEndHour8, shiftEndMinute8);

        //line below will add tasks from collection Relays for shift 1
        taskAddTaskActions().addTasks(garage, shift1, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from RECYCLE Relays for shift 1
        taskAddTaskActions().addTasks(garage, shift1, "SanitationWorker", "Recycling", "Relays Rcy", "2",2);

        //line below will add tasks from collection Relays for shift 2
        taskAddTaskActions().addTasks(garage, shift2, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from RECYCLE Relays for shift 2
        taskAddTaskActions().addTasks(garage, shift2, "SanitationWorker", "Recycling", "Relays Rcy", "2",2);

        //line below will add tasks from collection Relays for shift 3
        taskAddTaskActions().addTasks(garage, shift3, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from RECYCLE Relays for shift 3
        taskAddTaskActions().addTasks(garage, shift3, "SanitationWorker", "Recycling", "Relays Rcy", "2",2);

        //line below will add tasks from collection Relays for shift 4
        taskAddTaskActions().addTasks(garage, shift4, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from RECYCLE Relays for shift 4
        taskAddTaskActions().addTasks(garage, shift4, "SanitationWorker", "Recycling", "Relays Rcy", "2",2);

        //line below will add tasks from collection Relays for shift 5
        taskAddTaskActions().addTasks(garage, shift5, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from RECYCLE Relays for shift 5
        taskAddTaskActions().addTasks(garage, shift5, "SanitationWorker", "Recycling", "Relays Rcy", "2",2);

        //line below will add tasks from collection Relays for shift 6
        taskAddTaskActions().addTasks(garage, shift6, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from RECYCLE Relays for shift 6
        taskAddTaskActions().addTasks(garage, shift6, "SanitationWorker", "Recycling", "Relays Rcy", "2",2);

        //line below will add tasks from collection Relays for shift 7
        taskAddTaskActions().addTasks(garage, shift7, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from RECYCLE Relays for shift 7
        taskAddTaskActions().addTasks(garage, shift7, "SanitationWorker", "Recycling", "Relays Rcy", "2",2);

        //line below will add tasks from collection Relays for shift 8
        taskAddTaskActions().addTasks(garage, shift8, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from RECYCLE Relays for shift 8
        taskAddTaskActions().addTasks(garage, shift8, "SanitationWorker", "Recycling", "Relays Rcy", "2",2);*/

        //line below will go to live mode
        //taskModeUtilities().clickonEditModeButton();

        //line below will make shift 1 active
        taskModeUtilities().shiftStatus( null, shift1, "Active" );

        //line below will make shift 2 active
        taskModeUtilities().shiftStatus( null, shift2, "Active" );

        //line below will make shift 3 active
        taskModeUtilities().shiftStatus( null, shift3, "Active" );

        //line below will make shift 4 active
        taskModeUtilities().shiftStatus( null, shift4, "Active" );

        //line below will make shift 5 active
        taskModeUtilities().shiftStatus( null, shift5, "Active" );

        //line below will make shift 6 active
        taskModeUtilities().shiftStatus( null, shift6, "Active" );

        //line below will make shift 7 active
        taskModeUtilities().shiftStatus( null, shift7, "Active" );

        //line below will make shift 8 active
        taskModeUtilities().shiftStatus( null, shift8, "Active" );

        //line below will re-open equipment menu
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );

        //line below will getEquipment for shift 1 from a category
        String equipmentId1 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId1, shift1, "COLLECT", "RELAYS", null, 0);
        //line below will verify equipment present in Relays subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId1, shift1, "COLLECT", "RELAYS", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1, "true");
        //line below will getEquipment 2 from a category
        String equipmentId2 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId2, shift1, "RECYCLE", "RELAYS_RCY", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId2, shift1, "RECYCLE", "RELAYS_RCY", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId2, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2, "true");

        //line below will getEquipment for shift 2 from a category
        String equipmentId3 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId3, shift2, "COLLECT", "RELAYS", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId3, shift2, "COLLECT", "RELAYS", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId3, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId3, "true");
        //line below will getEquipment 2 from a category
        String equipmentId4 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId4, shift2, "RECYCLE", "RELAYS_RCY", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId4, shift2, "RECYCLE", "RELAYS_RCY", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId4, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId4, "true");

        //line below will getEquipment for shift 3 from a category
        String equipmentId5 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line will verify equipment added to relay collection
        equipmentUpdateLoadActions().updateLoad(equipmentId5,"Relay","01","Empty",null);
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId5, shift3, "COLLECT", "RELAYS", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId5, shift3, "COLLECT", "RELAYS", 0);
        //line below will check if equipment is not present not in Relay Collection after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Relay", "COLLECTION", null, equipmentId5, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId5, "true");
        //line below will getEquipment 2 from a category
        String equipmentId6 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line will verify equipment added to Rollover collection
        equipmentUpdateLoadActions().updateLoad(equipmentId6,"Rollover","01","Empty",null);
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId6, shift3, "RECYCLE", "RELAYS_RCY", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId6, shift3, "RECYCLE", "RELAYS_RCY", 0);
        //line below will check if equipment is not present not in Rollover Collection after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Rollover", "COLLECTION", null, equipmentId6, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId6, "true");

        //line below will getEquipment for shift 4 from a category
        String equipmentId7 = equipmentPanelUtilities().getEquipmentFromCategory("NonBin", "Available", "MechanicalBrooms");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId7, shift4, "COLLECT", "RELAYS", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId7, shift4, "COLLECT", "RELAYS", 0);
        //line below will check if equipment is not present not in Mechanical Brooms after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "MechanicalBrooms", null, equipmentId7, "false");
        //line below will check if equipment is not present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId7, "false");
        //line below will getEquipment 2 from a category
        String equipmentId8 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId8, shift4, "RECYCLE", "RELAYS_RCY", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId8, shift4, "RECYCLE", "RELAYS_RCY", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId8, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId8, "true");

        //line below will getEquipment for shift 5 from a category
        String equipmentId9 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId9, shift5, "COLLECT", "RELAYS", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId9, shift5, "COLLECT", "RELAYS", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId9, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId9, "true");
        //line below will getEquipment 2 from a category
        String equipmentId10 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId10, shift5, "RECYCLE", "RELAYS_RCY", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId10, shift5, "RECYCLE", "RELAYS_RCY", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId10, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId10, "true");

        //line below will getEquipment for shift 6 from a category
        String equipmentId11 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId11, shift6, "COLLECT", "RELAYS", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId11, shift6, "COLLECT", "RELAYS", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId11, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId11, "true");
        //line below will getEquipment 2 from a category
        String equipmentId12 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId12, shift6, "RECYCLE", "RELAYS_RCY", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId12, shift6, "RECYCLE", "RELAYS_RCY", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId12, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId12, "true");

        //line below will getEquipment for shift 7 from a category
        String equipmentId13 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId13, shift7, "COLLECT", "RELAYS", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId13, shift7, "COLLECT", "RELAYS", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId13, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId13, "true");
        //line below will getEquipment 2 from a category
        String equipmentId14 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId14, shift7, "RECYCLE", "RELAYS_RCY", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId14, shift7, "RECYCLE", "RELAYS_RCY", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId14, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId14, "true");

        //line below will getEquipment for shift 8 from a category
        String equipmentId15 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId15, shift8, "COLLECT", "RELAYS", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId15, shift8, "COLLECT", "RELAYS", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId15, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId15, "true");
        //line below will getEquipment 2 from a category
        String equipmentId16 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId16, shift8, "RECYCLE", "RELAYS_RCY", null, 0);
        //line below will verify equipment present in Relays RECYCLE subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId16, shift8, "RECYCLE", "RELAYS_RCY", 0);
        //line below will check if equipment is not present not in dual bins after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId16, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId16, "true");

    }


}
