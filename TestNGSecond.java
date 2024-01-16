package org.example;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestNGSecond {

    WebDriver chromeDriver;
    WebDriver firefoxDriver;

    @BeforeClass
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();

        WebDriverManager.firefoxdriver().setup();
        firefoxDriver = new FirefoxDriver();
    }

    @Test(priority = 1,description = "Login Test with valid credentials ")
    @Parameters("browser")
    public void loginWithValidCredentials(String browser) {
        WebDriver driver = getDriver(browser);
        driver.get("https://www.saucedemo.com/");

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

    @Test(priority = 2, description = "Login Test with invalid credentials")
    @Parameters("browser")
    public void loginWithInvalidCredentials(String browser) {
        WebDriver driver = getDriver(browser);
        driver.get("https://www.saucedemo.com/");

        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameInput.sendKeys("invalid_user");
        passwordInput.sendKeys("invalid_password");
        loginButton.click();

        // Verify that the login fails and an error message is displayed
        WebElement errorLabel = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertTrue(errorLabel.isDisplayed());
    }

    @Test(priority = 3 , description = "Login Test with Empty credentials")
    @Parameters("browser")
    public void loginWithEmptyCredentials(String browser) {
        WebDriver driver = getDriver(browser);
        driver.get("https://www.saucedemo.com/");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Verify that the login fails and an error message is displayed
        WebElement errorLabel = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertTrue(errorLabel.isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        chromeDriver.quit();
        firefoxDriver.quit();
    }

    private WebDriver getDriver(String browser) {
        return browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
    }
}



