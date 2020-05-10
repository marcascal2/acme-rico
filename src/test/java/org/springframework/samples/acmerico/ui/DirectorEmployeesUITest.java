package org.springframework.samples.acmerico.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

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
public class DirectorEmployeesUITest {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeEach
	public void setUp() throws Exception {
//		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	  @Test
	  public void testDirectorHasAccessToEmployeeList() throws Exception {
	    driver.get("http://localhost:" + port + "/");
	    driver.findElement(By.id("login-button")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("director1");
	    driver.findElement(By.id("username")).sendKeys(Keys.ENTER);
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("director1");
	    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
	    driver.findElement(By.id("dropdown-director-users")).click();
	    driver.findElement(By.partialLinkText("Find employees")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("Eduardo Garcia Prado", driver.findElement(By.id("employeeUrl")).getText());
	    driver.quit();
	  }
	  
	  @Test
	  public void testClientHasNotAccessToEmployeeList() throws Exception {
	    driver.get("http://localhost:8080/");
	    driver.findElement(By.id("login-button")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("client1");
	    driver.findElement(By.id("username")).sendKeys(Keys.ENTER);
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("client1");
	    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		assertEquals(false, isElementPresent(By.partialLinkText("Manage users")));
	    driver.quit();
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
