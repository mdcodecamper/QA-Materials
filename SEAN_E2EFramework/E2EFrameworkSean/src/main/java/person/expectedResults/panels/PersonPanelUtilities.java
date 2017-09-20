package person.expectedResults.panels;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import utilities.Utilities;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestPass;
import static common.pages.PersonDetailPage.iconNotFoundError;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by sdas on 10/6/2016.
 */
public class PersonPanelUtilities extends AbstractStartWebDriver {
    public PersonPanelUtilities(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }
    //==================== All Methods related to Expected Result validations ====================//


    // Pass any desired Category > subCategory > title to get the header count: pass null as unintended values
    // Example: To get Just the category head count which does not have or you don't need subCategory or title count pass the latter two arguments as null;
    // Criteria Sequence: subCategory, title
    public static int getAnyCategoryHeaderCount(String category, String... Criteria) {
        wDriver.manage().timeouts().implicitlyWait(15, SECONDS);
        String subCategory = null;
        String title = null;
        if (Criteria.length >= 1) {
            subCategory = Criteria[0];
        }
        if (Criteria.length > 1) {
            title = Criteria[1];
        }

        WebElement element = null;
        int categoryHeaderCount = 0;
        if (title == "SW") {
            title = "Sanitation";
        } else if (title == "SUP") {
            title = "Supervisor";
        } else if (title == "SUPN") {
            title = "Superintendent";
        }else if (title == "CIV") {
            title = "Civilian";
        }
        if (subCategory == "SW") {
            subCategory = "Sanitation";
        } else if (subCategory == "SUP") {
            subCategory = "Supervisor";
        } else if (title == "SUPN") {
            title = "Superintendent";
        }else if (subCategory == "CIV") {
            subCategory = "Civilian";
        }
        try {
            if (title == null && subCategory == null) {
                if (wDriver.findElements(By.xpath("//*[contains(@class,'Personnel" + category + "')]//*[contains(@class,'group-count')]")).size() > 0) {
                    element = wDriver.findElements(By.xpath("//*[contains(@class,'Personnel" + category + "')]//*[contains(@class,'group-count')]")).get(0);
                    categoryHeaderCount = extractNumbersFromAnyText(element.getText());
                    return categoryHeaderCount;
                }
            } else if (title == null) {
                if (wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'group-count')]")).size() > 0) {
                    element = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'group-count')]")).get(0);
                    categoryHeaderCount = extractNumbersFromAnyText(element.getText());
                    return categoryHeaderCount;
                }
            } else {
                if (wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'" + title + "')]//*[contains(@class,'group-count')]")).size() > 0) {
                    element = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'" + title + "')]//*[contains(@class,'group-count')]")).get(0);
                    categoryHeaderCount = extractNumbersFromAnyText(element.getText());
                    return categoryHeaderCount;
                }
            }
        } catch (NoSuchElementException e) {
            Reporter.log("No Category Found!!! \nPossible Cause: NO Person is assigned yet or a invalid locator is passed! \nSetting default count as: 0", true);
            categoryHeaderCount = 0;
            //Reporter.log("NoSuchElementException: " + e.toString(), true);
            return categoryHeaderCount;
        }
        wDriver.manage().timeouts().implicitlyWait(30, SECONDS);
        return categoryHeaderCount;
    }


    //Check if a card is present in particular SubCaegory within a parent Category
    public static boolean verifyCardPresenceInCategory(String category, String subCategory, String title, String cardName) throws IOException {
        wDriver.manage().timeouts().implicitlyWait( 15, SECONDS );
        List<WebElement> elements = null;
        boolean cardFound = false;
        String firstName = null;
        String lastName = null;
        int count = 0;
        if (title == "SW") {
            title = "Sanitation";
        } else if (title == "SUP") {
            title = "Super";
        } else if (title == "CIV") {
            title = "Civilian";
        }
        if (subCategory == "SW") {
            subCategory = "Sanitation";
        } else if (subCategory == "SUP") {
            subCategory = "Super";
        } else if (subCategory == "CIV") {
            subCategory = "Civilian";
        }
        String[] cardNameArray = cardName.split(" ");
        if (cardNameArray.length > 1) {
            firstName = cardNameArray[0];
            lastName = cardNameArray[1];
        } else {
            firstName = cardNameArray[0];
            lastName = null;
        }
        //EB setting to null gives wrong results
        //String subcategory = null;
        String subcategory = subCategory;
        //EB end
        List<WebElement> personCards = null;
        try {
            if (subcategory == null && title == null) {
                count = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'auPersonName')]")).size();
                if (count > 0)
                    personCards = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'auPersonName')]"));
            } else if (subcategory != null && title == null) {
                count = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'groupData " + subcategory + "')]//*[contains(@class,'auPersonName')]")).size();
                if (count > 0)
                    personCards = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'groupData " + subcategory + "')]//*[contains(@class,'auPersonName')]"));
            } else if (subcategory == null && title != null) {
                count = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + title + "')]//*[contains(@class,'auPersonName')]")).size();
                if (count > 0)
                    personCards = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + title + "')]//*[contains(@class,'auPersonName')]"));
            } else if (subcategory != null && title != null) {
                count = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'groupData " + subcategory + "')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + title + "')]//*[contains(@class,'auPersonName')]")).size();
                if (count > 0)
                    personCards = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'groupData " + subcategory + "')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + title + "')]//*[contains(@class,'auPersonName')]"));
            } else {
                logTestFailure(wDriver, "Invalid arguments passed to checkIfCardPresentIncategory method");
            }
            if (count > 0) {
                for (WebElement person : personCards) {
                    if (cardName.equals(person.getText())) {
                        cardFound = true;
                        break;
                    }
                }
            } else {
                cardFound = false;
            }
        } catch (NoSuchElementException e) {
            Reporter.log("Error in: verifyCardPresenceInCategory Msg: " + e.getMessage(), true);
        }
        return cardFound;
    }


    public static WebElement getPersonSectionElement(String category, String subCategory, String title) {
        WebElement element = null;
        try {
            if (title == null && subCategory == null) {
                element = wDriver.findElements(By.xpath("//*[contains(@class,'" + category + "')]")).get(0);
                return element;
            } else if (title == null) {
                element = wDriver.findElement(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'" + subCategory + "')]"));
                return element;
            } else {
                element = wDriver.findElement(By.xpath("//*[contains(@class,'" + category + "')]//*[contains(@class,'" + subCategory + "')]//*[contains(@class,'" + title + "')]"));
                return element;
            }
        } catch (NoSuchElementException e) {
            Reporter.log("No Category Found!!! \nPossible Cause: NO Person is assigned yet or a invalid locator is passed! \nSetting default count as: 0", true);
            //Reporter.log("NoSuchElementException: " + e.toString(), true);
            return null;
        }
    }

    /**
     * Created by srreddy on 10/14/2016.
     * personType has to be 'SW' for SanitationWorkers & 'SUPV' for Supervisors
     */

    public static String getPersonName(String Category, String... Criteria) throws InterruptedException {

        String personName = null;
        String personType = null;
        String SubCategory = null;
        List<WebElement> personnel;
        if (Criteria.length >= 1) {
            personType = Criteria[0];
        }
        if (Criteria.length > 1) {
            SubCategory = Criteria[1];
        }
        try {
            switch (Category) {
                case "Available":
                    personnel = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelAvailablePanel')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + personType + "')]//*[contains(@class,'auPersonName')]"));
                    personName = personnel.get(0).getText();
                    break;
                case "Unavailable":
                    personnel = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelUnavailablePanel')]//*[contains(@class,'" + SubCategory + "Unavailable')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + personType + "')]//*[contains(@class,'auPersonName')]"));
                    personName = personnel.get(0).getText();
                    break;
                case "Detached":
                    personnel = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelDetachmentPanel')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + personType + "')]//*[contains(@class,'auPersonName')]"));
                    personName = personnel.get(0).getText();
                    break;
                case "Mda":
                    personnel = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelMdaPanel')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + personType + "')]//*[contains(@class,'auPersonName')]"));
                    personName = personnel.get(0).getText();
                    break;
                case "Assigned":
                    personnel = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnalAssignedPanel')]//*[contains(@class,'" + SubCategory + "Assigned')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + personType + "')]//*[contains(@class,'auPersonName')]"));
                    personName = personnel.get(0).getText();
                    break;
                default:
                    logTestFailure(wDriver, "Category missing in getPersonName method call :: Category:'" + Category + "' SubCategory: '" + SubCategory + "' personType: '" + personType + "'");
                    org.testng.Assert.fail();
                    break;
            }

        } catch (Exception e) {
            logTestFailure(wDriver, "NO CARD is in Category: " + Category + " and SubCategroy: " + SubCategory + " By PersonType: " + personType);
            org.testng.Assert.fail();
        }
        System.out.println(personName);
        Thread.sleep(1000);
        return personName;
    }

    public static boolean checkIfCardPresentInCategory(String cardName, String Category, String... Criteria) {
        wDriver.manage().timeouts().implicitlyWait( 15, SECONDS );
        String SubCategory = null;
        String PersonType = null;
        if (Criteria.length >= 1) {

            SubCategory = Criteria[0];
        }
        if (Criteria.length > 1) {

            PersonType = Criteria[1];
        }
        SubCategory = (SubCategory != null) ? "-" + SubCategory : "";
        PersonType = (PersonType != null) ? "-" + PersonType : "";
        boolean cardFound = checkCardPresence(cardName, Category, Criteria);
        if (cardFound) {
            logTestPass(wDriver, "Person Card: '" + cardName + "' is present in '" + Category + "" + SubCategory + "" + PersonType + "' category as expected");
        } else {
            logTestFailure(wDriver, "Person Card: '" + cardName + "' is NOT present in '" + Category + " " + SubCategory + "" + PersonType + "' category");
        }
        return cardFound;
    }

    public static boolean checkIfCardNotPresentInCategory(String cardName, String Category, String... Criteria) {
        wDriver.manage().timeouts().implicitlyWait( 15, SECONDS );
        String SubCategory = null;
        String PersonType = null;
        if (Criteria.length >= 1) {

            SubCategory = Criteria[0];
        }

        if (Criteria.length > 1) {

            PersonType = Criteria[1];
        }
        SubCategory = (SubCategory != null) ? "-" + SubCategory : "";
        PersonType = (PersonType != null) ? "-" + PersonType : "";

        boolean cardFound = checkCardPresence(cardName, Category, Criteria);
        if (!cardFound) {
            logTestPass(wDriver, "Person Card: '" + cardName + "' is NOT present in '" + Category + "" + SubCategory + "" + PersonType + "' category as expected");
        } else {
            logTestFailure(wDriver, "Person Card: '" + cardName + "' is present in '" + Category + "" + SubCategory + "" + PersonType + "' category. It should be NOT be present here");
        }
        wDriver.manage().timeouts().implicitlyWait( 30, SECONDS );
        return cardFound;
    }

    public static boolean checkCardPresence(String cardName, String Category, String... Criteria) {
        wDriver.manage().timeouts().implicitlyWait( 5, SECONDS );
        boolean cardFound = false;
        String SubCategory = null;
        String personType = null;
        if (Criteria.length >= 1) {
            SubCategory = Criteria[0];
        }
        if (Criteria.length > 1) {
            personType = Criteria[1];
        }
        List<WebElement> personnel = null;
        int count = 0;
        try {
            if (SubCategory == null && personType == null) {
                count = wDriver.findElements(By.xpath("//*[contains(@class,'" + Category + "')]//*[contains(@class,'auPersonName')]")).size();
                if (count > 0)
                    personnel = wDriver.findElements(By.xpath("//*[contains(@class,'" + Category + "')]//*[contains(@class,'auPersonName')]"));
            } else if (SubCategory != null && personType == null) {
                count = wDriver.findElements(By.xpath("//*[contains(@class,'" + Category + "')]//*[contains(@class,'groupData " + SubCategory + "')]//*[contains(@class,'auPersonName')]")).size();
                if (count > 0)
                    personnel = wDriver.findElements(By.xpath("//*[contains(@class,'" + Category + "')]//*[contains(@class,'groupData " + SubCategory + "')]//*[contains(@class,'auPersonName')]"));
            } else if (SubCategory == null && personType != null) {
                count = wDriver.findElements(By.xpath("//*[contains(@class,'" + Category + "')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + personType + "')]//*[contains(@class,'auPersonName')]")).size();
                if (count > 0)
                    personnel = wDriver.findElements(By.xpath("//*[contains(@class,'" + Category + "')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + personType + "')]//*[contains(@class,'auPersonName')]"));
            } else if (SubCategory != null && personType != null) {
                count = wDriver.findElements(By.xpath("//*[contains(@class,'" + Category + "')]//*[contains(@class,'"+ SubCategory + "')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + personType + "')]//*[contains(@class,'auPersonName')]")).size();
                if (count > 0)
                    personnel = wDriver.findElements(By.xpath("//*[contains(@class,'" + Category + "')]//*[contains(@class,'" + SubCategory + "')]//*[contains(@class,'auPersonContainer personnel card card-container card- " + personType + "')]//*[contains(@class,'auPersonName')]"));
            } else {
                logTestFailure(wDriver, "Invalid arguments passed to checkCardPresence method");
            }
            if (count > 0) {
                for (WebElement person : personnel) {
                    if (cardName.contains(person.getText())) {
                        cardFound = true;
                        break;
                    }else{
                        cardFound = false;
                    }
                }
            }
        } catch (Exception e) {
            logTestFailure(wDriver, "Error occurred while checking if card is present in a category.");
            e.printStackTrace();
        }
        wDriver.manage().timeouts().implicitlyWait( 30, SECONDS );
        return cardFound;
    }


    public static void verifyCategoryCountAfter(int delta, int CategoryCountBefore, String Category, String... Criteria) {

        String subCategory = null;
        String title = null;
        String message = null;
        String Group = null;
        boolean assertTrue;
        try {
            if (Criteria.length >= 1) {
                subCategory = Criteria[0];
            }
            if (Criteria.length > 1) {
                title = Criteria[1];
            }
            if (delta < 0) {
                message = "decreased by " + Math.abs(delta);
            } else if (delta > 0) {
                message = "increased by " + Math.abs(delta);
            } else if (delta == 0) {
                message = "remained same ";
            }

            int CategoryCountAfter = 0;
            CategoryCountAfter = PersonPanelUtilities.getAnyCategoryHeaderCount(Category, subCategory, title);
            subCategory = (subCategory != null) ? "-" + subCategory : "";
            title = (title != null) ? "-" + title : "";
            Group = Category + " " + subCategory + " " + title;
            //if (CategoryCountAfter == (CategoryCountBefore + delta)) {
                //logTestPass(wDriver, "'" + Group + "' category count " + message + " . Expected: " + (CategoryCountBefore + delta) + ", Actual: " + CategoryCountAfter);
                //boolean assertTrue;
            if (CategoryCountAfter == (CategoryCountBefore + delta)) {

                    logTestPass(wDriver, "'" + Group + "' category count " + message + " . Expected: " + (CategoryCountBefore + delta) + ", Actual: " + CategoryCountAfter);
                } else {
                    logTestFailure(wDriver, "'" + Group + "' category count is inaccurate. Expected: " + (CategoryCountBefore + delta) + ", Actual: " + CategoryCountAfter);
                }
           // }
        } catch (Exception e) {
            logTestFailure(wDriver, "Error occured while verifying category count for : " + Group);
            e.printStackTrace();
        }

        //return assertTrue;
    }

    ////////// Redundant

    public static WebElement getPersonCard(String personCardName) {
        WebElement personCard = null;

        try {
            String[] cardNameArray = personCardName.split(Pattern.quote(". "));
            String firstName = cardNameArray.length > 1 ? cardNameArray[0] : "";
            String lastName = cardNameArray.length > 1 ? cardNameArray[1] : cardNameArray[0];
            if (lastName.contains("'")) {
                lastName = lastName.split("'")[1];
            }
            if (!firstName.equals("")) {
                personCard = wDriver.findElement(By.xpath("//div[div[div[span[span[contains(text(),'" + firstName + "')]/following-sibling::span[contains(text(),'" + lastName + "')]]]]]"));
            } else {
                personCard = wDriver.findElement(By.xpath("//div[div[div[span[span[contains(text(),'" + lastName + "')]]]]]"));

            }
        } catch (Exception e) {
            logTestFailure(wDriver, "Error occured while getting personCard web element: " + personCardName);
            e.printStackTrace();
        }
        return personCard;
    }

    public static String getPersonCardColor(String personCardName) {
        String personCardColor = null;
        try {
            personCardColor = getPersonCard(personCardName).getCssValue("background-color");
        } catch (Exception e) {
            logTestFailure(wDriver, "Error occured while getting PersonCard Color: " + personCardName);
            e.printStackTrace();
        }
        return personCardColor;
    }



    //////////////////////////====================WORK IN PROGRESS IGNORE THE CODES BELOW=====================//////////////////////////////


    //Get the Header Title of the section of any card
    public static String getCategoryLocationOfCard(String cardName) {
        // "//*[contains(text(),'D.')]/following-sibling::span[1][contains(text(),'SpPos17')]/ancestor::div[position()=8]/descendant::h5")
        //$x("//*[contains(text(),'M.')]/following-sibling::span[contains(text(),'Ryan')]/ancestor::div[7]")
        String categoryTitle = null;
        String[] cardNameArray = cardName.split(" ");
        String firstName = cardNameArray.length > 1 ? cardNameArray[0] : cardNameArray[0];
        String lastName = cardNameArray.length > 1 ? cardNameArray[1] : "";
        Reporter.log("In findACardInAnySection: Finding Card By Name: " + firstName + " " + lastName, true);
        extentTest.log(LogStatus.INFO, "Finding Card By Name: " + cardName);
        //List<WebElement> categoryHeaders = wDriver.findElements(By.xpath("//div[contains(text(),'" + firstName + "')]/following-sibling::span[contains(text(),'" + lastName + "')]/ancestor::div[position()=9]/descendant::h5"));
        List<WebElement> categoryHeaders = wDriver.findElements(By.xpath("//*[contains(text(),'" + firstName + "')]/following-sibling::span[contains(text(),'" + lastName + "')]/ancestor::div[7]/child::div[1]"));
        //In which Location
        //$x("//*[contains(text(),'M.')]/following-sibling::span[contains(text(),'Ryan')]/ancestor::div[7]/child::div[1]")
        if (categoryHeaders.size() == 1) {
            categoryTitle = categoryHeaders.get(0).getText();
            Reporter.log("Found Card in: " + categoryTitle, true);
        } else {
            Reporter.log("Could not locate Card!!! Card Name: " + cardName, true);
        }

        return categoryTitle;
    }

    /////////////////
    // Open Table By Name
    public static void openDetailsBySection(String sectionName) throws IOException {
        Reporter.log("Opening History Details Section..... ", true);
        By by = By.xpath("//*[text()='" + sectionName + "']/parent::*/parent::*/child::input");
        WebElement element = wDriver.findElement(by);
        try {
            Utilities.waitForElementThenDo(wDriver, element).click();
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();
        }
    }

//    public static boolean checkIfCardPresentInCategory(String cardName) {
//
//        String category = null;
//        String[] cardNameArray = cardName.split(" ");
//        String firstName = cardNameArray.length > 1 ? cardNameArray[0] : cardNameArray[0];
//        String lastName = cardNameArray.length > 1 ? cardNameArray[1] : "";
//        Reporter.log("In findACardInAnySection: Finding Card By Name: " + firstName + " " + lastName, true);
//        extentTest.log(LogStatus.INFO, "Finding Card By Name: " + cardName);
//        //List<WebElement> categoryHeaders = wDriver.findElements(By.xpath("//div[contains(text(),'" + firstName + "')]/following-sibling::span[contains(text(),'" + lastName + "')]/ancestor::div[position()=9]/descendant::h5"));
//        List<WebElement> categoryHeaders = wDriver.findElements(By.xpath("//*[contains(text(),'" + firstName + "')]/following-sibling::span[contains(text(),'" + lastName + "')]/ancestor::div[7]/child::div[1]"));
//        //In which Location
//        //$x("//*[contains(text(),'M.')]/following-sibling::span[contains(text(),'Ryan')]/ancestor::div[7]/child::div[1]")
//        if (categoryHeaders.size() == 1) {
//            categoryTitle = categoryHeaders.get(0).getText();
//            Reporter.log("Found Card in: " + categoryTitle, true);
//        } else {
//            Reporter.log("Could not locate Card!!! Card Name: " + cardName, true);
//        }
//
//        return true;
//    }


}
