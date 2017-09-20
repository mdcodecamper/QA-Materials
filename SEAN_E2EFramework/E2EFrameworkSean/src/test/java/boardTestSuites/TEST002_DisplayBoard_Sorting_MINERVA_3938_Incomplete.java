package boardTestSuites;

import _driverScript.AbstractStartWebDriver;
import common.actions.BoardActions;
import common.data.LoginData;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.ArrayList;

import static common.actions.BoardActions.getDisplayBoardPage;
import static common.actions.CommonActions.*;
import static common.pages.DisplayBoardPage.getDispBoardSectionCardCount;
import static common.pages.DisplayBoardPage.getDisplayBoardCards;
import static common.pages.SmartBoardPage.getOpsBoardCardNames;
import static person.expectedResults.panels.PersonPanelUtilities.getAnyCategoryHeaderCount;

/**
 * Created by sdas on 12/3/2016.
 */
public class TEST002_DisplayBoard_Sorting_MINERVA_3938_Incomplete extends AbstractStartWebDriver {
    private String url = LoginData.getLoginData(getUrl());
    private String date = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String[] testLocations = {"BKS13", "MN05", "SI03", "BX06", "QW05"};


    @Test(priority = 1, groups = {"Smoke", "Regression"})
    public void DisplayBoard_Sorting_By_Seniority_MINERVA_3938() throws InterruptedException, IOException {
        for(String location : testLocations){
            logTestInfo(wDriver, "Test Name:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " LOCATION:" + location);
            loginPage().goToBoardLocationByDate(url, location + "/", date);
            Reporter.log("Current URL: " + wDriver.getCurrentUrl(), true);
            waitForPageLoad(30);
            Thread.sleep(3000);
            int unavailCountOps = getAnyCategoryHeaderCount("Unavail");
            int detachCountOps = getAnyCategoryHeaderCount("Detach");
            logTestInfo(wDriver, "In OpsBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);
            BoardActions.orderBySeniority();
            Thread.sleep(3000);
            String[] sortOrderUnavailOps = getOpsBoardCardNames("Unavail");
            String[] sortOrderDetachOps = getOpsBoardCardNames("Detach");
            getDisplayBoardPage();
            waitForPageLoad(30);
            ArrayList allTabs = new ArrayList<String>(wDriver.getWindowHandles());
            wDriver.switchTo().window(allTabs.get(allTabs.size() - 1).toString());
            //waitForPageLoad(30);
            Thread.sleep(3000);
            setPageZoomSize(.5);
            String[] sortOrderUnavailDisp = getDisplayBoardCards("unavail");
            String[] sortOrderDetachDisp = getDisplayBoardCards("detach");

            int unavailCountDisp = getDispBoardSectionCardCount("Unavail");
            int detachCountDisp = getDispBoardSectionCardCount("Detach");
            logTestInfo(wDriver, "In DisplayBoard Unavailable Count:" + unavailCountOps + " and Detach Count:" + detachCountOps);
            try {
                logTestInfo(wDriver, "Now Validating Available and Detached Counts in OpsBoard and Display Board.....");
                Assert.assertEquals(unavailCountOps, unavailCountDisp, "Unavailable Count");
                Assert.assertEquals(detachCountOps, detachCountDisp, "Detach Count");
                logTestInfo(wDriver, "Now Validating Sorting Order in OpsBoard and Display Board.....");
                //Assert.assertEquals(sortOrderOps, sortOrderDisp,"Sort Order");
                validateCardSorting(sortOrderUnavailOps, sortOrderUnavailDisp);
                //validateCardSorting(sortOrderDetachOps, sortOrderDetachDisp);
                logTestPass(wDriver, "✓✓✓ ALL Validations Passed.......");
            } catch (Error e) {
                logTestFailure(wDriver, "✗✗✗ TEST VALIDATION FAILURE!!! REASON: " + e.getMessage());
                Assert.fail();
            }
        }

    }

    public static void validateCardSorting(String[] actualOrder, String[] expectedOrder){
        for(int i = 0; i < actualOrder.length -1; i++){
                try{
                    System.out.println("Actual: " + actualOrder[i] + " and Expected: " + expectedOrder[i]);
                    Assert.assertTrue(actualOrder[i].startsWith(expectedOrder[i]));
                   }catch(Exception e){
                    logTestFailure(wDriver, "Sorting Order Did NOT Match!!! Error: Actual: " + actualOrder[i] + " and Expected: " + expectedOrder[i]);
                    //Assert.fail();
                }

        }

    }


}

