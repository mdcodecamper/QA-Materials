package taskTestSuites.taskAssignmentType;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import person.expectedResults.panels.PersonCardUtilities;
import person.personDetach.actions.DetachActions;
import person.personUnavailable.actions.UnavailableActions;
import utilities.Utilities;


import java.io.IOException;

import static common.actions.CommonActions.deleteAllPersonHistory;
import static common.actions.CommonActions.logTestInfo;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 12/6/2016.
 */
public class TEST003_Minerva4356 extends AbstractStartWebDriver {

    private String location = "QW01";   //EquipmentData.equipmentSendingLocation;
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftStartMinute = "00";
    private String shiftEndMinute = "00";
    private String url = LoginData.getLoginData(getUrl());
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    public static String unavailableCode = "SICK";
    public static String effectiveDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String effectiveHour = "00";
    public static String effectiveMinute = "00";
    public static String endDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endHour = "23";
    public static String endMinute = "59";
    public static String remarks = "Add 'SICK' For Assignment Type";
    public static String detachToLocation = "MN08";
    public static String detachStartDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String detachEndDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String detachEndHour = "12";
    public static String detachEndMinute = "15";
    public static String detachShift = "1215-2015";
    public static String detachRemarks = "Person_Detach_ADD" + Utilities.getUUID();



    @Test(priority = 1,description = "Assign Person from unavailable with SICK to a task for assignment type")
    public void taskAssignmentType_Minerva4356_Unavailable() throws InterruptedException, IOException {
        extentTest.assignCategory( "Regression - Assignment type popup missing when assigning Unavailable Personnel" );
        location = setPersonLocationForTest("Available", "SW", 3);
        System.out.println( "**************************************************************************************" );
        System.out.println( "Regression - Assignment type popup missing when assigning Unavailable Personnel" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Regression - Assignment type popup missing when assigning Unavailable Personnel" );
        //String[] medicalFieldValues = {"","","","","",""};
        //String[] tempFieldValues ={};
        //line below will open current board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close equipment menu to expand task panel
        CommonActions.clickOnMenuIcon( wDriver, "Equipment" );

        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask( wDriver, null );

        //line below will get a person from available san worker catgory without next day assigned
        String personCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned( "Available", "SW", null );

        //line below will delete all person history
        deleteAllPersonHistory( personCardName );

        //line below will add Unavailable 'LODI' code to make the person unavailable from current day
        UnavailableActions.addUnavailable(personCardName, unavailableCode, effectiveDate, effectiveHour, effectiveMinute, endDate, endHour, endMinute, remarks, null, null, "");

        //line below will close personnel detail panel
        CommonActions.closeDetailPanel("Personnel");

        //line below will add a shift
        taskAddShiftActions().addShift( garage, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );

        //line below will add tasks from collection HouseHold subcategory for shift
        taskAddTaskActions().addTasks( garage, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-3/2-3]", 1 );

        //line below will click on edit mode to go back to live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will assign person to task 1 section 1 for household refuse and choose assignment Type
        assignmentTypeActions().assignmentType(garage, personCardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "1",0,1,"The Next Day");


    }// end of method closure


    @Test(priority = 2, description = "Assign Person from Detached to a task for assignment type")
    public void taskAssignmentType_Minerva4356_Detached() throws InterruptedException, IOException {

        extentTest.assignCategory( "Regression - Assignment type popup missing when assigning Detached Personnel" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Regression - Assignment type popup missing when assigning Detached Personnel" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Regression - Assignment type popup missing when assigning Detached Personnel" );

        //line below will get a person from available san worker catgory without next day assigned
        String personDetachCardName = PersonCardUtilities.getAPersonWithoutNextDayAssigned( "Available", "SW", null );

        //line below will delete all person history
        deleteAllPersonHistory( personDetachCardName );

        //line below will add Detach for current day
        DetachActions.addDetach(personDetachCardName, detachToLocation, detachStartDate, detachEndDate, detachEndHour, detachEndMinute, detachRemarks);

        //line below will close personnel detail panel
        CommonActions.closeDetailPanel("Personnel");

        //line below will assign person to task 1 section 2 for household refuse and choose assignment Type
        assignmentTypeActions().assignmentType(garage, personDetachCardName, shift, "COLLECT", "HOUSEHOLD_REFUSE", "2",0,1,"The Next Day");


    }// end of method closure



}// end of main class closure
