package taskTestSuites.taskDeleteTask;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 11/9/2016.
 */
public class TEST001_with_Section extends AbstractStartWebDriver {


    private String location = "QW01";   //EquipmentData.equipmentSendingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" );
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String url = LoginData.getLoginData( getUrl() );

    @Test(description = "Delete Task")
    public void taskDeleteTask_with_Section() throws InterruptedException, IOException {
        extentTest.assignCategory( "Smoke", "Delete Task" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - delete task 1 from house hold refuse & paper on section 1" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Scenario 1 - delete task 1 from house hold refuse on section 1" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close equipment menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );

        //line below will close personnel menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will add a shift
        taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );

        //line below will add tasks from collection HH Refuse & Paper subcategory
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Collection", "HH Refuse & Paper", "[1-3/2-5]", 1 );

        //line below will delete first task from section 1 on HH Refuse & Paper
        deleteTaskActions().deleteTask(null,shift,"COLLECT","HH_REFUSE_PAPER","1",0);

        logTestInfo( wDriver, "verification started for Edit Mode after Delete Task" );
        //line below will verify task 1 is not present for section 1 in Edit Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HH_REFUSE_PAPER","2",null,"1");

        logTestInfo( wDriver, "verification started for Live Mode after Delete Task" );
        //line below will verify task 1 is not present for section 1 in Live Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HH_REFUSE_PAPER","2","Live Mode","1");



    }











}
