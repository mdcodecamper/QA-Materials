package equipmentTestSuites.equipmentUpdateLoad;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.getAnyCategoryCardsCountAfter;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by ccollapally on 10/21/2016.
 */
public class TEST002_DualBins_Available_to_RelayCollection extends AbstractStartWebDriver{

    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String loadDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLoadDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String loadHour = Utilities.get24HourFormat(0);
    private String newStatus1 = "Relay";
    private String material1 = "01";
    private String newStatus2 = "Relay";
    private String material2 = "01";
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Update Load")
    public void equipmentUpdateLoad_DualBins_RelayColl() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","DualBins", 1);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Update Load" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - Update Load for dual bin's to Relay Collection Category" );
        System.out.println( "**************************************************************************************" );
        logTestInfo(wDriver, "Scenario 1 - Update Load for dual bin's to Relay Collection Category" );

        //line below will open ops board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available", "DualBins" );
        System.out.println("Equipment Id is " + equipmentId);
        logTestInfo(wDriver,"verification started on location " + location + " before Update Load");
        //line below will return available category count before update Load
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        //line below will return relay category count before Update Load
        int relayCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay",null,null);
        //line below will return available Dual BIn's category count before Update Load
        int dualBinsCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available","DualBins",null);
        //line below will return Collection category count before Update Load
        int collectionCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay","COLLECTION",null);
        //line below will check if equipment is present in Dual Bins category before Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Available","DualBins", null,equipmentId, "true");
        //line below will check if equipment color is as expected in dual bin category before update load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","limeGreen");
        //line below will check if equipment text color is as expected in dual bin category before update load
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment dual bin type is present before update load
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E","E");
        //line below will verify Update Load Status history before Update Load
        //equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId,"1","Load","EMPTY","","EMPTY","");
        loadHour = Utilities.get24HourFormat(0);
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,newStatus1,material1,newStatus2,material2);
        logTestInfo(wDriver,"verification started on location " + location + " after Update Load");
        //line below will return available count after Update Load
        getAnyCategoryCardsCountAfter("-1",availableCountBefore,"Equipment","Available",null,null);
        //line below will return dual bins count after Update Load
        getAnyCategoryCardsCountAfter("-1",dualBinsCountBefore,"Equipment","Available","DualBins",null);
        //line below will check if equipment is not present in dual bins category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Available","DualBins", null, equipmentId, "false");
        //line below will return relay count after Update Load
        getAnyCategoryCardsCountAfter("+1",relayCountBefore,"Equipment","Relay",null,null);
        //line below will return relay collection category count after Update Load
        getAnyCategoryCardsCountAfter("+1",collectionCountBefore,"Equipment","Relay","COLLECTION",null);
        //line below will check if equipment is present in relay collection category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","COLLECTION",null, equipmentId, "true");
        //line below will check if equipment color is as expected in collection category after update load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","limeGreen");
        //line below will check if equipment text color is as expected collection category after update load
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment dual bin type is present after update load
        equipmentPanelUtilities().equipmentBinType(equipmentId,"01","01");
        //line below will get both bins material colors
        equipmentColorUtilities().equipmentBinColor(equipmentId,"white","white");
        //line below will verify Update Load Status history after Update Load
        equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId,"1","LARGE","RELAY","01-H/H COLLECTION (CURBSIDE)",exLoadDate + " " + loadHour + ":",LoginData.getLoginData("username"),"Click On Equipment");
        equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId,"2","SMALL","RELAY","01-H/H COLLECTION (CURBSIDE)",exLoadDate + " " + loadHour + ":",LoginData.getLoginData("username"),"Click On Equipment");
    }

}

