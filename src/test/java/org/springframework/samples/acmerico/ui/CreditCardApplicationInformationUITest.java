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
public class CreditCardApplicationInformationUITest {
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
  public void testCreditCardApplicationInformationUI() throws Exception {
    driver.get("http://localhost:"+port+"/");
    driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/li[1]/a")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("client1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("client1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("My Applications")).click();
    driver.findElement(By.id("dropdown-clients-apps")).click();
    driver.findElement(By.id("credit-card-apps")).click();
    assertEquals("My Credit Cards Applications", driver.findElement(By.xpath("//h2")).getText());
    assertEquals("PENDING", driver.findElement(By.xpath("//table[@id='cardAppsTable']/tbody/tr[2]/td")).getText());
    assertEquals("client1", driver.findElement(By.xpath("//table[@id='cardAppsTable']/tbody/tr[2]/td[2]")).getText());
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