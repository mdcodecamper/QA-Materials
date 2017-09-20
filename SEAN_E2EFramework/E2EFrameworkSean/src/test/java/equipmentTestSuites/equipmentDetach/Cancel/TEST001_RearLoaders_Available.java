package equipmentTestSuites.equipmentDetach.Cancel;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
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
 * Created by skashem on 10/14/2016.
 */
public class TEST001_RearLoaders_Available extends AbstractStartWebDriver{



    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String detachTo = EquipmentData.equipmentReceivingLocation; //"MN01";
    private String detachHour = Utilities.get24HourFormat(0);
    private String detachMinute = "00";
    private String driver = "test001";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String detachDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDetachDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Cancel Detachment")
    public void equipmentCancelDetach_RearLoaders_Available() throws InterruptedException, IOException {

        setEquipmentLocationForTest("Available","RearLoaders",1);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Cancel Detachment" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - cancel a single bin rear loaders equipment" );
        System.out.println( "**************************************************************************************" );
        extentTest.log( LogStatus.INFO, "Scenario 1 - cancel a single bin rear loaders equipment" );

        //line below will open ops board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );
       // Thread.sleep(4000);
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from a category on sending location
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available", "RearLoaders" );
        System.out.println( "Equipment Id is " + equipmentId );
        detachHour = Utilities.get24HourFormat(0);
        //line below will detach a equipment before cancel detach on sending location
        equipmentDetachActions().detachEquipment(equipmentId, detachTo, detachHour, detachMinute, driver);
        logTestInfo(wDriver, "verification started on sending location " + location + " before cancel detach");
        //line below will verify equipment detachment history before cancel detach on sending location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, EquipmentData.verifyLocationIsDualGarage(location), detachTo, LoginData.getLoginData("username"), driver, "");
        //line below will return available category count before cancel detach on sending location
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        //line below will return available Rear Loaders category count before cancel detach on sending location
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);
        //line below will return pending detach category count before cancel detach on sending location
        int pendingDetachCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);
        //line below will check if equipment is present in pending detach before cancel detach on sending location
        equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingDetach", null, equipmentId, "true");
        //line below will check if equipment color is as expected in pending detach category before cancel detachon sending location
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in pending detach category before cancel detach on sending location
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present before cancel detach in sending location
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);


        //line below opens receiving location and refresh to the board page before cancel detach
        loginPage().goToBoardLocationByDate(url, detachTo + "/", Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));
        //wDriver.get(url + detachTo + "/" + Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));
        //Thread.sleep(4000);
        logTestInfo(wDriver, "verification started on receiving location " + detachTo + " before cancel detach");

        //line below will return pending attach category count before cancel detach on receiving location
        int pendingAttachCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);
        //line below will check if equipment is present in pending attach before cancel detach on receiving location
        equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingAttach", null, equipmentId, "true");
        //line below will check if equipment color is as expected in pending attach category before cancel detach on receiving location
        Thread.sleep(700);
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment", "navyBlue");
        //line below will check if equipment text color is as expected in pending attach category before cancel detach on receiving location
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present before cancel detach on receiving location
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will verify equipment detachment history before cancel detach on receiving location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, EquipmentData.verifyLocationIsDualGarage(location), detachTo, LoginData.getLoginData("username"), driver, "");


        //line below opens sending location and refresh to the board page before cancel detach
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        //wDriver.get(url + location + "/" + Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));
        //Thread.sleep(4000);
        //line below will cancel a equipment detachment
        equipmentCancelDetachActions().cancelEquipmentDetachment(equipmentId);
        logTestInfo(wDriver, "verification started on sending location " + location + " after cancel detach");
        //line below will return Available count after cancel detach
        CommonActions.getAnyCategoryCardsCountAfter("+1",availableCountBefore,"Equipment","Available",null,null);
        //line below will return Rear Loaders count after cancel detach
        CommonActions.getAnyCategoryCardsCountAfter("+1",rearLoadersCountBefore,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is present in rearLoaders after cancel detach
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders",null,equipmentId,"true");
        //line below will check if equipment color is as expected in rear loaders category before after detach on sending location
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment", "navyBlue");
        //line below will check if equipment text color is as expected in rear loaders category after cancel detach on sending location
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after cancel detach on sending location
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will return Pending Detach count after cancel detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",pendingDetachCountBefore,"Equipment","Pending","PendingDetach",null);
        //line below will check if equipment is not present in pending detach after detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingDetach",null,equipmentId,"false");
        detachHour = Utilities.get24HourFormat(0);
        //line below will verify equipment detachment history after cancel detach on sending location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Cancelled", exDetachDate + " " + detachHour + ":" + "", "", "", LoginData.getLoginData("username"), "", "");

        //line below opens receiving location and refresh to the board page after cancel detach
        loginPage().goToBoardLocationByDate(url, detachTo + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        //wDriver.get(url + detachTo + "/" + Utilities.getDesiredDateInFormat(0,"yyyyMMdd"));
        //Thread.sleep(4000);
        logTestInfo(wDriver, "verification started on receiving location " + detachTo + " after cancel detach");
        //line below will return pendting attach category count after cancel detach on receiving location
        CommonActions.getAnyCategoryCardsCountAfter("-1",pendingAttachCountBefore,"Equipment","Pending","PendingAttach",null);
        //line below will check if equipment is not present in pending attach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending","PendingAttach",null,equipmentId,"false");


    }


}
