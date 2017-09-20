package personTestSuites.personDetach.ADD;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import person.personDetach.actions.DetachActions;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static common.data.RawColorCodes.getRGBAColorName;
import static person.expectedResults.panels.PersonCardUtilities.getAPersonWithoutNextDayAssigned;
import static person.expectedResults.panels.PersonHistoryUtilities.validateDetailRowHistory;
import static person.expectedResults.panels.PersonPanelUtilities.verifyCardPresenceInCategory;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;


public class TEST006_DETACH_PERSONELL_VERIFY_COLOR_MINERVA_3809 extends AbstractStartWebDriver {

    //Test Data //
    public static String url = LoginData.getLoginData(getUrl());
    public static String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    public static String location = "BKSBO";
    public static String toLocation = "BKS10";
    public static String swCardName;
    public static String startDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    public static String endDate = Utilities.getDesiredDateInFormat(3, "MM/dd/yyyy");
    public static String exStartDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    public static String exEndDate = Utilities.getDesiredDateInFormat(3, "M/d/yyyy");
    public static String endHour = "12";
    public static String endMinute = "15";
    public static String shift = "1215-2015";
    public static String remarks = "Person_Detach_Perm" + Utilities.getUUID();
    ;
    String[] expectedDetails = {"Detach", "Active", "Perm", exStartDate, location, toLocation, remarks, shift};

