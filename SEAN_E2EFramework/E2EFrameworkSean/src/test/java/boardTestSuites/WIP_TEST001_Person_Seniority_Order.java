package boardTestSuites;

import _driverScript.AbstractStartWebDriver;
import common.actions.BoardActions;
import common.data.LoginData;
import common.pages.PersonDetailPage;
import org.apache.regexp.RE;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static common.actions.CommonActions.logTestInfo;
import static common.pages.SmartBoardPage.getAllPersonRanks;
import static common.pages.SmartBoardPage.getOpsBoardCardNames;

/**
 * Created by sdas on 12/7/2016.
 */
public class WIP_TEST001_Person_Seniority_Order extends AbstractStartWebDriver {
    private String url = LoginData.getLoginData(getUrl());
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");


    @Test(priority = 1, description = "Person_Seniority_Order")
    public void person_Seniority_Order() throws InterruptedException, IOException {
        String location = "BKS11";
        logTestInfo(wDriver, "Test Name:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " LOCATION:" + location);
        loginPage().goToBoardLocationByDate(url, location + "/", date);
        Reporter.log("Current URL: " + wDriver.getCurrentUrl(), true);

        BoardActions.orderBySeniority();
        Thread.sleep(3000);
        getOpsBoardCardNames("Unavailable");
        getAllPersonRanks("Unavailable");
        getOpsBoardCardNames("Unassigned");
        getAllPersonRanks("Unassigned");


    }


}
