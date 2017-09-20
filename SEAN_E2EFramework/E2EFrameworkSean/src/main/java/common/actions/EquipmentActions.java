package common.actions;

import _driverScript.AbstractStartWebDriver;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;
import static equipment.abstractBase.EquipmentBasePage.equipmentPanelUtilities;
import static equipment.abstractBase.EquipmentBasePage.equipmentUpdateLoadActions;

/**
 * Created by sdas on 11/10/2016.
 */
public class EquipmentActions extends AbstractStartWebDriver {
    public EquipmentActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }
    //******************************************************************************

   /* public static void equipmentSubmitAndVerify(WebDriver wDriver, WebElement Submit, WebElement Cancel) throws InterruptedException, IOException {
        waitForElementThenDo(wDriver, Submit, 1).click();
        logTestPass(wDriver, "Clicked Submit Button");
        Thread.sleep(3000);
        //if(wDriver.findElements(By.cssSelector(".auSubmit")).size() > 0 ) {
        wDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        try {
            if (waitForElementThenDo(wDriver, wDriver.findElement(By.cssSelector(".auSubmit")), 1).isDisplayed()) {
                if (wDriver.findElements(By.xpath("/*//*[contains(@class, 'error')]")).size() > 0) {
                    WebElement errMsg = wDriver.findElements(By.xpath("/*//*[contains(@class, 'error')]")).get(0);
                    Reporter.log("Error Message: " + errMsg.getText(), true);
                    logTestFailure(wDriver, "✗✗✗ Modal Window did NOT close!!!  Stopping Execution!!!");
                }
            }
        } catch (NoSuchElementException e) {
        }
        wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }*/

       public static void equipmentSubmitAndVerify(WebDriver wDriver, WebElement Submit, WebElement Cancel) throws InterruptedException, IOException {
           Thread.sleep(5750);
           //executorClick( wDriver, Submit );
           waitForElementThenDo(wDriver, Submit).click();
           Thread.sleep(2000);
           wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
           if(wDriver.findElements(By.cssSelector("button.btn.btn-primary.auSubmit")).size() == 0) {

           } else {
               wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

               logTestFailure(wDriver, "✗✗✗ Modal Window did Not close!!! Stopping Execution");
               Assert.fail();
           }
           wDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
       }

       public static void equipmentSubmitAndVerify_Negative(WebDriver wDriver, WebElement Submit, WebElement Cancel) throws InterruptedException, IOException {
           Thread.sleep(5750);
           waitForElementThenDo(wDriver, Submit).click();
           Thread.sleep(2000);
           wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
           if(wDriver.findElements(By.cssSelector("button.btn.btn-primary.auSubmit")).size() == 0) {
               wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

               logTestFailure(wDriver, "Modal Window did not show error message on invalid input.  Stopping Execution");
               Assert.fail();
           }
           wDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

       }


    public static void equipmentMouseClick(WebDriver wDriver, WebElement Submit, WebElement Cancel) throws InterruptedException, IOException {
        //Thread.sleep(5800);
        Thread.sleep(1300);
        Actions mouseClick = new Actions(wDriver);
        mouseClick.moveToElement( Submit ).perform();
        mouseClick.click().perform();
        Thread.sleep(2500);
        wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        if(wDriver.findElements(By.cssSelector("button.btn.btn-primary.auSubmit")).size() == 0) {
        } else {
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            logTestFailure(wDriver, "✗✗✗ Modal Window did Not close!!! Stopping Execution");
            Assert.fail();
        }
        wDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    }


    public static void moveEquipmentFromPendingLoad(String subcategory) throws IOException, InterruptedException {
        String equipmentId = null;
        String availableSubCategoryCount = equipmentPanelUtilities().getEquipmentCategoryCount("Available", subcategory, null);
        int availableSubCategoryCountInt = Integer.parseInt(availableSubCategoryCount);
        String pendingLoadCount = equipmentPanelUtilities().getEquipmentCategoryCount("Pending", "PendingLoad", null);
        int pendingLoadCountInt = Integer.parseInt(pendingLoadCount);
        // if(availableSubCategoryCountInt <= 10){
        for (int i = 0; i <= pendingLoadCountInt - 1; i++) {
            if (i > 11) {
                break;
            } else {
                try {

                    if (subcategory.equals("RearLoaders")) {
                        wDriver.findElements(By.xpath("//*[contains(@class,'auEquipmentPendingPanel')]//*[contains(@class,'auPendingLoad')]//*[contains(@style,'rgb(15, 117, 255)')]")).get(i).click();
                        equipmentId = wDriver.findElements(By.xpath("//*[contains(@class,'auEquipmentPendingPanel')]//*[contains(@class,'auPendingLoad')]//*[contains(@style,'rgb(15, 117, 255)')]//*[contains(@class,'auEquipmentId')]")).get(i).getText();
                        System.out.println("Equipment id is " + equipmentId);
                    } else if (subcategory.equals("DualBins")) {
                        wDriver.findElements(By.xpath("//*[contains(@class,'auEquipmentPendingPanel')]//*[contains(@class,'auPendingLoad')]//*[contains(@style,'rgb(153, 255, 0)')]")).get(i).click();
                        equipmentId = wDriver.findElements(By.xpath("//*[contains(@class,'auEquipmentPendingPanel')]//*[contains(@class,'auPendingLoad')]//*[contains(@style,'rgb(153, 255, 0)')]//*[contains(@class,'auEquipmentId')]")).get(i).getText();
                    }
                    //smartBoardPage().openEquipmentCardDetailPanel( equipmentId ); //clicking on bin equipment
                    String binType = wDriver.findElement(By.cssSelector(".auType")).getText();
                    System.out.println("Equipment Type is " + binType);
                    if (binType.toLowerCase().contains("dual-bin")) {
                        //line below will Update Load for dual bins equipment
                        equipmentUpdateLoadActions().updateLoad(equipmentId, "Empty", null, "Empty", null);
                    } else {
                        //line below will Update Load for single bin equipment
                        equipmentUpdateLoadActions().updateLoad(equipmentId, "Empty", null, null, null);
                    }


                } catch (Exception e) {
                    logTestInfo(wDriver, "No more equipment for subcategory " + subcategory + " appears on Pending Load category");
                    break;
                }


            }
        }
        //}

    }


    public static void verifyModalWindowRightClick(WebDriver wdriver, String equipmentId, String actionName) throws InterruptedException {

        Actions actionMethod = new Actions(wDriver);

        WebElement equipmentCard = wdriver.findElement(By.xpath("//*[contains(@data-id,'"+ equipmentId +"')]"));
        actionMethod.moveToElement(equipmentCard).perform();
        actionMethod.contextClick().perform();
        WebElement equipmentAction = wdriver.findElement(By.xpath( "//*[contains(@class,'dropdown-item') and contains(text(),'"+ actionName +"')]" ));
        actionMethod.moveToElement(equipmentAction).perform();
        actionMethod.click().perform();

        Thread.sleep( 2000 );

        if(wdriver.findElements(By.xpath("//*[contains(@class,'equipment-modal auEquipment')]")).size() > 0){
            wdriver.findElement( By.cssSelector(".auCancel")).click();
            logTestPass(wdriver,"able to right click on " + actionName + " action and modal window appears");
        } else {
            logTestFailure(wdriver,"unable to right click on " + actionName + " action or modal window didn't appear");
        }
    }




}
