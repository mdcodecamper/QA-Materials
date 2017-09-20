package taskTestSuites.taskUnassign.taskEquipmentUnassign;

import _driverScript.AbstractStartWebDriver;
import common.actions.TaskActions;
import common.data.LoginData;
import common.actions.CommonActions;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.equipmentColorUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 10/27/2016.
 */
public class TEST001_RearLoaders_HHRefuse_Section2_NotStartedShift extends AbstractStartWebDriver {

    private String location = "BX05";   //EquipmentData.equipmentSendingLocation; //"BKS10";
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour = Utilities.get24HourFormat(2);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(10);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String url = LoginData.getLoginData(getUrl());

    @Test(description = "Remove Equipment From Task")
    public void taskRemoveEquipment_HHRefuse_Section2_NotStartedShift() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","RearLoaders", 2);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Remove Equipment From Task" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - Remove single bin equipment from a HouseHold refuse in section 2 for not started shift" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Scenario 1 - Remove single bin equipment from a HouseHold refuse in section 2 for not started shift" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close person menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Available", "RearLoaders" );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will add a shift
        taskAddShiftActions().addShift(null,shiftStartHour, shiftStartMinute,shiftEndHour,shiftEndMinute);

        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks(null,shift,"SanitationWorker", "Collection", "HouseHold Refuse", "[1-4/2-0]",1 );

        //line below will assign equipment to task
        taskAssignActions().assignEquipmentToTask(null,equipmentId,shift,"COLLECT","HOUSEHOLD_REFUSE","1",0);

        logTestInfo(wDriver, "verification started on location " + location + " before removing equipment from task");
        //line below will click on shift header to make the shift active and verify it is started
        //taskModeUtilities().shiftStatus(null,shift,"Active");

        //line below will return pending load category count before removing equipment from task
       //String pendingLoadCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Pending","PendingLoad",null);
        //line below will return available count before removing equipment from task
        String availableCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Available",null,null);
        //line below will return available rear loaders count before removing equipment from task
        String rearLoadersCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Available","RearLoaders",null);
        //line below will check if equipment is present in not present in rear loaders before removing equipment from task
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "false");
        //line below will check if equipment is present in pending load before removing equipment from task
        //equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingLoad",null,equipmentId,"true");
        //line below will verify equipment present in houehold refuse subcategory on task 1 section 2
        taskModeUtilities().verifyCardPresentOnTask(true,null,"Equipment",equipmentId,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1");


        //line below will remove equipment from task
        taskUnassignActions().removeEquipmentFromTask(null,equipmentId,shift,"COLLECT","HOUSEHOLD_REFUSE","1",0);

        logTestInfo(wDriver, "verification started on location " + location + " after removing equipment from task");
        //line below will return Available count after removing equipment from task
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(+1,availableCountBefore,"Available",null,null);
        //line below will return Rear Loaders count after removing equipment from task
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(+1,rearLoadersCountBefore,"Available","RearLoaders",null);
        //line below will check if equipment is not present in rear loaders after removing equipment from task
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders",null,equipmentId,"true");
        //line below will return Pending Loade count after removing equipment from task
        //equipmentPanelUtilities().getEquipmentCategoryCountAfter(0,pendingLoadCountBefore,"Pending","PendingLoad",null);
        //line below will check if equipment is present in pending load after removing equipment from task
        //equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingLoad",null,equipmentId,"true");
        //line below will verify equipment not present in houehold refuse subcategory on task after removing equipment from task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Equipment",equipmentId,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1");
        //line below will check if equipment color is as expected in household refuse task 1 after removing equipment from task
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId,"Equipment","navyBlue");
        //line below will check if equipment text color is as expected in household refuse task 1 after removing equipment from task
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present in household refuse task 1 after removing equipment from task
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);

    }

    /*@Test(description = "Remove Person From Task")
    public void taskRemovePerson() throws InterruptedException, IOException {
        extentTest.assignCategory( "Smoke", "Tasks" );

        String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
        String emptyTaskColor = "rgba(0, 0, 0, 0)";
        String emptyTaskText = "P";
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");

        //line below will close equipment menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );
        String person1CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        String person1CardColor = PersonPanelUtilities.getPersonCardColor(person1CardName);

        taskAssignActions().assignPersonToTask(garage, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2", 0, 1);

        String person2CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        String person2CardColor = PersonPanelUtilities.getPersonCardColor(person2CardName);

        taskAssignActions().assignPersonToTask(garage, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2", 0, 2);

        int AvailableSWCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Available", "SW");
        int AssignedSWCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Assigned", shift, "SW");

        taskModeUtilities().verifyPersonCardOnTask(garage, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "2");
        taskModeUtilities().verifyPersonCardOnTask(garage, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "2");

        TaskAssignModal.verifyTaskColor(garage, person1CardColor, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "2");
        TaskAssignModal.verifyTaskColor(garage, person2CardColor, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "2");

        taskUnassignActions().removePersonToTask (garage, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2", 0, 1);
        taskUnassignActions().removePersonToTask (garage, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2", 0, 2);

        taskModeUtilities().verifyPersonCardOnTask(garage, "P" , shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "2");
        taskModeUtilities().verifyPersonCardOnTask(garage, "P", shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "2");

        PersonPanelUtilities.verifyCategoryCountAfter(+2, AvailableSWCountBefore, "Available", "SW");
        PersonPanelUtilities.verifyCategoryCountAfter(-2, AssignedSWCountBefore, "Assigned", shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute, "SW");

        TaskAssignModal.verifyTaskColor(garage, emptyTaskColor, emptyTaskText, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 1, "2");
        TaskAssignModal.verifyTaskColor(garage, emptyTaskColor, emptyTaskText , shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, 2, "2");

        softAssert.assertAll();
    }*/



}
