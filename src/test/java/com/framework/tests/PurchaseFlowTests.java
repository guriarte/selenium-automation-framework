package com.framework.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
        driver.manage().window().maximize();
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[id=\"toast-container\"]")));

        WebElement shoppingCartButton = driver.findElement(By.cssSelector("[data-test=\"nav-cart\"]"));
        shoppingCartButton.click();

        wait.until(ExpectedConditions.urlContains("/checkout"));
        WebElement rowContainingProduct = driver.findElement(By.xpath("//tr/td/span[contains(text(), \"Combination Pliers\")]"));
        WebElement quantityInputBoxForProduct = rowContainingProduct.findElement(By.xpath("//input"));
        quantityInputBoxForProduct.clear();
        quantityInputBoxForProduct.sendKeys("1");

        WebElement firstProceedToCheckoutButton = driver.findElement(By.cssSelector("[data-test=\"proceed-1\"]"));
        firstProceedToCheckoutButton.click();

        WebElement secondProceedToCheckoutButton = driver.findElement(By.cssSelector("[data-test=\"proceed-2\"]"));
        secondProceedToCheckoutButton.click();

        WebElement addressTextField = driver.findElement(By.cssSelector("[data-test=\"address\"]"));
        addressTextField.clear();
        addressTextField.sendKeys("Fake Address");

        WebElement cityTextField = driver.findElement(By.cssSelector("[data-test=\"city\"]"));
        cityTextField.clear();
        cityTextField.sendKeys("Fake City");

        WebElement stateTextField = driver.findElement(By.cssSelector("[data-test=\"state\"]"));
        stateTextField.clear();
        stateTextField.sendKeys("Fake State");

        WebElement countryTextField = driver.findElement(By.cssSelector("[data-test=\"country\"]"));
        countryTextField.clear();
        countryTextField.sendKeys("Fake Country");

        WebElement postCodeTextField = driver.findElement(By.cssSelector("[data-test=\"postcode\"]"));
        postCodeTextField.clear();
        postCodeTextField.sendKeys("12345");

        WebElement thirdProceedToCheckoutButton = driver.findElement(By.cssSelector("[data-test=\"proceed-3\"]"));
        thirdProceedToCheckoutButton.click();

        WebElement paymentDropdownElement = driver.findElement(By.cssSelector("[data-test=\"payment-method\"]"));
        Select paymentDropdown = new Select(paymentDropdownElement);

        paymentDropdown.selectByValue("credit-card");

        WebElement creditCardNumberTextBox = driver.findElement(By.cssSelector("[data-test=\"credit_card_number\"]"));
        creditCardNumberTextBox.clear();
        creditCardNumberTextBox.sendKeys("1234-5678-9012-3456");

        WebElement expirationDateTextBox = driver.findElement(By.cssSelector("[data-test=\"expiration_date\"]"));
        expirationDateTextBox.clear();
        expirationDateTextBox.sendKeys("11/2032");

        WebElement cvvTextBox = driver.findElement(By.cssSelector("[data-test=\"cvv\"]"));
        cvvTextBox.clear();
        cvvTextBox.sendKeys("123");

        WebElement cardHolderNameTextBox = driver.findElement(By.cssSelector("[data-test=\"card_holder_name\"]"));
        cardHolderNameTextBox.clear();
        cardHolderNameTextBox.sendKeys("Jane Doe");

        WebElement confirmButton = driver.findElement(By.cssSelector("[data-test=\"finish\"]"));
        confirmButton.click();
        confirmButton.click();
    }

}

