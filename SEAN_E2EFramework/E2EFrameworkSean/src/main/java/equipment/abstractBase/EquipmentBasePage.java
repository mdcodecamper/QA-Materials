package equipment.abstractBase;

import _driverScript.AbstractStartWebDriver;
import equipment.equipmentAcceptDetachment.actions.AcceptDetachActions;
import equipment.equipmentAcceptDetachment.modals.EquipmentAcceptDetachModal;
import equipment.equipmentCancelDetach.actions.CancelDetachActions;
import equipment.equipmentCancelDetach.modals.EquipmentCancelDetachModal;
import equipment.equipmentDetach.actions.DetachActions;
import equipment.equipmentDetach.modals.EquipmentDetachModal;
import equipment.equipmentDown.actions.DownActions;
import equipment.equipmentDown.modals.EquipmentDownModal;
import equipment.equipmentSnowUpdate.actions.SnowUpdateActions;
import equipment.equipmentSnowUpdate.modals.EquipmentSnowUpdateModal;
import equipment.equipmentUp.actions.UpActions;
import equipment.equipmentUp.modals.EquipmentUpModal;
import equipment.equipmentUpdateLoad.actions.UpdateLoadActions;
import equipment.equipmentUpdateLoad.modals.EquipmentUpdateLoadModal;
import equipment.expectedResults.panels.EquipmentColorUtilities;
import equipment.expectedResults.panels.EquipmentHistoryUtilities;
import equipment.expectedResults.panels.EquipmentPanelUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by sdas on 10/8/2016.
 */
public abstract class EquipmentBasePage extends AbstractStartWebDriver {
    public EquipmentBasePage(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }


    //////////////////////////////////////  EQUIPMENT PANELS  /////////////////////////////////////////


    //// Object for equipmentDetachModalPage
    public static EquipmentDetachModal equipmentDetachModal() {
        EquipmentDetachModal equipmentDetachModal = new EquipmentDetachModal(wDriver);
        return equipmentDetachModal;
    }

    //// Object for equipmentAcceptModalPage
    public static EquipmentAcceptDetachModal equipmentAcceptModal() {
        EquipmentAcceptDetachModal equipmentAcceptModal = new EquipmentAcceptDetachModal(wDriver);
        return equipmentAcceptModal;
    }

    //// Object for equipmentCancelDetachModalPage
    public static EquipmentCancelDetachModal equipmentCancelDetachModal() {
        EquipmentCancelDetachModal equipmentCancelDetachModal = new EquipmentCancelDetachModal(wDriver);
        return equipmentCancelDetachModal;
    }

    //// Object for equipmentUpModalPage
    public static EquipmentUpModal equipmentUpModal() {
        EquipmentUpModal equipmentUpModal = new EquipmentUpModal(wDriver);
        return equipmentUpModal;
    }

    //// Object for equipmentDownModalPage
    public static EquipmentDownModal equipmentDownModal() {
        EquipmentDownModal equipmentDownModal = new EquipmentDownModal(wDriver);
        return equipmentDownModal;
    }

    //// Object for equipment downActions page
    public static DownActions equipmentDownActions() {
        DownActions equipmentDownActions = new DownActions(wDriver);
        return equipmentDownActions;
    }

    //// Object for equipment acceptDetachActions page
    public static AcceptDetachActions equipmentAcceptDetachActions() {
        AcceptDetachActions equipmentAcceptDetachActions = new AcceptDetachActions(wDriver);
        return equipmentAcceptDetachActions;
    }

    //// Object for equipment cancelDetachActions page
    public static CancelDetachActions equipmentCancelDetachActions() {
        CancelDetachActions equipmentCancelDetachActions = new CancelDetachActions(wDriver);
        return equipmentCancelDetachActions;
    }

    //// Object for equipment upActions page
    public static UpActions equipmentUpActions() {
        UpActions equipmentUpActions = new UpActions(wDriver);
        return equipmentUpActions;
    }

    //// Object for equipmentDetachActions page
    public static DetachActions equipmentDetachActions() {
        DetachActions equipmentDetachActions = new DetachActions(wDriver);
        return equipmentDetachActions;
    }

    //// Object for equipment update load Modal page
    public static EquipmentUpdateLoadModal equipmentUpdateLoadModal() {
        EquipmentUpdateLoadModal equipmentUpdateLoadModal = new EquipmentUpdateLoadModal(wDriver);
        return equipmentUpdateLoadModal;
    }

    //// Object for equipment update load Actions page
    public static UpdateLoadActions equipmentUpdateLoadActions() {
        UpdateLoadActions equipmentUpdateLoadActions = new UpdateLoadActions(wDriver);
        return equipmentUpdateLoadActions;
    }

    //// Object for equipment snow update actions page
    public static SnowUpdateActions equipmentSnowUpdateActions() {
        SnowUpdateActions equipmentSnowUpdateActions = new SnowUpdateActions(wDriver);
        return equipmentSnowUpdateActions;
    }
    //// Object for equipment snow update modal page
    public static EquipmentSnowUpdateModal equipmentSnowUpdateModal() {
        EquipmentSnowUpdateModal equipmentSnowUpdateModal = new EquipmentSnowUpdateModal(wDriver);
        return equipmentSnowUpdateModal;
    }

    //// Object for equipmentPanelUtilities page
    public static EquipmentPanelUtilities equipmentPanelUtilities() {
        EquipmentPanelUtilities equipmentPanelUtilities = new EquipmentPanelUtilities(wDriver);
        return equipmentPanelUtilities;
    }

    //// Object for EquipmentHistoryUtilities page
    public static EquipmentHistoryUtilities equipmentHistoryUtilities() {
        EquipmentHistoryUtilities equipmentHistoryUtilities = new EquipmentHistoryUtilities(wDriver);
        return equipmentHistoryUtilities;
    }

    //// Object for EquipmentColorUtilities page
    public static EquipmentColorUtilities equipmentColorUtilities() {
        EquipmentColorUtilities equipmentColorUtilities = new EquipmentColorUtilities(wDriver);
        return equipmentColorUtilities;
    }


}
