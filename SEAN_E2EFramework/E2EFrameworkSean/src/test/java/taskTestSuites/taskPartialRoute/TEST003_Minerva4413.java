package taskTestSuites.taskPartialRoute;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import utilities.Utilities;


import java.io.IOException;

import static common.actions.CommonActions.*;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;
import static task.abstractBase.TaskBasePage.*;
import static task.loadPersonnel.actions.LoadPersonnelActions.loadPersonnel;

/**
 * Created by skashem on 12/19/2016.
 */
public class TEST003_Minerva4413 extends AbstractStartWebDriver {

    private String location = "BX01";   //EquipmentData.equipmentSendingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" );
    private String board2Date = Utilities.getDesiredDateInFormat( 1, "yyyyMMdd" );
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String url = LoginData.getLoginData( getUrl() );
    private String templateName = "MINERVA-4413 " + Utilities.getUUID();

    @Test(description = "Partial Route Minerva-4413")
    public void taskPartialRoute_Minerva4413() throws InterruptedException, IOException {
        extentTest.assignCategory( "Smoke", "Minerva4413" );
        location = setPersonLocationForTest("Available", "SW", 6);
        //System.out.println( "**************************************************************************************" );
        //System.out.println( "Scenario 1 - change task description for 3 task numbers in sequence on collection house hold refue on section 1" );
        //System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Partial Routes - copying updated source board partial route personnel to target board without new linked route" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask( wDriver, null );

        //line below will add a shift
        taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );

        //line below will add tasks from collection HH Refuse & Paper subcategory
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-6/2-6]", 1 );

        //line below will add 6th task start route from section 2
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 5, "2", "1", 0, "1/2 (4h)" );
        //line below will add 5th task as second route from section 2
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 4, "2", null, 1, "1/2 (4h)" );
        //line below will click on complete button on route modalwindow
        partialRouteActions().clickComplete();

        //line below will add 4th task from section 2 for second set as start route
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 3, "2", "1", 0, "1/2 (4h)" );
        //line below will add 2nd task from section 2 for second set as second route
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 1, "2", null, 1, "1/2 (4h)" );
        //line below will click on complete button on route modalwindow
        partialRouteActions().clickComplete();

        //line below will click on edit mode to go back to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will close equipment menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );

        //line below will get SW for person 1 container for first set of partial link routes
        String person1CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deletePersonHistoryByTable(person1CardName, "Detach");
        deletePersonHistoryByTable(person1CardName, "Unavail");
        closeDetailPanel( "Personnel" );
        //line below will assign SW to second set of route on container 1
        taskAssignActions().assignPersonToTask(null, person1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2", 5, 1);

        //line below will get SW for person 2 container for second set of partial link routes
        String person2CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deletePersonHistoryByTable(person2CardName, "Detach");
        deletePersonHistoryByTable(person2CardName, "Unavail");
        closeDetailPanel( "Personnel" );
        //line below will assign SW to second set of route on container 2
        taskAssignActions().assignPersonToTask(null, person2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2", 5, 2);

        //line below will get SW for person 1 container for first set of partial link routes
        String person3CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deletePersonHistoryByTable(person3CardName, "Detach");
        deletePersonHistoryByTable(person3CardName, "Unavail");
        closeDetailPanel( "Personnel" );
        //line below will assign SW to first set of route on container 1
        taskAssignActions().assignPersonToTask(null, person3CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2", 3, 1);

        //line below will get SW for person 2 container for first set of partial link routes
        String person4CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deletePersonHistoryByTable(person4CardName, "Detach");
        deletePersonHistoryByTable(person4CardName, "Unavail");
        closeDetailPanel( "Personnel" );
        //line below will assign SW to first set of route on container 2
        taskAssignActions().assignPersonToTask(null, person4CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2", 3, 2);

        //line below will click on edit mode to go back to edit mode
        taskModeUtilities().clickonEditModeButton();

        //line below will perform save template action
        taskTemplateActions().saveTemplate( templateName, "Standard");


        //line below will add 1st task from section 2 for third set as start route
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "2", "1", 0, "1/2 (4h)" );
        //line below will add 6th task from section 1 for third set as second route
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 5, "1", null, 1, "1/4 (2h)" );
        //line below will add 5th task from section 1 for third set as third route
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 4, "1", null, 1, "1/4 (2h)" );
        //line below will click on complete button on route modalwindow
        partialRouteActions().clickComplete();

        //line below will add 3rd task from section 1 for fourth set as start route
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 2, "1", "1", 0, "1/2 (4h)" );
        //line below will add 1st task from section 1 for fourth set as second route
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "1", null, 1, "1/2 (4h)" );

        //line below will click on complete button on route modalwindow
        partialRouteActions().clickComplete();

        //line below will click on edit mode to go back to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will get SW for person 1 container for fourth set of partial link routes
        String person5CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deletePersonHistoryByTable(person5CardName, "Detach");
        deletePersonHistoryByTable(person5CardName, "Unavail");
        closeDetailPanel( "Personnel" );
        //line below will assign SW to fourth set of route on container 1
        taskAssignActions().assignPersonToTask(null, person5CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2",0, 1);

        //line below will get SW for person 2 container for fourth set of partial link routes
        String person6CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deletePersonHistoryByTable(person6CardName, "Detach");
        deletePersonHistoryByTable(person6CardName, "Unavail");
        closeDetailPanel( "Personnel" );
        //line below will assign SW to fourth set of route on container 2
        taskAssignActions().assignPersonToTask(null, person6CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2",0, 2);

        //line below will get SW for person 1 container for third set of partial link routes
        String person7CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deletePersonHistoryByTable(person7CardName, "Detach");
        deletePersonHistoryByTable(person7CardName, "Unavail");
        closeDetailPanel( "Personnel" );
        //line below will assign SW to third set of route on container 1
        taskAssignActions().assignPersonToTask(null, person7CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 2, 1);

        //line below will get SW for person 2 container for third set of partial link routes
        String person8CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deletePersonHistoryByTable(person8CardName, "Detach");
        deletePersonHistoryByTable(person8CardName, "Unavail");
        closeDetailPanel( "Personnel" );
        //line below will assign SW to third set of route on container 2
        taskAssignActions().assignPersonToTask(null, person8CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 2, 2);

        //line below will open current day + 1 board
        loginPage().goToBoardLocationByDate( url, location + "/", board2Date );

        //line below will close equipment menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );

        //line below will click on edit mode to go to Edit mode
        taskModeUtilities().clickonEditModeButton();

        //line below will perform load template action
        taskTemplateActions().loadTemplate( templateName, "Standard");

        //line below will perform load personnel action
        loadPersonnel();

        Thread.sleep(5000);

        //verify person5 is not present in section two 1st task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Person1",person5CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"2");
        //verify person5 is not present in section 1 6th task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Person1",person5CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",5,"1");
        //verify person5 is not present in section 1 5th task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Person1",person5CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",4,"1");

        //verify person6 is not present in section two 1st task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Person2",person6CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"2");
        //verify person6 is not present in section 1 6th task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Person2",person6CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",5,"1");
        //verify person6 is not present in section 1 5th task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Person2",person6CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",4,"1");

        //verify person7 is not present in section 1 3rd task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Person1",person7CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",2,"1");
        //verify person7 is not present in section 1 1st task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Person1",person7CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1");

        //verify person8 is not present in section 1 3rd task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Person2",person8CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",2,"1");
        //verify person8 is not present in section 1 1st task
        taskModeUtilities().verifyCardPresentOnTask(false,null,"Person2",person8CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1");


    }








}
