package common.actions;

import _driverScript.AbstractStartWebDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;

/**
 * Created by sdas on 11/10/2016.
 */
public class PersonActions extends AbstractStartWebDriver {
    public PersonActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }
    //******************************************************************************
    public static void personSubmitAndVerify(WebDriver wDriver, WebElement Submit) throws InterruptedException, IOException {
        waitForElementThenDo(wDriver, Submit, 1).click();
        logTestPass(wDriver,  "Clicked Submit Button");
        Thread.sleep(3000);
        //if(wDriver.findElements(By.cssSelector(".auSubmit")).size() > 0 ) {
        wDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        try{
            if(waitForElementThenDo(wDriver, wDriver.findElement(By.cssSelector(".auSubmit")), 1).isDisplayed() ) {
                if(wDriver.findElements(By.xpath("//*[contains(@class, 'error')]")).size() > 0){
                    WebElement errMsg = wDriver.findElements(By.xpath("//*[contains(@class, 'error') or contains(@class, 'server-errors')]")).get(0);
                    Reporter.log("Error Message: " + errMsg.getText(), true);
                    logTestFailure(wDriver,  "✗✗✗ Modal Window did NOT close!!!  Stopping Execution!!!");
                }
            }
        }catch(NoSuchElementException e){
        }

        wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


    public static String getErrorMessage() throws InterruptedException, IOException {
        WebElement element = wDriver.findElement(By.xpath("//div[contains(@class, 'server-errors')]//li"));
        String errMsg = element.getText();
        return errMsg;
    }

    public static void personMakeAvailable(WebDriver wDriver,String shift, String subCategory, String personCardName) throws InterruptedException {
        List<WebElement> personName;
        Actions action = new Actions(wDriver);

        personName = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnalAssignedPanel')]//*[contains(@class,'" + shift + "')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + subCategory + "')]//*[contains(@class,'auPersonName')]"));
        for(int i = 0; i <= personName.size() -1; i++){
            String getPersonName = personName.get(i).getText();
            if(getPersonName.contains(personCardName)){
                executorClick(wDriver,personName.get(i));
                Thread.sleep(1200);
                try{
                    wDriver.findElement(By.xpath("//*[contains(@class,'auActionButton')]")).click();
                    Thread.sleep(700);
                   boolean elementExist = wDriver.findElements(By.cssSelector(".auMakeAvailable")).size() > 0;
                    wDriver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
                    if(elementExist == true){
                        logTestInfo( wDriver, "Making person " + getPersonName + " Available" );
                        wDriver.findElement(By.cssSelector(".auMakeAvailable")).click();
                    } else {
                        logTestFailure(wDriver,"Make Available Button is not visible on drop down");
                        Assert.fail();
                    }

                } catch(Exception e){
                    logTestFailure(wDriver,"Action button is not visible on person detail panel");
                    Assert.fail();
                }

                break;
            }

        }

    }
    //method below will get Header counts
    public static int get_cards_count(String panel, String section, String subsection) {
        int cards_count = 0;
        WebElement count_str;
        try {
            if (section == null) {
                if (wDriver.findElements(By.xpath
                        ("//*[contains(@class, '" + panel + "')]")).size() > 0) {
                    count_str = wDriver.findElement(By.xpath
                            ("//*[contains(@class, '" + panel + "')]//*[@class ='group-count']"));
                    cards_count = extractNumbersFromAnyText(count_str.getText());
                } else {
                    cards_count = 0;
                    logTestInfo(wDriver, "Count not found for panel " + panel + "; set to zero");
                }
            }
            else {
                if (wDriver.findElements(By.xpath
                        ("//*[contains(@class, '" + panel + "')]//*[contains(@class, '" + section + "')]")).size() > 0) {
                    count_str = wDriver.findElement(By.xpath
                            ("//*[contains(@class, '" + panel + "')]//*[contains(@class, '" + section + "')]//*[@class='group-count']"));
                    cards_count = extractNumbersFromAnyText(count_str.getText());
                } else {
                    cards_count = 0;
                    logTestInfo(wDriver, "Count not found for panel " + panel + ", section " + section + "; set to zero");
                }

            }

        } catch (Error e) {
            logTestFailure(wDriver, "Problem getting Cards count for " + panel + " and " + section + "! REASON: " + e.getMessage());
        }
        return cards_count;

    }



}
