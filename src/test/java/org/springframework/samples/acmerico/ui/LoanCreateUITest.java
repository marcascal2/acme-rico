package org.springframework.samples.acmerico.ui;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanCreateUITest {

  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @LocalServerPort
  private int port;
  
  @BeforeEach
  public void setUp() throws Exception {
    // System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void clientLoginUITest() throws Exception {
    driver.get("http://localhost:" + port + "/");
    driver.findElement(By.id("login-button")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("director1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("director1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Loans")).click();
    driver.findElement(By.id("loans-dropdown")).click();
    driver.findElement(By.id("see-loans")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("description")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("Prueba");
    driver.findElement(By.id("minimum_amount")).clear();
    driver.findElement(By.id("minimum_amount")).sendKeys("1000");
    driver.findElement(By.id("minimum_income")).clear();
    driver.findElement(By.id("minimum_income")).sendKeys("900");
    driver.findElement(By.id("number_of_deadlines")).clear();
    driver.findElement(By.id("number_of_deadlines")).sendKeys("10");
    driver.findElement(By.id("opening_price")).clear();
    driver.findElement(By.id("opening_price")).sendKeys("300");
    driver.findElement(By.id("monthly_fee")).clear();
    driver.findElement(By.id("monthly_fee")).sendKeys("0.04");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("Prueba", driver.findElement(By.xpath("//table[@id='loans']/tbody/tr[4]/td[2]")).getText());
    assertEquals("4", driver.findElement(By.linkText("4")).getText());
    driver.quit();
  }

  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}