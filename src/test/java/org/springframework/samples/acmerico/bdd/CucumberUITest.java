package org.springframework.samples.acmerico.bdd;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"src/test/java/"},
    plugin = {"pretty", "json:target/cucumber-reports/cucumber-report.json"},
    glue = {"src/test/java/org/springframework/samples/acmerico/bdd/stepdefinitions/LoginStepDefinitions"},
    monochrome=true)
public class CucumberUITest {

}