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
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentSnowUpdateActions;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/29/2016.
 */
public class TEST005_Snow_History_Pagination extends AbstractStartWebDriver {


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


    @Test(description = "Equipment Snow Update History")
    public void equipmentSnowUpdate_History_Pagination() throws InterruptedException, IOException {
        setEquipmentLocationForTest( "Available", "RearLoaders", 1 );
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo( wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName() );

        extentTest.assignCategory( "Smoke", "Snow Update History" );
        Reporter.log( "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true );
        logTestInfo( wDriver, "Scenario 5 - Perform Snow Update History for 5 records per page" );

        //line below will open ops board
        //loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Available", "RearLoaders" );
        System.out.println( "Equipment Id is " + equipmentId );

        String updteHour = Utilities.get24HourFormat(0);
        //line below will perform Snow Update Action
        equipmentSnowUpdateActions().snowUpdate(equipmentId,plowType,plowDirections,chain,load,snowAvailability,snowAssignment);
        //line below will perform Snow Update Action
        equipmentSnowUpdateActions().snowUpdate(equipmentId,"Mini V Plow",null,"Chained",null,null,"No");
        //line below will perform Snow Update Action
        equipmentSnowUpdateActions().snowUpdate(equipmentId,"Large V Plow",null,"Not Chained",null,null,"Yes");
        //line below will perform Snow Update Action
        equipmentSnowUpdateActions().snowUpdate(equipmentId,"None",null,"Not Chained",null,null,null);
        //line below will perform Snow Update Action
        equipmentSnowUpdateActions().snowUpdate(equipmentId,plowType,"Left",chain,load,snowAvailability,"Yes");

        //line below will verify Snow history pagination
        CommonActions.paginationVerification("Equipment",equipmentId,"Snow");

    }





}
