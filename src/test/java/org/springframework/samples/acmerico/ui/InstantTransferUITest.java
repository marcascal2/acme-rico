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
public class InstantTransferUITest {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
//		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testInstantTransferSuccess() throws Exception {
		driver.get("http://localhost:" + port + "/login");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("client1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("client1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("dropdown-clients-bank-accounts")).click();
		driver.findElement(By.id("my-accounts")).click();
		driver.findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//form[@id='transfer_app']/div")).click();
		driver.findElement(By.id("amount")).click();
		driver.findElement(By.id("amount")).clear();
		driver.findElement(By.id("amount")).sendKeys("90");
		driver.findElement(By.id("account_number_destination")).click();
		driver.findElement(By.id("account_number_destination")).clear();
		driver.findElement(By.id("account_number_destination")).sendKeys("ES28 1236 2352 0258 0214");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("2477.34", driver.findElement(By.xpath("//table[@id='accountsTable']/tbody/tr/td[2]")).getText());
		assertEquals("10090.0",
				driver.findElement(By.xpath("//table[@id='accountsTable']/tbody/tr[2]/td[2]")).getText());
		driver.quit();
	}

	@Test
	public void testInstantTransferUnsuccess() throws Exception {
		driver.get("http://localhost:" + port + "/login");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("client1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("client1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("dropdown-clients-bank-accounts")).click();
		driver.findElement(By.id("my-accounts")).click();
		driver.findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//form[@id='transfer_app']/div")).click();
		driver.findElement(By.id("amount")).click();
		driver.findElement(By.id("amount")).clear();
		driver.findElement(By.id("amount")).sendKeys("190");
		driver.findElement(By.id("account_number_destination")).click();
		driver.findElement(By.id("account_number_destination")).clear();
		driver.findElement(By.id("account_number_destination")).sendKeys("ES28 1236 2352 0258 0214");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("2567.34", driver.findElement(By.xpath("//table[@id='accountsTable']/tbody/tr/td[2]")).getText());
		assertEquals("10000.0",
				driver.findElement(By.xpath("//table[@id='accountsTable']/tbody/tr[2]/td[2]")).getText());
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
