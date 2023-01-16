package TakeHomeTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class main {
    WebDriver driver = new ChromeDriver();
    LogEntries logEntries;


    @BeforeSuite
    public void setup() {

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @AfterSuite
    public void tearDown() {

        driver.quit();
    }

    @Test
    public void LoginSucess() {


        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button/i"));

        username.clear();
        username.sendKeys("tomsmith");

        password.clear();
        password.sendKeys("SuperSecretPassword!");

        loginButton.click();

        WebElement header = driver.findElement(By.xpath("//*[@id=\"flash\"]"));
        System.out.println(header.getText());

        String title = "You logged into a secure area!\n" +
                "×";

        String actualTitle = header.getText();

        Assert.assertEquals(actualTitle, title);
    }

    @Test
    public void LoginFail() {

        String url = "http://localhost:7080/login";
        driver.get(url);


        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button/i"));

        username.clear();
        username.sendKeys("tomsmiths");

        password.clear();
        password.sendKeys("SuperSecretPassword!");

        loginButton.click();

        WebElement header = driver.findElement(By.xpath("//*[@id=\"flash\"]"));
        System.out.println(header.getText());

        String title = "Your username is invalid!\n" +
                "×";

        String actualTitle = header.getText();

        Assert.assertEquals(actualTitle, title);
    }

    @Test
    public void CheckBox() throws InterruptedException {

        String url = "http://localhost:7080/checkboxes";
        driver.get(url);

        WebElement checkBoxSelected = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/input[1]"));
        checkBoxSelected.click();
        Assert.assertTrue(checkBoxSelected.isSelected());

        Thread.sleep(5000);

        WebElement checkBoxNotSelected = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/input[2]"));
        checkBoxNotSelected.click();
        Assert.assertFalse(checkBoxNotSelected.isSelected());
    }

    @Test
    public void ContextMenu() throws InterruptedException {

        String url = "http://localhost:7080/context_menu";
        driver.get(url);

        WebElement openMenu = driver.findElement(By.xpath("//*[@id=\"hot-spot\"]"));
        Actions action = new Actions(driver);
        action.contextClick(openMenu).perform();

        Alert alert1 = driver.switchTo().alert();
        String print1 = alert1.getText();
        System.out.println(print1);
        Assert.assertEquals(print1, "You selected a context menu", "True");
        alert1.dismiss();
    }


    @Test
    public void DropDownTest() throws InterruptedException {

        String url = "http://localhost:7080/dropdown";
        driver.get(url);

        //Option 1
        Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"dropdown\"]")));
        String value = "1";
        dropdown.selectByValue(value);

        WebElement o = dropdown.getFirstSelectedOption();
        String selectedoption = o.getText();
        System.out.println("Selected element: " + selectedoption);

        Assert.assertEquals(value, "1", "This is True");
        System.out.println("Assert is True");

        Thread.sleep(5000);

        //Option 2

        Select dropdown1 = new Select(driver.findElement(By.xpath("//*[@id=\"dropdown\"]")));
        String value1 = "2";
        dropdown1.selectByValue(value1);

        WebElement o1 = dropdown.getFirstSelectedOption();
        String selectedoption1 = o1.getText();
        System.out.println("Selected element: " + selectedoption1);

        Assert.assertEquals(value1, "2", "This is True");
        System.out.println("Assert is True");
    }

    @Test
    public void DynamicContent() throws InterruptedException {

        String url = "http://localhost:7080/dynamic_content";
        driver.get(url);

        String title1 = driver.getPageSource();

        driver.navigate().refresh();

        Thread.sleep(5000);

        String title2 = driver.getPageSource();

        driver.navigate().refresh();

        Thread.sleep(5000);

        String title3 = driver.getPageSource();

        Assert.assertNotEquals(title1, title2 , title3);
    }

    @Test
    public void DynamicControls() throws InterruptedException {

        String url = "http://localhost:7080/dynamic_controls";
        driver.get(url);

        //Remove Button

        WebElement removeTest = driver.findElement(By.xpath("//*[@id=\"checkbox-example\"]/button"));
        removeTest.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkbox\"]/input")));

        Assert.assertTrue(checkbox.isEnabled(), "It is enabled");
        System.out.println("It is Gone");

        //Add Button

        WebElement addTest = driver.findElement(By.xpath("//*[@id=\"checkbox-example\"]/button"));
        addTest.click();
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkbox\"]/input")));

        Assert.assertTrue(element1.isDisplayed(), "It is enabled");
        System.out.println("It is Present");

        Thread.sleep(5000);

        //Enable Button

        WebElement enableTest = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/form[2]/button"));
        enableTest.click();
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element2 = wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div[1]/form[2]/input")));
        element2.sendKeys("Why");

        Assert.assertTrue(element2.isEnabled(), "It is enabled");
        System.out.println("It is Enabled");

        //Disabled

        WebElement disableTest = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/form[2]/button"));
        disableTest.click();
        WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element3 = wait3.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div[1]/form[2]/input")));

        Thread.sleep(5000);

        Assert.assertFalse(element3.isEnabled(), "It is Disabled");
        System.out.println("It is Disabled");
    }

    @Test
    public void DynamicLoading() throws InterruptedException {

        String url = "http://localhost:7080/dynamic_loading/2";
        driver.get(url);

        WebElement loadingClick = driver.findElement(By.xpath("//*[@id=\"start\"]/button"));
        loadingClick.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"finish\"]")));
        Assert.assertTrue(text.isEnabled(), "It is Loaded");
        System.out.println("It is Displaying : " + text.getText());
    }

    @Test
    public void DownloadTest() throws InterruptedException {

        String url = "http://localhost:7080/download";
        driver.get(url);

        WebElement linkToDownload = driver.findElement(By.linkText("some-file.txt"));
        linkToDownload.click();

        File f = new File("/Users/ketanmishra/Downloads/some-file.txt");

        Assert.assertTrue(f.isFile());
        System.out.println("File Found");
    }

    @Test
    public void UploadTest() throws InterruptedException {

        String url = "http://localhost:7080/upload";
        driver.get(url);

        WebElement linkToUpload = driver.findElement(By.xpath("//*[@id=\"file-upload\"]"));
        linkToUpload.sendKeys("/Users/ketanmishra/Downloads/some-file.txt");

        WebElement upload = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/form/input[2]"));
        upload.click();

        WebElement nextPage = driver.findElement(By.xpath("/html/body/div[2]/div/div/h3"));

        Assert.assertTrue(nextPage.isDisplayed());
        System.out.println("Success : " + nextPage.getText());

    }

    @Test
    public void FloatingMenu() throws InterruptedException {

        String url = "http://localhost:7080/floating_menu";
        driver.get(url);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        Thread.sleep(5000);

        WebElement bar = driver.findElement(By.xpath("/html/body/div[2]/div/div/h3"));

        Assert.assertTrue(bar.isDisplayed());
        System.out.println(bar.getText());

    }
    @Test
    public void iFrame() throws InterruptedException {

        String url = "http://localhost:7080/iframe";
        driver.get(url);

        driver.switchTo().frame("mce_0_ifr");
        WebElement txt = driver.findElement(By.xpath("//*[@id=\"tinymce\"]"));
        txt.clear();
        txt.sendKeys("Testing");

        String txt1 = txt.getText();

        Assert.assertEquals(txt1, "Testing");
    }


    @Test
    public void MouseHover() throws InterruptedException {

        String url = "http://localhost:7080/hovers";
        driver.get(url);

        Actions actions = new Actions(driver);
        WebElement test = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/img"));
        actions.moveToElement(test).perform();

        Thread.sleep(5000);

        WebElement user1 = driver.findElement(By.xpath("//html/body/div[2]/div/div/div[1]/div/h5"));
        Assert.assertTrue(user1.isDisplayed());
        System.out.println("Selected element Displays : " + user1.getText());

        //user 2

        WebElement test1 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/img"));
        actions.moveToElement(test1).perform();

        Thread.sleep(5000);

        WebElement user2 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div/h5"));
        Assert.assertTrue(user2.isDisplayed());
        System.out.println("Selected element Displays : " + user2.getText());

        //User 3:

        WebElement test2 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[3]/img"));
        actions.moveToElement(test2).perform();

        Thread.sleep(5000);

        WebElement user3 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[3]/div/h5"));
        Assert.assertTrue(user3.isDisplayed());
        System.out.println("Selected element Displays : " + user3.getText());


    }
    @Test
    public void JavaScriptAlet() throws InterruptedException {

        String url = "http://localhost:7080/javascript_alerts";
        driver.get(url);

        //JS1

        WebElement popup1 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/ul/li[1]/button"));
        popup1.click();
        Thread.sleep(5000);
        Alert alert = driver.switchTo().alert();
        String print = alert.getText();
        System.out.println(print);
        Assert.assertEquals(print,"I am a JS Alert", "True");
        alert.dismiss();

        //JS2

        WebElement popup2 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/ul/li[2]/button"));
        popup2.click();
        Thread.sleep(5000);
        Alert alert1 = driver.switchTo().alert();
        String print1 = alert1.getText();
        System.out.println(print1);
        Assert.assertEquals(print1,"I am a JS Confirm", "True");
        alert1.accept();

        //JS3

        WebElement popup3 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/ul/li[3]/button"));
        popup3.click();
        Thread.sleep(5000);
        Alert alert2 = driver.switchTo().alert();
        alert2.sendKeys("Testing");
        String print2 = alert.getText();
        System.out.println(print2);
        Assert.assertEquals(print2,"I am a JS prompt", "True");
        alert2.accept();

    }
    public void logConsoleEntries (LogEntries logEntries) {
        for (LogEntry logEntry : logEntries) {
            System.out.println(String.valueOf(" Time Stamp: " + logEntry.getTimestamp()));
            System.out.println(String.valueOf(" Log Level: " + logEntry.getLevel()));
            System.out.println(String.valueOf(" Log Message: " + logEntry.getMessage()));

        }
    }

    @Test
    public void JavaScripError() throws InterruptedException {
        String url = "http://localhost:7080/javascript_error";
        driver.get(url);

        logEntries = driver.manage().logs().get(LogType.BROWSER);
        logConsoleEntries(logEntries);

        Assert.assertEquals(logEntries.toString().isBlank(), Boolean.FALSE);

    }
    @Test
    public void NewTab() throws InterruptedException {

        String url = "http://localhost:7080/windows";
        driver.get(url);

        WebElement newWindow = driver.findElement(By.xpath("//*[@id=\"content\"]/div/a"));
        newWindow.click();

        List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs .get(1));
        WebElement title = driver.findElement(By.xpath("/html/body/div"));
        System.out.println(title.getText());
        String title1 = title.getText();

        Assert.assertEquals(title1, "New Window");
    }

    @Test
    public void DragAndDrop() throws InterruptedException, AWTException {
        Actions actions = new Actions(driver);
        WebElement from = driver.findElement(By.id("column-a"));
        WebElement to = driver.findElement(By.id("column-b"));

        ///Before dragging

        String header1 = from.getText();
        System.out.println(header1);

        String header2 = to.getText();
        System.out.println(header2);

        // The issue with the DragAndDrop in still not fixed, that's why using this.
        ((JavascriptExecutor) driver).executeScript(compileScript(), from, to);

        //After Dragging
        String header3 = from.getText();
        System.out.println(header3);

        String header4 = to.getText();
        System.out.println(header4);

        //First Box Verification
        Assert.assertNotEquals(header1, header3);

        //Second Box Verification
        Assert.assertNotEquals(header2, header4);

    }

    private String compileScript() {
        return "function createEvent(typeOfEvent) {\n" + "var event = document.createEvent(\"CustomEvent\");\n"
                + "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n"
                + "data: {},\n" + "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
                + "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n"
                + "return event;\n" + "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
                + "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
                + "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
                + "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n"
                + "}\n" + "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
                + "var dragStartEvent =createEvent('dragstart');\n"
                + "dispatchEvent(element, dragStartEvent);\n" + "var dropEvent = createEvent('drop');\n"
                + "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
                + "var dragEndEvent = createEvent('dragend');\n"
                + "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
                + "var source = arguments[0];\n" + "var destination = arguments[1];\n"
                + "simulateHTML5DragAndDrop(source,destination);";
    }



}

