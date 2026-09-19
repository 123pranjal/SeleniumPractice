package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownPage {

    WebDriver driver;
    By dropdown = By.id("dropdown");

    public DropdownPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectOptionByText(String visibleText) {
        Select select = new Select(driver.findElement(dropdown));
        select.selectByVisibleText(visibleText);
    }

    public void selectOptionByValue(String value) {
        Select select = new Select(driver.findElement(dropdown));
        select.selectByValue(value);
    }

    public String getSelectedOption() {
        Select select = new Select(driver.findElement(dropdown));
        return select.getFirstSelectedOption().getText();
    }
}