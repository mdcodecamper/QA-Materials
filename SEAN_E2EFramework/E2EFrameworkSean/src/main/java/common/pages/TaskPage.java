package common.pages;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import task.addShift.modals.TaskAddShiftModal;
import task.addTask.modals.TaskAddTaskModal;
import task.partialRoute.modals.TaskPartialRouteModal;
import task.taskDetails.modals.TaskDetailsModal;
import task.taskTemplate.modals.TaskTemplateModal;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;
import static common.actions.TaskActions.taskSubmitAndVerify;
import static task.abstractBase.TaskBasePage.taskDeleteShiftModal;
import static task.abstractBase.TaskBasePage.taskModeUtilities;

/**
 * Created by skashem on 10/21/2016.
 */
public class TaskPage extends AbstractStartWebDriver {

    ExtentTest extentTest;

    public TaskPage(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    // LOCATORS
    @FindBy(xpath = "//*[contains(text(),'Edit Mode')]")
    WebElement EditButton;
    @FindBy(css = ".btn-custom-shift.auCustomShift")
    WebElement AddShiftButton;
    @FindBy(css = ".auSaveTemplate")
    WebElement SaveTemplateButton;
    @FindBy(css = ".auLoadTemplate")
    WebElement LoadTemplateButton;
    @FindBy(css = ".auLoadPersonnel")
    WebElement LoadPersonnelButton;

    //*****************************************************************************
    public TaskPage clickOnTaskEdit() {
        waitForElementThenDo(wDriver, EditButton, 30).click();
        return new TaskPage(wDriver);
    }

    public TaskPage clickOnLoadPersonnel() {
        executorClick(wDriver, LoadPersonnelButton);
        return new TaskPage(wDriver);
    }


    public TaskAddShiftModal getAddShiftPanel(String garage) throws IOException, InterruptedException {
        // Reporter.log("Opening Add Shift Modal Window..... ", true);
        logTestInfo(wDriver, "Opening Add Shift Modal Window..... ");
        boolean shiftExist;
        WebElement deleteClick;
        WebElement addShitButton = null;
        //try {
        wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        taskModeUtilities().clickonEditModeButton();
        //Thread.sleep( 2500 );

        if (garage != null) {
            try {
                /*shiftExist = wDriver.findElements(By.xpath("/*//*[contains(@class,'panel-" + garage + "')]/*//*[contains(@class,'auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "')]")).size() > 0;
                if (shiftExist == true) {
                    //executorClick( wDriver, wDriver.findElement( By.xpath( "/*//*[contains(@class,'heading panel-" + garage + "')]" ) ) );
                    //Thread.sleep( 100 );
                    // executorClick( wDriver, wDriver.findElement( By.xpath( "/*//*[contains(@class,'panel-" + garage + "')]/*//*[contains(@class,'auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "')]" ) ) );
                    // Thread.sleep( 100 );
                    WebElement element = wDriver.findElement(By.xpath("/*//*[contains(@class,'panel-" + garage + "')]/*//*[contains(@class,'auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "')]/*//*[contains(@title,'Delete Shift')]"));
                    Utilities.executorScrollIntoView(wDriver, element);
                    Thread.sleep(100);
                    executorClick(wDriver, wDriver.findElement(By.xpath("/*//*[contains(@class,'panel-" + garage + "')]/*//*[contains(@class,'auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "')]/*//*[contains(@title,'Delete Shift')]")));
                    Thread.sleep(600);
                    taskDeleteShiftModal().clickYes();
                    //Utilities.alert(wDriver);
                    executorClick(wDriver,wDriver.findElement(By.xpath("/*//*[contains(@class,'panel-" + garage + "')]/*//*[contains(@class,'auCustomShift')]")) );
                } else {*/
                    //executorClick( wDriver, wDriver.findElement( By.xpath( "//*[contains(@class,'heading panel-" + garage + "')]" ) ) );
                    //Thread.sleep( 100 );
                    WebElement element = wDriver.findElement(By.xpath("//*[contains(@class,'panel-" + garage + "')]//*[contains(@class,'auCustomShift')]"));
                    Utilities.executorScrollIntoView(wDriver, element);
                    Thread.sleep(100);
                    executorClick(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'panel-" + garage + "')]//*[contains(@class,'auCustomShift')]")));
                    Thread.sleep(800);
                    //  }
                //addShitButton = utilities.Utilities.waitForElementThenDo( wDriver, wDriver.findElement( By.cssSelector( ".panel-" + garage + " .task-group-actions > button" ) ) );
            } catch (Exception e) {
                logTestFailure(wDriver, "add shift button in not visible for garage location " + garage);
                Assert.fail();
            }
        } else {
            try {
              /*  shiftExist = wDriver.findElements(By.xpath("/*//*[contains(@class,'auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "')]")).size() > 0;
                if (shiftExist == true) {
                    //executorClick( wDriver, wDriver.findElement( By.xpath( "/*//*[contains(@class,'auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "')]" ) ) );
                    //Thread.sleep( 100 );
                    WebElement element = wDriver.findElement(By.xpath("/*//*[contains(@class,'auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "')]/*//*[contains(@title,'Delete Shift')]"));
                    Utilities.executorScrollIntoView(wDriver, element);
                    Thread.sleep(100);
                    executorClick(wDriver, wDriver.findElement(By.xpath("/*//*[contains(@class,'auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "')]/*//*[contains(@title,'Delete Shift')]")));
                    Thread.sleep(600);
                    //Utilities.alert(wDriver);
                    taskDeleteShiftModal().clickYes();
                    executorClick(wDriver, AddShiftButton);
                } else {*/
                    //executorClick( wDriver, wDriver.findElement( By.xpath( "//*[contains(@class,'auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "')]" ) ) );
                    //Thread.sleep( 100 );
                   executorScrollIntoView(wDriver, wDriver.findElement( By.cssSelector( ".btn-custom-shift.auCustomShift" )));
                   Thread.sleep(500);
                   executorClick(wDriver,wDriver.findElement( By.cssSelector( ".btn-custom-shift.auCustomShift" )));
                   Thread.sleep(800);
               // }
            } catch (Exception e) {
                logTestFailure(wDriver, "add shift button in not visible");
                Assert.fail();
            }
        }

        wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return new TaskAddShiftModal(wDriver);
    }

