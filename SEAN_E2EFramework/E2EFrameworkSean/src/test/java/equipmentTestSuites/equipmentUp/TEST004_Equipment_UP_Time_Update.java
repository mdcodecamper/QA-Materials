package equipmentTestSuites.equipmentUp;


import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.openqa.selenium.By;
import org.testng.Assert;
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
 * Created by skashem on 10/4/2016.
 */

//@Listeners(TestNGTestListener.class)
public class TEST004_Equipment_UP_Time_Update extends AbstractStartWebDriver {

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
    private String downHourPast = Utilities.get24HourFormat(-2);
    private String downHourFuture = Utilities.get24HourFormat(+1);
    private String upHourPast = Utilities.get24HourFormat(-1);
    private String upHourFuture = Utilities.get24HourFormat(+2);
    private String upHourLessDown = Utilities.get24HourFormat(-3);
    private String upMinute = "00";
    private String mechanic1 = "mechanic001";
    private String reporter1 = "reporter001";
    private String remarks1 = "remarks001";
    private String mechanic2 = "mechanic002";
    private String reporter2 = "reporter002";
    private String remarks2 = "remarks002";
    private String downCode1 = "DN39";
    private String downCode2 = "DN87";
    private String downCode1_str = "DN39-AIR LEAK";
    private String downCode2_str = "DN87-ACCIDENT";

    private String downLocation = "MN01";
    private String url = LoginData.getLoginData(getUrl());
    private String downCountBefore;
    private String equipmentCountBefore;
    private String availableCountBefore;


