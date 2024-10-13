@All
@KafkaService
Feature: Add tests for kafka services with Cucumber layer

  Scenario: Produce and consume a kafka message
    When I produce a message on kafka
    Then I'm able to consume it