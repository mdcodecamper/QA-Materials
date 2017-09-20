package common.actions;

import _driverScript.AbstractStartWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static common.actions.CommonActions.logTestInfo;

/**
 * Created by sdas on 11/10/2016.
 */
public class BoardActions extends AbstractStartWebDriver {
    public BoardActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }

    //******************************************************************************


    public static void getDisplayBoardPage() throws InterruptedException {
        WebElement displayBoard = wDriver.findElement(By.xpath("//img[@src='/ui/assets/icons/display-board.png']"));
        waitForElementThenDo(wDriver, displayBoard).click();
        waitForPageLoad(60);
    }

    public static void publishBoardPage() throws InterruptedException {
        WebElement displayBoard = wDriver.findElement(By.xpath("//img[@src='/ui/assets/icons/publish-board.png']"));
        waitForElementThenDo(wDriver, displayBoard).click();
        waitForPageLoad(60);

    }

    public static void clickOnActivityTab() throws InterruptedException {
        WebElement displayBoard = wDriver.findElement(By.xpath("//img[@src='/ui/assets/icons/recent-activity.png']"));
        waitForElementThenDo(wDriver, displayBoard).click();
        waitForPageLoad(60);
    }

    public static void orderBySeniority() throws InterruptedException {
        logTestInfo(wDriver, "Sorting Board by Seniority.......");
        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class, 'sortmenu')]")), 3).click();
        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[text()='Seniority']")), 3).click();
        waitForPageLoad(60);
    }

    public static void orderByReverseSeniority() throws InterruptedException {
        logTestInfo(wDriver, "Sorting Board by Reverse Seniority.......");
        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class, 'sortmenu')]"))).click();
        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[text()='Reverse Seniority']"))).click();
        waitForPageLoad(60);
    }

    public static void orderByLocationSeniority() throws InterruptedException {
        logTestInfo(wDriver, "Sorting Board by Location Seniority.......");
        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class, 'sortmenu')]"))).click();
        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[text()='Reverse Seniority']"))).click();
        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class, 'sortmenu')]")),3).click();
        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[text()='Location Seniority']")), 3).click();
        waitForPageLoad(60);
    }

    public static void orderByLastName() throws InterruptedException {
        logTestInfo(wDriver, "Sorting Board by Last Name.......");
        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class, 'sortmenu')]")), 3).click();
        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[text()='Last Name']")), 3).click();
        waitForPageLoad(60);
    }







}
