package equipment.expectedResults.panels;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.data.LoginData;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Utilities;

import java.io.IOException;

import static common.actions.CommonActions.getAnyCategoryCardsCountAfter;
import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestPass;
import static equipment.abstractBase.EquipmentBasePage.*;

/**
 * Created by sdas on 10/6/2016.
 */
public class EquipmentHistoryUtilities extends AbstractStartWebDriver {
    ExtentTest extentTest;

    public EquipmentHistoryUtilities(WebDriver wDriver) {
        super();
        PageFactory.initElements( wDriver, this );
        this.extentTest = super.extentTest;

    }

    public void getEquipmentDetachHistory(String equipmentId, String row, String action, String dateTime, String from, String to, String actionTaken, String driver, String remarks) throws InterruptedException, IOException {
        try {
            try {
                wDriver.findElement( By.xpath( "//*[text()='" + equipmentId + "']" ) ).click();
                Thread.sleep( 1000 );
            } catch (Exception e) {

                logTestFailure(wDriver, "Equipment is not clickable" );

            }

            // boolean detachmentActionLabel = wDriver.findElements(By.cssSelector(".auDetachmentHist th.auAction")).size() > 0;
            //if(detachmentActionLabel == true){
            String getActionValue = wDriver.findElement( By.cssSelector( ".auDetachmentHist .auRecord.row-" + row + " td.auAction" ) ).getText();
            if (getActionValue.toString().equals( action.toString() )) {

                //System.out.println("Actual value of 'Action' matches with expected value --" + action);
                logTestPass(wDriver, "Actual value of 'Action' matches with expected value -- " + action );

            } else {
                //System.out.println("Expected value 'Action' is " + action + " but actual value is " + getActionValue);
                logTestFailure(wDriver, "Expected value 'Action' is " + action + " but actual value is " + getActionValue );
            }

            // } else {
            //System.out.println("detachment Action label is not found");
            //  extentTest.log(LogStatus.FAIL, "detachment Action label is not found");
            // getScreenShot(wDriver);
            // }

            //boolean detachmentDateTimeLabel = wDriver.findElements(By.cssSelector(".auDetachmentHist th.auDateTime")).size() > 0;
            // if(detachmentDateTimeLabel == true){
            String getDateTimeValue = wDriver.findElement( By.cssSelector( ".auDetachmentHist .auRecord.row-" + row + " td.auDateTime" ) ).getText();
            String[] arrayDateTime = dateTime.split( "/" );
            int arrayMonthInt = Integer.parseInt( arrayDateTime[0] );
            if (arrayMonthInt > 10) {
                arrayMonthInt = 0 + arrayMonthInt;
            }
            int arrayDayInt = Integer.parseInt( arrayDateTime[1] );
            if (arrayDayInt > 10) {
                arrayDayInt = 0 + arrayDayInt;
            }
            if (getDateTimeValue.toString().contains( arrayMonthInt + "/" + arrayDayInt + "/" + arrayDateTime[2] )) {

                // System.out.println("Actual value of 'Date & Time' matches with expected value --" + dateTime);
                logTestPass(wDriver, "Actual value of 'Date & Time' matches with expected value -- " + dateTime );

            } else {
                //System.out.println("Expected value is of 'Date & Time' is " + dateTime + " but actual value is " + getDateTimeValue);
                logTestFailure(wDriver, "Expected value is of 'Date & Time' is " + dateTime + " but actual value is " + getDateTimeValue );
            }

           /* } else {
                //System.out.println("detachment Date & Time label is not found");
                extentTest.log(LogStatus.FAIL, "detachment Date & Time label is not found");
                getScreenShot(wDriver);
            }*/

           /* boolean detachmentFromLabel = wDriver.findElements(By.cssSelector(".auDetachmentHist th.auFrom")).size() > 0;
            if(detachmentFromLabel == true){*/
            String getFromValue = wDriver.findElement( By.cssSelector( ".auDetachmentHist .auRecord.row-" + row + " td.auFrom" ) ).getText();
            if (getFromValue.toString().equals( from.toString() )) {

                //System.out.println("Actual value of 'From' matches with expected value --" + from);
                logTestPass(wDriver, "Actual value of 'From' matches with expected value -- " + from );

            } else {
                // System.out.println("Expected value 'From' is " + action + " but actual value is " + getFromValue);
                logTestFailure(wDriver, "Expected value 'From' is " + from + " but actual value is " + getFromValue );
            }

            /*} else {
                System.out.println("detachment From label is not found");
            }*/

           /* boolean detachmentToLabel = wDriver.findElements(By.cssSelector(".auDetachmentHist th.auTo")).size() > 0;
            if(detachmentToLabel == true){*/
            String getToValue = wDriver.findElement( By.cssSelector( ".auDetachmentHist .auRecord.row-" + row + " td.auTo" ) ).getText();
            if (getToValue.toString().equals( to.toString() )) {

                //System.out.println("Actual value of 'To' matches with expected value --" + to);
                logTestPass(wDriver, "Actual value of 'To' matches with expected value -- " + to );

            } else {
                //System.out.println("Expected value 'To' is " + to + " but actual value is " + getToValue);
                logTestFailure(wDriver, "Expected value 'To' is " + to + " but actual value is " + getToValue );
            }

           /* } else {
                //System.out.println("detachment To label is not found");
                extentTest.log(LogStatus.FAIL, "detachment To label is not found");
                getScreenShot(wDriver);
            }*/

           /* boolean detachmentActionTakenLabel = wDriver.findElements(By.cssSelector(".auDetachmentHist th.auActionTakenBy")).size() > 0;
            if(detachmentActionTakenLabel == true){*/
            String getActionTakenValue = wDriver.findElement( By.cssSelector( ".auDetachmentHist .auRecord.row-" + row + " td.auActionTakenBy" ) ).getText();
            if (getActionTakenValue.toString().equals( actionTaken.toString() )) {

                //System.out.println("Actual value of 'To' matches with expected value --" + to);
                logTestPass(wDriver, "Actual value of 'ActionTakenBy' matches with expected value -- " + actionTaken );

            } else {
                //System.out.println("Expected value 'To' is " + to + " but actual value is " + getToValue);
                logTestFailure(wDriver, "Expected value 'ActionTakenBy' is " + actionTaken + " but actual value is " + getActionTakenValue );
            }

           /* } else {
                //System.out.println("detachment To label is not found");
                extentTest.log(LogStatus.FAIL, "detachment ActionTakenBy label is not found");
                getScreenShot(wDriver);
            }*/

           /* boolean detachmentDriverLabel = wDriver.findElements(By.cssSelector(".auDetachmentHist th.auDriver")).size() > 0;
            if(detachmentDriverLabel == true){*/
            String getDriverValue = wDriver.findElement( By.cssSelector( ".auDetachmentHist .auRecord.row-" + row + " td.auDriver" ) ).getText();
            if (getDriverValue.toString().equals( driver.toString() )) {

                //System.out.println("Actual value of 'To' matches with expected value --" + to);
                logTestPass(wDriver, "Actual value of 'Driver' matches with expected value -- " + driver );

            } else {
                //System.out.println("Expected value 'To' is " + to + " but actual value is " + getToValue);
                logTestFailure(wDriver, "Expected value 'Driver' is " + driver + " but actual value is " + getDriverValue );
            }

            /*} else {
                //System.out.println("detachment To label is not found");
                extentTest.log(LogStatus.FAIL, "detachment Driver label is not found");
                getScreenShot(wDriver);
            }*/

           /* boolean detachmentRemarksLabel = wDriver.findElements(By.cssSelector(".auDetachmentHist th.auRemarks")).size() > 0;
            if(detachmentRemarksLabel == true){*/
            String getRemarksValue = wDriver.findElement( By.cssSelector( ".auDetachmentHist .auRecord.row-" + row + " td.auRemarks" ) ).getText();
            if (getRemarksValue.toString().equals( remarks.toString() )) {

                //System.out.println("Actual value of 'To' matches with expected value --" + to);
                logTestPass(wDriver, "Actual value of 'Remarks' matches with expected value -- " + remarks );

            } else {
                //System.out.println("Expected value 'To' is " + to + " but actual value is " + getToValue);
                logTestFailure(wDriver, "Expected value 'Remarks' is " + driver + " but actual value is " + getRemarksValue );
            }

           /* } else {
                //System.out.println("detachment To label is not found");
                extentTest.log(LogStatus.FAIL, "detachment Remarks label is not found");
                getScreenShot(wDriver);
            }*/


        } catch (Exception e) {

            logTestFailure(wDriver, "Equipment Detachment history is missing/labels not found" );

        }

    }

