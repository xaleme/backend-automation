import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.KafkaConsumerServ;
import service.KafkaProducerServ;

import java.util.Map;

public class TestKafka {

    private static final KafkaConsumerServ consumerServ = new KafkaConsumerServ();
    private static final KafkaProducerServ producerServ = new KafkaProducerServ();

    @BeforeAll
    public static void beforeAll() {
        consumerServ.subscribe();
        producerServ.produceMessage("New message");
    }

    @AfterEach
    public void cleanUp() {
        consumerServ.closeConsumer();
    }

    @Test
    void testConsumer() {
        Map<String, String> actualKafkaMsg = consumerServ.consumeMessages();

        Assertions.assertTrue(actualKafkaMsg.get("kafkaMessage").contains("New message"));
    }

}
