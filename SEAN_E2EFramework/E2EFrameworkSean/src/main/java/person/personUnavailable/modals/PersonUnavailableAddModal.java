package person.personUnavailable.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import common.pages.PersonDetailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.actions.PersonActions.personSubmitAndVerify;

/**
 * Created by srreddy on 10/11/2016.
 */
public class PersonUnavailableAddModal extends AbstractStartWebDriver {

    ExtentTest extentTest;

    public PersonUnavailableAddModal(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    @FindBy(css = ".auPersonnelUnavailableAddPanel .form-title")
    public static WebElement PanelHeader;
    @FindBy(css = ".auPersonnelUnavailableAddPanel .form-personnel-name")
    public static WebElement PersonCardName;
    @FindBy(css = ".auPersonnelUnavailableAddPanel .auUnavailableCode")
    public static WebElement UnavailableCodeDropdown;
    @FindBy(css = ".auPersonnelUnavailableAddPanel .auEffectiveDate")
    public static WebElement EffectiveDate;
    @FindBy(css = ".auPersonnelUnavailableAddPanel .auEndDate")
    public static WebElement EndDate;
    @FindBy(css = ".auPersonnelUnavailableAddPanel .auRemarks")
    public static WebElement Remarks;
    @FindBy(css = ".auSubmit")
    public static WebElement Submit;
    @FindBy(xpath = "//*[text()='Cancel']")
    public static WebElement Cancel;
    @FindAll(@FindBy(css = ".auPersonnelUnavailableAddPanel .auHour"))
    public static List<WebElement> HourWebElements;
    @FindAll(@FindBy(css = ".auPersonnelUnavailableAddPanel .auMinute"))
    public static List<WebElement> MinuteWebElements;
    public static WebElement EffectiveHour;
    public static WebElement EffectiveMinute;
    public static WebElement EndHour;
    public static WebElement EndMinute;


    /////////////////////////////////////////////////////////////////////////////////////////////


    public PersonUnavailableAddModal setMedicalDetailsFields(String[] medicalFieldValues) throws IOException {
        if (medicalFieldValues != null /*&& tempFieldValues.length == 8*/) {
            if (medicalFieldValues[0].length() > 0) {
                logTestInfo(wDriver, "Entering Symptoms: " + medicalFieldValues[0]);
                waitForElement(wDriver, wDriver.findElement(By.id("symptoms"))).sendKeys(medicalFieldValues[0]);
            }
            if (medicalFieldValues[1].length() > 0) {
                logTestInfo(wDriver, "Ordered to Clinic: " + medicalFieldValues[1]);
                WebElement Clinic = wDriver.findElement(By.id("clinic"));
                clickThenSelectDropDownByText(waitForElementThenDo(wDriver, Clinic), medicalFieldValues[1]);
            }
            if (medicalFieldValues[2].length() > 0) {
                logTestInfo(wDriver, "Going to Hospital: " + medicalFieldValues[2]);
                WebElement Hospital = wDriver.findElement(By.id("hospital"));
                clickThenSelectDropDownByText(waitForElementThenDo(wDriver, Hospital), medicalFieldValues[2]);
            }
            if (medicalFieldValues[3].length() > 0) {
                logTestInfo(wDriver, "Selecting Shift: " + medicalFieldValues[3]);
                WebElement Shift = wDriver.findElement(By.id("shift"));
                clickThenSelectDropDownByText(waitForElementThenDo(wDriver, Shift), medicalFieldValues[3]);
            }
            if (medicalFieldValues[4].length() > 0) {
                logTestInfo(wDriver, "Setting ML Address : " + medicalFieldValues[4]);
                WebElement MLAddress = wDriver.findElement(By.id("addressIndecator"));
                clickThenSelectDropDownByText(waitForElementThenDo(wDriver, MLAddress), medicalFieldValues[4]);
            } else {
                logTestFailure(wDriver, "No of values are NOT equal to required number of fields!!!");
            }
        }
        return new PersonUnavailableAddModal(wDriver);
    }


    public PersonUnavailableAddModal setTempAddressFields(String[] tempFieldValues) throws IOException {
        if (tempFieldValues != null /*&& tempFieldValues.length == 8*/) {
            if (tempFieldValues[0].length() > 0) {
                logTestInfo(wDriver, "Entering Street No: " + tempFieldValues[0]);
                waitForElement(wDriver, wDriver.findElement(By.cssSelector(".austreetNumber"))).sendKeys(tempFieldValues[0]);
            }
            if (tempFieldValues[1].length() > 0) {
                logTestInfo(wDriver, "Entering Apt: " + tempFieldValues[1]);
                waitForElement(wDriver, wDriver.findElement(By.cssSelector("#appartmentNumber"))).sendKeys(tempFieldValues[1]);
            }
            if (tempFieldValues[2].length() > 0) {
                logTestInfo(wDriver, "Entering City: " + tempFieldValues[2]);
                waitForElement(wDriver, wDriver.findElement(By.cssSelector("#city"))).sendKeys(tempFieldValues[2]);
            }
            if (tempFieldValues[3].length() > 0) {
                logTestInfo(wDriver, "Selecting State: " + tempFieldValues[3]);
                WebElement State = wDriver.findElement(By.cssSelector("#state"));
                clickThenSelectDropDownByText(waitForElementThenDo(wDriver, State), tempFieldValues[3]);
            }
            if (tempFieldValues[4].length() > 0) {
                logTestInfo(wDriver, "Entering ZipCode: " + tempFieldValues[4]);
                wDriver.findElement(By.cssSelector(".auzip")).sendKeys(tempFieldValues[4]);
            }
            if (tempFieldValues[5].length() > 0) {
                logTestInfo(wDriver, "Entering Home Phone: " + tempFieldValues[5]);
                wDriver.findElement(By.id("homephone")).sendKeys(tempFieldValues[5]);
            }
            if (tempFieldValues[6].length() > 0) {
                logTestInfo(wDriver, "Selecting District: " + tempFieldValues[6]);
                WebElement District = wDriver.findElement(By.cssSelector("#rDistrict"));
                clickThenSelectDropDownByText(waitForElementThenDo(wDriver, District), tempFieldValues[6]);
            }
            if (tempFieldValues[7].length() > 0) {
                logTestInfo(wDriver, "Selecting District: " + tempFieldValues[7]);
                WebElement Residence = wDriver.findElement(By.cssSelector("#rZone"));
                clickThenSelectDropDownByText(waitForElementThenDo(wDriver, Residence), tempFieldValues[7]);
            } else {
                logTestFailure(wDriver, "No of values are NOT equal to required number of fields!!!");
            }

        }
        return new PersonUnavailableAddModal(wDriver);
    }

    //------------------------------------------------------------------------------------------------
    public PersonUnavailableAddModal setUnavailableCode(String unavailableCode) throws IOException {
        if (unavailableCode != null) {
            logTestInfo(wDriver, "Selecting Unavailable Code : " + unavailableCode);
            clickThenSelectDropDownByText(waitForElementThenDo(wDriver, UnavailableCodeDropdown), unavailableCode);
        }
        return new PersonUnavailableAddModal(wDriver);
    }

    public PersonUnavailableAddModal setEffectiveDate(String effectiveDate) throws IOException {
        if (effectiveDate != null) {
            setDatePropertyTrue("startDate");
            logTestInfo(wDriver, "Entering Effective Date: " + effectiveDate);
            waitForElementThenDo(wDriver, EffectiveDate).sendKeys(effectiveDate);
        }
        return new PersonUnavailableAddModal(wDriver);
    }

    public PersonUnavailableAddModal setEffectiveTime(String effectiveHour, String effectiveMinute) throws IOException, InterruptedException {
        if (effectiveHour != null) {
            EffectiveHour = HourWebElements.get(0);
            logTestInfo(wDriver, "Entering Effective Hour: " + effectiveHour);
            waitForElementThenDo(wDriver, EffectiveHour).clear();
            waitForElementThenDo(wDriver, EffectiveHour).sendKeys(effectiveHour);
            Thread.sleep(300);
        }
        if (effectiveMinute != null) {
            EffectiveMinute = MinuteWebElements.get(0);
            logTestInfo(wDriver, "Entering End Minute: " + effectiveMinute);
            waitForElementThenDo(wDriver, EffectiveMinute).clear();
            waitForElementThenDo(wDriver, EffectiveMinute).sendKeys(effectiveMinute);
        }
        return new PersonUnavailableAddModal(wDriver);
    }

    public PersonUnavailableAddModal setEndDate(String endDate) throws IOException {
        if (endDate != null) {
            setDatePropertyTrue("endDate");
            logTestInfo(wDriver, "Entering End Date: " + endDate);
            waitForElementThenDo(wDriver, EndDate).sendKeys(endDate);
        }
        return new PersonUnavailableAddModal(wDriver);
    }

    public PersonUnavailableAddModal setEndTime(String endHour, String endMinute) throws IOException, InterruptedException {
        if (endHour != null) {
            EndHour = HourWebElements.get(1);
            logTestInfo(wDriver, "Entering End Hour: " + endHour);
            waitForElementThenDo(wDriver, EndHour).clear();
            waitForElementThenDo(wDriver, EndHour).sendKeys(endHour);
            Thread.sleep(300);
        }
        if (endMinute != null) {
            EndMinute = MinuteWebElements.get(1);
            logTestInfo(wDriver, "Entering End Minute: " + endMinute);
            waitForElementThenDo(wDriver, EndMinute).clear();
            waitForElementThenDo(wDriver, EndMinute).sendKeys(endMinute);
        }
        return new PersonUnavailableAddModal(wDriver);
    }

    public PersonUnavailableAddModal setRemarks(String remarks) throws IOException {
        if (remarks != null) {
            logTestInfo(wDriver, "Entered Remarks: " + remarks);
            waitForElementThenDo(wDriver, Remarks).sendKeys(remarks);
        }
        return new PersonUnavailableAddModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        logTestInfo(wDriver, "Clicking ADD UNAVAILABLE Submit Button");
        personSubmitAndVerify(wDriver, Submit);
        return new PersonDetailPage(wDriver);
    }


}
