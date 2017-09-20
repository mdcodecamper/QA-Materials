package bugScenarios.task;


import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.CommonActions;
import common.data.LoginData;
import common.actions.TaskActions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;
import static equipment.abstractBase.EquipmentBasePage.*;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 10/18/2016.
 */
public class MINERVA_3326 extends AbstractStartWebDriver {

    private String location = "QE07";
    private String detachTo ="BKN01";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String url = LoginData.getLoginData(getUrl());
    private String detachHour =  Utilities.get24HourFormat(0);
    private String detachMinute = "00";
    private String shiftStartHour = Utilities.get24HourFormat(-1);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(7);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;

    @Test
    public void MINERVA_3326() throws InterruptedException, IOException {

        extentTest.assignCategory( "Smoke", "Bug scenario for bug#3326" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Bug scenario for bug#3326" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Bug scenario for bug#3326" );

        //line below will open sending location board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //click on person menu to close person panel
        CommonActions.clickOnMenuIcon(wDriver,"Personnel");

        //line below will get Equipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders");

        //line below will add a shift
        taskAddShiftActions().addShift( null, shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute );

        //line below will add tasks from cleaning intern subcategory
        taskAddTaskActions().addTasks( null, shift, "SanitationWorker", "Cleaning", "Intern", "3", 1 );

        //line below will click on edit mode button to go to Live mode
        taskModeUtilities().clickonEditModeButton();

        //line below will click on shift header to make the shift active and verify it is started
        taskModeUtilities().shiftStatus(null,shift,"Active");

        //line below will click on equipment id
        smartBoardPage().openEquipmentCardDetailPanel(equipmentId);

        //line below will check if equipment has load history or not
        boolean loadUpdateExist = wDriver.findElements(By.cssSelector(".auLoadHist .panel-heading.auPanelHeader")).size() > 0;
        if(loadUpdateExist == false){

        } else {

            //line below will click on edit mode button to go to Edit mode
            taskModeUtilities().clickonEditModeButton();

            //line below will assign equipment to task
            taskAssignActions().assignEquipmentToTask(null, equipmentId, shift, "CLEAN", "INTERN", null,0);

            //line below will set task description to T/R1 for task number 1 on cleaning intern
            taskDetailActions().taskDetails( null, shift, "CLEAN", "INTERN", "Cleaning/Intern", 0, null, "T/R1", null, null, "End Task", null, null,null);

            logTestInfo(wDriver,"equipment Id " + equipmentId + " is selected for update load");

            //*************************Update Load***********************************************************************************************
            equipmentUpdateLoadActions().updateLoad(equipmentId,"Relay", "01", null, null);

            //*************************Detach Equipment***********************************************************************************************
            logTestInfo(wDriver,"detaching Equipment " + equipmentId + " from location " + location);
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId); //clicking on single bin equipment
            Thread.sleep(500);
            wDriver.findElement(By.xpath("//*[contains(@class,'auActionButton')]")).click(); //click on Action button
            Thread.sleep(600);
            wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

            boolean detachButtonExist = wDriver.findElements(By.xpath("//*[contains(@class,'auActionDetach')]")).size() > 0;
            if(detachButtonExist == true){
                wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                equipmentDetachActions().detachEquipment(equipmentId,detachTo,detachHour,detachMinute,"driver01");

                //*************************Accept Equipment***********************************************************************************************
                //line below opens receiving location
                Thread.sleep( 500 );
                loginPage().goToBoardLocationByDate(url, detachTo + "/", Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));
                logTestInfo(wDriver,"accepting Equipment " + equipmentId + " on location " + detachTo);
                equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId,"receiver01","remarks01",null,null);

                //*************************Detach Equipment from receiving location***********************************************************************************************
                logTestInfo(wDriver,"detaching Equipment " + equipmentId + " to location " + location);
                equipmentDetachActions().detachEquipment(equipmentId,location,detachHour,detachMinute,"driver02");

                //*************************Accept Equipment from sending location***********************************************************************************************
                loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));
                logTestInfo(wDriver,"accepting Equipment " + equipmentId + " to location " + location);
                smartBoardPage().openEquipmentCardDetailPanel(equipmentId); //clicking on single bin equipment
                Thread.sleep(500);
                wDriver.findElement(By.xpath("//*[contains(@class,'auActionButton')]")).click(); //click on Action button
                boolean elementExist = wDriver.findElements(By.xpath("//*[contains(@class,'auActionAcceptAttach')]")).size() > 0;
                if(elementExist == true){
                    logTestPass(wDriver, "accept equipment detach button is found");
                    wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                    equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId,"receiver02","remarks02",null,null);

                } else {
                    logTestFailure(wDriver, "accept attach button is not found");
                    getScreenShot( wDriver );
                }

            } else {
                //System.out.println("detach button doesn't appear on action drop down after update load");
                logTestFailure(wDriver, "detach button doesn't appear on action drop down after update load");

            } // end of detach button doesn't exist after update load


        } // end of if equipment has load history


    }
}
