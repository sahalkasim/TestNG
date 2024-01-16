package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class TestNGFirst {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test(priority = 1,description = "Login Test with valid credentials")
    public void loginWithValidCredentials() {
        driver.get("https://www.saucedemo.com/v1");

        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameInput.sendKeys("standard_user");
        passwordInput.sendKeys("secret_sauce");
        loginButton.click();

        // Verify that login is successful by checking for the presence of a product on the next page
        WebElement productLabel = driver.findElement(By.className("inventory_item_name"));
        Assert.assertTrue(productLabel.isDisplayed());
    }
    @Test(dependsOnMethods={"loginWithValidCredentials"},description = "Adding a product on inventory page test post successful SignIn")
    public void addToCart() {

        // Add a product to the cart
        WebElement addToCartButton = driver.findElement(By.xpath("//button[text()='ADD TO CART']"));
        addToCartButton.click();

        // Verify that the product is added to the cart successfully
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "1");
    }

    @Test(priority = 3,description = "Logout Test ")
    public void logout() {

        WebElement menuButton = driver.findElement(By.xpath("//*[@id='menu_button_container']/div/div[3]/div/button"));
        menuButton.click();

        WebElement logoutButton = driver.findElement(By.id("logout_sidebar_link"));
        logoutButton.click();

        // Verify that the user is redirected to the login page after logout
        WebElement loginButton = driver.findElement(By.id("login-button"));
        Assert.assertTrue(loginButton.isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
