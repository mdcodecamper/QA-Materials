package taskTestSuites.taskTemplate;

import _driverScript.AbstractStartWebDriver;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;


import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 1/12/2017.
 */
public class TEST004_SaveTemplate_EditTemplate extends AbstractStartWebDriver {

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
    private String templateName = "DeleteTemplate" + Utilities.getUUID();

    @Test(description = "Delete Template")
    public void taskEditTemplate_HHRefuse_CleaningBaskets() throws InterruptedException, IOException {

        extentTest.assignCategory( "Smoke", "Delete template" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - Ref#MINERVA-4567 Save a template for an active shift with collection and cleaning subcategories then Edit the templete" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Scenario 1 - Ref#MINERVA-4567 Save a template for an active shift with collection and cleaning subcategories then Edit the templete" );

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
        //line below will perform edit template action
        taskTemplateActions().editTemplate(templateName, "Standard", templateName + " - Edited","Special Event");
        //line below will verify template exist in Load Template Modal Window after delete
        taskModeUtilities().verifyTemplateExist( true, templateName + " - Edited");

    }















}
