package equipmentTestSuites.equipmentGrouping;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import equipmentTestSuites.testData.EquipmentData;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by EBronfman 02/2017
 */


public class TEST002_Equipment_Groups_Counts_Verification1 extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation;

    @Test(description = "Dual bins count verification")
    public void dualBins_count_verification() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","DualBins",1);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Regression", "Dual bins count verification");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 2 - Verifying  Dual bins group count display");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 2 - Verifying  Dual bins group count display");

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //code below will check expand/collapse functionality for Mechanical Brooms
        try {
            logTestInfo(wDriver, "verification started on verifying Dual bins count");
            //get expected count
            int dualBbinsCountExpected = CommonActions.getAnyCategoryCardsCount("Equipment","Available","DualBins",null);
            verify_Group_counts_Display("auDualBins", dualBbinsCountExpected, "Dual Bins");
            Thread.sleep(3000);
        }
        catch (Exception e) {
            logTestInfo(wDriver, "Problem finding elements " + e.getMessage());
        }
    }

    @Test(description = "Mechanical Brooms count verification")
    public void mechanicalBrooms_count_verification() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available", "MechanicalBrooms", 2);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Regression", "Mechanical Brooms count verification");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 2 - Verifying  Mechanical Brooms group count display");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 2 - Verifying  Mechanical Brooms group count display");

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon(wDriver, "Task");
        //code below will check expand/collapse functionality for Miscellaneous section
        try {
            logTestInfo(wDriver, "verification started on verifying Mechanical Brooms count");
            int mechanicalBroomsCountExpected = CommonActions.getAnyCategoryCardsCount("Equipment", "Available", "MechanicalBrooms", null);
            //get expected count
            verify_Group_counts_Display("auMechanicalBrooms" ,mechanicalBroomsCountExpected, "Mechanical Brooms");
            Thread.sleep(3000);
        } catch (Exception e) {
            logTestInfo(wDriver, "Problem finding elements " + e.getMessage());
        }
    }
    public void verify_Group_counts_Display(String groupClass, int expectedCount, String groupName) {
        Boolean countDisplayed = wDriver.findElement(By.xpath("//div[contains(@class, '" + groupClass + "')]//span[@class='group-count']")).isDisplayed();
        Assert.assertTrue(countDisplayed);
        if (countDisplayed)
            logTestPass(wDriver, groupName + " count is displayed as expected: " + expectedCount);
        else
            logTestFailure(wDriver, groupName + " count is not displayed as expected");
    }
}
