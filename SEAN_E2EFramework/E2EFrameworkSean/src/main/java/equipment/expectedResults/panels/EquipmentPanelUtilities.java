package equipment.expectedResults.panels;

import _driverScript.AbstractStartWebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.velocity.test.provider.Child;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import utilities.Utilities;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.*;
import static equipment.abstractBase.EquipmentBasePage.equipmentSnowUpdateActions;

/**
 * Created by skashem on 10/5/2016.
 */
public class EquipmentPanelUtilities extends AbstractStartWebDriver {
    ExtentTest extentTest;
    public EquipmentPanelUtilities(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;
    }

    //line below will return any equipment category count from panel header
    public String getEquipmentCategoryCount(String categoryName, String subCategoryName, String childCategoryName) throws IOException {
        String headerCount = null;
        String headerCountReplace = null;
        String equipmentCategory = null;
        List<WebElement> equipmentList = null;
        int headerCountInt;
        if(subCategoryName == null){
            equipmentCategory = categoryName;
        } else {
            equipmentCategory = subCategoryName;
        }
        try{
            if(subCategoryName != null){
                 headerCount = wDriver.findElement(By.xpath("//*[contains(@class,'auEquipment"+ categoryName +"Panel')]//*[contains(@class,'au"+ subCategoryName +"')]//*[contains(@class,'group-count')]")).getText();
            } else {
                 headerCount = wDriver.findElement(By.xpath("//*[contains(@class,'auEquipment') and contains(@class,'" + categoryName + "')]//*[contains(@class,'group-count')]")).getText();
            }
            if(childCategoryName != null){
                headerCount = wDriver.findElement(By.xpath("//*[contains(@class,'auEquipment"+ categoryName +"Panel')]//*[contains(@class,'au"+ subCategoryName +"')]//*[contains(@class,'group-count') and contains(text(),'"+ childCategoryName +"')]")).getText();
            } //else {
                //headerCountReplace = headerCount.replace("(", "").replace(")", "");
                //headerCountInt = Integer.parseInt(headerCountReplace);
                headerCountInt = Utilities.extractNumbersFromAnyText(headerCount);
                headerCountReplace = Integer.toString(headerCountInt);
            //}

            try{
                if(subCategoryName != null){
                    wDriver.manage().timeouts().implicitlyWait( 4, TimeUnit.SECONDS );
                    equipmentList = wDriver.findElements(By.xpath("//*[contains(@class,'auEquipment"+ categoryName +"Panel')]//*[contains(@class,'au"+ subCategoryName +"')]//*[contains(@class,'auEquipmentId')]"));
                } else {
                    wDriver.manage().timeouts().implicitlyWait( 4, TimeUnit.SECONDS );
                    equipmentList = wDriver.findElements(By.xpath("//*[contains(@class,'auEquipment') and contains(@class,'" + categoryName + "')]//*[contains(@class,'auEquipmentId')]"));
                }
                //System.out.println("equipment list count in " + headerName + " is " + equipmentListSize);
                if(childCategoryName != null){
                    equipmentCategory = childCategoryName;
                    String childCat;
                    if(childCategoryName.equals("Recycling Misc")){
                        String[] arrayChildCat = childCategoryName.split(" ");
                        childCat = arrayChildCat[1];
                    } else {
                        childCat = childCategoryName;
                    }
                    wDriver.manage().timeouts().implicitlyWait( 4, TimeUnit.SECONDS );
                    equipmentList = wDriver.findElements(By.xpath("//*[contains(@class,'"+ subCategoryName + categoryName +"')]//*[contains(@class,'equipment-recycling-"+ childCat.toLowerCase() +"')]//*[contains(@class,'auEquipmentId')]"));
                }

                if(headerCountInt == equipmentList.size()){

                    //System.out.println("equipment cards count and category " + categoryName + " count matches and count number is " + headerCountInt);
                    logTestPass(wDriver, "equipment cards list count and category " + equipmentCategory + " count matches and count number is " + headerCountInt);


                } else {

                    //System.out.println("equipment cards count and category " + categoryName + " count does not match");
                    logTestFailure(wDriver, "equipment cards list count and category " + equipmentCategory + " count does not match. Equipment List count " + equipmentList.size() + " ,Equipment Category count " + headerCountInt);
                    getScreenShot(wDriver);
                }

            } catch(Exception e) {
                //System.out.println("equipment list count on category " + categoryName + " not found");
                if(subCategoryName != null) {
                    if (wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment" + categoryName + "Panel')]//*[contains(@class,'au" + subCategoryName + "')]//*[contains(@class,'auEquipmentId')]" ) ).size() == 0) {
                        getScreenShot(wDriver);
                        extentTest.log( LogStatus.INFO, "equipment count for " + equipmentCategory + " not found" );
                    }
                } else {
                    if (wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment') and contains(@class,'" + categoryName + "')]//*[contains(@class,'group-count')]" ) ).size() == 0) {
                        //getScreenShot( wDriver );
                        logTestFailure(wDriver, "equipment count for " + equipmentCategory + " not found" );
                    }
                }

                if(childCategoryName != null) {
                    if (wDriver.findElements(By.xpath("//*[contains(@class,'auEquipment"+ categoryName +"Panel')]//*[contains(@class,'au"+ subCategoryName +"')]//*[contains(@class,'equipment-"+ childCategoryName +"')]//*[contains(@class,'auEquipmentId')]")).size() == 0) {
                        getScreenShot(wDriver);
                        extentTest.log( LogStatus.INFO, "equipment count for " + equipmentCategory + " not found" );
                    }
                }
                logTestFailure(wDriver, "equipment list count on category " + equipmentCategory + " not found");
                getScreenShot(wDriver);
            }
        }catch(Exception e){
           // System.out.println("equipment count for " + categoryName + " not found");
            if(subCategoryName != null) {
                wDriver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
                if (wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment" + categoryName + "Panel')]//*[contains(@class,'au" + subCategoryName + "')]//*[contains(@class,'group-count')]" ) ).size() == 0) {
                    //getScreenShot(wDriver);
                    logTestPass(wDriver, "equipment count for " + equipmentCategory + " not found because there is no equipment for that category" );
                }
            } else {
                wDriver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
                if (wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment') and contains(@class,'" + categoryName + "')]//*[contains(@class,'group-count')]" ) ).size() == 0) {
                    //getScreenShot( wDriver );
                    logTestPass(wDriver, "equipment count for " + equipmentCategory + " not found" );
                }
            }

            if(childCategoryName != null) {
                wDriver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
                if (wDriver.findElements(By.xpath("//*[contains(@class,'auEquipment"+ categoryName +"Panel')]//*[contains(@class,'au"+ subCategoryName +"')]//*[contains(@class,'equipment-"+ childCategoryName +"')]//*[contains(@class,'auEquipmentId')]")).size() == 0) {
                    //getScreenShot(wDriver);
                    logTestPass(wDriver, "equipment count for " + equipmentCategory + " not found" );
                }
            }


        }

        return headerCountReplace;
    }



