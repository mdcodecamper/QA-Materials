package common.actions;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static task.abstractBase.TaskBasePage.taskDeleteShiftModal;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 10/21/2016.
 */
public class TaskActions extends AbstractStartWebDriver{

    public TaskActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }
    //******************************************************************************


    public static void taskSubmitAndVerify(WebDriver wDriver, WebElement Submit, WebElement Cancel) throws InterruptedException, IOException {
        Thread.sleep(2000);
        //waitForElementThenDo(wDriver, Submit).sendKeys( Keys.ENTER );
        //executorClick( wDriver,Submit );
        waitForElementThenDo(wDriver, Submit).click();
        wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Thread.sleep(2500);
        if(wDriver.findElements(By.cssSelector(".btn.btn-primary.auSubmit")).size() > 0 || wDriver.findElements(By.cssSelector(".btn.btn-primary.auSave")).size() > 0 || wDriver.findElements(By.cssSelector(".btn.btn-primary.auLoad")).size() > 0) {
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            logTestFailure(wDriver, "✗✗✗ Modal Window did Not close!!! Stopping Execution!!! NO Error Message Displayed!!!");
            Assert.fail();
        }
        wDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    }


   /* public static void deleteAllShiftWithTask(WebDriver webDriver, String garage) throws InterruptedException, IOException {

        // Reporter.log("Opening Add Tasks Modal Window..... ", true);
        logTestInfo(wDriver, "Opening Edit Mode Modal Window..... ");
        boolean elementExist;
        WebElement element;
        String shift = null;
        int getSize;
        taskModeUtilities().clickonEditModeButton();
        wDriver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);

        if(garage != null){
            getSize = wDriver.findElements(By.cssSelector(".panel-"+ garage +" .shift-title.auHeader")).size();
        } else {
            getSize = wDriver.findElements(By.cssSelector(".shift-title.auHeader")).size();
        }

        try {
            for(int i = 0; i <= getSize - 1; i++) {
                if (garage != null) {
                    shift = wDriver.findElements(By.cssSelector(".panel-"+ garage +" .shift-title.auHeader")).get(i).getText();
                    executorScrollIntoView( wDriver, wDriver.findElement( By.xpath( "/*//*[contains(@class,'panel-" + garage + "')]/*//*[contains(@class,'auShift-" + shift + "')]" ) ) );
                    elementExist = wDriver.findElements( By.xpath( "/*//*[contains(@class,'panel-" + garage + "')]/*//*[contains(@class,'auShift-" + shift + "')]/*//*[contains(@title,'Edit Shift/Tasks')]" ) ).size() > 0;

                } else {
                    shift = wDriver.findElements(By.cssSelector(".shift-title.auHeader")).get(i).getText();
                    executorScrollIntoView( wDriver, wDriver.findElement( By.xpath( "/*//*[contains(@class,'auShift-" + shift + "')]" ) ) );
                    elementExist = wDriver.findElements( By.xpath( "/*//*[contains(@class,'auShift-" + shift + "')]/*//*[contains(@title,'Edit Shift/Tasks')]" ) ).size() > 0;
                }
                if (elementExist == true){
                    if (garage != null) {
                        executorClick( wDriver, wDriver.findElement( By.xpath( "/*//*[contains(@class,'panel-" + garage + "')]/*//*[contains(@class,'auShift-" + shift + "')]/*//*[contains(@title,'Delete Shift')]" ) ) );
                        taskDeleteShiftModal().clickYes();
                    } else {
                        executorClick( wDriver, wDriver.findElement( By.xpath( "/*//*[contains(@class,'auShift-" + shift + "')]/*//*[contains(@title,'Delete Shift')]" ) ) );
                        taskDeleteShiftModal().clickYes();
                    }

                }
            }//closure of For Loop
            taskModeUtilities().clickonEditModeButton();
        } catch(Exception e){
            logTestFailure(wDriver, "inaccuracy while deleting shift with task(s)");
            Assert.fail();
        }
    }*/

    public static void deleteAllShiftWithTask(WebDriver driver, String garage) throws InterruptedException, IOException {
         int getSize;
         String shift = null;
         String allShift = null;
         String arrShift = null;
         boolean elementExist = false;
        waitForElementThenDo( wDriver, wDriver.findElement( By.xpath( "//*[contains(text(),'Edit Mode')]" ) ) );
        wDriver.manage().timeouts().implicitlyWait( 2, TimeUnit.SECONDS );
        if(garage != null){
            getSize = wDriver.findElements(By.cssSelector(".panel-"+ garage +" .shift-title.auHeader")).size();
        } else {
            getSize = wDriver.findElements(By.cssSelector(".shift-title.auHeader")).size();
        }

        try {
            for (int i = 0; i <= getSize - 1; i++) {

                if (garage != null) {
                    elementExist = wDriver.findElements( By.cssSelector( ".panel-" + garage + " .shift-title.auHeader" ) ).size() > 0;
                    if(elementExist == true) {
                        shift = wDriver.findElements( By.cssSelector( ".panel-" + garage + " .shift-title.auHeader" ) ).get( i ).getText();
                    }
                } else {
                    elementExist = wDriver.findElements( By.cssSelector( ".shift-title.auHeader" ) ).size() > 0;
                    if(elementExist == true) {
                        shift = wDriver.findElements( By.cssSelector( ".shift-title.auHeader" ) ).get( i ).getText();
                    }
                }

                if(elementExist == true) {
                    allShift += shift + ",";
                }

            }
            taskModeUtilities().clickonEditModeButton();
            if(elementExist == true) {
                String[] arrayShift = allShift.split( "," );
                for (int j = 0; j <= arrayShift.length - 1; j++) {
                    if (arrayShift[j].contains( "null" )) {
                        arrShift = arrayShift[j].replace( "null", "" );
                    } else {
                        arrShift = arrayShift[j];
                    }
                    if (garage != null) {
                        executorClick( wDriver, wDriver.findElement( By.xpath( "//*[contains(@class,'panel-" + garage + "')]//*[contains(@class,'auShift-" + arrShift + "')]//*[contains(@title,'Delete Shift')]" ) ) );
                        taskDeleteShiftModal().clickYes();
                    } else {
                        executorClick( wDriver, wDriver.findElement( By.xpath( "//*[contains(@class,'auShift-" + arrShift + "')]//*[contains(@title,'Delete Shift')]" ) ) );
                        taskDeleteShiftModal().clickYes();
                    }
                }
            } else {
                logTestInfo(wDriver,"No shift(s) is available to delete");
            }
            taskModeUtilities().clickonEditModeButton();
            Thread.sleep(600);
        } catch(Exception e){
            logTestFailure(wDriver, "inaccuracy while deleting shift with task(s)");
            Assert.fail();
        }
    }

    public static void dragdrop(WebDriver wDriver, WebElement LocatorFrom, WebElement LocatorTo) {

        String xto=Integer.toString(LocatorTo.getLocation().x);
        String yto=Integer.toString(LocatorTo.getLocation().y);
        ((JavascriptExecutor)wDriver).executeScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
                        "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
                LocatorFrom,xto,yto);
    }


}
