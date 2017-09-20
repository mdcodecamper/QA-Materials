package person.personDetach.actions;


import common.pages.SmartBoardPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import person.abstractBase.PersonBasePage;

import java.io.IOException;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;

/**
 * Created by sdas on 9/27/2016.
 */
public class DetachActions extends PersonBasePage {
    public DetachActions(WebDriver wDriver) {
        super(wDriver);
        PageFactory.initElements(wDriver, this);
    }


    public static void addDetach(String personCardName, String toLocation, String startDate, String endDate, String startHour, String startMinute, String remarks) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);

        try {
            logTestInfo(wDriver, "Performing Add Detach.....");
            logTestInfo(wDriver, "Current Card is: " + personCardName);
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            Thread.sleep(1000);
            personDetailPanelPage().getDetachAddPanel();
            personDetachAddModal().detachToLocation(toLocation)
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .setStartTime(startHour, startMinute)
                    //.setEndTime(endHour, endMinute) // End Hour and Minute Fields are NOT currently editable
                    .setRemarks(remarks);
            personDetachAddModal().clickSubmit();
        } catch (Exception e) {
            logTestFailure(wDriver,  "Add Person Detach Failed!!!");
        }

    }

    public static void editDetach(String personCardName, int rowIndex, String newDate, String remarks) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        try {
            logTestInfo(wDriver, "Performing Edit Detach.....");
            logTestInfo(wDriver, "Current Card is: " + personCardName);
            //smartBoardPage().openPersonCardDetailPanel(personCardName);
            //openPersonCardDetailByFullName
            Reporter.log("Detach Editing: " + SmartBoardPage.personFullName, true);
            //smartBoardPage().openPersonCardDetailByFullName(SmartBoardPage.personFullName);
            smartBoardPage().openPersonCardDetailByFullName(personCardName, SmartBoardPage.personFullName);
            Thread.sleep(1000);
            if(newDate.contains("/")){
                String[] dateArray = newDate.split("/");
                String[] currentStartDate = wDriver.findElements(By.cssSelector(".auDetachmentHist .auStartDate span")).get(0).getText().split("/");
                String[] currentEndDate =wDriver.findElements(By.cssSelector(".auDetachmentHist .auEndDate span")).get(0).getText().split("/");
                String[] defaultEndDate = (currentEndDate.length > 2) ? currentEndDate : "00/00/0000".split("/");
                //System.out.println("currentStartDate: " + currentStartDate[0] + "currentEndDate: " + currentEndDate.length);
                //System.out.println("defaultEndDate: " + defaultEndDate[0] );
                int diffDays = Integer.parseInt(defaultEndDate[1]) - Integer.parseInt(currentStartDate[1]);
                int diffMonths = Integer.parseInt(defaultEndDate[0]) - Integer.parseInt(currentStartDate[0]);
                int diffYears = Integer.parseInt(defaultEndDate[2]) - Integer.parseInt(currentStartDate[2]);
                //System.out.println("diffDays: " + diffDays + "diffMonths: " + diffMonths + "diffYears: " + diffYears );
                //Reporter.log("Navigating To Detach Edit Panel...", true);
                personDetailPanelPage().getDetachEditPanel(rowIndex);
                if(diffYears != 0){
                    Reporter.log("Editing End date >>  MM DD YYYY : " + dateArray[0] + dateArray[1] + dateArray[2], true );
                    personDetachEditModal()
                            .setEndDate(dateArray[0] + dateArray[1] + dateArray[2] );
                }else if(diffMonths > 0){
                    Reporter.log("Editing End date >>  MM DD: " + dateArray[0] + dateArray[1], true);
                    personDetachEditModal()
                            .setEndDate(dateArray[0] + dateArray[1]);
                }else if(diffDays > 0){
                    Reporter.log("Editing End date >>  DD: " + dateArray[1], true);
                    personDetachEditModal()
                            .setEndDate(dateArray[1]);
                }
                personDetachEditModal()
                        .setRemarks(remarks);
                personDetachEditModal().clickSubmit();

            }else{
                getScreenShot(wDriver);
                Reporter.log("INVALID Date Found!!! Aborting Test....");
                Assert.fail();
            }

        } catch (Exception e) {
            logTestFailure(wDriver,  "Edit Person Detach Failed!!!");
        }
    }


    public static void deleteDetach(String personCardName, int rowIndex, String reason) throws IOException {
        Reporter.log("==================================================================", true);
        Reporter.log("Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName(), true);
        try {
            logTestInfo(wDriver, "Performing Add Detach.....");
            logTestInfo(wDriver, "Current Card is: " + personCardName);
            //smartBoardPage().openPersonCardDetailPanel(personCardName);
            Reporter.log("Detach Deleting : " + SmartBoardPage.personFullName, true);
            //smartBoardPage().openPersonCardDetailByFullName(SmartBoardPage.personFullName);
            smartBoardPage().openPersonCardDetailByFullName(personCardName, SmartBoardPage.personFullName);
            Thread.sleep(1000);
            personDetailPanelPage().getDetachDeletePanel(rowIndex)
                    .setDetachDeleteReason(reason)
                    .clickSubmit();
        } catch (Exception e) {
            logTestFailure(wDriver,  "Delete Person Detach Failed!!!");
        }
    }


}