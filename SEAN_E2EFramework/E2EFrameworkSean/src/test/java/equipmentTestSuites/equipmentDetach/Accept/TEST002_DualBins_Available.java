package equipmentTestSuites.equipmentDetach.Accept;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.getAnyCategoryCardsCount;
import static common.actions.CommonActions.getAnyCategoryCardsCountAfter;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipment.abstractBase.EquipmentBasePage.equipmentHistoryUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 11/18/2016.
 */
public class TEST002_DualBins_Available extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation;     //"BKS10";
    private String detachTo = EquipmentData.equipmentReceivingLocation;    //"MN01";
    private String detachHour = Utilities.get24HourFormat(0);
    private String detachMinute = "00";
    private String driver = "test002";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String detachDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDetachDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String url = LoginData.getLoginData(getUrl());
    private String receiver = "receiver002";
    private String remarks = "remarks002";

    @Test(description = "Equipment Accept Detachment")
    public void equipmentAcceptDetach_DualBins_Available() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","DualBin", 1);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke", "Accept Equipmemnt Detachment");
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Accept a dual bins equipment on receiving location");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - Accept a dual bins equipment on receiving location");

        //line below will open ops board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        // Thread.sleep(4000);

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from a category on sending location
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins");
        System.out.println("Equipment Id is " + equipmentId);
        detachHour = Utilities.get24HourFormat(0);
        //line below will detach a equipment before accept detach on sending location
        equipmentDetachActions().detachEquipment(equipmentId, detachTo, detachHour, detachMinute, driver);
        logTestInfo(wDriver, "verification started on sending location " + location + " before accept detach");
        //line below will return pending detach category count before accept detach on sending location
        int pendingDetachCountBefore = getAnyCategoryCardsCount("Equipment","Pending", "PendingDetach", null);
        //line below will check if equipment is present in pending detach before accept detach on sending location
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "true");
        //line below will return detached category count before accept detach on sending location
        int detachCountBefore = getAnyCategoryCardsCount("Equipment","Detached", null, null);
        //line below will verify equipment detachment history before accept detach on sending location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, location, detachTo, LoginData.getLoginData("username"), driver, "");


        //line below opens receiving location and refresh to the board page before accept detach
        loginPage().goToBoardLocationByDate(url, detachTo + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        //Thread.sleep(4000);
        logTestInfo(wDriver, "verification started on receiving location " + detachTo + " before accept detach");

        //line below will return pendting attach category count before accept detach on receiving location
        int pendingAttachCountBefore = getAnyCategoryCardsCount("Equipment","Pending", "PendingAttach", null);
        //line below will check if equipment is present in pending attach before accept detach on receiving location
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId, "true");
        //line below will return available category count before accept detach on receiving location
        int availableCountBefore = getAnyCategoryCardsCount("Equipment","Available", null, null);
        //line below will return available Rear Loaders category count before accept detach on receiving location
        int dualBinsCountBefore = getAnyCategoryCardsCount("Equipment","Available", "DualBins", null);
        //line below will verify equipment detachment history before accept detach on receiving location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, location, detachTo, LoginData.getLoginData("username"), driver, "");
        detachHour = Utilities.get24HourFormat(0);
        //line below will accept equipment detachment on receiving location
        equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId, receiver, remarks,null,null);

        logTestInfo(wDriver, "verification started on receiving location " + detachTo + " after accept detach");
        //line below will return pendting attach category count after accept detach on receiving location
        getAnyCategoryCardsCountAfter("-1", pendingAttachCountBefore, "Equipment","Pending", "PendingAttach", null);
        //line below will check if equipment is not present in pending attach after accept detach on receiving location
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingAttach", null, equipmentId, "false");
        //line below will return Available count after accept detach
        getAnyCategoryCardsCountAfter("+1", availableCountBefore, "Equipment","Available", null, null);
        //line below will return Rear Loaders count after accept detach
        getAnyCategoryCardsCountAfter("+1", dualBinsCountBefore, "Equipment","Available", "DualBins", null);
        //line below will check if equipment is present in rear loaders after accept detach on receiving location
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "DualBins", null, equipmentId, "true");
        //line below will verify equipment detachment history after accept detach on receiving location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Accepted", exDetachDate + " " + detachHour + ":", "", "", receiver, "", remarks);
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Accepted", exDetachDate + " " + detachHour + ":", "", "", receiver, "", remarks);
        String equipmentAssignedLocation = equipmentHistoryUtilities().getAssignedLocation(equipmentId);


        //line below opens sending location and refresh to the board page before accept detach
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        logTestInfo(wDriver, "verification started on sending location " + location + " after accept detach");
        //line below will return Pending Detach count after accept detach
        getAnyCategoryCardsCountAfter("-1", pendingDetachCountBefore, "Equipment","Pending", "PendingDetach", null);
        //line below will check if equipment is not present in pending detach after detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "false");
        //line below will verify equipment detachment history after accept detach on sending location
        equipmentHistoryUtilities().verifyEquipmentAssignedLocation(equipmentId, equipmentAssignedLocation, location, detachTo, detachCountBefore);
        //line below will verify equipment detachment history after accept detach on sending location
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Accepted", exDetachDate + " " + detachHour + ":", "", "", receiver, "", remarks);


    }
}
