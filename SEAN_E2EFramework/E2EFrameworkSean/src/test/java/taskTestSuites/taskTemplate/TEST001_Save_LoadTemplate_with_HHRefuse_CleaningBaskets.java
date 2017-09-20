package taskTestSuites.taskTemplate;

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
 * Created by skashem on 10/31/2016.
 */
public class TEST001_Save_LoadTemplate_with_HHRefuse_CleaningBaskets extends AbstractStartWebDriver {

    private String location = "QW01";   //EquipmentData.equipmentSendingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" );
    private String boardDate1= Utilities.getDesiredDateInFormat( 1, "yyyyMMdd" );
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String templateNumber = "01";
    private String url = LoginData.getLoginData( getUrl() );
    private String templateName = "smokeTest - saveTemplate" + Utilities.getUUID();
    @Test(description = "Save Template")
    public void taskSaveTemplate_HHRefuse_CleaningBaskets() throws InterruptedException, IOException {

        extentTest.assignCategory( "Smoke", "Save Template" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - save a template for an active shift with collection and cleaning subcategories" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Scenario 1 - save a template for an active shift with collection and cleaning subcategories" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );
        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);
        //line below will add a shift
        taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );
        //line below will add tasks from cleaning Baskets - Regular
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Cleaning", "Baskets - Regular", "3",1 );
        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-3/2-3]",2);
        //line below will perform save template action
        taskTemplateActions().saveTemplate( templateName, "Standard");

    }

    @Test(dependsOnMethods = "taskSaveTemplate_HHRefuse_CleaningBaskets",description = "Load Template")
    public void taskLoadTemplate_HHRefuse_CleaningBaskets() throws InterruptedException, IOException {
        extentTest.assignCategory( "Smoke", "Load Template" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - Load a template for an active shift with collection and cleaning subcategories" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Scenario 1 - Load a template for an active shift with collection and cleaning subcategories" );

        //line below will open current board + 1
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate1 );
        //line below will click on Edit Mode
        taskModeUtilities().clickonEditModeButton();
        //line below will perform load template action
        taskTemplateActions().loadTemplate( templateName, "Standard");

        logTestInfo( wDriver, "verification started for Edit Mode after Load Template" );
        //line below will verify all the task is present for Collection HouseHold Refusefor section 1
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","3",null,"1");
        //line below will verify all the task is present for Collection HouseHold Refusefor section 2
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","3",null,"2");
        //line below will verify subcategory header is present on Edit Mode in valid format for HouseHold Refuse
        taskModeUtilities().verifySubCategoryHeader(null,"HouseHold Refuse",shift,"COLLECT","HOUSEHOLD_REFUSE");
        //line below will verify all the task is present for Cleaning Baskets - Regular
        taskModeUtilities().verifyTaskPresent(true,null,shift,"CLEAN","BASKETS_REG","3",null,null);
        //line below will verify subcategory header is present on Edit Mode in valid format for Baskets - Regular
        taskModeUtilities().verifySubCategoryHeader(null,"Baskets - Regular",shift,"CLEAN","BASKETS_REG");
        //line below will click on Edit Mode to go to live mode
        taskModeUtilities().clickonEditModeButton();
        logTestInfo( wDriver, "verification started for Live Mode after Load Template" );
        //line below will verify all the task is present for Collection HouseHold Refusefor section 1
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","3",null,"1");
        //line below will verify all the task is present for Collection HouseHold Refusefor section 2
        taskModeUtilities().verifyTaskPresent(true,null,shift,"COLLECT","HOUSEHOLD_REFUSE","3",null,"2");
        //line below will verify subcategory header is present on Edit Mode in valid format for HouseHold Refuse
        taskModeUtilities().verifySubCategoryHeader(null,"HouseHold Refuse",shift,"COLLECT","HOUSEHOLD_REFUSE");
        //line below will verify all the task is present for Cleaning Baskets - Regular
        taskModeUtilities().verifyTaskPresent(true,null,shift,"CLEAN","BASKETS_REG","3","Live Mode",null);
        //line below will verify subcategory header is present on Edit Mode in valid format for Baskets - Regular
        taskModeUtilities().verifySubCategoryHeader(null,"Baskets - Regular",shift,"CLEAN","BASKETS_REG");

    }



}
