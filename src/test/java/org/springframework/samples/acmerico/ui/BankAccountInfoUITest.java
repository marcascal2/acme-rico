package org.springframework.samples.acmerico.ui;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAccountInfoUITest {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();
  
  @LocalServerPort
  private int port;

  @BeforeEach
  public void setUp() throws Exception {
	String pathToGeckoDriver = "C:\\Users\\Anton\\Desktop\\Workspace-DP2";
	System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe"); 
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testBankAccountInfoUIPositive() throws Exception {
	    driver.get("http://localhost:" + port + "/");
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
	    driver.findElement(By.id("my-accounts")).click();
	    driver.findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
	    assertEquals("ES23 0025 0148 1259 1424", driver.findElement(By.id("accountNumber")).getAttribute("value"));
	    assertEquals("2567.34", driver.findElement(By.id("amount")).getAttribute("value"));
	    assertEquals("Viajes", driver.findElement(By.id("alias")).getAttribute("value"));
  }
  
  @Test
  public void testBankAccountInfoUINegative() throws Exception {
    driver.get("http://localhost:" + port + "/");
    driver.findElement(By.id("login-button")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("worker1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("worker1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    assertNotEquals("Bank Accounts", driver.findElement(By.id("dropdown-clients")).getText());
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
