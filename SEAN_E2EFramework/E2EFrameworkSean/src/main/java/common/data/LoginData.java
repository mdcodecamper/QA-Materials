package common.data;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.Hashtable;


/**
 * Created by sdas on 10/1/2016.
 */
public class LoginData {
    public LoginData(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);

    }
       // All Testing environment names with urls
    public static String getLoginData(String key) {
        key = key.toLowerCase();
        Hashtable<String, String> testData = new Hashtable<String, String>();

        testData.put("username", "smart_userstg49");
        testData.put("password", "Change4dsny");
        testData.put("qaa", "http://10.155.204.63:88/ui/opsboard/");
        testData.put("uat", "http://mstlvv-dsnsmt11.csc.nycnet/ui/opsboard/");
        testData.put("stage", "http://msslvv-dsnsmt13.csc.nycnet/ui/opsboard/");
        testData.put("qa1", "http://mstlvv-dsnsmt05.csc.nycnet/ui/opsboard/");
        testData.put("qa2", "http://mstlvv-dsnsmt10.csc.nycnet/ui/opsboard/");
        testData.put("local", "http://192.168.99.100/ui/#/opsboard/");
        testData.put("flex", "http://10.155.209.46:88/ui/opsboard/");
        testData.put("flex2", "http://10.155.209.46:89/ui/opsboard/");
        testData.put("auto", "http://10.155.209.46:90/ui/opsboard/");
        testData.put("test1", "http://10.155.209.46:88/ui/opsboard/");
        testData.put("blaze", "http://10.155.209.46:90/ui/opsboard/");
        testData.put("new", "http://10.155.208.122:88/ui/opsboard/");

        return testData.get(key);
    }

    // New Created for us http://10.155.209.46:90/ui/opsboard/MN02/20161201

}
