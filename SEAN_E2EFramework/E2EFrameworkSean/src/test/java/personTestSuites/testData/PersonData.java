package personTestSuites.testData;

import _driverScript.AbstractStartWebDriver;

import java.util.Arrays;
import java.util.Collections;

import static common.actions.CommonActions.getAnyCategoryCardsCount;
import static common.actions.CommonActions.logTestInfo;
import static common.data.LoginData.getLoginData;

/**
 * Created by sdas on 12/09/2016.
 */
public class PersonData extends AbstractStartWebDriver {
    //public static String testLocation;


    public static String[] locations = {"SI02", "SI03", "BKS10", "MN03","QE07", "QE11", "MN05", "MN09", "MN07", "BKS13", "BX05", "BX06", "BX07", "SI01", "BKN01","BX02", "QW02", "QW01", "MN10" };
    public static String date = getDesiredDateInFormat(0, "yyyyMMdd");

    public static String setPersonLocationForTest(String section, String subCategory, int quantity) throws InterruptedException {
        String url = getLoginData(AbstractStartWebDriver.url);
        String testLocation = null;
        Collections.shuffle(Arrays.asList(locations));;
        int headCount = 0;
        logTestInfo(wDriver, "Looking for a Testing Location with " + section + ": " +subCategory + " count >= " + quantity);
        while (headCount < quantity) {
            for (int i = 0; i < locations.length -1; i++) {
                loginPage().goToBoardLocationByDate(url, (locations[i]+ "/"), date);
                //Thread.sleep(2000);
                headCount = getAnyCategoryCardsCount("Personnel",section, subCategory, null);
                if (headCount >= quantity) {
                    testLocation = locations[i];
                    logTestInfo(wDriver, "Location Found is: " + testLocation + " with " + subCategory + " Count = " + headCount);
                    break;
                }
                if(i == locations.length -1){
                    break;
                }
            }

        }
        return testLocation;
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

/*    @Test()
    public void sampleTest() throws InterruptedException {
        setPersonLocationForTest("Unavailable", "CHART", 2);
        System.out.println(setPersonLocationForTest("Unavailable", "CHART", 2));
    }*/

}