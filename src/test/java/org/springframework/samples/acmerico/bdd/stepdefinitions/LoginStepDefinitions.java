package org.springframework.samples.acmerico.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginStepDefinitions extends AbstractStep {

    @LocalServerPort
    private int port;

    @Given("I am not logged in the system")
    public void IamNotLogged() throws Exception {
        getDriver().get("http://localhost:" + port + "/login");
    }

    @When("I tray to login as a client")
    public void ItryToLogin() throws Exception {
        getDriver().findElement(By.id("username")).clear();
        getDriver().findElement(By.id("username")).sendKeys("client1");
        getDriver().findElement(By.id("password")).click();
        getDriver().findElement(By.id("password")).clear();
        getDriver().findElement(By.id("password")).sendKeys("client1");
        getDriver().findElement(By.id("password")).sendKeys(Keys.ENTER);
    }

    @Then("Client name is shown as the current user")
    public void IsLoggedIn() throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[@id='username']")));
        assertEquals("CLIENT1", getDriver().findElement(By.xpath("//strong[@id='username']")).getText());
        stopDriver();
    }

}