    @Test(priority = 1, description = "Detach Chief out of Boro and Verify Color")
    public void DETACH_CHIEF() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        verify_color_counts_after_detach("Chief", "GS_II", "Chief", "auChiefsHeader");
        logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");

    }

    @Test(priority = 2, description = "Detach Civilian out of Boro and Verify Color")
    public void DETACH_CIVILIAN() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        //location = setPersonLocationForTest("Available", "SW", 3);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        verify_color_counts_after_detach("Civilians", "CIV", "Civilian", "auCiviliansHeader");
        logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");

    }

    @Test(priority = 4, description = "Detach Sanitation Worker out of Boro and Verify Color change")
    public void DETACH_SANITATION_WORKER() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        verify_color_counts_after_detach("SW", "SW", "Sanitation Workers", "auSanitationWorkersHeader");
        logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");

    }

    @Test(priority = 3, description = "Detach Superintendents out of Boro and Verify Color change")
    public void DETACH_SUPERINTENDENT() throws InterruptedException, IOException {
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        verify_color_counts_after_detach("SUPN", "GS_I ", "Superintendents", "auSuperintendentsHeader");
        logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");

    }


    public void verify_color_counts_after_detach(String category, String subcategory, String category_long, String section) throws InterruptedException, IOException {
        System.out.println("getting detach type count");

        int detachCountBefore = get_cards_count("auPersonnelDetachmentPanel", section, null);
        System.out.println("detach type count is " + detachCountBefore);
        int availableCountBefore = get_cards_count("auPersonnelAvailablePanel", null, null);
        System.out.println("getting available type count");
        int availableTypeCountBefore =get_cards_count("auPersonnelAvailablePanel", section, null);
        System.out.println("available type count is " + availableTypeCountBefore);
        logTestInfo(wDriver, category_long + " Detach Count Before : " + detachCountBefore + " Available Count Before : " + availableCountBefore);
        //get counts in receiving location before detach
        loginPage().goToBoardLocationByDate(url, toLocation + "/", date);
        Thread.sleep(1200);
        int availableCountBefore_rec = get_cards_count("auPersonnelAvailablePanel", null, null);
        int availableTypeCountBefore_rec = get_cards_count("auPersonnelAvailablePanel", section, null);
        logTestInfo(wDriver, "Location " + toLocation + ": " + category_long + " Available Count Before : " + availableTypeCountBefore_rec + ", Available Count Before : " + availableCountBefore_rec);

        loginPage().goToBoardLocationByDate(url, location + "/", date);
        Thread.sleep(1200);
        swCardName = getAPersonWithoutNextDayAssigned("Available", subcategory, null);
        String cardColorBefore = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
        logTestInfo(wDriver, "Location " + location + ": " + category_long + " Card found : " + swCardName + " with color " + cardColorBefore);
        // deletePersonHistoryByTable(swCardName, "Detach");
        //  deletePersonHistoryByTable(swCardName, "Unavail");

        logTestInfo(wDriver, "Now Performing  Detach Add Action....");
        DetachActions.addDetach(swCardName, toLocation, startDate, null, endHour, endMinute, remarks);
        Thread.sleep(3000);
        personDetailPanelPage().closePersonCardDetailPanel();
        int detachCountAfter = get_cards_count("auPersonnelDetachmentPanel", section, null);
        int availableCountAfter = get_cards_count("auPersonnelAvailablePanel", null, null);
        int availableTypeCountAfter = get_cards_count("auPersonnelAvailablePanel", section, null);
        String cardColorAfter = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));

        try {
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color After: " + cardColorAfter);
            Assert.assertEquals(cardColorAfter, cardColorBefore, "Card Color");
            logTestInfo(wDriver, "Verifying card in Detach and NOT in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("Detach", category, "", swCardName), "Card in Detached " + category_long);
            Assert.assertFalse(verifyCardPresenceInCategory("Available", category, "", swCardName), "Card in Available " + category_long);
            logTestInfo(wDriver, "Asserting Before and After Counts.............");
            Assert.assertEquals(detachCountAfter, detachCountBefore + 1, "Detach Card Counts");
            Assert.assertEquals(availableCountAfter, availableCountBefore - 1, "Available Card Counts");
            Assert.assertEquals(availableTypeCountAfter, availableTypeCountBefore - 1, "Available  " + category_long + " Card Counts");
            //Validate Detail History
            logTestInfo(wDriver, "Validating Details History with Expected Details....");
            validateDetailRowHistory(swCardName, "Detach", remarks, expectedDetails);

        } catch (Error e) {
            Reporter.log("✗✗✗ TEST VALIDATION FAILURE!!!" + e.getMessage(), true);
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
        }
        //Getting to recceiving Location
        logTestInfo(wDriver, "Validating From Receiving Location  " + toLocation);
        loginPage().goToBoardLocationByDate(url, toLocation + "/", date);
        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");
        Thread.sleep(1200);
        int availableCountAfter_rec = get_cards_count("auPersonnelAvailablePanel", null, null);
        int availableTypeCountAfter_rec = get_cards_count("auPersonnelAvailablePanel", section, null);

        try {
            logTestInfo(wDriver, "Verifying card in Available");
            Assert.assertTrue(verifyCardPresenceInCategory("Available", category, "", swCardName), "Card in Available  " + category_long);
            logTestInfo(wDriver, "Asserting Before and After Counts on receiving location ............");
            logTestInfo(wDriver, toLocation + ": all count before:" + availableCountBefore_rec + ", all count after: " + availableCountAfter_rec);
            Assert.assertEquals(availableCountAfter_rec, availableCountBefore_rec + 1, "Available Card Counts");
            Assert.assertEquals(availableTypeCountAfter_rec, availableTypeCountBefore_rec + 1, "Available " + category_long + " Card Counts");
            System.out.println("----------------------------");
            String cardColorDetached = getRGBAColorName(getRawElementColor(swCardName, "Personnel"));
            System.out.println("----------------------------");
            logTestInfo(wDriver, "Verifying Card Color Before: " + cardColorBefore + " and Color in Detach location: " + cardColorDetached);
            if (subcategory == "SW") {
                Assert.assertFalse(cardColorDetached.equalsIgnoreCase(cardColorBefore), "Card Color");
            } else {
                Assert.assertEquals(cardColorDetached, cardColorBefore, "Card Color");
            }

        } catch (Exception e) {
            logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
            Assert.fail();
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

