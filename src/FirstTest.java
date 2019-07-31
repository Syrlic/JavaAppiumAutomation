import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\WSOTeam\\JavaAppiumAutomation\\apks\\wiki.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
    @After
    public void tearDown(){
        driver.quit();
    }
    @Test
    public void firstTest(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find Skip button",
                2);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannon enter in app",
                2);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']" +
                "//*[@class='android.view.ViewGroup']//*[@text='Object-oriented programming language']"),
                "Cannot find Object-oriented programming language topic searching java ",
                8);

    }

    @Test
    public void testCancelSearch(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find Skip button",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search container",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementAndClear(By.id("org.wikipedia:id/search_src_text"),
                "Cannot find text field",
                5
        );
        waitForElementAndClick(
                By.className("android.widget.ImageButton"),
                "Cannot find <- to cancel search",
                5
        );
        waitForElementNotPresent(
                By.className("android.widget.ImageButton"),
                "Cannot find <- to cancel search",
                5
        );
    }

    @Test
    public void testCompareArticleTitle(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find Skip button",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search container",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']" +
                        "//*[@class='android.view.ViewGroup']//*[@text='Object-oriented programming language']"),
                "Cannot find search container",
                5
        );
        WebElement title_element = waitForElementPresent(
                By.id("pagelib_edit_section_title_description"),
                "Cannot find article title",
                5
        );
        String article_title = title_element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected article",
                "Object-oriented programming language",
                article_title
        );
    }

    @Test
    public void testSearchText(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Skip')]"),
                "Cannot find Skip button",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search container",
                5
        );
        WebElement text_element = waitForElementPresent(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search container",
                5);
        String text = text_element.getAttribute("text");
        Assert.assertEquals(
                "Text not found",
                "Search Wikipedia",
                text
        );
    }
    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSec){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));

    }

    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by, error_message, 5);

    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSec){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSec);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSec){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSec);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSec){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSec){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSec);
        element.clear();
        return element;
    }

}
