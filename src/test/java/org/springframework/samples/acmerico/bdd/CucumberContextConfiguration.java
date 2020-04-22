package org.springframework.samples.acmerico.bdd;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.acmerico.bdd.stepdefinitions.AbstractStep;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class CucumberContextConfiguration extends AbstractStep {

}
