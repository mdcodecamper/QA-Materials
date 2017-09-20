package equipmentTestSuites.equipmentGrouping;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import equipmentTestSuites.testData.EquipmentData;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static common.actions.CommonActions.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;

/**
 * Created by EBronfman 02/2017
 */


public class TEST001_Equipment_Groups_Expand_Collapse extends AbstractStartWebDriver {

    private String location = EquipmentData.equipmentSendingLocation;

    @Test(description = "Mechanical Brooms expande/collapsw")
    public void mechanicalBrooms_Expand_Collapse() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","MechanicalBrooms",2);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Regression", "Mechanical Brooms expand/collapse");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Verifying  Mechanical Brooms group collapse-expand functionality");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - Verifying  Mechanical Brooms group collapse-expand functionality");

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //code below will check expand/collapse functionality for Mechanical Brooms
        try {
            logTestInfo(wDriver, "verification started on collapsing Mechanical Brooms section");
            verify_ExpandCollapse("auMechanicalBrooms", "equipment-MechanicalBroomsAvailable", "hide");
            Thread.sleep(3000);
            logTestInfo(wDriver, "verification started on expanding Mechanical Brooms section");
            verify_ExpandCollapse("auMechanicalBrooms", "equipment-MechanicalBroomsAvailable", "show");
            Thread.sleep(3000);

        }
        catch (Exception e) {
            logTestInfo(wDriver, "Problem finding elements " + e.getMessage());
        }

    }

    @Test(description = "Miscellaneous expande/collapse")
    public void miscellaneous_Expand_Collapse() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Available","Miscellaneous",2);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Regression", "Miscellaneous expande/collapse");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 2 - Verifying  Miscellaneous group collapse-expand functionality");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 2 - Verifying  Miscellaneous group collapse-expand functionality");

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //code below will check expand/collapse functionality for Miscellaneous section
        try {
            logTestInfo(wDriver, "verification started on collapsing Miscellaneous section");
            verify_ExpandCollapse("auMiscellaneous", "equipment-MiscellaneousAvailable", "hide");
            Thread.sleep(3000);
            logTestInfo(wDriver, "verification started on expanding Miscellaneous section");
            verify_ExpandCollapse("auMiscellaneous", "equipment-MiscellaneousAvailable", "show");
            Thread.sleep(3000);
        }
        catch (Exception e) {
            logTestInfo(wDriver, "Problem finding elements " + e.getMessage());
        }

    }

    @Test(description = "Pending Load section expande/collapse")
    public void pendingLoad_Expand_Collapse() throws InterruptedException, IOException {
        setEquipmentLocationForTest("Pending","PendingLoad",2);
        location = EquipmentData.equipmentSendingLocation;
        logTestInfo(wDriver, "Executing Test Name:   " + Thread.currentThread().getStackTrace()[1].getMethodName());

        extentTest.assignCategory("Regression", "Pending Load expande/collapse");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 3 - Verifying  Pending Load group collapse-expand functionality");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 3 - Verifying  Pending Load group collapse-expand functionality");

        //line below will close personnel menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Personnel" );
        //line below will close task menu to expand equipment panel
        CommonActions.clickOnMenuIcon( wDriver, "Task" );
        //code below will check expand/collapse functionality for Miscellaneous section
        try {
            logTestInfo(wDriver, "verification started on collapsing Pending Load section");
            verify_ExpandCollapse("auPendingLoad", "equipment-PendingLoadPending", "hide");
            Thread.sleep(3000);
            logTestInfo(wDriver, "verification started on expanding Pending Load section");
            verify_ExpandCollapse("auPendingLoad", "equipment-PendingLoadPending", "show");
        }
        catch (Exception e) {
            logTestInfo(wDriver, "Problem finding elements " + e.getMessage());
        }

    }


}
