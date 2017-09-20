package taskTestSuites.taskAssign.taskSupervisionAssign;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;

import static personTestSuites.testData.PersonData.setPersonLocationForTest;
import static task.abstractBase.TaskBasePage.*;
import static task.abstractBase.TaskBasePage.taskAssignActions;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 12/15/2016.
 */
public class TEST002_Minerva4407 extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation;
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String url = LoginData.getLoginData(getUrl());
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    public static String supCardName;

    @Test(description = "Assign Supervisor To Supervision Route")
    public void taskAssignSupervisorToRoute_Minerva4407() throws InterruptedException, IOException {
        location = setPersonLocationForTest("Available", "SUP", 2);
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke", "Assign Supervisor To Supervision Route");
        logTestInfo(wDriver, "Scenario 2 - block of tasks where supervision was assigned to header comes back after refresh when entire shift is deleted");

        //line below will open current board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close equipment menu
        CommonActions.clickOnMenuIcon(wDriver, "Equipment");

        //line below will close task menu
        //CommonActions.clickOnMenuIcon(wDriver, "Task");

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will person from SUP available category
        supCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SUP",null);

        logTestInfo(wDriver, "verification started on location " + location + " before assigning Supervisor to Supervision Route");

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute);

        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks(garage, shift, "ManagerialSupervision", "Supervisor", "Executive Officer", "4",1);

        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks(garage, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-3/2-0]",1);

        //line below will click on edit mode to go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will assign supervisor to supervisor task 1
        taskAssignActions().assignPersonToTask(garage, supCardName, shift, "SUPV", "EXEC_OFF", null, 0,1);
        //line below will assign supervisor to supervisor Route on household refuse section 1 header
        taskAssignActions().assignSupervisionToRoute(garage, supCardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0,"Header");

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will navigate back to current board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        logTestInfo(wDriver, "verification started on location " + location + " after deleting the shift");
        //line below will check if shift is not present in live mode
        taskModeUtilities().verifyShiftHeaderLiveMode(shift,false);

    }




}
