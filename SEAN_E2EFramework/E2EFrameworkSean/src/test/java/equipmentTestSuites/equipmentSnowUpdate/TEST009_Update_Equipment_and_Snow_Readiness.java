package equipmentTestSuites.equipmentSnowUpdate;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/27/2016.
 */
public class TEST009_Update_Equipment_and_Snow_Readiness extends AbstractStartWebDriver {


    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String lastUpdated = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLastUpdated = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String plowType = "Regular Plow";
    private String plowDirections = "Right";
    private String chain = "Not Chained";
    private String load = null;
    private String workingDown = "Yes";
    private String snowAssignment = "Yes";
    private String url = LoginData.getLoginData(getUrl());
    private String downHour1 = Utilities.get24HourFormat(-1);
    private String downMinute1 = "00";
    private String loadStatus = "RELAY";
    private String material = "01";
    private String material_long = "01-H/H COLLECTION (CURBSIDE)";
    private String mechanic1 = "mechanic001";
    private String reporter1 = "reporter001";
    private String remarks1 = "remarks001";
    private String downCode1 = "DN39";
    private String downCode1_str = "DN39-AIR LEAK";
    private String serviceLocation = "MN01";


    @Test(description = "Update Load, down Equipment, update snow readiness, up Equipment")
    public void equipmentSnowUpdate_Load_Down_Snow_Readiness() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","RearLoaders",1);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Regression", "Update Load, down Equipment, update snow readiness, up Equipment" );
        //Reporter.log( "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true );
        // System.out.println( "**************************************************************************************" );
        //System.out.println( "Scenario 1 - Update Load, down Equipment, update snow readiness, up Equipment" );
        //System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Scenario 9 - Update Load, down Equipment, update snow readiness, up Equipment" );

        //line below will open ops board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from Available Rear Loaders category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Available", "RearLoaders");
        System.out.println("Equipment Id is " + equipmentId);
        //check if equipment is not down, otherwise Up before proceeding
        logTestInfo(wDriver, "Checking if Down option is available (for Working Down equipment)");
        WebElement card = wDriver.findElement(By.xpath("//*[contains(@data-id,'"+ equipmentId +"')]"));
        try {
            if (card.getAttribute("data-actions").contains("Edit Down")) {
                logTestInfo(wDriver, "Setting equipment Up");
                equipmentUpActions().upEquipment(equipmentId, mechanic1, reporter1, Utilities.get24HourFormat(0), "00");
            }
        }
        catch (Exception e) {
            logTestInfo(wDriver, "Problem getting equipment actions " + e.getMessage());
        }

        //Code below will update and verify load

        extentTest.log(LogStatus.INFO,"verification started on location " + location + " before Update Load");
        //line below will return available category count before update Load
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        //line below will return relay category count before Update Load
        int relayCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay",null,null);
        //line below will return available rear loaders category count before Update Load
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);
        //line below will return Recycling category count before Update Load
        int coollectionCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay","COLLECTION",null);
        //line below will check if equipment is present in rear loaders category before Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null,equipmentId, "true");
        //line below will check if equipment color is as expected in rear loaders category before update load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in rear loaders category before update load
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present before update load
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        String loadHour = Utilities.get24HourFormat(0);
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,loadStatus,material,null,null);
        extentTest.log(LogStatus.INFO,"verification started on location " + location + " for equipmentId " + " after Update Load");
        //line below will return available count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableCountBefore,"Equipment","Available",null,null);
        //line below will return rear loaders count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("-1",rearLoadersCountBefore,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is not present in rear loaders category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "false");
        //line below will return relay count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("+1",relayCountBefore,"Equipment","Relay",null,null);
        //line below will return relay recycling category count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("+1",coollectionCountBefore,"Equipment","Relay","COLLECTION",null);
        //line below will check if equipment is present in relay COLLECTION category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","COLLECTION", null, equipmentId, "true");
        //line below will check if equipment color is as expected in collection category after update load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment", "navyBlue");
        //line below will check if equipment text color is as expected collection category after update load
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after update load
        equipmentPanelUtilities().equipmentBinType(equipmentId,"01",null);
        //line below will get single bin material color
        equipmentColorUtilities().equipmentBinColor(equipmentId,"white",null);
        //line below will verify Update Load Status history after Update Load
        equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId,"1","Load",loadStatus,material_long,exLastUpdated + " " + loadHour + ":",LoginData.getLoginData("username"));

        //--------------------------
        //code below will down equipment
        int downCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Down",null, null);
        //line below will return relay category count before down
        int relayCountBeforeDown = CommonActions.getAnyCategoryCardsCount("Equipment","Relay",null,null);
        //line below will return relay collection category count before down
        int coollectionCountBeforeDown = CommonActions.getAnyCategoryCardsCount("Equipment","Relay","COLLECTION",null);

        String downHour = Utilities.get24HourFormat(-2);
        equipmentDownActions().downEquipment( equipmentId, downCode1, serviceLocation, downHour, "00", reporter1, mechanic1, remarks1,
                                              null, null, null, null, null, null, null , null, null, null, null, null, null, null );
        Thread.sleep(2000);
        logTestInfo(wDriver, "verification started on location " + location + " for equipment " + equipmentId + " after down" );
        //Line below will verify up/down history before up for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", null, "Set Down", exLastUpdated, downHour  + ":" + "00", downCode1_str, serviceLocation, reporter1, mechanic1, remarks1 );
        //line below will check if equipment is present in down before up
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "true" );
        //line below will check if equipment is not present in relay COLLECTION category after down
        equipmentPanelUtilities().verifyEquipmentPresent( "Relay", "COLLECTION", null, equipmentId, "false" );
        //line below will return Relay count after down
        CommonActions.getAnyCategoryCardsCountAfter("-1",relayCountBeforeDown,"Equipment","Relay",null, null);
        //line below will return Relay COLLECTION count after down
        CommonActions.getAnyCategoryCardsCountAfter("-1",coollectionCountBeforeDown,"Equipment","Relay","COLLECTION",null);
        //line below will return down count after down
        CommonActions.getAnyCategoryCardsCountAfter("+1",downCountBefore,"Equipment","Down",null, null);

        //----------------------------
        //code below will perform Snow Update
        //lines below will return counts before Snow Update
        int downCountBeforeSnowUpdate = CommonActions.getAnyCategoryCardsCount("Equipment","Down",null, null);
        int relayCountBeforeSnowUpdate = CommonActions.getAnyCategoryCardsCount("Equipment","Relay",null,null);
        int coollectionCountBeforeSnowUpdate = CommonActions.getAnyCategoryCardsCount("Equipment","Relay","COLLECTION",null);

        String snowUpdateHour = Utilities.get24HourFormat(0);
        equipmentSnowUpdateActions().snowUpdate(equipmentId,plowType,plowDirections,chain,load,workingDown,snowAssignment);
        logTestInfo(wDriver,"Verification started for snow equipment after Snow Update");
        //line below will verify Snow Update history after Snow Update
        equipmentHistoryUtilities().getEquipmentSnowUpdateHistory(equipmentId,"1",plowType,plowDirections,chain,"",snowAssignment,exLastUpdated + " " + snowUpdateHour + ":",LoginData.getLoginData("username"));
        //line below will check if equipment is present in Relay after Snow Update
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","COLLECTION", null, equipmentId, "true");
        //line below will return down count Snow Update
        CommonActions.getAnyCategoryCardsCountAfter("-1", downCountBeforeSnowUpdate,"Equipment","Down",null, null);
        //line below will check if equipment is not present in Down after Snow Update
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null, equipmentId, "false");
        //line below will return available count after Snow Update
        CommonActions.getAnyCategoryCardsCountAfter("+1",relayCountBeforeSnowUpdate,"Equipment","Relay",null, null);
        //line below will return Relay COLLECTION count after Snow Update
        CommonActions.getAnyCategoryCardsCountAfter("+1",coollectionCountBeforeSnowUpdate,"Equipment","Relay","COLLECTION", null);
        ////line below will check if equipment top color is as expected
        equipmentColorUtilities().verifyEquipmentCardRowTopColor(equipmentId, "black", true);
        //line below will check if equipment color is as expected after Snow Update
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment", "orange");
        //line below will check if equipment text color is as expected after Snow Update
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after Snow Update
        equipmentPanelUtilities().equipmentBinType(equipmentId,"01",null);
        //=========================================================
        //code below will Up equipment
        String upHour = Utilities.get24HourFormat(0);
        //line below will up a equipment
        equipmentUpActions().upEquipment( equipmentId, mechanic1, reporter1, upHour, "00" );
        logTestInfo(wDriver, "verification started on location " + location + " after up" );
        //line below will check if equipment is not present in down after up
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "false" );
        //line below will check if equipment is present in Relay after Up
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","COLLECTION", null, equipmentId, "true");
        //line below will return up history expected result after up
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", null, "Set Up", exLastUpdated, upHour + ":" + "00", "", "", reporter1, mechanic1, "" );
        ////line below will check if equipment top color is as expected
        equipmentColorUtilities().verifyEquipmentCardRowTopColor(equipmentId, "orange", false);
        //line below will check if equipment color is as expected after Up
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment", "orange");
        //line below will check if equipment text color is as expected after Up
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after Up
        equipmentPanelUtilities().equipmentBinType(equipmentId,"01",null);
    }







}
