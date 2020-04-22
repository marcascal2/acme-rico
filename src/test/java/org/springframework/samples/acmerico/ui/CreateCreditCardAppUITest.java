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
public class CreateCreditCardAppUITest {
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
	public void testCreateCreditCardAppUI() throws Exception {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.id("login-button")).click();
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
		driver.findElement(By.id("request-cc")).click();
		driver.findElement(By.id("dropdown-clients-apps")).click();
		driver.findElement(By.id("credit-card-apps")).click();
		assertEquals("PENDING", driver.findElement(By.xpath("//table[@id='cardAppsTable']/tbody/tr[3]/td[1]")).getText());
		driver.quit();
	}

	@Test
	public void testCreditCardAppUINegative() throws Exception {
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
		driver.findElement(By.id("request-cc")).click();
		driver.findElement(By.id("dropdown-clients")).click();
		driver.findElement(By.id("my-accounts")).click();
		driver.findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
		driver.findElement(By.id("request-cc")).click();
		driver.findElement(By.id("dropdown-clients")).click();
		driver.findElement(By.id("my-accounts")).click();
		driver.findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
		driver.findElement(By.id("request-cc")).click();
		assertEquals("Your have to wait until we accept your pending credit card applications.",
				driver.findElement(By.xpath("//h3")).getText());
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