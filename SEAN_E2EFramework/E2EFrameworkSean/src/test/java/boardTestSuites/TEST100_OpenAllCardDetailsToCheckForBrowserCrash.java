package boardTestSuites;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.List;

/**
 * Created by sdas on 10/12/2016.
 */
public class TEST100_OpenAllCardDetailsToCheckForBrowserCrash extends AbstractStartWebDriver {
    //@FindAll(@FindBy(css = ".auPersonContainer"))
    //List<WebElement> AllCards;

    private String url = LoginData.getLoginData(getUrl());
    private String location = "MN01/";
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");

    @Test(priority = 1, description = "Open All Person Details Panels")
    public void openAllPersonDetailsPanels() throws InterruptedException, IOException {
        System.out.println("End Date: " +  Utilities.getDesiredDateInFormat(1, "yyyyMMdd"));
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        loginPage().goToBoardLocationByDate(url, location, date);
        List<WebElement> AllPersonCards = wDriver.findElements(By.cssSelector(".auPersonContainer"));
        Reporter.log("Number of total cards: " + AllPersonCards.size());
        int totalCardOpened = 0;
        for(int i = 0; i < AllPersonCards.size(); i++){
            Reporter.log("Now clicking on Person card: " + i, true);
            AllPersonCards.get(i).click();
            Thread.sleep(500);
            personDetailPanelPage().closePersonCardDetailPanel();
            totalCardOpened = i;
        }
        Reporter.log("Number of cards clicked is: " + totalCardOpened, true);
    }

    @Test(priority = 2, description = "Open All Equipment Details Panels")
    public void openAllEquipmentDetailsPanels() throws InterruptedException, IOException {
        System.out.println("End Date: " +  Utilities.getDesiredDateInFormat(1, "yyyyMMdd"));
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        loginPage().goToBoardLocationByDate(url, location, date);
        List<WebElement> AllEquipCards = wDriver.findElements(By.cssSelector(".equipment"));
        Reporter.log("Number of total cards: " + AllEquipCards.size());
        int totalCardOpened = 0;
        for(int i = 0; i < AllEquipCards.size(); i++){
            Reporter.log("Now clicking on Equipment card: " + i, true);
            AllEquipCards.get(i).click();
            Thread.sleep(500);
            equipmentDetailPanelPage().closeEquipmentCardDetailPanel();
            totalCardOpened = i;
        }
        Reporter.log("Number of cards clicked is: " + totalCardOpened, true);
    }
}
