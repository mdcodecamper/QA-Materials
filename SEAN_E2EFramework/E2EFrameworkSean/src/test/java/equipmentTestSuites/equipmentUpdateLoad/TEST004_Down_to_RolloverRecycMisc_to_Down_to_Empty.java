package equipmentTestSuites.equipmentUpdateLoad;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipment.abstractBase.EquipmentBasePage.equipmentHistoryUtilities;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/9/2016.
 */
public class TEST004_Down_to_RolloverRecycMisc_to_Down_to_Empty extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String loadDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLoadDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String loadHour = Utilities.get24HourFormat(0);
    private String newStatus1 = "Rollover";
    private String material1 = "44";
    private String newStatus2 = null;
    private String material2 = null;
    private String upHour = Utilities.get24HourFormat(0);
    private String upMinute = "00";
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Update Load for Rear Loaders From Down to Rollover Recycling Misc to Down")
    public void equipmentUpdateLoad_Down_RolloverRecycMisc_Down_Empty() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","RearLoaders", 1);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Update Load" );
        //System.out.println( "**************************************************************************************" );
        //System.out.println( "Scenario 4 - ref# MINERVA-3766 - Update Load for Rear Loaders From Down then up the equipment and then Update to Rollover Recycling Misc to Down and then Empty" );
        //System.out.println( "**************************************************************************************" );
        logTestInfo(wDriver, "Scenario 4 - ref# MINERVA-3766 - Update Load for Rear Loaders From Down then up the equipment and then Update to Rollover Recycling Misc to Down and then Empty" );

        //line below will open ops board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available", "RearLoaders", "Down");
        System.out.println("Equipment Id is " + equipmentId);
        //line below will down an equipment before updated load
        equipmentDownActions().downEquipment( equipmentId, "DN87", "MN01", Utilities.get24HourFormat( -2 ), "00", "reporter01", "mechanic01", "remarks01", "DN39", "MN01", Utilities.get24HourFormat( -2 ) , "00", "reporter02", "mechanic02", "remarks02", null, null, null, null, null, null, null );
        logTestInfo(wDriver,"verification started on location " + location + " before Update Load");
        //line below will return Down category count before update Load
        int downCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Down",null,null);
        //line below will return rollover category count before Update Load
        int rolloverCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Rollover",null,null);
        //line below will return rollover recyling misc count before Update Load
        int rolloverRecycMiscCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Rollover","RECYCLING","Recycling Misc");
        //line below will check if equipment is present in Down category before Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null,equipmentId, "true");
        //line below will check if equipment color is as expected in dual bin category before update load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in dual bin category before update load
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment dual bin type is present before update load
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        loadHour = Utilities.get24HourFormat(0);
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,newStatus1,material1,newStatus2,material2);
        upHour = Utilities.get24HourFormat(-1);
        //line below will up a equipment
        equipmentUpActions().upEquipment( equipmentId, "mechanic01", "reporter01", upHour, upMinute );
        logTestInfo(wDriver,"verification started on location " + location + " after Update Load and upping the equipmennt");
        //line below will return down count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("-1",downCountBefore,"Equipment","Down",null, null);
       //line below will check if equipment is not present in down category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null, equipmentId, "false");
        //line below will return rollover count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("+1",rolloverCountBefore,"Equipment","Rollover",null, null);
        //line below will return rollover recycling misc count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("+1",rolloverRecycMiscCountBefore,"Equipment","Rollover","RECYCLING", "Recycling Misc");
        //line below will check if equipment is present in rollover recycling misc category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Rollover","RECYCLING","Recycling Misc", equipmentId, "true");
        //line below will verify Update Load Status history after Update Load
        equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId,"1","Load","ROLLOVER","44-TEXTILES",exLoadDate + " " + loadHour + ":",LoginData.getLoginData("username"),"Click On Equipment");

        logTestInfo(wDriver,"verification started on location " + location + " before Updating Load again so equipment can go back to Down category");
        //line below will down an equipment before updated load
        equipmentDownActions().downEquipment( equipmentId, "DN87", "MN01", Utilities.get24HourFormat( 0 ), "00", "reporter01", "mechanic01", "remarks01", "DN39", "MN01", Utilities.get24HourFormat( 0 ) , "00", "reporter02", "mechanic02", "remarks02", null, null, null, null, null, null, null );
        //line below will return Down category count before update Load again
        int downCountBefore2 = CommonActions.getAnyCategoryCardsCount("Equipment","Down",null,null);
        //line below will return rollover category count before Update Load again
        int rolloverCountBefore2 = CommonActions.getAnyCategoryCardsCount("Equipment","Rollover",null,null);
        loadHour = Utilities.get24HourFormat(0);
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,"Empty",null,null,null);
        //line below will return down count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("0",downCountBefore2,"Equipment","Down",null, null);
        //line below will check if equipment is present in down category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null, equipmentId, "true");
        //line below will return rollover count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("0",rolloverCountBefore2,"Equipment","Rollover",null, null);
        //line below will check if equipment is not present in rollover recycling misc category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Rollover","RECYCLING","Recycling Misc", equipmentId, "false");

        //line below will verify Update Load Status history after Update Load
        equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId,"1","Load","EMPTY","",exLoadDate + " " + loadHour + ":","","Click On Equipment");


    }




}
