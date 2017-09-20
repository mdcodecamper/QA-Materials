package equipmentTestSuites.equipmentSnowUpdate;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 10/20/2016.
 */
public class TEST008_RearLoaders_Available_PreviousDay extends AbstractStartWebDriver {


    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(-1, "yyyyMMdd");
    private String lastUpdated = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLastUpdated = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String plowType = "Regular Plow";
    private String plowDirections = "Right";
    private String chain = "Not Chained";
    private String load = null;
    private String snowAvailability = null;
    private String snowAssignment = "Yes";
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Snow Update")
    public void equipmentSnowUpdate_RearLoaders_Available_PreviousDay() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","RearLoaders", 1,null,"-1");
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Snow Update" );
        Reporter.log( "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - Perform Snow Update on single bin Rear Loaders equipment on previouw day" );
        System.out.println( "**************************************************************************************" );
        logTestInfo(wDriver, "Scenario 1 - Perform Snow Update on single bin Rear Loaders equipment on previous day" );

        //line below will open ops board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders" );
        System.out.println( "Equipment Id is " + equipmentId );
        logTestInfo(wDriver, "verification started on location " + location + " before Snow Update" );
        wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS );
        //line below will return available category count before snow update
        String availableCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Available",null,null);
        //line below will return available rear loaders category count before snow update
        String rearLoadersCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Available","RearLoaders",null);
        //line below will check if equipment is present in rear loaders category before snow update
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null,equipmentId, "true");
        //line below will check if equipment color is as expected in rear loaders category before snow update
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId,"Equipment", "navyBlue");
        //line below will check if equipment text color is as expected in rear loaders category before snow update
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present before snow update
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will get Snow Update Hour
        String updteHour = Utilities.get24HourFormat(0);
        //line below will perform Snow Update
        equipmentSnowUpdateActions().snowUpdate(equipmentId,plowType,plowDirections,chain,load,snowAvailability,snowAssignment);
        logTestInfo(wDriver, "verification started on location " + location + " after Snow Update" );
       // wDriver.manage().timeouts().implicitlyWait( 3, TimeUnit.SECONDS );
        //line below will return available count after snow update
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(0,availableCountBefore,"Available",null,null);
        //line below will return rear loaders count after snow update
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(0,rearLoadersCountBefore,"Available","RearLoaders",null);
        //line below will check if equipment is present in rear loaders category after snow update
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "true");
        //line below will check if equipment color is as expected in rear loaders category after snow update
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId,"Equipment", "orange");
        //line below will check if equipment text color is as expected in rear loaders category after snow update
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after snow update
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will check for equipment snow plow type indicator on equipment card after snow update
        equipmentPanelUtilities().getEquipmentSnowIndicator(equipmentId,"R",null);
        //line below will verify Snow Update history after Snow Update
        equipmentHistoryUtilities().getEquipmentSnowUpdateHistory(equipmentId,"1",plowType,plowDirections,chain,"",snowAssignment,exLastUpdated + " " + updteHour + ":",LoginData.getLoginData("username"));

    }
}
