package com.seleniumframework.pages;

import com.seleniumframework.core.elements.Button;
import com.seleniumframework.core.elements.Input;
import com.seleniumframework.core.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;
    private final String uri = "/auth/login";
    private final By emailTextBoxCssSelector = By.cssSelector("[data-test=\"email\"]");
    private final By passwordTextBoxCssSelector = By.cssSelector("[data-test=\"password\"]");
    private final By submitButtonCssSelector = By.cssSelector("[data-test=\"login-submit\"]");

    public LoginPage(WebDriver driver, WaitUtils waitUtils) {
        this.driver = driver;
        this.waitUtils = waitUtils;
    }

    public void fillEmailTextBox(String text) {
        Input emailTextBox = new Input(driver, emailTextBoxCssSelector);
        emailTextBox.typeText(text);
    }

    public void fillPasswordTextBox(String text) {
        Input passwordTextBox = new Input(driver, passwordTextBoxCssSelector);
        passwordTextBox.typeText(text);
    }

    public void clickSubmitButton() {
        Button submitButton = new Button(driver, submitButtonCssSelector);
        submitButton.click();
    }

}
