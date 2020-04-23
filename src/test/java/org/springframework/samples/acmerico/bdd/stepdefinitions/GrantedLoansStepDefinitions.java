package org.springframework.samples.acmerico.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.springframework.boot.web.server.LocalServerPort;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GrantedLoansStepDefinitions extends AbstractStep {
	
	@LocalServerPort
    private int port;
	
	 @Given("I am not logged in the system as director")
	 public void IamNotLogged() throws Exception {
		 getDriver().get("http://localhost:" + port + "/login");
	 }
	 
	 @When("I tray to login as a director and see the granted loans")
	 public void ItryToLogin() throws Exception {
		 getDriver().findElement(By.id("username")).click();
		 getDriver().findElement(By.id("username")).clear();
		 getDriver().findElement(By.id("username")).sendKeys("director1");
		 getDriver().findElement(By.id("password")).click();
		 getDriver().findElement(By.id("password")).clear();
		 getDriver().findElement(By.id("password")).sendKeys("director1");
		 getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
		 getDriver().findElement(By.linkText("Loans")).click();
		 getDriver().findElement(By.id("loans-dropdown")).click();
		 getDriver().findElement(By.id("see-loans")).click();
	 }
	 
	 @Then("The list of granted loans is shown")
	 public void IsLoggedIn() throws Exception {
		 assertEquals("Préstamo de estudios", getDriver().findElement(By.linkText("Préstamo de estudios")).getText());
		 assertEquals("Préstamo hipotecario", getDriver().findElement(By.linkText("Préstamo hipotecario")).getText());
	 }

}
