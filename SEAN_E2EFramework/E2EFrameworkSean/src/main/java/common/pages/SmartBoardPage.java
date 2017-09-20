package common.pages;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;


public class SmartBoardPage extends AbstractStartWebDriver {

    public static String personFullName;

    public SmartBoardPage(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }
    //***************************************************************************************


    public static String[] getOpsBoardCardNames(String sectionName) throws InterruptedException {
        String[] allPersonNames;
        List<String> listNames = new ArrayList<>();
        List<WebElement> personCards = wDriver.findElements(By.xpath("//div[contains(@class, 'context-personnel-"+sectionName.toLowerCase()+"')]"));
        for (WebElement card : personCards) {
            String name = card.findElement(By.cssSelector(".auPersonName")).getText();
            String rank = card.findElement(By.cssSelector(".auRank")).getText();
            listNames.add(name);
        }
        allPersonNames = listNames.toArray(new String[listNames.size()]);
        logTestInfo(wDriver,"All Names Found in OpsBoard for Section " + sectionName.toUpperCase() +" : " + listNames);
        return allPersonNames;
    }

    public static int[] getAllPersonRanks(String sectionName) throws InterruptedException {
        int[] allPersonRanks = null;
        List<Integer> listRanks = new ArrayList<>();
        List<WebElement> personCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel')]//*[contains(@class, '" +sectionName+ "')]//*[contains(@class, 'card-container')]"));
        for (WebElement card : personCards) {
            String name = card.findElement(By.cssSelector(".auPersonName")).getText();
            String rank = card.findElement(By.cssSelector("div.auRank")).getAttribute("innerText");
            //Reporter.log("Rank: " + rank, true);
            rank = rank.toString() + "";
            if(rank !="" || rank !=" " || rank !=null){
                try{
                    listRanks.add(Integer.parseInt(rank.replace("#", "")));
                    allPersonRanks = listRanks.stream().mapToInt(i->i).toArray();
                }catch (NumberFormatException e){
                    listRanks.add(Integer.parseInt("9999"));
                }
            }
        }
        logTestInfo(wDriver, "Total Ranks: " + sectionName + " : " + allPersonRanks.length);
        logTestInfo(wDriver, "All Person Ranks: " + sectionName + " : " + listRanks);
        return allPersonRanks;
    }




    public PersonDetailPage openPersonCardDetailPanel(String personCardName) throws InterruptedException {
        List<WebElement> personCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel')]//*[contains(@class, 'card-container')]"));
        for (WebElement card : personCards) {
            if (card.getText().contains(personCardName)) {
                card.click();
                WebElement header = waitForElementThenDo(wDriver, wDriver.findElement(By.cssSelector(".auPersonnelDetail .auHeaderTitle")), 5);
                personFullName = header.getText();
                //logTestInfo(wDriver, "Clicked On Card: " + personFullName);
            }
        }
        return new PersonDetailPage(wDriver);
    }

    // Get PersonHeaderText
    public PersonDetailPage openPersonCardDetailByFullName(String personCardName, String fullName) throws InterruptedException {
        logTestInfo(wDriver, "Finding Person with Full Name: " + fullName);
        String[] cardNameArray = personCardName.split(" ");
        String lastName = cardNameArray.length == 2 ? cardNameArray[1] : cardNameArray[0];
        String firstName = cardNameArray.length == 1 ? cardNameArray[0] : cardNameArray[0];
        List<WebElement> personCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel')]//*[contains(@class, 'card-container')]"));
        for (WebElement card : personCards) {
            if ( card.getText().contains(lastName.substring(0, 3)) && card.getText().contains(firstName.substring(0, 1)) ) {
                card.click();
                Thread.sleep(100);
                WebElement header = wDriver.findElement(By.cssSelector(".auPersonnelDetail .auHeaderTitle"));
                if (waitForElementThenDo(wDriver, header).getText().contains(fullName)) {
                    break;
                }
            }
        }
        return new PersonDetailPage(wDriver);
    }

    // Get PersonHeaderText
    public PersonDetailPage openPersonCardDetailByFullName(String fullName) throws InterruptedException {
        logTestInfo(wDriver, "Opening Person With Full Name: " + fullName);
        List<WebElement> personCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel')]//*[contains(@class, 'card-container')]"));
        for (WebElement card : personCards) {
            card.click();
            Thread.sleep(100);
            WebElement header = wDriver.findElement(By.cssSelector(".auPersonnelDetail .auHeaderTitle"));
            if (waitForElementThenDo(wDriver, header).getText().contains(fullName)) {
                break;
            }
        }
        return new PersonDetailPage(wDriver);
    }


    public EquipmentDetailPage openEquipmentCardDetailPanel(String cardName) throws InterruptedException, IOException {
        Reporter.log("Opening Detail Panel for Equipment: " + cardName, true);
        Thread.sleep(1300);
        WebElement element = wDriver.findElement(By.xpath("//*[text()='" + cardName + "']"));
        try {
            extentTest.log(LogStatus.INFO, "FINDING CARD BY NAME: " + cardName);
            executorClick(wDriver, element);
        } catch (NoSuchElementException e) {
            logTestFailure(wDriver, "NO CARD IS FOUND BY: " + cardName);
            Assert.fail();
            e.printStackTrace();
        }
        return new EquipmentDetailPage(wDriver);
    }


    public static WebElement findACard(String cardName) {
        WebElement foundCard = null;
        List<WebElement> personCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel')]//*[contains(@class, 'card-container')]"));
        for (WebElement card : personCards) {
            if (waitForElementThenDo(wDriver, card, 5).getText().contains(cardName)) {
                foundCard = card;
            }
        }
        return foundCard;

    }


}
