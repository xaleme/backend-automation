@All
@RestAssured
Feature: Tests using RestAssured library

  Scenario: Send a GET request
    When I send a GET request to nationalize.io api with name "Alexandru"
    Then The response contains a list of countries
    And "RO" country code is the most probable