    //line below will allow you to get equipment category count after
    public String getEquipmentCategoryCountAfter(int countIncreaseDecrease, String categoryCountBefore, String categoryNameAfter, String subCategoryNameAfter, String childCategoryNameAfter) throws IOException {
        String equipmentCategory = null;
        String categoryCountAfter = null;
        int categoryCountBeforeInt;
        int categoryCountafterInt;
        if(subCategoryNameAfter == null){
            equipmentCategory = categoryNameAfter;
        } else {
            equipmentCategory = subCategoryNameAfter;
        }

       try {
            categoryCountAfter = getEquipmentCategoryCount(categoryNameAfter,subCategoryNameAfter,childCategoryNameAfter);
            categoryCountafterInt = Integer.parseInt(categoryCountAfter);
            categoryCountBeforeInt = Integer.parseInt(categoryCountBefore);
               if(countIncreaseDecrease == +1){
                   if (categoryCountafterInt == (categoryCountBeforeInt + 1)) {
                       logTestPass(wDriver, "category count for " + equipmentCategory + " is increased by one. Count before action was " +  categoryCountBefore + " and after is " + categoryCountAfter + " as expected");
                   } else {
                       //System.out.println( "category count for " + equipmentCategory + " inaccurate. Count number is " + categoryCountafterInt );
                       logTestFailure(wDriver, "category count for " + equipmentCategory + " is inaccurate. Count before action was " +  categoryCountBefore + " and after is " + categoryCountafterInt);
                       //getScreenShot( wDriver );
                   }
               }

           if(countIncreaseDecrease == -1){
               if (categoryCountafterInt == (categoryCountBeforeInt - 1)) {
                   logTestPass(wDriver, "category count for " + equipmentCategory + " is decreased by one. Count before action was " +  categoryCountBefore + " and after is " + categoryCountAfter + " as expected");
               } else {
                   //System.out.println( "category count for " + equipmentCategory + " inaccurate. Count number is " + categoryCountafterInt );
                   logTestFailure(wDriver, "category count for " + equipmentCategory + " is inaccurate. Count before action was " +  categoryCountBefore + " and after is " + categoryCountafterInt);
                   //getScreenShot( wDriver );
               }
           }

           if(countIncreaseDecrease == 0){
               if (categoryCountafterInt == categoryCountBeforeInt) {
                   logTestPass(wDriver, "category count for " + equipmentCategory + " is same as before. Count before action was " +  categoryCountBefore + " and after is " + categoryCountAfter + " as expected");
               } else {
                   //System.out.println( "category count for " + equipmentCategory + " inaccurate. Count number is " + categoryCountafterInt );
                   logTestFailure(wDriver, "category count for " + equipmentCategory + " is inaccurate. Count before action was " +  categoryCountBefore + " and after is " + categoryCountafterInt);
                   //getScreenShot( wDriver );
               }
           }

          // }
       } catch(Exception e) {
           //System.out.println("categoy count for " + categoryNameAfter + " is not present");
           if(subCategoryNameAfter != null) {
               wDriver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
               if (wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment" + categoryNameAfter + "Panel')]//*[contains(@class,'au" + subCategoryNameAfter + "')]//*[contains(@class,'group-count')]" ) ).size() == 0) {
                   //getScreenShot(wDriver);
                   logTestPass(wDriver, "equipment count for " + equipmentCategory + " not found after Action because there is no equipment for that category" );
               }
               if(childCategoryNameAfter != null) {
                   wDriver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
                   equipmentCategory = childCategoryNameAfter;
                   if (wDriver.findElements(By.xpath("//*[contains(@class,'auEquipment"+ categoryNameAfter +"Panel')]//*[contains(@class,'au"+ subCategoryNameAfter +"')]//*[contains(@class,'equipment-"+ childCategoryNameAfter +"')]//*[contains(@class,'auEquipmentId')]")).size() == 0) {
                       //getScreenShot(wDriver);
                       logTestPass(wDriver, "equipment count for " + equipmentCategory + " not found after Action because there is no equipment for that category" );
                   }
               }
           } else {
               wDriver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
               if (wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment') and contains(@class,'" + categoryNameAfter + "')]//*[contains(@class,'group-count')]" ) ).size() == 0) {
                   //getScreenShot( wDriver );
                   logTestPass(wDriver, "equipment count for " + equipmentCategory + " not found" );
               }
           }

       }

        return categoryCountAfter;
    }


