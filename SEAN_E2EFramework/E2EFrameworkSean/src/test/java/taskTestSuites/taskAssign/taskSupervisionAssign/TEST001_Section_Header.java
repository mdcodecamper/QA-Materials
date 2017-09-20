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
import static person.expectedResults.panels.PersonPanelUtilities.checkIfCardNotPresentInCategory;
import static person.expectedResults.panels.PersonPanelUtilities.checkIfCardPresentInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;
import static task.abstractBase.TaskBasePage.*;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 12/14/2016.
 */
public class TEST001_Section_Header extends AbstractStartWebDriver {

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
    public void taskAssignSupervisorToRoute_SectionHeader() throws InterruptedException, IOException {
        location = setPersonLocationForTest("Available", "SUP", 1);
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke", "Assign Supervisor To Supervision Route");
        logTestInfo(wDriver, "Scenario 1 - Assign a Supervisor to active shift on section 1 header for HouseHold Refuse");

        //line below will open current board
        //loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close equipment menu
        CommonActions.clickOnMenuIcon(wDriver, "Equipment");

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will close task menu
        CommonActions.clickOnMenuIcon(wDriver, "Task");

        //line below will get person from SUP available category
        supCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SUP",null);

        //line below will reopen task menu
        CommonActions.clickOnMenuIcon(wDriver, "Task");

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute);

        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks(garage, shift, "ManagerialSupervision", "Supervisor", "Executive Officer", "4",1);

        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks(garage, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-3/2-0]",1);

        //line below will click on edit mode to go to live mode
        taskModeUtilities().clickonEditModeButton();

        logTestInfo(wDriver, "verification started on location " + location + " before assigning Supervisor to Supervision Route");
        //line below will return available count before assigning equipment to task
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Available", null, null);
        //line below will return available supervisor count before assigning supervisor to route
        int supCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Available", "SUP", null);
        //line below will check if person is present in Available SUP category before assigning supervisor to route
        checkIfCardPresentInCategory(supCardName, "Available",null,"SUP");
        //line below will assign supervisor to supervisor task 1
        taskAssignActions().assignPersonToTask(garage, supCardName, shift, "SUPV", "EXEC_OFF", null, 0,1);
        //line below will assign supervisor to supervisor Route on household refuse section 1 header
        taskAssignActions().assignSupervisionToRoute(garage, supCardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0,"Header");

        logTestInfo(wDriver, "verification started on location " + location + " after assigning equipment to task");
        //line below will return Available count after assigning Supervisor to Supervision task
        CommonActions.getAnyCategoryCardsCountAfter("-1", availableCountBefore, "Personnel","Available", null, null);
        //line below will return Available Supervisor count after assigning Supervisor to Supervision task
        CommonActions.getAnyCategoryCardsCountAfter("1", supCountBefore, "Personnel","Available", "SUP", null);
        //line below will check if person is not present in SUP after assigning person to Supervision Route
        checkIfCardNotPresentInCategory(supCardName, "Available","SUP");
        //line below will verify person present in supervisor task 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "person1",supCardName, shift, "SUPV", "EXEC_OFF", 0);
        //line below will verify the right supervisor code appears on section 1  header
        taskModeUtilities().verifySupervisionCode(garage,shift,"COLLECT", "HOUSEHOLD_REFUSE", "1", 0, "Header","XO");
        //line below will verify the right supervisor code appears on section 1  route 1
        taskModeUtilities().verifySupervisionCode(garage,shift,"COLLECT", "HOUSEHOLD_REFUSE", "1", 0, "Route1","XO");
        //line below will verify the right supervisor code appears on section 1  route 2
        taskModeUtilities().verifySupervisionCode(garage,shift,"COLLECT", "HOUSEHOLD_REFUSE", "1", 1, "Route2","XO");
        //line below will verify the right supervisor code appears on section 1  route 3
        taskModeUtilities().verifySupervisionCode(garage,shift,"COLLECT", "HOUSEHOLD_REFUSE", "1", 2, "Route3","XO");

    }

}
