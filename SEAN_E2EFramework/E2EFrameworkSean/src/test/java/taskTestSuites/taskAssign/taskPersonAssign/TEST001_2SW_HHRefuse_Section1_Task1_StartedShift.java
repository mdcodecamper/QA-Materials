package taskTestSuites.taskAssign.taskPersonAssign;

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
import static task.abstractBase.TaskBasePage.taskAssignActions;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 12/16/2016.
 */
public class TEST001_2SW_HHRefuse_Section1_Task1_StartedShift extends AbstractStartWebDriver {

    private String location = "MN01";   //EquipmentData.equipmentSendingLocation;
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String url = LoginData.getLoginData(getUrl());
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;

    @Test(description = "Assign Person To Task")
    public void taskAssignPerson_2SW_HHRefuse_Section1_Task1_Started_Shift() throws InterruptedException, IOException {

        CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        CommonActions.clickOnMenuIcon(wDriver, "Equipment");

        int AvailableSWCountBefore = getAnyCategoryCardsCount("Personnel","Available", "SW",null);
        int AssignedSWCountBefore = getAnyCategoryCardsCount("Personnel","Assigned", "SW", shift);

        String person1CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        String person1CardColor = PersonPanelUtilities.getPersonCardColor(person1CardName);

        taskAssignActions().assignPersonToTask(garage, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 1);

        String person2CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        String person2CardColor = PersonPanelUtilities.getPersonCardColor(person2CardName);

        taskAssignActions().assignPersonToTask(garage, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 2);

        //line below will click on shift header to make the shift active and verify it is started
        taskModeUtilities().shiftStatus(garage, shift, "Active");

        taskModeUtilities().verifyPersonCardOnTask(garage, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "1");
        taskModeUtilities().verifyPersonCardOnTask(garage, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "1");
        getAnyCategoryCardsCountAfter("-2",AvailableSWCountBefore,"Personnel","Available", "SW", null);
        getAnyCategoryCardsCountAfter("+2",AssignedSWCountBefore,"Personnel","Assigned", "SW", shift);

        TaskAssignModal.verifyTaskColor(garage, person1CardColor, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "1");
        TaskAssignModal.verifyTaskColor(garage, person2CardColor, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "1");

        softAssert.assertAll();

    }

}