    // class below will return expected result from up/down history panel
    public void getEquipmentUpDownHisotry(String equipmentId, String row, String child, String action, String date, String time, String downCode, String serviceLocation, String reporter, String mechanic, String remarks) throws IOException {
        int dCode;
        try {

            try {
                executorClick(wDriver,wDriver.findElement( By.xpath( "//*[text()='" + equipmentId + "']" )));
                Thread.sleep( 1000 );
            } catch (Exception e) {

                logTestFailure(wDriver, "Equipment is not clickable" );

            }

           /* boolean upDownActionLabel = wDriver.findElements( By.cssSelector( ".auUpDownHist th.auAction" ) ).size() > 0;
            if(upDownActionLabel == true) {*/
            String getActionValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auAction" ) ).getText();
            if (getActionValue.toString().equals( action.toString() )) {

                //System.out.println("Actual value of 'Action' matches with expected value --" + action);
                logTestPass(wDriver, "Actual value of 'Action' matches with expected value -- " + action );

            } else {
                //System.out.println("Expected value 'Action' is " + action + " but actual value is " + getActionValue);
                logTestFailure(wDriver, "Expected value 'Action' is " + action + " but actual value is " + getActionValue );
            }

            /*} else {
                //System.out.println("detachment Action label is not found");
                logTestFailure(wDriver, "Up/Down Action label is not found" );
                getScreenShot( wDriver );
            }*/

             /*  boolean upDownDateLabel = wDriver.findElements( By.cssSelector( ".auUpDownHist th.auDate" ) ).size() > 0;
            if(upDownDateLabel == true) {*/
            String getDateValue;
            if (child != null) {
                int childInt = Integer.parseInt( child );
                getDateValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auDate tr:nth-child(" + childInt + ")" ) ).getText();
            } else {
                getDateValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auDate" ) ).getText();
            }
            if (getDateValue.toString().equals( date.toString() )) {

                logTestPass(wDriver, "Actual value of 'Date' matches with expected value -- " + date );

            } else {
                logTestFailure(wDriver, "Expected value of 'Date' is " + date + " but actual value is " + getDateValue );
            }

            /*} else {
                logTestFailure(wDriver, "Up/Down Date label is not found" );
                getScreenShot( wDriver );
            }
*/
           /* boolean timeLabel = wDriver.findElements( By.cssSelector( ".auUpDownHist th.auTime" ) ).size() > 0;
            if(timeLabel == true) {*/
            String getTimeValue;
            if (child != null) {
                int childInt = Integer.parseInt( child );
                getTimeValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auTime tr:nth-child(" + childInt + ")" ) ).getText();
            } else {
                getTimeValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auTime" ) ).getText();
            }

            if (getTimeValue.toString().contains( time.toString())) {

                logTestPass(wDriver, "Actual value of 'Time' matches with expected value -- " + time );

            } else {
                logTestFailure(wDriver, "Expected value of 'Time' is " + time + " but actual value is " + getTimeValue );
            }

          /*  boolean downCodeLabel = wDriver.findElements( By.cssSelector( ".auUpDownHist th.auDownCode" ) ).size() > 0;
            if(downCodeLabel == true) {*/
            String getDownCodeValue;
            if (child != null) {
                int childInt = Integer.parseInt( child );
                dCode = childInt;
                getDownCodeValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auDownCode tr:nth-child(" + childInt + ")" ) ).getText();
            } else {
                getDownCodeValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auDownCode" ) ).getText();
                dCode = 1;
            }
            if (getDownCodeValue.toString().equals( downCode.toString() )) {

                logTestPass(wDriver, "Actual value of 'Down Code' " + dCode + " matches with expected value -- " + downCode );

            } else {
                logTestFailure(wDriver, "Expected value of 'Down Code' " + dCode + " is " + downCode + " but actual value is " + getDownCodeValue );
            }
            /*} else {
                logTestFailure(wDriver, "Up/Down Down Code label is not found" );
                getScreenShot( wDriver );
            }*/

                    /*boolean serviceLocationLabel = wDriver.findElements( By.cssSelector( ".auUpDownHist th.auServiceLocation tr:nth-child("+ child +")" ) ).size() > 0;
                    if(serviceLocationLabel == true) {*/
            String getServiceLocationValue;
            if (child != null) {
                int childInt = Integer.parseInt( child );
                getServiceLocationValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auServiceLocation tr:nth-child(" + childInt + ")" ) ).getText();
            } else {
                getServiceLocationValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auServiceLocation" ) ).getText();
            }
            if (getServiceLocationValue.toString().equals( serviceLocation.toString() )) {

                logTestPass(wDriver, "Actual value of 'Service Location' matches with expected value -- " + serviceLocation );

            } else {
                logTestFailure(wDriver, "Expected value of 'Service Location' is " + serviceLocation + " but actual value is " + getServiceLocationValue );
            }

                /*} else {
                    logTestFailure(wDriver, "Up/Down Service Location label is not found" );
                    getScreenShot( wDriver );
                }*/

           /* boolean reporterLabel = wDriver.findElements( By.cssSelector( ".auUpDownHist th.auReporter" ) ).size() > 0;
            if(reporterLabel == true) {*/
            String getReporterValue;
            if (child != null) {
                int childInt = Integer.parseInt( child );
                getReporterValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auReporter tr:nth-child(" + childInt + ")" ) ).getText();
            } else {
                getReporterValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auReporter" ) ).getText();
            }
            if (getReporterValue.toString().equals( reporter.toString() )) {

                logTestPass(wDriver, "Actual value of 'Reporter' matches with expected value -- " + reporter );

            } else {
                logTestFailure(wDriver, "Expected value of 'Reporter' is " + reporter + " but actual value is " + getReporterValue );
            }

            /*} else {
                logTestFailure(wDriver, "Up/Down Reporter label is not found" );
                getScreenShot( wDriver );
            }*/

           /* boolean mechanicLabel = wDriver.findElements( By.cssSelector( ".auUpDownHist th.auMechanic" ) ).size() > 0;
            if(mechanicLabel == true) {*/
            String getMechanicValue;
            if (child != null) {
                int childInt = Integer.parseInt( child );
                getMechanicValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auMechanic tr:nth-child(" + childInt + ")" ) ).getText();
            } else {
                getMechanicValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auMechanic" ) ).getText();
            }
            if (getMechanicValue.toString().equals( mechanic.toString() )) {

                logTestPass(wDriver, "Actual value of 'Mechanic' matches with expected value -- " + mechanic );

            } else {
                logTestFailure(wDriver, "Expected value of 'Mechanic' is " + mechanic + " but actual value is " + getMechanicValue );
            }

           /* } else {
                logTestFailure(wDriver, "Up/Down Mechanic label is not found" );
                getScreenShot( wDriver );
            }*/

           /* boolean remarksLabel = wDriver.findElements( By.cssSelector( ".auUpDownHist th.auRemarks" ) ).size() > 0;
            if(remarksLabel == true) {*/
            String getRemarksValue;
            if (child != null) {
                int childInt = Integer.parseInt( child );
                getRemarksValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auRemarks tr:nth-child(" + childInt + ")" ) ).getText();
            } else {
                getRemarksValue = wDriver.findElement( By.cssSelector( ".auUpDownHist .auRecord.row-" + row + " td.auRemarks" ) ).getText();
            }
            if (getRemarksValue.toString().equals( remarks.toString() )) {

                logTestPass(wDriver, "Actual value of 'Remarks' matches with expected value -- " + remarks );

            } else {
                logTestFailure(wDriver, "Expected value of 'Remarks' is " + remarks + " but actual value is " + getRemarksValue );
            }

           /* } else {
                logTestFailure(wDriver, "Up/Down Remarks label is not found" );
                getScreenShot( wDriver );
            }*/
            /*} else {
                logTestFailure(wDriver, "Up/Down Time label is not found" );
                getScreenShot( wDriver );
            }*/



        } catch (Exception e) {

            logTestFailure(wDriver, "Up/Down history is missing/labels not found" );
            getScreenShot( wDriver );

        }
    }


