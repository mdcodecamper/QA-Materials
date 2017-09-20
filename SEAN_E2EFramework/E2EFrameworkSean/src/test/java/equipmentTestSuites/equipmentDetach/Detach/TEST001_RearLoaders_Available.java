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
public class TEST001_RearLoaders_Available extends AbstractStartWebDriver {


    /***************************************************************
     * Scenario 1 - Detach A Single Bin Equipment From Available Group
     ****************************************************************/
    //private WebDriver wdriver;
    //Test Data
    // private String url = LoginCredentials.url;
    //private String equipmentId = "25CW-857";
    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String detachTo = EquipmentData.equipmentReceivingLocation; //"MN01";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String detachDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDetachDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");

    // private String equipmentId = "536-056";
    private String detachHour = Utilities.get24HourFormat(0);
    private String detachMinute = "00";
    private String driver = "test001";
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Detach")
    public void equipmentDetach_RearLoaders_Available() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","RearLoaders",1);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());
        extentTest.assignCategory("Smoke", "Detach Equipment");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Detach a single bin equipment from Rear Loaders category");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - Detach a single bin equipment from Rear Loaders category");

        //line below will open sending location board
        //loginPage().goToBoardLocationByDate(url,(location + "/").toString(), boardDate);
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will move equipment from pending load to available category if equipment in available category is <= 10
       // EquipmentActions.moveEquipmentFromPendingLoad("RearLoaders");
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders" );
        System.out.println("Equipment Id is " + equipmentId);
        logTestInfo(wDriver, "verification started on sending location " + location + " before detach");
        //line below will return available category count before detach
        //String availableCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Available",null,null);
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        //line below will return available Rear Loaders category count before detach on sending location
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);
        //line below will check if equipment is present in rearLoaders before detach on sending location
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null,equipmentId, "true");
        //line below will check if equipment color is as expected in rearLoaders category before detach on sending location
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in Rear Loaders category before detach on receiving location
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present before detach in sending location
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will return pendting detach category count before detach on sending location
        int pendingDetachCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);


        logTestInfo(wDriver, "verification started on receiving location " + detachTo + " before detach");
        //line below opens receiving location and refresh to the board page before detach
        loginPage().goToBoardLocationByDate(url, detachTo + "/", Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));
        //wDriver.get(url + detachTo + "/" + Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));

        //line below will return pendting detach category count before detach on receiving location
        int pendingAttachCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);

        //line below opens sending location and refresh to the board page before detach
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        //wDriver.get(url + location + "/" + Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));
        logTestInfo(wDriver, "verification started on sending location " + location + " after detach");

        detachHour = Utilities.get24HourFormat(0);
        //line below will detach a equipment
        equipmentDetachActions().detachEquipment(equipmentId, detachTo, detachHour, detachMinute, driver);
        //line below will return Available count after detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableCountBefore,"Equipment","Available",null,null);
        //line below will return Rear Loaders count after detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",rearLoadersCountBefore,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is not present in rearLoaders after detach
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders",null,equipmentId,"false");
        //line below will return Pending Detach count after detach
        CommonActions.getAnyCategoryCardsCountAfter("+1",pendingDetachCountBefore,"Equipment","Pending","PendingDetach",null);
        //line below will check if equipment is present in pending detach after detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingDetach",null,equipmentId,"true");
        //line below will check if equipment color is as expected in pending detach category after detach on sending location
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in pending detach category after detach on sending location
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after detach in sending location
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will verify equipment detachment history after detach on sending location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, EquipmentData.verifyLocationIsDualGarage(location), detachTo, LoginData.getLoginData("username"), driver, "");

        logTestInfo(wDriver, "verification started on receiving location " + detachTo + " after detach");
        //line below opens receiving location and refresh to the board page
        loginPage().goToBoardLocationByDate(url, detachTo + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        //wDriver.get(url + detachTo + "/" + Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));

        //line below will return pendting attach category count after detach on receiving location
        CommonActions.getAnyCategoryCardsCountAfter("+1",pendingAttachCountBefore,"Equipment","Pending","PendingAttach",null);
        //line below will check if equipment is present in pending attach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingAttach",null,equipmentId,"true");
        //line below will check if equipment color is as expected in pending attach category after detach on receiving location
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in pending attach category after detach on receiving location
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after detach in receiving location
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will verify equipment detachment history after detach on receiving lcoation
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, EquipmentData.verifyLocationIsDualGarage(location), detachTo, LoginData.getLoginData("username"), driver, "");

      //  Thread.sleep(1000);


    }


}
