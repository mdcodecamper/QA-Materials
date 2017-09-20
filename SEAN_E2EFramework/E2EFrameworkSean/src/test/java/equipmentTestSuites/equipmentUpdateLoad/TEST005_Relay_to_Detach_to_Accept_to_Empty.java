package equipmentTestSuites.equipmentUpdateLoad;

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
 * Created by skashem on 12/12/2016.
 */
public class TEST005_Relay_to_Detach_to_Accept_to_Empty extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String locationTo = EquipmentData.equipmentReceivingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String loadDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLoadDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String loadHour = Utilities.get24HourFormat(0);
    private String newStatus1 = "Relay";
    private String material1 = "01";
    private String newStatus2 = null;
    private String material2 = null;
    private String detachHour = Utilities.get24HourFormat(0);
    private String detachMinute = "00";
    private String driver = "test001";
    private String exDetachDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Update Load for Rear Loaders From Down to Rollover Recycling Misc to Down")
    public void equipmentUpdateLoad_Relay_Detach_Accept_Empty() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "RearLoaders", 2 );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Smoke", "Update Load" );
        logTestInfo( wDriver, "Scenario 5 - ref# MINERVA-3781 - Update Load for Rear Loaders to Relay COLLECTION category then Detach the equipment and Accept and then Update to Empty on receiving location" );

        //line below will open ops board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available", "RearLoaders" );
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,newStatus1,material1,newStatus2,material2);

        logTestInfo(wDriver,"verification started on Sending location " + location + " after Update Load");
        //line below will return relay category count after Update Load
        int relayCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay",null,null);
        //line below will return relay COLLECTION count after Update Load
        int relayCollCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay","COLLECTION",null);
        //line below will check if equipment is present in Relay COLLECTION after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","COLLECTION", null,equipmentId, "true");
        //line below will check if equipment color is as expected after update load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected after update load
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment dual bin type is present after update load
        equipmentPanelUtilities().equipmentBinType(equipmentId,"01",null);
        //line below will return pendting detach category count before detach on sending location
        int pendingDetachCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);

        logTestInfo(wDriver, "verification started on receiving location " + locationTo + " before detach");
        //line below opens receiving location and refresh to the board page before detach
        loginPage().goToBoardLocationByDate(url, locationTo + "/", Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));
        //line below will return pendting detach category count before detach on receiving location
        int pendingAttachCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);

        //line below opens sending location and refresh to the board page before detach
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        detachHour = Utilities.get24HourFormat(0);
        //line below will detach a equipment
        equipmentDetachActions().detachEquipment(equipmentId, locationTo, detachHour, detachMinute, driver);

        logTestInfo(wDriver, "verification started on sending location " + location + " after detach");
        //line below will return Relay count after detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",relayCountBefore,"Equipment","Relay",null,null);
        //line below will return Relay COLLECTION count after detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",relayCollCountBefore,"Equipment","Relay","COLLECTION",null);
        //line below will check if equipment is not present in Relay COLLECTION after detach
        equipmentPanelUtilities().verifyEquipmentPresent("Available","Relay","COLLECTION",equipmentId,"false");
        //line below will return Pending Detach count after detach
        CommonActions.getAnyCategoryCardsCountAfter("+1",pendingDetachCountBefore,"Equipment","Pending","PendingDetach",null);
        //line below will check if equipment is present in pending detach after detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingDetach",null,equipmentId,"true");
        //line below will check if equipment color is as expected in pending detach category after detach on sending location
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment", "navyBlue");
        //line below will check if equipment text color is as expected in pending detach category after detach on sending location
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after detach in sending location
        equipmentPanelUtilities().equipmentBinType(equipmentId,"01",null);
        //line below will verify equipment detachment history after detach on sending location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, location, locationTo, LoginData.getLoginData("username"), driver, "");

        logTestInfo(wDriver, "verification started on receiving location " + locationTo + " after detach");
        //line below opens receiving location and refresh to the board page
        loginPage().goToBoardLocationByDate(url, locationTo + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        //line below will return pendting attach category count after detach on receiving location
        CommonActions.getAnyCategoryCardsCountAfter("+1",pendingAttachCountBefore,"Equipment","Pending","PendingAttach",null);
        //line below will check if equipment is present in pending attach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingAttach",null,equipmentId,"true");
        int pendingAttachCountBeforeAccept = CommonActions.getAnyCategoryCardsCount( "Equipment","Pending", "PendingAttach",null );
        //line below will verify equipment detachment history after detach on receiving lcoation
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, location, locationTo, LoginData.getLoginData("username"), driver, "");
        detachHour = Utilities.get24HourFormat(0);
        //line below will accept equipment detachment on receiving location
        equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId, "receiver01", "remarks001",null,null);
        //line below will verify equipment pending attach coutn after Accept Detachment
        CommonActions.getAnyCategoryCardsCountAfter( "-1", pendingAttachCountBeforeAccept,"Equipment","Pending", "PendingAttach", null);
       //line below will verify equipment appears on Relay COLLECTION caegory after accept detachment
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","COLLECTION",null,equipmentId,"true");
        //line below will verify equipment detachment history after detach on receiving lcoation
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Accepted", exDetachDate + " " + detachHour + ":", "", "", "receiver01", "", "remarks001");
        loadHour = Utilities.get24HourFormat(0);
        //line below will perform update load Equipment action after accept detachment
        equipmentUpdateLoadActions().updateLoad(equipmentId,"Empty",null,newStatus2,material2);
       //line below will verify equipment not present in relay COLLECTION
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","COLLECTION",null,equipmentId,"false");
        //line below will verify equipment is present in rear loaders
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders",null,equipmentId,"true");
        //line below will check if equipment color is as expected
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after update load to empty
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will verify Update Load Status history after Update Load to empty
        equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId,"1","Load","EMPTY","",exLoadDate + " " + loadHour + ":",LoginData.getLoginData("username"),"Click On Equipment");

    }









}
