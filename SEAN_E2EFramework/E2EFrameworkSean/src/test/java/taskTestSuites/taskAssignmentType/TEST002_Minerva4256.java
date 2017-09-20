package taskTestSuites.taskAssignmentType;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import person.expectedResults.panels.PersonPanelUtilities;
import utilities.Utilities;


import java.io.IOException;

import static common.actions.CommonActions.deleteAllPersonHistory;
import static common.actions.CommonActions.logTestInfo;
import static person.expectedResults.panels.PersonCardUtilities.getIndicatorCode;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;
import static task.abstractBase.TaskBasePage.*;
import static task.loadPersonnel.actions.LoadPersonnelActions.loadPersonnel;

/**
 * Created by skashem on 11/30/2016.
 */
public class TEST002_Minerva4256 extends AbstractStartWebDriver {

    private String location = "SI01";       //EquipmentData.equipmentSendingLocation; //"BKS10";
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String board2Date = Utilities.getDesiredDateInFormat(1, "yyyyMMdd");
    private String shift1StartHour = Utilities.get24HourFormat(-1);
    private String shift1EndHour = Utilities.get24HourFormat(7);
    private String shift2StartHour = Utilities.get24HourFormat(15);
    private String shift2EndHour = Utilities.get24HourFormat(23);
    private String shiftStartMinute = "00";
    private String shiftEndMinute = "00";
    private String url = LoginData.getLoginData(getUrl());
    private String shift1 = shift1StartHour + shiftStartMinute + "-" + shift1EndHour + shiftEndMinute;
    private String shift2 = shift2StartHour + shiftStartMinute + "-" + shift2EndHour + shiftEndMinute;
    private String templateNumber = "01";


    @Test(description = "Assign Person as Next Day without Unavailability")
    public void taskAssignmentType_Minerva4256() throws InterruptedException, IOException {
        extentTest.assignCategory( "Regression", "Loading initial assignment in Next Day flow " );
        location = setPersonLocationForTest("Available", "SW", 3);
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 2 - Loading initial assignment in Next Day flow" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Scenario 2 - Loading initial assignment in Next Day flow" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close equipment menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will get a person from available san worker catgory
        //String person1CardName = PersonPanelUtilities.getPersonName("Available", "SW");
        String person1CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);

        //line below will delete person future unavailability history
        deleteAllPersonHistory(person1CardName);

        //line below will close personnel detail panel
        CommonActions.closeDetailPanel("Personnel");

        //line below will check the person's card color
        String person1CardColor = PersonPanelUtilities.getPersonCardColor(person1CardName);

        //line below will add shift1
        taskAddShiftActions().addShift(garage, shift1StartHour, shiftStartMinute, shift1EndHour, shiftEndMinute);

        //line below will add tasks from collection HouseHold subcategory for shift 1
        taskAddTaskActions().addTasks(garage, shift1, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-3/2-0]",1);

        //line below will click on edit mode to go back to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add shift2
        taskAddShiftActions().addShift(garage, shift2StartHour, shiftStartMinute, shift2EndHour, shiftEndMinute);

        //line below will add tasks from collection HouseHold subcategory for shift 2
        taskAddTaskActions().addTasks(garage, shift2, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-0/2-3]",1);

        //line below will perform save template action
        String templateName = "smokeTest - NextDay" + Utilities.getUUID();
        taskTemplateActions().saveTemplate( templateName, "Standard");

        //line below will click on edit mode to go back to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will get person available sw count before assigmentType
        int AvailableSWCountBefore = PersonPanelUtilities.getAnyCategoryHeaderCount("Available", "SW");
        //line below will get person assigned sw count before assigmentType for shift 1
        int AssignedSWCountBefore1 = PersonPanelUtilities.getAnyCategoryHeaderCount("Assigned", shift1, "SW");
        //line below will get person assigned sw count before assigmentType for shift 1
        int AssignedSWCountBefore2 = PersonPanelUtilities.getAnyCategoryHeaderCount("Assigned", shift2, "SW");


        //line below will assign person to task for shift 1
        taskAssignActions().assignPersonToTask(garage, person1CardName, shift1, "COLLECT", "HOUSEHOLD_REFUSE", "1",0,1);
        //line below will verify if the person is present on section 1 household for task number 1 on shift 1
        taskModeUtilities().verifyCardPresentOnTask(true,garage,"person1",person1CardName,shift1,"COLLECT","HOUSEHOLD_REFUSE",0,"1");
        //line below verifies person sw available count after
        PersonPanelUtilities.verifyCategoryCountAfter(-1, AvailableSWCountBefore, "Available", "SW");
        //line below verifies person sw assigned count after for shift 1
        PersonPanelUtilities.verifyCategoryCountAfter(+1, AssignedSWCountBefore1, "Assigned", shift1, "SW");
        //check if person is not present in SW category on current board
        PersonPanelUtilities.checkIfCardNotPresentInCategory(person1CardName, "Available","SW");
        //check if person present in assigned category on current board for shift 1
        PersonPanelUtilities.checkIfCardPresentInCategory(person1CardName, "Assigned",shift1,"SW");

        //line below will assign person to task for shift 2 and choose assignment Type
        assignmentTypeActions().assignmentType(garage, person1CardName, shift2, "COLLECT", "HOUSEHOLD_REFUSE", "2",1,2,"The Next Day");
        //line below will verify if the person is present on section 2 household for task number 2 on shift 2
        taskModeUtilities().verifyCardPresentOnTask(true,garage,"person2",person1CardName,shift2,"COLLECT","HOUSEHOLD_REFUSE",1,"2");
        //code below will check person indicator for next day on current board
        getIndicatorCode(person1CardName, "MadeAvailable");
        //line below verifies person sw assigned count after for shift 2
        PersonPanelUtilities.verifyCategoryCountAfter(+1, AssignedSWCountBefore2, "Assigned", shift2, "SW");
        //check if person present in assigned category on current board for shift 2
        PersonPanelUtilities.checkIfCardPresentInCategory(person1CardName, "Assigned",shift2,"SW");

        //line below will open next day board
        loginPage().goToBoardLocationByDate( url, location + "/", board2Date );
        //line below will close equipment menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );
        //line below will click on Edit Mode
        taskModeUtilities().clickonEditModeButton();
        //line below will perform load template action
        taskTemplateActions().loadTemplate( templateName, "Standard");

        //line below will perform Load Personnel action
        loadPersonnel();

        //line below will click on Edit Mode to go back to Live Mode
        taskModeUtilities().clickonEditModeButton();

        //check if person present in assigned category on next day board for shift 2
        PersonPanelUtilities.checkIfCardPresentInCategory(person1CardName, "Assigned",shift2,"SW");

        //line below will verify if the person is not present on section 1 household for task number 1 on shift 1 afer Load Personnel
        taskModeUtilities().verifyCardPresentOnTask(false,garage,"person1",person1CardName,shift1,"COLLECT","HOUSEHOLD_REFUSE",0,"1");

    }






}