    //code below will check equipment Update Load history
    public void getEquipmentLoadStatusHistory(String equipmentId, String row, String bin, String status, String materialType, String lastUpdated, String updatedBy, String ... Criteria) throws InterruptedException, IOException {
        try {

            String clickOnEquipment = null;

            if(Criteria.length >= 1)
            {
                clickOnEquipment = Criteria[0];
            }

            if(clickOnEquipment != null) {
                try {
                    wDriver.findElement( By.xpath( "//*[text()='" + equipmentId + "']" ) ).click();
                    Thread.sleep( 1000 );
                } catch (Exception e) {

                    logTestFailure(wDriver, "Equipment is not clickable" );
                }
            }

            boolean binRowExist = wDriver.findElements(By.cssSelector(".auLoadHist .auRecord.row-" + row + " td.auBin")).size() > 0;
            String getBinValue;
            if(binRowExist == true) {
                getBinValue = wDriver.findElement( By.cssSelector( ".auLoadHist .auRecord.row-" + row + " td.auBin" ) ).getText();
            } else {
                getBinValue = wDriver.findElement( By.cssSelector( ".auLoadHist td.auBin" ) ).getText();
            }
            if (getBinValue.toString().equals( bin.toString() )) {

                //System.out.println("Actual value of 'Action' matches with expected value --" + action);
                logTestPass(wDriver, "Actual value of 'Bin' matches with expected value -- " + bin );

            } else {
                //System.out.println("Expected value 'Action' is " + action + " but actual value is " + getActionValue);
                logTestFailure(wDriver, "Expected value 'Bin' is " + bin + " but actual value is " + getBinValue );
            }

            boolean statusRowExist = wDriver.findElements(By.cssSelector(".auLoadHist .auRecord.row-" + row + " td.auStatus")).size() > 0;
            String getStatusValue;
            if(statusRowExist == true) {
                getStatusValue = wDriver.findElement( By.cssSelector( ".auLoadHist .auRecord.row-" + row + " td.auStatus" ) ).getText();
            } else {
                getStatusValue = wDriver.findElement( By.cssSelector( ".auLoadHist td.auStatus" ) ).getText();
            }
            if (getStatusValue.toString().equals( status.toString())) {

                //System.out.println("Actual value of 'Action' matches with expected value --" + action);
                logTestPass(wDriver, "Actual value of 'Status' matches with expected value -- " + status );

            } else {
                //System.out.println("Expected value 'Action' is " + action + " but actual value is " + getActionValue);
                logTestFailure(wDriver, "Expected value 'Status' is " + status + " but actual value is " + getStatusValue );
            }

            boolean materialRowExist = wDriver.findElements(By.cssSelector(".auLoadHist .auRecord.row-" + row + " td.auMaterialType")).size() > 0;
            String getMaterialTypeValue;
            if(materialRowExist == true) {
                getMaterialTypeValue = wDriver.findElement( By.cssSelector( ".auLoadHist .auRecord.row-" + row + " td.auMaterialType" )).getText();
            } else {
                getMaterialTypeValue = wDriver.findElement( By.cssSelector( ".auLoadHist td.auMaterialType " ) ).getText();
            }
            if (getMaterialTypeValue.toString().equals( materialType.toString())) {

                //System.out.println("Actual value of 'Action' matches with expected value --" + action);
                logTestPass(wDriver, "Actual value of 'Material Type' matches with expected value -- " + materialType);

            } else {
                //System.out.println("Expected value 'Action' is " + action + " but actual value is " + getActionValue);
                logTestFailure(wDriver, "Expected value 'Material Type' is " + materialType + " but actual value is " + getMaterialTypeValue);
            }

            if(lastUpdated.toString() != "SKIP".toString()){
                boolean lastUpdatedRowExist = wDriver.findElements( By.cssSelector( ".auLoadHist .auRecord.row-" + row + " td.auLastUpdated" ) ).size() > 0;
                String getlastUpdatedeValue;
                if (lastUpdatedRowExist == true) {
                    getlastUpdatedeValue = wDriver.findElement( By.cssSelector( ".auLoadHist .auRecord.row-" + row + " td.auLastUpdated" ) ).getText();
                } else {
                    getlastUpdatedeValue = wDriver.findElement( By.cssSelector( ".auLoadHist td.auLastUpdated " ) ).getText();
                }
                if (getlastUpdatedeValue.toString().contains( lastUpdated.toString() )) {

                    //System.out.println("Actual value of 'Action' matches with expected value --" + action);
                    logTestPass(wDriver, "Actual value of 'Last Updated' matches with expected value -- " + lastUpdated );

                } else {
                    //System.out.println("Expected value 'Action' is " + action + " but actual value is " + getActionValue);
                    logTestFailure(wDriver, "Expected value 'Last Updated' is " + lastUpdated + " but actual value is " + getlastUpdatedeValue );
                }
            }

            boolean updatedByRowExist = wDriver.findElements(By.cssSelector(".auLoadHist .auRecord.row-" + row + " td.auUpdatedBy")).size() > 0;
            String getUpdatedByValue;
            if(updatedByRowExist == true) {
                getUpdatedByValue = wDriver.findElement( By.cssSelector( ".auLoadHist .auRecord.row-" + row + " td.auUpdatedBy" )).getText();
            } else {
                getUpdatedByValue = wDriver.findElement( By.cssSelector( ".auLoadHist td.auUpdatedBy " )).getText();
            }
            if (getUpdatedByValue.toString().contains( updatedBy.toString())) {

                //System.out.println("Actual value of 'Action' matches with expected value --" + action);
                logTestPass(wDriver, "Actual value of 'Updated By' matches with expected value -- " + updatedBy);

            } else {
                //System.out.println("Expected value 'Action' is " + action + " but actual value is " + getActionValue);
                logTestFailure(wDriver, "Expected value 'Updated By' is " + updatedBy + " but actual value is " + getUpdatedByValue);
            }


        } catch (Exception e) {

            logTestFailure(wDriver, "Update Load history is missing/labels not found" );

        }
    }

