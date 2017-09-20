package equipmentTestSuites.equipmentUp;


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
 * Created by skashem on 10/4/2016.
 */

//@Listeners(TestNGTestListener.class)
public class TEST001_RearLoaders extends AbstractStartWebDriver {

//    public TEST001_Equipment_Detach(WebDriver wDriver) {
//        super();
//        PageFactory.initElements(wDriver, this);
//
//    }

    /***************************************************************
     *Scenario 1 - Detach A Single Bin Equipment From Available Group
     ****************************************************************/
    //private WebDriver wdriver;
    //Test Data
    // private String url = LoginCredentials.url;
    //private String equipmentId = "25CW-857";
    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String upDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exUpDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String upHour = Utilities.get24HourFormat(0);
    private String upMinute = "00";
    private String mechanic = "mechanic001";
    private String reporter = "reporter001";
    private String url = LoginData.getLoginData(getUrl());
    private String downCountBefore;
    private String rearLoadersCountBefore;
    private String availableCountBefore;


    @Test(description = "Equipment Up")
    public void equipmentUp_RearLoaders() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","RearLoaders", 4);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Up Equipment" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - Up an equipment from down category" );
        System.out.println( "**************************************************************************************" );
        logTestInfo(wDriver, "Scenario 1 - Up an equipment from down category" );

        //line below will open sending location board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Available", "RearLoaders", "Down" );
        System.out.println( "Equipment Id is " + equipmentId );
        //line below will down an equipment
        equipmentDownActions().downEquipment( equipmentId, "DN87", "MN01", Utilities.get24HourFormat( -1 ), "00", "reporter01", "mechanic01", "remarks01", "DN39", "MN01", Utilities.get24HourFormat( -1 ) , "00", "reporter02", "mechanic02", "remarks02", null, null, null, null, null, null, null );
        //Line below will verify up/down history before up for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", "1", "Set Down", exUpDate, Utilities.get24HourFormat( -1 )  + ":" + "00", "DN87-ACCIDENT", "MN01", "reporter01", "mechanic01", "remarks01" );
        //Line below will verify up/down history before up for down code 2
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", "2", "Set Down", exUpDate, Utilities.get24HourFormat( -1 )  + ":" + "00", "DN39-AIR LEAK", "MN01", "reporter02", "mechanic02", "remarks02" );
        logTestInfo(wDriver, "verification started on location " + location + " before up" );
        //line below will return down category count before up
        downCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Down", null, null );
        //line below will return available category count before up
        availableCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Available", null, null );
        //line below will return available rear loaders category count before up
        rearLoadersCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Available", "RearLoaders", null );
        //line below will check if equipment is present in down before up
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "true" );
        //line below will check if equipment color is as expected in down category before up
        equipmentColorUtilities().verifyEquipmentCardColor( equipmentId, "Equipment","navyBlue" );
        //line below will check if equipment text color is as expected in down category before up
        equipmentColorUtilities().verifyEquipmentTextColor( equipmentId, "black" );
        //line below will check equipment rear loader bin type is present in down category before up
        equipmentPanelUtilities().equipmentBinType( equipmentId, "E", null );
        //line below will get up hour before up action
        upHour = Utilities.get24HourFormat(0);
        //line below will up a equipment
        equipmentUpActions().upEquipment( equipmentId, mechanic, reporter, upHour, upMinute );
        logTestInfo(wDriver, "verification started on location " + location + " after up" );
        //line below will return down count after up
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(-1,downCountBefore, "Down", null, null );
        //line below will return available count after up
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(+1,availableCountBefore, "Available", null, null );
        //line below will return rear loaders count after up
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(+1,rearLoadersCountBefore, "Available", "RearLoaders", null );
        //line below will check if equipment is not present in down after up
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "false" );
        //line below will check if equipment is present in rear loaders after up
        equipmentPanelUtilities().verifyEquipmentPresent( "Available", "RearLoaders", null, equipmentId, "true" );
        //line below will check if equipment color is as expected in down category after up
        equipmentColorUtilities().verifyEquipmentCardColor( equipmentId, "Equipment","navyBlue" );
        //line below will check if equipment text color is as expected in down category after up
        equipmentColorUtilities().verifyEquipmentTextColor( equipmentId, "black" );
        //line below will check equipment rear loader bin type is present in down category after up
        equipmentPanelUtilities().equipmentBinType( equipmentId, "E", null );
        //line below will return up history expected result after up
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", null, "Set Up", exUpDate, upHour + ":" + upMinute, "", "", reporter, mechanic, "" );



    }

    }



