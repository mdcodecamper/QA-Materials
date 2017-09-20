package common.pages;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static common.actions.BoardActions.clickOnActivityTab;
import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;


public class RecentActivities extends AbstractStartWebDriver {

    public static String personFullName;

    public RecentActivities(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }
    //***************************************************************************************

    public static void recentActivityValidator(String... args) {
        try {
            String recentActivityLog = getRecActivityContent(0);
            for (String arg : args) {
                logTestInfo(wDriver, "Validation Recent Activity Log Contains: " + arg);
                Assert.assertTrue(recentActivityLog.contains(arg), "Asserting: " + arg);
            }
        } catch (Exception e) {
            logTestFailure(wDriver, "Recent Activity Log Validation Failed!!!");
        }
    }

    public static String getRecActivityContent(int rowNum) throws InterruptedException {
        clickOnActivityTab();
        //getScreenShot(wDriver); ;
        WebElement recentWindow = waitForElementThenDo(wDriver, wDriver.findElement(By.tagName("recent-activity")), 5);
        String ActivityContent = waitForElementThenDo(wDriver, recentWindow.findElements(By.cssSelector(".ra-item")).get(rowNum), 5).getText();
        //Thread.sleep(3000);
        logTestInfo(wDriver, "Most Recent Activity Log Content Found is: ");
        logTestInfo(wDriver, ActivityContent);
        clickOnActivityTab();
        return ActivityContent;
    }

    public static int getRecActivityRowCount() throws InterruptedException {
        clickOnActivityTab();
        WebElement recentWindow = waitForElementThenDo(wDriver, wDriver.findElement(By.tagName("recent-activity")), 5);
        int activityRows = recentWindow.findElements(By.cssSelector(".ra-item")).size();
        Thread.sleep(3000);
        //Reporter.log("Row Count Found: " + activityRows, true);
        clickOnActivityTab();
        return activityRows;
    }



}
