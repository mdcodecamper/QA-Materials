package common.data;

import _driverScript.AbstractStartWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.Hashtable;

/**
 * Created by sdas on 11/9/2016.
 */
public class RawColorCodes extends AbstractStartWebDriver{
    public RawColorCodes(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }


    // Color Data
    public static String getRGBAColorName(String rawColorValue) {
        Hashtable<String, String> colorMap = new Hashtable<String, String>();
        String colorName = null;
        colorMap.put("rgba(0, 0, 0, 1)", "black");
        colorMap.put("rgba(255, 255, 255, 1)", "white");
        colorMap.put("rgba(255, 10, 155, 1)", "pink");
        colorMap.put("rgba(255, 153, 0, 1)", "yellowOrange");
        colorMap.put("rgba(225, 90, 0, 1)", "orange");
        colorMap.put("rgba(255, 115, 19, 1)", "lightOrange");
        colorMap.put("rgba(255, 255, 0, 1)", "yellow");
        colorMap.put("rgba(175, 95, 50, 1)", "brown");
        colorMap.put("rgba(153, 255, 0, 1)", "limeGreen");
        colorMap.put("rgba(0, 175, 1, 1)", "brightGreen");
        colorMap.put("rgba(15, 117, 255, 1)", "navyBlue");
        colorMap.put("rgba(144, 35, 254, 1)", "purple");
        colorMap.put("rgba(255, 137, 127, 1)", "salmon");
        colorMap.put("rgba(52, 241, 248, 1)", "tiffanyBlue");
        colorMap.put("rgba(13, 221, 255, 1)", "blue");
        colorMap.put("rgba(116, 196, 13, 1)", "green");
        colorMap.put("rgba(50, 50, 51, 1)", "darkGray");
        colorMap.put("rgba(222, 184, 135, 1)", "almond");
        colorMap.put("rgba(xx, xx, xx, xx)", "red");
        colorMap.put("rgba(xx, xx, xx, xx)", "lightYellow");
        colorName = colorMap.get(rawColorValue);
        return colorName;
    }

}
