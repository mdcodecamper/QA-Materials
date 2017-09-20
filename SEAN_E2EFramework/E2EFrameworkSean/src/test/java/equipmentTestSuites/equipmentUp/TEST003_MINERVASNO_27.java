package equipmentTestSuites.equipmentUp;

import common.actions.CommonActions;
import common.actions.EquipmentActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static _driverScript.AbstractStartWebDriver.extentTest;
import static _driverScript.AbstractStartWebDriver.getUrl;
import static _driverScript.AbstractStartWebDriver.wDriver;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 1/27/2017.
 */
public class TEST003_MINERVASNO_27 {

    private String location = "BKS10";   //EquipmentData.equipmentSendingLocation;
    private String garage = null;
    private String boardDate = Utilities.getDesiredDateInFormat(-1, "yyyyMMdd");

    private String url = LoginData.getLoginData(getUrl());
    private String shiftStartHour1 = Utilities.get24HourFormat(-1);
    private String shiftEndHour1 = Utilities.get24HourFormat(7);
    private String shiftStartMinute = "00";
    private String shiftEndMinute = "00";
    private String shift1 = shiftStartHour1 + shiftStartMinute + "-" + shiftEndHour1 + shiftEndMinute;
    private String downCode1 = "DN87";
    private String serviceLocation1 = "MN02";
    private String downHour1 = Utilities.get24HourFormat(-1);
    private String downMinute1 = "00";
    private String mechanic1 = "mechanic001";
    private String reporter1 = "reporter001";
    private String remarks1 = "remarks001";
    private String downCode2 = "DN39";
    private String serviceLocation2 = "MN02";
    private String downHour2 = Utilities.get24HourFormat(-1);
    private String downMinute2 = "00";
    private String mechanic2 = "mechanic002";
    private String reporter2 = "reporter002";
    private String remarks2 = "remarks002";
    private String downCode3 = "DN59";
    private String serviceLocation3 = "MN02";
    private String downHour3 = Utilities.get24HourFormat(-1);
    private String downMinute3 = "00";
    private String mechanic3 = "mechanic003";
    private String reporter3 = "reporter003";
    private String remarks3 = "remarks003";
    private String exUpDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String upHour = Utilities.get24HourFormat(0);
    private String upMinute = "00";
    private String mechanic = "mechanic001";
    private String reporter = "reporter001";
    private String downDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDownDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");

    @Test(description = "Up Equipment from previous day")
    public void equipmentUp_MinervaSno27() throws IOException, InterruptedException {
        setEquipmentLocationForTest("Available","RearLoaders", 1, null, "-1");
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Regression", "Up Equipment from previous day" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Ability to set a vehicle Up from previous day's ops board" );
        System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Ability to set a vehicle Up from previous day's ops board" );

        //line below will open current board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close person menu
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available","RearLoaders","Down" );

        //line below will down a equipment
        equipmentDownActions().downEquipment(equipmentId,downCode1,serviceLocation1,downHour1,downMinute1,reporter1,mechanic1,remarks1,downCode2,serviceLocation2,downHour2,downMinute2,reporter2,mechanic2,remarks2,downCode3,serviceLocation3,downHour3,downMinute3,reporter3,mechanic3,remarks3);

        //line below will return down category count before up
        int downCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Down",null,null);
        //line below will return available category count before up
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        //line below will return available rear loaders category count before up
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);
        //line below will check if equipment is present in down before up
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null,equipmentId, "true");

        //line below will verify right click is present for up action
        EquipmentActions.verifyModalWindowRightClick(wDriver,equipmentId,"Up");

        //line below will up a equipment
        upHour = Utilities.get24HourFormat(0);
        equipmentUpActions().upEquipment( equipmentId, mechanic, reporter, upHour, upMinute );
        logTestInfo(wDriver, "verification started on location " + location + " after up" );

        //line below will return down count after up
        CommonActions.getAnyCategoryCardsCountAfter("-1",downCountBefore,"Equipment","Down",null,null);
        //line below will return available count after up
        CommonActions.getAnyCategoryCardsCountAfter("+1",availableCountBefore,"Equipment","Available",null,null);
        //line below will return rear loaders count after up
        CommonActions.getAnyCategoryCardsCountAfter("+1",rearLoadersCountBefore,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is not present in down after up
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null,equipmentId, "false");
        //line below will check if equipment is present in rear loaders after up
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null,equipmentId, "true");

        //line below will return up history expected result after up
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", null, "Set Up", exUpDate, upHour + ":" + upMinute, "", "", reporter, mechanic, "" );

        //Line below will verify up/down history after up for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"2","1","Set Down",exDownDate,downHour1 + ":"  + downMinute1,"DN87-ACCIDENT",serviceLocation1,reporter1,mechanic1,remarks1);
        //Line below will verify up/down history after up for down code 2
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"2","2","Set Down",exDownDate,downHour2 + ":"  + downMinute2,"DN39-AIR LEAK",serviceLocation2,reporter2,mechanic2,remarks2);
        //Line below will verify up/down history after up for down code 3
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"2","3","Set Down",exDownDate,downHour3 + ":"  + downMinute3,"DN59-BATTERIES",serviceLocation3,reporter3,mechanic3,remarks3);


    }





}
