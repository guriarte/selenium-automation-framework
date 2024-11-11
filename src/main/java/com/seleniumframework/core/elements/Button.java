package com.seleniumframework.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Button {
    private final WebElement buttonElement;

    public Button(WebDriver driver, By bySelector){
        this.buttonElement = driver.findElement(bySelector);
    }

    public void click() {
        buttonElement.click();
    }
}
