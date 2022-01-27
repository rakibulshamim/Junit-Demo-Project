import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class JUnit {

    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void Setup(){
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headed");
        driver=new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }
    @Test
    public void getTitle(){
        driver.get("https://demoqa.com");
        System.out.println(driver.getTitle());
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("ToolsQA"));
    }
    @Test
    public void checkIfElementExists(){
        driver.get("https://demoqa.com");
        wait=new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement imgElement = wait.until(ExpectedConditions.elementToBeClickable(By.className("banner-image")));
        boolean status = imgElement.isDisplayed();
        Assert.assertTrue(status);
    }
    @Test
    public void fillupForm(){
        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.id("userName")).sendKeys("Shamim");
        driver.findElement(By.id("userEmail")).sendKeys("shamim@test.com");
        driver.findElement(By.id("currentAddress")).sendKeys("Dhaka");
        driver.findElement(By.id("permanentAddress")).sendKeys("Jamalpur");
        driver.findElement(By.id("submit")).sendKeys(Keys.ENTER);
    }
    @Test
    public void clickButton(){
        driver.get("https://demoqa.com/buttons");
        WebElement doubleClickBtnElement = driver.findElement(By.id("doubleClickBtn"));
        WebElement rightClickBtnElement = driver.findElement(By.id("rightClickBtn"));
        List<WebElement> ClickBtnElement = driver.findElements(By.tagName("button"));

        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickBtnElement).perform();
        actions.contextClick(rightClickBtnElement).perform();
        actions.click(ClickBtnElement.get(3)).perform();

        String text1 = driver.findElement(By.id("doubleClickMessage")).getText();
        String text2 = driver.findElement(By.id("rightClickMessage")).getText();
        String text3 = driver.findElement(By.id("dynamicClickMessage")).getText();

        Assert.assertTrue(text1.contains("You have done a double click"));
        Assert.assertTrue(text2.contains("You have done a right click"));
        Assert.assertTrue(text3.contains("You have done a dynamic click"));
    }
    @Test
    public void clickIfMultipleButtons(){
        driver.get("https://demoqa.com/buttons");
        List<WebElement> list = driver.findElements(By.cssSelector("[type=button]"));
        Actions actions = new Actions(driver);
        actions.doubleClick(list.get(1)).perform();
        actions.contextClick(list.get(2)).perform();
        actions.click(list.get(3)).perform();
    }
    @Test
    public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        driver.switchTo().alert().accept();

        driver.findElement(By.id("timerAlertButton")).click();
        Thread.sleep(5000);
        driver.switchTo().alert().accept();

        driver.findElement(By.id("confirmButton")).click();
        driver.switchTo().alert().dismiss();

        driver.findElement(By.id(("promtButton"))).click();
        driver.switchTo().alert().sendKeys("Shamim");
        driver.switchTo().alert().accept();
        String text= driver.findElement(By.id("promptResult")).getText();
        Assert.assertTrue(text.contains("Shamim"));
    }
    @Test
    public void selectDate(){
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("02/02/2002");
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);
    }
    @Test
    public void selectDropdown(){
        driver.get("https://demoqa.com/select-menu");
        Select color = new Select(driver.findElement(By.id("oldSelectMenu")));
        color.selectByValue("2");
        Select cars=new Select(driver.findElement(By.id("cars")));
        if (cars.isMultiple()) {
            cars.selectByValue("volvo");
            cars.selectByValue("audi");
        }
    }
    @Test
    public void handleNewTab(){
        driver.get("https://demoqa.com/links");
        driver.findElement(By.id("simpleLink")).click();
        ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
        //switch to open tab
        driver.switchTo().window(w.get(1));
        String title = driver.getTitle();
        System.out.println("New tab title: " + driver.getTitle());
        Boolean status = driver.findElement(By.xpath("//img[@src='/images/Toolsqa.jpg']")).isDisplayed();
        Assert.assertEquals(true,status);
        driver.close();
        driver.switchTo().window(w.get(0));
    }
    @Test
    public void handleChildWindow() {
        driver.get("https://demoqa.com/browser-windows");
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("windowButton")));
        button.click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String text = driver.findElement(By.id("sampleHeading")).getText();
                Assert.assertTrue(text.contains("This is a sample page"));
                driver.close();
            }
        }
    }
    @Test
    public void modalDialog() throws InterruptedException {
        driver.get("https://demoqa.com/modal-dialogs");
        driver.findElement(By.id("showSmallModal")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("closeSmallModal")).click();

        driver.findElement(By.id("showLargeModal")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("closeLargeModal")).click();
    }
    @Test
    public void webTables(){
        driver.get("https://demoqa.com/webtables");
        driver.findElement(By.xpath("//span[@id='edit-record-2']//*[@stroke='currentColor']")).click();
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Shamim");
        driver.findElement(By.id("age")).clear();
        driver.findElement(By.id("age")).sendKeys("25");
        driver.findElement(By.id("submit")).click();
    }
    @After
    public void closeBrowser(){
        //driver.quit();
        //driver.close();
    }
}
