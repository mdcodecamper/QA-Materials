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
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.*;
import static task.abstractBase.TaskBasePage.taskAssignActions;

/**
 * Created by skashem on 01/24/2017.
 */
public class TEST007_MINERVASNO17_ActiveTask_CurrentDay extends AbstractStartWebDriver {

    private String location = "BKS10";   //EquipmentData.equipmentSendingLocation;
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");

    private String url = LoginData.getLoginData(getUrl());
    private String shiftStartHour1 = Utilities.get24HourFormat(-1);
    private String shiftEndHour1 = Utilities.get24HourFormat(7);
    private String shiftStartMinute = "00";
    private String shiftEndMinute = "00";
    private String shift1 = shiftStartHour1 + shiftStartMinute + "-" + shiftEndHour1 + shiftEndMinute;


    @Test(description = "Assign Equipment To Task")
    public void taskAssignEquipment_MinervaSno17_ActiveTask_CurrentDay() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","RearLoaders", 1, "MechanicalBrooms");
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Assign Equipment To Task" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Assign single bin and non bin equipment to an active task on current day" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Assign single bin and non bin equipment to an active task on current day" );

        //line below will open current board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close person menu
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask( wDriver, null );

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour1, shiftStartMinute, shiftEndHour1, shiftEndMinute);
        //line below will add tasks from collection Relays for shift 1
        taskAddTaskActions().addTasks(garage, shift1, "SanitationWorker", "Collection", "Relays", "2",1);
        //line below will add tasks from cleaning Hand Broom for shift 1
        taskAddTaskActions().addTasks(garage, shift1, "SanitationWorker", "Cleaning", "Hand Broom", "2",2);

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will make shift 1 active
        taskModeUtilities().shiftStatus( null, shift1, "Active" );

        //line below will getEquipment for shift 1 from a category
        String equipmentId1 = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Relays
        taskAssignActions().assignEquipmentToTask(garage, equipmentId1, shift1, "COLLECT", "RELAYS", null, 0);

        //line below will get nonbin Equipment for shift 1 from a category
        String equipmentId2 = equipmentPanelUtilities().getEquipmentFromCategory("NonBin", "Available", "MechanicalBrooms");
        //line below will go to Edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will assign equipment to task on Hand Broom
        taskAssignActions().assignEquipmentToTask(garage, equipmentId2, shift1, "CLEAN", "HAND_BROOM", null, 0);

        //line below will verify equipment present in Relays subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId1, shift1, "COLLECT", "RELAYS", 0);
        //line below will check if equipment is not present in Rear Loaders after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1, "true");

        //line below will verify equipment present in Hand Broom subcategory on task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId2, shift1, "CLEAN", "HAND_BROOM", 0);
        //line below will check if equipment is not present in Mechanical Brooms after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "MechanicalBrooms", null, equipmentId2, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2, "false");



        //line below will open previous day board
        loginPage().goToBoardLocationByDate( url, location + "/", Utilities.getDesiredDateInFormat( -1, "yyyyMMdd" ) );

        //line below will close person menu
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask( wDriver, null );

        //line below will check if equipment is not present in Rear Loaders after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId1, "false");
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId1, "true");

        //line below will check if equipment is  present in Mechanical Brooms
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "MechanicalBrooms", null, equipmentId2, "true");
        //line below will check if equipment is not present in pending load
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId2, "false");

    }


}
