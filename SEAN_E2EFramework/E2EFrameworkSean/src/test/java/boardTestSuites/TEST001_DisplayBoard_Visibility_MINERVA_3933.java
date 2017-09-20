package boardTestSuites;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.ArrayList;

import static common.actions.BoardActions.getDisplayBoardPage;
import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;
import static common.pages.DisplayBoardPage.getDispBoardSectionCardCount;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;

/**
 * Created by sdas on 12/3/2016.
 */
public class TEST001_DisplayBoard_Visibility_MINERVA_3933 extends AbstractStartWebDriver {
    private String url = LoginData.getLoginData(getUrl());
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");


    @Test(priority = 1, groups = {"Smoke", "Regression"})
    public void displayBoardTest_Staten_Island() throws InterruptedException, IOException {
        String location = "SI03";
        logTestInfo(wDriver, "Test Name:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " LOCATION:" + location);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        Reporter.log("Current URL: " + wDriver.getCurrentUrl(), true);
        int unavailCountOps = getAnyCategoryHeaderCount("Unavail");
        int detachCountOps = getAnyCategoryHeaderCount("Detach");
        logTestInfo(wDriver, "In OpsBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);
        getDisplayBoardPage();
        Thread.sleep(3000);
        ArrayList allTabs = new ArrayList<String>(wDriver.getWindowHandles());
        wDriver.switchTo().window(allTabs.get(allTabs.size() - 1).toString());
        waitForPageLoad(30);
        int unavailCountDisp = getDispBoardSectionCardCount("Unavail");
        int detachCountDisp = getDispBoardSectionCardCount("Detach");
        logTestInfo(wDriver, "In DisplayBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);
        try {
            logTestInfo(wDriver, "Now Validatiing Available and Detached Counts");
            Assert.assertEquals(unavailCountOps, unavailCountDisp);
            Assert.assertEquals(detachCountOps, detachCountDisp);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 2, groups = {"Regression"})
    public void displayBoardTest_Manhattan() throws InterruptedException, IOException {
        String location = "MN03";
        logTestInfo(wDriver, "Test Name:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " LOCATION:" + location);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        Reporter.log("Current URL: " + wDriver.getCurrentUrl(), true);
        int unavailCountOps = getAnyCategoryHeaderCount("Unavail");
        int detachCountOps = getAnyCategoryHeaderCount("Detach");
        logTestInfo(wDriver, "In OpsBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);
        getDisplayBoardPage();
        Thread.sleep(3000);
        ArrayList allTabs = new ArrayList<String>(wDriver.getWindowHandles());
        wDriver.switchTo().window(allTabs.get(allTabs.size() - 1).toString());
        waitForPageLoad(30);
        int unavailCountDisp = getDispBoardSectionCardCount("Unavail");
        int detachCountDisp = getDispBoardSectionCardCount("Detach");
        logTestInfo(wDriver, "In DisplayBoard Unavailable Count:" + unavailCountDisp + " and Detach Count:" + detachCountDisp);
        try {
            logTestInfo(wDriver, "Now Validatiing Available and Detached Counts");
            Assert.assertEquals(unavailCountOps, unavailCountDisp);
            Assert.assertEquals(detachCountOps, detachCountDisp);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 3, groups = {"Smoke", "Regression"})
    public void displayBoardTest_Brooklyn_South() throws InterruptedException, IOException {
        String location = "BKS07";
        logTestInfo(wDriver, "Test Name:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " LOCATION:" + location);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        Reporter.log("Current URL: " + wDriver.getCurrentUrl(), true);
        int unavailCountOps = getAnyCategoryHeaderCount("Unavail");
        int detachCountOps = getAnyCategoryHeaderCount("Detach");
        logTestInfo(wDriver, "In OpsBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);
        getDisplayBoardPage();
        Thread.sleep(3000);
        ArrayList allTabs = new ArrayList<String>(wDriver.getWindowHandles());
        wDriver.switchTo().window(allTabs.get(allTabs.size() - 1).toString());
        waitForPageLoad(30);
        int unavailCountDisp = getDispBoardSectionCardCount("Unavail");
        int detachCountDisp = getDispBoardSectionCardCount("Detach");
        logTestInfo(wDriver, "In DisplayBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);

        try {
            logTestInfo(wDriver, "Now Validatiing Available and Detached Counts");
            Assert.assertEquals(unavailCountOps, unavailCountDisp);
            Assert.assertEquals(detachCountOps, detachCountDisp);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 4, groups = {"Regression"})
    public void displayBoardTest_Queens_West() throws InterruptedException, IOException {
        String location = "QW05";
        logTestInfo(wDriver, "Test Name:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " LOCATION:" + location);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        Reporter.log("Current URL: " + wDriver.getCurrentUrl(), true);
        int unavailCountOps = getAnyCategoryHeaderCount("Unavail");
        int detachCountOps = getAnyCategoryHeaderCount("Detach");
        logTestInfo(wDriver, "In OpsBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);
        getDisplayBoardPage();
        Thread.sleep(3000);
        ArrayList allTabs = new ArrayList<String>(wDriver.getWindowHandles());
        wDriver.switchTo().window(allTabs.get(allTabs.size() - 1).toString());
        waitForPageLoad(30);
        int unavailCountDisp = getDispBoardSectionCardCount("Unavail");
        int detachCountDisp = getDispBoardSectionCardCount("Detach");
        logTestInfo(wDriver, "In DisplayBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);

        try {
            logTestInfo(wDriver, "Now Validatiing Available and Detached Counts");
            Assert.assertEquals(unavailCountOps, unavailCountDisp);
            Assert.assertEquals(detachCountOps, detachCountDisp);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 5, groups = {"Regression"})
    public void displayBoardTest_Bronx() throws InterruptedException, IOException {
        String location = "BX06";
        logTestInfo(wDriver, "Test Name:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " LOCATION:" + location);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        Reporter.log("Current URL: " + wDriver.getCurrentUrl(), true);
        int unavailCountOps = getAnyCategoryHeaderCount("Unavail");
        int detachCountOps = getAnyCategoryHeaderCount("Detach");
        logTestInfo(wDriver, "In OpsBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);
        getDisplayBoardPage();
        Thread.sleep(3000);
        ArrayList allTabs = new ArrayList<String>(wDriver.getWindowHandles());
        wDriver.switchTo().window(allTabs.get(allTabs.size() - 1).toString());
        waitForPageLoad(30);
        int unavailCountDisp = getDispBoardSectionCardCount("Unavail");
        int detachCountDisp = getDispBoardSectionCardCount("Detach");
        logTestInfo(wDriver, "In DisplayBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);

        try {
            logTestInfo(wDriver, "Now Validatiing Available and Detached Counts");
            Assert.assertEquals(unavailCountOps, unavailCountDisp);
            Assert.assertEquals(detachCountOps, detachCountDisp);
            logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
        } catch (Error e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
    }
}

