package equipmentTestSuites.equipmentSnowUpdate;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 2/14/2017.
 */
public class TEST011_CurrentDay_MinervaSno27 extends AbstractStartWebDriver {

    /***************************************************************
     *Scenario 1 - Detach A Single Bin Equipment From Available Group
     ****************************************************************/
    //private WebDriver wdriver;
    //Test Data
    // private String url = LoginCredentials.url;
    //private String equipmentId = "25CW-857";
    private String location = "SI01";   //EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String downDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDownDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String downCode1 = "DN87";
    private String serviceLocation1 = "MN02";
    private String downHour1 = Utilities.get24HourFormat(0);
    private String downMinute1 = "00";
    private String mechanic1 = "mechanic001";
    private String reporter1 = "reporter001";
    private String remarks1 = "remarks001";
    private String downCode2 = "DN39";
    private String serviceLocation2 = "MN02";
    private String downHour2 = Utilities.get24HourFormat(0);
    private String downMinute2 = "00";
    private String mechanic2 = "mechanic002";
    private String reporter2 = "reporter002";
    private String remarks2 = "remarks002";
    private String downCode3 = "DN59";
    private String serviceLocation3 = "MN02";
    private String downHour3 = Utilities.get24HourFormat(0);
    private String downMinute3 = "00";
    private String mechanic3 = "mechanic003";
    private String reporter3 = "reporter003";
    private String remarks3 = "remarks003";
    private String url = LoginData.getLoginData(getUrl());
    private String shift1 = "1200-2000";
    private String shift2 = "1300-2100";
    private String shift3 = "1400-2200";
    private String shift4 = "1500-2300";
    private String shift5 = "1600-0000";
    private String shift6 = "1700-0100";
    private String shift7 = "1800-0200";
    private String shift8 = "1900-0300";
    private String shift9 = "2000-0400";
    private String shift10 = "2100-0500";
    private String exLastUpdated = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String plowType = "Regular Plow";
    private String plowDirections = "Right";
    private String chain = "Not Chained";
    private String load = null;
    private String snowAvailability = null;
    private String snowAssignment = "Yes";


    @Test(description = "Equipment Snow Update")
    public void equipmentSnowUpdate_MinervaSno27_CurrentDay() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available", "RearLoaders", 4);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Regression","Snow Update");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Peform Snow Update on a equipment on current day with active tasks on previous day");
        System.out.println("**************************************************************************************");


        //line below will open previous day board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        //line below will getEquipment from a task category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders");
        System.out.println("Equipment Id is " + equipmentId);
        Thread.sleep( 2000 );
        //line below will get Snow Update Hour
        String updteHour = Utilities.get24HourFormat(0);
        //line below will perform snow update on a equipment assigned to active task
        equipmentSnowUpdateActions().snowUpdateRightClick("Equipment",equipmentId,"Regular","Right","Not Chained",null,null,"Yes");
        //line below will check if equipment is present in Rear Loaders after snow update
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null,equipmentId, "true");
        //line below will check if equipment color is as expected after snow update in pending load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","orange");
        //line below will check if equipment text color is as expected in down category before edit down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment dual bins bin type is present in down category before edit down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will verify Snow Update history after Snow Update
        equipmentHistoryUtilities().getEquipmentSnowUpdateHistory(equipmentId,"1",plowType,plowDirections,chain,"",snowAssignment,exLastUpdated + " " + updteHour + ":",LoginData.getLoginData("username"));


        //line below will open previous day board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(-1, "yyyyMMdd"));

        //line below will verify equipment is in Rear Loaders category after snow update on previous day board
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "true");
        //line below will check if equipment color is as expected in down category after Snow Update
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","orange");
        //line below will check if equipment text color is as expected in down category after Snow Update
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present in down category after Snow Update
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);

        //line below will verify Snow Update history after Snow Update
        equipmentHistoryUtilities().getEquipmentSnowUpdateHistory(equipmentId,"1",plowType,plowDirections,chain,"",snowAssignment,exLastUpdated + " " + updteHour + ":",LoginData.getLoginData("username"));

    }











}
