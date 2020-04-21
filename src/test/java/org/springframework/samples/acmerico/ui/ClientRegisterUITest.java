package org.springframework.samples.acmerico.ui;

import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientRegisterUITest {


  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @LocalServerPort
  private int port;

  @BeforeEach
  public void setUp() throws Exception {
    // String pathToGeckoDriver="C:\\Users\\Javier\\Downloads";
    // System.setProperty("webdriver.gecko.driver", pathToGeckoDriver+ "\\geckodriver.exe");
    System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testClientRegisterUI() throws Exception {
    driver.get("http://localhost:"+port+"/users/new");
    driver.findElement(By.id("firstName")).click();
    driver.findElement(By.id("firstName")).clear();
    driver.findElement(By.id("firstName")).sendKeys("Javier");
    driver.findElement(By.id("lastName")).clear();
    driver.findElement(By.id("lastName")).sendKeys("Ruiz");
    driver.findElement(By.id("address")).clear();
    driver.findElement(By.id("address")).sendKeys("Gordal 9");
    driver.findElement(By.id("birthDate")).clear();
    driver.findElement(By.id("birthDate")).sendKeys("1998/11/27");
    driver.findElement(By.id("city")).clear();
    driver.findElement(By.id("city")).sendKeys("Sevilla");
    driver.findElement(By.id("maritalStatus")).clear();
    driver.findElement(By.id("maritalStatus")).sendKeys("Single");
    driver.findElement(By.id("salaryPerYear")).clear();
    driver.findElement(By.id("salaryPerYear")).sendKeys("1500000000");
    driver.findElement(By.id("age")).clear();
    driver.findElement(By.id("age")).sendKeys("21");
    driver.findElement(By.id("job")).clear();
    driver.findElement(By.id("job")).sendKeys("Student");
    driver.findElement(By.id("lastEmployDate")).clear();
    driver.findElement(By.id("lastEmployDate")).sendKeys("2018/11/27");
    driver.findElement(By.id("user.username")).clear();
    driver.findElement(By.id("user.username")).sendKeys("javi");
    driver.findElement(By.id("user.password")).clear();
    driver.findElement(By.id("user.password")).sendKeys("javi");
    driver.findElement(By.xpath("//*[@id='dniFile']")).sendKeys("C:\\Users\\Javier\\Pictures\\629642.jpg");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.get("http://localhost:"+port+"/login");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("javi");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("javi");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    assertEquals("JAVI", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
  }


  @Test
  public void testClientRegisterUINegativeCase() throws Exception{
    driver.get("http://localhost:"+port+"/login");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("client1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("client1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    assertEquals("client1", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
    //assertEquals(false, driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/li[2]/a")).isDisplayed());
    //assertEquals(0, driver.findElement(By.linkText("Register")).getSize());
    assertEquals(false, isElementPresent(By.linkText("Register")));

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
