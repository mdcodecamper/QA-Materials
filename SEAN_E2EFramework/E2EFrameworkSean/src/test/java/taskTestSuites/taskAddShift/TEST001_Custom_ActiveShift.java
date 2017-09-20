package taskTestSuites.taskAddShift;

import _driverScript.AbstractStartWebDriver;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.taskAddShiftActions;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 10/21/2016.
 */
public class TEST001_Custom_ActiveShift extends AbstractStartWebDriver {

    private String location = "BKS11";   //EquipmentData.equipmentSendingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "15";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "15";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;

    private String url = LoginData.getLoginData(getUrl());

    @Test(description = "Add Shift")
    public void taskAddShift_Custom_ActiveShift() throws InterruptedException, IOException {


        extentTest.assignCategory( "Smoke", "Add Shift" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - Add a custom active shift" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Scenario 1 - Add a custom active shift" );

        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );
        //line below will delete any shift that has task(s) before adding a new shift
        TaskActions.deleteAllShiftWithTask(wDriver,null);
        //line below will add a shift
        taskAddShiftActions().addShift(null,shiftStartHour,shiftStartMinute,shiftEndHour,shiftEndMinute);
        //line below will check if shift is present on edit mode
        taskModeUtilities().verifyShiftHeaderEditMode(shift,true);
        //line below will click on edit mode to go to live mode
        taskModeUtilities().clickonEditModeButton();
        //line below will check if shift is not present on live mode
        taskModeUtilities().verifyShiftHeaderLiveMode(shift,false);


    }

}
