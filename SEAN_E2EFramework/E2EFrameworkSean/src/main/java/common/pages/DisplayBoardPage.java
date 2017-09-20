package common.pages;

import _driverScript.AbstractStartWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static common.actions.CommonActions.logTestInfo;
import static common.data.RawColorCodes.getRGBAColorName;


public class DisplayBoardPage extends AbstractStartWebDriver {

    public static String personFullName;

    public DisplayBoardPage(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }

    //********************************************************************


    public static String getDisplayBoardDate() {
        String boardDate = null;
        List<WebElement> locationEle = wDriver.findElements(By.cssSelector(".displaydate"));
        boardDate = locationEle.get(0).getText();
        return boardDate;
    }

    public static String getDisplayBoardLocation() {
        String location = null;
        List<WebElement> locationEle = wDriver.findElements(By.cssSelector(".block-location"));
        location = locationEle.get(0).getText();
        return location;
    }

    public static int getDispBoardSectionCardCount(String sectionName) {
        int count = 0;
        Set<String> cardSet = new HashSet<>();
        sectionName = sectionName.toLowerCase();
        List<WebElement> allCards = wDriver.findElements(By.xpath("//*[contains(@class, 'block block-" + sectionName + "')]//span[contains(@class, 'namefull')]"));
        for (WebElement card : allCards) {
            if (card.isDisplayed()) {
                count++;
            }
        }
        return count;
    }

    public static String getDispBoardShiftColor(String shiftHeader) throws InterruptedException {
        String color = null;
        List<WebElement> allShifts = wDriver.findElements(By.xpath("//div[contains(@class, 'block block-shift')]"));
        for (WebElement shift : allShifts) {
            String header = waitForElementThenDo(wDriver, shift).getText();
            if (header.contains(shiftHeader)) {
                //Reporter.log("Shift Color From getDispBoardShiftColor: " + waitForElementThenDo(wDriver, shift).getCssValue("background-color"),true);
                color = getRGBAColorName(waitForElementThenDo(wDriver, shift).getCssValue("background-color"));
                break;
            }
        }
        return color;
    }

    public static boolean isShiftPresent(String shiftHeader) {
        boolean shiftPresent = false;
        List<WebElement> allShifts = wDriver.findElements(By.xpath("//*[contains(@class, 'block block-shift')]"));
        for (WebElement shift : allShifts) {
            String header = shift.getText();
            if (header.contains(shiftHeader)) {
                shiftPresent = true;
                break;
            }
        }
        return shiftPresent;
    }

    public static String[] getSectionHeaderTitles() {
        List<WebElement> headerEleList = waitAndGetAllElements(wDriver, wDriver.findElements(By.cssSelector(".block-header")));
        Set<String> headerList = new HashSet<>();
        //logTestInfo(wDriver, "No Of Header Titles: " + headerEleList.size());
        for (WebElement header : headerEleList) {
            Reporter.log("Current Header Title: " + header.getText(), true);
            headerList.add(header.getText());
        }
        logTestInfo(wDriver, "Header Titles: " + headerList);
        String headerTitles[] = headerList.toArray(new String[headerList.size()]);
        return headerTitles;
    }

    public static String[] getDisplayBoardCards(String sectionName) {
        List<String> cardSet = new LinkedList<>();
        List<WebElement> blocks = wDriver.findElements(By.xpath("//*[contains(@class, 'block block-"+ sectionName.toLowerCase() +"') and contains(@class, 'block-task')]"));
        //System.out.println("No of Blocks: " + blocks.size());
        for(WebElement block : blocks){
            List<WebElement> cardEleList = block.findElements(By.tagName("person"));
            for (WebElement card : cardEleList) {
                String[] cardTexts = card.getText().split("\\r?\\n");
                for (String cardText : cardTexts) {
                    if (cardText.length() > 5) {
                        System.out.println("cardName: " + cardText);
                        cardSet.add(cardText.trim().substring(0, 5));
                    }
                }
            }
        }
        logTestInfo(wDriver, "All DisplayBoard Cards in Section: "+ sectionName +" is : " + cardSet);
        String allCards[] = cardSet.toArray(new String[cardSet.size()]);
        return allCards;
    }


/*    public static String[] getDisplayBoardCards(){
        List<WebElement> cardEleList = wDriver.findElements(By.cssSelector(".namefull"));
        Set<String> cardSet = new HashSet<>();
        logTestInfo(wDriver, "No Of Header Titles: " + cardSet.size());
        for (WebElement card : cardEleList ) {
            String[] cardTexts = card.getText().split("\\r?\\n");
            for(String txt : cardTexts){
                if(txt.length() > 2){
                    cardSet.add(txt);
                }
            }
        }
        logTestInfo(wDriver, "All DisplayBoard Cards: " + cardSet);
        String allCards[] = cardSet.toArray(new String[cardSet.size()]);
        return allCards;
    }*/


    public static WebElement findACard(String cardName) {
        WebElement foundCard = null;
        List<WebElement> personCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel')]//*[contains(@class, 'card-container')]"));
        for (WebElement card : personCards) {
            if (waitForElementThenDo(wDriver, card).getText().contains(cardName)) {
                foundCard = card;
            } else {
                foundCard = null;
            }
        }
        return foundCard;

    }


}
