package equipmentTestSuites.equipmentDown.Add;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 10/12/2016.
 */
public class TEST001_RearLoaders_Available_3Codes extends AbstractStartWebDriver{


    /***************************************************************
     *Scenario 1 - Detach A Single Bin Equipment From Available Group
     ****************************************************************/
    //private WebDriver wdriver;
    //Test Data
    // private String url = LoginCredentials.url;
    //private String equipmentId = "25CW-857";
    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
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


    @Test(description = "Equipment Down")
    public void equipmentDown_RearLoaders_Available_3Codes() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available", "RearLoad", 4);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke","Down Equipment");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Down a equipment from Available Rear Loaders category with 3 down codes");
        System.out.println("**************************************************************************************");
        extentTest.log( LogStatus.INFO, "Scenario 1 - Down a equipment from Available Rear Loaders category with 3 down codes");

        //line below will open sending location board
        //loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //Thread.sleep(4000);
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available","RearLoaders","Down" );
        System.out.println("Equipment Id is " + equipmentId);
        logTestInfo(wDriver,"verification started on location " + location + " before down");
        //line below will return down category count before down
        String downCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Down",null,null);
        //line below will return available category count before down
        String availableCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Available",null,null);
        //line below will return available rear loaders category count before down
        String rearLoadersCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Available","RearLoaders",null);
        //line below will check if equipment is present in rear loaders before down
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null,equipmentId, "true");
        //line below will check if equipment color is as expected in rear loaders category before down
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in rear loaders category before down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present in rear loaders category before down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will down a equipment
        equipmentDownActions().downEquipment(equipmentId,downCode1,serviceLocation1,downHour1,downMinute1,reporter1,mechanic1,remarks1,downCode2,serviceLocation2,downHour2,downMinute2,reporter2,mechanic2,remarks2,downCode3,serviceLocation3,downHour3,downMinute3,reporter3,mechanic3,remarks3);
        logTestInfo(wDriver,"verification started on location " + location + " after down");
        //wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //line below will return down count after down
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(+1,downCountBefore,"Down",null,null);
        //line below will return available count after down
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(-1,availableCountBefore,"Available",null,null);
        //line below will return rear loaders count after down
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(-1,rearLoadersCountBefore,"Available","RearLoaders",null);
        //line below will check if equipment is not present in rear loaders after down
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "false");
        //line below will check if equipment is present in down after down
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null, equipmentId, "true");
        //line below will check if equipment color is as expected in down category after down
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in down category after down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        executorScrollIntoView(wDriver,wDriver.findElement( By.xpath("//*[contains(@class,'equipment card') and contains(@data-id,'"+ equipmentId +"')]")));
        Thread.sleep(1000);
        //line below will check equipment rear loader bin type is present in down category after down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //Line below will verify up/down history after down for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","1","Set Down",exDownDate,downHour1 + ":"  + downMinute1,"DN87-ACCIDENT",serviceLocation1,reporter1,mechanic1,remarks1);
        //Line below will verify up/down history after down for down code 2
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","2","Set Down",exDownDate,downHour2 + ":"  + downMinute2,"DN39-AIR LEAK",serviceLocation2,reporter2,mechanic2,remarks2);
        //Line below will verify up/down history after down for down code 3
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","3","Set Down",exDownDate,downHour3 + ":"  + downMinute3,"DN59-BATTERIES",serviceLocation3,reporter3,mechanic3,remarks3);



    }



}
