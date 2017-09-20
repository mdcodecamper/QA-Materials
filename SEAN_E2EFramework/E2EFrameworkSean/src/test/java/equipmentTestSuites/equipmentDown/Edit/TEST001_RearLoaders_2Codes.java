package equipmentTestSuites.equipmentDown.Edit;

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
 * Created by skashem on 10/19/2016.
 */
public class TEST001_RearLoaders_2Codes extends AbstractStartWebDriver {

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


    @Test(description = "Equipment Edit Down")
    public void equipmentEditDown_RearLoaders_2Codes() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available", "RearLoaders", 4);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Smoke","Edit Down Equipment");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Edit a Down equipment with two down codes which has one downcode");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - Edit a Down equipment with two down codes which has one downcode");

        //line below will open sending location board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //Thread.sleep(4000);
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available","RearLoaders", "Down" );
        System.out.println("Equipment Id is " + equipmentId);
        //line below will down a equipment before edit down
        equipmentDownActions().downEquipment(equipmentId,downCode1,serviceLocation1,downHour1,downMinute1,reporter1,mechanic1,remarks1,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        extentTest.log(LogStatus.INFO,"verification started on location " + location + " before edit down");
        //line below will return down category count before edit down
        String downCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Down",null,null);
        //line below will check if equipment is present in down category before edit down
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null,equipmentId, "true");
        //line below will check if equipment color is as expected in down category before edit down
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in down category before edit down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present in down category before edit down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will edit a down  equipment
        equipmentDownActions().editDownEquipment(equipmentId,downCode2,null,null,null,null,null,"remarks01Redefined",downCode3,serviceLocation2,downHour2,downMinute2,reporter2,mechanic2,remarks2,null,null,null,null,null,null,null);
        extentTest.log(LogStatus.INFO,"verification started on location " + location + " after edit down");
        //wDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //line below will return down count after edit down
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(0,downCountBefore,"Down",null,null);
        //line below will check if equipment is present in down after down
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null, equipmentId, "true");
        //line below will check if equipment color is as expected in down category after edit down
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in down category after edit down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present in down category after edit down
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //Line below will verify down history after edit down for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","1","Add/Edit Down",exDownDate,downHour1 + ":"  + downMinute1,"DN39-AIR LEAK",serviceLocation1,reporter1,mechanic1,"remarks01Redefined");
        //Line below will verify down history after edit down for down code 2
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1","2","Add/Edit Down",exDownDate,downHour2 + ":"  + downMinute2,"DN59-BATTERIES",serviceLocation2,reporter2,mechanic2,remarks2);
        //Line below will verify previous down history after edit down for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"2",null,"Set Down",exDownDate,downHour1 + ":"  + downMinute1,"DN87-ACCIDENT",serviceLocation1,reporter1,mechanic1,remarks1);

    }
}
