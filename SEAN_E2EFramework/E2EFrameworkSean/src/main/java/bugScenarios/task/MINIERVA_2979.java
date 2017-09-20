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
import static equipment.abstractBase.EquipmentBasePage.equipmentDownActions;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentUpdateLoadActions;
import static task.abstractBase.TaskBasePage.taskAddShiftActions;
import static task.abstractBase.TaskBasePage.taskAddTaskActions;
import static task.abstractBase.TaskBasePage.taskAssignActions;

/**
 * Created by skashem on 10/18/2016.
 */
public class MINIERVA_2979 extends AbstractStartWebDriver {

    private String location = "QE07";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String url = LoginData.getLoginData(getUrl());
    private String shiftStartHour = Utilities.get24HourFormat(-9);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(-1);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;

    @Test
    public void MINERVA_2979() throws InterruptedException, IOException {

        extentTest.assignCategory( "Smoke", "Bug scenario for bug#2979" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Bug scenario for bug#2979" );
        System.out.println( "**************************************************************************************" );
        logTestInfo(wDriver, "Bug scenario for bug#2979" );

        //line below will open sending location board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //click on person menu to close person panel
        CommonActions.clickOnMenuIcon(wDriver,"Personnel");

        //line below will get Equipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders");

        //line below will add a shift
        taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );

        //line below will add tasks from cleaning intern subcategory
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Cleaning", "M/B \"O\" Route", "3", 1 );

        //line below will assign equipment to task
        taskAssignActions().assignEquipmentToTask(null, equipmentId, shift, "CLEAN", "MB_ORTE", null,0);

        //*************************Update Load***********************************************************************************************
        Thread.sleep(500);
        //line below will click on equipment id
        smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
        Thread.sleep(800);
        boolean loadUpdateExist = wDriver.findElements(By.cssSelector(".auLoadHist .panel-heading.auPanelHeader")).size() > 0;
        if(loadUpdateExist == true){
            equipmentUpdateLoadActions().updateLoad(equipmentId,"Relay","02",null,null);
        }

        //line below will down a equipment
        equipmentDownActions().downEquipment(equipmentId,"DN39","MN01",Utilities.get24HourFormat(0),"00","reporter01","mechanic01","remakrs01",null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        wDriver.findElement(By.xpath("//*[contains(@class,'auActionButton')]")).click(); //click on Action button
        Thread.sleep(500);
        boolean upExist = wDriver.findElements(By.cssSelector(".auActionUp")).size() > 0;
        if(upExist == true){
            logTestInfo(wDriver, "Up action button is available on action drop down");
        } else {
            logTestFailure(wDriver, "Up action button is not available on action drop down");
        }


    }


}
