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
import static equipment.abstractBase.EquipmentBasePage.equipmentColorUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentDownActions;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/27/2016.
 */
public class TEST003_Equipment_FrontEndLoader extends AbstractStartWebDriver {


    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String lastUpdated = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLastUpdated = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String plowType = "Regular Plow";
    private String plowDirections = "Right";
    private String chain = "Not Chained";
    private String load = null;
    private String snowAvailability = null;
    private String snowAssignment = "Yes";
    private String url = LoginData.getLoginData(getUrl());
    private String downCode1 = "DN87";
    private String serviceLocation1 = "MN02";
    private String downHour1 = Utilities.get24HourFormat(-1);
    private String downMinute1 = "00";
    private String mechanic1 = "mechanic001";
    private String reporter1 = "reporter001";
    private String remarks1 = "remarks001";


    @Test(description = "Equipment Snow Update for Front End Loader")
    public void equipmentSnowUpdate_Available_FrontEndLoader() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "Snow", 1,"FrontEndLoader" );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Smoke", "Snow Update" );
        Reporter.log( "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true );
       // System.out.println( "**************************************************************************************" );
        //System.out.println( "Scenario 1 - Perform Snow Update snow equpment for Front End Loader" );
        //System.out.println( "**************************************************************************************" );
        logTestInfo( wDriver, "Senario 3 - Ref#MINERVA-3780 - Perform Snow Update on Snow Equipment for Front End Loader" );

        //line below will open ops board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from snow FED category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("NonBin", "Available", "Snow","Down","FrontEndLoader");

        //line below will verify snow updatd modal window functionalities for FED equipment
        equipmentPanelUtilities().verifyEquipmentModalWindow(equipmentId,"Snow Update","Disabled","Disabled","Disabled","Disabled","Yes");

        //line below will return available category count before down
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        //line below will return available snow category count before down
        int availableSnowCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available","Snow",null);
        //line below will return available snow FED category count before down
        int availableSnowFEDCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available","Snow","FrontEndLoader");
        //line below will check if equipment is present in Snow FED category before down
        equipmentPanelUtilities().verifyEquipmentPresent("Available","Snow", "FrontEndLoader",equipmentId, "true");
        //line below will check if equipment color is as expected in snow category before fown
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId,"Equipment", "orange");
        //line below will check if equipment text color is as expected in rear loaders category before snow update
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will get equipment down count before down
        int downCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Down",null,null);

        downHour1 = Utilities.get24HourFormat(-1);
        //line below will down a equipment
        equipmentDownActions().downEquipment(equipmentId,downCode1,serviceLocation1,downHour1,downMinute1,reporter1,mechanic1,remarks1,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

        //line below will verify snow updatd modal window functionalities for FED equipment after down
        equipmentPanelUtilities().verifyEquipmentModalWindow(equipmentId,"Snow Update","Disabled","Disabled","Disabled","Yes","Yes");

        //line below will verify snow equipment down count after down
        CommonActions.getAnyCategoryCardsCountAfter("+1",downCountBefore,"Equipment","Down",null,null);
        //line below will check if equipment is present in down category after down
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null,equipmentId, "true");
        //line below will verify snow equipment available count after down
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableCountBefore,"Equipment","Available",null,null);
        //line below will verify snow equipment snow count after down
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableSnowCountBefore,"Equipment","Available","Snow",null);
        //line below will verify snow equipment FED count after down
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableSnowFEDCountBefore,"Equipment","Available","Snow","FrontEndLoader");
        //line below will verify equipment not present in Snow FED category
        equipmentPanelUtilities().verifyEquipmentPresent("Available","Snow", "FrontEndLoader",equipmentId, "false");

    }


}