    @Test(description = "Equipment Up - update to Past Time, verify Error", priority = 1)
    public void equipmentUp_Past_Time() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available", "RearLoaders", 1);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Regression", "Equipment UP - Time Update to past");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Equipment UP - Time Update to past");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - Equipment UP - Time Update to past");
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
        equipmentDownActions().downEquipment( equipmentId, downCode1, downLocation, downHourPast, "00", reporter1, mechanic1, remarks1,
                                                           downCode2,downLocation, downHourPast, "00", reporter2, mechanic2, remarks2, null, null, null, null, null, null, null );
        Thread.sleep(2000);

        logTestInfo(wDriver, "verification started on location " + location + " for equipment " + equipmentId + " before up" );

       //Line below will verify up/down history before up for down code 1
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", "1", "Set Down", exUpDate, downHourPast  + ":" + "00", downCode1_str, downLocation, reporter1, mechanic1, remarks1 );
        //Line below will verify up/down history before up for down code 2
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", "2", "Set Down", exUpDate, downHourPast  + ":" + "00", downCode2_str, downLocation, reporter2, mechanic2, remarks2 );
        //line below will return down category count before up
        downCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Down", null, null );
        //line below will return available category count before up
        availableCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Available", null, null );
        //line below will return available rear loaders category count before up
        equipmentCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Available", "RearLoaders", null );
        //line below will check if equipment is present in down before up
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "true" );
        //line below will up a equipment
        equipmentUpActions().upEquipment( equipmentId, mechanic1, reporter1, upHourPast, "00" );
        logTestInfo(wDriver, "verification started on location " + location + " after up" );
        //line below will return down count after up
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(-1,downCountBefore, "Down", null, null );
        //line below will return available count after up
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(+1,availableCountBefore, "Available", null, null );
        //line below will return rear loaders count after up
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(+1,equipmentCountBefore, "Available", "RearLoaders", null );
        //line below will check if equipment is not present in down after up
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "false" );
        //line below will check if equipment is present in rear loaders after up
        equipmentPanelUtilities().verifyEquipmentPresent( "Available", "RearLoaders", null, equipmentId, "true" );
        //line below will return up history expected result after up
        equipmentHistoryUtilities().getEquipmentUpDownHisotry( equipmentId, "1", null, "Set Up", exUpDate, upHourPast + ":" + "00", "", "", reporter1, mechanic1, "" );

    }

    @Test(description = "Equipment Up - update to Future Time (Negative)", priority = 2)
    public void equipmentUp_Future_Time() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","DualBins", 1);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Regression", "Equipment UP - Time Update to future and verify Error" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 2 - Equipment UP - Time Update to future and verify Error" );
        System.out.println( "**************************************************************************************" );
        logTestInfo(wDriver, "Scenario 2 - Equipment UP - Time Update to future and verify Error" );


        //line below will open sending location board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);
        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon(wDriver, "Task");
        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "DualBins", "Down");
        System.out.println("Equipment Id is " + equipmentId);
        //line below will down an equipment
        equipmentDownActions().downEquipment_Negative(equipmentId, downCode1, downLocation, upHourFuture, "00", reporter1, mechanic1, remarks1,
                downCode2, downLocation, upHourFuture, "00", reporter2, mechanic2, remarks2, null, null, null, null, null, null, null);
        logTestInfo(wDriver, "verification started on location  " + location + " after using future time " + upHourFuture);
        String expectedMessage = "Down Time for Code 1 cannot be later than current time.";
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

            logTestFailure(wDriver, e.getMessage());
        }
        waitForElementThenDo(wDriver, wDriver.findElement(By.cssSelector("button.btn.btn-secondary.auCancel"))).click();
        Thread.sleep(2000);
        //line below will check if equipment is not present in down
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "false" );

        wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        //line below will down an equipment with past time
        equipmentDownActions().downEquipment(equipmentId, downCode1, downLocation, downHourPast, "00", reporter1, mechanic1, remarks1,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        logTestInfo(wDriver, "verification started on location " + location + " for equipment " + equipmentId + " before up" );
        //line below will return down category count before up
        downCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Down", null, null );
        //line below will return available category count before up
        availableCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Available", null, null );
        //line below will return available rear loaders category count before up
        equipmentCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount( "Available", "DualBins", null );
        //line below will check if equipment is present in down before up
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "true" );
        //line below will up a equipment with future time
        equipmentUpActions().upEquipment_Negative( equipmentId, mechanic1, reporter1, upHourFuture, upMinute );
        logTestInfo(wDriver, "verification started on location  " + location + " after using future Up time " + upHourFuture);
        String expectedUpMessage = "Up Time cannot be later than current time";
        try {
            Assert.assertTrue(wDriver.findElement(By.xpath("//*[contains(text(), expectedUpMessage)]")).isDisplayed(), "Expected Error Message is not displayed");
            if (wDriver.findElement(By.xpath("//*[contains(text(), expectedUpMessage)]")).isDisplayed())
                logTestPass(wDriver, "Correct Error message is displayed - " + expectedUpMessage);
            else {
                wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                logTestFailure(wDriver, "Error Message is not displayed for invalid input");
                Assert.fail();
            }

        } catch (Exception e) {

            logTestFailure(wDriver, e.getMessage());
        }
        waitForElementThenDo(wDriver, wDriver.findElement(By.cssSelector("button.btn.btn-secondary.auCancel"))).click();
        Thread.sleep(2000);
        //line below will check if equipment is present in down
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "true" );
        //line below will up a equipment with Up time less than Down time
        equipmentUpActions().upEquipment_Negative( equipmentId, mechanic1, reporter1, upHourLessDown, upMinute );
        logTestInfo(wDriver, "verification started on location  " + location + " after using Up time less than Down time " + upHourLessDown);
        String expectedUpMessage2 = "Cannot Up equipment. Please select Up time later than original down time.";
        try {
            Assert.assertTrue(wDriver.findElement(By.xpath("//*[contains(text(), expectedUpMessage2)]")).isDisplayed(), "Expected Error Message is not displayed");
            if (wDriver.findElement(By.xpath("//*[contains(text(), expectedUpMessage2)]")).isDisplayed())
                logTestPass(wDriver, "Correct Error message is displayed - " + expectedUpMessage2);
            else {
                wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                logTestFailure(wDriver, "Error Message is not displayed for invalid input");
                Assert.fail();
            }

        } catch (Exception e) {

            logTestFailure(wDriver, e.getMessage());
        }
        waitForElementThenDo(wDriver, wDriver.findElement(By.cssSelector("button.btn.btn-secondary.auCancel"))).click();
        Thread.sleep(2000);
        //line below will check if equipment is present in down
        equipmentPanelUtilities().verifyEquipmentPresent( "Down", null, null, equipmentId, "true" );
        //line below will return dual bins count after Cancel invalid Up action
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(0,equipmentCountBefore, "Available", "DualBins", null );
    }

}



