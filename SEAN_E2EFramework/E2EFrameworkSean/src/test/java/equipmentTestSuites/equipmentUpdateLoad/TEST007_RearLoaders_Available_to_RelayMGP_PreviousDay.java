package equipmentTestSuites.equipmentUpdateLoad;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.CommonActions;
import common.actions.EquipmentActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 1/17/2017.
 */
public class TEST007_RearLoaders_Available_to_RelayMGP_PreviousDay extends AbstractStartWebDriver {


    private String location = EquipmentData.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(-1, "yyyyMMdd");
    private String loadDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLoadDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String loadHour = Utilities.get24HourFormat(0);
    private String newStatus1 = "Relay";
    private String material1 = "33";
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Update Load")
    public void equipmentUpdateLoad_RearLoaders_RelayMGP_PreviousDay() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","RearLoaders", 1,null,"-1");
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Update Load" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 1 - Update Load for Empty single bin to Relay Recycling MGP Category" );
        System.out.println( "**************************************************************************************" );
        logTestInfo(wDriver, "Scenario 1 - Update Load for Empty single bin to Relay Recycling MGP Category" );

        //line below will open ops board
        //loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders" );
        System.out.println("Equipment Id is " + equipmentId);
        extentTest.log( LogStatus.INFO,"verification started on location " + location + " before Update Load");
        //line below will return available category count before update Load
        int availableCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        //line below will return relay category count before Update Load
        int relayCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay",null,null);
        //line below will return available rear loaders category count before Update Load
        int rearLoadersCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);
        //line below will return Recycling category count before Update Load
        int recyclingCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay","RECYCLING",null);
        //line below will return MGP category count before Update Load
        int MGPCountBefore = CommonActions.getAnyCategoryCardsCount("Equipment","Relay","RECYCLING","MGP");
        //line below will check if equipment is present in rear loaders category before Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null,equipmentId, "true");
        //line below will check if equipment color is as expected in rear loaders category before update load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment","navyBlue");
        //line below will check if equipment text color is as expected in rear loaders category before update load
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present before update load
        equipmentPanelUtilities().equipmentBinType(equipmentId,"E",null);
        //line below will verify Update Load Status history before Update Load
        equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId,"1","Load","EMPTY","","SKIP","","Click On Equipment");
        loadHour = Utilities.get24HourFormat(0);
        //line below will verify user able to right click on update load and modal winodw appears
        EquipmentActions.verifyModalWindowRightClick(wDriver,equipmentId,"Update Load");
        //line below will Update Load a equipment
        equipmentUpdateLoadActions().updateLoad(equipmentId,newStatus1,material1,null,null);
        extentTest.log(LogStatus.INFO,"verification started on location " + location + " after Update Load");
        //line below will return available count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableCountBefore,"Equipment","Available",null,null);
        //line below will return rear loaders count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("-1",rearLoadersCountBefore,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is not present in rear loaders category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Available","RearLoaders", null, equipmentId, "false");
        //line below will return relay count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("+1",relayCountBefore,"Equipment","Relay",null,null);
        //line below will return relay recycling category count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("+1",recyclingCountBefore,"Equipment","Relay","RECYCLING",null);
        //line below will return relay recycling MGP category count after Update Load
        CommonActions.getAnyCategoryCardsCountAfter("+1",MGPCountBefore,"Equipment","Relay","RECYCLING","MGP");
        //line below will check if equipment is present in relay recycling organics category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","RECYCLING", "MGP", equipmentId, "true");
        //line below will check if equipment color is as expected in collection category after update load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Equipment", "navyBlue");
        //line below will check if equipment text color is as expected collection category after update load
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId,"black");
        //line below will check equipment rear loader bin type is present after update load
        equipmentPanelUtilities().equipmentBinType(equipmentId,"33",null);
        //line below will verify Update Load Status history after Update Load
        equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId,"1","Load","RELAY","33-METAL/GLASS/PLASTIC",exLoadDate + " " + loadHour + ":",LoginData.getLoginData("username"),"Click On Equipment");

        //line below will open current day board
        loginPage().goToBoardLocationByDate(url, location + "/", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));

        //line below will check if equipment is present in relay recycling organics category after Update Load on current board
        equipmentPanelUtilities().verifyEquipmentPresent("Relay","RECYCLING", "MGP", equipmentId, "true");

        //line below will verify Update Load Status history after Update Load
        equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId,"1","Load","RELAY","33-METAL/GLASS/PLASTIC",exLoadDate + " " + loadHour + ":",LoginData.getLoginData("username"),"Click On Equipment");


    }
















}
