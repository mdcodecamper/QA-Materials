package equipmentTestSuites.equipmentDown.Add;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
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
public class TEST003_MechanicalBrooms_ServiceField_FreeText extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String downDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDownDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String downCode1 = "DN87";
    private String serviceLocation1 = "No Service Location";
    private String downHour1 = Utilities.get24HourFormat(-1);
    private String downMinute1 = "00";
    private String mechanic1 = "mechanic001";
    private String reporter1 = "reporter001";
    private String remarks1 = "remarks001";
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Down Service Field")
    public void equipmentDown_MechBrooms_Available_1Code_NoServiceLocation() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "MechanicalBrooms", 1 );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Regression", "Down Equipment Service Field" );
        extentTest.log( LogStatus.INFO, "Scenario 3 - Ref#MINERVA-3763 - Down equipment by entering free text in the 'service location' drop down field (ex.'test 123') and verify that this value appears in the Up/Down History." );
        //line below will open sending location board
        //loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "NonBin", "Available", "MechanicalBrooms");
        logTestInfo( wDriver, "verification started on location " + location + " before down" );
        //line below will return down category count before down
        int downCountBefore = getAnyCategoryCardsCount("Equipment","Down", null, null );
        //line below will return available category count before down
        int availableCountBefore = getAnyCategoryCardsCount("Equipment","Available", null, null );
        //line below will return available mechanical brooms category count before down
        int mechBroomsCountBefore = getAnyCategoryCardsCount("Equipment","Available", "MechanicalBrooms", null );
        //line below will check if equipment is present in mechanical brooms before down
        equipmentPanelUtilities().verifyEquipmentPresent( "Available", "MechanicalBrooms", null, equipmentId, "true" );
        //line below will check if equipment color is as expected in rear loaders category before down
        equipmentColorUtilities().verifyEquipmentCardColor( equipmentId, "Equipment", "yellow" );
        //line below will check if equipment text color is as expected in rear loaders category before down
        equipmentColorUtilities().verifyEquipmentTextColor( equipmentId, "black" );
        //line below will down a equipment
        equipmentDownActions().downEquipment( equipmentId, downCode1, serviceLocation1, downHour1, downMinute1, reporter1, mechanic1, remarks1, null, null, null, null, null, null, null, null, null, null, null, null, null, null );
        logTestInfo( wDriver, "verification started on location " + location + " after down" );
        //line below will return down count after down
        getAnyCategoryCardsCountAfter("+1",downCountBefore,"Equipment","Down",null,null);
        //line below will return available count after down
        getAnyCategoryCardsCountAfter("-1",availableCountBefore,"Equipment","Available",null,null);
        //line below will return rear loaders count after down
        getAnyCategoryCardsCountAfter("-1",mechBroomsCountBefore,"Equipment","Available","MechanicalBrooms",null);
        //line below will check if equipment is not present in mechanical brooms after down
        equipmentPanelUtilities().verifyEquipmentPresent("Available","Mechanical Brooms", null, equipmentId, "false");
        //line below will check if equipment is present in down after down
        equipmentPanelUtilities().verifyEquipmentPresent("Down",null, null, equipmentId, "true");
        //line below will check if equipment color is as expected in down category after down
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","yellow");
        //line below will check if equipment text color is as expected in down category after down
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //Line below will verify up/down history after down for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry(equipmentId,"1",null,"Set Down",exDownDate,downHour1 + ":"  + downMinute1,"DN87-ACCIDENT",serviceLocation1,reporter1,mechanic1,remarks1);


    }



}
