package equipmentTestSuites.equipmentUpdateLoad;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentUpdateLoadActions;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/29/2016.
 */
public class TEST006_Load_History_Pagination extends AbstractStartWebDriver {


    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String loadDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLoadDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String loadHour = Utilities.get24HourFormat(0);
    private String newStatus1 = "Relay";
    private String material1 = "46";
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Update Load Historay Pagination")
    public void equipmentUpdateLoad_LoadHistory_Pagination() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "RearLoaders", 1 );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Regression", "Update Load History Pagination" );
        logTestInfo( wDriver, "Scenario 6 - Perform Update Load History for 5 records per page" );

        //line below will open ops board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Available", "RearLoaders" );

        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,newStatus1,material1,null,null);
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,"Rollover","24",null,null);
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,"Empty",null,null,null);
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,newStatus1,material1,null,null);
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,"Rollover","24",null,null);

        //line below will verify Load history pagination
        CommonActions.paginationVerification("Equipment",equipmentId,"Load");

    }







}