    public String getAssignedLocation(String equipmentId){
        String assignedLocation = null;
        try {
            wDriver.findElement( By.xpath( "//*[text()='" + equipmentId + "']" ) ).click();
            Thread.sleep( 1000 );
        } catch (Exception e) {

            logTestFailure( wDriver, "Equipment is not clickable" );
        }

        try{
             if(wDriver.findElements(By.cssSelector(".auAssigned")).size() > 0) {
                 assignedLocation = wDriver.findElement( By.cssSelector( ".auAssigned" ) ).getText();
             } else {

                 logTestFailure(wDriver,"assign location is not available/empty");
                 Assert.fail();
             }
        }catch(Exception e){

            logTestFailure( wDriver, "Assigned value is not found on Equipment History Panel");
        }

        return assignedLocation;

    }
    public void verifyEquipmentAssignedLocation(String equipmentId, String assignedLocation, String location, String receivingLocation, int detachedCountBefore) throws IOException, InterruptedException {

        if (assignedLocation.equals( location )) {
            //line below will check detached category count on sending location
            getAnyCategoryCardsCountAfter( "+1",detachedCountBefore, "Equipment", "Detached", null, null );
            //line below will check if equipment is present in detached category on sending location
            equipmentPanelUtilities().verifyEquipmentPresent( "Detached", receivingLocation, null, equipmentId, "true" );
            logTestPass(wDriver, "Equipment " + equipmentId + " is present in 'Detached' category on sending location " + location );
        } else if(assignedLocation.equals( receivingLocation )) {
            logTestPass(wDriver, "Equipment " + equipmentId + " assigned location is same as detach to location " + receivingLocation );
        } else {

                String url = LoginData.getLoginData( getUrl() );

                if (assignedLocation.equals("QE11A") && assignedLocation.equals( "QE11W" )){
                    String assignedLocationDual = "QE11";
                    loginPage().goToBoardLocationByDate( url, assignedLocationDual + "/", Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" ) );
                    wDriver.findElement( By.xpath( "//*[contains(@class,'dualgarage actionmenu')]//*[contains(@class,'arrow-down')]" ) ).click();
                    if (assignedLocation.equals( "QE11W" )) {
                        wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 0 ).click();
                    } else {
                        wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 1 ).click();
                    }
                    //line below will check detached category count on assigned location
                    equipmentPanelUtilities().getEquipmentCategoryCount( "Detached", null, null );
                    //line below will check if equipment is present in detached category on assigned location
                    equipmentPanelUtilities().verifyEquipmentPresent( "Detached", receivingLocation, null, equipmentId, "true" );
                    logTestPass(wDriver, "Equipment " + equipmentId + " is present in 'Detached' category on assigned location " + assignedLocation );

                }
                if(assignedLocation.equals("QE13J") && assignedLocation.equals( "QE13W" )){
                    String assignedLocationDual = "QE13";
                    loginPage().goToBoardLocationByDate( url, assignedLocationDual + "/", Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" ) );
                    wDriver.findElement( By.xpath( "//*[contains(@class,'dualgarage actionmenu')]//*[contains(@class,'arrow-down')]" ) ).click();
                    if (assignedLocation.equals( "QE13J" )) {
                        wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 0 ).click();
                    } else {
                        wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 1 ).click();
                    }
                    //line below will check detached category count on assigned location
                    equipmentPanelUtilities().getEquipmentCategoryCount( "Detached", null, null );
                    //line below will check if equipment is present in detached category on assigned location
                    equipmentPanelUtilities().verifyEquipmentPresent( "Detached", receivingLocation, null, equipmentId, "true" );
                    logTestPass(wDriver, "Equipment " + equipmentId + " is present in 'Detached' category on assigned location " + assignedLocation );

                }

