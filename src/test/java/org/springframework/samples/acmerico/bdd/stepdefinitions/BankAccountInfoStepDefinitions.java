package org.springframework.samples.acmerico.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.*;
import org.springframework.boot.web.server.LocalServerPort;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class BankAccountInfoStepDefinitions extends AbstractStep {

    @LocalServerPort
    private int port;

    @Given("I am not logged in the system how client")
    public void IamNotLoggedHowClient() throws Exception {
        getDriver().get("http://localhost:" + port + "/login");
    }
    
    @When("I tray to login as a client and look my bank account information")
    public void ItryToLoginhowClient() throws Exception {
        getDriver().findElement(By.id("username")).click();
        getDriver().findElement(By.id("username")).clear();
        getDriver().findElement(By.id("username")).sendKeys("client1");
  	    getDriver().findElement(By.id("password")).click();
  	    getDriver().findElement(By.id("password")).clear();
   	    getDriver().findElement(By.id("password")).sendKeys("client1");
  	    getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
  	    getDriver().findElement(By.linkText("Bank Accounts")).click();
  	    getDriver().findElement(By.id("dropdown-clients")).click();
  	    getDriver().findElement(By.id("my-accounts")).click();
  	    getDriver().findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
    }

    @Then("The information is shown as the current user")
    public void IsLoggedIn() throws Exception {
        assertEquals("ES23 0025 0148 1259 1424", getDriver().findElement(By.id("accountNumber")).getAttribute("value"));
  	    assertEquals("2567.34", getDriver().findElement(By.id("amount")).getAttribute("value"));
   	    assertEquals("Viajes", getDriver().findElement(By.id("alias")).getAttribute("value"));
        stopDriver();
    }
    
    @Given("I am not logged in the system how worker or director")
    public void IamNotLoggedHowWorkerOrDirector() throws Exception {
        getDriver().get("http://localhost:" + port + "/login");
    }
    
    @When("I tray to login as a worker or director and look my bank account information")
    public void ItryToLoginHowWorkerOrDirector() throws Exception {
        getDriver().findElement(By.id("username")).click();
        getDriver().findElement(By.id("username")).clear();
        getDriver().findElement(By.id("username")).sendKeys("worker1");
  	    getDriver().findElement(By.id("password")).click();
  	    getDriver().findElement(By.id("password")).clear();
   	    getDriver().findElement(By.id("password")).sendKeys("worker1");
  	    getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
    }
    
    @Then("The button ins't shown in the menu bar")
    public void NoHasButton() throws Exception {
    	assertEquals(false, isElementPresent(By.linkText("Bank Accounts")));
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
