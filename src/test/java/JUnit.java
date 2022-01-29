import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.openqa.selenium.By.*;

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
        WebElement imgElement = wait.until(ExpectedConditions.elementToBeClickable(className("banner-image")));
        boolean status = imgElement.isDisplayed();
        Assert.assertTrue(status);
    }
    @Test
    public void fillupForm(){
        driver.get("https://demoqa.com/text-box");
        driver.findElement(id("userName")).sendKeys("Shamim");
        driver.findElement(id("userEmail")).sendKeys("shamim@test.com");
        driver.findElement(id("currentAddress")).sendKeys("Dhaka");
        driver.findElement(id("permanentAddress")).sendKeys("Jamalpur");
        driver.findElement(id("submit")).sendKeys(Keys.ENTER);
    }
    @Test
    public void clickButton(){
        driver.get("https://demoqa.com/buttons");
        WebElement doubleClickBtnElement = driver.findElement(id("doubleClickBtn"));
        WebElement rightClickBtnElement = driver.findElement(id("rightClickBtn"));
        List<WebElement> ClickBtnElement = driver.findElements(tagName("button"));

        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickBtnElement).perform();
        actions.contextClick(rightClickBtnElement).perform();
        actions.click(ClickBtnElement.get(3)).perform();

        String text1 = driver.findElement(id("doubleClickMessage")).getText();
        String text2 = driver.findElement(id("rightClickMessage")).getText();
        String text3 = driver.findElement(id("dynamicClickMessage")).getText();

        Assert.assertTrue(text1.contains("You have done a double click"));
        Assert.assertTrue(text2.contains("You have done a right click"));
        Assert.assertTrue(text3.contains("You have done a dynamic click"));
    }
    @Test
    public void clickIfMultipleButtons(){
        driver.get("https://demoqa.com/buttons");
        List<WebElement> list = driver.findElements(cssSelector("[type=button]"));
        Actions actions = new Actions(driver);
        actions.doubleClick(list.get(1)).perform();
        actions.contextClick(list.get(2)).perform();
        actions.click(list.get(3)).perform();
    }
    @Test
    public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(id("alertButton")).click();
        driver.switchTo().alert().accept();

        driver.findElement(id("timerAlertButton")).click();
        Thread.sleep(5000);
        driver.switchTo().alert().accept();

        driver.findElement(id("confirmButton")).click();
        driver.switchTo().alert().dismiss();

        driver.findElement(id(("promtButton"))).click();
        driver.switchTo().alert().sendKeys("Shamim");
        driver.switchTo().alert().accept();
        String text= driver.findElement(id("promptResult")).getText();
        Assert.assertTrue(text.contains("Shamim"));
    }
    @Test
    public void selectDate(){
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(id("datePickerMonthYearInput")).sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        driver.findElement(id("datePickerMonthYearInput")).sendKeys("02/02/2002");
        driver.findElement(id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);
    }
    @Test
    public void selectDropdown() throws InterruptedException {
        driver.get("https://demoqa.com/select-menu");
        Select color=new Select(driver.findElement(By.id("oldSelectMenu")));
        color.selectByValue("2");
        Select cars=new Select(driver.findElement(By.id("cars")));
        if (cars.isMultiple()) {
            cars.selectByValue("volvo");
            cars.selectByValue("audi");
        }
        wait=new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//input[@id='react-select-2-input']"))).sendKeys("A root option");
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//div[contains(@id,'react-select')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//input[@id='react-select-3-input']"))).sendKeys("Mrs.");
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//div[contains(@id,'react-select')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(xpath("//input[@id='react-select-4-input']")))
                .sendKeys(Keys.chord("Green",Keys.TAB,"Blue",Keys.TAB,"Black",Keys.TAB,"Red",Keys.TAB));
    }
    @Test
    public void handleNewTab(){
        driver.get("https://demoqa.com/links");
        driver.findElement(id("simpleLink")).click();
        ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
        //switch to open tab
        driver.switchTo().window(w.get(1));
        String title = driver.getTitle();
        System.out.println("New tab title: " + driver.getTitle());
        Boolean status = driver.findElement(xpath("//img[@src='/images/Toolsqa.jpg']")).isDisplayed();
        Assert.assertEquals(true,status);
        driver.close();
        driver.switchTo().window(w.get(0));
    }
    @Test
    public void handleChildWindow() {
        driver.get("https://demoqa.com/browser-windows");
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(id("windowButton")));
        button.click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String text = driver.findElement(id("sampleHeading")).getText();
                Assert.assertTrue(text.contains("This is a sample page"));
                driver.close();
            }
        }
    }
    @Test
    public void modalDialog() throws InterruptedException {
        driver.get("https://demoqa.com/modal-dialogs");
        driver.findElement(id("showSmallModal")).click();
        Thread.sleep(1000);
        driver.findElement(id("closeSmallModal")).click();

        driver.findElement(id("showLargeModal")).click();
        Thread.sleep(1000);
        driver.findElement(id("closeLargeModal")).click();
    }
    @Test
    public void webTables(){
        driver.get("https://demoqa.com/webtables");
        driver.findElement(xpath("//span[@id='edit-record-2']//*[@stroke='currentColor']")).click();
        driver.findElement(id("firstName")).clear();
        driver.findElement(id("firstName")).sendKeys("Shamim");
        driver.findElement(id("age")).clear();
        driver.findElement(id("age")).sendKeys("25");
        driver.findElement(id("submit")).click();
    }
    @Test
    public void scrapData(){
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(className("rt-tbody"));
        List<WebElement> allRows = table.findElements(className("rt-tr"));
        int i=0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(className("rt-td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num["+i+"] "+ cell.getText());
            }
        }
    }
    @Test
    public void uploadImage(){
        driver.get("https://demoqa.com/upload-download");
        WebElement uploadElement = driver.findElement(id("uploadFile"));
        uploadElement.sendKeys("D:\\HD.jpg");
        String text= driver.findElement(id("uploadedFilePath")).getText();
        Assert.assertTrue(text.contains("HD.jpg"));
    }
    @Test
    public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String text= driver.findElement(id("sampleHeading")).getText();
        System.out.println(text);
        Assert.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();
    }
    @Test
    public void mouseHover() throws InterruptedException {
        driver.get("https://www.aiub.edu/");
        WebElement menuAboutElement = driver.findElement(xpath("//a[@href='/about']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(menuAboutElement).perform();
        Thread.sleep(2000);
        WebElement subMenu = driver.findElement(xpath("//a[@href='/about/Information']"));
        actions.moveToElement(subMenu);
        actions.click().build().perform();
    }
    @Test
    public void keyboardEvents() throws InterruptedException {
        driver.get("https://www.google.com/");
        WebElement searchElement = driver.findElement(name("q"));
        Actions action = new Actions(driver);
        action.moveToElement(searchElement);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("Selenium Webdriver")
                .keyUp(Keys.SHIFT)
                .doubleClick()
                .contextClick()
                .perform();

        Thread.sleep(5000);
    }
    @Test
    public void takeScreenShot() throws IOException {
        driver.get("https://demoqa.com");
        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(screenshotFile, DestFile);
    }
    public static void readFromExcel(String filePath,String fileName,String sheetName) throws IOException {
        File file =    new File(filePath+"\\"+fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        if(fileExtensionName.equals(".xls")){
            workbook = new HSSFWorkbook(inputStream);
        }
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
        for (int i = 0; i < rowCount+1; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                DataFormatter formatter = new DataFormatter();
                System.out.println(formatter.formatCellValue((row.getCell(j))));
            }
            System.out.println();
        }
    }
    @Test
    public void readData() throws IOException {
        readFromExcel("D:\\","Book1.xls","Sheet1");
    }
    @After
    public void closeBrowser(){
        driver.quit();
        //driver.close();
    }
}
