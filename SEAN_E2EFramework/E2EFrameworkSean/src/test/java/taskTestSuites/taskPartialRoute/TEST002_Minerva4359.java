package taskTestSuites.taskPartialRoute;

import _driverScript.AbstractStartWebDriver;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;


import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static task.abstractBase.TaskBasePage.*;
import static task.abstractBase.TaskBasePage.partialRouteActions;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 12/8/2016.
 */
public class TEST002_Minerva4359 extends AbstractStartWebDriver {


    private String location = "BX01";   //EquipmentData.equipmentSendingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" );
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String url = LoginData.getLoginData( getUrl() );

    @Test(description = "Partial Route")
    public void taskPartialRoute_Minerva4359() throws InterruptedException, IOException {
        extentTest.assignCategory( "Smoke", "Minerva4359" );
        //System.out.println( "**************************************************************************************" );
        //System.out.println( "Scenario 1 - change task description for 3 task numbers in sequence on collection house hold refue on section 1" );
        //System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Partial Routes - Changing the second route fraction then the first makes complete button disabled" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will add a shift
        taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );

        //line below will add tasks from collection HH Refuse & Paper subcategory
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-3/2-0]", 1 );

        //line below will add first route first as start route
        partialRouteActions().partialRoute(null,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1","1",0,"1/2 (4h)");
        //line below will add second route
        partialRouteActions().partialRoute(null,shift,"COLLECT","HOUSEHOLD_REFUSE",1,"1",null,1,"1/4 (2h)");
        //line below will add third route
        partialRouteActions().partialRoute(null,shift,"COLLECT","HOUSEHOLD_REFUSE",2,"1",null,2,"1/4 (2h)");

        //line below will re edit fraction for second route from 1/4 to 1/2
        partialRouteActions().partialRoute(null,shift,"COLLECT","HOUSEHOLD_REFUSE",1,"1",null,1,"1/2 (4h)");
        //line below will re edit fraction for first route(start route) route from 1/2 to 1/4
        partialRouteActions().partialRoute(null,shift,"COLLECT","HOUSEHOLD_REFUSE",0,"1","1",0,"1/4 (2h)");
        //line below will click on complete button on route modalwindow
        partialRouteActions().clickComplete();


    }


}
