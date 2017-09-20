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
public class TEST002_Detach_Accept_Cancel_RightClick_DetailsPanel extends AbstractStartWebDriver {

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


    @Test(description = "Equipment Detach Accept & Cancel Pagination")
    public void equipment_Detach_Accept_Cancel_RightClick_DetailsPanel() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "RearLoaders", 1 );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "regression", "Detach Accept Cancel Equipment" );
        logTestInfo( wDriver, "Detach/Accept/Cancel a equipment and check detail panel doesn't appear after action is performed" );

        //line below will open sending location board
        //loginPage().goToBoardLocationByDate( url, (location + "/").toString(), boardDate );
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders" );

        detachHour = Utilities.get24HourFormat(-2);
        //line below will detach a equipment
        equipmentDetachActions().detachEquipmentRightClick(equipmentId, detachTo, detachHour, detachMinute, driver);
        //line below will cancel a equipment detachment
        equipmentCancelDetachActions().cancelEquipmentDetachmentRightClick(equipmentId);
        //line below will detach a equipment
        equipmentDetachActions().detachEquipment(equipmentId, detachTo, detachHour, "10", driver);
        //line below will open receiving location board
        loginPage().goToBoardLocationByDate( url, (detachTo + "/").toString(), boardDate );
        detachHour2 = Utilities.get24HourFormat(-1);
        //line below will accept equipment detachment on receiving location
        equipmentAcceptDetachActions().acceptEquipmentDetachmentRightClick(equipmentId, receiver, remarks,detachHour2,"20");

    }



}
