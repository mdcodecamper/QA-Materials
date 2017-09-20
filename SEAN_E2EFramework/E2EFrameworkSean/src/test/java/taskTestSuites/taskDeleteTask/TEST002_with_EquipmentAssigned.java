package taskTestSuites.taskDeleteTask;

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

/**
 * Created by skashem on 12/12/2016.
 */
public class TEST002_with_EquipmentAssigned extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation;
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String url = LoginData.getLoginData(getUrl());
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;

    @Test(description = "Delete a task with equipment")
    public void taskDeleteTask_with_Equipment() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "RearLoaders", 4 );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Smoke", "Delete a Task with Equipment" );
        logTestInfo( wDriver, "Scenario 2 - Tasks delete route when route has equipment attached" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close person menu
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

        //line below will close task menu
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Available", "RearLoaders", "Down" );

        //line below will reopen task menu
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask( wDriver, null );

        //line below will add a shift
        taskAddShiftActions().addShift( garage, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );

        //line below will add tasks from cleaning baskets - regular category
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Cleaning", "Baskets - Regular", "3",1 );

        //line below will assign equipment to task
        taskAssignActions().assignEquipmentToTask( garage, equipmentId, shift, "CLEAN", "BASKETS_REG", null, 0 );

        logTestInfo( wDriver, "verification started on location " + location + " before deleting a task with equipment" );
        //line below will return available count before deleting a task with equipment
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount( "Equipment", "Available", null, null );
        //line below will return available rear loaders count before deleting a task with equipment
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount( "Equipment", "Available", "RearLoaders", null );
        //line below will check if equipment is not present in rear loaders before deleting a task with equipment
        equipmentPanelUtilities().verifyEquipmentPresent( "Available", "RearLoaders", null, equipmentId, "false" );

        //line below will go back to edit mode
        taskModeUtilities().clickonEditModeButton();
        //line below will delete first task from Cleaning Baskets - Regular
        deleteTaskActions().deleteTask(null,shift,"CLEAN","BASKETS_REG",null,0);

        logTestInfo( wDriver, "verification started for Edit Mode after Deleting a Task with equipment" );
        //line below will verify task 1 is not present in baskets- regular for edit mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"CLEAN","BASKETS_REG","2");

        logTestInfo( wDriver, "verification started for Live Mode after Deleting a Task with equipment" );
        //line below will verify task 1 is not present in baskets- regular for live mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"CLEAN","BASKETS_REG","2","Live Mode");

        logTestInfo( wDriver, "verification started for Equipment after Deleting a Task with equipment" );
        //line below will check if equipment is present in rear loaders before deleting a task with equipment
        equipmentPanelUtilities().verifyEquipmentPresent( "Available", "RearLoaders", null, equipmentId, "true" );
       //line below will return available count after deleting a task with equipment
        CommonActions.getAnyCategoryCardsCountAfter("+1",availableCountBefore,"Equipment","Available",null,null);
        //line below will return rearloaders count after deleting a task with equipment
        CommonActions.getAnyCategoryCardsCountAfter("+1",rearLoadersCountBefore,"Equipment","Available","RearLoaders",null);



    }













    }
