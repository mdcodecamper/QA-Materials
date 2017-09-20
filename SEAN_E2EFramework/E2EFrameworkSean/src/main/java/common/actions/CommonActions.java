package common.actions;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import utilities.Utilities;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static common.data.RawColorCodes.getRGBAColorName;
import static common.pages.PersonDetailPage.iconNotFoundError;

/**
 * Created by sdas on 10/6/2016.
 */

public class CommonActions extends AbstractStartWebDriver {
    public CommonActions(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }


    //******************************************************************************

    ///////////////////////////////////////////////////////////////////////////////////////
    // Open/Expand/Close Any Table By Name
    public static void openDetailsBySection(String historyTableName) throws IOException {
        Reporter.log("Opening History Details Section..... ", true);
        WebElement tableCheckBox = wDriver.findElement(By.xpath("//*[contains(@class,'auPersonnelDetail')]//*[contains(@class,'" + historyTableName + "')]//input[@type='checkbox']"));
        try {
            Utilities.waitForElementThenDo(wDriver, tableCheckBox).click();
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();
        }
    }

    // Delete Person History From Specific Table
    public static void deletePersonHistoryByTable(String personCardName, String historyTableName) throws InterruptedException, IOException {
        wDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        List<WebElement> deleteIcons;
        smartBoardPage().openPersonCardDetailPanel(personCardName);
        Thread.sleep(1000);
        if (wDriver.findElements(By.xpath("//*[contains(@class,'details-pane')]//*[contains(@class, '" + historyTableName + "')]//tr")).size() > 1) {
            Reporter.log("Rows found in table name/containing: " + historyTableName + " Now Deleting History.....", true);
            List<WebElement> histTable = waitAndGetAllElements(wDriver, wDriver.findElements(By.xpath("//*[contains(@class,'details-pane')]//*[contains(@class, '" + historyTableName + "')]")), 10);
            if (waitAndGetAllElements(wDriver, histTable.get(0).findElements(By.tagName("tr")), 1).size() > 0) {
                for (int page = 0; page < 6; page++) {
                    deleteIcons = histTable.get(0).findElements(By.cssSelector(".auPersonnelDetail .auDelete"));
                    for (int i = 0; i < deleteIcons.size(); i++) {
                        while (deleteIcons.get(i).isDisplayed()) {
                            Thread.sleep(500);
                            deleteIcons.get(i).click();
                            Thread.sleep(1000);
                            try {
                                waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//input[@type='text']")), 10).sendKeys
                                        ("AutomationClean: " + Utilities.getUUID());
                                wDriver.findElement(By.xpath("//button[@type='submit']")).click();
                                Thread.sleep(3500);
                                if(wDriver.findElements(By.cssSelector(".form-group.buttons .btn-submit.auSubmit")).size() > 0){
                                    logTestFailure(wDriver,"Delete Modal Window didn't close...Stopping execution");
                                    Assert.fail();
                                }
                            } catch (NoSuchElementException e) {
                                Thread.sleep(500);
                                wDriver.findElement(By.xpath("//button[text()='Close']")).click();
                                Thread.sleep(1000);
                                i++;
                            }
                            Thread.sleep(1000);
                            histTable = wDriver.findElements(By.xpath("//*[contains(@class,'details-pane')]//*[contains(@class, '" + historyTableName + "')]"));
                            deleteIcons = histTable.get(0).findElements(By.cssSelector(".auPersonnelDetail .auDelete"));

                        }
                    }
                    Thread.sleep(500);
                    if (wDriver.findElements(By.xpath("//*[contains(@class,'details-pane')]//*[contains(@class, '" + historyTableName + "')]//*[text()='Next']")).size() > 0) {
                        wDriver.findElement(By.xpath("//*[contains(@class,'details-pane')]//*[contains(@class, '" + historyTableName + "')]//*[text()='Next']")).click();
                        Thread.sleep(500);
                    } else {
                        break;
                    }
                }
            }
        } else {
            Reporter.log("No Rows to Delete in " + historyTableName + " Skipping Deletion", true);
        }
        wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }


    // Delete All Person History From All Tables
    public static void deleteAllPersonHistory(String personCardName) throws InterruptedException, IOException {
        String[] tables = {"Detach", "Ground", "Unavail", "Mda", "Special"};
        for (String table : tables) {
            deletePersonHistoryByTable(personCardName, table);
            Thread.sleep(1000);
        }
    }

