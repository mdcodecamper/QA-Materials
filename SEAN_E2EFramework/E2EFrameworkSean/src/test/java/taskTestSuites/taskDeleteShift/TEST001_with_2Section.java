package taskTestSuites.taskDeleteShift;

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
 * Created by ccollapally on 10/25/2016.
 */
public class TEST001_with_2Section extends AbstractStartWebDriver {

    private String location = "QW01";   // EquipmentData.equipmentSendingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "15";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "15";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String url = LoginData.getLoginData(getUrl());

    @Test(description = "Delete Shift")
    public void taskDeleteShift_with_2Section() throws InterruptedException, IOException {

        extentTest.assignCategory( "Smoke", "Delete Shift" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - delete a custom shift which has assigned to it" );
        System.out.println( "**************************************************************************************" );
        logTestInfo(wDriver, "Scenario 1 - delete a custom shift which has tasks assigned to it" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will add a shift
        taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );
        //line below will scroll into a shift
        taskModeUtilities().scrollIntoShift(null,shift);
        //line below will add tasks from collection HH Refuse & Paper subcategory
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Collection", "HH Refuse & Paper", "[1-3/2-5]", 1 );

        //implicit wait after submit for one second to speed up the process
        //wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        //line below will delete the shift
        taskDeleteShiftActions().deleteShift(null,shift);
        //line below will verify tasks are not present for section 1 in Edit Mode
        taskModeUtilities().verifyTaskPresent(false,null,shift,"COLLECT","HH_REFUSE_PAPER","3",null,"1");
        //line below will verify tasks are not present for section 2 in Edit Mode
        taskModeUtilities().verifyTaskPresent(false,null,shift,"COLLECT","HH_REFUSE_PAPER","5",null,"2");
        //line below will check if shift is not present in live mode
        taskModeUtilities().verifyShiftHeaderLiveMode(shift,false,"Live Mode");
    }

}
