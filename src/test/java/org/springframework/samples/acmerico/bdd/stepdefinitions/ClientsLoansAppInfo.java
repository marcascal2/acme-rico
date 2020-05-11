package org.springframework.samples.acmerico.bdd.stepdefinitions;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.boot.web.server.LocalServerPort;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ClientsLoansAppInfo extends AbstractStep {

    @LocalServerPort  //HU 20
    private int port;

    @Given("I am not logged in the system as a client")
    public void IamNotLoggedAsAClient() throws Exception {
        getDriver().get("http://localhost:" + port + "/login");
    }

    @When("I tray to login as a client and look my loan applications information if I have")
    public void ItryToLoginAsAClient() throws Exception {
        getDriver().findElement(By.id("username")).click();
        getDriver().findElement(By.id("username")).clear();
        getDriver().findElement(By.id("username")).sendKeys("client1");
  	    getDriver().findElement(By.id("password")).click();
  	    getDriver().findElement(By.id("password")).clear();
   	    getDriver().findElement(By.id("password")).sendKeys("client1");
        getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
        getDriver().findElement(By.id("dropdown-clients-apps")).click();
        getDriver().findElement(By.id("loan-apps")).click();
        getDriver().findElement(By.linkText("Student loan")).click();
    }

    @Then("The information about loans is shown as the current user")
    public void showLoanInformation() throws Exception {
        assertEquals("Loan Application Information", getDriver().findElement(By.xpath("//h2")).getText());
        assertEquals("Amount paid", getDriver().findElement(By.xpath("//tr[5]/th")).getText());
        assertEquals("Loan description", getDriver().findElement(By.xpath("//th")).getText());
        assertEquals("Status", getDriver().findElement(By.xpath("//tr[4]/th")).getText());
        assertEquals("Client", getDriver().findElement(By.xpath("//tr[7]/th")).getText());
        stopDriver();
    }

    @Given("A user logged in as a client")
    public void IamNotLoggedAsClient() throws Exception {
        getDriver().get("http://localhost:" + port + "/login");
        getDriver().findElement(By.id("username")).click();
        getDriver().findElement(By.id("username")).clear();
        getDriver().findElement(By.id("username")).sendKeys("client1");
  	    getDriver().findElement(By.id("password")).click();
  	    getDriver().findElement(By.id("password")).clear();
   	    getDriver().findElement(By.id("password")).sendKeys("client1");
        getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
    }
    
    @When("He does not meet the necessary conditions to contract a loan")
    public void ItryToLoginAsClient() throws Exception {
        getDriver().findElement(By.id("dropdown-clients-bank-accounts")).click();
		getDriver().findElement(By.id("my-accounts")).click();
        getDriver().findElement(By.linkText("ES23 0025 0148 1259 1424")).click();
        getDriver().findElement(By.id("apply-loan")).click();
    }
    
    @Then("He will not be able to obtain information about him")
    public void showLoanInformationUnsuccess() throws Exception {
    	assertEquals(false, isElementPresent(By.xpath("//table[@id='loans']/tbody/tr[4]/td[1]")));
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