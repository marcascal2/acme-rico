package org.springframework.samples.acmerico.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AcceptRefuseLoanAppsStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;

	@Given("I am not logged in the system as director1")
	public void IamNotLoggedAsEmployeeAccept() throws Exception {
		getDriver().get("http://localhost:" + port + "/login");
	}

	@When("I try to login as a director1 and accept a loan application")
	public void ItryToLoginEmployeeAccept() throws Exception {
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys("director1");
		getDriver().findElement(By.id("password")).click();
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys("director1");
		getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
		getDriver().findElement(By.linkText("Clients requests")).click();
		getDriver().findElement(By.id("dropdown-director")).click();
		getDriver().findElement(By.id("loan-applications")).click();
		getDriver().findElement(By.xpath("(//a[contains(text(),'Préstamo de estudios')])[3]")).click();
		 assertEquals("Accept Loan Application", getDriver().findElement(By.xpath("//form/button")).getText());
	}

	@Then("The loan application is accepted")
	public void LoanApplicationAccepted() throws Exception {
		getDriver().findElement(By.xpath("//form/button")).click();
	    assertEquals("ACCEPTED", getDriver().findElement(By.xpath("//table[@id='loanApps']/tbody/tr[3]/td[4]")).getText());
		stopDriver();
	}

	@Given("I am not logged in the system as worker")
	public void IamNotLoggedAsEmployeeRefuse() throws Exception {
		getDriver().get("http://localhost:" + port + "/login");
	}

	@When("I try to login as a worker and refuse a loan application")
	public void ItryToLoginEmployeeRefuse() throws Exception {
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys("worker1");
		getDriver().findElement(By.id("password")).click();
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys("worker1");
		getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
		getDriver().findElement(By.linkText("Clients requests")).click();
		getDriver().findElement(By.id("dropdown-workers")).click();
		getDriver().findElement(By.id("loan-applications")).click();
		getDriver().findElement(By.xpath("(//a[contains(text(),'Préstamo de estudios')])[2]")).click();
		assertEquals("Refuse Loan Application", getDriver().findElement(By.xpath("//form[2]/button")).getText());
	}

	@Then("The loan application is refused")
	public void LoanApplicationRefused() throws Exception {
		getDriver().findElement(By.xpath("//form[2]/button")).click();
		assertEquals("REJECTED",
				getDriver().findElement(By.xpath("//table[@id='loanApps']/tbody/tr[2]/td[4]")).getText());
		stopDriver();
	}

	@Given("I am not logged in the system as client")
	public void IamNotLoggedAsClientAccept() throws Exception {
		getDriver().get("http://localhost:" + port + "/login");
	}

	@When("I try to login as a client and accept or refuse a loan application")
	public void ItryToLoginClientAccept() throws Exception {
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys("client3");
		getDriver().findElement(By.id("password")).click();
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys("client3");
		getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
	}

	@Then("The system does not permit accept or refuse a loan application")
	public void CannotAcceptOrRefuse() throws Exception {
		assertEquals(false, isElementPresent(By.linkText("Clients requests")));
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
