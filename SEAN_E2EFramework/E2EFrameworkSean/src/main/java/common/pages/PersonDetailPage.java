package common.pages;


import _driverScript.AbstractStartWebDriver;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import person.personDetach.modals.PersonDetachAddModal;
import person.personDetach.modals.PersonDetachDeleteModal;
import person.personDetach.modals.PersonDetachEditModal;
import person.personMda.modals.PersonMdaAddModal;
import person.personMda.modals.PersonMdaDeleteModal;
import person.personMda.modals.PersonMdaEditModal;
import person.personUnavailable.modals.PersonUnavailableAddModal;
import person.personUnavailable.modals.PersonUnavailableDeleteModal;
import person.specialPosition.modals.PersonSpecialPositionAddModal;
import person.specialPosition.modals.PersonSpecialPositionDeleteModal;
import person.specialPosition.modals.PersonSpecialPositionEditModal;
import utilities.Utilities;

import java.io.IOException;
import java.util.List;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestPass;

/**
 * Created by sdas on 9/23/2016.
 */
public class PersonDetailPage extends AbstractStartWebDriver {
    public PersonDetailPage(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }

    // LOCATORS
    @FindBy(css = ".auActionButton")
    WebElement ActionButton;
    @FindBy(css = ".auActionMenu .auAddMDA")
    WebElement AddMDA;
    @FindAll(@FindBy(css = ".auMdaHist .auEdit"))
    List<WebElement> EditMDA;
    @FindAll(@FindBy(css = ".auMdaHist .auDelete"))
    List<WebElement> DeleteMDA;
    @FindBy(css = ".auActionMenu .auActionAddSpecialPosition")
    WebElement AddSpecialPos;
    @FindAll(@FindBy(css = ".auSpecialPositionHist .auEdit"))
    List<WebElement> EditSpecialPos;
    @FindAll(@FindBy(css = ".auSpecialPositionHist .auDelete"))
    List<WebElement> DeleteSpecialPos;
    @FindBy(css = ".auActionMenu .auActionAddDetach")
    WebElement AddDetach;
    @FindAll(@FindBy(css = ".auDetachmentHist .auEdit"))
    List<WebElement> EditDetach;
    @FindAll(@FindBy(css = ".auDetachmentHist .auDelete"))
    List<WebElement> DeleteDetach;
    @FindBy(css = ".auPersonnelDetail .auActionClose")
    WebElement DetailClose;
    @FindBy(css = ".auActionMenu .auAddUnavailable")
    WebElement AddUnavailable;
    @FindAll(@FindBy(css = ".auUnavailabilityHist .auEdit"))
    List<WebElement> EditUnavailable;
    @FindAll(@FindBy(css = ".auUnavailabilityHist .auDelete"))
    List<WebElement> DeleteUnavailable;




    //======================================================================================
    // SPECIAL POSITIONS ACTIONS/////////////////////////
    public PersonDetachAddModal getDetachAddPanel() throws IOException {
        Reporter.log("Opening PersonDetachAddModal..... ", true);
        try {
           waitForElementThenDo(wDriver, ActionButton, 30).click();
           waitForElementThenDo(wDriver, AddDetach, 30).click();
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();

        }
        return new PersonDetachAddModal(wDriver);
    }

    public PersonDetachEditModal getDetachEditPanel(int rowIndex) throws IOException {
        Reporter.log("Opening PersonDetachEditModal..... ", true);
        try {
         Utilities.waitForElementThenDo(wDriver, EditDetach.get(rowIndex), 30).click();
         //   PersonHistoryUtilities.getEditOrDeleteIconFromTable("Detach", "auEdit").click();
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();

        }
        return new PersonDetachEditModal(wDriver);
    }

    public PersonDetachDeleteModal getDetachDeletePanel(int rowIndex) throws IOException {
        Reporter.log("Opening PersonDetachDeleteModal..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, DeleteDetach.get(rowIndex), 5).click();
            //PersonHistoryUtilities.getEditOrDeleteIconFromTable("Detach", "auDelete").click();
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();
        }
        return new PersonDetachDeleteModal(wDriver);
    }


    // SPECIAL POSITIONS ACTIONS//////////////////////////
    public PersonSpecialPositionAddModal getSpecialPositionAddPanel() throws IOException {
        Reporter.log("Opening Special Position ADD Panel..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, ActionButton, 30).click();
            Utilities.waitForElementThenDo(wDriver, AddSpecialPos, 30).click();
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();
        }
        return new PersonSpecialPositionAddModal(wDriver);
    }

