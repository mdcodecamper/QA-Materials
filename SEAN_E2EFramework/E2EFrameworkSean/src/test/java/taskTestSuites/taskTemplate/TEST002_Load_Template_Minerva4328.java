package taskTestSuites.taskTemplate;

import _driverScript.AbstractStartWebDriver;
import common.actions.TaskActions;
import common.data.LoginData;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilities.Utilities;


import java.io.IOException;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 12/2/2016.
 */
public class TEST002_Load_Template_Minerva4328 extends AbstractStartWebDriver {

    private String location = "QW01";   //EquipmentData.equipmentSendingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" );
    private String board2Date= Utilities.getDesiredDateInFormat( 1, "yyyyMMdd" );
    private String shiftStartHour = Utilities.get24HourFormat(0);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(8);
    private String shiftEndMinute = "00";
    private String shiftNewStartHour = Utilities.get24HourFormat(-1);
    private String shiftNewStartMinute = "00";
    private String shiftNewEndHour = Utilities.get24HourFormat(7);
    private String shiftNewEndMinute = "00";
    private String shiftNew2StartHour = Utilities.get24HourFormat(1);
    private String shiftNew2StartMinute = "00";
    private String shiftNew2EndHour = Utilities.get24HourFormat(9);
    private String shiftNew2EndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String shiftNew = shiftNewStartHour + shiftNewStartMinute + "-" + shiftNewEndHour + shiftNewEndMinute;
    private String shiftNew2 = shiftNew2StartHour + shiftNew2StartMinute + "-" + shiftNew2EndHour + shiftNew2EndMinute;
    private String templateNumber = "01";
    private String url = LoginData.getLoginData( getUrl() );
    private String templateName = "smokeTest - Minerva4328" + Utilities.getUUID();

    @Test(description = "Load Template")
    public void taskLoadTemplate_MINERVA4328() throws InterruptedException, IOException {

        extentTest.assignCategory( "Smoke", "MINERVA-4328" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "MINERVA4328 - Updating Shift Start Time to an Earlier Time does not Reflect Correctly after F5" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "MINERVA4328 - Updating Shift Start Time to an Earlier Time does not Reflect Correctly after F5" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );
        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask( wDriver, null );
        //line below will add a shift
        taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );
        //line below will add tasks from cleaning Baskets - Regular
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Cleaning", "Baskets - Regular", "3", 1 );
        //line below will add tasks from collection HouseHold subcategory
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Collection", "HouseHold Refuse", "[1-3/2-3]", 2 );
        //line below will perform save template action
        taskTemplateActions().saveTemplate( templateName, "Standard" );
        //line below will open next day board
        loginPage().goToBoardLocationByDate( url, location + "/", board2Date );
        //line below will click on Edit Mode
        taskModeUtilities().clickonEditModeButton();
        //line below will perform load template action
        taskTemplateActions().loadTemplate( templateName, "Standard");
        //line below will edit a shift
        taskModeUtilities().editShit( null,shift,shiftNewStartHour,shiftNewStartMinute);
        //line below will refresh to next day board page
        loginPage().goToBoardLocationByDate( url, location + "/", board2Date );
        if(wDriver.findElements( By.xpath("//*[contains(@class,'auShift-" + shiftNew + "')]")).size() > 0){
            //line below will click on Edit Mode
            taskModeUtilities().clickonEditModeButton();
            //line below will edit a shift and change to different shift
            taskModeUtilities().editShit(null,shiftNew,shiftNew2StartHour,shiftNew2StartMinute);
            //line below will refresh to next day board page
            loginPage().goToBoardLocationByDate( url, location + "/", board2Date );
            if(wDriver.findElements( By.xpath("//*[contains(@class,'auShift-" + shiftNew + "')]")).size() > 0){
                logTestFailure(wDriver,"Expected new shift " + shiftNew2 + " but found old edited shift - " + shiftNew);
            } else {
                logTestPass( wDriver, "found newer shift " + shiftNew2 );
            }

        } else {

            if(wDriver.findElements( By.xpath("//*[contains(@class,'auShift-" + shift + "')]")).size() > 0) {
                logTestFailure(wDriver,"Expected new shift " + shiftNew + " but found old shift - " + shift);
            } else {
                logTestPass( wDriver, "found new shift " + shiftNew );
            }
        }


    }



}