    //line below will verify if equipment card is present in equipment category
    public void verifyEquipmentPresent(String categoryName, String subCategoryName, String childCategoryName, String equipmentId, String enterTrueFalseValue) throws IOException {
        wDriver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
        String equipmentCategory = null;
        List<WebElement> verifyElement = null;
        if(subCategoryName == null){
            equipmentCategory = categoryName;
        } else {
            equipmentCategory = subCategoryName;
        }
        if(childCategoryName != null){
            equipmentCategory = childCategoryName;
        }
        try {

            //executorClick(wDriver,waitForElementThenDo(wDriver,wDriver.findElement(By.cssSelector(".equipmentpane.taskspane equipment > equipment-list")),10));

            if (subCategoryName != null) {
                // executorScrollIntoView(wDriver,waitForElementThenDo(wDriver,wDriver.findElement( By.xpath( "//*[contains(@class,'auEquipment" + categoryName + "')]//*[contains(@class,'au" + subCategoryName + "')]//*[text()='" + equipmentId + "']" )),10));
                 verifyElement = wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment" + categoryName + "')]//*[contains(@class,'au" + subCategoryName + "')]//*[text()='" + equipmentId + "']" ));
            } else{
               // executorScrollIntoView(wDriver,waitForElementThenDo(wDriver,wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment" + categoryName + "')]//*[text()='" + equipmentId + "']" )).get(0),10));
                verifyElement = wDriver.findElements(By.xpath( "//*[contains(@class,'auEquipment" + categoryName + "')]//*[text()='" + equipmentId + "']" ));
            }

            if(childCategoryName != null){
                equipmentCategory = childCategoryName;
                String childCat;
                if(subCategoryName.equals("RECYCLING")) {
                    if (childCategoryName.equals( "Recycling Misc" )) {
                        String[] arrayChildCat = childCategoryName.split( " " );
                        childCat = arrayChildCat[1];
                    } else {
                        childCat = childCategoryName;
                    }
                   // executorScrollIntoView(wDriver,waitForElementThenDo(wDriver,wDriver.findElement( By.xpath( "//*[contains(@class,'" + subCategoryName + categoryName + "')]//*[contains(@class,'equipment-recycling-" + childCat.toLowerCase() + "')]//*[text()='" + equipmentId + "']" ) ),10));
                    verifyElement = wDriver.findElements( By.xpath( "//*[contains(@class,'" + subCategoryName + categoryName + "')]//*[contains(@class,'equipment-recycling-" + childCat.toLowerCase() + "')]//*[text()='" + equipmentId + "']" ) );
                } else {
                    //executorScrollIntoView(wDriver,waitForElementThenDo(wDriver,wDriver.findElement(By.xpath("//*[contains(@class,'auEquipment" + categoryName + "')]//*[contains(@class,'" + subCategoryName + "')]//*[contains(@class,'" + childCategoryName.toUpperCase() + "')]//*[text()='" + equipmentId + "']" ) ),10));
                    verifyElement = wDriver.findElements(By.xpath("//*[contains(@class,'auEquipment" + categoryName + "')]//*[contains(@class,'" + subCategoryName + "')]//*[contains(@class,'" + childCategoryName.toUpperCase() + "')]//*[text()='" + equipmentId + "']" ) );
                }
            }

                    if(enterTrueFalseValue.contains("true")){
                                try {
                                        if(verifyElement.size() > 0) {
                                            //System.out.println("Pass - equipment " + equipmentId + " found in category " + categoryName);
                                            logTestPass(wDriver, "equipment " + equipmentId + " is found in category " + equipmentCategory + " as expected" );
                                        } else {
                                            //System.out.println("Fail - equipment " + equipmentId + " not found in category " + categoryName);
                                            executorScrollIntoView( wDriver,wDriver.findElement(By.xpath("//*[contains(text(),'"+ equipmentId +"')]")));
                                            logTestFailure(wDriver, "equipment " + equipmentId + " not found in category " + equipmentCategory);
                                            //getScreenShot( wDriver );
                                        }

                                    } catch(Exception e){
                                        logTestInfo(wDriver,"equipment category " + equipmentCategory + " not found" );
                                        //getScreenShot( wDriver );
                                        //Assert.fail();
                                }
                    } else if(enterTrueFalseValue.contains("false")){
                        try{

                                if (verifyElement.size() == 0) {
                                    //System.out.println("Pass - equipment " + equipmentId + " found in category " + categoryName);
                                    logTestPass(wDriver, "equipment " + equipmentId + " not found in category " + equipmentCategory + " as expected");
                                } else {
                                    //System.out.println("Fail - equipment " + equipmentId + " not found in category " + categoryName);
                                    executorScrollIntoView( wDriver,wDriver.findElement(By.xpath("//*[contains(text(),'"+ equipmentId +"')]")));
                                    logTestFailure(wDriver, "equipment " + equipmentId + " found in category " + equipmentCategory );
                                    //getScreenShot( wDriver );
                                }
                        } catch(Exception e) {
                                logTestInfo(wDriver, "equipment category " + equipmentCategory + " not found");
                                //getScreenShot(wDriver);
                                //Assert.fail();
                            }
                    }

        } catch(Exception e){
                            //System.out.println("Element not found");
                            logTestFailure(wDriver, "Equipment Category " + equipmentCategory +  " is not found");
                            Assert.fail();
        }

    }


