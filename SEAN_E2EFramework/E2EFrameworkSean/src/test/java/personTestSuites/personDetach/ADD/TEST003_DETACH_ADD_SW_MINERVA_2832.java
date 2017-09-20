package personTestSuites.personDetach.ADD;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.data.LoginData;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import person.personDetach.actions.DetachActions;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.*;
import static personTestSuites.testData.PersonData.setPersonLocationForTest;

/**
 * Created by skashem on 12/2/2016.
 */
public class TEST003_DETACH_ADD_SW_MINERVA_2832 extends AbstractStartWebDriver {

    private String location = "BKN03";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String url = LoginData.getLoginData(getUrl());
    private String detachTo = "BKN01";

    @Test(description = "Minerva-2832")
    public void DETACH_ADD_SW_MINERVA2832() throws InterruptedException, IOException {

        extentTest.assignCategory("Smoke","Bug scenario for Minerva-2832");
        System.out.println("**************************************************************************************");
        System.out.println("MINERVA -2832 - User unable to set personnel open ended detachment if unavailable date overlaps");
        System.out.println("**************************************************************************************");
        logTestInfo( wDriver, "MINERVA -2832 - User unable to set personnel open ended detachment if unavailable date overlaps");
        location = setPersonLocationForTest("Available", "SW", 3);
        //line below will open current board
        loginPage().goToBoardLocationByDate( url, location + "/", boardDate );

        clickOnMenuIcon(wDriver, "Equipment");
        clickOnMenuIcon(wDriver, "Task");

        Thread.sleep(1500);

        //line below will get person available sw count
        int personCountInteger = CommonActions.getAnyCategoryCardsCount("Personnel", "Available","SW",null);
        //System.out.println("person SW count is " + personCountInteger);
        for(int i = 0; i <= personCountInteger -1; i++){
            wDriver.findElements( By.cssSelector(".auPersonnelAvailablePanel .auSanitationWorkers .auPersonName")).get(i).click();
            Thread.sleep(400);

            wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            boolean detachmentHistory = wDriver.findElements(By.xpath("//*[contains(@class,'auDetachmentHist')]//*[contains(@class,'auRecord row-')]")).size() > 0;
            if(detachmentHistory == false){
                String personCard = wDriver.findElements( By.cssSelector(".auPersonnelAvailablePanel .auSanitationWorkers .auPersonName")).get(i).getText();
                System.out.println("person "+ personCard + " is selected");
                for(int j= 0; j <= 4; j++){
                    String unavailabilityHistory = wDriver.findElements(By.xpath("//*[contains(@class,'auUnavailabilityHis')]//*[contains(@class,'history-table-row auRecord row-')]")).get(j).getText();
                    if(unavailabilityHistory.toString().toLowerCase().contains("chart") && unavailabilityHistory.toString().toLowerCase().contains("future")){
                        //line below will be detaching a person with open end date
                        DetachActions.addDetach(personCard,detachTo,Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy"),Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy"),"23","00","remarks01");
                        //line below will reture if the error message exist true or false
                        wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                        boolean error = wDriver.findElements(By.cssSelector("server-errors > div > ul > li")).size() > 0;
                        if(error == false){
                            logTestPass(wDriver, "no error is detected for this bug scenario");
                        } else {
                            String errorMessage = wDriver.findElement(By.cssSelector("server-errors > div > ul > li")).getText();
                            logTestFailure(wDriver, "error message is detected for this scenario: " + errorMessage);
                        }

                        break;

                    } // end of is unavailable history contains future status
                }// end of unavailable looping through rows

                break;

            }// end of is detachment history doesn't exist *//*
        } // end of main loop

    }













}
