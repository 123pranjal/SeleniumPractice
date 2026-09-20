package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DropdownPage {

    WebDriver driver;
    By dropdown = By.id("dropdown");
    WebDriverWait wait;

    public DropdownPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectOptionByText(String visibleText) {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));
        Select select = new Select(driver.findElement(dropdown));
        select.selectByVisibleText(visibleText);
    }

    public void selectOptionByValue(String value) {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));
        Select select = new Select(driver.findElement(dropdown));
        select.selectByValue(value);
    }

    public String getSelectedOption() {
        Select select = new Select(driver.findElement(dropdown));
        return select.getFirstSelectedOption().getText();
    }
}