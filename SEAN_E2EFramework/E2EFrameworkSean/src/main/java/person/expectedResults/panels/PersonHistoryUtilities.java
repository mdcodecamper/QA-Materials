package person.expectedResults.panels;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.*;

/**
 * Created by sdas on 10/6/2016.
 */


public class PersonHistoryUtilities extends AbstractStartWebDriver {
    public PersonHistoryUtilities(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }


    public static String NextButtonUnavailableHistory = ".auUnavailabilityHist .pagination-next.page-item";
    public static String PreviousButtonUnavailableHistory = ".auUnavailabilityHist .pagination-prev.page-item";
    public static String Record = ".auUnavailabilityHist table .auRecord";
    public static String Remarks = ".auUnavailabilityHist .auRemarks";
    public static String Type = ".auUnavailabilityHist .auType";
    public static String Action = ".auUnavailabilityHist .auAction";
    public static String Status = ".auUnavailabilityHist .auRecord .auStatus";
    public static String EndDate = ".auUnavailabilityHist .auEndDate";
    public static String EndTime = ".auUnavailabilityHist .auEndTime";
    public static String StartDate = ".auUnavailabilityHist .auStartDate";
    public static String StartTime = ".auUnavailabilityHist .auStartTime";


    //==================== All Methods related to Expected Result validations ====================//


    ///////////////////////////////////////////////////////////////////////////////////////
    // Find a desired row in a Detail History Table by a rowFinderString/Keyword and get all the cell Data
    // AND Validate History Row Details with Expected Details
    public static void validateDetailRowHistory(String cardName, String historyTableName, String rowFinderString, String[] expectedDetails) throws IOException, InterruptedException {
        //closeTaskPanel();
        wDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        try {
            if (wDriver.findElements(By.xpath("//*[contains(@class, 'details-pane')]")).size() < 1)
                smartBoardPage().openPersonCardDetailPanel(cardName);
            String[] actualDetails = getDetailHistoryRowData(cardName, historyTableName, rowFinderString);
            logTestInfo(wDriver, "Actual "+ historyTableName +" History Deatils:      " + Arrays.asList(actualDetails));
            logTestInfo(wDriver, "Expected "+ historyTableName +" History Details:    " + Arrays.asList(expectedDetails));
            Assert.assertEquals(actualDetails, expectedDetails, "Details History");
            logTestPass(wDriver, "TEST PASSED: Actual History Details MATCHED with the Expected Details");
        } catch (Error e) {
            logTestFailure(wDriver, "TEST FAILED!!! Actual and Expected Details DID NOT MATCH!!!  REASON:" + e.getMessage());
            Assert.fail();
        }

    }

