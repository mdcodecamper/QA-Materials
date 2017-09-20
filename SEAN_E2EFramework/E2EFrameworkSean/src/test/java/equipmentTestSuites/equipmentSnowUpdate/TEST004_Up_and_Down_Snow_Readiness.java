package equipmentTestSuites.equipmentSnowUpdate;

import _driverScript.AbstractStartWebDriver;
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
 * Created by skashem on 12/27/2016.
 */
public class TEST004_Up_and_Down_Snow_Readiness extends AbstractStartWebDriver {


    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String lastUpdated = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLastUpdated = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String plowType = "Regular Plow";
    private String plowDirections = "Right";
    private String chain = "Not Chained";
    private String load = null;
    private String workingDown = null;
    private String snowAssignment = "Yes";
    private String url = LoginData.getLoginData(getUrl());
    private String downCode1 = "DN87";
    private String serviceLocation1 = "MN02";
    private String downHour1 = Utilities.get24HourFormat(-1);
    private String downMinute1 = "00";
    private String mechanic1 = "mechanic001";
    private String reporter1 = "reporter001";
    private String remarks1 = "remarks001";


    @Test(description = "Equipment up and down snow readiness")
    public void equipmentSnowUpdate_Up_Down_Snow_Readiness() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "RearLoaders", 2);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Regression", "Snow Update up and down snow readiness" );
        //Reporter.log( "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true );
        // System.out.println( "**************************************************************************************" );
        //System.out.println( "Scenario 1 - Perform Snow Update snow equpment for Front End Loader" );
        //System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Senario 4 - Ref#MINERVA-3778 - Equipment up and down snow readiness" );

        //line below will open ops board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from snow FED category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Available", "RearLoaders", "Down");

        //line below will get Snow Update Hour
        String snowUpdateHour = Utilities.get24HourFormat(0);
        //line below will perform Snow Update
        equipmentSnowUpdateActions().snowUpdate(equipmentId,plowType,plowDirections,chain,load,workingDown,snowAssignment);
        logTestInfo(wDriver,"Verification started for snow equipment before Down");
        //line below will verify Snow Update history after Snow Update
        equipmentHistoryUtilities().getEquipmentSnowUpdateHistory(equipmentId,"1",plowType,plowDirections,chain,"",snowAssignment,exLastUpdated + " " + snowUpdateHour + ":",LoginData.getLoginData("username"));
        //line below will get down count before down
        int downCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Down",null, null);
        //line below will get available count before down
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null, null);
        //line below will get Rear Loaders count before down
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders", null);
        //line below will check if equipment is present in Rear Loaders before down
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "true");
        logTestInfo(wDriver,"Verification started for snow equipment after Down");
        downHour1 = Utilities.get24HourFormat(-1);
        //line below will down a equipment before Snow Update
        equipmentDownActions().downEquipment(equipmentId,downCode1,serviceLocation1,downHour1,downMinute1,reporter1,mechanic1,remarks1,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
        //line below will return down count after down
        CommonActions.getAnyCategoryCardsCountAfter("+1",downCountBefore,"Equipment","Down",null, null);
        //line below will check if equipment is present in Down after down
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null, equipmentId, "true");
        //line below will return available count after down
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableCountBefore,"Equipment","Available",null, null);
        //line below will return Rear Loaders count after down
        CommonActions.getAnyCategoryCardsCountAfter("-1",rearLoadersCountBefore,"Equipment","Available","RearLoaders", null);
        //line below will check if equipment is not present in Rear Loaders after down
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "false");

        //line below will get down count before snow update with working down
        int downCountBeforeWorkingDown = CommonActions.getAnyCategoryCardsCount("Equipment","Down",null, null);
        //line below will get available count before snow update with working down
        int availableCountBeforeWorkingDown = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null, null);
        //line below will get Rear Loaders count before snow update with working down
        int rearLoadersCountBeforeWorkingDown = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders", null);

        snowUpdateHour = Utilities.get24HourFormat(0);
        //line below will perform Snow Update with working down
        equipmentSnowUpdateActions().snowUpdate(equipmentId,plowType,plowDirections,chain,load,"Yes",snowAssignment);
        logTestInfo(wDriver,"Verification started for snow equipment after snow update with working down");
        //line below will verify Snow Update history after snow update with working down
        equipmentHistoryUtilities().getEquipmentSnowUpdateHistory(equipmentId,"1",plowType,plowDirections,chain,"",snowAssignment,exLastUpdated + " " + snowUpdateHour + ":",LoginData.getLoginData("username"));
        //line below will return down count after snow update with working down
        CommonActions.getAnyCategoryCardsCountAfter("-1",downCountBeforeWorkingDown,"Equipment","Down",null, null);
        //line below will check if equipment is not present in Down after snow update with working down
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null, equipmentId, "false");
        //line below will return available count after snow update with working down
        CommonActions.getAnyCategoryCardsCountAfter("+1",availableCountBeforeWorkingDown,"Equipment","Available",null, null);
        //line below will return Rear Loaders count after snow update with working down
        CommonActions.getAnyCategoryCardsCountAfter("+1",rearLoadersCountBeforeWorkingDown,"Equipment","Available","RearLoaders", null);
        //line below will check if equipment is present in Rear Loaders after snow update with working down
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "true");

        //line below will verify equipment card top row backgroun is black for working down
        equipmentColorUtilities().verifyEquipmentCardRowTopColor( equipmentId,"Black",true);

        //line below will get down count before snow update with working down as 'No'
        int downCountBeforeWorkingDownNo = CommonActions.getAnyCategoryCardsCount("Equipment","Down",null, null);
        //line below will get available count before snow update with working down as 'No'
        int availableCountBeforeWorkingDownNo = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null, null);
        //line below will get Rear Loaders count before snow update with working down as 'No'
        int rearLoadersCountBeforeWorkingDownNo = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders", null);

        snowUpdateHour = Utilities.get24HourFormat(0);
        //line below will perform Snow Update with working down as 'No'
        equipmentSnowUpdateActions().snowUpdate(equipmentId,plowType,plowDirections,chain,load,"No",null);
        logTestInfo(wDriver,"Verification started for snow equipment after snow update with working down as 'No'");
        //line below will verify Snow Update history after snow update with working down as 'No'
        equipmentHistoryUtilities().getEquipmentSnowUpdateHistory(equipmentId,"1",plowType,plowDirections,chain,"","Yes",exLastUpdated + " " + snowUpdateHour + ":",LoginData.getLoginData("username"));
        //line below will return down count after snow update with working down as 'No'
        CommonActions.getAnyCategoryCardsCountAfter("+1",downCountBeforeWorkingDownNo,"Equipment","Down",null, null);
        //line below will check if equipment is present in Down after snow update with working down as 'No'
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null, equipmentId, "true");
        //line below will return available count after snow update with working down as 'No'
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableCountBeforeWorkingDownNo,"Equipment","Available",null, null);
        //line below will return Rear Loaders count after snow update with working down as 'No'
        CommonActions.getAnyCategoryCardsCountAfter("-1",rearLoadersCountBeforeWorkingDownNo,"Equipment","Available","RearLoaders", null);
        //line below will check if equipment is not present in Rear Loaders after snow update with working down as 'No'
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "false");

        //line below will verify equipment card top row backgroun is not black for working down as 'No'
        equipmentColorUtilities().verifyEquipmentCardRowTopColor( equipmentId,"Black",false);


    }







}
