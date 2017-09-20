package equipmentTestSuites.equipmentSnowUpdate;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/13/2016.
 */
public class TEST002_RelayMisc_Down_SnowUpdate_Up extends AbstractStartWebDriver {


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
    private String downCode1 = "DN87";
    private String serviceLocation1 = "MN02";
    private String downHour1 = Utilities.get24HourFormat(-1);
    private String downMinute1 = "00";
    private String mechanic1 = "mechanic001";
    private String reporter1 = "reporter001";
    private String remarks1 = "remarks001";
    private String url = LoginData.getLoginData(getUrl());
    private String upHour = null;
    private String exUpDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");

    @Test(description = "Equipment Snow Update with 'Working Down' from Down category and then Up")
    public void equipmentSnowUpdate_RelayMisc_Down_SnowUpdate_Up() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","RearLoaders", 6);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Snow Update" );
        Reporter.log( "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true );
        logTestInfo(wDriver, "Scenario 2 - Ref# Minerva3767 - Update Equipment to Relay Misecellaneous. Then Down it and perform Snow Update with 'Working Down'. Then Up the equipment." );

        //line below will open ops board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders", "Down" );
        System.out.println( "Equipment Id is " + equipmentId );
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,"Relay","26",null,null);
        downHour1 = Utilities.get24HourFormat(-1);
        //line below will down a equipment before Snow Update
        equipmentDownActions().downEquipment(equipmentId,downCode1,serviceLocation1,downHour1,downMinute1,reporter1,mechanic1,remarks1,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        logTestInfo(wDriver, "verification started on location " + location + " before Snow Update" );
         //line below will check if equipment color is as expected in rear loaders category before snow update
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId,"Equipment", "navyBlue");
        //line below will check if equipment text color is as expected before snow update
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present before snow update
        equipmentPanelUtilities().equipmentBinType(equipmentId,"26",null);

        //line below will return down category count before Snow Update
        int downCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Down",null, null);
        //line below will return Relay category count before Snow Update
        int relayCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay",null, null);
        //line below will return Relay Miscellenous count before Snow Update
        int relayMiscCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay","MISCELLANEOUS", null);
        //line below will check if equipment is present in down category before Snow Update
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null,equipmentId, "true");

        //line below will get Snow Update Hour
        String updteHour = Utilities.get24HourFormat(0);
        //line below will perform Snow Update
        equipmentSnowUpdateActions().snowUpdate(equipmentId,plowType,plowDirections,chain,load,workingDown,snowAssignment);

        logTestInfo(wDriver, "verification started on location " + location + " after Snow Update with 'Working Down'" );
        //line below will return down count after snow update
        CommonActions.getAnyCategoryCardsCountAfter("-1",downCountBefore,"Equipment","Down",null, null);
        //line below will return relay count after snow update
        CommonActions.getAnyCategoryCardsCountAfter("+1",relayCountBefore,"Equipment","Relay",null, null);
        //line below will return relay Miscellaneous count after snow update
        CommonActions.getAnyCategoryCardsCountAfter("+1",relayMiscCountBefore,"Equipment","Relay","MISCELLANEOUS", null);
        //line below will check if equipment is present in Relay Miscellaneous after snow update
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","MISCELLANEOUS", null, equipmentId, "true");
        //line below will check if equipment is not present in Down after snow update
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null, equipmentId, "false");
        //line below will check if equipment color is as expected after snow update
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId,"Equipment", "orange");
        //line below will check if equipment text color is as expected in after snow update
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after snow update
        equipmentPanelUtilities().equipmentBinType(equipmentId,"26",null);
        //line below will check for equipment snow plow type indicator on equipment card after snow update
        equipmentPanelUtilities().getEquipmentSnowIndicator(equipmentId,"R",null);
        //line below will verify Snow Update history after Snow Update
        equipmentHistoryUtilities().getEquipmentSnowUpdateHistory(equipmentId,"1",plowType,plowDirections,chain,"",snowAssignment,exLastUpdated + " " + updteHour + ":",LoginData.getLoginData("username"));

        //line below will get up hour before up action
        upHour = Utilities.get24HourFormat(0);
        //line below will perform Up action for equipment with working down
        equipmentUpActions().upEquipment( equipmentId, "mechanic01", "reporter01", upHour, "00");
        //line below will check if equipment is present in Relay Miscellaneous after Up for equipment with Working Down
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","MISCELLANEOUS", null, equipmentId, "true");
        //line below will return up history expected result after up
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", null, "Set Up", exUpDate, upHour + ":" + "00", "", "", "reporter01", "mechanic01", "" );


    }



}
