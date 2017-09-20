package taskTestSuites.taskAddTasks;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestInfo;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 10/25/2016.
 */
public class TEST003_Recycling_Collection_MultipleShifts_DualGarage extends AbstractStartWebDriver {

    private String location = "QE11";   //EquipmentData.equipmentSendingLocation;
    private String garage = "QE11W";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour1 = "12"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute1 = "00";
    private String shiftEndHour1 = "20"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute1 = "00";
    private String shiftStartHour2 = "13"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute2 = "00";
    private String shiftEndHour2 = "21"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute2 = "00";
    private String shiftStartHour3 = "14"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute3 = "00";
    private String shiftEndHour3 = "22"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute3 = "00";
    private String shiftStartHour4 = "15"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute4 = "00";
    private String shiftEndHour4 = "23"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute4 = "00";
    private String shiftStartHour5 = "16"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute5 = "00";
    private String shiftEndHour5 = "00"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute5 = "00";
    private String shiftStartHour6 = "17"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute6 = "00";
    private String shiftEndHour6 = "01"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute6 = "00";
    private String shiftStartHour7 = "18"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute7 = "00";
    private String shiftEndHour7 = "02"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute7 = "00";
    private String shiftStartHour8 = "19"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute8 = "00";
    private String shiftEndHour8 = "03"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute8 = "00";
    private String shiftStartHour9 = "20"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute9 = "00";
    private String shiftEndHour9 = "04"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute9 = "00";
    private String shiftStartHour10 = "21"; //Utilities.get24HourFormat(-1);
    private String shiftStartMinute10 = "00";
    private String shiftEndHour10 = "05"; //Utilities.get24HourFormat(7);
    private String shiftEndMinute10 = "00";
    private String url = LoginData.getLoginData(getUrl());
    private String shift1 = "1200-2000";
    private String shift2 = "1300-2100";
    private String shift3 = "1400-2200";
    private String shift4 = "1500-2300";
    private String shift5 = "1600-0000";
    private String shift6 = "1700-0100";
    private String shift7 = "1800-0200";
    private String shift8 = "1900-0300";
    private String templateName = "Snow Fixes - saveTemplate" + Utilities.getUUID();

    @Test(description = "Assign Equipment To Task")
    public void taskAssignEquipment_Minerva4606_Assign() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","DualBins", 14);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Smoke", "Assign Equipment To Task" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - Assign single bin equipment to a collection and RECYCLE for a over night shift" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Scenario 1 - Assign single bin equipment to a collection and RECYCLE for a over night shift on dual garage" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will go to Edit mode
       // taskModeUtilities().clickonEditModeButton();

       //line below will close person menu
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask( wDriver, null );

        //line below will add a shift
        taskAddShiftActions().addShift( garage, shiftStartHour1, shiftStartMinute1, shiftEndHour1, shiftEndMinute1 );

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift( garage, shiftStartHour2, shiftStartMinute2, shiftEndHour2, shiftEndMinute2 );

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift( garage, shiftStartHour5, shiftStartMinute5, shiftEndHour5, shiftEndMinute5 );

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift( garage, shiftStartHour6, shiftStartMinute6, shiftEndHour6, shiftEndMinute6 );

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift( garage, shiftStartHour7, shiftStartMinute7, shiftEndHour7, shiftEndMinute7 );

        //line below will go to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will add a shift
        taskAddShiftActions().addShift( garage, shiftStartHour8, shiftStartMinute8, shiftEndHour8, shiftEndMinute8 );

        //line below will add tasks from collection Relays for shift 1
        taskAddTaskActions().addTasks( garage, shift1, "SanitationWorker", "Collection", "Relays", "2", 1 );
        //line below will add tasks from RECYCLE Relays for shift 1
        taskAddTaskActions().addTasks( garage, shift1, "SanitationWorker", "Recycling", "Relays Rcy", "2", 2 );

        //line below will add tasks from collection Relays for shift 2
        taskAddTaskActions().addTasks( garage, shift2, "SanitationWorker", "Collection", "Relays", "2", 1 );
        //line below will add tasks from RECYCLE Relays for shift 2
        taskAddTaskActions().addTasks( garage, shift2, "SanitationWorker", "Recycling", "Relays Rcy", "2", 2 );

        //line below will add tasks from collection Relays for shift 5
        taskAddTaskActions().addTasks( garage, shift5, "SanitationWorker", "Collection", "Relays", "2", 1 );
        //line below will add tasks from RECYCLE Relays for shift 5
        taskAddTaskActions().addTasks( garage, shift5, "SanitationWorker", "Recycling", "Relays Rcy", "2", 2 );

        //line below will add tasks from collection Relays for shift 6
        taskAddTaskActions().addTasks( garage, shift6, "SanitationWorker", "Collection", "Relays", "2", 1 );
        //line below will add tasks from RECYCLE Relays for shift 6
        taskAddTaskActions().addTasks( garage, shift6, "SanitationWorker", "Recycling", "Relays Rcy", "2", 2 );

        //line below will add tasks from collection Relays for shift 7
        taskAddTaskActions().addTasks( garage, shift7, "SanitationWorker", "Collection", "Relays","2", 1);
        //line below will add tasks from RECYCLE Relays for shift 7
        taskAddTaskActions().addTasks( garage, shift7, "SanitationWorker", "Recycling", "Relays Rcy", "2", 2);

        //line below will add tasks from collection Relays for shift 8
        taskAddTaskActions().addTasks( garage, shift8, "SanitationWorker", "Collection", "Relays", "2", 1 );
        //line below will add tasks from RECYCLE Relays for shift 8
        taskAddTaskActions().addTasks( garage, shift8, "SanitationWorker", "Recycling", "Relays Rcy", "2", 2 );

        //line below will perform Save Template action
        taskTemplateActions().saveTemplate( templateName, "Standard");

        for(int i=1; i <= 7; i++){
            //this will create shift for remaining days
            loginPage().goToBoardLocationByDate( url, location + "/", Utilities.getDesiredDateInFormat(i, "yyyyMMdd") );

            //line below will close equipment menu
            CommonActions.clickOnMenuIcon( wDriver, "Equipment" );

            //line below will close person menu
            CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

            //line below will go to Edit Mode
            taskModeUtilities().clickonEditModeButton();

            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

            //line below will perform Load Template action
            taskTemplateActions().loadTemplate(templateName, "Standard");


        }


    }

}
