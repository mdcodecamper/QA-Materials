package taskTestSuites.taskAssign.taskEquipmentAssign;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;


import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.equipmentColorUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 10/25/2016.
 */
public class TEST001_RearLoaders_Available_HHRefuse_Section1_Task1_StartedShift extends AbstractStartWebDriver {

    private String location = "BX05";   //EquipmentData.equipmentSendingLocation;
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String url = LoginData.getLoginData(getUrl());
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;


    @Test(description = "Assign Equipment To Task")
    public void taskAssignEquipment_RearLoaders_HHRefuse_Section1_StartedShift() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","RearLoaders", 2);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke", "Assign Equipment To Task");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Assign single bin equipment to a HouseHold refuse in section one for an active shift");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - Assign single bin equipment to a HouseHold refuse in section one for an active shift");

        //line below will open current board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close person menu
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");

        //line below will close task menu
        CommonActions.clickOnMenuIcon(wDriver, "Task");

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders");

        //line below will reopen task menu
        CommonActions.clickOnMenuIcon(wDriver, "Task");

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will go to live mode to get equipment count before
        //taskModeUtilities().clickonEditModeButton();

        logTestInfo(wDriver, "verification started on location " + location + " before assigning equipment to task");
        //line below will return pending load category count before assigning equipment to task
        int pendingLoadCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending", "PendingLoad", null);
        //line below will return available count before assigning equipment to task
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available", null, null);
        //line below will return available rear loaders count before assigning equipment to task
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available", "RearLoaders", null);
        //line below will check if equipment is present in rear loaders before assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");

        //line below will add a shift
        taskAddShiftActions().addShift(garage, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute);

        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks(garage, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-3/2-0]",1);

        //line below will assign equipment to task
        taskAssignActions().assignEquipmentToTask(garage, equipmentId, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0);

        logTestInfo(wDriver, "verification started on location " + location + " after assigning equipment to task");
        //line below will click on shift header to make the shift active and verify it is started
        taskModeUtilities().shiftStatus(garage, shift, "Active");
        //line below will return Available count after assigning equipment to task
        CommonActions.getAnyCategoryCardsCountAfter("-1", availableCountBefore, "Equipment", "Available", null, null);
        //line below will return Rear Loaders count after assigning equipment to task
        CommonActions.getAnyCategoryCardsCountAfter("-1", rearLoadersCountBefore, "Equipment", "Available", "RearLoaders", null);
        //line below will check if equipment is not present in rear loaders after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "false");
        //line below will return Pending Loade count after assigning equipment to task
        CommonActions.getAnyCategoryCardsCountAfter("+1", pendingLoadCountBefore, "Equipment", "Pending", "PendingLoad", null);
        //line below will check if equipment is present in pending load after assigning equipment to task
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingLoad", null, equipmentId, "true");
        //line below will verify equipment present in household refuse subcategory on task
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Equipment",equipmentId, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "1");
        //line below will check if equipment color is as expected in household refuse task 1 after assign equipment to task
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Task","navyBlue");
        //line below will check if equipment text color is as expected in household refuse task 1 after assign equipment to task
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId, "black");
        //line below will check equipment rear loader bin type is present in household refuse task 1 after assign equipment to task
        equipmentPanelUtilities().equipmentBinType(equipmentId, "E", null);

    }

    /*@Test(dependsOnMethods="taskAssignEquipment",description = "Assign Person To Task")
    public void taskAssignPerson() throws InterruptedException, IOException {

        CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        CommonActions.clickOnMenuIcon(wDriver, "Equipment");
        String person1CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        String person1CardColor = PersonPanelUtilities.getPersonCardColor(person1CardName);

        int AvailableSWCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Available", "SW");
        int AssignedSWCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Assigned", shift, "SW");

        taskAssignActions().assignPersonToTask(garage, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 1);

        String person2CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        String person2CardColor = PersonPanelUtilities.getPersonCardColor(person2CardName);

        taskAssignActions().assignPersonToTask(garage, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 2);

        //line below will click on shift header to make the shift active and verify it is started
        taskModeUtilities().shiftStatus(garage, shift, "Active");

        taskModeUtilities().verifyPersonCardOnTask(garage, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "1");
        taskModeUtilities().verifyPersonCardOnTask(garage, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "1");
        PersonPanelUtilities.verifyCategoryCountAfter(-2, AvailableSWCountBefore, "Available", "SW");
        PersonPanelUtilities.verifyCategoryCountAfter(+2, AssignedSWCountBefore, "Assigned", shift, "SW");

        TaskAssignModal.verifyTaskColor(garage, person1CardColor, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "1");
        TaskAssignModal.verifyTaskColor(garage, person2CardColor, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "1");

        softAssert.assertAll();


    }*/
}
