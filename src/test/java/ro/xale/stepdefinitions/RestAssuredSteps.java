package ro.xale.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RestAssuredSteps {

    @When("I send a GET request to nationalize.io api with name {string}")
    public void iSendAGETRequestToNationalizeIoApiWithName(String name) {

    }

    @Then("The response contains a list of countries")
    public void theResponseContainsAListOfCountries() {

    }

    @And("{string} country code is the most probable")
    public void countryCodeIsTheMostProbable(String countryCode) {

    }
}
