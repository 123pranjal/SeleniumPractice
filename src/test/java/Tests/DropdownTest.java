package Tests;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;

import pages.DropdownPage;
import org.openqa.selenium.chrome.ChromeOptions;

public class DropdownTest {

    WebDriver driver;
    DropdownPage dropdownPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        // Check if running in CI (GitHub Actions sets this automatically)
        boolean isCI = System.getenv("CI") != null;

        if (isCI) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        if (!isCI) {
            driver.manage().window().maximize();
        }

        driver.get("https://the-internet.herokuapp.com/login");
        dropdownPage = new DropdownPage(driver);
    }

    @Test
    public void testSelectByVisibleText() throws InterruptedException  {
        dropdownPage.selectOptionByText("Option 2");
        Thread.sleep(2000);
        Assert.assertEquals(dropdownPage.getSelectedOption(), "Option 2",
                "Selected option text did not match");
    }

    @Test
    public void testSelectByValue() {
        dropdownPage.selectOptionByValue("1");
        Assert.assertEquals(dropdownPage.getSelectedOption(), "Option 1",
                "Selected option did not match expected value's text");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(),
                        new File("test-output/failure_" + result.getName() + ".png").toPath());
                System.out.println("Page title at failure: " + driver.getTitle());
                System.out.println("Page source snippet: " +
                        driver.getPageSource().substring(0, Math.min(500, driver.getPageSource().length())));
            } catch (Exception e) {
                System.out.println("Screenshot/debug capture failed: " + e.getMessage());
            }
        }
        if (driver != null) {
            driver.quit();
        }
    }
    
}