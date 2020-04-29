package org.springframework.samples.acmerico.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ClientsDebtsStepDefinitions extends AbstractStep {

	@LocalServerPort
    private int port;
	
	@Given("I am not logged in the system as worker1")
	public void IamNotLoggedAsWorker() throws Exception {
		 getDriver().get("http://localhost:" + port + "/login");
	}
	
	@When("I try to login as a worker1 and can see the button debts")
	public void ItryToLoginAsWorker() throws Exception {
		 getDriver().findElement(By.id("username")).click();
		 getDriver().findElement(By.id("username")).clear();
	     getDriver().findElement(By.id("username")).sendKeys("worker1");
	     getDriver().findElement(By.id("password")).click();
	     getDriver().findElement(By.id("password")).clear();
	     getDriver().findElement(By.id("password")).sendKeys("worker1");
		 getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
		 getDriver().findElement(By.linkText("Debts")).click();
		 getDriver().findElement(By.id("debts")).click();
	}
	
	@Then("The debts can be seen in the table")
	public void IsLoggedInAsWorker() throws Exception {
		assertEquals("User", getDriver().findElement(By.xpath("//table[@id='debtsTable']/thead/tr/th")).getText());
	    assertEquals("Refresh date", getDriver().findElement(By.xpath("//table[@id='debtsTable']/thead/tr/th[2]")).getText());
	    stopDriver();
	}
	
	@Given("I am not logged in the system as client1")
	public void IamNotLoggedAsClient() throws Exception {
		 getDriver().get("http://localhost:" + port + "/login");
	}
	
	@When("I try to login as a client1")
	public void ItryToLoginAsClient() throws Exception {
		 getDriver().findElement(By.id("username")).click();
		 getDriver().findElement(By.id("username")).clear();
	     getDriver().findElement(By.id("username")).sendKeys("client1");
	     getDriver().findElement(By.id("password")).click();
	     getDriver().findElement(By.id("password")).clear();
	     getDriver().findElement(By.id("password")).sendKeys("client1");
		 getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
	}
	
	@Then("The button debts can not be seen")
	public void IsLoggedInAsClient() throws Exception {
		assertEquals(false, isElementPresent(By.linkText("Debts")));
        stopDriver();
	}
	
	private boolean isElementPresent(By by) {
        try {
          getDriver().findElement(by);
          return true;
        } catch (NoSuchElementException e) {
          return false;
        }
    }
}
