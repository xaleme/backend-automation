import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.xale.service.KafkaConsumerServ;

import java.util.Map;

public class TestConsumer {

    private final KafkaConsumerServ consumerServ = new KafkaConsumerServ();
    private Map<String, String> actualKafkaMsg;


    @AfterEach
    public void cleanUp() {
        consumerServ.closeConsumer();
    }

    @Test
    void testConsumer() {
        consumerServ.subscribe();
        actualKafkaMsg = consumerServ.consumeMessages();

        Assertions.assertFalse(actualKafkaMsg.entrySet().isEmpty());
    }

}