    //line below will will return a equipment within a specific category
    public String getEquipmentFromCategory(String binNonBin,String categoryName, String subCategoryName, String... Criteria) throws IOException, InterruptedException {
        Thread.sleep(1700);
        String equipmentId = null;
        WebElement element = null;
        String equipmentCardColor;
        String equipmentCategory = null;
        String downAction = null;
        String condition = null;
        String childCategoryName = null;
        int categoryCountint;

        if(Criteria.length >= 1)
        {
            downAction = Criteria[0];
        }
        if(Criteria.length > 1)
        {
            childCategoryName = Criteria[1];
        }
        if(subCategoryName == null && childCategoryName == null){
            equipmentCategory = categoryName;
        } else if(subCategoryName != null && childCategoryName == null){
            equipmentCategory = subCategoryName;
        } else if(subCategoryName != null && childCategoryName != null ){
            equipmentCategory = childCategoryName;
        }
        try{
                if(subCategoryName != null) {
                    categoryCountint = getAnyCategoryCardsCount( "Equipment", categoryName, subCategoryName, null );
                    Thread.sleep( 700 );
                } else {
                    categoryCountint = getAnyCategoryCardsCount( "Equipment", categoryName, null, null );
                    Thread.sleep( 700 );
                }
                for(int i = 0; i <= categoryCountint - 1; i++) {
                    if (subCategoryName != null && childCategoryName == null) {
                        element = wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment" + categoryName + "Panel')]//*[contains(@class,'au" + subCategoryName + "')]//*[contains(@class,'auEquipmentId')]" ) ).get( i );
                    } else if(subCategoryName == null && childCategoryName == null) {
                        element = wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment') and contains(@class,'" + categoryName + "')]//*[contains(@class,'auEquipmentId')]" ) ).get( i );
                    } else if(subCategoryName != null && childCategoryName != null ){
                        element = wDriver.findElements( By.xpath( "//*[contains(@class,'auEquipment" + categoryName + "Panel')]//*[contains(@class,'au" + subCategoryName + "')]//*[contains(@class,'" + childCategoryName.toUpperCase() + "')]//*[contains(@class,'auEquipmentId')]" ) ).get( i );
                    }
                    equipmentCardColor = wDriver.findElement( By.xpath( "//div[contains(@data-id,'" + element.getText() + "')]" ) ).getCssValue( "background-color" );
                    //direction = wDriver.findElement( By.xpath( "//*[contains(@data-id,'" + element.getText() + "')]//*[contains(@class,'auPlowDirection')]" ) ).getText();
                    element.click();
                    condition = wDriver.findElement( By.cssSelector( ".auCondition" ) ).getText();
                        if(condition.equals(downAction)) {
                        } else{
                            if (binNonBin.toLowerCase().equals( "bin" )) {
                                if (wDriver.findElements( By.xpath( "//*[contains(@data-id,'" + element.getText() + "')]//*[contains(@class,'bin-type')]" ) ).size() > 0) {
                                    wDriver.manage().timeouts().implicitlyWait( 1, TimeUnit.SECONDS );
                                    if (!equipmentCategory.equals( "Snow" )) {
                                        if (equipmentCardColor.equals( "rgba(225, 90, 0, 1)" )) {
                                            //line below will perform Snow Update
                                            equipmentId = element.getText();
                                            equipmentSnowUpdateActions().snowUpdate( equipmentId, "None", null, "Not Chained", null, null, null );
                                            logTestInfo( wDriver, "equipment " + equipmentId + " is selected from category " + equipmentCategory );
                                            break;
                                        } else {
                                            equipmentId = element.getText();
                                            logTestInfo( wDriver, "equipment " + equipmentId + " is selected from category " + equipmentCategory );
                                            break;
                                        }
                                    } else {
                                        equipmentId = element.getText();
                                        logTestInfo( wDriver, "equipment " + equipmentId + " is selected from category " + equipmentCategory );
                                        break;
                                    }
                                }
                            }
                            if (binNonBin.toLowerCase().equals( "nonbin" )) {
                                //if (!direction.equals( "" )) {
                                //} else {
                                equipmentId = element.getText();
                                logTestPass( wDriver, "equipment " + equipmentId + " is selected from category " + equipmentCategory );
                                break;
                                //}

                            }

                        }// end of if condition 'Down" is true/false


                }//end of for loop
                 closeDetailPanel( "Equipment" );
                /*if(equipmentId == null){
                    Assert.fail();
                }*/

        } catch(Exception e) {
            //System.out.println("element not found");
            logTestFailure(wDriver, "equipment category " + equipmentCategory + " is not found");
            Assert.fail();

        }
        return equipmentId;
    }


