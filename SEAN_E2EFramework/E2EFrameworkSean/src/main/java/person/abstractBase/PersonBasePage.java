package person.abstractBase;

import _driverScript.AbstractStartWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import person.personDetach.modals.PersonDetachAddModal;
import person.personDetach.modals.PersonDetachDeleteModal;
import person.personDetach.modals.PersonDetachEditModal;
import person.personMda.modals.PersonMdaAddModal;
import person.personMda.modals.PersonMdaDeleteModal;
import person.personMda.modals.PersonMdaEditModal;
import person.personUnavailable.modals.PersonPanelModal;
import person.personUnavailable.modals.PersonUnavailableAddModal;
import person.personUnavailable.modals.PersonUnavailableDeleteModal;
import person.personUnavailable.modals.PersonUnavailableEditModal;
import person.specialPosition.modals.PersonSpecialPositionAddModal;
import person.specialPosition.modals.PersonSpecialPositionDeleteModal;
import person.specialPosition.modals.PersonSpecialPositionEditModal;

/**
 * Created by sdas on 10/8/2016.
 */
public abstract class PersonBasePage extends AbstractStartWebDriver {
    public PersonBasePage(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }

    // Object for PersonDetachAddPage
    public static PersonDetachAddModal personDetachAddModal() {
        PersonDetachAddModal personDetachAddModal = new PersonDetachAddModal(wDriver);
        return personDetachAddModal;
    }

    // Object for PersonDetachEditPage
    public static PersonDetachEditModal personDetachEditModal() {
        PersonDetachEditModal personDetachEditModal = new PersonDetachEditModal(wDriver);
        return personDetachEditModal;
    }

    // Object for PersonDetachEditPage
    public static PersonDetachDeleteModal personDetachDeleteModal() {
        PersonDetachDeleteModal personDetachDeleteModal = new PersonDetachDeleteModal(wDriver);
        return personDetachDeleteModal;
    }

    //// ALL MDA ////////////////////////////////
    // Object for PersonMdaAddPage
    public static PersonMdaAddModal personMdaAddModal() {
        PersonMdaAddModal personMdaAddModal = new PersonMdaAddModal(wDriver);
        return personMdaAddModal;
    }

    // Object for PersonMdaEditPage
    public static PersonMdaEditModal personMdaEditModal() {
        PersonMdaEditModal personMdaEditModal = new PersonMdaEditModal(wDriver);
        return personMdaEditModal;
    }

    // Object for PersonMdaDeletePage
    public static PersonMdaDeleteModal personMdaDeleteModal() {
        PersonMdaDeleteModal personMdaDeleteModal = new PersonMdaDeleteModal(wDriver);
        return personMdaDeleteModal;
    }

    //// ALL SPECIAL POSITION /////////////////////
    // Object for PersonSpecialPositionAddPage
    public static PersonSpecialPositionAddModal personSpecialPostionAddModal() {
        PersonSpecialPositionAddModal personSpecialPostionAddModal = new PersonSpecialPositionAddModal(wDriver);
        return personSpecialPostionAddModal;
    }

    // Object for PersonSpecialPositionEditPage
    public static PersonSpecialPositionEditModal personSpecialPositionEditModal() {
        PersonSpecialPositionEditModal personSpecialPositionEditModal = new PersonSpecialPositionEditModal(wDriver);
        return personSpecialPositionEditModal;
    }

    // Object for PersonSpecialPositionEditPage
    public static PersonSpecialPositionDeleteModal personSpecialPositionDeleteModal() {
        PersonSpecialPositionDeleteModal personSpecialPositionDeleteModal = new PersonSpecialPositionDeleteModal(wDriver);
        return personSpecialPositionDeleteModal;
    }

    //// ALL Unavailable ////////////////////////////////
        // Object for PersonUnavailableAddPage
        public static PersonUnavailableAddModal personUnavailableAddModal() {
            PersonUnavailableAddModal personUnavailableAddModal = new PersonUnavailableAddModal(wDriver);
            return personUnavailableAddModal;
        }

       // Object for PersonUnavailableEditPage
        public static PersonUnavailableEditModal personUnavailableEditModal() {
            PersonUnavailableEditModal personUnavailableEditModal = new PersonUnavailableEditModal(wDriver);
            return personUnavailableEditModal;
        }

        // Object for PersonUnavailableDeletePage
        public static PersonUnavailableDeleteModal personUnavailableDeleteModal() {
            PersonUnavailableDeleteModal personUnavailableDeleteModal = new PersonUnavailableDeleteModal(wDriver);
            return personUnavailableDeleteModal;
        }

    // Object for PersonUnavailableDeletePage
      public static PersonPanelModal personPanelModal() {
          PersonPanelModal personPanelModal = new PersonPanelModal(wDriver);
        return personPanelModal;
    }

    //// ALL PERSON CARD PANELS For Expected Result Validations/////////////////////



}
