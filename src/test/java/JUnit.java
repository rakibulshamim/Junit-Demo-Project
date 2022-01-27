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
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

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
    @After
    public void closeBrowser(){
        //driver.quit();
    }
}
