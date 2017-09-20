package equipmentTestSuites.testData;

import _driverScript.AbstractStartWebDriver;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static common.actions.CommonActions.getAnyCategoryCardsCount;
import static common.actions.CommonActions.logTestInfo;
import static common.data.LoginData.getLoginData;

/**
 * Created by sdas on 10/28/2016.
 */
public class EquipmentData extends AbstractStartWebDriver {
    public static String equipmentSendingLocation;
    public static String equipmentReceivingLocation = "MN11";


    public static String[] locations = {"QW02","BKS10", "MN01", "MN07", "SI02", "BKN03", "BKN01","BX02", "QE10", "QE11", "QE13","MN05", "MN06", "BKS13", "BX03", "BX06", "BX07", "SI02", "SI03"};
    public static String date = null;

    public static void setEquipmentLocationForTest(String section, String subCategory, int quantity, String... criteria) throws InterruptedException {
        String subCategory2 = null;
        String dateNumber = null;
        int i = 0;
        if (criteria.length == 1) {
            subCategory2 = criteria[0];
        }
        if (criteria.length > 1) {
            dateNumber = criteria[1];
        }

        if(dateNumber != null) {
            int intDateNumber = Integer.parseInt( dateNumber );
            date = getDesiredDateInFormat(intDateNumber, "yyyyMMdd");
        } else {
            date = getDesiredDateInFormat(0, "yyyyMMdd");
        }
        String url = getLoginData( AbstractStartWebDriver.url );
        int headCount1 = 0;
        int headCount = 0;
        int headCount2 = 0;
        int mainHeaderCount = 0;
        if (subCategory2 != null) {
            logTestInfo( wDriver, "Looking for a Location with " + section + ": " + subCategory + " and " + subCategory2 + " count >= " + quantity );
        } else {
            logTestInfo( wDriver, "Looking for a Location with " + section + ": " + subCategory + " count >= " + quantity );
        }
        for (i = 0; i < locations.length - 1; i++) {
            loginPage().goToBoardLocationByDate( url, (locations[i] + "/"), date );
            Thread.sleep(2000);
            if (subCategory2 != null) {
                    headCount = getAnyCategoryCardsCount( "Equipment", section, subCategory,null);
                if(subCategory.equals("Snow") || subCategory.equals("Miscellaneous") || subCategory.equals("RECYCLING")) {
                    headCount2 = getAnyCategoryCardsCount( "Equipment", section, subCategory, subCategory2 );
                } else {
                    headCount2 = getAnyCategoryCardsCount( "Equipment", section, subCategory2, null );

                }
            } else if (subCategory != null && subCategory2 == null) {
                headCount = getAnyCategoryCardsCount( "Equipment", section, subCategory, null );
            } else if (subCategory == null) {
                headCount = getAnyCategoryCardsCount( "Equipment", section, null, null );
            }
                if (subCategory2 != null) {
                        if (headCount >= quantity && headCount2 >= quantity) {
                            equipmentSendingLocation = locations[i];
                            logTestInfo( wDriver, "Location Found is: " + equipmentSendingLocation + " with " + subCategory + " Count = " + headCount + " and " + subCategory2 + " Count = " + headCount2 );
                            break;
                        }

                } else if (subCategory != null && subCategory2 == null) {
                    if (headCount >= quantity) {
                        equipmentSendingLocation = locations[i];
                        logTestInfo( wDriver, "Location Found is: " + equipmentSendingLocation + " with " + subCategory + " Count = " + headCount );
                        break;
                    }
                } else if (subCategory == null) {
                    if (headCount >= quantity) {
                        equipmentSendingLocation = locations[i];
                        logTestInfo( wDriver, "Location Found is: " + equipmentSendingLocation + " with " + section + " Count = " + headCount );
                        break;
                    }
                }

        /*        if(locations[i].equals("QE11")){
                    locations[i] = "QE11W";
                }*/
            if (subCategory2 != null) {
                if(headCount >= quantity && headCount2 >= quantity) {
                    break;
                }
            } else if(headCount >= quantity) {
                    break;
            }
        }

        //return locations[i];
    }

    //line below will get dual garage location
    public static String verifyLocationIsDualGarage(String location){
        wDriver.manage().timeouts().implicitlyWait( 2, TimeUnit.SECONDS );
        if(wDriver.findElements(By.xpath("//*[contains(@class,'sortmenu dualgarage')]")).size() > 0) {
            if (wDriver.findElements( By.xpath( "//*[text()='QE11W']" ) ).size() > 0) {
                location = "QE11W";
            } else if (wDriver.findElements( By.xpath( "//*[text()='QE13J']" ) ).size() > 0) {
                location = "QE13J";
            }
        }

        return location;
    }



    public static String getNonCurrentLocation(String currentLocation){
        String location = null;
        for(int i = 0; i <= locations.length -1; i++){
            if(!currentLocation.equals(locations[i])){
                location = locations[i];
                break;
            }
        }

        return location;
    }

    public static String getDifferentLocation(String assignedLocation, String detachToLocation){
        String location = null;
        for(int i = 0; i <= locations.length -1; i++){
            if(!assignedLocation.equals(locations[i]) && !detachToLocation.equals(locations[i])){
                location = locations[i];
                break;
            }
        }

        return location;
    }
}
