package org.springframework.samples.acmerico.ui;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyExchangeUITest {
	
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@LocalServerPort
	private int port;

	@BeforeEach  //HU 17
	public void setUp() throws Exception {
//		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testExchangeRates() throws Exception {
		driver.get("http://localhost:" + port + "/");
	    driver.findElement(By.id("login-button")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("client1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("client1");
	    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
	    driver.findElement(By.id("dropdown-clients-bank-accounts")).click();
	    driver.findElement(By.id("exchanges")).click();
	    new Select(driver.findElement(By.id("initRate"))).selectByVisibleText("EUR");
	    driver.findElement(By.xpath("//option[@value='EUR']")).click();
	    new Select(driver.findElement(By.id("postRate"))).selectByVisibleText("EUR");
	    driver.findElement(By.xpath("(//option[@value='EUR'])[2]")).click();
	    driver.findElement(By.id("amount")).click();
	    driver.findElement(By.id("amount")).clear();
	    driver.findElement(By.id("amount")).sendKeys("1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.id("resultAmount")).click();
	    assertEquals("1.0", driver.findElement(By.id("resultAmount")).getAttribute("value"));
	}

	@Test
	public void testNegativeExchangeRates() throws Exception {
		driver.get("http://localhost:" + port + "/");
	    driver.findElement(By.id("login-button")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("client1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("client1");
	    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
	    driver.findElement(By.id("dropdown-clients-bank-accounts")).click();
	    driver.findElement(By.id("exchanges")).click();
	    new Select(driver.findElement(By.id("initRate"))).selectByVisibleText("EUR");
	    driver.findElement(By.xpath("//option[@value='EUR']")).click();
	    new Select(driver.findElement(By.id("postRate"))).selectByVisibleText("EUR");
	    driver.findElement(By.xpath("(//option[@value='EUR'])[2]")).click();
	    driver.findElement(By.id("amount")).click();
	    driver.findElement(By.id("amount")).clear();
	    driver.findElement(By.id("amount")).sendKeys("-");
	    driver.findElement(By.id("amount")).sendKeys("1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("Can not be null or negative", driver.findElement(By.xpath("//form[@id='add-client-form']/div/div[3]/div/span[2]")).getText());
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
