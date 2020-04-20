package org.springframework.samples.acmerico.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CreditCardApplicationInformationErrorUITest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
    String pathToGeckoDriver="C:\\Users\\Javier\\Downloads";
    System.setProperty("webdriver.gecko.driver", pathToGeckoDriver+ "\\geckodriver.exe");
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCreditCardApplicationInformationErrorUI() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("client1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("client1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/div/a/span")).click();
    driver.findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
    driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
    driver.findElement(By.linkText("Logout")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("worker1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("worker1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("worker1")).click();
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/div/a/span")).click();
    assertEquals("worker1", driver.findElement(By.linkText("worker1")).getText());
    assertEquals("10", driver.findElement(By.linkText("10")).getText());
    assertEquals("client1", driver.findElement(By.xpath("//table[@id='creditCardsAppTable']/tbody/tr[10]/td[3]")).getText());
    driver.findElement(By.linkText("10")).click();
    assertEquals("PENDING", driver.findElement(By.xpath("//tr[2]/td")).getText());
    assertEquals("client1", driver.findElement(By.xpath("//tr[3]/td")).getText());
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
    driver.findElement(By.linkText("Logout")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("client1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("client1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.id("navbarDropdown")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/div/a[3]/span")).click();
  }

  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
