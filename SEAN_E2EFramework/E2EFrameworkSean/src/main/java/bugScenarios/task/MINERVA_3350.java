package bugScenarios.task;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.CommonActions;
import common.data.LoginData;
import common.actions.TaskActions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static task.abstractBase.TaskBasePage.taskAddShiftActions;
import static task.abstractBase.TaskBasePage.taskAddTaskActions;
import static task.abstractBase.TaskBasePage.taskAssignActions;

/**
 * Created by skashem on 10/18/2016.
 */
public class MINERVA_3350 extends AbstractStartWebDriver{

    private String location = "BKN03";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String url = LoginData.getLoginData(getUrl());
    private String shiftStartHour = Utilities.get24HourFormat(-8);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(0);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;

    @Test
    public void MINERVA_3350() throws InterruptedException, IOException {

        extentTest.assignCategory( "Smoke", "Bug scenario for bug#3350" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Bug scenario for bug#3350" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Bug scenario for bug#3350" );

        //line below will open sending location board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //click on person menu to close person panel
        CommonActions.clickOnMenuIcon(wDriver,"Personnel");

        //line below will get Equipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("NonBin","Available","MechanicalBrooms");

        //line below will add a shift
        taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );

        //line below will add tasks from cleaning intern subcategory
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Cleaning", "M/B \"O\" Route", "3", 1 );

         //line below will assign equipment to task
        taskAssignActions().assignEquipmentToTask(null, equipmentId, shift, "CLEAN", "MB_ORTE", null,0);

        //line below will refresh to the board again
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        try{
            //line below will click on equipment id
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            logTestPass(wDriver, "Board hasn't crashed and element is still clickable");
        } catch(Exception e) {
            logTestFailure(wDriver, "Board crashed and element is not clickable");
        }



    }



}
