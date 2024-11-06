package com.framework.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PurchaseFlowTests {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void testPurchaseFlowEndToEnd() {
        driver.get("http://localhost:4200/");
        WebElement signInButton = driver.findElement(By.cssSelector("[data-test=\"nav-sign-in\"]"));
        signInButton.click();

        WebElement emailTextBox = driver.findElement(By.cssSelector("[data-test=\"email\"]"));
        emailTextBox.sendKeys("customer@practicesoftwaretesting.com");

        WebElement passwordTextBox = driver.findElement(By.cssSelector("[data-test=\"password\"]"));
        passwordTextBox.sendKeys("welcome01");

        WebElement submitButton = driver.findElement(By.cssSelector("[data-test=\"login-submit\"]"));
        submitButton.click();

        // add network call for login?
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/account"));
        Assert.assertTrue("The URL does not contain /account", driver.getCurrentUrl().contains("/account"));

        WebElement profileButton = driver.findElement(By.cssSelector("[data-test=\"nav-profile\"]"));
        Assert.assertTrue("Profile button is not displayed", profileButton.isDisplayed());
        WebElement homeButton = driver.findElement(By.cssSelector("[data-test=\"nav-home\"]"));
        homeButton.click();

        WebElement searchBar = driver.findElement(By.cssSelector("[data-test=\"search-query\"]"));
        searchBar.sendKeys("Pliers" + Keys.ENTER);

        List<WebElement> products = driver.findElements(By.cssSelector("[data-test=\"product-name\"]"));
        for (WebElement product : products) {
            if(product.getText().equals("Combination Pliers")){
                product.click();
                break;
            }
        }
        wait.until(ExpectedConditions.urlContains("/product"));
        WebElement increaseQuantityButton = driver.findElement(By.cssSelector("[data-test=\"increase-quantity\"]"));
        increaseQuantityButton.click();
        increaseQuantityButton.click();

        WebElement quantityInputBox = driver.findElement(By.cssSelector("[data-test=\"quantity\"]"));
        Assert.assertEquals("3", quantityInputBox.getAttribute("value"));

        WebElement addToCartButton = driver.findElement(By.cssSelector("[data-test=\"add-to-cart\"]"));
        addToCartButton.click();

        WebElement addedToCartPopup = driver.findElement(By.cssSelector("[id=\"toast-container\"]"));
        Assert.assertTrue("Added to Cart Popup is not visible", addedToCartPopup.isDisplayed());

    }
}
