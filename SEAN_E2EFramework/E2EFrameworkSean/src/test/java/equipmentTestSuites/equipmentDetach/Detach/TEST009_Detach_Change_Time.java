package equipmentTestSuites.equipmentDetach.Detach;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 12/29/2016.
 */
public class TEST009_Detach_Change_Time extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation;
    private String detachTo = EquipmentData.equipmentReceivingLocation;
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String detachDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exDetachDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String detachHour = null;
    private String detachHour2 = null;
    private String detachMinute = "00";
    private String driver = "driver001";
    private String url = LoginData.getLoginData(getUrl());
    private String receiver = "receiver001";
    private String remarks = "remarks001";
    private String detachHourCancel = null;
    ///// This is a test comment


    @Test(description = "Equipment Detach - Past time")
    public void equipment_Detach_Past_Time() throws InterruptedException, IOException {

        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Initiate Detach using Past time and verify History");
        System.out.println("**************************************************************************************");

        extentTest.assignCategory( "regression", "Initiate Detach using Past time" );
        logTestInfo( wDriver, "Scenario 1- Initiate Detach using Past time and verify History" );
        setEquipmentLocationForTest("Available","RearLoaders",1);
        location = EquipmentData.equipmentSendingLocation;
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin","Available","RearLoaders" );
        detachHour = Utilities.get24HourFormat(-2);

        int availableCountBeforeDetach = CommonActions.getAnyCategoryCardsCount("Equipment","Available",null,null);
        int pendingDetachCountBeforeDetach = CommonActions.getAnyCategoryCardsCount("Equipment","Pending","PendingDetach",null);
        int rearLoadersCountBeforeDetach = CommonActions.getAnyCategoryCardsCount("Equipment","Available","RearLoaders",null);

        //line below will detach a equipment
        equipmentDetachActions().detachEquipment(equipmentId, detachTo, detachHour, detachMinute, driver);
        Thread.sleep(2000);
        logTestInfo(wDriver, "verification started on location  " + location + " after detach for " + equipmentId);
        //line below will return pending detach count after detach
        CommonActions.getAnyCategoryCardsCountAfter("+1",pendingDetachCountBeforeDetach,"Equipment","Pending","PendingDetach",null);
        //line below will check if equipment is present in pending detach before accept detach
        equipmentPanelUtilities().verifyEquipmentPresent("Pending", "PendingDetach", null, equipmentId, "true");
        //line below will return available count after detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",availableCountBeforeDetach,"Equipment","Available",null,null);
        //line below will return rear loaders count after detach
        CommonActions.getAnyCategoryCardsCountAfter("-1",rearLoadersCountBeforeDetach,"Equipment","Available","RearLoaders",null);
        //line below will check if equipment is  not present in rear loaders before accept detach
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "false");

        //line below will verify equipment detachment history after detach
        equipmentHistoryUtilities().getEquipmentDetachHistory(equipmentId, "1", "Initiated Detach", exDetachDate + " " + detachHour + ":" + detachMinute, EquipmentData.verifyLocationIsDualGarage(location), detachTo, LoginData.getLoginData("username"), driver, "");

    }

    @Test(description = "Equipment Detach - Future time (Negative scenario)", dependsOnMethods = "equipment_Detach_Past_Time")
    public void equipment_Detach_Future_Time() throws InterruptedException, IOException {

        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        System.out.println("**************************************************************************************");
        System.out.println("Scenario 2 - Initiate Detach using Future time and verify Error message");
        System.out.println("**************************************************************************************");

        extentTest.assignCategory("regression", "Initiate Detach using Future time");
        logTestInfo(wDriver, "Scenario 2 - Initiate Detach using Future time and verify Error message");
        setEquipmentLocationForTest("Available", "RearLoaders", 1);
        location = EquipmentData.equipmentSendingLocation;
        //line below will open sending location board
        loginPage().goToBoardLocationByDate(url, (location + "/").toString(), boardDate);

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon(wDriver, "Task");
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders");
        detachHour = Utilities.get24HourFormat(+2);

        //line below will detach a equipment
        equipmentDetachActions().detachEquipment_Negative(equipmentId, detachTo, detachHour, detachMinute, driver);
        Thread.sleep(2000);
        logTestInfo(wDriver, "verification started on location  " + location + " after using future time " + detachHour);
        String expectedMessage = "Detach Time cannot be later than current time.";
        try {
            Assert.assertTrue(wDriver.findElement(By.xpath("//*[contains(text(), expectedMessage)]")).isDisplayed(), "Expected Error Message is not displayed");
            if (wDriver.findElement(By.xpath("//*[contains(text(), expectedMessage)]")).isDisplayed())
                logTestPass(wDriver, "Correct Error message is displayed - " + expectedMessage);
            else {
                wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                logTestFailure(wDriver, "Error Message is not displayed for invalid input");
                Assert.fail();
            }

        } catch (Exception e) {

            logTestFailure(wDriver,  e.getMessage());
        }
    }



}





