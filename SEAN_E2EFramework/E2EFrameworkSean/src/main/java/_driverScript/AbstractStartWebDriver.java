package _driverScript;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.LoginActions;
import common.data.LoginData;
import common.pages.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import utilities.Utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static common.actions.CommonActions.logTestInfo;


public abstract class AbstractStartWebDriver extends Utilities {

    public static WebDriver wDriver = null;
    public static long suiteStartTime;
    public static long suiteEndTime;
    public static long testStartTime;
    public static long testEndTime;
    public static ExtentReports extentReport;
    public static ExtentTest extentTest;
    public static String Path = null;
    public static int boardLoadTimeout = 90;
    public static SoftAssert softAssert;
    //=========================== SET TEST ENV & REPORT PATH HERE ============================//
    // Test Environment
    public static String url = "QA1";

    // Test Report path
   //public static String reportsPath = System.getProperty("user.dir") + "//AutomationReports//"+ url + "//";
    public static String reportsPath = "Z://QA//1AutomationReports//" + url + "//";

    //==========================================================================================
    public static String getUrl() {
        return url;
    }

    @BeforeSuite
    public static void beforeSuite() throws IOException, InterruptedException {
        Reporter.log(":::::::::::::::::: CLOSING ALL INSTANCES OF CHROME BROWSER AND DRIVER ::::::::::::::::::",
                true);
        //Kill ALL instances and services of Chrome and ChromeDriver then free memory
        Runtime.getRuntime().exec("src/main/resources/CleanUp.cmd");

        Reporter.log("================= STARTING SMART AUTOMATION TEST ON ENVIRONMENT: " + getUrl().toUpperCase() + " =================",
                true);
        extentReport = ExtentReports();
        extentReport.addSystemInfo("Test Environment", getUrl().toUpperCase())
                .addSystemInfo("Browser Name", "Chrome");
        LoginActions.setBrowser("chrome");
        suiteStartTime = System.currentTimeMillis();
        LoginActions.setUrl(LoginData.getLoginData(url));
        LoginActions.login();

    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        extentTest = extentReport.startTest(method.getName());
        testStartTime = System.currentTimeMillis();
        extentTest.log(LogStatus.INFO, "TEST STARTED: " + method.getName());
        softAssert = new SoftAssert();
    }

    @AfterMethod()
    public void afterMethod(Method method) throws IOException {
        testEndTime = System.currentTimeMillis();
        extentTest.log(LogStatus.INFO, "TEST ENDED: " + method.getName());
        logTestInfo(wDriver, "Execution Time for Current Test: " + method.getName() + " is: " + convertSecondsToHMmSs(testEndTime - testStartTime));
        extentReport.endTest(extentTest);
        extentReport.flush();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws InterruptedException, IOException {
        suiteEndTime = System.currentTimeMillis();
        logTestInfo(wDriver, "Total Execution Time for Test Suite: : " + convertSecondsToHMmSs(suiteEndTime - suiteStartTime));
        Reporter.log("TOTAL TEST EXECUTION TIME OF WHOLE SUITE: " + convertSecondsToHMmSs(suiteEndTime - suiteStartTime));
        logTestInfo(wDriver, "********************* END OF DSNY SMARTBOARD AUTOMATION TEST *********************");
        logTestInfo(wDriver, "TEST RESULT SAVED IN: " + reportsPath);

//        wDriver.close();
//        wDriver.quit();
//        wDriver = null;

        //Open Report After Test is Complete
        WebDriver reportDriver = new ChromeDriver();
        reportDriver.manage().window().maximize();
        if(!Path.isEmpty()) {
            Reporter.log("=========== TEST IS COMPLETE : OPENING TEST REPORT ===========", true);
            reportDriver.get(Path);
        }else{
            Reporter.log("!!!!! FAILED TO GENERATE REPORT !!!!!", true);
        }

    }


    ////////////////////////  ALL PAGE OBJECTS ARE INITIATED HERE ///////////////////////
    // Object for DSNYLoginPage
    public static DSNYLoginPage loginPage() {
        DSNYLoginPage loginPage = new DSNYLoginPage(wDriver);
        return loginPage;
    }

    // Object for SmartBoardPage
    public static SmartBoardPage smartBoardPage() {
        SmartBoardPage smartBoardPage = new SmartBoardPage(wDriver);
        return smartBoardPage;
    }
    // Object for SmartBoardPage
    public static DisplayBoardPage displayBoardPage() {
        DisplayBoardPage displayBoardPage = new DisplayBoardPage(wDriver);
        return displayBoardPage;
    }

    // Object for PersonDetailPanelPage
    public static PersonDetailPage personDetailPanelPage() {
        PersonDetailPage personDetailPanelPage = new PersonDetailPage(wDriver);
        return personDetailPanelPage;
    }

    // Object for EquipmentDetailPage
    public static EquipmentDetailPage equipmentDetailPanelPage() {
        EquipmentDetailPage equipmentDetailPanelPage = new EquipmentDetailPage(wDriver);
        return equipmentDetailPanelPage;
    }

    // Object for TaskEditPage
    public static TaskPage taskEditPage() {
        TaskPage taskEditPage = new TaskPage(wDriver);
        return taskEditPage;
    }


    //////////////////////////////////////  TASK PANELS  /////////////////////////////////////////

    /**************
     * Extent Reports
     **************/

    public static ExtentReports ExtentReports() {
        if (Path == null) {
            Path = reportsPath + getDateTime() + ".html";
            File file = new File(Path);
            //File screenshotsDirectory = new File(reportsPath + "Screenshots//");
            try {
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        extentReport = new ExtentReports(Path, false);
        return extentReport;
    }


    //  *************
    // * Extent Report Screenshot
    //*************

    public static void getScreenShot(WebDriver wDriver) {
        try {
            String fileName = url+ getDateTime() + ".png";
            String directory = reportsPath + "Screenshots//";
            File sourceFile = ((TakesScreenshot) wDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(sourceFile, new File(directory + fileName));
            //String imgPath = directory + fileName;
            String image = extentTest.addScreenCapture("Screenshots//" + fileName);
            extentTest.log(LogStatus.FAIL, "", image);
        } catch (Exception e) {
            extentTest.log(LogStatus.FAIL, "Error Occured while taking SCREENSHOT!!!");
            e.printStackTrace();
        }
    }

    public static void getRegularScreenShot(WebDriver wDriver) {
        try {
            String fileName = url+ getDateTime() + ".png";
            String directory = reportsPath + "Screenshots//";
            File sourceFile = ((TakesScreenshot) wDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(sourceFile, new File(directory + fileName));
            //String imgPath = directory + fileName;
            String image = extentTest.addScreenCapture("Screenshots//" + fileName);
            extentTest.log(LogStatus.PASS, "", image);
        } catch (Exception e) {
            extentTest.log(LogStatus.FAIL, "Error Occured while taking SCREENSHOT!!!");
            e.printStackTrace();
        }
    }


}