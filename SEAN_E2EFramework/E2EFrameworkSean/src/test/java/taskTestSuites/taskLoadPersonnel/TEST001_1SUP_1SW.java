package taskTestSuites.taskLoadPersonnel;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import utilities.Utilities;


import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.data.RawColorCodes.getRGBAColorName;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.*;
import static task.loadPersonnel.actions.LoadPersonnelActions.loadPersonnel;


public class TEST001_1SUP_1SW extends AbstractStartWebDriver {

    public static String location = "MN01";
    public static String garage = null;
    public static String boardDate1 = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String boardDate2 = Utilities.getDesiredDateInFormat(1, "yyyyMMdd");
    public static String shiftStartHour = Utilities.get24HourFormat(-2);
    public static String shiftStartMinute = "00";
    public static String shiftEndHour = Utilities.get24HourFormat(6);
    public static String shiftEndMinute = "00";
    public static String url = LoginData.getLoginData(getUrl());
    public static String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    //public static String personSUP1 = null;
    //public static String personSW1 = null;

    @Test(description = "Task_Load_Personnel")
    public void taskLoadPersonnel_1SUP_1SW() throws InterruptedException, IOException {
        //************************************* Add and Assign Shifts on Previous Day*************************************//
        setEquipmentLocationForTest("Available", "RearLoaders", 1);
        location = EquipmentData.equipmentSendingLocation;
        //loginPage().goToBoardLocationByDate(url, location + "/", boardDate1);
        //Data
        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        String personSUP1 = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SUP",null);
        String personSW1 = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        String personSUP1ColorBefore = getRGBAColorName(getRawElementColor(personSUP1, "Personnel"));
        String personSW1ColorBefore = getRGBAColorName(getRawElementColor(personSW1, "Personnel"));

        //line below will delete person future unavailability history for sup
        smartBoardPage().openPersonCardDetailPanel(personSUP1);
        Thread.sleep(1000);
        deleteAllPersonHistory(personSUP1);
        //line below will delete person future unavailability history for sw
        smartBoardPage().openPersonCardDetailPanel(personSW1);
        Thread.sleep(1000);
        deleteAllPersonHistory(personSW1);
        //line below will close personnel detail panel
        CommonActions.closeDetailPanel("Personnel");

        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders");
        taskAddShiftActions().addShift(garage, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute);
        taskAddTaskActions().addTasks(null, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-3/2-4]", 1);

        //line below will perform save template action
        String templateName = "smokeTest - loadPersonnel" + Utilities.getUUID();
        taskTemplateActions().saveTemplate( templateName, "Standard");

        taskAssignActions().assignEquipmentToTask(garage, equipmentId, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0);
        taskAssignActions().assignPersonToTask(garage, personSUP1, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 1);
        taskAssignActions().assignPersonToTask(garage, personSW1, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 2);
        Thread.sleep(1000);

        //************************************* Load Personnel Action On Next Day *************************************//

        loginPage().goToBoardLocationByDate(url, location + "/", boardDate2);

         int availableEquipCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment", "Available", null,null);
        //line below will return available rear loaders count before
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment", "Available", "RearLoaders",null);
        //line below will click on Edit Mode to go to Edit Mode
        taskModeUtilities().clickonEditModeButton();
        //line below will perform load template action
        taskTemplateActions().loadTemplate( templateName, "Standard");
        //line below will click on Edit Mode to go to Live Mode
        taskModeUtilities().clickonEditModeButton();
        //line below will get person assigned sw count before
        int assignedSWCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Assigned", "SW",shift);
        //line below will get person assigned sup count before
        int assignedSUPCountBefore = CommonActions.getAnyCategoryCardsCount("Personnel", "Assigned", "SUP",shift);
        //line below will click on Edit Mode to go to Edit Mode
        taskModeUtilities().clickonEditModeButton();
        //Click and Load Personnel
        loadPersonnel();
        Thread.sleep(3000);
        logTestInfo(wDriver,"verification started on Edit Mode");
        //line below will verify equipment is not present in houehold refuse subcategory on task 1 & section 1
        taskModeUtilities().verifyCardPresentOnTask(false,garage, "Equipment",equipmentId, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "1");
        //line below will verify person 1 present in houehold refuse subcategory on task 1 & section 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Person1",personSUP1, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "1");
        //line below will verify person 2 present in houehold refuse subcategory on task 1 & section 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Person2",personSW1, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "1");
        //line below will verify task is present on subcaegor within a category on a shift for section 1 in Edit Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","3",null,"1");
        //line below will verify task is present on subcaegor within a category on a shift for section 2 in Edit Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","4",null,"3");
        //line below will verify subcategory header is present on Edit Mode in valid format
        taskModeUtilities().verifySubCategoryHeader(null,"HouseHold Refuse",shift,"COLLECT","HOUSEHOLD_REFUSE");

        //line below will click on Edit Mode to go to Live Mode
        taskModeUtilities().clickonEditModeButton();

        logTestInfo(wDriver,"verification started on Live Mode");
        //line below will verify equipment is not present in household refuse subcategory on task 1 & section 1
        taskModeUtilities().verifyCardPresentOnTask(false,garage, "Equipment",equipmentId, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "1");
        //line below will verify person 1 present in houehold refuse subcategory on task 1 & section 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Person1",personSUP1, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "1");
        //line below will verify person 2 present in houehold refuse subcategory on task 1 & section 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage, "Person2",personSW1, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "1");
        //line below will verify task is present on subcaegor within a category on a shift for section 1 in Edit Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","3",null,"1");
        //line below will verify task is present on subcaegor within a category on a shift for section 2 in Edit Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","4",null,"2");
        //line below will verify subcategory header is present on Edit Mode in valid format
        taskModeUtilities().verifySubCategoryHeader(null,"HouseHold Refuse",shift,"COLLECT","HOUSEHOLD_REFUSE");

        //line below will return Available count after
        CommonActions.getAnyCategoryCardsCountAfter("0",availableEquipCountBefore,"Equipment","Available",null, null);
        //line below will return Rear Loaders count after
        CommonActions.getAnyCategoryCardsCountAfter("0",rearLoadersCountBefore,"Equipment","Available","RearLoaders",null);
        //line below verifies person sw assigned count after
        CommonActions.getAnyCategoryCardsCountAfter("+1", assignedSWCountBefore, "Personnel", "Assigned", "SW",shift);
        //line below verifies person sup assigned count after
        CommonActions.getAnyCategoryCardsCountAfter("+1", assignedSUPCountBefore, "Personnel", "Assigned", "SUP",shift);
        String personSUP1ColorAfter = getRGBAColorName(getRawElementColor(personSUP1, "Personnel"));
        String personSW1ColorAfter = getRGBAColorName(getRawElementColor(personSW1, "Personnel"));

    }




}


