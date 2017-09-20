package equipmentTestSuites.equipmentUpdateLoad;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.LogStatus;
import common.actions.CommonActions;
import common.data.LoginData;
import equipmentTestSuites.testData.EquipmentData;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.getAnyCategoryCardsCount;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by skashem on 10/14/2016.
 */
public class TEST008_DualBins_Pending_to_Available extends AbstractStartWebDriver{


    private String location = "QW01";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String loadDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLoadDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String loadHour = Utilities.get24HourFormat(0);
    private String newStatus1 = "Relay";
    private String material1 = "46";
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Update Load")
    public void equipmentUpdateLoad_PendingLoad_Available() throws InterruptedException, IOException {
        //setEquipmentLocationForTest("Available","DualBins", 1);
        //location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory( "Smoke", "Update Load" );
        System.out.println( "**************************************************************************************" );
        System.out.println( "Scenario 8 - Update Load for Dual Bins from Pending Load to Available(Empty)" );
        System.out.println( "**************************************************************************************" );
        logTestInfo(wDriver, "Scenario 8 - Update Load for Dual Bins from Pending Load to Available(Empty)" );

        //line below will open ops board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );

        int pendingLoadCount = getAnyCategoryCardsCount( "Equipment","Pending", "PendingLoad", null );

        for(int i = 1; i <= pendingLoadCount; i++) {
            //line below will getEquipment from a category

            String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory( "Bin", "Pending", "PendingLoad" );
            //System.out.println( "Equipment Id is " + equipmentId );
            wDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(wDriver.findElements( By.xpath("//*[contains(@data-id,'"+ equipmentId +"')]//*[contains(@class,'single-bin')]")).size() > 0){
                equipmentUpdateLoadActions().updateLoad( equipmentId, "Empty", null, null, null );
            } else {
                equipmentUpdateLoadActions().updateLoad( equipmentId, "Empty", null, "Empty", null );
            }

        }

    }

}