    public static void validateDetailRowHistory(String[] actualDetails, String[] expectedDetails) throws IOException, InterruptedException {
        try {
            logTestInfo(wDriver, "Actual History Deatils:      " + Arrays.asList(actualDetails));
            logTestInfo(wDriver, "Expected History Details:    " + Arrays.asList(expectedDetails));
            Assert.assertEquals(actualDetails, expectedDetails, "Person Details History");
            logTestPass(wDriver, "Actual History Details MATCHED with the Expected Details");
        } catch (Error e) {
            logTestFailure(wDriver, "Actual and Expected Details DID NOT MATCH!!!  REASON:" + e.getMessage());
            Assert.fail();
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // Find a desired row in a Detail History Table by a rowFinderString/Keyword and get all the cell Data
    public static String[] getDetailHistoryRowData(String cardName, String tableName, String finderKey) throws IOException, InterruptedException {
        String[] actualDetails = {};
        WebElement tableBody;
        boolean rowFound = false;
        WebElement nextButton;
        int pageNo = 1;
        List<WebElement> noOfRows = null;
        try {
            if (wDriver.findElements(By.xpath("/*//*[contains(@class, 'details-pane')]")).size() < 1)
                smartBoardPage().openPersonCardDetailPanel(cardName);
            while (!rowFound) {
                tableBody = wDriver.findElement(By.xpath("//*[contains(@class, '" + tableName + "') and contains(@class, 'Hist')]"));
                noOfRows = tableBody.findElements(By.tagName("tr"));
                //Reporter.log("Number of Rows: " + noOfRows.size(), true);
                String tableData = tableBody.getText();
                if (tableData.contains(finderKey)) {
                    WebElement actualRow = wDriver.findElement(By.xpath("//*[contains(@class, '" + tableName + "')]//td[contains(., '" + finderKey + "')]/parent::tr"));
                    actualDetails = waitForElementThenDo(wDriver, actualRow).getText().split(" ");
                    Thread.sleep(500);
                    rowFound = true;
                    break;
                } else if (!rowFound && noOfRows.size() == 7) {
                    nextButton = wDriver.findElement(By.xpath("//*[contains(@class, '" + tableName + "') and contains(@class, 'Hist')]//*[text()='Next']"));
                    nextButton.click();
                    Thread.sleep(500);
                    pageNo++;
                } else if ((noOfRows.size() < 7 && actualDetails.length == 0) || (pageNo > 5)) {
                    actualDetails = null;
                    logTestFailure(wDriver, "NO ROW DATA FOUND IN HISTORY TABLE!");
                    Assert.fail();
                    break;
                }
            }
        } catch (Exception e) {
            logTestFailure(wDriver, "NO ROW DATA FOUND IN HISTORY TABLE!");
            Assert.fail();
            return null;
        }
        logTestInfo(wDriver, tableName.toUpperCase() +" History Deatils Row Found:   " + Arrays.asList(actualDetails));
        return actualDetails;

    }



    public static void verifyUnavailableHistory(String personCardName, String unavailableCode, String action, String status, String effectiveDate, String effectiveHour, String effectiveMinute, String endDate, String endHour, String endMinute, String remarks) {

        List<WebElement> rows, listRemarks;
        String row = null, rowRemarks, actualType, actualAction, actualStatus, actualStartDate, actualStartTime, actualEndDate, actualEndTime, actualRemarks;
        boolean historyPass = true, rowFound = false;
        int i = 1;
        int rowNumber = 0, pageNumber = 1;
        WebElement nextButton;

        try {
            nextButton = wDriver.findElement(By.cssSelector(NextButtonUnavailableHistory));
            while (!rowFound) {

                rows = wDriver.findElements(By.cssSelector(Record));
                listRemarks = wDriver.findElements(By.cssSelector(Remarks));

                for (i = 1; i < listRemarks.size(); i++) {
                    rowRemarks = listRemarks.get(i).getText();
                    if (rowRemarks.equals(remarks)) {
                        rowNumber = i;
                        row = rows.get(i - 1).getText();
                        rowFound = true;
                        break;
                    }
                }

                if (rows.size() < 4 && !rowFound) {
                    pageNumber = pageNumber;
                    rowNumber = rowNumber;
                    break;
                }

                if (!rowFound) {
                    nextButton.findElement(By.xpath("a")).click();
                }

                if (pageNumber > 50) {
                    break;
                }
            }

            if (rowFound) {

                System.out.println("row: " + row);
                extentTest.log(LogStatus.INFO, "Verifying Unavailable History in Page Number: " + pageNumber + ", Row Number: " + rowNumber);

                if (unavailableCode != null) {
                    actualType = wDriver.findElements(By.cssSelector(Type)).get(rowNumber).getText();
                    if (!actualType.equals(unavailableCode)) {
                        logTestFailure(wDriver, "unavailableCode did not match. Expected: " + unavailableCode + ", Actual: " + actualType);
                        historyPass = false;
                    }
                }

                if (action != null) {
                    actualAction = wDriver.findElements(By.cssSelector(Action)).get(rowNumber).getText();
                    if (!actualAction.equals(action)) {
                        logTestFailure(wDriver, "Action did not match. Expected: " + action + ", Actual: " + actualAction);
                        historyPass = false;
                    }
                }

                if (status != null) {
                    actualStatus = wDriver.findElements(By.cssSelector(".auUnavailabilityHist .auStatus")).get(rowNumber).getText();
                    if (!actualStatus.equals(status)) {
                        logTestFailure(wDriver, "Status did not match. Expected: " + status + ", Actual: " + actualStatus);
                        historyPass = false;
                    }
                }

                if (effectiveDate != null) {
                    actualStartDate = wDriver.findElements(By.cssSelector(StartDate)).get(rowNumber).getText();
                    if (!actualStartDate.equals(effectiveDate)) {
                        logTestFailure(wDriver, "Effective Date did not match. Expected: " + effectiveDate + ", Actual: " + actualStartDate);
                        historyPass = false;
                    }
                }

                if (effectiveHour != null && effectiveMinute != null) {
                    actualStartTime = wDriver.findElements(By.cssSelector(StartTime)).get(rowNumber).getText();
                    if (!actualStartTime.equals(effectiveHour + ":" + effectiveMinute)) {
                        logTestFailure(wDriver, "Effective Time did not match. Expected: " + effectiveHour + ":" + effectiveMinute + ", Actual: " + actualStartTime);
                        historyPass = false;
                    }
                }

                if (endDate != null) {
                    actualEndDate = wDriver.findElements(By.cssSelector(EndDate)).get(rowNumber).getText();
                    if (!actualEndDate.equals(endDate)) {
                        logTestFailure(wDriver, "End Date did not match. Expected: " + endDate + ", Actual: " + actualEndDate);
                        historyPass = false;
                    }
                }

                if (endHour != null && endMinute != null) {
                    actualEndTime = wDriver.findElements(By.cssSelector(EndTime)).get(rowNumber).getText();
                    if (!actualEndTime.equals(endHour + ":" + endMinute)) {
                        logTestFailure(wDriver, "Status did not match. Expected: " + endHour + ":" + endMinute + ", Actual: " + actualEndTime);
                        historyPass = false;
                    }
                }

                if (remarks != null) {
                    actualRemarks = wDriver.findElements(By.cssSelector(Remarks)).get(rowNumber).getText();
                    if (!actualRemarks.equals(remarks)) {
                        logTestFailure(wDriver, "Remarks did not match. Expected: " + remarks + ", Actual: " + actualRemarks);
                        historyPass = false;
                    }
                }

                if (historyPass) {
                    logTestPass(wDriver, "Unavailable History Validation passed for Page Number: " + pageNumber + ", Row Number: " + rowNumber);
                }
            }

        } catch (Exception e) {
            logTestFailure(wDriver, "Exception occured while validating unavailable history row number " + rowNumber);
            Assert.fail();
        }


    }

    //Go to Editable Unavailable History Page with Editable Icons
    public static void getUnavailHistEditablePage(String personCardName) throws InterruptedException {
        smartBoardPage().openPersonCardDetailPanel(personCardName);
        List<WebElement> rows;
        List<WebElement> statusElements;
        int pageNumber = 0;
        boolean rowFound = false;
        WebElement nextButton;
        try {
            nextButton = wDriver.findElement(By.cssSelector(NextButtonUnavailableHistory));
            while (!rowFound || pageNumber > 10) {
                rows = wDriver.findElements(By.cssSelector(Record));
                statusElements = wDriver.findElements(By.cssSelector(Status));
                pageNumber = +1;
                for (int j = 0; j < rows.size(); j++) {
                    String status = statusElements.get(j).getText();
                    if (status.equals("Active") || status.equals("Future")) {
                        rowFound = true;
                        break;
                    }
                }
                if (rows.size() < 4 && !rowFound) {
                    break;
                }
                if (!rowFound) {
                    nextButton.findElement(By.xpath("a")).click();
                }
            }
        } catch (Exception e) {
            logTestFailure(wDriver, "Error occured while getting Row Number for Editable Unavailable History");
            Assert.fail();
        }
    }



}
