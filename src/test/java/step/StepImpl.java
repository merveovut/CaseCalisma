package step;

import com.thoughtworks.gauge.Step;
import driver.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StepImpl {

    private static WebDriver driver;
    private WebDriverWait wait;
    private int waitTimeOut = 15;

    public StepImpl() {
        driver = Driver.webDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeOut));
    }

    public By byElement(String locator, String locatorType){
        By byElement = null;
        switch (locatorType){
            case "id":
                byElement = By.id(locator);
                break;
            case "css":
                byElement = By.cssSelector(locator);
                break;
            case "xpath":
                byElement = By.xpath(locator);
                break;
        }
        return byElement;
    }

    @Step("<url> urline gidilir.")
    public void goToUrl(String url){
        driver.get(url);
    }

    @Step("<sn> sn bekle")
    public void waitSecond(String sn) throws InterruptedException {
        Thread.sleep(Integer.parseInt(sn) * 1000L);
    }

    @Step("<locator> selectorlu tipi <locatorType> olan elemente tıkla")
    public void clickElement(String locator, String locatorType){
        By byElement = byElement(locator, locatorType);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byElement));
        driver.findElement(byElement).click();
    }

    @Step("<locator> selectorlu tipi <locatorType> olan element var mı?")
    public void checkElement(String locator, String locatorType){
        try{
            By byElement = byElement(locator, locatorType);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byElement));
        }catch (Exception e){
            Assert.fail(locator + " elementi bulunamadı.");
        }
    }

    @Step("<locator> selectorlu tipi <locatorType> olan elementi görünmeyene kadar bekle")
    public void waitInvisibleElement(String locator, String locatorType){
        By byElement = byElement(locator, locatorType);
        WebElement element = driver.findElement(byElement);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    @Step("<locator> selectorlu tipi <locatorType> olan elemente <text> yaz")
    public void sendKeyElement(String locator, String locatorType, String text){
        By byElement = byElement(locator, locatorType);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byElement));
        driver.findElement(byElement).sendKeys(text);
    }

    @Step("<locator> selectorlu tipi <locatorType> olan elementlerden <text> değerine eşit olana tıkla")
    public void clickTextEqualsElements(String locator, String locatorType, String text){
        By byElement = byElement(locator, locatorType);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byElement));
        List<WebElement> elements = driver.findElements(byElement);
        for(WebElement element : elements){
            if(element.getText().equals(text)){
                element.click();
                return;
            }
        }
    }

    @Step("<locator> li tipi <locatorType> olan elementlerden <text> değerine eşit olanın yanındaki <locator2> li tipi <locatorType2> elemente <kez> tıkla")
    public void clickTextEqualsElements(String locator, String locatorType, String text, String locator2, String locatorType2, String kez){
        By byElement = byElement(locator, locatorType);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byElement));
        List<WebElement> elements = driver.findElements(byElement);
        for(WebElement element : elements){
            if(element.getText().equals(text)){
                WebElement element2 = element.findElement(byElement(locator2, locatorType2));
                for(int i = 0; i < Integer.parseInt(kez); i++){
                    wait.until(ExpectedConditions.elementToBeClickable(element2));
                    element2.click();
                }
                return;
            }
        }
    }

    @Step("<locator> li tipi <locatorType> olan elementlerden <text> değerine eşit olanın yanındaki <locator2> li tipi <locatorType2> elementin texti <text2> mi?")
    public void checkNearElementText(String locator, String locatorType, String text, String locator2, String locatorType2, String text2){
        By byElement = byElement(locator, locatorType);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byElement));
        List<WebElement> elements = driver.findElements(byElement);
        for(WebElement element : elements){
            if(element.getText().equals(text)){
                WebElement element2 = element.findElement(byElement(locator2, locatorType2));
                if(element2.getText().equals(text2)){
                    return;
                }
            }
        }
    }

    @Step("Tarih <tarih> olarak seçilir.")
    public void clickCalendarDate(String tarih){
        By tarihElementleri = byElement(".CalendarMonth[data-visible='true'] .CalendarMonth_caption > strong", "css");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tarihElementleri));
        By sagOkElementi = byElement(".DayPickerNavigation_button[style='display: block;']", "css");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(sagOkElementi));
        WebElement sagOkElement = driver.findElement(sagOkElementi);
        for(int i = 0; i < 10; i++){
            List<WebElement> tarihElements = driver.findElements(tarihElementleri);
            for(WebElement element : tarihElements){
                if(element.getText().equals(tarih)){
                    return;
                }else {
                    sagOkElement.click();
                }
            }
        }
        Assert.fail(tarih + " değerine eşit element bulunamadı.");
    }
}