    // Find a person who does not have any active task(Mda, detach, unavail ......)
    public static String getFreshCardForTest(String sectionName, String title, String historyType) throws InterruptedException, IOException {
        String cardName = null;
        boolean cardFound = false;
        title = (title == "SUPN") ? "Superin" : title;
        List<WebElement> allCards = wDriver.findElements(By.xpath("//*[contains(@class,'" + sectionName + "')]//*[contains(@class,'" + title + "') and contains(@class,'card-container')]"));
        for (int i = 0; i <= allCards.size(); i++) {
            allCards.get(i).click();
            Thread.sleep(500);
            WebElement historyTable = wDriver.findElement(By.xpath("//*[contains(@class, 'details-pane')]//*[contains(@class, '" + historyType + "')]//tbody"));
            if (!historyTable.getText().toString().contains("Active") && !historyTable.getText().contains("Future")) {
                Thread.sleep(500);
                //cardName = allCards.get(i).getText().split("\\r?\\n")[0];
                cardName = allCards.get(i).findElement(By.cssSelector(".card-title")).getText();
                logTestInfo(wDriver, "Found a valid card for testing: " + cardName);
                //Reporter.log("Found a valid card for testing: " + cardName, true);
                personDetailPanelPage().closePersonCardDetailPanel();
                break;
            }

        }

        return cardName;
    }


    public static String getIndicatorCodeColor(String cardName, String codeType) {
        String indicatorColor = null;
        List<WebElement> allCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel') or contains(@class, 'auPanelEquipment') or contains(@class, 'auPanelTask')]//*[contains(@class, 'card-container')]"));
        for (WebElement card : allCards) {
            if (card.getText().contains(cardName)) {
                //System.out.println(card.findElement(By.cssSelector(".auSpecialPosition")).getText());
                WebElement indEle = card.findElement(By.cssSelector(".au" + codeType));
                indicatorColor = getRGBAColorName(indEle.getCssValue("color"));
                logTestInfo(wDriver, "In getIndicatorCodeColor Indicator Text COLOR is: " + getRGBAColorName(indicatorColor));
            }
        }
        return indicatorColor;
    }

    public static String getRawElementColor(String cardName, String panelName) throws InterruptedException {
        List<WebElement> allCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanel" + panelName + "')]//*[contains(@class, 'card-container')]"));
        String bgColor = null;
        if (isCardFound(cardName, panelName)) {
            for (WebElement card : allCards) {
                if (card.getText().contains(cardName)) {
                    Thread.sleep(300);
                    bgColor = waitForElementThenDo(wDriver, card, 1).getCssValue("background-color");
                    System.out.println("bg color is " + bgColor);
                    Thread.sleep(300);
                    break;
                }
            }
        } else {
            //Reporter.log("Card Not Found by Name: " + cardName, true);
            logTestFailure(wDriver, "In getRawElementColor > Card Not Found by Name: " + cardName + " RawColor: " + bgColor);
            Assert.fail();
        }
        return bgColor;
    }

