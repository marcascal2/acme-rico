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
public class CreditCardInformationUITest {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @LocalServerPort
  private int port;

  @BeforeEach
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCreditCardInformation() throws Exception {
    driver.get("http://localhost:"+port+"/");
    driver.findElement(By.id("login-button")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("client1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("client1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Bank Accounts")).click();
    driver.findElement(By.id("dropdown-clients")).click();
    driver.findElement(By.id("my-credit-card")).click();
    driver.findElement(By.linkText("4095742744779740")).click();
    assertEquals("4095742744779740", driver.findElement(By.id("number")).getAttribute("value"));
    assertEquals("123", driver.findElement(By.id("cvv")).getAttribute("value"));
  }

//   TODO: Preguntar duda con el navigate().to()
//   @Test
//   public void testCreditCardInformationNegative() throws Exception {
//     driver.get("http://localhost:"+port+"/");
//     driver.findElement(By.id("login-button")).click();
//     driver.findElement(By.id("username")).clear();
//     driver.findElement(By.id("username")).sendKeys("worker1");
//     driver.findElement(By.id("password")).click();
//     driver.findElement(By.id("password")).clear();
//     driver.findElement(By.id("password")).sendKeys("worker1");
//     driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
//     driver.findElement(By.linkText("Bank Accounts")).click();
//     driver.navigate().to("http://localhost:"+port+"/cards/1/show");
//   }

  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}