package org.springframework.samples.acmerico.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientListUITest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testClientListUISuccess() throws Exception {
		driver.get("http://localhost:"+ port +"/");
		driver.get("http://localhost:"+ port +"/login");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("director1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("director1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("navbarDropdown")).click();
		driver.get("http://localhost:"+ port + "/clients/find");
		driver.get("http://localhost:"+ port + "/clients?lastName=");

		assertEquals("George Franklin", driver.findElement(By.linkText("George Franklin")).getText());
		assertEquals("Betty Davis", driver.findElement(By.linkText("Betty Davis")).getText());
		assertEquals("Eduardo Rodriquez", driver.findElement(By.linkText("Eduardo Rodriquez")).getText());
		assertEquals("Harold Davis", driver.findElement(By.linkText("Harold Davis")).getText());
		assertEquals("Peter McTavish", driver.findElement(By.linkText("Peter McTavish")).getText());
		assertEquals("Javier Ruiz", driver.findElement(By.linkText("Javier Ruiz")).getText());
		assertEquals("Pedro Pérez", driver.findElement(By.linkText("Pedro Pérez")).getText());
		assertEquals("Omar Monteslamaravilla", driver.findElement(By.linkText("Omar Monteslamaravilla")).getText());
		assertEquals("Leiloleilo Davile", driver.findElement(By.linkText("Leiloleilo Davile")).getText());
		assertEquals("Peter Parker", driver.findElement(By.linkText("Peter Parker")).getText());
	}

	@Test
	public void testClientListUIUnsuccess() throws Exception {
		driver.get("http://localhost:"+ port + "/");
		driver.get("http://localhost:"+ port + "/login");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("client1");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("client1");
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		assertEquals(false, isElementPresent(By.linkText("Clients")));;
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