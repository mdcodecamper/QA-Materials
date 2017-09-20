package equipmentTestSuites.multiActions;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/29/2016.
 */
public class TEST003_UpdateLoad_SnowUpdate_RightClick_DetailsPanel extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String detachTo = EquipmentData.equipmentReceivingLocation; //"MN01";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String detachDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDetachDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String detachHour = null;
    private String detachHour2 = null;
    private String detachMinute = "00";
    private String driver = "driver001";
    private String url = LoginData.getLoginData(getUrl());
    private String receiver = "receiver001";
    private String remarks = "remarks001";
    private String detachHourCancel = null;


    @Test(description = "Equipment Update Load & Snow Update Detail Panel")
    public void equipment_Detach_Accept_Cancel_RightClick_DetailsPanel() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "RearLoaders", 1 );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "regression", "UpdateLoad & Snow Update Equipment" );
        logTestInfo( wDriver, "Update Load/Snow Update a equipment and check detail panel doesn't appear after action is performed" );

        //line below will open sending location board
        //loginPage().goToBoardLocationByDate( url, (location + "/").toString(), boardDate );
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders" );

        //line below will perfrom Update Load on a equipment
        equipmentUpdateLoadActions().updateLoadRightClick("Equipment",equipmentId,"Relay","01",null,null);
        //line below perfrom Update Load on a equipment
        equipmentSnowUpdateActions().snowUpdateRightClick("Equipment",equipmentId,"Regular","Right","Chained",null,null,"Yes");

    }

}