    public PersonSpecialPositionEditModal getSpecialPositionEditPanel(int rowIndex) throws IOException {
        Reporter.log("Opening Special Position ADD Panel EDIT..... ", true);
        try {
            for(WebElement ele : EditSpecialPos){
                if(ele.isDisplayed() && !ele.getAttribute("class").contains("hidden")){
                    ele.click();
                    break;
                }
            }
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();
        }
        return new PersonSpecialPositionEditModal(wDriver);
    }

    public PersonSpecialPositionDeleteModal getSpecialPositionDeletePanel(int rowIndex) throws IOException {
        Reporter.log("Opening Special Position DELETE Panel..... ", true);
        try {
            for(WebElement ele : DeleteSpecialPos){
                if(ele.isDisplayed() && !ele.getAttribute("class").contains("hidden")){
                    ele.click();
                break;
                }
            }
            //Utilities.waitForElementThenDo(wDriver, DeleteSpecialPos.get(rowIndex), 30).click();
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();
        }
        return new PersonSpecialPositionDeleteModal(wDriver);
    }

    ///MDA RELATED ACTIONS/////////////////////////////

    public PersonMdaAddModal getMdaAddPanel() throws IOException {
        Reporter.log("Opening MDA ADD Panel..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, ActionButton).click();
            Utilities.waitForElementThenDo(wDriver, AddMDA, 30).click();

        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();
        }
        return new PersonMdaAddModal(wDriver);
    }

    public PersonMdaEditModal getMdaEditPanel(int rowIndex) throws IOException {
        Reporter.log("Opening MDA EDIT Panel..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, EditMDA.get(rowIndex), 30).click();
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();
        }
        return new PersonMdaEditModal(wDriver);
    }

    public PersonMdaDeleteModal getMdaDeletePanel(int rowIndex) throws IOException {
        Reporter.log("Opening MDA DELETE Panel..... ", true);
        try {
            Utilities.waitForElementThenDo(wDriver, DeleteMDA.get(rowIndex), 30).click();
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();
        }
        return new PersonMdaDeleteModal(wDriver);
    }
    public SmartBoardPage closePersonCardDetailPanel() throws IOException {
        Reporter.log("Closing Detail Panel..... ", true);
        try {
            //wDriver.findElements(By.xpath("//img[contains(@class, 'auActionClose')]")).get(1).click();
            Utilities.waitForElementThenDo(wDriver, DetailClose, 5).click();
        } catch (Exception e) {
            iconNotFoundError();
            e.toString();
            Assert.fail();
        }
        return new SmartBoardPage(wDriver);
    }

    ///UNAVAILBLE RELATED ACTIONS/////////////////////////////

    public PersonUnavailableAddModal getUnavailableAddPanel() throws IOException {
        Reporter.log("Opening ADD Unavailable Panel..... ", true);
        logTestPass(wDriver, "Opening ADD Unavailable Panel");
        try {
            Utilities.waitForElementThenDo(wDriver, ActionButton).click();
            Utilities.waitForElementThenDo(wDriver, AddUnavailable, 30).click();
        } catch (Exception e) {
            logTestFailure (wDriver, "Could not open ADD Unavailable Panel");
            e.toString();
            Assert.fail();
        }

        return new PersonUnavailableAddModal(wDriver);
    }

    public PersonMdaEditModal getUnavailableEditModal(int rowIndex) throws IOException {
        Reporter.log("Opening Unavailable EDIT Panel..... ", true);
        try {
            for(WebElement edit: EditUnavailable){
                if(edit.isDisplayed() && !edit.getAttribute("class").contains("hidden")){
                    edit.click();
                    break;
                }
            }
        } catch (Exception e) {
            iconNotFoundError();
        }
        return new PersonMdaEditModal(wDriver);
    }

    public PersonUnavailableDeleteModal getUnavailableDeleteModal(int rowIndex) throws IOException {
        logTestPass(wDriver, "Opening Unavailable Delete Panel..... ");
        try {
            Utilities.waitForElementThenDo(wDriver, DeleteUnavailable.get(rowIndex), 30).click();
           /* for(WebElement delete: DeleteUnavailable){
                if(delete.isDisplayed() && delete.isEnabled() && !delete.getAttribute("class").contains("hidden")){
                    delete.click();
                    break;
                    }
                }*/

        } catch (NoSuchElementException e) {
            iconNotFoundError();
        }

        return new PersonUnavailableDeleteModal(wDriver);
    }


    public static void iconNotFoundError() throws IOException {
        logTestFailure(wDriver, "NO Clickable Icon is found at the location!!!");
    }

}
