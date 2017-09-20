package equipmentTestSuites.equipmentDetach.Detach;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.getAnyCategoryCardsCount;
import static common.actions.CommonActions.getAnyCategoryCardsCountAfter;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/20/2016.
 */
public class TEST004_Detach_A_to_B_to_C extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    //private String detachTo2 = "BX01"; //"MN01";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String detachDate1 = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDetachDate1 = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String detachHour1 = Utilities.get24HourFormat(-2);
    private String detachMinute1 = "00";
    private String driver1 = "test001";
    private String detachDate2 = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDetachDate2 = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String detachHour2 = Utilities.get24HourFormat(-1);
    private String detachMinute2 = "00";
    private String driver2 = "test002";
    private String receiver1 = "receiver01";
    private String remarks1 = "remarks01";
    private String receiver2 = "receiver02";
    private String remarks2 = "remarks02";
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Detach")
    public void equipmentDetach_A_to_B_to_C() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "RearLoaders", 1 );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Smoke", "Detach Equipment" );
        logTestInfo( wDriver, "Scenario 1 - Ref# MINERVA-3758 - Detach Equipment from A to B to C (A is the owner location)" );

        //line below will open sending location board
        //loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panelf
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category on sending location
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders");
        //line below will open owner location board
        String assignedLocation = equipmentHistoryUtilities().setAssignedLocation( equipmentId, location );
        //line below will get location B
        String detachTo1 = EquipmentData.getNonCurrentLocation(assignedLocation);
        //line below will get location C
        String detachTo2 = EquipmentData.getDifferentLocation(assignedLocation,detachTo1);

        logTestInfo(wDriver, "verification started on owner location " + assignedLocation + " before detach");
        Thread.sleep(1200);
        //line below will get detach count on location A before detach
        int detachCountABefore = getAnyCategoryCardsCount("Equipment","Detached", null, null);
        //line below will get pending detach count on location A before detach
        int pendingDetachCountABefore = getAnyCategoryCardsCount("Equipment","Pending", "PendingDetach", null);
        //line below will get available count on location A before detach
        int availableCountABefore = getAnyCategoryCardsCount("Equipment","Available", null, null);
        //line below will get rear loaders count on location A before detach
        int rearLoadersCountABefore = getAnyCategoryCardsCount("Equipment","Available", "RearLoaders", null);
        //line below will check if equipment is present in rear loaders before accept detach on location A
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");
        //line below will check current location and assigned
        equipmentHistoryUtilities().getEquipmentDetailHistory(equipmentId,null,null,null,assignedLocation);

        //line below will open location B board
        loginPage().goToBoardLocationByDate(url, detachTo1 + "/", boardDate);

        logTestInfo(wDriver, "verification started on location B " + detachTo1 + " before detach");
        Thread.sleep(1200);
        //line below will get pending attach count on location B before detach
        int pendingAttachCountBBefore = getAnyCategoryCardsCount("Equipment","Pending", "PendingAttach", null);
        //line below will get available count on location B before detach
        int availableCountBBefore = getAnyCategoryCardsCount("Equipment","Available", null, null);
        //line below will get rear loaders count on location B before detach
        int rearLoadersCountBBefore = getAnyCategoryCardsCount("Equipment","Available", "RearLoaders", null);

        //line below will open owner location(A) board
        //loginPage().goToBoardLocationByDate(url, assignedLocation + "/", boardDate);
        equipmentHistoryUtilities().navigateToAssignedLocation( equipmentId,assignedLocation,0 );
        //line below will perform detach action on owner location
        equipmentDetachActions().detachEquipment(equipmentId,detachTo1,detachHour1,detachMinute1,driver1);

        logTestInfo(wDriver, "verification started on location A " + assignedLocation + " after detach");
        Thread.sleep(1200);
        //line below will return pending detach count after detach on locaiton A
        getAnyCategoryCardsCountAfter("+1",pendingDetachCountABefore,"Equipment","Pending","PendingDetach",null);
        //line below will check if equipment is present in pending detach before accept detach on location A
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "true");
        //line below will return available count after detach on locaiton A
        getAnyCategoryCardsCountAfter("-1",availableCountABefore,"Equipment","Available",null,null);
        //line below will return rear loaders count after detach on locaiton A
        getAnyCategoryCardsCountAfter("-1",rearLoadersCountABefore,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is  not present in rear loaders before accept detach on location A
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "false");
        //linebelow will get pending attach count before accept detach on location A
        int pendingDetachACountBeforeAccept = getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);
        //line below will verify detachment history before detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate1 + " " + detachHour1 + ":" + detachMinute1, assignedLocation,detachTo1, LoginData.getLoginData("username"), driver1, "");

        //line below will open location B board
        loginPage().goToBoardLocationByDate(url, detachTo1 + "/", boardDate);
        logTestInfo(wDriver, "verification started on location B " + detachTo1 + " after detach");
        Thread.sleep(1200);
        //line below will return pending attach count after detach on location B
        getAnyCategoryCardsCountAfter("+1",pendingAttachCountBBefore,"Equipment","Pending","PendingAttach",null);
        //line below will check if equipment is present in pending attach before accept detach on location B
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId, "true");
        //linebelow will get pending attach count before accept detach
        int pendingAttachBCountBeforeAccept = getAnyCategoryCardsCount("Equipment","Pending","PendingAttach",null);
        //line below will verify detachment history before detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate1 + " " + detachHour1 + ":" + detachMinute1, assignedLocation,detachTo1, LoginData.getLoginData("username"), driver1, "");

        //line below will perform Accept Detach on location B
        equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId, receiver1, remarks1,detachHour1,"01");
        logTestInfo(wDriver, "verification started on location B " + detachTo1 + " after accept detachment");
        Thread.sleep(1200);
        //line below will return available count after accept detach on location B
        getAnyCategoryCardsCountAfter("+1",availableCountBBefore,"Equipment","Available",null,null);
        //line below will return available rear loaders count after accept detach on location B
        getAnyCategoryCardsCountAfter("+1",rearLoadersCountBBefore,"Equipment","Available","RearLoaders",null);
        //line below will return pending attach count after accept detach on location B
        getAnyCategoryCardsCountAfter("-1",pendingAttachBCountBeforeAccept,"Equipment","Pending","PendingAttach",null);
        //line below will check if equipment is present in available rear loaders after accept detach on location B
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");
        //line below will check current location and assigned
        equipmentHistoryUtilities().getEquipmentDetailHistory(equipmentId,null,null,null,detachTo1);
        //line below will verify detachment history after accept detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Accepted", exDetachDate1 + " " + detachHour1 + ":01", "","", receiver1, "", remarks1);


        //line below will open location A board
        //loginPage().goToBoardLocationByDate(url, assignedLocation + "/", boardDate);
        equipmentHistoryUtilities().navigateToAssignedLocation( equipmentId,assignedLocation,0 );
        logTestInfo(wDriver, "verification started on location A " + assignedLocation + " after accept detachment");
        Thread.sleep(1200);
        //line below will return pending detach count after accept detach on location A
        getAnyCategoryCardsCountAfter("-1",pendingDetachACountBeforeAccept,"Equipment","Pending","PendingDetach",null);
        //line below will check detach count after accept detachment on location A
        getAnyCategoryCardsCountAfter("+1",detachCountABefore,"Equipment","Detached",null,null);
        //line below will check if equipment is present in detached on receiving location b category after accept detach on location A
        equipmentPanelUtilities().verifyEquipmentPresent("Detached", detachTo1, null, equipmentId, "true");
        //line below will check current location and assigned
        equipmentHistoryUtilities().getEquipmentDetailHistory(equipmentId,null,null,null,detachTo1);
        //line below will verify detachment history after accept detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Accepted", exDetachDate1 + " " + detachHour1 + ":01", "","", receiver1, "", remarks1);


        //line below will open location B board
        loginPage().goToBoardLocationByDate(url, detachTo1 + "/", boardDate);
        logTestInfo(wDriver, "verification started on location B " + detachTo1 + " before detach to location C");
        Thread.sleep(1200);
        //line below will get pending detach count on location B before detach to C
        int pendingDetachCountBBeforeC = getAnyCategoryCardsCount("Equipment","Pending", "PendingDetach", null);
        //line below will get available count on location A before detach
        int availableCountBBeforeC = getAnyCategoryCardsCount("Equipment","Available", null, null);
        //line below will get rear loaders count on location A before detach
        int rearLoadersCountBBeforeC = getAnyCategoryCardsCount("Equipment","Available", "RearLoaders", null);
        //line below will check if equipment is present in rear loaders before accept detach on location A
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");
        //line below will check current location and assigned
        equipmentHistoryUtilities().getEquipmentDetailHistory(equipmentId,null,null,null,detachTo1);

        //line below will open location C board
        loginPage().goToBoardLocationByDate(url, detachTo2 + "/", boardDate);
        logTestInfo(wDriver, "verification started on location C " + detachTo2 + " before detach from location B");
        Thread.sleep(1200);
        //line below will get pending attach count on location C before detach from B
        int pendingAttachCountBBeforeC = getAnyCategoryCardsCount("Equipment","Pending", "PendingAttach", null);

        //line below will open location B board
        loginPage().goToBoardLocationByDate(url, detachTo1 + "/", boardDate);
        equipmentDetachActions().detachEquipment( equipmentId,detachTo2,detachHour2,detachMinute2,driver2);
        logTestInfo(wDriver, "verification started on location B " + detachTo1 + " after detach to location C");
        Thread.sleep(1200);
        //line below will return pending detach count afer detach to location C
        getAnyCategoryCardsCountAfter("+1",pendingDetachCountBBeforeC,"Equipment","Pending","PendingDetach",null);
        //line below will return availablecount afer detach to location C
        getAnyCategoryCardsCountAfter("-1",availableCountBBeforeC,"Equipment","Available",null,null);
        //line below will return rear loaders count afer detach to location C
        getAnyCategoryCardsCountAfter("-1",rearLoadersCountBBeforeC,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is present in Pending Detach after detach to location C
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "true");
        //line below will return pending detach count before accepted on Location C
        int pendingDetachCountBBeforeAcceptToC = getAnyCategoryCardsCount("Equipment","Pending", "PendingDetach", null);
        //line below will verify detachment history after detach to location C
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate2 + " " + detachHour2 + ":" + detachMinute2, detachTo1,detachTo2, LoginData.getLoginData("username"), driver2, "");


        //line below will open location C board
        loginPage().goToBoardLocationByDate(url, detachTo2 + "/", boardDate);
        logTestInfo(wDriver, "verification started on location C " + detachTo2 + " after detach from location B");
        Thread.sleep(1200);
        //line below will return pending attach count afer detach from location B
        getAnyCategoryCardsCountAfter("+1",pendingAttachCountBBeforeC,"Equipment","Pending","PendingAttach",null);
        //line below will check if equipment is present in Pending Attach after detach from location B
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId, "true");
        //line below will get pending attach count before accepted on Location C
        int pendingAttachCountCBeforeAcceptToC = getAnyCategoryCardsCount("Equipment","Pending", "PendingAttach", null);
        //line below will get available count before accepted on Location C
        int availableCountCBeforeAcceptToC = getAnyCategoryCardsCount("Equipment","Available", null, null);
        //line below will get rear loaders count before accepted on Location C
        int rearLoadersCountCBeforeAcceptToC = getAnyCategoryCardsCount("Equipment","Available", "RearLoaders", null);
        //line below will verify detachment history after detach from location B
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate2 + " " + detachHour2 + ":" + detachMinute2, detachTo1,detachTo2, LoginData.getLoginData("username"), driver2, "");

        //line below will accept equipment on location C
        equipmentAcceptDetachActions().acceptEquipmentDetachment( equipmentId,receiver2,remarks2,detachHour2,"38" );
        logTestInfo(wDriver, "verification started on location C " + detachTo2 + " after accept detachment from location B");
        Thread.sleep(1200);
        //line below will return pending attach count afer accept from location B
        getAnyCategoryCardsCountAfter("-1",pendingAttachCountCBeforeAcceptToC,"Equipment","Pending","PendingAttach",null);
        //line below will check if equipment is not present in Pending Attach after accept from location B
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId, "false");
        //line below will return available count afer accept from location B
        getAnyCategoryCardsCountAfter("+1",availableCountCBeforeAcceptToC,"Equipment","Available",null,null);
        //line below will return rear loaders count afer accept from location B
        getAnyCategoryCardsCountAfter("+1",rearLoadersCountCBeforeAcceptToC,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is present in rear loaders after accept from location B
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");
        //line below will check current location
        equipmentHistoryUtilities().getEquipmentDetailHistory(equipmentId,null,null,null,detachTo2);
        //line below will verify detachment history after accept on location C
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Accepted", exDetachDate2 + " " + detachHour2 + ":38", "","", receiver2, "", remarks2);


        //line below will open location B board after accept on location C
        loginPage().goToBoardLocationByDate(url, detachTo1 + "/", boardDate);
        logTestInfo(wDriver, "verification started on location B " + detachTo2 + " after accept detachment on location C");
        Thread.sleep(1200);
        //line below will return pending detach count afer accept on location C
        getAnyCategoryCardsCountAfter("-1",pendingDetachCountBBeforeAcceptToC,"Equipment","Pending","PendingDetach",null);
        //line below will check if equipment is not present in pending detach after accept on location C
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "false");


        //line below will open location A board after accept on location C
        //loginPage().goToBoardLocationByDate(url, assignedLocation + "/", boardDate);
        equipmentHistoryUtilities().navigateToAssignedLocation( equipmentId,assignedLocation,0 );
        logTestInfo(wDriver, "verification started on location A " + detachTo2 + " after accept detachment on location C");
        Thread.sleep(1200);
        //line below will check if equipment is present in detached after accept on location C
        equipmentPanelUtilities().verifyEquipmentPresent("Detached", detachTo2, null, equipmentId, "true");
        //line below will verify detachment history after accept on location C
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Accepted", exDetachDate2 + " " + detachHour2 + ":38", "","", receiver2, "", remarks2);


    }






}