                if(!assignedLocation.contains("QE11") && !assignedLocation.contains("QE13") && !assignedLocation.contains(receivingLocation) && !assignedLocation.contains(location)){
                    loginPage().goToBoardLocationByDate( url, assignedLocation + "/", Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" ) );
                    //line below will check detached category count on assigned location
                    equipmentPanelUtilities().getEquipmentCategoryCount( "Detached", null, null );
                    //line below will check if equipment is present in detached category on assigned location
                    equipmentPanelUtilities().verifyEquipmentPresent( "Detached", receivingLocation, null, equipmentId, "true" );
                    logTestPass(wDriver, "Equipment " + equipmentId + " is present in 'Detached' category on assigned location " + assignedLocation );
                }


            }

    }

    //code below will allow you set assigned location as the home location
    public String setAssignedLocation(String equipmentId, String currentLocation) throws IOException, InterruptedException {
        String equipmentAssignedLocation = null;
        String url = LoginData.getLoginData( getUrl() );
        equipmentAssignedLocation = equipmentHistoryUtilities().getAssignedLocation(equipmentId);

        if (equipmentAssignedLocation.equals("QE11A") || equipmentAssignedLocation.equals( "QE11W" )){
            if(!currentLocation.equals("QE11")){
                equipmentDetachActions().detachEquipment(equipmentId,equipmentAssignedLocation,null,null,"test001");
                loginPage().goToBoardLocationByDate( url, "QE11/", Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" ) );
                wDriver.findElement( By.xpath( "//*[contains(@class,'dualgarage actionmenu')]//*[contains(@class,'arrow-down')]" ) ).click();
                if (equipmentAssignedLocation.equals( "QE11W" )) {
                    wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 0 ).click();
                    equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId,"receiver001","remarks001",null,null);
                } else {
                    wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 1 ).click();
                    equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId,"receiver001","remarks001",null,null);
                }
            }
        }

        if (equipmentAssignedLocation.equals("QE13J") || equipmentAssignedLocation.equals( "QE13W" )){
            if(!currentLocation.equals("QE13")) {
                equipmentDetachActions().detachEquipment(equipmentId,equipmentAssignedLocation,null,null,"test001");
                loginPage().goToBoardLocationByDate( url, "QE13/", Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" ) );
                wDriver.findElement( By.xpath( "//*[contains(@class,'dualgarage actionmenu')]//*[contains(@class,'arrow-down')]" ) ).click();
                if (equipmentAssignedLocation.equals( "QE13J" )) {
                    wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 0 ).click();
                    equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId,"receiver001","remarks001",null,null);
                } else {
                    wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 1 ).click();
                    equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId,"receiver001","remarks001",null,null);
                }

            } else {
                wDriver.findElement( By.xpath( "//*[contains(@class,'dualgarage actionmenu')]//*[contains(@class,'arrow-down')]" ) ).click();
                if (equipmentAssignedLocation.equals( "QE13J" )) {
                    wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 0 ).click();
                } else {
                    wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 1 ).click();
                }
            }

        }

        if(!equipmentAssignedLocation.contains("QE11") && !equipmentAssignedLocation.contains("QE13")){
            if(!currentLocation.equals(equipmentAssignedLocation)) {
                equipmentDetachActions().detachEquipment( equipmentId, equipmentAssignedLocation, null, null, "test001" );
                loginPage().goToBoardLocationByDate( url, equipmentAssignedLocation + "/", Utilities.getDesiredDateInFormat( 0, "yyyyMMdd" ) );
                equipmentAcceptDetachActions().acceptEquipmentDetachment(equipmentId,"receiver001","remarks001",null,null);
            }
        }

        return equipmentAssignedLocation;

    }

    //code below will check equipment Snow Update history
    public void getEquipmentSnowUpdateHistory(String equipmentId, String row, String plowType, String plowDirection, String chains, String load, String snowAssignment, String lastUpdated, String updatedBy) throws InterruptedException, IOException {
        try {

            try {
                wDriver.findElement( By.xpath( "//*[text()='" + equipmentId + "']" ) ).click();
                Thread.sleep( 1000 );
            } catch (Exception e) {

                logTestFailure(wDriver, "Equipment is not clickable" );

            }
            boolean plowTypeRowExist = wDriver.findElements(By.cssSelector(".auSnowHist .auRecord.row-" + row + " td.auPlowType")).size() > 0;
            String getPlowTypeValue;
            if(plowTypeRowExist == true) {
                getPlowTypeValue = wDriver.findElement( By.cssSelector( ".auSnowHist .auRecord.row-" + row + " td.auPlowType" )).getText();
            } else {
                getPlowTypeValue = wDriver.findElement( By.cssSelector( ".auSnowHist td.auPlowType" ) ).getText();
            }
            if (getPlowTypeValue.toString().equals( plowType.toString() )) {

                logTestPass(wDriver, "Actual value of 'Plow Type' matches with expected value -- " + plowType);

            } else {
                logTestFailure(wDriver, "Expected value of 'Plow Type' is " + plowType + " but actual value is " + getPlowTypeValue);
            }

            boolean plowDirectionExist = wDriver.findElements(By.cssSelector(".auSnowHist .auRecord.row-" + row + " td.auPlowDirection")).size() > 0;
            String getPlowDirectionValue;
            if(plowDirectionExist == true) {
                getPlowDirectionValue = wDriver.findElement( By.cssSelector( ".auSnowHist .auRecord.row-" + row + " td.auPlowDirection" ) ).getText();
            } else {
                getPlowDirectionValue = wDriver.findElement( By.cssSelector( ".auSnowHist td.auPlowDirection" ) ).getText();
            }
            if (getPlowDirectionValue.toString().equals( plowDirection.toString() )) {

                logTestPass(wDriver, "Actual value of 'Plow Direction' matches with expected value -- " + plowDirection);

            } else {
                logTestFailure(wDriver, "Expected value of 'Plow Direction' is " + plowDirection + " but actual value is " + getPlowDirectionValue);
            }

            boolean chainsExist = wDriver.findElements(By.cssSelector(".auSnowHist .auRecord.row-" + row + " td.auChains")).size() > 0;
            String getChainsValue;
            if(chainsExist == true) {
                getChainsValue = wDriver.findElement( By.cssSelector( ".auSnowHist .auRecord.row-" + row + " td.auChains" ) ).getText();
            } else {
                getChainsValue = wDriver.findElement( By.cssSelector( ".auSnowHist td.auChains" ) ).getText();
            }
            if (getChainsValue.toString().equals( chains.toString() )) {

                logTestPass(wDriver, "Actual value of 'Chains' matches with expected value -- " + chains);

            } else {
                logTestFailure(wDriver, "Expected value of 'Chains' is " + plowDirection + " but actual value is " + getChainsValue);
            }

            boolean loadExist = wDriver.findElements(By.cssSelector(".auSnowHist .auRecord.row-" + row + " td.auLoad")).size() > 0;
            String getLoadValue;
            if(loadExist == true) {
                getLoadValue = wDriver.findElement( By.cssSelector( ".auSnowHist .auRecord.row-" + row + " td.auLoad" ) ).getText();
            } else {
                getLoadValue = wDriver.findElement( By.cssSelector( ".auSnowHist td.auLoad" ) ).getText();
            }
            if (getLoadValue.toString().equals( load.toString() )) {

                logTestPass(wDriver, "Actual value of 'Load' matches with expected value -- " + load);

            } else {
                logTestFailure(wDriver, "Expected value of 'Load' is " + load + " but actual value is " + getLoadValue);
            }

            boolean snowAssignExist = wDriver.findElements(By.cssSelector(".auSnowHist .auRecord.row-" + row + " td.auSnowAssignment")).size() > 0;
            String getSnowAssignValue;
            if(snowAssignExist == true) {
                getSnowAssignValue = wDriver.findElement( By.cssSelector( ".auSnowHist .auRecord.row-" + row + " td.auSnowAssignment" ) ).getText();
            } else {
                getSnowAssignValue = wDriver.findElement( By.cssSelector( ".auSnowHist td.auSnowAssignment" ) ).getText();
            }
            if (getSnowAssignValue.toString().equals( snowAssignment.toString() )) {

                logTestPass(wDriver, "Actual value of 'Snow Assignment' matches with expected value -- " + snowAssignment);

            } else {
                logTestFailure(wDriver, "Expected value of 'Snow Assignment' is " + snowAssignment + " but actual value is " + getSnowAssignValue);
            }

            boolean lastUpdatedExist = wDriver.findElements(By.cssSelector(".auSnowHist .auRecord.row-" + row + " td.auLastUpdated")).size() > 0;
            String getLasUpdateValue;
            if(lastUpdatedExist == true) {
                getLasUpdateValue = wDriver.findElement( By.cssSelector( ".auSnowHist .auRecord.row-" + row + " td.auLastUpdated" ) ).getText();
            } else {
                getLasUpdateValue = wDriver.findElement( By.cssSelector( ".auSnowHist td.auLastUpdated" ) ).getText();
            }
            if (getLasUpdateValue.toString().contains( lastUpdated.toString() )) {

                logTestPass(wDriver, "Actual value of 'Last Updated' matches with expected value -- " + lastUpdated);

            } else {
                logTestFailure(wDriver, "Expected value of 'Last Updated' is " + lastUpdated + " but actual value is " + getLasUpdateValue);
            }

            boolean updatedByExist = wDriver.findElements(By.cssSelector(".auSnowHist .auRecord.row-" + row + " td.auUpdatedBy")).size() > 0;
            String getUpdatedByValue;
            if(updatedByExist == true) {
                getUpdatedByValue = wDriver.findElement( By.cssSelector( ".auSnowHist .auRecord.row-" + row + " td.auUpdatedBy" ) ).getText();
            } else {
                getUpdatedByValue = wDriver.findElement( By.cssSelector( ".auSnowHist td.auUpdatedBy" ) ).getText();
            }
            if (getUpdatedByValue.toString().equals( updatedBy.toString() )) {

                logTestPass(wDriver, "Actual value of 'Updated By' matches with expected value -- " + updatedBy);

            } else {
                logTestFailure(wDriver, "Expected value of 'Updated By' is " + updatedBy + " but actual value is " + getUpdatedByValue);
            }


        } catch (Exception e) {

            logTestFailure(wDriver, "Snow Update history is missing/labels not found" );

        }
    }


    //line below will verify equipment detail history from equipment panel
    public void getEquipmentDetailHistory(String equipmentId,String equipmentCondition, String lastUpdated, String lastUpdatedBy,String currentLoaction){

        try {

            try {
                wDriver.findElement( By.xpath( "//*[text()='" + equipmentId + "']" ) ).click();
                Thread.sleep( 1000 );
            } catch (Exception e) {

                logTestFailure(wDriver, "Equipment is not clickable" );

            }

            String equipmentLabel = wDriver.findElement(By.cssSelector( "equipment-details .auHeaderTitle > h1" )).getText();
            if (equipmentLabel.equals( equipmentId )) {
                logTestPass( wDriver, "Equipment label'" + equipmentLabel + "' appears as Expected" );
            } else {
                logTestFailure( wDriver, "Equipment label is inaccurate. Expected " + equipmentId + " but actual " +  equipmentLabel);
            }

            if(equipmentCondition != null) {
                String conditionLabel = wDriver.findElement( By.cssSelector( ".auConditionLabel" ) ).getText();
                if (conditionLabel.equals( "Condition" )) {
                    logTestPass( wDriver, "Condition label '" + conditionLabel + "' appears as Expected" );
                } else {
                    logTestFailure( wDriver, "Condition label is inaccurate" );
                }
                String conditionValue = wDriver.findElement( By.cssSelector( ".auCondition." + equipmentCondition.toLowerCase() + "" ) ).getText();
                if (conditionValue.equals( equipmentCondition )) {
                    logTestPass( wDriver, "Equipment Condition value '" + equipmentCondition + "' appears as Expected" );
                } else {
                    logTestFailure( wDriver, "Equipment Condition value is inaccurate" );
                }
            }

            if(lastUpdated != null) {
                String lastUpdatedLabel = wDriver.findElement( By.cssSelector( ".auLastUpdateLabel" ) ).getText();
                if (lastUpdatedLabel.equals( "Last Updated" )) {
                    logTestPass( wDriver, "Last Updated label '" + lastUpdatedLabel + "' appears as Expected" );
                } else {
                    logTestFailure( wDriver, "Last Updated label is inaccurate" );
                }
                String lastUpdatedValue = wDriver.findElement( By.cssSelector( ".auLastUpdate" ) ).getText();
                if (lastUpdatedValue.contains( lastUpdated )) {
                    logTestPass( wDriver, "Last Updated value '" + lastUpdated + "' appears as Expected" );
                } else {
                    logTestFailure( wDriver, "Last Updated value is inaccurate. Expected " + lastUpdated + " but actual " + lastUpdatedValue );
                }
            }

            if(lastUpdatedBy != null) {
                String lastUpdatedByLabel = wDriver.findElement( By.cssSelector( ".auLastUpdatedByLabel" ) ).getText();
                if (lastUpdatedByLabel.equals( "Last Updated By" )) {
                    logTestPass( wDriver, "Last Updated By label '" + lastUpdatedByLabel + "' appears as Expected" );
                } else {
                    logTestFailure( wDriver, "Last Updated By label is inaccurate" );
                }
                String lastUpdatedByValue = wDriver.findElement( By.cssSelector( ".auLastUpdate" ) ).getText();
                if (lastUpdatedByValue.equals( lastUpdatedBy )) {
                    logTestPass( wDriver, "Last Updated By value '" + lastUpdatedBy + "' appears as Expected" );
                } else {
                    logTestFailure( wDriver, "Last Updated By value is inaccurate. Expected " + lastUpdatedBy + " but actual " + lastUpdatedByValue );
                }
            }

            if(currentLoaction != null) {
                String currentLocationLabel = wDriver.findElement( By.cssSelector( ".auCurrentLocationLabel" ) ).getText();
                if (currentLocationLabel.equals( "Current Location" )) {
                    logTestPass( wDriver, "Current Location label '" + currentLocationLabel + "' appears as Expected" );
                } else {
                    logTestFailure( wDriver, "Current Location label is inaccurate" );
                }
                String currentLocationValue = wDriver.findElement( By.cssSelector( ".auCurrentLocation" ) ).getText();
                if (currentLocationValue.equals( currentLoaction )) {
                    logTestPass( wDriver, "Current Location value '" + currentLoaction + "' appears as Expected" );
                } else {
                    logTestFailure( wDriver, "Current Location value is inaccurate. Expected " + currentLoaction + " but actual " + currentLocationValue );
                }

                String assignedLabel = wDriver.findElement( By.cssSelector( ".auAssignedLabel" ) ).getText();
                if (assignedLabel.equals( "Assigned" )) {
                    logTestPass( wDriver, "Assigned label '" + assignedLabel + "' appears as Expected" );
                } else {
                    logTestFailure( wDriver, "Assigned label is inaccurate" );
                }
                String assignedValue= null;
                if(wDriver.findElements( By.cssSelector( ".auAssigned" ) ).size() > 0){
                    assignedValue = wDriver.findElement( By.cssSelector( ".auAssigned" ) ).getText();
                    logTestPass( wDriver, "Assigned value '" + assignedValue + "' appears as Expected" );
                } else {
                    logTestFailure( wDriver, "Assigned value is inaccurate " + assignedValue);
                }
            }


        } catch (Exception e) {

            logTestFailure(wDriver, "Equipment Detail History is missing/labels not found" );

        }


    }


    //code below will allow you set assigned location as the home location
    public static void navigateToAssignedLocation(String equipmentId, String equipmentAssignedLocation, int day) throws IOException, InterruptedException {
        String url = LoginData.getLoginData( getUrl() );
        if (equipmentAssignedLocation.equals("QE11A")) {
            loginPage().goToBoardLocationByDate( url, "QE11/", Utilities.getDesiredDateInFormat( day, "yyyyMMdd" ) );
            wDriver.findElement( By.xpath( "//*[contains(@class,'dualgarage actionmenu')]//*[contains(@class,'arrow-down')]" ) ).click();
            wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 1 ).click();
            Thread.sleep(1400);
        } else if (equipmentAssignedLocation.equals( "QE11W" )) {
            loginPage().goToBoardLocationByDate( url, "QE11/", Utilities.getDesiredDateInFormat( day, "yyyyMMdd" ) );
            wDriver.findElement( By.xpath( "//*[contains(@class,'dualgarage actionmenu')]//*[contains(@class,'arrow-down')]" ) ).click();
            wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 0 ).click();
            Thread.sleep(1400);
        }else if (equipmentAssignedLocation.equals("QE13W")) {
            loginPage().goToBoardLocationByDate( url, "QE13/", Utilities.getDesiredDateInFormat( day, "yyyyMMdd" ) );
            wDriver.findElement( By.xpath( "//*[contains(@class,'dualgarage actionmenu')]//*[contains(@class,'arrow-down')]" ) ).click();
            wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 1 ).click();
            Thread.sleep(1400);
        } else if (equipmentAssignedLocation.equals( "QE13J" )) {
            loginPage().goToBoardLocationByDate( url, "QE13/", Utilities.getDesiredDateInFormat( day, "yyyyMMdd" ) );
            wDriver.findElement( By.xpath( "//*[contains(@class,'dualgarage actionmenu')]//*[contains(@class,'arrow-down')]" ) ).click();
            wDriver.findElements( By.xpath( "//*[contains(@class,'subitem highlight')]" ) ).get( 0 ).click();
            Thread.sleep(1400);
        } else {
            loginPage().goToBoardLocationByDate( url, ""+ equipmentAssignedLocation +"/", Utilities.getDesiredDateInFormat( day, "yyyyMMdd" ) );
        }

    }

}
