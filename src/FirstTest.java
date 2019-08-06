import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.net.URL;
import java.util.List;

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

    @Test
    public void searchAndClearTest(){
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
                "boom",
                "Cannot find search input",
                5
        );
        WebElement search_list = waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find title element by ID",
                5
        );
        List<WebElement> list_titles = search_list.findElements(By.id("org.wikipedia:id/page_list_item_title"));
//        for(WebElement element : list_titles){
//            System.out.println(element.getText());
//        }
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find clear button by ID",
                5
        );
        WebElement elem_empty = waitForElementPresent(
                By.id("org.wikipedia:id/search_empty_container"),
                "Cannot find clear element by ID",
                5
        );
        List<WebElement> list_empty = elem_empty.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        Assert.assertTrue(!list_titles.isEmpty());
        Assert.assertTrue(list_empty.isEmpty());
    }

    @Test
    public void wordIsPresentTest(){
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
        String word = "spring";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                word,
                "Cannot find search input",
                5
        );
        WebElement search_list = waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find title element by ID",
                5
        );
        List<WebElement> list_titles = search_list.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        for(WebElement element : list_titles){
            String text = element.getText().toLowerCase();
         //   System.out.println(element.getText());
            Assert.assertTrue(text.contains(word));
        }
    }

    @Test
    public void testSwipeArticle(){
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
                "Appium",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']" +
                        "[@text='Appium']"),
                "Cannot find Appium container",
                5
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/page_contents_container"),
                "Cannot find article page",
                15
        );
        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                        "Cannot find the end of the article",
                20
        );

    }

    @Test
    public void saveFirstArticleToMyList(){

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

    protected void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width/2;
        int start_y = (int) (size.height*0.8);
        int end_y = (int) (size.height*0.2);
        action.press(x, start_y).waitAction().moveTo(x, end_y).release().perform();
    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes){
        int already_swiped = 0;

        while (driver.findElements(by).size()==0){
            if(already_swiped > max_swipes){
                waitForElementPresent(by, "Cannot find element by swiping up. \n"
                        + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }
}
