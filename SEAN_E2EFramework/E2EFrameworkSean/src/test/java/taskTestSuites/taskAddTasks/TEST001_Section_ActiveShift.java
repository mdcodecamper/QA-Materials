package taskTestSuites.taskAddTasks;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.taskAddShiftActions;
import static task.abstractBase.TaskBasePage.taskAddTaskActions;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 10/24/2016.
 */
public class TEST001_Section_ActiveShift extends AbstractStartWebDriver {

    private String location = "BKS11";   //EquipmentData.equipmentSendingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" );
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String url = LoginData.getLoginData( getUrl() );

    @Test(description = "Add Tasks")
    public void taskAddTasks() throws InterruptedException, IOException {

        extentTest.assignCategory( "Smoke", "Add Tasks" );
        logTestInfo( wDriver, "Scenario 1 - create multiple task for Collection HouseHold Refuse & Passover Refuse for an active shift" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close person menu
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");

        //line below will close Equipment menu
        //CommonActions.clickOnMenuIcon(wDriver, "Equipment");

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);

        //line below will add a shift
        taskAddShiftActions().addShift(null,shiftStartHour,shiftStartMinute,shiftEndHour,shiftEndMinute);

        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks(null,shift,"SanitationWorker","Collection","HouseHold Refuse_Passover Refuse","[1-3/2-5]_[1-3/2-5]",1);


        logTestInfo( wDriver, "verification started for Edit Mode after add tasks" );
        //line below will verify task is present on subcaegor within a category on a shift for section 1 in Edit Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","3",null,"1");
        //line below will verify task is present on subcaegor within a category on a shift for section 2 in Edit Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","5",null,"2");
        //line below will verify subcategory header is present on Edit Mode in valid format
        taskModeUtilities().verifySubCategoryHeader(null,"HouseHold Refuse",shift,"COLLECT","HOUSEHOLD_REFUSE");
        //line below will verify task is present on Passover subcategory within a category on a shift for section 1 in Edit Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","PASSOVER_REFUSE","3",null,"1");
        //line below will verify task is present on subcaegor within a category on a shift for section 2 in Edit Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","PASSOVER_REFUSE","5",null,"2");
        //line below will verify subcategory header is present on Edit Mode in valid format
        taskModeUtilities().verifySubCategoryHeader(null,"Passover Refuse",shift,"COLLECT","PASSOVER_REFUSE");

        logTestInfo( wDriver, "verification started for Live Mode after add tasks" );
        //line below will verify task is present on subcaegor within a category on a shift for section 1 in Live Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","3","Click on Edit Mode","1");
        //line below will verify task is present on subcaegor within a category on a shift for section 2 in Live Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","5","Live Mode","2");
        //line below will verify subcategory header is present on Live Mode in valid format
        taskModeUtilities().verifySubCategoryHeader(null,"HouseHold Refuse",shift,"COLLECT","HOUSEHOLD_REFUSE");
        //line below will verify task is present on Passover subcategory within a category on a shift for section 1 in Live Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","PASSOVER_REFUSE","3",null,"1");
        //line below will verify task is present on subcaegor within a category on a shift for section 2 in Live Mode
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","PASSOVER_REFUSE","5",null,"2");
        //line below will verify subcategory header is present on Live Mode in valid format
        taskModeUtilities().verifySubCategoryHeader(null,"Passover Refuse",shift,"COLLECT","PASSOVER_REFUSE");
    }

}
