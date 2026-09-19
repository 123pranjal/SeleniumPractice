package Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExcelUtils;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;
    int rowIndex = 0;

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

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws Exception {
        return ExcelUtils.getLoginData();
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, String expectedMessage) throws Exception {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickSubmit();

        String actualMessage = loginPage.getFlashMessage();
        String status = actualMessage.contains(expectedMessage) ? "PASS" : "FAIL";
        ExcelUtils.writeResult(rowIndex, actualMessage, status);
        rowIndex++;

        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Expected message not found for username: " + username);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}