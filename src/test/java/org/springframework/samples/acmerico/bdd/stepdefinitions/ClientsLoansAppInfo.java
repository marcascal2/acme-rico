package org.springframework.samples.acmerico.bdd.stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ClientsLoansAppInfo extends AbstractStep {

    @LocalServerPort
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
        getDriver().findElement(By.linkText("My Applications")).click();
        getDriver().findElement(By.id("dropdown-clients-apps")).click();
        getDriver().findElement(By.id("loanapps")).click();
        getDriver().findElement(By.linkText("Préstamo de estudios")).click();

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

    

    @When("I tray to login as a client and look my loan applications information but but I do not have")
    public void ItryToLoginAsAClient2() throws Exception {
        getDriver().findElement(By.id("username")).click();
        getDriver().findElement(By.id("username")).clear();
        getDriver().findElement(By.id("username")).sendKeys("client2");
  	    getDriver().findElement(By.id("password")).click();
  	    getDriver().findElement(By.id("password")).clear();
   	    getDriver().findElement(By.id("password")).sendKeys("client2");
        getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
        getDriver().findElement(By.linkText("My Applications")).click();
        getDriver().findElement(By.id("dropdown-clients-apps")).click();
        getDriver().findElement(By.id("loanapps")).click();
       

    }

    @Then("no information about my loan applications is displayed")
    public void showEmptyLoanInformation() throws Exception {

    }

  private boolean isElementPresent(By by) {
    try {
      getDriver().findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      getDriver().switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

}