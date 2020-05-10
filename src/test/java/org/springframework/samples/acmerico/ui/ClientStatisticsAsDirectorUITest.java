package org.springframework.samples.acmerico.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientStatisticsAsDirectorUITest {

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
	public void testAccessClientStatistics() throws Exception {
		driver.get("http://localhost:" + port + "/");
	    driver.findElement(By.id("login-button")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("director1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("director1");
	    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
	    driver.findElement(By.id("dropdown-director-users")).click();
	    driver.findElement(By.partialLinkText("Find clients")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("George Franklin")).click();
	    driver.findElement(By.linkText("Client statics")).click();
	    driver.findElement(By.xpath("//body/div")).click();
	    assertEquals("Number of Bank Accounts", driver.findElement(By.xpath("//th")).getText());
	    driver.findElement(By.xpath("//tr")).click();
	    assertEquals("3", driver.findElement(By.xpath("//td")).getText());
	    driver.findElement(By.xpath("//tr[3]/th")).click();
	    assertEquals("Total Amount in Bank", driver.findElement(By.xpath("//tr[3]/th")).getText());
	    driver.findElement(By.xpath("//tr[3]")).click();
	    assertEquals("14567", driver.findElement(By.xpath("//tr[3]/td")).getText());
	    driver.quit();
	}
	
	@Test
	public void testAccessClientStatisticsAsWorker() throws Exception {
		driver.get("http://localhost:" + port + "/");
	    driver.findElement(By.id("login-button")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("worker1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("worker1");
	    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
	    driver.findElement(By.id("clients-worker")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("George Franklin")).click();
		assertEquals(false, isElementPresent(By.linkText("Client statics")));
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
	
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
