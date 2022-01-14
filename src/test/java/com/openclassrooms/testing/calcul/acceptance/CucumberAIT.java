package com.openclassrooms.testing.calcul.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = { "pretty", "html:target/html-cucumber-report" })
public class CucumberAIT {


    /**
     * Les annotations @Given, @When et @Then viennent de Cucumber.
     * Le contenu textuel des annotations doit alors correspondre au contenu textuel des fichiers
     * de fonctionnalités ! Et là où c'est intéressant,
     * c'est que vous pouvez paramétrer les contenus textuels dans vos méthodes !
     *
     * La correspondance avec @Given("un élève utilise le Calculateur") est dans la classe CalculatorSteps
     *
     *
     */
    @Given("un élève utilise le Calculateur")
    public void a_student_is_using_the_Calculator() {

    }

    @When("{int} et {int} sont additionnés")
    public void and_are_added(Integer leftArgument, Integer rightArgument) {

    }

    @Then("on montre {int} à l'élève")
    public void the_student_is_shown(Integer expectedResult) {

    }
}
