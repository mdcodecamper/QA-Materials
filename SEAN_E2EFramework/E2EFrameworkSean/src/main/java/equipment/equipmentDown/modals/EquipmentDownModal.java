package equipment.equipmentDown.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.data.LoginData;
import common.pages.EquipmentDetailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import task.taskDetails.modals.TaskDetailsModal;
import utilities.Utilities;

import java.io.IOException;
import java.util.List;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.CommonActions.logTestPass;
import static common.actions.EquipmentActions.equipmentSubmitAndVerify;
import static common.actions.EquipmentActions.equipmentSubmitAndVerify_Negative;

/**
 * Created by skashem on 10/12/2016.
 */
public class EquipmentDownModal extends AbstractStartWebDriver {
    ExtentTest extentTest;

    public EquipmentDownModal(WebDriver wDriver) {
        super();
        PageFactory.initElements( wDriver, this );
        this.extentTest = super.extentTest;
    }

    @FindBy(css = ".auEquipmentDownPanel .form-title")
    @CacheLookup
    public static WebElement PanelHeader;
    @FindBy(css = "auEquipmentDownPanel .form-equipment-name")
    @CacheLookup
    public static WebElement EquipmentId;
    /*@FindAll(@FindBy(css = ".ui-select-match"))
    @CacheLookup public static List<WebElement> DropDown;
    @FindBy(css = "ng-select > div > input")
    @CacheLookup public static WebElement SearchCodeField;
    @FindAll(@FindBy(css = "ng-select > div > ul"))*/
    @FindBy(xpath = "//*[contains(@class,'auDownCode1')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup
    public static WebElement DownCode1Button;
    @FindBy(xpath = "//*[contains(@class,'auDownCode1')]//*[contains(@class,'search auText')]")
    @CacheLookup
    public static WebElement DownCode1Search;
    @FindBy(xpath = "//*[contains(@class,'auServiceLocation1')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup
    public static WebElement ServiceLocaion1Button;
    @FindBy(xpath = "//*[contains(@class,'auServiceLocation1')]//*[contains(@class,'search auText')]")
    @CacheLookup
    public static WebElement ServiceLocation1Search;
    @FindBy(xpath = "//*[contains(@class,'auDownTime1')]//*[contains(@class,'auHour')]")
    @CacheLookup
    public static WebElement Downhour1;
    @FindBy(xpath = "//*[contains(@class,'auDownTime1')]//*[contains(@class,'auMinute')]")
    @CacheLookup
    public static WebElement DownMinute1;
    @FindBy(xpath = "//*[contains(@class,'auReporter1')]")
    @CacheLookup
    public static WebElement Reporter1;
    @FindBy(xpath = "//*[contains(@class,'auMechanic1')]")
    @CacheLookup
    public static WebElement Mechanic1;
    @FindBy(xpath = "//*[contains(@class,'auRemarks1')]")
    @CacheLookup
    public static WebElement Remarks1;

    @FindBy(xpath = "//*[contains(@class,'auDownCode2')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup
    public static WebElement DownCode2Button;
    @FindBy(xpath = "//*[contains(@class,'auDownCode2')]//*[contains(@class,'search auText')]")
    @CacheLookup
    public static WebElement DownCode2Search;
    @FindBy(xpath = "//*[contains(@class,'auServiceLocation2')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup
    public static WebElement ServiceLocaion2Button;
    @FindBy(xpath = "//*[contains(@class,'auServiceLocation2')]//*[contains(@class,'search auText')]")
    @CacheLookup
    public static WebElement ServiceLocation2Search;
    @FindBy(xpath = "//*[contains(@class,'auDownTime2')]//*[contains(@class,'auHour')]")
    @CacheLookup
    public static WebElement Downhour2;
    @FindBy(xpath = "//*[contains(@class,'auDownTime2')]//*[contains(@class,'auMinute')]")
    @CacheLookup
    public static WebElement DownMinute2;
    @FindBy(xpath = "//*[contains(@class,'auReporter2')]")
    @CacheLookup
    public static WebElement Reporter2;
    @FindBy(xpath = "//*[contains(@class,'auMechanic2')]")
    @CacheLookup
    public static WebElement Mechanic2;
    @FindBy(xpath = "//*[contains(@class,'auRemarks2')]")
    @CacheLookup
    public static WebElement Remarks2;

    @FindBy(xpath = "//*[contains(@class,'auDownCode3')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup
    public static WebElement DownCode3Button;
    @FindBy(xpath = "//*[contains(@class,'auDownCode3')]//*[contains(@class,'search auText')]")
    @CacheLookup
    public static WebElement DownCode3Search;
    @FindBy(xpath = "//*[contains(@class,'auServiceLocation3')]//*[contains(@class,'btn btn-default')]")
    @CacheLookup
    public static WebElement ServiceLocaion3Button;
    @FindBy(xpath = "//*[contains(@class,'auServiceLocation3')]//*[contains(@class,'search auText')]")
    @CacheLookup
    public static WebElement ServiceLocation3Search;
    @FindBy(xpath = "//*[contains(@class,'auDownTime3')]//*[contains(@class,'auHour')]")
    @CacheLookup
    public static WebElement Downhour3;
    @FindBy(xpath = "//*[contains(@class,'auDownTime3')]//*[contains(@class,'auMinute')]")
    @CacheLookup
    public static WebElement DownMinute3;
    @FindBy(xpath = "//*[contains(@class,'auReporter3')]")
    @CacheLookup
    public static WebElement Reporter3;
    @FindBy(xpath = "//*[contains(@class,'auMechanic3')]")
    @CacheLookup
    public static WebElement Mechanic3;
    @FindBy(xpath = "//*[contains(@class,'auRemarks3')]")
    @CacheLookup
    public static WebElement Remarks3;

    @FindAll(@FindBy(css = "ul > li > div > a"))
    @CacheLookup
    public static List<WebElement> ElementClick;

    @FindBy(css = "button.btn.btn-primary.auSubmit")
    @CacheLookup public static WebElement Submit;
    @FindBy(css = "button.btn.btn-secondary.auCancel")
    @CacheLookup public static WebElement Cancel;
    @FindBy(css = ".auConditionLabel")
    @CacheLookup public static WebElement ConditionLabel;
    @FindBy(css = ".auCondition.down")
    @CacheLookup public static WebElement ConditionValue;
    private String downDate = null;
    private String downHour = null;
    public EquipmentDownModal setDownCode1(String downCode1) {
        Reporter.log( "Entering Down Code 1....." + downCode1, true );
        if (downCode1 != null) {
            logTestInfo( wDriver, "Down Code 1 is set to " + downCode1 );
            DownCode1Button.click();
            DownCode1Search.sendKeys( downCode1 );
            if (ElementClick.size() > 0) {
                ElementClick.get( 0 ).click();
            }
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setRepairLocation1(String repairLocation1) {
        Reporter.log( "Entering Down Code 1....." + repairLocation1, true );
        if (repairLocation1 != null) {
            logTestInfo( wDriver, "Service Location 1 is set to " + repairLocation1 );
            ServiceLocaion1Button.click();
            ServiceLocation1Search.sendKeys( repairLocation1 );
            if (ElementClick.size() > 0) {
                ElementClick.get( 0 ).click();
            }
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setDownHour1(String downHour1) {
        Reporter.log( "Entering Down Hour 1....." + downHour1, true );
        if (downHour1 != null) {
            logTestInfo( wDriver, "Down Hour 1 is set to " + downHour1 );
            Downhour1.clear();
            Downhour1.sendKeys( downHour1 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setDownMinute1(String downMinute1) {
        Reporter.log( "Entering Down Minute 1....." + downMinute1, true );
        if (downMinute1 != null) {
            logTestInfo( wDriver, "Down Minute 1 is set to " + downMinute1 );
            DownMinute1.clear();
            DownMinute1.sendKeys( downMinute1 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setReporter1(String reporter1) {
        Reporter.log( "Entering Reporter 1....." + reporter1, true );
        if (reporter1 != null) {
            logTestInfo( wDriver, "Reporter 1 is set to " + reporter1 );
            Reporter1.clear();
            Reporter1.sendKeys( reporter1 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setMechanic1(String mechanic1) {
        Reporter.log( "Entering Mechanic 1....." + mechanic1, true );
        if (mechanic1 != null) {
            logTestInfo( wDriver, "Mechanic 1 is set to " + mechanic1 );
            Mechanic1.clear();
            Mechanic1.sendKeys( mechanic1 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setRemarks1(String remarks1) {
        Reporter.log( "Entering Remarks 1....." + remarks1, true );
        if (remarks1 != null) {
            logTestInfo( wDriver, "Remarks 1 is set to " + remarks1 );
            Remarks1.clear();
            Remarks1.sendKeys( remarks1 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setDownCode2(String downCode2) {
        Reporter.log( "Entering Down Code 2....." + downCode2, true );
        if (downCode2 != null) {
            logTestInfo( wDriver, "Down Code 2 is set to " + downCode2 );
            DownCode2Button.click();
            DownCode2Search.sendKeys( downCode2 );
            if (ElementClick.size() > 0) {
                ElementClick.get( 0 ).click();
            }
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setRepairLocation2(String repairLocation2) {
        Reporter.log( "Entering Down Code 2....." + repairLocation2, true );
        if (repairLocation2 != null) {
            logTestInfo( wDriver, "Service Location 2 is set to " + repairLocation2 );
            ServiceLocaion2Button.click();
            ServiceLocation2Search.sendKeys( repairLocation2 );
            if (ElementClick.size() > 0) {
                ElementClick.get( 0 ).click();
            }
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setDownHour2(String downHour2) {
        Reporter.log( "Entering Down Hour 2....." + downHour2, true );
        if (downHour2 != null) {
            logTestInfo( wDriver, "Down Hour 2 is set to " + downHour2 );
            Downhour2.clear();
            Downhour2.sendKeys( downHour2 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setDownMinute2(String downMinute2) {
        Reporter.log( "Entering Down Minute 2....." + downMinute2, true );
        if (downMinute2 != null) {
            logTestInfo( wDriver, "Down Minute 2 is set to " + downMinute2 );
            DownMinute2.clear();
            DownMinute2.sendKeys( downMinute2 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setReporter2(String reporter2) {
        Reporter.log( "Entering Reporter 2....." + reporter2, true );
        if (reporter2 != null) {
            logTestInfo( wDriver, "Reporter 2 is set to " + reporter2 );
            Reporter2.clear();
            Reporter2.sendKeys( reporter2 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setMechanic2(String mechanic2) {
        Reporter.log( "Entering Mechanic 2....." + mechanic2, true );
        if (mechanic2 != null) {
            logTestInfo( wDriver, "Mechanic 2 is set to " + mechanic2 );
            Mechanic2.clear();
            Mechanic2.sendKeys( mechanic2 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setRemarks2(String remarks2) {
        Reporter.log( "Entering Remarks 2....." + remarks2, true );
        if (remarks2 != null) {
            logTestInfo( wDriver, "Remarks 2 is set to " + remarks2 );
            Remarks2.clear();
            Remarks2.sendKeys( remarks2 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setDownCode3(String downCode3) {
        Reporter.log( "Entering Down Code 3....." + downCode3, true );
        if (downCode3 != null) {
            logTestInfo( wDriver, "Down Code 3 is set to " + downCode3 );
            DownCode3Button.click();
            DownCode3Search.sendKeys( downCode3 );
            if (ElementClick.size() > 0) {
                ElementClick.get( 0 ).click();
            }
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setRepairLocation3(String repairLocation3) {
        Reporter.log( "Entering Down Code 3....." + repairLocation3, true );
        if (repairLocation3 != null) {
            logTestInfo( wDriver, "Service Location 3 is set to " + repairLocation3 );
            ServiceLocaion3Button.click();
            ServiceLocation3Search.sendKeys( repairLocation3 );
            if (ElementClick.size() > 0) {
                ElementClick.get( 0 ).click();
            }
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setDownHour3(String downHour3) {
        Reporter.log( "Entering Down Hour 3....." + downHour3, true );
        if (downHour3 != null) {
            logTestInfo( wDriver, "Down Hour 3 is set to " + downHour3 );
            Downhour3.clear();
            Downhour3.sendKeys( downHour3 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setDownMinute3(String downMinute3) {
        Reporter.log( "Entering Down Minute 3....." + downMinute3, true );
        if (downMinute3 != null) {
            logTestInfo( wDriver, "Down Minute 3 is set to " + downMinute3 );
            DownMinute3.clear();
            DownMinute3.sendKeys( downMinute3 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setReporter3(String reporter3) {
        Reporter.log( "Entering Reporter 3....." + reporter3, true );
        if (reporter3 != null) {
            logTestInfo( wDriver, "Reporter 3 is set to " + reporter3 );
            Reporter3.clear();
            Reporter3.sendKeys( reporter3 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setMechanic3(String mechanic3) {
        Reporter.log( "Entering Mechanic 3....." + mechanic3, true );
        if (mechanic3 != null) {
            logTestInfo( wDriver, "Mechanic 3 is set to " + mechanic3 );
            Mechanic3.clear();
            Mechanic3.sendKeys( mechanic3 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDownModal setRemarks3(String remarks3) {
        Reporter.log( "Entering Remarks 3....." + remarks3, true );
        if (remarks3 != null) {
            logTestInfo( wDriver, "Remarks 3 is set to " + remarks3 );
            Remarks3.clear();
            Remarks3.sendKeys( remarks3 );
        }
        return new EquipmentDownModal( wDriver );
    }

    public EquipmentDetailPage clickSubmit() throws InterruptedException, IOException {
        downDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
        downHour = Utilities.get24HourFormat(0);
        wDriver.findElement( By.cssSelector( ".modal-header.auHeader" ) ).click();
        logTestInfo( wDriver, "Clicking Down Equipment Submit Button....." );
        equipmentSubmitAndVerify( wDriver, Submit, Cancel );

        return new EquipmentDetailPage( wDriver );

    }
    public EquipmentDetailPage clickSubmit_Negative() throws InterruptedException, IOException {
        downDate = Utilities.getDesiredDateInFormat(0, "MM/dd/yyyy");
        downHour = Utilities.get24HourFormat(0);
        wDriver.findElement( By.cssSelector( ".modal-header.auHeader" ) ).click();
        logTestInfo( wDriver, "Clicking Down Equipment Submit Button....." );
        equipmentSubmitAndVerify_Negative( wDriver, Submit, Cancel );

        return new EquipmentDetailPage( wDriver );

    }

    public EquipmentDownModal downCondition(String equipmentId) throws IOException, InterruptedException {
        smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
        logTestInfo( wDriver, "Verifying Down Condition label and value" );
        String conditionLabel = ConditionLabel.getText();
        //System.out.println("down condition label " + conditionLabel);
        if (conditionLabel.equals( "Condition" )) {
            logTestPass( wDriver, "Condition label '" + conditionLabel + "' appears as Expected" );
        } else {
            logTestFailure( wDriver, "Condition label is inaccurate" );
        }
        String conditionValue = ConditionValue.getText();
        //System.out.println("down condition value " + conditionValue);
        if (conditionValue.equals( "Down" )) {
            logTestPass( wDriver, "Equipment Condition value 'Down' appears as Expected" );
        } else {
            logTestFailure( wDriver, "Equipment Condition value is inaccurate" );
        }
        return new EquipmentDownModal( wDriver );

    }

    public EquipmentDownModal lastUpdate() {
        logTestInfo( wDriver, "Verifying last Update label and value" );
        String lastUpdateLabel = wDriver.findElement(By.cssSelector(".auLastUpdateLabel")).getText();
        if (lastUpdateLabel.equals( "Last Update" )) {
            logTestPass( wDriver, "Last Update label '" + lastUpdateLabel + "' appears as Expected" );
        } else {
            logTestFailure( wDriver, "Last Update label is inaccurate" );
        }
        String lastUpdateValue = wDriver.findElement(By.cssSelector("span.auLastUpdate")).getText();
        if (lastUpdateValue.contains(downDate + " " + downHour)) {
            logTestPass( wDriver, "Equipment Last Update value appears as Expected" );
        } else {
            logTestFailure( wDriver, "Equipment Last Update value is inaccurate" );
        }
        return new EquipmentDownModal( wDriver );

    }

    public EquipmentDownModal lastUpdatedBy() {
        logTestInfo( wDriver, "Verifying last Updated By label and value" );
        String lastUpdatedByLabel = wDriver.findElement(By.cssSelector(".auLastUpdatedByLabel")).getText();
        if (lastUpdatedByLabel.equals( "Last Update By" )) {
            logTestPass( wDriver, "Last Updated By label '" + lastUpdatedByLabel + "' appears as Expected" );
        } else {
            logTestFailure( wDriver, "Last Updated By label is inaccurate" );
        }
        String lastUpdatedByValue = wDriver.findElement(By.cssSelector("span.auLastUpdatedBy")).getText();
        if (lastUpdatedByValue.equals(LoginData.getLoginData("username"))) {
            logTestPass( wDriver, "Equipment Last Update By value appears as Expected - " + lastUpdatedByValue);
        } else {
            logTestFailure( wDriver, "Equipment Last Updated By value is inaccurate" );
        }
        return new EquipmentDownModal( wDriver );

    }


}// end of main class closure
