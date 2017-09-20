package common.actions;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestFailure;

public class LoginActions extends AbstractStartWebDriver {
    public LoginActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }

    public static void setBrowser(String browserName) throws IOException {
        if ((browserName.equalsIgnoreCase("Chrome"))) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            //DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-extensions", "--incognito");
            wDriver = new ChromeDriver(options);
            wDriver.manage().deleteAllCookies();
            wDriver.manage().window().maximize();
            int screenWidth = wDriver.manage().window().getSize().getWidth();
            //Reporter.log("Current Screen Width: " + screenWidth, true);
            if(!(screenWidth >= 1936)){
                wDriver.manage().window().setSize(new Dimension(1936, 1066));
            }
            //wDriver.manage().window().maximize();
            wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            wDriver.manage().timeouts().pageLoadTimeout(boardLoadTimeout, TimeUnit.SECONDS);

        } else {
            logTestFailure(wDriver,"Board did not load in " + boardLoadTimeout + " Seconds");
            Reporter.log("Browser is NOT Supported!!! Aborting Test....", true);
            Assert.fail();
        }
    }

    public static void setTimeOuts(int implicitWait, int pageLoadTime) throws IOException {
            wDriver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
            wDriver.manage().timeouts().pageLoadTimeout( pageLoadTime, TimeUnit.SECONDS);
    }

    public static String setUrl(String url) {
        wDriver.navigate().to(url);
        Reporter.log("Navigating to Login Page: " + url, true);
        return url;
    }

    public static void login() throws InterruptedException {
        loginPage().enterUserName().enterPassword();
        Thread.sleep(1000);
    }

    public static boolean assertLogin() throws IOException {
        boolean loggedIn;
        extentTest.log(LogStatus.INFO, "ASSERTING LOGIN SUCCESS");
        Reporter.log("Validating if Login is Successful....", true);
        if (elementCheck(wDriver, wDriver.findElement(By.id("workunit")), 30)){
            extentTest.log(LogStatus.INFO, "Validating if Login is Successful....");
            Reporter.log("*********** LOGIN TO DSNY SMARTAPP SUCCEEDED ***********", true);
            loggedIn = true;
        } else {
            loggedIn = false;
            extentTest.log(LogStatus.FAIL, "LOGIN FAILED!!! ABORTING TEST!!!");
            Reporter.log("*********** LOGIN FAILED!!! ABORTING TEST!!! ***********", true);
            getScreenShot(wDriver);
            Assert.fail();
        }
        return loggedIn;
    }


}
