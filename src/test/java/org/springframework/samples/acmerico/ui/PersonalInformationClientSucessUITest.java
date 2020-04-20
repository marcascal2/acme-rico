package org.springframework.samples.acmerico.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.jupiter.api.Test;

public class PersonalInformationClientSucessUITest {
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
  public void testPersonalInformationClientSuccessUI() throws Exception {
    driver.get("http://localhost:8080/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("worker1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("worker1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    assertEquals("worker1", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
    driver.findElement(By.linkText("Clients")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("George Franklin")).click();
    assertEquals("Client Information", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("110 W. Liberty St.", driver.findElement(By.xpath("//tr[2]/td")).getText());
    assertEquals("George Franklin", driver.findElement(By.xpath("//b")).getText());
    assertEquals("1000.0", driver.findElement(By.xpath("//tr[6]/td")).getText());
    assertEquals("Madison", driver.findElement(By.xpath("//tr[4]/td")).getText());
    driver.findElement(By.xpath("//th")).click();
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
