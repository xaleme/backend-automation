package ro.xale.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaZKBroker;

@Configuration
public class KafkaEmbeddedServer {


    @Bean
    public EmbeddedKafkaBroker initBroker() {
        EmbeddedKafkaBroker broker = new EmbeddedKafkaZKBroker(1, true, "default.topic");
        broker.kafkaPorts(9092);

        return broker;
    }
}
