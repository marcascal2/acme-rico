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
public class AcceptAndRejectTransferAppUITest {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
		// System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testAcceptAndRejectUI() throws Exception {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.id("login-button")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("worker1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("worker1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("clients-requests")).click();
		driver.findElement(By.id("dropdown-workers")).click();
		driver.findElement(By.id("transfer-apps")).click();
		assertEquals("PENDING",
				driver.findElement(By.xpath("//table[@id='transfersAppTable']/tbody/tr[4]/td[3]")).getText());
		driver.findElement(By.linkText("4")).click();
		assertEquals("Accept Transfer", driver.findElement(By.xpath("//form/button")).getText());
		assertEquals("Refuse Transfer", driver.findElement(By.xpath("//form[2]/button")).getText());
		driver.quit();
	}

	@Test
	public void testTransferAppCreation() throws Exception {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.id("login-button")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("client1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("client1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		driver.findElement(By.linkText("Bank Accounts")).click();
		driver.findElement(By.id("dropdown-clients")).click();
		driver.findElement(By.id("my-accounts")).click();
		driver.findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.id("amount")).clear();
		driver.findElement(By.id("amount")).sendKeys("200");
		driver.findElement(By.id("account_number_destination")).clear();
		driver.findElement(By.id("account_number_destination")).sendKeys("ES28 1236 2352 0258 0214");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		driver.findElement(By.linkText("Logout")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.id("login-button")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("worker1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("worker1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		driver.findElement(By.linkText("Clients requests")).click();
		driver.findElement(By.id("dropdown-workers")).click();
		driver.findElement(By.id("transfer-apps")).click();
		driver.findElement(By.linkText("20")).click();
		assertEquals("ES28 1236 2352 0258 0214", driver.findElement(By.xpath("//tr[4]/td")).getText());
		driver.quit();
	}

	@Test
	public void testTransferAppRejected() throws Exception {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.id("login-button")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("client1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("client1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		driver.findElement(By.linkText("Bank Accounts")).click();
		driver.findElement(By.id("dropdown-clients")).click();
		driver.findElement(By.id("my-accounts")).click();
		driver.findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.id("amount")).clear();
		driver.findElement(By.id("amount")).sendKeys("200");
		driver.findElement(By.id("account_number_destination")).clear();
		driver.findElement(By.id("account_number_destination")).sendKeys("ES28 1236 2352 0258 0214");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		driver.findElement(By.linkText("Logout")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.id("login-button")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("worker1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("worker1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("clients-requests")).click();
		driver.findElement(By.id("dropdown-workers")).click();
		driver.findElement(By.id("transfer-apps")).click();
		driver.findElement(By.linkText("20")).click();
		driver.findElement(By.xpath("//form[2]/button")).click();
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		driver.quit();
	}
	
	@Test
	public void testTransferAppAsInstant() throws Exception {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.id("login-button")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("client1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("client1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		driver.findElement(By.linkText("Bank Accounts")).click();
		driver.findElement(By.id("dropdown-clients")).click();
		driver.findElement(By.id("my-accounts")).click();
		driver.findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.id("amount")).clear();
		driver.findElement(By.id("amount")).sendKeys("50");
		driver.findElement(By.id("account_number_destination")).clear();
		driver.findElement(By.id("account_number_destination")).sendKeys("ES29 1258 1010 1064 2579");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("2050.0", driver.findElement(By.xpath("//table[@id='accountsTable']/tbody/tr[3]/td[2]")).getText());
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
