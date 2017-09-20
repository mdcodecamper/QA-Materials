package person.personUnavailable.modals;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import common.pages.PersonDetailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;

import static common.actions.CommonActions.logTestInfo;
import static common.actions.PersonActions.personSubmitAndVerify;

/**
 * Created by srreddy on 10/19/2016.
 */
public class PersonUnavailableEditModal extends AbstractStartWebDriver {

    ExtentTest extentTest;

    public PersonUnavailableEditModal (WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    @FindBy(css = ".auPersonnelUnavailableUpdatePanel .form-title")
    public static WebElement PanelHeader;
    @FindBy(css = ".auPersonnelUnavailableUpdatePanel .form-personnel-name")
    public static WebElement PersonName;
    @FindBy(css = ".auPersonnelUnavailableUpdatePanel .auUnavailableCode")
    public static WebElement UnavailableCodeDropdown;
    @FindBy(css = ".auPersonnelUnavailableUpdatePanel .auEffectiveDate")
    public static WebElement EffectiveDate;
    @FindBy(css = ".auPersonnelUnavailableUpdatePanel .auEndDate")
    public static WebElement EndDate;
    @FindBy(css = ".auPersonnelUnavailableUpdatePanel .auRemarks")
    public static WebElement Remarks;
    @FindBy(css = ".auSubmit")
    public static WebElement Submit;
    @FindBy(xpath = "//*[text()='Cancel']")
    public static WebElement Cancel;
    @FindAll(@FindBy(css = ".auPersonnelUnavailableUpdatePanel .auHour"))
    public static List<WebElement> HourWebElements;
    @FindAll(@FindBy(css = ".auPersonnelUnavailableUpdatePanel .auMinute"))
    public static List<WebElement> MinuteWebElements;
    public static WebElement EffectiveHour;
    public static WebElement EffectiveMinute;
    public static WebElement EndHour;
    public static WebElement EndMinute;

    public PersonUnavailableEditModal setUnavailableCode(String unavailableCode) throws IOException {
        if (unavailableCode != null) {
            logTestInfo(wDriver, "Selecting Unavailable Code : " + unavailableCode);
            clickThenSelectDropDownByText(waitForElementThenDo(wDriver, UnavailableCodeDropdown), unavailableCode);
        }
        return new PersonUnavailableEditModal(wDriver);
    }

    public PersonUnavailableEditModal setEffectiveDate(String effectiveDate) throws IOException {
        if (effectiveDate != null) {
            setDatePropertyTrue("startDate");
            logTestInfo(wDriver, "Entering Effective Date: " + effectiveDate);
            //waitForElementThenDo(wDriver, EffectiveDate).clear();
            waitForElementThenDo(wDriver, EffectiveDate).sendKeys(effectiveDate);
        }
        return new PersonUnavailableEditModal(wDriver);
    }

    public PersonUnavailableEditModal setEffectiveTime(String effectiveHour, String effectiveMinute) throws IOException, InterruptedException {
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
        return new PersonUnavailableEditModal(wDriver);
    }

    public PersonUnavailableEditModal setEndDate(String endDate) throws IOException {
        if (endDate != null) {
            setDatePropertyTrue("endDate");
            logTestInfo(wDriver, "Entering End Date: " + endDate);
            //waitForElementThenDo(wDriver, EndDate).clear();
            waitForElementThenDo(wDriver, EndDate).sendKeys(endDate);
        }
        return new PersonUnavailableEditModal(wDriver);
    }

    public PersonUnavailableEditModal setEndTime(String endHour, String endMinute) throws IOException, InterruptedException {
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
        return new PersonUnavailableEditModal(wDriver);
    }

    public PersonUnavailableEditModal setRemarks(String remarks) throws IOException {
        if (remarks != null) {
            logTestInfo(wDriver, "Entered Remarks: " + remarks);
            waitForElementThenDo(wDriver, Remarks).clear();
            waitForElementThenDo(wDriver, Remarks).sendKeys(remarks);
        }
        return new PersonUnavailableEditModal(wDriver);
    }

    public PersonDetailPage clickSubmit() throws InterruptedException, IOException {
        logTestInfo(wDriver, "Clicking EDIT Unavailable Submit Button");
        personSubmitAndVerify(wDriver, Submit);
        return new PersonDetailPage(wDriver);
    }

}
