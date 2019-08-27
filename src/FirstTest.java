import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        driver.rotate(ScreenOrientation.PORTRAIT);
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
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='Add this article to a reading list']"),
                "Cannot find button to open article options",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find button to skip onboard",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/create_button"),
                "Cannot find button to add article in the list",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input field",
                5
        );
        String nameFolder = "Learning programming";
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameFolder,
                "Cannon write text in the field",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find OK button",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='No thanks']"),
                "Cannot find 'No thanks' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find button 'My lists'",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Learning programming']"),
                "Cannot find FOLDER with article in it",
                15
        );
        // проверить локатор
        waitForElementPresent(
                By.xpath("//android.view.ViewGroup[@text='Java (programming language)']"),
                "Cannot find article in the folder",
                15
        );
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot swipe element");
        waitForElementNotPresent(
                By.xpath("//android.view.ViewGroup[@text='Java (programming language)']"),
                "Element not deleted",
                20
        );
    }

    @Test
    public void amountOfNotEmptySearch(){
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
        String searchLine = "Linkin park discography";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5
        );
        String searchResult = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                "/*[@class='android.view.ViewGroup']";
        waitForElementPresent(
                By.xpath(searchResult),
                "Cannot find anything by request " + searchLine,
                15
        );
        int amountOfElements = getAmountOfElements(By.xpath(searchResult));
        Assert.assertTrue(
                "We found a few results",
                amountOfElements > 0);

    }
    @Test
    public void amountOfEmptySearch(){
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
        String searchLine = "abbbracadabrabra";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5
        );
        String searchResult = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                "/*[@class='android.view.ViewGroup']";
        String emptyResults = "//*[@text='No results found']";
        waitForElementPresent(
                By.xpath(emptyResults),
                "Cannot find empty result by the request "+searchLine,
                15
        );
        assertElementNotPresent(
                By.xpath(searchResult),
                "We have found some results by request "+searchLine
        );
    }

    @Test
    public void changeScreenOrientationOnSearchResult(){
        driver.rotate(ScreenOrientation.PORTRAIT);
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
        String searchLine = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5
        );
        String searchResult = "//*[@resource-id='org.wikipedia:id/search_results_container']" +
                "//*[@class='android.view.ViewGroup']//*[@text='Object-oriented programming language']";
        waitForElementAndClick(
                By.xpath(searchResult),
                "Cannot find anything by request " + searchLine,
                15
        );
        String titleBeforeRotation = waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='content']//*[@text='Java (programming language)']"),
                        "text",
                        "Cannot find title of article",
                        15
        );
        driver.rotate(ScreenOrientation.LANDSCAPE);
        String titleAfterRotation = waitForElementAndGetAttribute(
                By.xpath("//android.view.View[@text='Java (programming language)']"),
                "text",
                "Cannot find title of article after rotation",
                15
        );
        Assert.assertEquals(
                "Article title have been changed after rotation",
                titleBeforeRotation,
                titleAfterRotation
        );
        driver.rotate(ScreenOrientation.PORTRAIT);
        String titleAfterSecondRotation = waitForElementAndGetAttribute(
                By.xpath("//android.view.View[@text='Java (programming language)']"),
                "text",
                "Cannot find title of article after second rotation",
                15
        );
        Assert.assertEquals(
                "Article title have been changed after rotation",
                titleBeforeRotation,
                titleAfterSecondRotation
        );
    }

    @Test
    public void testSearchArticleInBackground(){
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
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']" +
                        "//*[@class='android.view.ViewGroup']//*[@text='Object-oriented programming language']"),
                "Cannot find search container",
                5
        );
        driver.runAppInBackground(3);
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
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']" +
                        "//*[@class='android.view.ViewGroup']//*[@text='Object-oriented programming language']"),
                "Cannot find article after returning from background",
                5
        );
    }

    @Test
    public void testSaveTwoArticles(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search container",
                5
        );
        Map<String, By> searchrequests = new HashMap<>();
        searchrequests.put("Artist",
                By.xpath("//*[contains(@text, 'Person who creates, practises and/or demonstrates any art')]"));
        searchrequests.put("Musician",
                By.xpath("//*[contains(@text, 'Person who performs or composes music')]"));
        String nameFolder = "Art";
        int count = 0;
        for (Map.Entry<String, By> item : searchrequests.entrySet()) {
            count++;
            waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text, 'Search…')]"),
                    item.getKey(),
                    "Cannot find search input " + item,
                    5
            );
            waitForElementAndClick(
                    item.getValue(),
                    "Cannot find searching article",
                    15
            );
            waitForElementAndClick(
                    By.xpath("//*[@content-desc='Add this article to a reading list']"),
                    "Cannot find button to open option add article to a reading list",
                    10
            );
            if(count == 1) {
                waitForElementAndClick(
                        By.id("org.wikipedia:id/onboarding_button"),
                        "Cannot find button to skip onboard",
                        5
                );
            }
            if (count == 1 && waitForElementNotPresent(
                    By.xpath("//*[@resource-id='org.wikipedia:id/lists_container']" +
                            "//*[@text='Art']"),
                    "Found art folder",
                    10))
            {
                waitForElementAndClear(
                        By.id("org.wikipedia:id/text_input"),
                        "Cannot find input field",
                        5
                );
                waitForElementAndSendKeys(
                        By.id("org.wikipedia:id/text_input"),
                        nameFolder,
                        "Cannon write text in the field",
                        5
                );
                waitForElementAndClick(
                        By.xpath("//*[@text='OK']"),
                        "Cannot find OK button",
                        5
                );
            } else {
                waitForElementAndClick(
                        By.xpath("//*[@class='android.widget.LinearLayout']" +
                                "//*[@text='Art']"),
                        "Cannot find art folder",
                        10);

            }
            waitForElementAndClick(
                    By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                    "Cannot close article " + item.getKey(),
                    5
            );

            waitForElementAndClick(
                    By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                    "Cannot find search input",
                    5
            );
        }
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find return button",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find button 'My lists'",
                18
        );
            //проверяет есть ли наша папка в списке
        waitForElementAndClick(
                By.id("org.wikipedia:id/item_container"),
                "Cannot find art folder",
                15
        );
            //удаление и проверка
        String title_1 = waitForElementAndGetAttribute
                        (By.id("org.wikipedia:id/page_list_item_title"),
                            "text",
                            "Cannot find attribute",
                            15);
        swipeElementToLeft(
                    By.id("org.wikipedia:id/page_list_item_title"),
                    "Cannot find an article to remove"
        );
        searchrequests.remove(title_1);
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find article",
                15);
        String title_2 = waitForElementAndGetAttribute
                (By.id("org.wikipedia:id/view_page_title_text"),
                        "text",
                        "Cannot find attribute",
                        15);
            Assert.assertEquals(title_2, searchrequests.entrySet().iterator().next().getKey());


    }

    @Test
    public void assertElementPresent(){
        String searchLine = "Sahara desert ant";
        String locator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                "//*[@text='Sahara Desert ant']";
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search container",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input " + searchLine,
                5
        );
        String title = waitForElementAndGetAttribute(
                By.xpath(locator),
                "text",
                "Cannot find locator by search text " + searchLine,
                15
        );
        Assert.assertEquals(
                "SMTH goes wrong",
                searchLine.toLowerCase(),
                title.toLowerCase()
        );
        waitForElementAndClick(
                By.xpath(locator),
                "Cannot find search article " + searchLine,
                15
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

    protected void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width/2;
        int start_y = (int) (size.height*0.8);
        int end_y = (int) (size.height*0.1);
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
    protected void swipeElementToLeft(By by, String error_message){
        WebElement element = waitForElementPresent(by, error_message, 10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y+lower_y)/2;
        TouchAction action = new TouchAction(driver);
        action.press(right_x, middle_y).waitAction(150).moveTo(left_x, middle_y).release().perform();
    }
    private int getAmountOfElements(By by){
        List<WebElement> elements = driver.findElements(by);
        return elements.size();
    }
    private void assertElementNotPresent(By by, String error_message){
        int amountElements = getAmountOfElements(by);
        if(amountElements > 0){
            String defMessage = "An element "+ by.toString()+ "suppose to be not present";
            throw new AssertionError(defMessage +" "+error_message);
        }
    }
    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeOutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        return element.getAttribute(attribute);
    }

}
