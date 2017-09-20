package equipmentTestSuites.equipmentDetach.Detach;


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
 * Created by skashem on 10/4/2016.
 */

//@Listeners(TestNGTestListener.class)

/**
 * Created by skashem on 10/4/2016.
 */

//@Listeners(TestNGTestListener.class)
public class TEST002__DualBins_Relay_RecycMisc extends AbstractStartWebDriver {


    /***************************************************************
     * Scenario 1 - Detach A Single Bin Equipment From Available Group
     ****************************************************************/
    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String detachTo = EquipmentData.equipmentReceivingLocation; //"MN01";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String detachDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDetachDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String detachHour = Utilities.get24HourFormat(0);
    private String detachMinute = "00";
    private String driver = "test001";
    private String url = LoginData.getLoginData(getUrl());


    @Test(priority = 1, description = "Equipment Detach")
    public void equipmentDetach_DualBins_Relay_RecycMisc() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","DualBins", 1);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Regression", "Detach Equipment");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 2 - Detach A Dual Bin Equipment From Relay Recycling Misc Group");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 2 - Detach A Dual Bin Equipment From Relay Recycling Misc Group");

        //line below will open sending location board
        //loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","DualBins");
        logTestInfo(wDriver, "verification started on sending location " + location + " before detach");
        //line below will perform Update Load action before detach
        equipmentUpdateLoadActions().updateLoad(equipmentId,"Relay","44","Empty",null);
        //line below will return relay category count before detach
        int relayCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay",null,null);
        //line below will return recycling count after detach
        int recycCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay","RECYCLING",null);
        //line below will check if equipment is present in recycling misc before detach on sending location
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","RECYCLING", "Recycling Misc", equipmentId, "true");
        //line below will return pendting detach category count before detach on sending location
        String pendingDetachCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Pending","PendingDetach", null);
        //line below will check if equipment color is as expected in recycling misc category before detach
        equipmentColorUtilities().verifyEquipmentCardColor( equipmentId, "Equipment","limeGreen" );
        //line below will check if equipment text color is as expected in recycling misc category before detach
        equipmentColorUtilities().verifyEquipmentTextColor( equipmentId, "black" );
        //line below will check equipment rear loader bin type is present in recycling misc category before detach
        equipmentPanelUtilities().equipmentBinType( equipmentId, "E", "44" );


        logTestInfo(wDriver, "verification started on receiving location " + detachTo + " before detach");
        //line below opens receiving location and refresh to the board page before detach
        loginPage().goToBoardLocationByDate(url, detachTo + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        //line below will return pendting detach category count before detach on receiving location
        int pendingAttachCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);
        //line below opens sending location and refresh to the board page before detach
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        detachHour = Utilities.get24HourFormat(0);
        //line below will detach a equipment
        equipmentDetachActions().detachEquipment(equipmentId, detachTo, detachHour, detachMinute, driver);

        logTestInfo(wDriver, "verification started on sending location " + location + " after detach");
        //line below will return Relay count after detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",relayCountBefore,"Equipment","Relay",null,null);
        //line below will return Recycling count after detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",recycCountBefore,"Equipment","Relay","RECYCLING",null);
        //line below will check if equipment is not present in Relay after detach
        equipmentPanelUtilities().verifyEquipmentPresent("Relay",null,null,equipmentId,"false");
        //line below will return Pending Detach count after detach
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(+1,pendingDetachCountBefore,"Pending","PendingDetach", null);
        //line below will check if equipment is present in pending detach after detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingDetach", null, equipmentId,"true");
        //line below will check if equipment color is as expected in pending detach category after detach
        equipmentColorUtilities().verifyEquipmentCardColor( equipmentId, "Equipment","limeGreen" );
        //line below will check if equipment text color is as expected in pending detach category after detach
        equipmentColorUtilities().verifyEquipmentTextColor( equipmentId, "black" );
        //line below will check equipment rear loader bin type is present in pending detach category after detach
        equipmentPanelUtilities().equipmentBinType( equipmentId, "E", "44" );
        //line below will verify equipment detachment history after detach on sending location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, EquipmentData.verifyLocationIsDualGarage(location), detachTo, LoginData.getLoginData("username"), driver, "");


        logTestInfo(wDriver, "verification started on receiving location " + detachTo + " after detach");
        //line below opens receiving location and refresh to the board page
        loginPage().goToBoardLocationByDate(url, detachTo + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        //line below will return pendting attach category count after detach on receiving location
        CommonActions.getAnyCategoryCardsCountAfter("+1",pendingAttachCountBefore,"Equipment","Pending","PendingAttach",null);
        //line below will check if equipment is present in pending attach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingAttach", null, equipmentId,"true");
        //line below will check if equipment color is as expected in pending attach category after detach
        equipmentColorUtilities().verifyEquipmentCardColor( equipmentId, "Equipment","limeGreen" );
        //line below will check if equipment text color is as expected in pending attach category after detach
        equipmentColorUtilities().verifyEquipmentTextColor( equipmentId, "black" );
        //line below will check equipment rear loader bin type is present in pending attach category after detach
        equipmentPanelUtilities().equipmentBinType( equipmentId, "E", "44" );
        //line below will verify equipment detachment history after detach on receiving lcoation
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, EquipmentData.verifyLocationIsDualGarage(location), detachTo, LoginData.getLoginData("username"), driver, "");


    }


}
