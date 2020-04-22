package org.springframework.samples.acmerico.ui;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonalInformationClientUITest {
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
	public void testPersonalInformationClientUITest() throws Exception {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/li[1]/a")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("worker1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("worker1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		assertEquals("worker1", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[1]/li[2]/a/span[2]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("George Franklin")).click();
		assertEquals("Client Information", driver.findElement(By.xpath("//h2")).getText());
		assertEquals("110 W. Liberty St.", driver.findElement(By.xpath("//tr[2]/td")).getText());
		assertEquals("George Franklin", driver.findElement(By.xpath("//b")).getText());
		assertEquals("10000.0", driver.findElement(By.xpath("//tr[6]/td")).getText());
		assertEquals("Madison", driver.findElement(By.xpath("//tr[4]/td")).getText());
		driver.findElement(By.xpath("//th")).click();
		assertNotEquals("Client1",
				driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
	}

	@Test
	public void testPersonalInformationClientNegativeCaseUITest() throws Exception {
		driver.get("http://localhost:" + port + "/");
		driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[2]/li[1]/a")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("worker1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("worker1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		assertEquals("worker1", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul[1]/li[2]/a/span[2]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("George Franklin")).click();
		assertEquals("Client Information", driver.findElement(By.xpath("//h2")).getText());
		assertEquals("110 W. Liberty St.", driver.findElement(By.xpath("//tr[2]/td")).getText());
		assertEquals("George Franklin", driver.findElement(By.xpath("//b")).getText());
		assertEquals("10000.0", driver.findElement(By.xpath("//tr[6]/td")).getText());
		assertEquals("Madison", driver.findElement(By.xpath("//tr[4]/td")).getText());
		driver.findElement(By.xpath("//th")).click();
		assertNotEquals("Client2",
				driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
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
