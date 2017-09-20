package equipmentTestSuites.equipmentDetach.Detach;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.getAnyCategoryCardsCount;
import static common.actions.CommonActions.getAnyCategoryCardsCountAfter;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

public class TEST008_Detach_Accept_Refresh extends AbstractStartWebDriver {

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


    @Test(description = "Equipment Detach, Accept & Refresh")
    public void equipment_Detach_Accept_Refresh() throws InterruptedException, IOException {

        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "regression", "Detach and Accept Equipment" );
        logTestInfo( wDriver, "Scenario 8 - Equipment Detach, Accept & Refresh" );

        setEquipmentLocationForTest( "Available", "RearLoaders", 1 );
        location = EquipmentData.equipmentSendingLocation;
        //line below will open sending location board
        loginPage().goToBoardLocationByDate( url, (location + "/").toString(), boardDate );
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders" );
        //Counters before detach on sending board
        int availableCountBeforeDetach_sending = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        int pendingDetachCountBeforeDetach_sending = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);
        int rearLoadersCountBeforeDetach_sending = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);

        //line below will open receiving location board
        loginPage().goToBoardLocationByDate( url, (detachTo + "/").toString(), boardDate );
        Thread.sleep(1200);
        //Counters before detach on receiving board
/*
        int pendingAttachCountBeforeDetach_receiving = 0;
        if (Utilities.isElementPresent(wDriver, By.cssSelector(".groupData.equipment-PendingAttachPending" )) > 0) {
            pendingAttachCountBeforeDetach_receiving = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);
        }
*/
        int pendingAttachCountBeforeDetach_receiving = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);
        int rearLoadersCountBeforeDetach_receiving = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);
        int availableCountBeforeDetach_receiving = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);

        //line below will open sending location board
        loginPage().goToBoardLocationByDate( url, (location + "/").toString(), boardDate );
        Thread.sleep(1200);
        detachHour = Utilities.get24HourFormat(-2);
        //line below will detach a equipment
        equipmentDetachActions().detachEquipment(equipmentId, detachTo, detachHour, detachMinute, driver);

        logTestInfo(wDriver, "Verification started on sending location " + location + " after detach");
        Thread.sleep(1200);
        //line below will return pending detach count after detach
        getAnyCategoryCardsCountAfter("+1",pendingDetachCountBeforeDetach_sending,"Equipment","Pending","PendingDetach",null);
        //line below will check if equipment is present in pending detach before accept detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "true");
        //line below will return available count after detach
        getAnyCategoryCardsCountAfter("-1",availableCountBeforeDetach_sending,"Equipment","Available",null,null);
        //line below will return rear loaders count after detach
        getAnyCategoryCardsCountAfter("-1",rearLoadersCountBeforeDetach_sending,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is  not present in rear loaders before accept detach on location A
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "false");
        //line below will verify detachment history after detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1",
                "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, EquipmentData.verifyLocationIsDualGarage(location), detachTo, LoginData.getLoginData("username"), driver, "");

        //Counters before accept on sending board
        int pendingDetachCountBeforeAccept_sending = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);

        //line below will open receiving location board
        loginPage().goToBoardLocationByDate( url, (detachTo + "/").toString(), boardDate );
        Thread.sleep(1200);
        logTestInfo(wDriver, "Verification started on receiving location " + detachTo + " after detach and before accept");
        int pendingAttachCountBeforeAccept_receiving = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);

        //line below will check if equipment is present in pending attach before accept detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId, "true");
        //line below will return pending attach count before accept
        getAnyCategoryCardsCountAfter("+1",pendingAttachCountBeforeDetach_receiving,"Equipment","Pending","PendingAttach",null);

        detachHour2 = Utilities.get24HourFormat(-1);
        //line below will accept equipment detachment on receiving location
        equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId, receiver, remarks,detachHour2,"20");
        Thread.sleep(1200);
        logTestInfo( wDriver,"Verification Started after Accepting detachment on Receiving location " + detachTo);
        //line below will verify equipment detachment history after detach on receiving location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Accepted", exDetachDate + " " + detachHour2 + ":20", "", "", receiver, "", remarks);
        //line below will return pending attach count after accept
        getAnyCategoryCardsCountAfter("-1",pendingAttachCountBeforeAccept_receiving,"Equipment","Pending","PendingAttach",null);
        //line below will return available count after attach
        getAnyCategoryCardsCountAfter("+1",availableCountBeforeDetach_receiving,"Equipment","Available",null,null);
        //line below will return rear loaders count after attach
        getAnyCategoryCardsCountAfter("+1",rearLoadersCountBeforeDetach_receiving,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is not present in pending attach after accept
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId, "false");
        //line below will check if equipment is present in rear loaders after accept
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");
        logTestInfo( wDriver,"Verification Started after refreshing receiving location " + detachTo);
        //code below will verify equipment is present in Available/RearLoaders after refresh on receiving location
        loginPage().goToBoardLocationByDate( url, (detachTo + "/").toString(), boardDate );
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");

        //line below will open sending location board
        loginPage().goToBoardLocationByDate( url, (location + "/").toString(), boardDate );
        Thread.sleep(1200);
        logTestInfo( wDriver,"Verification Started after Accepting detachment on sending location " + location);
        //line below will check if equipment is not present in pending detach after accept
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "false");
        //line below will return pending detach count after accept
        getAnyCategoryCardsCountAfter("-1",pendingDetachCountBeforeAccept_sending,"Equipment","Pending","PendingDetach",null);


    }



}
