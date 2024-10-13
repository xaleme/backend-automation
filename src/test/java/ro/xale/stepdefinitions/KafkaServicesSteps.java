package ro.xale.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import ro.xale.service.KafkaConsumerServ;
import ro.xale.service.KafkaProducerServ;

import java.util.Map;

public class KafkaServicesSteps {

    private static final KafkaConsumerServ consumerServ = new KafkaConsumerServ();
    private final KafkaProducerServ producerServ = new KafkaProducerServ();

    @Before("@KafkaService")
    public void beforeScenario() {
        consumerServ.subscribe();
    }

    @After("@KafkaService")
    public void afterScenario() {
        consumerServ.closeConsumer();
    }

    @When("I produce a message on kafka")
    public void iProduceAMessageOnKafka() {
        producerServ.produceMessage("New cucumber message");
    }

    @Then("I'm able to consume it")
    public void iMAbleToConsumeIt() {
        Map<String, String> actualKafkaMsg = consumerServ.consumeMessages();

        Assertions.assertTrue(actualKafkaMsg.get("kafkaMessage").contains("New cucumber message"));
    }
}
