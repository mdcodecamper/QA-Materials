package equipmentTestSuites.equipmentUpdateLoad;

import _driverScript.AbstractStartWebDriver;
import common.actions.CommonActions;
import common.actions.TaskActions;
import common.data.LoginData;
import org.testng.annotations.Test;
import utilities.Utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.clickOnDualGarageDropDown;
import static common.actions.CommonActions.logTestInfo;
import static equipment.abstractBase.EquipmentBasePage.*;
import static equipmentTestSuites.testData.EquipmentData.setEquipmentLocationForTest;
import static task.abstractBase.TaskBasePage.*;

/**
 * Created by skashem on 10/27/2016.
 */
public class TEST003_Minerva4030 extends AbstractStartWebDriver {

    private String location = "QE11";      //ReusableActions.equipmentSendingLocation; //"BKS10";
    private String boardDate = Utilities.getDesiredDateInFormat(0, "yyyyMMdd");
    private String loadDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
    private String exLoadDate = Utilities.getDesiredDateInFormat(0, "M/d/yyyy");
    private String loadHour = Utilities.get24HourFormat(0);
    private String newStatus1 = "Empty";
    private String material1 = null;
    private String shiftStartHour = Utilities.get24HourFormat(-2);
    private String shiftStartMinute = "00";
    private String shiftEndHour = Utilities.get24HourFormat(6);
    private String shiftEndMinute = "00";
    private String shift = shiftStartHour + shiftStartMinute + "-" + shiftEndHour + shiftEndMinute;
    private String url = LoginData.getLoginData(getUrl());


    @Test(description = "Equipment Update Load")
    public void equipmentUpdateLoad_Minerva4030() throws InterruptedException, IOException {
        extentTest.assignCategory("Smoke", "Update Load");
        System.out.println("**************************************************************************************");
        System.out.println("Scenario 1 - Update Load for single bin to empty which is assigned to a shift");
        System.out.println("**************************************************************************************");
        logTestInfo(wDriver, "Scenario 1 - Update Load for single bin to empty which is assigned to an active shift");

        //line below will open ops board
        loginPage().goToBoardLocationByDate(url, location + "/", boardDate);

        //line below will open QE11A dual garage board
        clickOnDualGarageDropDown(1);

        //line below will close person menu to expand task panel
        CommonActions.clickOnMenuIcon(wDriver, "Personnel");

        //line below will getEquipment from a category
        String equipmentId = equipmentPanelUtilities().getEquipmentFromCategory("Bin", "Available", "RearLoaders");

        //line below will delete any shift that has task(s) before adding a new shift on QE11W
        TaskActions.deleteAllShiftWithTask(wDriver, "QE11W");

        //line below will delete any shift that has task(s) before adding a new shift on QE11A
        TaskActions.deleteAllShiftWithTask(wDriver, "QE11A");

        //line below will add a shift
        taskAddShiftActions().addShift("QE11A", shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute);

        //line below will add tasks to cleaning baskets - regular subcategory
        taskAddTaskActions().addTasks("QE11A", shift, "SanitationWorker", "Cleaning", "Baskets - Regular", "3", 1);

        //line below will assign equipment to task 1 on cleaning baskets - regular
        taskAssignActions().assignEquipmentToTask("QE11A", equipmentId, shift, "CLEAN", "BASKETS_REG", null, 0);

        logTestInfo(wDriver, "verification started on location " + location + "A before Update Load");
        wDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        //line below will return available category count before update Load
        String availableCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Available", null, null);
        //line below will return available rear loaders category count before Update Load
        String rearLoadersCountBefore = equipmentPanelUtilities().getEquipmentCategoryCount("Available", "RearLoaders", null);
        //line below will verify equipment present in cleaning baskets - regular subcategory on task 1 before update load

        taskModeUtilities().verifyCardPresentOnTask(true, "QE11A", "Equipment", equipmentId, shift, "CLEAN", "BASKETS_REG", 0);
        //line below will check if equipment color is as expected before update load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Task","navyBlue");
        //line below will check if equipment text color is as expected before update load
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId, "black");
        //line below will check equipment single bin type is present before update load
        equipmentPanelUtilities().equipmentBinType(equipmentId, "E", null);
        loadHour = Utilities.get24HourFormat(0);
        //line below will perform Load Update
        equipmentUpdateLoadActions().updateLoad(equipmentId, newStatus1, null, null, null);
        //line below will open QE11W dual garage board
        clickOnDualGarageDropDown(0);
        //line below will open QE11A dual garage boardclickOnDualGarageDropDown(1);
        clickOnDualGarageDropDown(1);

        logTestInfo(wDriver, "verification started on location " + location + "A after Update Load");
        //line below will return available count after Update Load
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(0, availableCountBefore, "Available", null, null);
        //line below will return rear loaders count after Update Load
        equipmentPanelUtilities().getEquipmentCategoryCountAfter(0, rearLoadersCountBefore, "Available", "RearLoaders", null);
        //line below will check if equipment is not present in rear loaders category after Update Load
        equipmentPanelUtilities().verifyEquipmentPresent("Available", "RearLoaders", null, equipmentId, "false");
        //line below will verify equipment present in cleaning baskets - regular subcategory on task 1 after update load
        taskModeUtilities().verifyCardPresentOnTask(true, "QE11A", "Equipment", equipmentId, shift, "CLEAN", "BASKETS_REG", 0);
        //line below will check if equipment color is as expected after update load
        equipmentColorUtilities().verifyEquipmentCardColor(equipmentId, "Task","navyBlue");
        //line below will check if equipment text color is as expected after update load
        equipmentColorUtilities().verifyEquipmentTextColor(equipmentId, "black");
        //line below will check equipment single bin type is present after update load
        equipmentPanelUtilities().equipmentBinType(equipmentId, "E", null);
        //line below will verify Update Load Status history after Update Load
        equipmentHistoryUtilities().getEquipmentLoadStatusHistory(equipmentId, "1", "Load", "EMPTY", "", exLoadDate + " " + loadHour + ":", LoginData.getLoginData("username"));

    }


}
