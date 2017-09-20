package common.pages;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;

public class DSNYLoginPage extends AbstractStartWebDriver {
    public DSNYLoginPage(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }
//*******************************************************************
    @FindBy(id = "username")
    WebElement Username;
    @FindBy(id = "password")
    WebElement Password;
    @FindBy(xpath = "//*[@type='submit']")
    WebElement SubmitBtn;
    @FindBy(css = "div.dayofweek")
    WebElement dayColor;

    public DSNYLoginPage navigateToHomePage(String url) {
        Reporter.log("Navigating to Homepage.....", true);
        wDriver.navigate().to(url);
        return new DSNYLoginPage(wDriver);
    }

    public DSNYLoginPage enterUserName() {
        //Reporter.log("Entering Username.....", true);
        if (waitForElement(wDriver, SubmitBtn).isDisplayed()) {
            Username.sendKeys(LoginData.getLoginData("username"));
        } else {
            Reporter.log("Login page did NOT load successfully!!!", true);
            Assert.fail();
        }
        return new DSNYLoginPage(wDriver);
    }

    public DSNYLoginPage enterPassword() throws InterruptedException {
        Reporter.log("Entering Password.....", true);
        Password.sendKeys(LoginData.getLoginData("password"));
        SubmitBtn.click();
        Thread.sleep(1000);
        return new DSNYLoginPage(wDriver);
    }

    public SmartBoardPage goToBoardLocationByDate(String url, String location, String date) throws InterruptedException {
        logTestInfo(wDriver, "Navigating to Board Location: " + location + " and Date: " + date);
        wDriver.navigate().to(url + location + date);
        //  NEW LOGIN TESTING
          waitForPageLoad(90);

        // PREVIOUS WORKING CODE
        Thread.sleep(5000);
        String dayElement = waitForElementThenDo(wDriver, dayColor).getCssValue("color");
        int i = 0;
        int j = 0;
        logTestInfo(wDriver,"Waiting for Board to be Online.....");
        while (!dayElement.equals("rgba(153, 255, 1, 1)")) {
            dayElement = waitForElementThenDo(wDriver, dayColor).getCssValue("color");
            Thread.sleep(5000);
            if (i > 14 && i % 15 == 0 && j < boardLoadTimeout) {
                logTestInfo(wDriver, "Board is not online after " + i + " seconds. Refreshing Browser......");
                //wDriver.navigate().refresh();
                wDriver.navigate().to(url + location + date);
                j += 15;
                if (j > boardLoadTimeout) {
                    logTestFailure(wDriver, "Board is not online after " + boardLoadTimeout + " seconds. Exiting the test!!!");
                }
                break;
            }
            i++;
        }
        logTestInfo(wDriver,"SmartBoard Loaded Successfully: Element Color is Green");
        return new SmartBoardPage(wDriver);
    }
    


}

