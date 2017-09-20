package boardTestSuites;

import _driverScript.AbstractStartWebDriver;
import common.data.LoginData;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.*;

/**
 * Created by sdas on 12/3/2016.
 */
public class TEST003_OpenBoard_Visibility extends AbstractStartWebDriver {
    private String url = LoginData.getLoginData(getUrl());
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");


    @Test(priority = 1, groups = {"Smoke", "Regression"})
    public void openBoard_boardLoad() throws InterruptedException, IOException {
        String[] boroLocations = {"SBAT","QE11","QE13","BKN01","MN01","QWBO","QEBO","MNBO","BKSBO","SIBO","BKNBO","BXBO","QE07A"};
        for(int i = 0; i <= boroLocations.length -1;i++) {
            logTestInfo(wDriver, "Test Name:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " LOCATION:" + boroLocations[i]);
            loginPage().goToBoardLocationByDate(url, boroLocations[i] + "/", date);
            //Waiting for the board to load
            Reporter.log("Current URL: " + wDriver.getCurrentUrl(), true);
            Thread.sleep(8000);
            wDriver.manage().timeouts().implicitlyWait( 2,TimeUnit.SECONDS);
            try {
                executorClick(wDriver,waitForElementThenDo(wDriver,wDriver.findElement( By.xpath( "//*[contains(@class,'auEquipmentAvailablePanel')]//*[contains(@class,'paneltoggle')]" )),2));
                if(wDriver.findElements(By.xpath("//*[contains(@class,'auEquipmentAvailablePanel')]//*[contains(@class,'paneltoggle')]")).size() > 0 ){
                    logTestPass(wDriver, "Equipment Panel loaded for location " + boroLocations[i]);
                }
            } catch(Exception e) {
                logTestFailure(wDriver, "Equipment Panel didn't load for location " + boroLocations[i]);
            }

            try {
                executorClick(wDriver,waitForElementThenDo(wDriver,wDriver.findElement( By.xpath( "//*[contains(@class,'auPersonName')]" )),2));
                if(wDriver.findElements(By.xpath("//*[contains(@class,'auPersonName')]")).size() > 0 ){
                    logTestPass(wDriver, "Personnel Panel loaded for location " + boroLocations[i]);
                }
            } catch(Exception e) {
                logTestFailure(wDriver, "Personnel Panel didn't load for location " + boroLocations[i]);
            }

            try {
                executorClick(wDriver,waitForElementThenDo(wDriver,wDriver.findElement( By.xpath( "//*[contains(text(),'Edit Mode')]" )),2));
                if(wDriver.findElements(By.xpath("//*[contains(text(),'Edit Mode')]")).size() > 0 ){
                    logTestPass(wDriver, "Task Panel loaded for location " + boroLocations[i]);
                }
            } catch(Exception e) {
                logTestFailure(wDriver, "Task Panel didn't load for location " + boroLocations[i]);
            }
            wDriver.manage().timeouts().implicitlyWait( 30,TimeUnit.SECONDS);

        }
    }


}

