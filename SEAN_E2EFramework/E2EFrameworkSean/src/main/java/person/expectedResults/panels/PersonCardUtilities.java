package person.expectedResults.panels;

import _driverScript.AbstractStartWebDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.util.List;

import static common.actions.CommonActions.logTestFailure;
import static common.actions.CommonActions.logTestInfo;
import static common.data.RawColorCodes.getRGBAColorName;

/**
 * Created by sdas on 10/6/2016.
 */
public class PersonCardUtilities extends AbstractStartWebDriver {
    public PersonCardUtilities(WebDriver wDriver) {
        super();
        PageFactory.initElements(wDriver, this);
    }

    //==================== All Methods related to Person Card Expected Result validations ====================//

    // To check if animation is activated(Not working fully yet)
    public static boolean isCardAnimated(String cardName) {
        boolean isAnimated = false;
        List<WebElement> personCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel')]//*[contains(@class, 'card-container')]"));
        for (WebElement card : personCards) {
            if (card.getText().contains(cardName)) {
                isAnimated = card.getAttribute("innerHTML").toString().contains("pulse") || card.getAttribute("innerHTML").toString().contains("blink");
                Reporter.log("Activated Card Animation: " + card.getAttribute("innerHTML"), true);
                logTestInfo(wDriver, "Activated Card Animation: " + isAnimated);
            }
        }
        return isAnimated;
    }

    public static String getIndicatorCode(String cardName, String codeType) {
        String indicatorCode = null;
        List<WebElement> personCards = wDriver.findElements(By.xpath("//*[contains(@class, 'auPanelPersonnel')]//*[contains(@class, 'card-container')]"));
        for (WebElement card : personCards) {
            if (card.getText().contains(cardName)) {
                //System.out.println(card.findElement(By.cssSelector(".auSpecialPosition")).getText());
                WebElement indEle = card.findElement(By.cssSelector(".au" + codeType));
                indicatorCode = waitForElementThenDo(wDriver, indEle, 10).getText();
                //Reporter.log("Indicator Text Found is: " + indicatorCode, true);
                logTestInfo(wDriver,"Indicator Text Found - " + indicatorCode);
                //String indicatorColor = indEle.getCssValue("color");
                //Reporter.log("Indicator Text COLOR is: " + indicatorColor, true);
                break;
            }
        }
        return indicatorCode;
    }




    public static String getAPersonWithoutNextDayAssigned(String category,String subCategory, String shiftTime) throws InterruptedException {
        String indicatorCode = null;
        String personCardName = null;
        List<WebElement> personCard = null;
        int personCount = 0;

              if(category.equals( "Assigned")){
                  personCount = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnalAssignedPanel')]//*[contains(@class,'" + shiftTime + "Assigned')]//*[contains(@class,'auPersonContainer') and contains(@class,'" + subCategory + "')]//*[contains(@class,'auPersonName')]")).size();
                  personCard = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnalAssignedPanel')]//*[contains(@class,'" + shiftTime + "Assigned')]//*[contains(@class,'auPersonContainer') and contains(@class,'" + subCategory + "')]//*[contains(@class,'auPersonName')]"));
              }

              if(category.equals( "Detached")){
                  personCount = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelDetachmentPanel')]//*[contains(@class,'auPersonContainer') and contains(@class,'" + subCategory + "')]//*[contains(@class,'auPersonName')]")).size();
                  personCard = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelDetachmentPanel')]//*[contains(@class,'auPersonContainer') and contains(@class,'" + subCategory + "')]//*[contains(@class,'auPersonName')]"));

              }

              if(category.equals( "Available")){
                  personCount = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelAvailablePanel')]//*[contains(@class,'auPersonContainer') and contains(@class,'" + subCategory + "')]//*[contains(@class,'auPersonName')]")).size();
                  personCard = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelAvailablePanel')]//*[contains(@class,'auPersonContainer') and contains(@class,'" + subCategory + "')]//*[contains(@class,'auPersonName')]"));

              }
              if(category.equals( "MDA")){
                  personCount = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelMdaPanel')]//*[contains(@class,'auPersonContainer') and contains(@class,'" + subCategory + "')]//*[contains(@class,'auPersonName')]")).size();
                  personCard = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelMdaPanel')]//*[contains(@class,'auPersonContainer') and contains(@class,'" + subCategory + "')]//*[contains(@class,'auPersonName')]"));

              }
              if(category.equals( "Unavailable")){
                  personCount = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelUnavailablePanel')]//*[contains(@class,'auPersonContainer') and contains(@class,'" + subCategory + "')]//*[contains(@class,'auPersonName')]")).size();
                  personCard = wDriver.findElements(By.xpath("//*[contains(@class,'auPersonnelUnavailablePanel')]//*[contains(@class,'auPersonContainer') and contains(@class,'" + subCategory + "')]//*[contains(@class,'auPersonName')]"));

              }

              if(personCount != 0) {
                  //System.out.println("Person card is " + (personCard));
                  for (int i = 0; i <= personCount - 1; i++) {
                      personCardName = personCard.get( i ).getText();
                          indicatorCode = PersonCardUtilities.getIndicatorCode(personCardName,"MadeAvailable");
                          if (!indicatorCode.equals( "N" )) {
                              logTestInfo( wDriver, "Next Day Indicator Not Found " + indicatorCode );
                              logTestInfo( wDriver, "Person selected - " + personCardName );
                              break;

                            }

                      }

              } else {
                  if(category.equals("Assigned")){
                      logTestFailure( wDriver, "NO CARD is in Category: " + category + " & Shift: " + shiftTime + " By PersonType: " + subCategory );
                      Assert.fail();
                  } else {
                      logTestFailure( wDriver, "NO CARD is in Category: " + category + " By PersonType: " + subCategory );
                      Assert.fail();
                  }

              }

        return personCardName;
    }


    public static String getIndicatorCodeColor(String cardName, String codeType) {
        String indicatorColor = null;
        List<WebElement> personCards = wDriver.findElements(By.xpath("/*//*[contains(@class, 'auPanelPersonnel')]/*//*[contains(@class, 'card-container')]"));
        for (WebElement card : personCards) {
            if (card.getText().contains(cardName)) {
                //System.out.println(card.findElement(By.cssSelector(".auSpecialPosition")).getText());
                WebElement indEle = card.findElement(By.cssSelector(".au" + codeType));
                indicatorColor = getRGBAColorName(indEle.getCssValue("color"));
                Reporter.log("In getIndicatorCodeColor Indicator Text COLOR is: " + getRGBAColorName(indicatorColor), true);
            }
        }
        return indicatorColor;
    }

}
