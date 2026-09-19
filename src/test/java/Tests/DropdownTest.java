package Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/login");
        loginPage = new LoginPage(driver);
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
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}