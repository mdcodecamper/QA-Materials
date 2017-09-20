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

/**
 * Created by skashem on 12/19/2016.
 */
public class TEST004_Assign_SW_to_Section_NoSection_StartedShift extends AbstractStartWebDriver {

    private String location = "MN01";   //EquipmentData.equipmentSendingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" );
    private String board2Date = Utilities.getDesiredDateInFormat( 1, "yyyyMMdd" );
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String url = LoginData.getLoginData( getUrl() );

    @Test(description = "Partial Route Minerva-4413")
    public void taskPartialRoute_Assign_SW_to_Section_NoSection_StartedShift() throws InterruptedException, IOException {
        extentTest.assignCategory( "Smoke", "assign sanworker to partial link task for section & non-section subcategories for a started shift" );
        location = setPersonLocationForTest("Available", "SW", 2);
        //System.out.println( "**************************************************************************************" );
        //System.out.println( "Scenario 1 - change task description for 3 task numbers in sequence on collection house hold refue on section 1" );
        //System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "assign sanworker to partial link task for section & non-section subcategories for a started shift" );

        //line below will open current board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask( wDriver, null );

        //line below will add an active shift
        taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );

        //line below will add tasks from collection HH Refuse
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-4/2-4]", 1 );

        //line below will add tasks from cleaning Baskets - Regular
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Cleaning", "Baskets - Regular", "5", 2 );

        //line below will add 1st task from section 1 as start route on HouseHold Refuse
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "1", "1", 0, "1/4 (2h)" );
        //line below will add 1st task from section 2 as second route on HouseHold Refuse
        partialRouteActions().partialRoute( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", 0, "2", null, 1, "1/4 (2h)" );
        //line below will add 1st task from cleaning as third route on Baskets - Regular
        partialRouteActions().partialRoute( null, shift, "CLEAN", "BASKETS_REG", 0, null, null, 2, "1/4 (2h)" );
        //line below will add 2nd task from cleaning as fourth route on Baskets - Regular
        partialRouteActions().partialRoute( null, shift, "CLEAN", "BASKETS_REG", 1, null, null, 3, "1/4 (2h)" );
        //line below will click on complete button on route modalwindow
        partialRouteActions().clickComplete();

        //line below will click on Edit Mode to go to Live Mode
        taskModeUtilities().clickonEditModeButton();

        //line below will close equipment menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );

        //line below will get available personnel count before
        int availableCountBefore = getAnyCategoryCardsCount("Personnel","Available",null,null);
        //line below will get available SW count before
        int swCountBefore = getAnyCategoryCardsCount("Personnel","Available","SW",null);
        //line below will get assigned count before
        int assignedCountBefore = getAnyCategoryCardsCount("Personnel","Assigned",null,null);
        //line below will get assigned SW count before
        int assignedSWCountBefore = getAnyCategoryCardsCount("Personnel","Assigned","SW",shift);

        //line below will get SW for person 1 container
        String sw1CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deletePersonHistoryByTable(sw1CardName, "Detach");
        deletePersonHistoryByTable(sw1CardName, "Unavail");
        closeDetailPanel( "Personnel" );
        //line below will assign SW to second set of route on container 1
        taskAssignActions().assignPersonToTask(null, sw1CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 1);

        //line below will get SW for person 1 container
        String sw2CardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned("Available","SW",null);
        deletePersonHistoryByTable(sw2CardName, "Detach");
        deletePersonHistoryByTable(sw2CardName, "Unavail");
        closeDetailPanel( "Personnel" );
        //line below will assign SW to second set of route on container 1
        taskAssignActions().assignPersonToTask(null, sw2CardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1", 0, 2);

        //line below will verify available count after
        getAnyCategoryCardsCountAfter("-2",availableCountBefore,"Personnel","Available",null,null);
        //line below will verify available SW count after
        getAnyCategoryCardsCountAfter("-2",swCountBefore,"Personnel","Available","SW",null);
        //line below will verify assigned count after
        getAnyCategoryCardsCountAfter("+2",assignedCountBefore,"Personnel","Assigned",null,null);
        //line below will verify Assigned SW count after
        getAnyCategoryCardsCountAfter("+2",assignedSWCountBefore,"Personnel","Assigned","SW",shift);

        //line below will verify SW1 appears on section 1 task 1 household refuse on container 1
        taskModeUtilities().verifyCardPresentOnTask(true,null,"Person1",sw1CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1");
        //line below will verify sw1 card is in full color intensity on starting route
        CommonActions.verifyCardOpacityFromTaskPanel( null,"Person1",sw1CardName,"1",shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1");
        //line below will verify SW2 appears on section 1 task 1 household refuse on container 2
        taskModeUtilities().verifyCardPresentOnTask(true,null,"Person2",sw2CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1");
        //line below will verify sw2 card is in full color intensity on starting route on container 2
        CommonActions.verifyCardOpacityFromTaskPanel( null,"Person2",sw2CardName,"1",shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1");

        //line below will verify SW1 appears on section 2 task 1 household refuse on container 1
        taskModeUtilities().verifyCardPresentOnTask(true,null,"Person1",sw1CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"2");
        //line below will verify sw1 card is in half color intensity on second route
        CommonActions.verifyCardOpacityFromTaskPanel( null,"Person1",sw1CardName,"0.4",shift,"COLLECT","HOUSEHOLD_REFUSE",0,"2");
        //line below will verify SW2 appears on section 2 task 1 household refuse on container 2
        taskModeUtilities().verifyCardPresentOnTask(true,null,"Person2",sw2CardName,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"2");
        //line below will verify sw2 card is in half color intensity on second route on container 2
        CommonActions.verifyCardOpacityFromTaskPanel( null,"Person2",sw2CardName,"0.4",shift,"COLLECT","HOUSEHOLD_REFUSE",0,"2");

        //line below will verify SW1 appears on task 1 for Cleaning Baskets - Regular on container 1
        taskModeUtilities().verifyCardPresentOnTask(true,null,"Person1",sw1CardName,shift,"CLEAN","BASKETS_REG",0);
        //line below will verify sw1 card is in half color intensity on third route
        CommonActions.verifyCardOpacityFromTaskPanel( null,"Person1",sw1CardName,"0.4",shift,"CLEAN","BASKETS_REG",0,null);
        //line below will verify SW2 appears on task 1 for Cleaning Baskets - Regular on container 2
        taskModeUtilities().verifyCardPresentOnTask(true,null,"Person2",sw2CardName,shift,"CLEAN","BASKETS_REG",0);
        //line below will verify sw2 card is in half color intensity on third route on container 2
        CommonActions.verifyCardOpacityFromTaskPanel( null,"Person2",sw2CardName,"0.4",shift,"CLEAN","BASKETS_REG",0,null);

        //line below will verify SW1 appears on task 2 for Cleaning Baskets - Regular on container 1
        taskModeUtilities().verifyCardPresentOnTask(true,null,"Person1",sw1CardName,shift,"CLEAN","BASKETS_REG",1);
        //line below will verify sw1 card is in half color intensity on fourth route
        CommonActions.verifyCardOpacityFromTaskPanel( null,"Person1",sw1CardName,"0.4",shift,"CLEAN","BASKETS_REG",1,null);
        //line below will verify SW2 appears on task 2 for Cleaning Baskets - Regular on container 2
        taskModeUtilities().verifyCardPresentOnTask(true,null,"Person2",sw2CardName,shift,"CLEAN","BASKETS_REG",1);
        //line below will verify sw2 card is in half color intensity on fourth route on container 2
        CommonActions.verifyCardOpacityFromTaskPanel( null,"Person2",sw2CardName,"0.4",shift,"CLEAN","BASKETS_REG",1,null);

    }



    }
