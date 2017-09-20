package equipmentTestSuites.equipmentSnowUpdate;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.equipmentHistoryUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentSnowUpdateActions;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/29/2016.
 */
public class TEST006_Snow_Readiness_Rules extends AbstractStartWebDriver {


    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String lastUpdated = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLastUpdated = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String plowType = "Regular Plow";
    private String plowDirections = "Right";
    private String chain = "Not Chained";
    private String load = null;
    private String snowAvailability = null;
    private String snowAssignment = "Yes";
    private String url = LoginData.getLoginData(getUrl());
    private String snowUpdateHour = null;


    @Test(description = "Equipment Snow Readiness Rules")
    public void equipmentSnowUpdate_SnowReadiness_Rules() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "RearLoaders", 1 );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Regression", "Snow Readiness Rules" );
        Reporter.log( "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true );
        logTestInfo( wDriver, "Scenario 6 - Ref#MINERVA-3776 - Equipment Snow Readiness rules" );

        //line below will open ops board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Available", "RearLoaders","Down");

        logTestInfo(wDriver,"Verifying Plow type is set to Regular and Snow Assignment became enabled - Yes & No will be displayed in drop down");
        equipmentPanelUtilities().verifyEquipmentModalWindow(equipmentId,"Snow Update","Regular Plow","Right","Not Chained","Disabled","Yes");
        //line below will perform Snow Update Action
        equipmentSnowUpdateActions().snowUpdate(equipmentId,"Regular Plow","Right","Not Chained",null,null,"Yes");
        snowUpdateHour = Utilities.get24HourFormat(0);
        //line below will verify Snow Update history after snow update
        logTestInfo(wDriver,"Verifying Snow Update History after Snow Update is performed");
        equipmentHistoryUtilities().getEquipmentSnowUpdateHistory(equipmentId,"1","Regular Plow","Right","Not Chained","","Yes",exLastUpdated + " " + snowUpdateHour + ":",LoginData.getLoginData("username"));

        logTestInfo(wDriver,"Verifying Snow Readiness is set to None & Plow Direction, Snow Availability, & Snow Assignment is disabled");
        equipmentPanelUtilities().verifyEquipmentModalWindow(equipmentId,"Snow Update","None","Disabled","Not Chained","Disabled","Disabled");
        //line below will perform Snow Update Action
        equipmentSnowUpdateActions().snowUpdate(equipmentId,"None",null,"Not Chained",null,null,null);
        snowUpdateHour = Utilities.get24HourFormat(0);
        //line below will verify Snow Update history after snow update for None plow type
        logTestInfo(wDriver,"Verifying Snow Update History after Snow Update is performed for None Plow Type");
        equipmentHistoryUtilities().getEquipmentSnowUpdateHistory(equipmentId,"1","None","","Not Chained","","No",exLastUpdated + " " + snowUpdateHour + ":",LoginData.getLoginData("username"));

    }





}