    //method below will verify a card color opacity from a task)
    public static void verifyCardOpacityFromTaskPanel(String garage, String cardType, String cardId, String cardOpacity, String shift, String category, String subCategory, int taskNumber, String sectionNumber) {
        String cardContainer = null;
        String clickOnEditMode = null;
        String cardSelector = null;

        if (cardType.toLowerCase().equals("person1")) {
            cardSelector = "auPersonContainer1 > personnel-card > div";
        }

        if (cardType.toLowerCase().equals("person2")) {
            cardSelector = "auPersonContainer2 > personnel-card > div";
        }

        if (cardType.toLowerCase().equals("equipment")) {
            cardSelector = "auEquipmentContainer > equipment-card > div";
        }

        try {
            if (sectionNumber != null) {
                if (garage != null) {
                    //executorClick(wDriver,wDriver.findElement( By.xpath("//*[contains(@class,'heading panel-"+ garage +"')]")) );
                    //Thread.sleep(100);
                    //executorClick(wDriver,wDriver.findElement( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + "" )) );
                    executorScrollIntoView(wDriver, wDriver.findElements(By.cssSelector(".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " ." + cardSelector + "")).get(taskNumber));
                    Thread.sleep(200);
                    cardContainer = wDriver.findElements(By.cssSelector(".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " ." + cardSelector + "")).get(taskNumber).getCssValue("opacity");

                } else {
                    //executorClick(wDriver,wDriver.findElement( By.cssSelector( ".auShift-" + shift + "" )) );
                    executorScrollIntoView(wDriver, wDriver.findElements(By.cssSelector(".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " ." + cardSelector + "")).get(taskNumber));
                    Thread.sleep(200);
                    cardContainer = wDriver.findElements(By.cssSelector(".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + ".auSectionName-" + sectionNumber + " ." + cardSelector + "")).get(taskNumber).getCssValue("opacity");
                }
                if (cardContainer.toString().equals(cardOpacity.toString())) {
                    logTestPass(wDriver, "Card opacity " + cardOpacity + " is valid for " + cardId + " on task number " + (taskNumber + 1) + " for section " + sectionNumber + " on subcategory " + subCategory + " for shift " + shift + " as expected");
                } else {
                    logTestFailure(wDriver, "Card opacity " + cardOpacity + " is not valid for " + cardId + " on task number " + (taskNumber + 1) + " for section " + sectionNumber + " on subcategory " + subCategory + " for shift " + shift + " expected " + cardOpacity + " but actual " + cardContainer);
                }
            } else {

                if (garage != null) {
                    //executorClick(wDriver,wDriver.findElement( By.xpath("//*[contains(@class,'heading panel-"+ garage +"')]")) );
                    //Thread.sleep(100);
                    //executorClick(wDriver,wDriver.findElement( By.cssSelector( ".panel-" + garage + " .auShift-" + shift + "" )) );
                    executorScrollIntoView(wDriver, wDriver.findElements(By.cssSelector(".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " ." + cardSelector + "")).get(taskNumber));
                    Thread.sleep(200);
                    cardContainer = wDriver.findElements(By.cssSelector(".panel-" + garage + " .auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " ." + cardSelector + "")).get(taskNumber).getCssValue("opacity");
                } else {
                    //executorClick(wDriver,wDriver.findElement( By.cssSelector( ".auShift-" + shift + "" )) );
                    executorScrollIntoView(wDriver, wDriver.findElements(By.cssSelector(".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " ." + cardSelector + "")).get(taskNumber));
                    Thread.sleep(200);
                    cardContainer = wDriver.findElements(By.cssSelector(".auShift-" + shift + ".auCategory-" + category + ".auSubCategory-" + subCategory + " ." + cardSelector + "")).get(taskNumber).getCssValue("opacity");
                }
                if (cardContainer.toString().equals(cardOpacity.toString())) {
                    logTestPass(wDriver, "Card opacity " + cardOpacity + " for " + cardId + " is valid on task number " + (taskNumber + 1) + " on subcategory " + subCategory + " for shift " + shift + " as expected");
                } else {
                    logTestFailure(wDriver, "Card opacity " + cardOpacity + " for " + cardId + " is not valid on task number " + (taskNumber + 1) + " on subcategory " + subCategory + " for shift " + shift + " expected " + cardOpacity + " but actual " + cardContainer);
                }
            }

            // return equipmentContainer;
        } catch (Exception e) {
            logTestFailure(wDriver, "Failed while verifying Card opacity On Task");
            //e.printStackTrace();
        }
    }

    public static int getCardYAxis(String cardName) {
        int yAxis = 0;
        List<WebElement> allCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel') or contains(@class, 'auPanelEquipment') or contains(@class, 'auPanelTask')]//*[contains(@class, 'card-container')]"));
        int i = 0;
        while (i < allCards.size()) {
            String text = allCards.get(i).getText();
            if (text.contains(cardName)) {
                yAxis = allCards.get(i).getLocation().getY();
                Reporter.log("Card Y-Axis: " + yAxis, true);
                break;
            }
            i++;
        }
        return yAxis;
    }

    public static WebElement getCardElement(String cardName) {
        WebElement cardElement = null;
        List<WebElement> allCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel') or contains(@class, 'auPanelEquipment') or contains(@class, 'auPanelTask')]//*[contains(@class, 'card-container')]"));
        int i = 0;
        while (i < allCards.size()) {
            String text = allCards.get(i).getText();
            if (text.contains(cardName)) {
                cardElement = allCards.get(i);
                allCards.get(i).getLocation().getY();
                break;
            }
            i++;
        }
        return cardElement;
    }

    // Is Card Present on Board
    public static boolean isCardFound(String cardName, String panelName) {
        boolean cardFound = false;
        List<WebElement> allCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanel" + panelName + "')]//*[contains(@class, 'card-container')]"));
        int i = 0;
        while (i < allCards.size()) {
            String text = allCards.get(i).getText();
            if (text.contains(cardName)) {
                cardFound = true;
                break;
            }
            i++;
        }
        //Reporter.log("Card Found: " + cardFound, true);
        return cardFound;
    }

    public static void closeDetailPanel(String category) {
        try {
            actionClick(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'" + category.toLowerCase() + "-details')]//*[contains(@class,'auActionClose')]")));
            Thread.sleep(5000);
        } catch (Exception e) {
            logTestFailure(wDriver, "can't click on close icon on " + category + " panel");
        }

    }

    public static void clickOnDualGarageDropDown(int index) {
        try {
            wDriver.findElement(By.xpath("//*[contains(@class,'dualgarage actionmenu')]//*[contains(@class,'arrow-down')]")).click();
            Thread.sleep(500);
            wDriver.findElements(By.xpath("//*[contains(@class,'subitem highlight')]")).get(index).click();
            Thread.sleep(800);
        } catch (Exception e) {
            logTestFailure(wDriver, "✗✗✗ Dual garage drop down menu is not found");
        }
    }

    public static void logTestFailure(WebDriver wDriver, String logMessage) {
        try {
            extentTest.log(LogStatus.FAIL, logMessage);
            getScreenShot(wDriver);
            Reporter.log(logMessage, true);
            //Assert.fail();
        } catch (Exception e) {
            Reporter.log("Exception: " + e.getMessage(), true);
            extentTest.log(LogStatus.INFO, "✗✗✗ Failed while logging failure in logTestFailure method");
        }
    }

    public static void logTestPass(WebDriver wDriver, String logMessage) {
        try {
            extentTest.log(LogStatus.PASS, logMessage);
            Reporter.log(logMessage, true);
        } catch (Exception e) {
            Reporter.log("Exception: " + e.getMessage(), true);
            extentTest.log(LogStatus.INFO, "✗✗✗ Failed while logging PASS in logTestPass method");
        }
    }

    public static void logTestInfo(WebDriver wDriver, String logMessage) {
        try {
            extentTest.log(LogStatus.INFO, logMessage);
            Reporter.log(logMessage, true);
        } catch (Exception e) {
            Reporter.log("Exception: " + e.getMessage(), true);
            extentTest.log(LogStatus.INFO, "✗✗✗ Failed while logging INFO in logTestInfo method");
        }
    }

    public static void clickOnMenuIcon(WebDriver wDriver, String equipmentPersonTaskMenu) {
        try {
            wDriver.findElement(By.xpath("//*[contains(@class,'auMenu" + equipmentPersonTaskMenu + "')]")).click();
        } catch (Exception e) {
            logTestFailure(wDriver, equipmentPersonTaskMenu + "✗✗✗ Menu icon for: " + equipmentPersonTaskMenu + " is not found on Main Menu Bar");
        }
    }

/*
    public static boolean isWithInDateRange(String date, String startDate, String endDate) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date Fdate = fmt.parse(startDate);
            java.util.Date Tdate = fmt.parse(endDate);
            java.util.Date ActualDate = fmt.parse(date);
            if (ActualDate.compareTo(Fdate) >= 0 && ActualDate.compareTo(Tdate) <= 0) {
                return true;
            }
        } catch (Exception e) {
            logTestFailure(wDriver, "✗✗✗ Error occured while comparing dates in 'isWithInDateRange()' method");
            Assert.fail();
            Reporter.log("Exception: " + e.getMessage(), true);
        }
        return false;
    }
*/


    /*
    public static void closeTaskPanel(){
        wDriver.findElement(By.cssSelector(".auTaskPanel .closepanel")).click();
    }
    public static void closePersonnelPanel(){
        wDriver.findElement(By.cssSelector(".auPanelPersonnel .closepanel")).click();
    }
    public static void closeEquipmentPanel(){
        wDriver.findElement(By.cssSelector(".auPanelEquipment .closepanel")).click();
    }

*/
    public static int getAnyCategoryCardsCount(String panelType, String category, String subCategory, String ChildCategoryOrShiftName) {
        wDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        //turnOffImplicitWaits();

        WebElement element = null;

        int categoryHeaderCount = 0;
        int cardsCount = 0;
        String cardId = null;
        List<WebElement> cardList = null;

        if (panelType.equals("Equipment")) {
            cardId = "auEquipmentId";
        } else {
            cardId = "auPersonName";
        }
        if (subCategory == "SW") {
            subCategory = "SanitationWorkers";
        } else if (subCategory == "SUP") {
            subCategory = "Supervisors";
        } else if (subCategory == "SUPN") {
            subCategory = "Superintendents";
        } else if (subCategory == "CIV") {
            subCategory = "Civilians";
        }
        try {
            if (subCategory == null && ChildCategoryOrShiftName == null) {
                // try {
                if (panelType.equals("Personnel") && category.equals("Unavailable")) {
                    waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'personnel-Unavailable')]//*[contains(@class,'" + cardId + "')]")), 15);
                    element = waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(text(),'Unavailable')]")), 15);
                    categoryHeaderCount = extractNumbersFromAnyText(element.getText());
                    cardList = wDriver.findElements(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'personnel-Unavailable')]//*[contains(@class,'" + cardId + "')]"));
                    cardsCount = cardList.size();
                    if (categoryHeaderCount != cardsCount) {
                        logTestFailure(wDriver, "Card header count & card list count doesn't match for category " + category + ". Card List count is " + cardsCount + " but card header count is " + categoryHeaderCount);
                    }
                    // return cardsCount;

                } else if (panelType.equals("Personnel") && category.equals("Assigned")) {
                    waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'AssignedPanel')]//*[contains(@class,'" + cardId + "')]")), 15);
                    element = waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'AssignedPanel')]//*[contains(@class,'group-count')]")), 15);
                    categoryHeaderCount = extractNumbersFromAnyText(element.getText());
                    cardList = wDriver.findElements(By.xpath("//*[contains(@class,'AssignedPanel')]//*[contains(@class,'" + cardId + "')]"));
                    cardsCount = cardList.size();
                    if (categoryHeaderCount != cardsCount) {
                        logTestFailure(wDriver, "Card header count & card list count doesn't match for category " + category + ". Card List count is " + cardsCount + " but card header count is " + categoryHeaderCount);
                    }
                    // return cardsCount;
                } else {
                    waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'" + cardId + "')]")), 15);
                    element = waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'group-count')]")), 15);
                    categoryHeaderCount = extractNumbersFromAnyText(element.getText());
                    cardList = wDriver.findElements(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'" + cardId + "')]"));
                    cardsCount = cardList.size();
                    if (categoryHeaderCount != cardsCount) {
                        logTestFailure(wDriver, "Card header count & card list count doesn't match for category " + category + ". Card List count is " + cardsCount + " but card header count is " + categoryHeaderCount);
                    }
                    // return cardsCount;
                }
                /*} catch(Exception e) {
                    logTestInfo(wDriver,"No Category Found!!! \nPossible Cause: NO Person is assigned yet or a invalid locator is passed! \nSetting default count as: 0");
                    cardsCount = 0;
                    //Reporter.log("NoSuchElementException: " + e.toString(), true);
                    return cardsCount;
                }*/
            } else if (subCategory != null && ChildCategoryOrShiftName == null) {
                //try{
                if(category.toLowerCase().equals("assigned")){
                    waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'auPerson') and contains(@class,'Assigned')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'group-count')]")), 15);
                    element = waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'auPerson') and contains(@class,'Assigned')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'group-count')]")), 15);
                    cardList = wDriver.findElements(By.xpath("//*[contains(@class,'auPerson') and contains(@class,'Assigned')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'" + cardId + "')]"));
                } else {
                    waitForElementThenDo( wDriver, wDriver.findElement( By.xpath( "//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'au" + subCategory + "')]//*[contains(@class,'" + cardId + "')]" )), 15 );
                    element = waitForElementThenDo( wDriver, wDriver.findElement( By.xpath( "//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'au" + subCategory + "')]//*[contains(@class,'group-count')]" ) ), 15 );
                    cardList = wDriver.findElements(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'au" + subCategory + "')]//*[contains(@class,'" + cardId + "')]"));
                }
                categoryHeaderCount = extractNumbersFromAnyText(element.getText());
                cardsCount = cardList.size();
                if (categoryHeaderCount != cardsCount) {
                    logTestFailure(wDriver, "Card header count & card list count doesn't match for subCategory " + subCategory + ". Card List count is " + cardsCount + " but card header count is " + categoryHeaderCount);
                }
                // return cardsCount;
                /*} catch(Exception e) {
                logTestInfo(wDriver,"No Category Found!!! \nPossible Cause: NO Person is assigned yet or a invalid locator is passed! \nSetting default count as: 0");
                cardsCount = 0;
                //Reporter.log("NoSuchElementException: " + e.toString(), true);
                return cardsCount;
                }*/
            } else if (ChildCategoryOrShiftName != null && subCategory != null) {
                String childCat;
                if (ChildCategoryOrShiftName.equals("Recycling Misc")) {
                    String[] arrayChildCat = ChildCategoryOrShiftName.split(" ");
                    childCat = arrayChildCat[1];
                } else {
                    childCat = ChildCategoryOrShiftName;
                }
                // try{
                if (panelType.equals("Equipment")) {
                    if (subCategory.equals("RECYCLING")) {
                        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'equipment-recycling-" + childCat.toLowerCase() + "')]//*[contains(@class,'" + cardId + "')]")), 15);
                        element = waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'group-count') and contains(text(),'" + childCat + "')]")), 15);
                        categoryHeaderCount = extractNumbersFromAnyText(element.getText());
                        cardList = wDriver.findElements(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'equipment-recycling-" + childCat.toLowerCase() + "')]//*[contains(@class,'" + cardId + "')]"));
                        cardsCount = cardList.size();
                        // return cardsCount;
                    } else {
                        waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'" + ChildCategoryOrShiftName.toUpperCase() + "')]//*[contains(@class,'" + cardId + "')]")), 15);
                        element = waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'au" + ChildCategoryOrShiftName.toUpperCase() + "')]//*[contains(@class,'group-count')]")), 15);
                        categoryHeaderCount = extractNumbersFromAnyText(element.getText());
                        //System.out.println("header count is " + categoryHeaderCount);
                        cardList = wDriver.findElements(By.xpath("//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'" + ChildCategoryOrShiftName.toUpperCase() + "')]//*[contains(@class,'" + cardId + "')]"));
                        cardsCount = cardList.size();
                    }
                } else {
                    if(category.toLowerCase().equals( "assigned" )) {
                        waitForElementThenDo( wDriver, wDriver.findElement( By.xpath( "//*[contains(@class,'auPerson') and contains(@class,'Assigned')]//*[contains(@class,'" + ChildCategoryOrShiftName + "')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'" + cardId + "')]" ) ), 15 );
                        element = waitForElementThenDo(wDriver, wDriver.findElement(By.xpath("//*[contains(@class,'auPerson') and contains(@class,'Assigned')]//*[contains(@class,'" + ChildCategoryOrShiftName + "')]//*[contains(@class,'au" + subCategory + "')]//*[contains(@class,'group-count')]")), 15);
                        cardList = wDriver.findElements(By.xpath("//*[contains(@class,'auPerson') and contains(@class,'Assigned')]//*[contains(@class,'" + ChildCategoryOrShiftName + "')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'" + cardId + "')]"));
                    } else {
                        waitForElementThenDo( wDriver, wDriver.findElement( By.xpath( "//*[contains(@class,'au" + panelType + category + "Panel')]//*[contains(@class,'" + ChildCategoryOrShiftName + "')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'" + cardId + "')]" ) ), 15 );
                        element = waitForElementThenDo( wDriver, wDriver.findElement( By.xpath( "//*[contains(@class,'" + category + "Panel')]//*[contains(@class,'" + ChildCategoryOrShiftName + "')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'group-count')]")), 15);
                        cardList = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "Panel')]//*[contains(@class,'" + ChildCategoryOrShiftName + "')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'" + cardId + "')]"));
                    }
                    categoryHeaderCount = extractNumbersFromAnyText(element.getText());
                    cardsCount = cardList.size();
                }

                if (categoryHeaderCount != cardsCount) {
                    logTestFailure(wDriver, "Card header count & card list count doesn't match for Criteria/Subcategory " + ChildCategoryOrShiftName + ". Card List count is " + cardsCount + " but card header count is " + categoryHeaderCount);
                }
            }
        } catch (NoSuchElementException e) {
            logTestInfo(wDriver, "No Category Found!!! \nPossible Cause: NO " + panelType + " is assigned yet or a invalid locator is passed! \nSetting default count as: 0");
            cardsCount = 0;
        }
        wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return cardsCount;
    }


    public static void getAnyCategoryCardsCountAfter(String countAmount, int categoryCountBefore, String panelType, String category, String subCategory, String ChildCategoryOrShiftName) {
        //wDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        int categoryCountAfter = 0;
        int intCountAmount;
        String cardCategory = null;
        if (subCategory == null && ChildCategoryOrShiftName == null) {
            cardCategory = category;
        } else if (subCategory != null && ChildCategoryOrShiftName == null) {
            cardCategory = subCategory;
        } else if (subCategory != null && ChildCategoryOrShiftName != null) {
            cardCategory = ChildCategoryOrShiftName + " " + subCategory;
        }
        categoryCountAfter = CommonActions.getAnyCategoryCardsCount(panelType, category, subCategory, ChildCategoryOrShiftName);
        if (countAmount.contains("-")) {
            intCountAmount = Integer.parseInt(countAmount);

            if ((categoryCountBefore + intCountAmount) == categoryCountAfter) {
                logTestPass(wDriver, "category count for " + cardCategory + " is decreased by " + intCountAmount + ". Count before action was " + categoryCountBefore + " and after is " + categoryCountAfter + " as expected");
            } else {
                logTestFailure(wDriver, "category count for " + cardCategory + " is inaccurate. Count before action was " + categoryCountBefore + " and after is " + categoryCountAfter + "");

            }
        } else if (countAmount.contains("+")) {
            intCountAmount = Integer.parseInt(countAmount);
            if ((categoryCountBefore + intCountAmount) == categoryCountAfter) {
                logTestPass(wDriver, "category count for " + cardCategory + " is increased by " + intCountAmount + ". Count before action was " + categoryCountBefore + " and after is " + categoryCountAfter + " as expected");
            } else {
                logTestFailure(wDriver, "category count for " + cardCategory + " is inaccurate. Count before action was " + categoryCountBefore + " and after is " + categoryCountAfter + "");

            }
        } else if (countAmount.contains("0")) {
            if (categoryCountBefore == categoryCountAfter) {
                logTestPass(wDriver, "category count for " + cardCategory + " is same as before. Count before action was " + categoryCountBefore + " and after is " + categoryCountAfter + " as expected");
            } else {
                logTestFailure(wDriver, "category count for " + cardCategory + " is inaccurate. Count before action was " + categoryCountBefore + " and after is " + categoryCountAfter + "");

            }
        }
        //wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


    //method below will verify history doesn't appear more than 5 row per page for any history
    public static void paginationVerification(String cardType, String cardName, String historyType) throws InterruptedException, IOException {
        if (cardType.toLowerCase().equals("equipment")) {
            smartBoardPage().openEquipmentCardDetailPanel(cardName);
        } else {
            smartBoardPage().openPersonCardDetailPanel(cardName);
        }
        wDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        boolean historyExist = wDriver.findElements(By.xpath("//*[contains(@class,'au" + historyType + "Hist')]//*[contains(@class,'auRecord row-')]")).size() > 0;
        if (historyExist == true) {
            int historyLength = wDriver.findElements(By.xpath("//*[contains(@class,'au" + historyType + "Hist')]//*[contains(@class,'auRecord row-')]")).size();
            //System.out.println( "history length is " + historyLength );
            //for (int i = 1; i <= historyLength; i++) {
            if (historyLength > 5) {
                logTestFailure(wDriver, "more than 5 rows exist for " + historyType + " history on default page. Actual is " + historyLength);
            } else {
                if (historyLength == 5) {
                    logTestPass(wDriver, "5 rows of records appear for " + historyType + " history on default page as expected");
                } else {
                    logTestPass(wDriver, historyLength + " row(s) of record(s) appear for " + historyType + " history on default page as expected");

                }
            }
            //}
        }

    }

    //methods below will right click on a card and perform an action
    public static void rightClickOnCard(String panelName, String cardType, String cardId, String actionName) throws InterruptedException {

        List<WebElement> cardElement = null;
        String elementType = null;
        String panelType = null;

        if (panelName.toLowerCase().contains("equipment")) {

            panelType = "Equipment";

        } else if (panelName.toLowerCase().contains("person")) {

            panelType = "Personnel";

        } else {

            panelType = "Task";
        }

        if (cardType.toLowerCase().contains("equipment")) {

            elementType = "auEquipmentId";

        } else {

            elementType = "auPersonName";
        }
        //waitForElementThenDo(wDriver,wDriver.findElement(By.xpath("//*[contains(@class,'au') and contains(@class,'" + panelType + "') and contains(@class,'Panel')]//*[contains(@class,'" + elementType + "')]")),10);
        cardElement = wDriver.findElements(By.xpath("//*[contains(@class,'au') and contains(@class,'" + panelType + "')]//*[contains(@class,'" + elementType + "')]"));
        if (cardElement.size() > 0) {
            Actions action = new Actions(wDriver);
            for (WebElement card : cardElement) {
                //System.out.println("card id is " + card.getText());
                if (cardId.contains(card.getText())) {
                    Thread.sleep(800);
                    action.contextClick(card).perform();
                    Thread.sleep(500);
                    action.moveToElement(wDriver.findElement(By.xpath("//*[contains(@class,'context-menu active')]//*[text()='" + actionName + "']"))).perform();
                    action.click().perform();
                    Thread.sleep(700);
                    break;
                }
            }
        } else {
            logTestFailure(wDriver, "locator not found in " + panelType + " for card id " + cardId);
            Assert.fail();
        }


    }

    //method below will verify Section expand/collapse functionality
    public static void verify_ExpandCollapse(String toggleGroup, String section, String displayAction) {
        //click on Section Caret to expand/collapse group
        wDriver.findElement(By.xpath("//div[contains(@class, '" + toggleGroup + "')]//toggle//i")).click();
        Boolean sectionExpanded = wDriver.findElement(By.xpath("//div[contains(@class, '" + section + "')]")).isDisplayed();
        if (displayAction.equals("show")) {
            Assert.assertTrue(sectionExpanded,"Section " + section + " is expected to be expanded, but is collapsed");
            if (sectionExpanded)
                logTestPass(wDriver, "Section " + section + " is expanded as expected");
            else
                logTestFailure(wDriver, "Section " + section + " is collapsed but expected to be expanded");
            //check Caret class ('caret-down' for expanded, 'caret-right' for collapsed
            Boolean caretDown = wDriver.findElement(By.xpath("//div[contains(@class, '" + toggleGroup + "')]//toggle//i")).getAttribute("class").contains("fa-caret-down");
            Assert.assertTrue(caretDown, "Caret is not displayed correctly");
            if (caretDown)
                logTestPass(wDriver, "Caret symbol is pointing down as expected");
            else
                logTestFailure(wDriver, "Caret symbol is not pointing down as expected");
        }
        else if (displayAction.equals("hide")){
            Assert.assertFalse(sectionExpanded, "Section " + section + " is expected to be collapsed, but is expanded");
            if (!(sectionExpanded))
                logTestPass(wDriver, "Section " + section + " is collapsed as expected");
            else
                logTestFailure(wDriver, "Section " + section + " is expanded but expected to be collapsed");
            Boolean caretUp = wDriver.findElement(By.xpath("//div[contains(@class, '" + toggleGroup + "')]//toggle//i")).getAttribute("class").contains("fa-caret-right");
            Assert.assertTrue(caretUp, "Caret is not displayed correctly");
            if (caretUp)
                logTestPass(wDriver, "Caret symbol is pointing right as expected");
            else
                logTestFailure(wDriver, "Caret symbol is not pointing right as expected");

        }

    }

}