    //line below will return Equipment Bin type
    public void equipmentBinType(String equipmentId, String bin1, String bin2) throws IOException {

            //boolean bin1Exist = wDriver.findElements(By.xpath("//*[contains(text(),'"+ equipmentId +"')]//*[contains(@class,'material-type bin-type')]")).size() > 0;
            try{
                String bin1Text = wDriver.findElements(By.xpath("//*[contains(@data-id,'" + equipmentId + "')]//*[contains(@class,'bin-type')]" )).get(0).getText();
                if(bin1Text.toString().equals(bin1.toString())){
                    logTestPass(wDriver, "equipment " + equipmentId + " bin1 indicator is found as expected- " + bin1);
                } else {
                    logTestFailure(wDriver, "equipment " + equipmentId + " bin1 indicator expected " + bin1 + " doesn't match with actual - " + bin1Text);
                    //getScreenShot(wDriver);
                }
            }catch(Exception e){

                logTestFailure(wDriver, "equipment " + equipmentId + " bin1 indicator is not found");
                getScreenShot(wDriver);
            }

            if(bin2 != null) {
                try {
                    String bin2Text = wDriver.findElements(By.xpath("//*[contains(@data-id,'" + equipmentId + "')]//*[contains(@class,'bin-type')]" )).get(1).getText();
                    if (bin2Text.toString().equals( bin2.toString() )) {
                        logTestPass(wDriver, "equipment " + equipmentId + " bin2 indicator is found as expected - " + bin2 );
                    } else {
                        logTestFailure(wDriver, "equipment " + equipmentId + " bin2 indicator expected " + bin2 + " doesn't match with actual - " + bin2Text );
                        ////getScreenShot( wDriver );
                    }
                } catch (Exception e) {
                    logTestFailure(wDriver, "equipment " + equipmentId + " bin2 indicator is not found" );
                    //getScreenShot( wDriver );
                }

            }

    }

