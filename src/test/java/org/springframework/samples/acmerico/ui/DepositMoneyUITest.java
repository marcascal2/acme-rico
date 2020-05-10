package org.springframework.samples.acmerico.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepositMoneyUITest {
	
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
	public void testDepositMoney() throws Exception {
		driver.get("http://localhost:" + port + "/");
	    driver.findElement(By.id("login-button")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("client1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("client1");
	    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
	    driver.findElement(By.id("dropdown-clients-bank-accounts")).click();
	    driver.findElement(By.id("my-accounts")).click();
	    driver.findElement(By.linkText("ES29 1258 1010 1064 2579")).click();
	    driver.findElement(By.id("deposit-money")).click();
	    driver.findElement(By.id("amount")).click();
	    driver.findElement(By.id("amount")).clear();
	    driver.findElement(By.id("amount")).sendKeys("20");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("ES29 1258 1010 1064 2579")).click();
	    driver.findElement(By.id("amount")).click();
	    assertEquals("2020.0", driver.findElement(By.id("amount")).getAttribute("value"));
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
