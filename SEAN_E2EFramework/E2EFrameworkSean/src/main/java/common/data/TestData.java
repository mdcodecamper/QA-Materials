package common.data;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.Utilities;

import java.text.ParseException;
import java.util.Hashtable;


/**
 * Created by sdas on 10/1/2016.
 */
public class TestData {
    public TestData(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(getData("endDate"));
    }

    public static String getData(String key) throws ParseException {
        Hashtable<String, String> testData = new Hashtable<String, String>();

        testData.put("location", "MN02/");
        testData.put("toLocation", "MN03/");
        testData.put("date", Utilities.getDesiredDateInFormat(0, "yyyyMMdd"));
        testData.put("personCardName", "SpPos12");
        testData.put("startDate", "10032016");
        testData.put("mdaType", "4 - MDA 4 – LIGHT DUTY");
        testData.put("appointmentDate", "10042016");
        testData.put("endDate", Utilities.getDesiredDateInFormat(0, "MMddyyyy"));
        //testData.put("endDate", "10022016");
        testData.put("endHour", "11");
        testData.put("endMinute", "22");
        testData.put("startHour", "11");
        testData.put("startMinute", "33");
        testData.put("remarks", "Sean Remark");
        testData.put("reason", "Sean Reason");
        testData.put("rowIndex", "0");

        return testData.get(key);

    }


}
