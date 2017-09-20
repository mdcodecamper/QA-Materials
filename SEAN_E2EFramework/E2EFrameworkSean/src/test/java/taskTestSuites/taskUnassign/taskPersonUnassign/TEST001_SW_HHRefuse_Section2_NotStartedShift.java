package taskTestSuites.taskUnassign.taskPersonUnassign;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import person.expectedResults.panels.PersonPanelUtilities;
import task.assignToTask.modals.TaskAssignModal;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.getAnyCategoryCardsCount;
import static common.actions.CommonActions.getAnyCategoryCardsCountAfter;
import static task.abstractBase.TaskBasePage.*;
import static task.abstractBase.TaskBasePage.taskModeUtilities;
import static task.abstractBase.TaskBasePage.taskUnassignActions;

/**
 * Created by skashem on 12/16/2016.
 */
public class TEST001_SW_HHRefuse_Section2_NotStartedShift extends AbstractStartWebDriver {

    private String location = "MN01";   //EquipmentData.equipmentSendingLocation; //"BKS10";
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour = Utilities.get24HourFormat(2);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(10);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String url = LoginData.getLoginData(getUrl());

    @Test(description = "Remove Person From Task")
    public void taskRemovePerson_SW_HHRefuse_Section2_NotStartedShift() throws InterruptedException, IOException {
        extentTest.assignCategory( "Smoke", "Tasks" );

        String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
        String emptyTaskColor = "rgba(0, 0, 0, 0)";
        String emptyTaskText = "P";
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");

        //line below will close equipment menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );
        String person1CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        String person1CardColor = PersonPanelUtilities.getPersonCardColor(person1CardName);

        taskAssignActions().assignPersonToTask(garage, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 1);

        String person2CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        String person2CardColor = PersonPanelUtilities.getPersonCardColor(person2CardName);

        taskAssignActions().assignPersonToTask(garage, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 2);

        int AvailableSWCountBefore = getAnyCategoryCardsCount("Personnel","Available", "SW",null);
        int AssignedSWCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Personnel","Assigned", "SW", shift);

        taskModeUtilities().verifyPersonCardOnTask(garage, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "1");
        taskModeUtilities().verifyPersonCardOnTask(garage, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "1");

        TaskAssignModal.verifyTaskColor(garage, person1CardColor, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "1");
        TaskAssignModal.verifyTaskColor(garage, person2CardColor, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "1");

        taskUnassignActions().removePersonToTask (garage, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 1);
        taskUnassignActions().removePersonToTask (garage, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 2);

        taskModeUtilities().verifyPersonCardOnTask(garage, "P" , shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "1");
        taskModeUtilities().verifyPersonCardOnTask(garage, "P", shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "1");

        getAnyCategoryCardsCountAfter("+2", AvailableSWCountBefore,"Personnel", "Available", "SW", null);
        getAnyCategoryCardsCountAfter("-2", AssignedSWCountBefore, "Personnel","Assigned", "SW", shift);

        TaskAssignModal.verifyTaskColor(garage, emptyTaskColor, emptyTaskText, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "1");
        TaskAssignModal.verifyTaskColor(garage, emptyTaskColor, emptyTaskText , shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "1");

        softAssert.assertAll();
    }



}
