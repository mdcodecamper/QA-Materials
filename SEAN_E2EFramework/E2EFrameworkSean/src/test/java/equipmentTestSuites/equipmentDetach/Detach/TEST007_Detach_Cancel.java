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
 * Created by skashem on 12/29/2016.
 */
public class TEST007_Detach_Cancel extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation;
    private String detachTo = EquipmentData.equipmentReceivingLocation;
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


    @Test(description = "Equipment Detach & Cancel")
    public void equipment_Detach__Cancel() throws InterruptedException, IOException {

        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        System.out.println("**************************************************************************************");
        System.out.println("Scenario 7 - Initiate Detach and then Cancel equipment");
        System.out.println("**************************************************************************************");

        extentTest.assignCategory( "regression", "Detach and Cancel Equipment" );
        logTestInfo( wDriver, "Scenario 7 - Initiate Detach and then Cancel equipment" );
        setEquipmentLocationForTest("Available","RearLoaders",1);
        location = EquipmentData.equipmentSendingLocation;
        //line below will open sending location board
        loginPage().goToBoardLocationByDate(url,(location + "/").toString(), boardDate);

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders" );
        detachHour = Utilities.get24HourFormat(-2);

        int availableCountBeforeDetach = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        int pendingDetachCountBeforeDetach = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);
        int rearLoadersCountBeforeDetach = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);

        //line below will detach a equipment
        equipmentDetachActions().detachEquipment(equipmentId, detachTo, detachHour, detachMinute, driver);
        Thread.sleep(2000);
        logTestInfo(wDriver, "verification started on location  " + location + " after detach for " + equipmentId);
        //line below will return pending detach count after detach
        CommonActions.getAnyCategoryCardsCountAfter("+1",pendingDetachCountBeforeDetach,"Equipment","Pending","PendingDetach",null);
        //line below will check if equipment is present in pending detach before accept detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "true");
        //line below will return available count after detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableCountBeforeDetach,"Equipment","Available",null,null);
        //line below will return rear loaders count after detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",rearLoadersCountBeforeDetach,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is  not present in rear loaders before accept detach
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "false");

        //line below will verify equipment detachment history after detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, EquipmentData.verifyLocationIsDualGarage(location), detachTo, LoginData.getLoginData("username"), driver, "");

        //lines below will get counts before cancel detach
        int availableCountBeforeCancel = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        int pendingDetachCountBeforeCancel = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);
        int rearLoadersCountBeforeCancel = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);
        // line below will cancel a equipment detachment
        equipmentCancelDetachActions().cancelEquipmentDetachment(equipmentId);

        logTestInfo( wDriver,"Verification Started after cancelled detach for " + equipmentId);
        //line below will verify equipment detachment history after canceling detachment
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Cancelled",  exDetachDate, "", "", LoginData.getLoginData("username"), "", "");
        //line below will return pending detach count after cancel
        CommonActions.getAnyCategoryCardsCountAfter("-1",pendingDetachCountBeforeCancel,"Equipment","Pending","PendingDetach",null);
        //line below will check if equipment is present in pending detach after cancel
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "false");
        //line below will return available count after cancel
        CommonActions.getAnyCategoryCardsCountAfter("+1",availableCountBeforeCancel,"Equipment","Available",null,null);
        //line below will return rear loaders count after cancel
        CommonActions.getAnyCategoryCardsCountAfter("+1",rearLoadersCountBeforeCancel,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is  not present in rear loaders cancel detach
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");
       //Thread.sleep(2000);
    }



}
