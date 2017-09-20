package person.expectedResults.panels;

import _driverScript.AbstractStartWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static person.expectedResults.panels.PersonHistoryUtilities.getUnavailHistEditablePage;

/**
 * Created by sdas on 2/1/2017.
 */
public class UnavailableModalDetails extends AbstractStartWebDriver {
    public UnavailableModalDetails(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }

    public static String getDetachLocFromUnvailModal(String personCardName) {
        try {
            smartBoardPage().openPersonCardDetailPanel(personCardName);
            getUnavailHistEditablePage(personCardName);
            personDetailPanelPage().getUnavailableEditModal(0);
            String detachLoc = jsExecGetValueById(wDriver, "detachLocation");
            logTestInfo(wDriver, "Detach Location Found is: " + detachLoc);
            wDriver.findElement(By.xpath("//*[text() = 'Cancel']")).click();
            return detachLoc;
        } catch (Exception e) {
            logTestInfo(wDriver, "Could Not Retrieve Detach Location Value!");
            return "Could Not Retrieve Detach Location Value!";
        }
    }

    public static String getInformationIconMsg(String cardName, int index) throws InterruptedException, IOException {
        String infoMsg = null;
        smartBoardPage().openPersonCardDetailPanel(cardName);
        personDetailPanelPage().getUnavailableEditModal(index);
        moveToAnElement(wDriver, wDriver.findElements(By.xpath("//img[@src='/ui/assets/icons/info.png']")).get(index));
        List<WebElement> list = wDriver.findElements(By.xpath("//span[contains(@class, 'collapsed')]"));
        infoMsg = waitForElement(wDriver, list.get(0), 15).getText();
        Thread.sleep(500);
        moveToAnElement(wDriver, wDriver.findElement(By.cssSelector(".auCancel")));
        waitForElementThenDo(wDriver, wDriver.findElement(By.cssSelector(".auCancel")), 1).click();
        //wDriver.findElement(By.xpath("//*[text()='Cancel']")).click();
        //wDriver.findElement(By.cssSelector(".modal-body.auCancel")).click();


        return infoMsg;
    }


    public static String[] unavailEmployeeDetails(String personCardName) throws InterruptedException, IOException {
        LinkedList<String> employeeDetailsList = new LinkedList<>();
        smartBoardPage().openPersonCardDetailPanel(personCardName);
        getUnavailHistEditablePage(personCardName);
        personDetailPanelPage().getUnavailableEditModal(0);
        employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auCivilServiceTitle")).getText());
        employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auReferenceNumber")).getText());
        employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auAddress")).getText());
        employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auPayrollLocationID")).getText());
        employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auPhoneHome")).getText());
        employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auChartName")).getText());
        //employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auPhoneEmergency")).getText());
        employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auChartNo")).getText());
        //employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auCategoryLabel")).getText());
        //employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auDutyStatusNo")).getText());
        //employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auResidenceZone")).getText());
        //employeeDetailsList.add(wDriver.findElement(By.cssSelector(".auResidenceDistrict")).getText());

        logTestInfo(wDriver, "Employee Details Found on Modal: " + employeeDetailsList);
        wDriver.findElement(By.xpath("//*[text()='Cancel']")).click();

        return employeeDetailsList.toArray(new String[employeeDetailsList.size()]);
    }

    public static String[] getMedicalDetails(String personCardName) throws InterruptedException, IOException {
        LinkedList<String> medicalDetailsList = new LinkedList<>();
        smartBoardPage().openPersonCardDetailPanel(personCardName);
        getUnavailHistEditablePage(personCardName);
        personDetailPanelPage().getUnavailableEditModal(0);
        medicalDetailsList.add(jsExecGetValueById(wDriver, "symptoms"));
        medicalDetailsList.add(jsExecGetValueById(wDriver, "clinic"));
        medicalDetailsList.add(jsExecGetValueById(wDriver, "hospital"));
        medicalDetailsList.add(jsExecGetValueById(wDriver, "shift"));
        medicalDetailsList.add(jsExecGetValueById(wDriver, "detachLocation"));
        medicalDetailsList.add(jsExecGetValueById(wDriver, "addressIndecator"));
        medicalDetailsList.add(jsExecGetValueById(wDriver, "trialDate"));
        logTestInfo(wDriver, "Medical Details Found on Modal: " + medicalDetailsList);
        wDriver.findElement(By.xpath("//*[text()='Cancel']")).click();
        return medicalDetailsList.toArray(new String[medicalDetailsList.size()]);
    }

    public static String[] tempAddressDetails(String personCardName) throws InterruptedException, IOException {
        LinkedList<String> tempAddressDetailsList = new LinkedList<>();
        smartBoardPage().openPersonCardDetailPanel(personCardName);
        getUnavailHistEditablePage(personCardName);
        personDetailPanelPage().getUnavailableEditModal(0);
        tempAddressDetailsList.add(jsExecGetValueById(wDriver, "streetNumber"));
        tempAddressDetailsList.add(jsExecGetValueById(wDriver, "appartmentNumber"));
        tempAddressDetailsList.add(jsExecGetValueById(wDriver, "city"));
        tempAddressDetailsList.add(jsExecGetValueById(wDriver, "state"));
        tempAddressDetailsList.add(jsExecGetValueById(wDriver, "zip"));
        tempAddressDetailsList.add(jsExecGetValueById(wDriver, "homephone"));
        tempAddressDetailsList.add(jsExecGetValueById(wDriver, "rDistrict"));
        tempAddressDetailsList.add(jsExecGetValueById(wDriver, "rZone"));
        logTestInfo(wDriver, "Temporary Address Details Found on Modal: " + tempAddressDetailsList);
        wDriver.findElement(By.xpath("//*[text()='Cancel']")).click();
        return tempAddressDetailsList.toArray(new String[tempAddressDetailsList.size()]);
    }


}
