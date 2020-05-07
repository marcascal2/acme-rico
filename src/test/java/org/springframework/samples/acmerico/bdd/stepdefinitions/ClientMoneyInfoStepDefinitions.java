package org.springframework.samples.acmerico.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ClientMoneyInfoStepDefinitions extends AbstractStep {

	@LocalServerPort
    private int port;
	
	 @Given("I am not logged in the system as client in the system")
	    public void IamNotLoggedAsClient() throws Exception {
	        getDriver().get("http://localhost:" + port + "/login");
	    }
	    
	    @When("I tray to login as a client and look money statistics as client")
	    public void ItryToLoginAsClient() throws Exception {
	        getDriver().findElement(By.id("username")).click();
	        getDriver().findElement(By.id("username")).clear();
	        getDriver().findElement(By.id("username")).sendKeys("client1");
	  	    getDriver().findElement(By.id("password")).click();
	  	    getDriver().findElement(By.id("password")).clear();
	   	    getDriver().findElement(By.id("password")).sendKeys("client1");
	  	    getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
	  	    getDriver().findElement(By.id("dashboards")).click();
	    }

	    @Then("The statistics is shown as the current client")
	    public void IsLoggedIn() throws Exception {
	    	assertEquals(true, isElementPresent(By.xpath("//canvas")));
	    	assertEquals(true, isElementPresent(By.xpath("//div[3]/canvas")));
	    	assertEquals(true, isElementPresent(By.xpath("//div[2]/canvas")));
	        stopDriver();
	    }
	    
	    @Given("I am not logged in the system as worker in the system")
	    public void IamNotLoggedAsWorkerOrDirector() throws Exception {
	        getDriver().get("http://localhost:" + port + "/login");
	    }
	    
	    @When("I tray to login as a worker and look money statistics as worker")
	    public void ItryToLoginAsWorkerOrDirector() throws Exception {
	        getDriver().findElement(By.id("username")).click();
	        getDriver().findElement(By.id("username")).clear();
	        getDriver().findElement(By.id("username")).sendKeys("worker1");
	  	    getDriver().findElement(By.id("password")).click();
	  	    getDriver().findElement(By.id("password")).clear();
	   	    getDriver().findElement(By.id("password")).sendKeys("worker1");
	  	    getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
	    }
	    
	    @Then("The button for the statistics no exist")
	    public void NoHasButton() throws Exception {
	    	assertEquals(false, isElementPresent(By.id("dashboards")));
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
