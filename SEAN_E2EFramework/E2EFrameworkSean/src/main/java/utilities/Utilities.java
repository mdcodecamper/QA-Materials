package utilities;


import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static _driverScript.AbstractStartWebDriver.wDriver;
import static common.actions.CommonActions.logTestFailure;


public class Utilities {

    /**************
     * Change DOM element property to true
     ************/
    public static void setDatePropertyTrue(String attributeName) throws IOException {
        WebElement element= wDriver.findElement(By.id(attributeName));
        JavascriptExecutor js = (JavascriptExecutor) wDriver;
        js.executeScript ("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element,
                "onkeydown",
                "return true");
    }


    /**************
     * Convert milliseconds to HHmmss
     ************/
    public static String convertSecondsToHMmSs(long milliSeconds) {
        long seconds = milliSeconds/1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h,m,s);
    }

    /**************
     * HTML JavaScript Drag and Drop........
     ************/
    public static void dragdrop(WebElement LocatorFrom, WebElement LocatorTo) {
        String xto=Integer.toString(LocatorTo.getLocation().x);
        String yto=Integer.toString(LocatorTo.getLocation().y);
        ((JavascriptExecutor)wDriver).executeScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
                        "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
                LocatorFrom,xto,yto);
    }
    /**************
     * Read .properties file.........
     ************/
    public static String propFileReader(String path, String key) throws IOException {
        //File source = new File(path);
        FileInputStream fStream = new FileInputStream(path);
        Properties prop = new Properties();
        prop.load(fStream);
        return prop.getProperty(key);
    }

    /**************
     * Actions Move to Element
     ************/
    public static WebElement moveToAnElement(WebDriver wDriver, WebElement element) {
        Actions actions = new Actions(wDriver);
        actions.moveToElement(element).build().perform();
        return element;
    }

    /**************
     * Accept JavaScript Pop-ups/Alerts
     ************/
    public void acceptJSAlert(WebDriver wDriver) {
        WebDriverWait wait = new WebDriverWait(wDriver, 120);
        wait.until(ExpectedConditions.alertIsPresent());
        wDriver.switchTo().alert().accept();
    }

    /**************
     * Read Cell Data from a Excel File(.xls)
     ************/
    public static String getXLCellData(String filePath, int sheetIndex, int colNum, int rowNum)
            throws IOException, BiffException {
        String cellData = null;
        Workbook wb = Workbook.getWorkbook(new File(filePath));
        wb.getNumberOfSheets();
        Sheet sheet = wb.getSheet(sheetIndex);
        try {
            cellData = sheet.getCell(colNum, rowNum).getContents();
        } catch (Exception e) {
            return null;
        }
        return cellData;
    }

    /**************
     * Get Todays Date in desired format
     ************/
    public static String getTodaysDateInFormat(String format) {
        format = (format == null) ? "yyyyMMdd" : format;
        DateFormat strDate = new SimpleDateFormat(format);
        Date date = new Date();
        return strDate.format(date);
    }

    /**************
     * Get Todays Date in desired format
     ************/
    public static String getDesiredDateInFormat(int days, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(true);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        String date1 = dateFormat.format(calendar.getTime());
        return date1;
    }

    /**************
     * Take ScreenShot
     ************/
    public static void takeScreenshot(WebDriver wDriver, String directory, String fileName) throws IOException {
        fileName = fileName + ".png";
        // directory = "src/";
        File sourceFile = ((TakesScreenshot) wDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File(directory + fileName));
        // String destination = directory + fileName;
    }

    /**************
     * Take ScreenShot and return the path where the screenshot is save
     ************/
    public static String takeScreenshotReturnPath(WebDriver driver, String fileName, String path) throws IOException {
        fileName = fileName + ".png";
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File(path + fileName));
        String destination = path + fileName;
        return destination;
    }


    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0,8);

    }

    /**************
     * Wait for Element to Load/be Visible
     ************/
    public static WebElement waitForElement(WebDriver wDriver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(wDriver, 60);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    /**************
     * Wait for Element to Load/be Visible for a given Time
     ************/
    public static WebElement waitForElement(WebDriver wDriver, WebElement element, int timeOut) {
        WebDriverWait wait = new WebDriverWait(wDriver, timeOut);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }


    /**************Wait Poll for element until the element is present or timeout************/
    /*public static WebElement fluentWait(WebDriver wDriver, final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(wDriver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement element = wait.until(new Function<WebDriver, WebElement>() {@Override
			public WebElement apply(WebDriver wDriver) {
				return wDriver.findElement(locator);
			}
		});
		return element;
	}
	*/
    /**************
     * Wait for Element to Load/be Visible for a given Time
     ************/
    public static int isElementPresent(WebDriver wDriver, By by) {
        List<WebElement> elements = wDriver.findElements(by);
        try{
            return elements.size();
        }catch(Throwable t){
            return -1;
        }
    }
    /**************
     * Find Only Broken links on a Page
     ************/
    public static List<String> getBrokenLinks(WebDriver wDriver, ArrayList<String> brokenLinks) {
        List<WebElement> linksToClick = new ArrayList<WebElement>();
        List<WebElement> elements = wDriver.findElements(By.tagName("a"));
        elements.addAll(wDriver.findElements(By.tagName("img")));
        for (WebElement e : elements) {
            if (e.getAttribute("href") != null) {
                linksToClick.add(e);
            }
        }
        for (WebElement link : linksToClick) {
            String href = link.getAttribute("href");

            try {
                if ((!linkStatus(new URL(href)).contains("OK")) || (!linkStatus(new URL(href)).contains("404"))) {
                    Reporter.log("Broken Link found: " + href, true);
                    // System.err.println("Broken Link found: " + href);
                    brokenLinks.add(href);
                }
            } catch (MalformedURLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
        return brokenLinks;
    }

    /**************
     * Find Link Status of all Links on Page
     ************/
    public static ArrayList<String> getLinkStatus(WebDriver wDriver, ArrayList<String> allLinkStatus) {
        List<WebElement> linksToClick = new ArrayList<WebElement>();
        List<WebElement> elements = wDriver.findElements(By.tagName("a"));
        elements.addAll(wDriver.findElements(By.tagName("img")));
        for (WebElement e : elements) {
            if (e.getAttribute("href") != null) {
                linksToClick.add(e);
            }
        }
        for (WebElement link : linksToClick) {
            String href = link.getAttribute("href");
            try {
                System.out.println("URL " + href + " returned " + linkStatus(new URL(href)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return allLinkStatus;
    }

    /**************
     * Find Clickable Links on a Page
     ************/

    public static ArrayList<String> findAllLinks(WebDriver wDriver, ArrayList<String> allLinks) {
        List<WebElement> elements = wDriver.findElements(By.tagName("a"));
        elements.addAll(wDriver.findElements(By.tagName("img")));
        for (WebElement e : elements) {
            if (e.getAttribute("href") != null) {
                allLinks.add(e.getAttribute("href"));
            }
        }
        return allLinks;
    }

    /**************
     * Get Link Status of an URL
     **************/
    public static String linkStatus(URL url) {
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.connect();
            String responseMessage = http.getResponseMessage();
            http.disconnect();
            return responseMessage;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**************
     * Click on any Visible text on the page
     **************/
    public static void clickOnVisibleText(WebDriver wDriver, String textValue) {
        WebDriverWait wait = new WebDriverWait(wDriver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='" + textValue + "']")));
        wDriver.findElement(By.xpath("//*[text()='" + textValue + "']")).click();
    }

    /**************
     * Wait Until Element is Visible Then do something
     **************/
    public static WebElement waitForElementThenDo(WebDriver wDriver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(wDriver, 60);
        element = wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    /**************
     * Wait Until ALL Elements ARE Visible Then do something
     **************/
    public static List<WebElement> waitAndGetAllElements(WebDriver wDriver, List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(wDriver, 60);
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
        return elements;
    }
    /**************
     * Wait Until ALL Elements ARE Visible Then do something
     **************/
    public static List<WebElement> waitAndGetAllElements(WebDriver wDriver, List<WebElement> elements, int waitTime) {
        WebDriverWait wait = new WebDriverWait(wDriver, waitTime);
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
        return elements;
    }

    /**************
     * Wait Until Given Time for Element to be Visible Then do something Overloaded Method
     **************/
    public static WebElement waitForElementThenDo(WebDriver wDriver, WebElement element, int waitTime) {
        WebDriverWait wait = new WebDriverWait(wDriver, waitTime);
        element = wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    /**************
     * Element Presence Check
     **************/
    public static boolean elementCheck(WebDriver wDriver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(wDriver, 60);
        element = wait.until(ExpectedConditions.visibilityOf(element));
        if (element.isDisplayed() && element.isEnabled()) {
            return true;
        } else {
            return false;
        }
    }
    /**************
     * Element Presence Check
     **************/
    /*public static boolean elementCheck(WebDriver wDriver, List<WebElement> elements, int timeout) {
        WebDriverWait wait = new WebDriverWait(wDriver, timeout);
        element = wait.until(ExpectedConditions.visibilityOf(element));
        if (element.isDisplayed() && element.isEnabled() && element.getSize() > 0) {
     * Wait for given time and then Element Presence Check
     **************/
    public static boolean elementCheck(WebDriver wDriver, WebElement element, int waitFor) {
        WebDriverWait wait = new WebDriverWait(wDriver, waitFor);
        element = wait.until(ExpectedConditions.visibilityOf(element));
        if (element.isDisplayed() && element.isEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    /**************
     * Select Drop Down By Visible Text method
     **************/
    public static void clickThenSelectDropDownByText(WebElement element, String visibleText) {
        Select select = new Select(element);
        //element.click();
        select.selectByVisibleText(visibleText);
    }

    /**************
     * Select Drop Down By Option Value
     **************/
    public static void selectFromDropDownByValue(WebElement element, String itemValue) {
            Select select = new Select(element);
            select.selectByValue(itemValue);
    }
    /**************
     * Get the string values of selected items
     **************/
    public static String getSelectedItems(WebElement element) {
        Select select = new Select(element);
        return select.getAllSelectedOptions().toString();
    }
    /*************
     * Select Drop Down By Visible Text method
     *************/
    public static void selectFromDropDownByVisibleText(WebElement element, String itemValue) {
            Select select = new Select(element);
            select.selectByVisibleText(itemValue);    }

    /**************
     * Select Drop Down By Index
     **************/
    public static void selectFromDropDownByIndex(WebElement element, int indexNo) {
            Select select = new Select(element);
            select.selectByIndex(indexNo);
    }

    /**************
     * Switch Window
     **************/
    public static void switchTo(int index, WebDriver wDriver) {
        try {
            List<String> win = new ArrayList<String>(wDriver.getWindowHandles());
            wDriver.switchTo().window(win.get(index));
        } catch (IndexOutOfBoundsException e) {
            Reporter.log("Invalid Window Index : " + index, true);
        }
    }

    /**************
     * Get all item of a select dropdown as list
     **************/
    public static void getListOfDropBoxItems(WebElement element) {
        try {
            List<String> list = new ArrayList<String>();
            Select select = new Select(element);
            for (WebElement ele : select.getOptions()) {
                list.add(ele.toString());
            }
        } catch (Error e) {
            Reporter.log("Selected element is not a dropdown list", true);
        }
    }

    /**************
     * Get Random Alphabetic String
     **************/
    public static String setRandomStringByLength(int strLength) {

        return RandomStringUtils.randomAlphabetic(strLength);
    }
    /**************
     * Get Current Time Hour in 24 Format
     **************/
    public static String getCurrentTimeIn24HrFormat(){
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String currHr = df.format(new Date());
        return currHr;
    }

    /**************
     * Get Current Time Minute
     **************/
    public static String getCurrentMinute(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        //like "HH:mm" or just "mm", whatever you want
        String minute = sdf.format(date);
        return minute;
    }

    /**************
     * code below will return hour in 24 hour format
     **************/
    public static String get24HourFormat(int hour) {
        String resultString = null;
        String resultVerify;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, hour);
        int result = cal.get(Calendar.HOUR_OF_DAY);
        if(result < 10){
            resultVerify = Integer.toString(result);
            resultString = "0" + resultVerify;
        } else {
            resultString = Integer.toString(result);
        }

        return resultString;

    }


    /**************
     * get date time
     **************/

    public static String getDateTime() {
        SimpleDateFormat sdfDateTime;
        String strDateTime;
        sdfDateTime = new SimpleDateFormat("yyyyMMdd'_'HHmmss'_'SSS");
        Date now = new Date();
        strDateTime = sdfDateTime.format(now);
        return strDateTime;
    }
    /**************
     * Extract Numbers from any alphanumerics
     **************/
    public static int extractNumbersFromAnyText(String alphaNumeric) {
        int desiredNum = 0;
        try{
            desiredNum = Integer.parseInt(alphaNumeric.replaceAll("\\D+", ""));
        }catch(NumberFormatException e){
            desiredNum = 0;
        }
        return desiredNum;
    }

    /**************
     * click on element using javascript executor method
     **************/
    public static void executorClick(WebDriver wDriver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)wDriver;
        executor.executeScript("arguments[0].click();", element);
    }

    /**************
     * scroll into element view using javascript executor method
     **************/
    public static void scrollPageByHeightY(WebDriver wDriver, int y) {
        JavascriptExecutor executor = (JavascriptExecutor)wDriver;
        //executor.executeScript("scroll(0," + y +")");
        executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
    /**************
     * Check if Page is loaded for 60 Second
     **************/
    public static void waitForPageLoad(int seconds) {
        JavascriptExecutor js = (JavascriptExecutor)wDriver;
        if (js.executeScript("return document.readyState").toString().equals("complete")){
            return;
        }
        // Keep Checking every 1 Second if page is loaded for 60 sec.
        for (int i=0; i < seconds; i++){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {}
            if (js.executeScript("return document.readyState").toString().equals("complete")){
                break;
            }
            if(i == 90){
                logTestFailure(wDriver, "Page did not NOT load in " + i + " Seconds!!!");
                Assert.fail();
            }
        }
    }

    /**************
     * Zoom in or Out Current Page Argument format: 0.5. 0.75, 1.15 etc
     **************/
    public static void setPageZoomSize(double size) {
        JavascriptExecutor executor = (JavascriptExecutor) wDriver;
        executor.executeScript("document.body.style.transform='scale("+ size +")';");
    }

    /**************
     * scroll into element view using javascript executor method
     **************/
    public static void executorScrollIntoView(WebDriver wDriver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)wDriver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**************
     * Get Text values of disabled input fields
     **************/
    public static String jsExecGetValueById(WebDriver wDriver, String id) {
        JavascriptExecutor executor = (JavascriptExecutor)wDriver;
        return executor.executeScript("return document.getElementById('"+id+"').value").toString().trim();
    }


    /**************
     * click on element using Action method
     **************/
    public static void actionClick(WebDriver wDriver, WebElement element) {
         Actions action = new Actions(wDriver);
        action.moveToElement(element).click().build().perform();
    }

    /**************
     * getting 24hour shift format for task
     **************/
    public static int getShiftHourFormat(int hour){

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, hour);
        int result = cal.get(Calendar.HOUR_OF_DAY);

        return result;

    }
    /**************
     * switch to alert
     **************/
    public static void alert(WebDriver wDrver){
        Alert alert = wDrver.switchTo().alert();
        alert.accept();

    }

    public static void turnOffImplicitWaits() {
        wDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    public static void turnOnImplicitWaits() {
        wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


}
