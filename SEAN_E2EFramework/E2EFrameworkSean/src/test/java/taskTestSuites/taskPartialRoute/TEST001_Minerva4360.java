package taskTestSuites.taskPartialRoute;

import _driverScript.AbstractStartWebDriver;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 11/14/2016.
 */
public class TEST001_Minerva4360 extends AbstractStartWebDriver {


    private String location = "BX01";   //EquipmentData.equipmentSendingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" );
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String url = LoginData.getLoginData( getUrl() );

        @Test(priority = 1,description = "Task Details")
        public void taskDetails() throws InterruptedException, IOException {

            extentTest.assignCategory( "Smoke", "Task Details" );
            //System.out.println( "**************************************************************************************" );
            //System.out.println( "Scenario 1 - change task description for 3 task numbers in sequence on collection house hold refue on section 1" );
            //System.out.println( "**************************************************************************************" );
            logTestInfo( wDriver, "Scenario 1 - change task description for 3 task numbers in sequence on collection house hold refue on section 1" );

            //line below will open current board
            loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

            //line below will delete any shift that has task(s) before adding a new shift
            TaskActions.deleteAllShiftWithTask(wDriver,null);

            //line below will add a shift
            taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );

            //line below will add tasks from collection HH Refuse & Paper subcategory
            taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-3/2-0]", 1 );

            //line below will click on edit button to go to live mode
            taskModeUtilities().clickonEditModeButton();

            //line below will click on shift header to make the shift active and verify it is started
            taskModeUtilities().shiftStatus(null, shift, "Active");

            //line below will set task description to T/R1 for task number 1
            taskDetailActions().taskDetails( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", "Collection/HouseHold Refuse", 0, "1", "T/R1", null, null, null, "E", "P", "P" );
            //line below will set task description to T/R2 for task number 2
            taskDetailActions().taskDetails( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", "Collection/HouseHold Refuse", 1, "1", "T/R2", null, null, null, "E", "P", "P" );
            //line below will set task description to T/R3 for task number 3
            taskDetailActions().taskDetails( null, shift, "COLLECT", "HOUSEHOLD_REFUSE", "Collection/HouseHold Refuse", 2, "1", "T/R3", null, null, null, "E", "P", "P" );

        }

        @Test(priority = 2,description = "Partial Route")
        public void taskPartialRoute_Minerva4360() throws InterruptedException, IOException {

            extentTest.assignCategory( "Smoke", "Partial Route" );
            //System.out.println( "**************************************************************************************" );
            //System.out.println( "Scenario 1 - Assign three Partial Routes to collection house hold refuse on section 1" );
            //System.out.println( "**************************************************************************************" );
            logTestInfo( wDriver, "Scenario 1 - Assign three Partial Routes to collection house hold refuse on section 1" );

            //line below will click on edit button to go to Edit mode
            taskModeUtilities().clickonEditModeButton();

            //line below will add first route first as start route
            partialRouteActions().partialRoute(null,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1","1",0,"1/2 (4h)");
            //line below will add second route
            partialRouteActions().partialRoute(null,shift,"COLLECT","HOUSEHOLD_REFUSE",1,"1",null,1,"1/4 (2h)");
            //line below will add third route
            partialRouteActions().partialRoute(null,shift,"COLLECT","HOUSEHOLD_REFUSE",2,"1",null,2,"1/4 (2h)");
            //line below will click on complete button on route modalwindow
            partialRouteActions().clickComplete();

            logTestInfo( wDriver, "verification started on Edit Mode after partial route" );
            //line below will verify partial Start route icon routes on task 1 in edit mode
            taskModeUtilities().verifyPartialRouteIconOnTask(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"start","1");
            //line below will verify partial Non Start route icon on task 2 in edit mode
            taskModeUtilities().verifyPartialRouteIconOnTask(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE",1,"","1");
            //line below will verify partial icon Non Start route on task 3 in edit mode
            taskModeUtilities().verifyPartialRouteIconOnTask(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE",2,"","1");

            //line below will verify route sequence for route 1 on route pop up window
            taskModeUtilities().verifyPartialRouteIconOnRouteWindow(null,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"HouseHold Refuse1","T/R1",1,3,"1/2 (4h)","1");
            //line below will verify route sequence for route 2(start route) on route pop up window
            taskModeUtilities().verifyPartialRouteIconOnRouteWindow(null,shift,"COLLECT","HOUSEHOLD_REFUSE",1,"HouseHold Refuse1","T/R2",2,3,"1/4 (2h)","1");
            //line below will verify route sequence for route 3 on route pop up window
            taskModeUtilities().verifyPartialRouteIconOnRouteWindow(null,shift,"COLLECT","HOUSEHOLD_REFUSE",2,"HouseHold Refuse1","T/R3",3,3,"1/4 (2h)","1");

            logTestInfo( wDriver, "verification started on Live Mode after partial route" );
            //line below will verify partial Start route icon routes on task 1 in live mode
            taskModeUtilities().verifyPartialRouteIconOnTask(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"start","1");
            //line below will verify partial Non Start route icon on task 2 in live mode
            taskModeUtilities().verifyPartialRouteIconOnTask(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE",1,"","1");
            //line below will verify partial icon Non Start route on task 3 in live mode
            taskModeUtilities().verifyPartialRouteIconOnTask(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE",2,"","1");

            //line below will verify route sequence for route 1 on route pop up window
            taskModeUtilities().verifyPartialRouteIconOnRouteWindow(null,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"HouseHold Refuse1","T/R1",1,3,"1/2 (4h)","1");
            //line below will verify route sequence for route 2(start route) on route pop up window
            taskModeUtilities().verifyPartialRouteIconOnRouteWindow(null,shift,"COLLECT","HOUSEHOLD_REFUSE",1,"HouseHold Refuse1","T/R2",2,3,"1/4 (2h)","1");
            //line below will verify route sequence for route 3 on route pop up window
            taskModeUtilities().verifyPartialRouteIconOnRouteWindow(null,shift,"COLLECT","HOUSEHOLD_REFUSE",2,"HouseHold Refuse1","T/R3",3,3,"1/4 (2h)","1");

        }

    }
