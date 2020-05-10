package org.springframework.samples.acmerico.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TransfersStepDefinitions extends AbstractStep {

	@LocalServerPort  //HU 15
	private int port;

	@Given("I am a user logged in as a client")
	public void IamNotLoggedHowClient() throws Exception {
		getDriver().get("http://localhost:" + port + "/login");
	}

	@When("I need to make a transfer")
	public void ItryToSendTransferhowClient() throws Exception {
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys("client1");
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys("client1");
  	    getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
		getDriver().findElement(By.id("dropdown-clients-bank-accounts")).click();
		getDriver().findElement(By.id("my-accounts")).click();
		getDriver().findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
		getDriver().findElement(By.id("create-transfer")).click();
		getDriver().findElement(By.id("amount")).clear();
		getDriver().findElement(By.id("amount")).sendKeys("200");
		getDriver().findElement(By.id("account_number_destination")).clear();
		getDriver().findElement(By.id("account_number_destination")).sendKeys("ES28 1236 2352 0258 0214");
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		getDriver().findElement(By.id("username")).click();
		getDriver().findElement(By.linkText("Logout")).click();
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		getDriver().findElement(By.id("login-button")).click();
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys("worker1");
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys("worker1");
  	    getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
		getDriver().findElement(By.id("dropdown-workers-clients-request")).click();
		getDriver().findElement(By.id("transfer-apps")).click();
		getDriver().findElement(By.linkText("20")).click();
		getDriver().findElement(By.id("accept-transfer-button")).click();
		getDriver().findElement(By.id("username")).click();
		getDriver().findElement(By.linkText("Logout")).click();
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		getDriver().findElement(By.id("login-button")).click();
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys("client1");
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys("client1");
  	    getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
		getDriver().findElement(By.id("dropdown-clients-bank-accounts")).click();
		getDriver().findElement(By.id("my-accounts")).click();
	}

	@Then("the system sends it to its destination online once the employee has accepted it")
	public void IsSent() throws Exception {
		assertEquals("10200.0",
				getDriver().findElement(By.xpath("//table[@id='accountsTable']/tbody/tr[2]/td[2]")).getText());
        stopDriver();
	}

}
