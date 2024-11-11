package com.seleniumframework.pages;

import com.seleniumframework.core.elements.Button;
import com.seleniumframework.core.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;
    final By signInButtonCssSelector = By.cssSelector("[data-test=\"nav-sign-in\"]");

    public HomePage(WebDriver driver, WaitUtils waitUtils) {
        this.driver = driver;
        this.waitUtils = waitUtils;
    }

    public void visit() {
        driver.get("http://localhost:4200/");
    }

    public void clickSignInButton() {
        Button signInButton = new Button(driver, signInButtonCssSelector);
        signInButton.click();
    }
}