    //line below will return Equipment snow indicators
    public void getEquipmentSnowIndicator(String equipmentId, String plowDirection, String chain) throws IOException {

        if(plowDirection != null) {
            try {
                String direction = wDriver.findElement( By.xpath( "//*[contains(@data-id,'" + equipmentId + "')]//*[contains(@class,'auPlowDirection')]" )).getText();
                if (direction.toString().equals( plowDirection.toString() )) {
                    logTestPass(wDriver, "equipment " + equipmentId + " Plow Direction indicator is found as expected - " + plowDirection );
                } else {
                    logTestFailure(wDriver, "equipment " + equipmentId + " Plow Direction indicator expected " + plowDirection + " doesn't match with actual " + direction );
                    //getScreenShot( wDriver );
                }
            } catch (Exception e) {
                logTestFailure(wDriver, "equipment " + equipmentId + " Plow Direction indicator is not found" );
                //getScreenShot( wDriver );
            }
        }

        if(chain != null) {
            try {
                String chainText = wDriver.findElements( By.xpath( "//*[contains(@data-id,'" + equipmentId + "')]//*[contains(@class,'bin-type')]" ) ).get( 1 ).getText();
                if (chainText.toString().equals( chain.toString() )) {
                    logTestPass(wDriver, "equipment " + equipmentId + " Chained indicator is found as expected - " + chain );
                } else {
                    logTestFailure(wDriver, "equipment " + equipmentId + " Chained indicator expected " + chain + " doesn't match with actual " + chainText );
                    //getScreenShot( wDriver );
                }
            } catch (Exception e) {
                logTestFailure(wDriver, "equipment " + equipmentId + " Chained indicator is not found" );
                //getScreenShot( wDriver );
            }

        }

    }


