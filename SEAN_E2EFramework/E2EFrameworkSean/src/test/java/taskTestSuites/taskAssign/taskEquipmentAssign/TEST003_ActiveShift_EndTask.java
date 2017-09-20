package taskTestSuites.taskAssign.taskEquipmentAssign;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.equipmentColorUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.*;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 12/13/2016.
 */
public class TEST003_ActiveShift_EndTask extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation;
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String url = LoginData.getLoginData(getUrl());
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;


    @Test(description = "Assign Equipment To Task and End Task")
    public void taskAssignEquipment_BinAndNonBin_to_ActiveShift_and_EndTask() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","DualBins", 1,"MechanicalBrooms");
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke", "Assign Equipment To Task and End Task");
        logTestInfo(wDriver, "Scenario 3 - Ref#Minerva3852 - Assign Bin & Non-Bin equipment to full route on a current shift and End Task");

        //line below will open current board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close person menu
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");

        //line below will close task menu
        CommonActions.clickOnMenuIcon(wDriver, "Task");

        //line below will get Dual Bins Equipment from a category
        String equipmentId1 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        //line below will get Mechanical Brooms Equipment from a category
        String equipmentId2 = equipmentPanelUtilities().getEquipmentFromCategory("NonBin", "Available", "MechanicalBrooms");

        logTestInfo(wDriver, "verification started on location " + location + " before assigning equipment to task");
        //line below will return pending load category count before assigning equipment to task
        int pendingLoadCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending", "PendingLoad", null);
        //line below will return available count before assigning equipment to task
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available", null, null);
        //line below will return available Dual Bins count before assigning equipment to task
        int dualBinsCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available", "DualBins", null);
        //line below will return available Dual Bins count before assigning equipment to task
        int mechBroomsCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available", "MechanicalBrooms", null);

        //line below will check if equipment is present in Dual Bins before assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1, "true");
        //line below will check if equipment is present in Mechanical Brooms before assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "MechanicalBrooms", null, equipmentId2, "true");

        //line below will reopen task menu
        CommonActions.clickOnMenuIcon(wDriver, "Task");

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute);

        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks(garage, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-2/2-0]",1);
        //line below will assign Mechanical Brooms equipment to task 2
        taskAssignActions().assignEquipmentToTask(garage, equipmentId2, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 1);
        //line below will click on edit mode to go back to to edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign Dual Bins equipment to task 1
        taskAssignActions().assignEquipmentToTask(garage, equipmentId1, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0);

        //line below will click on shift header to make the shift active and verify it is started
        taskModeUtilities().shiftStatus(garage, shift, "Active");
        //line below will set task description to T/R1 for task number 1
        taskDetailActions().taskDetails( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", "Collection/HouseHold Refuse", 0, "1", "T/R1", null, null, "End Task", equipmentId1, "P", "P" );
        //line below will set task description to T/R2 for task number 2
        taskDetailActions().taskDetails( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", "Collection/HouseHold Refuse", 1, "1", "T/R2", null, null, "End Task", equipmentId2, "P", "P" );

        logTestInfo(wDriver, "verification started on location " + location + " after assigning equipments to task");

        //line below will check if dual bin equipment color is as expected in household refuse task 1 after assign equipment to task and end task
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId1,"Task","darkGray");
        //line below will check dual bin equipment rear loader bin type is present in household refuse task 1 after assign equipment to task and end task
        equipmentPanelUtilities().equipmentBinType(equipmentId1, "E", "E");
        //line below will check if mechanical brooms equipment color is as expected in household refuse task 1 after assign equipment to task and end task
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId2,"Task","darkGray");

        //line below will return Available count after assigning equipment to task
        CommonActions.getAnyCategoryCardsCountAfter("-1", availableCountBefore, "Equipment","Available", null, null);
        //line below will return Mechanical Brooms count after assigning equipment to task
        CommonActions.getAnyCategoryCardsCountAfter("0", mechBroomsCountBefore, "Equipment","Available", "MechanicalBrooms", null);
        //line below will return Dual Bins count after assigning equipment to task
        CommonActions.getAnyCategoryCardsCountAfter("-1", dualBinsCountBefore, "Equipment","Available", "DualBins", null);
        //line below will return Pending Load count after assigning equipment to task
        CommonActions.getAnyCategoryCardsCountAfter("+1", pendingLoadCountBefore, "Equipment","Pending", "PendingLoad", null);
        //line below will check if equipment is not present in Dual Bins after assigning equipment to task & end Task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId1, "false");
        //line below will check if equipment is present in Mechanical Brooms after assigning equipment to task & end Task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "MechanicalBrooms", null, equipmentId2, "true");
        //line below will check if Dual Bin equipment is present in pending load after assigning equipment to task and end Task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1, "true");
        //line below will verify Dual Bins equipment is present in houehold refuse subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId1, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "1");
        //line below will verify Mechanical Brooms equipment is present in houehold refuse subcategory on task 2
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId2, shift, "COLLECT", "HOUSEHOLD_REFUSE", 1, "1");

    }


}
