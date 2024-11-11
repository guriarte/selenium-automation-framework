package com.seleniumframework.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Input {
    private final WebElement inputElement;

    public Input(WebDriver driver, By bySelector) {
        this.inputElement = driver.findElement(bySelector);
    }

    public void typeText(String text) {
        inputElement.sendKeys(text);
    }
}
