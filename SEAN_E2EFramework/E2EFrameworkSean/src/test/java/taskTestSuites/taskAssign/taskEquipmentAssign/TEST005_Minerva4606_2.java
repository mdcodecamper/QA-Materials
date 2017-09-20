package taskTestSuites.taskAssign.taskEquipmentAssign;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 10/25/2016.
 */
public class TEST005_Minerva4606_2 extends AbstractStartWebDriver {

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
    private String shift9 = "2000-0400";
    private String shift10 = "2100-0500";


    @Test(description = "Assign Equipment To Task")
    public void taskAssignEquipment_Minerva() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","DualBins", 14);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke", "Assign Equipment To Task");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Assign dual bins equipment to a collection and recycling for a over night shift");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - Assign dual bins equipment to a collection and recycling for a over night shift");

        //line below will open current board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close person menu
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");

        //line below will delete any shift that has task(s) before adding a new shift
        //TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour9, shiftStartMinute9, shiftEndHour9, shiftEndMinute9);

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour10, shiftStartMinute10, shiftEndHour10, shiftEndMinute10);

        //line below will add tasks from collection Relays for shift 9
        taskAddTaskActions().addTasks(garage, shift9, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from Recycling Relays for shift 9
        taskAddTaskActions().addTasks(garage, shift9, "SanitationWorker", "Recycling", "Relays Rcy", "2",2);

        //line below will add tasks from collection Relays for shift 10
        taskAddTaskActions().addTasks(garage, shift10, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from Recycling Relays for shift 10
        taskAddTaskActions().addTasks(garage, shift10, "SanitationWorker", "Recycling", "Relays Rcy", "2",2);

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        /*//line below will close task menu
        CommonActions.clickOnMenuIcon(wDriver, "Task");

        //line below will getEquipment from a category
        String equipmentId1 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders");

        //line below will reopen task menu
        CommonActions.clickOnMenuIcon(wDriver, "Task");

        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId1, shift1, "COLLECT", "RELAYS", null, 0);
        //line below will assign equipment to task on Relays Rcy
        taskAssignActions().assignEquipmentToTask(garage, equipmentId1, shift1, "COLLECT", "RELAYS_RCY", null, 0);

        logTestInfo(wDriver, "verification started on location " + location + " before assigning equipment to task");
        //line below will return pending load category count before assigning equipment to task
        int pendingLoadCountBefore1 = CommonActions.getAnyCategoryCardsCount("Equipment","Pending", "PendingLoad", null);
        //line below will return available count before assigning equipment to task
        int availableCountBefore1 = CommonActions.getAnyCategoryCardsCount("Equipment","Available", null, null);
        //line below will return available rear loaders count before assigning equipment to task
        int rearLoadersCountBefore1 = CommonActions.getAnyCategoryCardsCount("Equipment","Available", "RearLoaders", null);
        //line below will check if equipment is present in rear loaders before assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1, "true");

        logTestInfo(wDriver, "verification started on location " + location + " for shift - " + shift1 + " after assigning equipment to task");
        //line below will click on shift header to make the shift active and verify it is started
        taskModeUtilities().shiftStatus(garage, shift1, "Active");
        //line below will return Available count after assigning equipment to task
        CommonActions.getAnyCategoryCardsCountAfter("-1", availableCountBefore1, "Equipment", "Available", null, null);
        //line below will return Rear Loaders count after assigning equipment to task
        CommonActions.getAnyCategoryCardsCountAfter("-1", rearLoadersCountBefore1, "Equipment", "Available", "RearLoaders", null);
        //line below will check if equipment is not present in rear loaders after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1, "false");
        //line below will return Pending Loade count after assigning equipment to task
        CommonActions.getAnyCategoryCardsCountAfter("+1", pendingLoadCountBefore1, "Equipment", "Pending", "PendingLoad", null);
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1, "true");
        //line below will verify equipment present in Relays subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId1, shift1, "COLLECT", "RELAYS", 0, null);
        //line below will verify equipment present in Relays Recycling subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId1, shift1, "COLLECT", "RELAYS_RCY", 0, null);*/

    }


}
