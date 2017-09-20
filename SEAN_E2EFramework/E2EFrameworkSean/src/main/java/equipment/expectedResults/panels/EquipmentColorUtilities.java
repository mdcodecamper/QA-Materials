package equipment.expectedResults.panels;

import _driverScript.AbstractStartWebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.io.IOException;

import static common.actions.CommonActions.getRawElementColor;
import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestPass;
import static common.data.RawColorCodes.getRGBAColorName;

/**
 * Created by skashem on 10/11/2016.
 */
public class EquipmentColorUtilities extends AbstractStartWebDriver{

    ExtentTest extentTest;
    public EquipmentColorUtilities(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
        this.extentTest = super.extentTest;

    }

    public void verifyEquipmentCardColor(String equipmentId, String panelName, String equipmentExpectedColor) throws IOException, InterruptedException {
        try {
            Thread.sleep(1000);
            String equipmentActualColor = getRGBAColorName(getRawElementColor(equipmentId, panelName));
            Reporter.log("Actual equipmentCardColor: " + equipmentActualColor + " and Expected equipmentCardColor: " + equipmentExpectedColor, true);
            if (equipmentActualColor.equals(equipmentExpectedColor)) {
                    logTestPass(wDriver, "Equipment: " + equipmentId + " equipmentActualColor: " + equipmentActualColor + " matches with equipmentExpectedColor: " + equipmentExpectedColor);
                } else {
                executorScrollIntoView(wDriver,wDriver.findElement( By.xpath( "//*[contains(@data-id,'"+ equipmentId +"')]" )));
                logTestFailure(wDriver, "Equipment: " + equipmentId + " equipmentActualColor: " + equipmentActualColor + " does NOT match with equipmentExpectedColor: " + equipmentExpectedColor);
                    getScreenShot(wDriver);
                }

        }catch(Exception e){
            extentTest.log( LogStatus.FAIL, "Error Getting Background Color for Equipment Card: " + equipmentId );
            getScreenShot(wDriver);
        }
    }


    public void verifyEquipmentCardRowTopColor(String equipmentId,String expectedCardColor, boolean expectedBlack) throws IOException, InterruptedException {
        try {
            String equipmentActualColor = wDriver.findElement(By.xpath("//*[contains(@data-id,'"+ equipmentId +"')]//*[contains(@class,'cardrow-top')]")).getCssValue("background-color");
            String equipmentActCardColor = getRGBAColorName(equipmentActualColor);
            if(expectedBlack == true) {
                if (equipmentActCardColor.equals(expectedCardColor.toLowerCase())) {
                    logTestPass( wDriver, "Equipment: " + equipmentId + " equipment Actual Color for card row top: " + equipmentActCardColor + " matches with equipment Expected Color for card row top: " + expectedCardColor.toLowerCase() );
                } else {
                    logTestFailure( wDriver, "Equipment: " + equipmentId + " equipment Actual Color for card row top: " + equipmentActCardColor + " does NOT match with equipment Expected Color card row top: " + expectedCardColor.toLowerCase() );
                }
            } else {
                if (equipmentActCardColor == null){
                    logTestPass( wDriver, "Equipment: " + equipmentId + " card row top color is not black as expected" );
                } else {
                    logTestFailure( wDriver, "Equipment: " + equipmentId + " card row top color is black");
                }
            }
        }catch(Exception e){
            logTestFailure( wDriver, "Error Getting card row top Color for Equipment Card: " + equipmentId );
        }
    }


    // code below will return equipment card text color
    public void verifyEquipmentTextColor(String equipmentId, String equipmentTextColor) throws IOException {
        String[] arrayCardTextColor;
        String equipmentTextColor2 = null;
        try {
            String equipmentCardTextColor = wDriver.findElement(By.xpath( "//div[contains(@data-id,'" + equipmentId + "')]" ) ).getCssValue("color");
            arrayCardTextColor = equipmentCardTextColor.split("rgba");
            equipmentTextColor2 = arrayCardTextColor[1].replace( "(", "" ).replace( ")", "" );
            if (equipmentTextColor.equals( "black" )) {
                String black = "0, 0, 0, 1";
                if(equipmentTextColor2.toString().equals(black.toString())){
                    logTestPass( wDriver, "Equipment expected text color black for equipment " + equipmentId + " matches with actual text color");
                } else {
                    logTestFailure(wDriver, "Expected text color for equipment " + equipmentId + " is " +  black + " but actual card color is " + equipmentTextColor2);
                }
            }

            if (equipmentTextColor.equals( "red" )) {
                String red = "255, 0, 0, 1";
                if(equipmentTextColor2.toString().equals(red.toString())){
                    logTestPass( wDriver, "Equipment expected text color red for equipment " + equipmentId + " matches with actual text color");
                } else {
                    logTestFailure(wDriver, "Expected text color for equipment " + equipmentId + " is " +  red + " but actual card color is " + equipmentTextColor2);
                }
            }


        } catch(Exception e){
            logTestFailure( wDriver, "equipment card text color value for equipment " + equipmentId + " is not found");
        }

        //return backGroundcolor;
    }

    //line below will return Equipment material Color
    public void equipmentBinColor(String equipmentId, String material1, String material2) throws IOException {

        //boolean bin1Exist = wDriver.findElements(By.xpath("//*[contains(text(),'"+ equipmentId +"')]//*[contains(@class,'material-type bin-type')]")).size() > 0;
        if(material1 != null) {
            try {
                String bin1Text = wDriver.findElements( By.xpath( "//*[contains(@data-id,'" + equipmentId + "')]//*[contains(@class,'bin-type')]" ) ).get( 0 ).getCssValue( "background-color" );
                String equipmentActualBinColor = getRGBAColorName( bin1Text.toString() );
                // System.out.println("color is " + equipmentActualBinColor);
                if (equipmentActualBinColor.equals( material1.toString() )) {
                    logTestPass( wDriver, "equipment " + equipmentId + " bin1 indicator material color is found as expected- " + material1 );
                } else {
                    logTestFailure( wDriver, "equipment " + equipmentId + " bin1 indicator expected " + material1 + " doesn't match with actual - " + equipmentActualBinColor );
                }
            } catch (Exception e) {

                logTestFailure( wDriver, "equipment " + equipmentId + " bin1 indicator is not found" );
                getScreenShot( wDriver );
            }
        }

        if(material2 != null) {
            try {
                String bin2Text = wDriver.findElements(By.xpath("//*[contains(@data-id,'" + equipmentId + "')]//*[contains(@class,'bin-type')]" )).get(1).getCssValue( "background-color" );
                String equipmentActualBin2Color = getRGBAColorName(bin2Text.toString());
                if(equipmentActualBin2Color.equals(material2.toString())){
                    logTestPass(wDriver, "equipment " + equipmentId + " bin2 indicator material color is found as expected- " + material2);
                } else {
                    logTestFailure(wDriver, "equipment " + equipmentId + " bin2 indicator expected " + material2 + " doesn't match with actual - " + equipmentActualBin2Color);
                }
            } catch (Exception e) {
                logTestFailure(wDriver, "equipment " + equipmentId + " bin2 indicator is not found" );
            }

        }

    }

}
