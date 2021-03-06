package org.springframework.samples.acmerico.ui;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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
public class AddRemoveWorkerUITest {
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

	@Test // HU 5
	public void testAddWorkerSuccess() throws Exception {
		driver.get("http://localhost:" + port + "/login");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("director1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("director1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		Assert.assertEquals("DIRECTOR1", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		driver.get("http://localhost:" + port + "/employees/new");
		driver.findElement(By.id("firstName")).click();
		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys("worker7");
		driver.findElement(By.id("lastName")).click();
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys("worker7");
		driver.findElement(By.id("salary")).click();
		driver.findElement(By.id("salary")).clear();
		driver.findElement(By.id("salary")).sendKeys("1000");
		driver.findElement(By.id("user.username")).click();
		driver.findElement(By.id("user.username")).clear();
		driver.findElement(By.id("user.username")).sendKeys("worker7");
		driver.findElement(By.id("user.password")).click();
		driver.findElement(By.id("user.password")).clear();
		driver.findElement(By.id("user.password")).sendKeys("worker7");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("worker7 worker7", driver.findElement(By.xpath("//b")).getText());
		driver.quit();
	}

	@Test
	public void testAddDeleteWorkerUnsuccessClient() throws Exception {
		driver.get("http://localhost:" + port + "/login");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("client1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("client1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		assertEquals(false, isElementPresent(By.id("navbarDropdown")));
		driver.quit();
	}

	@Test
	public void testAddDeleteWorkerUnsuccessWorker() throws Exception {
		driver.get("http://localhost:" + port + "/login");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("worker1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("worker1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		assertEquals(false, isElementPresent(By.id("navbarDropdown")));
		driver.quit();
	}
	
	@Test
	public void testDeleteWorkerSuccess() throws Exception {
		driver.get("http://localhost:" + port + "/login");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("director1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("director1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		Assert.assertEquals("DIRECTOR1", driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		driver.get("http://localhost:" + port + "/employees/2");
		driver.findElement(By.id("delete-worker")).click();
		Assert.assertNotEquals("/employees/2", driver.findElement(By.id("employeeUrl")));
		driver.quit();
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
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