    public void verifyEquipmentModalWindow(String equipmentId,String actionName, String plowTypeDisabledEnabled, String plowDirectionsDisabledEnabled, String chainDisabledEnabled, String downSnowAvailabilityDisabledEnabled, String snowAssignEnabledDisabled) throws IOException, InterruptedException {

        if(actionName.equals("Snow Update")){

            String plowTypeLabel, plowDirectionsLabel, chainLabel, workingDownLabel = null, snowAssignmentlabel;

            //line below will click on equipment
            smartBoardPage().openEquipmentCardDetailPanel(equipmentId);
            //line below will open Snow Update Modal Window
            equipmentDetailPanelPage().getSnowUpdatePanel();
            //line below will wait for modal window
            Thread.sleep( 2000 );

           if(wDriver.findElements(By.xpath( "//label[contains(text(),'Plow Type')]")).size() > 0) {
               plowTypeLabel = wDriver.findElement( By.xpath( "//label[contains(text(),'Plow Type')]" ) ).getText();
               if(plowTypeLabel.equals( "Plow Type:" )){
               } else {
                   logTestFailure(wDriver,"label name is inaccurate. Expected Plow Type but actual " + plowTypeLabel);
               }
           } else {
               logTestFailure(wDriver, "Plow Type Label is not found");
           }

           if(wDriver.findElements(By.cssSelector("#plowtype div.ui-select-match > span")).size() > 0){
               if(!plowTypeDisabledEnabled.toLowerCase().contains("disabled")) {
                   wDriver.findElements( By.cssSelector( "#plowtype div.ui-select-match > span" ) ).get( 0 ).click();
                   String plowTypeList = wDriver.findElement(By.cssSelector("#plowtype > div > ul")).getText();
                   //System.out.println("plow type list is " + plowTypeList);
                   if(plowTypeList.contains("Regular Plow") && plowTypeList.contains("Mini V Plow") && plowTypeList.contains("Large V Plow")){
                       logTestPass(wDriver, "accurate drop down list for Plow Type appears as expected " + plowTypeList);
                   } else {
                       logTestFailure(wDriver, "inaccurate drop down list for Plow Type appears");
                   }
                   wDriver.findElement(By.cssSelector("#plowtype > div > input")).sendKeys(plowTypeDisabledEnabled);
                   wDriver.findElement(By.cssSelector("#plowtype > div > ul")).click();
               } else if(plowTypeDisabledEnabled.toLowerCase().contains("disabled")){
                   boolean plowTypeDisabled = wDriver.findElements(By.xpath( "//*[contains(@class,'auPlowType')]//*[contains(@class,'ui-select') and contains(@style,'not-allowed')]" )).size() > 0;
                   if(plowTypeDisabled == true){
                       logTestPass(wDriver,"Plow Type drop down is disabled as expected for equipment " + equipmentId);
                   } else {
                       logTestFailure(wDriver,"Plow Type is not disabled for equipment " + equipmentId);
                   }
               }
           } else {
               logTestFailure(wDriver, "Plow Type Drop Down is not found");
           }


            if(wDriver.findElements(By.xpath( "//label[contains(text(),'Plow Directions')]")).size() > 0) {
                plowDirectionsLabel = wDriver.findElement( By.xpath( "//label[contains(text(),'Plow Directions')]" ) ).getText();
                if(plowDirectionsLabel.equals( "Plow Directions:" )){
                } else {
                    logTestFailure(wDriver,"label name is inaccurate. Expected Plow Directions but actual " + plowDirectionsLabel);
                }
            } else {
                logTestFailure(wDriver, "Plow Directions Label is not found");
            }

            if(wDriver.findElements(By.cssSelector("#plowdirections .ui-select-match > span")).size() > 0){
                if(!plowDirectionsDisabledEnabled.toLowerCase().contains("disabled")) {
                    wDriver.findElements( By.cssSelector( "#plowdirections > div > div.ui-select-match > span" ) ).get( 0 ).click();
                    String plowDirectionsList = wDriver.findElement(By.cssSelector("#plowdirections > div > ul")).getText();
                    if(plowDirectionsList.contains("Right") && plowDirectionsList.contains("Left") &&  plowDirectionsList.contains("Straight") &&  plowDirectionsList.contains("Straight w/Wings")){
                        logTestPass(wDriver, "accurate drop down list for Plow Directions appears as expected " + plowDirectionsList);
                    } else {
                        logTestFailure(wDriver, "inaccurate drop down list for Plow Directions appears");
                    }
                    wDriver.findElement(By.cssSelector("#plowdirections > div > input")).sendKeys(plowDirectionsDisabledEnabled);
                    wDriver.findElement(By.cssSelector("#plowdirections > div > ul")).click();
                } else if(plowDirectionsDisabledEnabled.toLowerCase().contains("disabled")){
                    boolean plowDirectionsDisabled = wDriver.findElements(By.xpath( "//*[contains(@class,'auplowDirections')]//*[contains(@class,'ui-select') and contains(@style,'not-allowed')]" )).size() > 0;
                    if(plowDirectionsDisabled == true){
                        logTestPass(wDriver,"Plow Directions drop down is disabled as expected for equipment " + equipmentId);
                    } else {
                        logTestFailure(wDriver,"Plow Directions is not disabled for equipment " + equipmentId);
                    }
                }
            } else {
                logTestFailure(wDriver, "Plow Directions Drop Down is not found");
            }


            if(wDriver.findElements(By.xpath( "//label[contains(text(),'Chain')]")).size() > 0) {
                chainLabel = wDriver.findElement( By.xpath( "//label[contains(text(),'Chain')]" ) ).getText();
                if(chainLabel.equals( "Chain:" )){
                } else {
                    logTestFailure(wDriver,"label name is inaccurate. Expected Chain but actual " + chainLabel);
                }
            } else {
                logTestFailure(wDriver, "Chain Label is not found");
            }
            if(wDriver.findElements(By.cssSelector("#chain .ui-select-match > span")).size() > 0){
                if(!chainDisabledEnabled.toLowerCase().contains("disabled")) {
                    wDriver.findElements( By.cssSelector( "#chain > div > div.ui-select-match > span" ) ).get( 0 ).click();
                    String chainList = wDriver.findElement(By.cssSelector("#chain > div > ul")).getText();
                    if(chainList.contains("Chained") && chainList.contains("Not Chained")){
                        logTestPass(wDriver, "accurate drop down list for Chain appears as expected " + chainList);
                    } else {
                        logTestFailure(wDriver, "inaccurate drop down list for Chain appears ");
                    }
                    wDriver.findElement(By.cssSelector("#chain > div > input")).sendKeys(chainDisabledEnabled);
                    wDriver.findElement(By.cssSelector("#chain > div > ul")).click();
                } else if(chainDisabledEnabled.toLowerCase().contains("disabled")){
                    boolean chainDisabled = wDriver.findElements(By.xpath( "//*[contains(@class,'auChain')]//*[contains(@class,'ui-select') and contains(@style,'not-allowed')]" )).size() > 0;
                    if(chainDisabled == true){
                        logTestPass(wDriver,"Chain drop down is disabled as expected for equipment " + equipmentId);
                    } else {
                        logTestFailure(wDriver,"Chain is not disabled for equipment " + equipmentId);
                    }
                }
            } else {
                logTestFailure(wDriver, "Chain Drop Down is not found");
            }

            if(wDriver.findElements(By.xpath( "//label[contains(text(),'Working Down')]")).size() > 0) {
                workingDownLabel = wDriver.findElement( By.xpath( "//label[contains(text(),'Working Down')]" ) ).getText();
                if(workingDownLabel.equals( "Working Down:" )){
                } else {
                    logTestFailure(wDriver,"label name is inaccurate. Expected Working Down but actual " + workingDownLabel);
                }
            } else if(wDriver.findElements(By.xpath( "//label[contains(text(),'Available for Snow')]")).size() > 0) {
                workingDownLabel = wDriver.findElement( By.xpath( "//label[contains(text(),'Available for Snow')]" ) ).getText();
                if(workingDownLabel.equals( "Available for Snow:" )){
                } else {
                    logTestFailure(wDriver,"label name is inaccurate. Expected Available for Snow but actual " + workingDownLabel);
                }
            } else {
                logTestFailure(wDriver, workingDownLabel + " Label is not found");
            }
            if(wDriver.findElements(By.cssSelector("#workingdown .ui-select-match > span")).size() > 0){
                if(!downSnowAvailabilityDisabledEnabled.toLowerCase().contains("disabled")) {
                    wDriver.findElements( By.cssSelector( "#workingdown > div > div.ui-select-match > span" ) ).get( 0 ).click();
                    String workingDownList = wDriver.findElement(By.cssSelector("#workingdown > div > ul")).getText();
                    if(workingDownList.contains("Yes") && workingDownList.contains("No")){
                        logTestPass(wDriver, "accurate drop down list for " + workingDownLabel + " appears as expected " + workingDownList);
                    } else {
                        logTestFailure(wDriver, "inaccurate drop down list for " + workingDownLabel + " appears ");
                    }
                    wDriver.findElement(By.cssSelector("#workingdown > div > input")).sendKeys(downSnowAvailabilityDisabledEnabled);
                    wDriver.findElement(By.cssSelector("#workingdown > div > ul")).click();
                } else if(downSnowAvailabilityDisabledEnabled.toLowerCase().contains("disabled")){
                    boolean workingDownDisabled = wDriver.findElements(By.xpath( "//*[contains(@class,'auWorkingDown')]//*[contains(@class,'ui-select') and contains(@style,'not-allowed')]" )).size() > 0;
                    if(workingDownDisabled == true){
                        logTestPass(wDriver,workingDownLabel + " drop down is disabled as expected for equipment " + equipmentId);
                    } else {
                        logTestFailure(wDriver,workingDownLabel + " is not disabled for equipment " + equipmentId);
                    }
                }
            } else {
                logTestFailure(wDriver, workingDownLabel + " Drop Down is not found");
            }


            if(wDriver.findElements(By.xpath( "//label[contains(text(),'Snow Assignment')]")).size() > 0) {
                snowAssignmentlabel = wDriver.findElement( By.xpath( "//label[contains(text(),'Snow Assignment')]" ) ).getText();
                if(snowAssignmentlabel.equals( "Snow Assignment:" )){
                } else {
                    logTestFailure(wDriver,"label name is inaccurate. Expected Snow Assignment but actual " + snowAssignmentlabel);
                }
            } else {
                logTestFailure(wDriver, "Snow Assignment Label is not found");
            }
            if(wDriver.findElements(By.cssSelector("#snowAssignment div.ui-select-match > span")).size() > 0){
                if(!snowAssignEnabledDisabled.toLowerCase().contains("disabled")) {
                    wDriver.findElements( By.cssSelector( "#snowAssignment > div > div.ui-select-match > span" ) ).get( 0 ).click();
                    String snowAssignList = wDriver.findElement(By.cssSelector("#snowAssignment > div > ul")).getText();
                    if(snowAssignList.contains("Yes") && snowAssignList.contains("No")){
                        logTestPass(wDriver, "accurate drop down list for Snow Assignment appears as expected " + snowAssignList);
                    } else {
                        logTestFailure(wDriver, "inaccurate drop down list for Snow Assignment appears ");
                    }
                    wDriver.findElement(By.cssSelector("#snowAssignment > div > input")).sendKeys(snowAssignEnabledDisabled);
                    wDriver.findElement(By.cssSelector("#snowAssignment > div > ul")).click();
                } else if(snowAssignEnabledDisabled.toLowerCase().contains("disabled")){
                    boolean snowAssignmentDisabled = wDriver.findElements(By.xpath("//*[contains(@id,'snowAssignment')]//*[contains(@style,'cursor: not-allowed')]" )).size() > 0;
                    //System.out.println("Snow assignment disabled? " + snowAssignmentDisabled);
                    if(snowAssignmentDisabled == true){
                        logTestPass(wDriver,"Snow Assignment drop down is disabled as expected for equipment " + equipmentId);
                    } else {
                        logTestFailure(wDriver,"Snow Assignement is not disabled for equipment " + equipmentId);
                    }
                }
            } else {
                logTestFailure(wDriver, "Snow Assignement Drop Down is not found");
            }


            try{

                executorClick(wDriver,wDriver.findElement(By.cssSelector(".btn.btn-link.auCancel")));

            } catch(Exception e ) {

                logTestFailure(wDriver,"modal window didn't close after clicking on cancel button");
            }


        }



    }





}// closure for parent classs