    public TaskAddTaskModal getAddTasksPanel(String garage, String shift) throws IOException, InterruptedException {
        // Reporter.log("Opening Add Tasks Modal Window..... ", true);
        logTestInfo(wDriver, "Opening Add Tasks Modal Window..... ");
        boolean elementExist;
        WebElement element;
        try {
            if (garage != null) {
                //wDriver.findElement( By.cssSelector( ".panel-" + garage + " .auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "" ) ).click();
                //executorClick(wDriver,wDriver.findElement( By.xpath("//*[contains(@class,'heading panel-"+ garage +"')]")) );
                //Thread.sleep(100);
                executorScrollIntoView(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'panel-" + garage + "')]//*[contains(@class,'auShift-" + shift + "')]")));
                elementExist = wDriver.findElements(By.xpath("//*[contains(@class,'panel-" + garage + "')]//*[contains(@class,'auShift-" + shift + "')]//*[contains(text(),'Task')]")).size() > 0;

            } else {
                //wDriver.findElement( By.cssSelector( ".auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "" ) ).click();
                executorScrollIntoView(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'auShift-" + shift + "')]")));
                elementExist = wDriver.findElements(By.xpath("//*[contains(@class,'auShift-" + shift + "')]//*[contains(text(),'Task')]")).size() > 0;
            }
            if (elementExist == true) {

                if (garage != null) {
                    //executorClick(wDriver,wDriver.findElement( By.xpath("//*[contains(@class,'heading panel-"+ garage +"')]")) );
                    //Thread.sleep(100);
                    element = wDriver.findElement(By.xpath("//*[contains(@class,'panel-" + garage + "')]//*[contains(@class,'auShift-" + shift + "')]//*[contains(text(),'Task')]"));
                    Utilities.executorScrollIntoView(wDriver, element);
                    Thread.sleep(100);
                    //executorClick(wDriver,wDriver.findElement( By.cssSelector( ".panel-" + garage + " .auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "" )) );
                    //Thread.sleep(100);
                    executorClick(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'panel-" + garage + "')]//*[contains(@class,'auShift-" + shift + "')]//*[contains(text(),'Task')]")));
                } else {
                    //executorClick( wDriver,wDriver.findElement( By.cssSelector( ".auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "" )) );
                    //Thread.sleep(100);
                    element = wDriver.findElement(By.xpath("//*[contains(@class,'auShift-" + shift + "')]//*[contains(text(),'Task')]"));
                    Utilities.executorScrollIntoView(wDriver, element);
                    Thread.sleep(100);
                    executorClick(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'auShift-" + shift + "')]//*[contains(text(),'Task')]")));
                }
            } else {
                if (garage != null) {
                    //executorClick( wDriver, wDriver.findElement( By.xpath( "//*[contains(@class,'heading panel-" + garage + "')]" ) ) );
                    //Thread.sleep( 100 );
                    //executorClick( wDriver, wDriver.findElement( By.cssSelector( ".panel-" + garage + " .auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "" ) ) );
                    //Thread.sleep( 100 );
                    element = wDriver.findElement(By.xpath("//*[contains(@class,'panel-" + garage + "')]//*[contains(@class,'auShift-" + shift + "')]//*[contains(@title,'Edit Shift/Tasks')]"));
                    Utilities.executorScrollIntoView(wDriver, element);
                    Thread.sleep(100);
                    executorClick(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'panel-" + garage + "')]//*[contains(@class,'auShift-" + shift + "')]//*[contains(@title,'Edit Shift/Tasks')]")));
                } else {
                    //executorClick( wDriver, wDriver.findElement( By.cssSelector( ".auShift-" + shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute + "" ) ) );
                    //Thread.sleep( 100 );
                    element = wDriver.findElement(By.xpath("//*[contains(@class,'auShift-" + shift + "')]//*[contains(@title,'Edit Shift/Tasks')]"));
                    Utilities.executorScrollIntoView(wDriver, element);
                    Thread.sleep(100);
                    executorClick(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'auShift-" + shift + "')]//*[contains(@title,'Edit Shift/Tasks')]")));
                }
            }

        } catch (Exception e) {
            logTestFailure(wDriver, "Action button is not found for Add Tasks");
            e.toString();
            Assert.fail();
        }
        wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return new TaskAddTaskModal(wDriver);
    }

    public TaskPartialRouteModal getPartialRouteModal(String garage, String shift, String category, String subCategory, int taskAmount, String sectionNumber, String ... Criteria){

            String clickOnEditMode = null;
            boolean elementExist;
            int taskAmountBoard = 0;

            if(Criteria.length == 1)
            {
                clickOnEditMode = Criteria[0];
            }


            try {
                if(clickOnEditMode != null) {
                    Thread.sleep(800);
                    executorClick(wDriver,wDriver.findElement( By.xpath( "//*[contains(text(),'Edit Mode')]" )) );            }
            } catch(Exception e) {
                logTestFailure(wDriver, "Task Edit Mode is not found");
            }

            try {

                if (sectionNumber != null) {

                    if (garage != null) {
                        //logTestInfo(wDriver,"assertion started on dual garage " + garage);
                        executorScrollIntoView( wDriver, wDriver.findElements( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " .auPersonContainer1" )).get(taskAmount) );
                        wDriver.findElements( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " div.linked" ) ).get(taskAmount).click();
                        wDriver.findElements( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " div.linked" ) ).get(taskAmount).click();
                    } else {
                        executorScrollIntoView( wDriver, wDriver.findElements( By.cssSelector( ".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " .auPersonContainer1" )).get(taskAmount) );
                        wDriver.findElements( By.cssSelector( ".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " div.linked" ) ).get( taskAmount ).click();
                        wDriver.findElements( By.cssSelector( ".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " div.linked" ) ).get( taskAmount ).click();
                    }

                } else {
                    if (garage != null) {
                        executorScrollIntoView( wDriver, wDriver.findElements( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " .auPersonContainer1" ) ).get( taskAmount ) );
                        wDriver.findElements( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " div.linked" ) ).get( taskAmount ).click();
                        wDriver.findElements( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " div.linked" ) ).get( taskAmount ).click();
                    } else {
                        executorScrollIntoView( wDriver, wDriver.findElements( By.cssSelector( ".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " .auPersonContainer1" ) ).get( taskAmount ) );
                        wDriver.findElements( By.cssSelector( ".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " div.linked" ) ).get( taskAmount ).click();
                        wDriver.findElements( By.cssSelector( ".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " div.linked" ) ).get( taskAmount ).click();

                    }

                }
            } catch(Exception e){

                logTestFailure( wDriver, "Parital Route icon is not visible" );
                Assert.fail();

            }

        wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return new TaskPartialRouteModal(wDriver);
    }

    public TaskDetailsModal clickOnTaskDetailIcon(String garage, String shift, String category, String subCategory, int taskAmount, String sectionNumber, String ... Criteria){

        String clickOnEditMode = null;
        boolean elementExist;
        int taskAmountBoard = 0;

        if(Criteria.length == 1)
        {
            clickOnEditMode = Criteria[0];
        }


        try {
            if(clickOnEditMode != null) {
                Thread.sleep(800);
                executorClick(wDriver,wDriver.findElement( By.xpath( "//*[contains(text(),'Edit Mode')]" )) );            }
        } catch(Exception e) {
            logTestFailure(wDriver, "Task Edit Mode is not found");
        }

        try {

            if (sectionNumber != null) {

                if (garage != null) {
                    //logTestInfo(wDriver,"assertion started on dual garage " + garage);
                    executorScrollIntoView( wDriver, wDriver.findElements( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " .auPersonContainer1" )).get(taskAmount) );
                    wDriver.findElements( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " .auSupervisorContainer > div" ) ).get(taskAmount).click();
                } else {
                    executorScrollIntoView( wDriver, wDriver.findElements( By.cssSelector( ".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " .auPersonContainer1" )).get(taskAmount) );
                    wDriver.findElements( By.cssSelector( ".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " .auSupervisorContainer > div" ) ).get( taskAmount ).click();
                }

            } else {
                if (garage != null) {
                    executorScrollIntoView( wDriver, wDriver.findElements( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " .auPersonContainer1" ) ).get( taskAmount ) );
                    wDriver.findElements( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " .auSupervisorContainer > div" ) ).get( taskAmount ).click();
                } else {
                    executorScrollIntoView( wDriver, wDriver.findElements( By.cssSelector( ".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " .auPersonContainer1" ) ).get( taskAmount ) );
                    wDriver.findElements( By.cssSelector( ".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " .auSupervisorContainer > div" ) ).get( taskAmount ).click();

                }

            }
        } catch(Exception e){

            logTestFailure( wDriver, "Task Details is not found" );
            Assert.fail();

        }

        wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return new TaskDetailsModal(wDriver);
    }


    public TaskTemplateModal getSaveTemplate() {
        wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        try {
            executorClick(wDriver, SaveTemplateButton);
        } catch (Exception e) {
            logTestFailure(wDriver, "Action button is not found for Save Template");
            e.toString();
            Assert.fail();

        }


        return new TaskTemplateModal(wDriver);
    }

    public TaskTemplateModal getLoadTemplate() {
        wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        try {
            executorClick(wDriver, LoadTemplateButton);
        } catch (Exception e) {
            logTestFailure(wDriver, "Action button is not found for Load Template");
            e.toString();
            Assert.fail();

        }

        //wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return new TaskTemplateModal(wDriver);
    }





}
