package ro.xale.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class KafkaConsumerServ {

    private final KafkaConsumer<String, String> consumer;

    public KafkaConsumerServ() {
        this.consumer = setConsumerConfig();
    }

    public Map<String, String> consumeMessages() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                return processRecord(record);
            }
        }
    }

    public void closeConsumer() {
        Thread haltedHook = new Thread(consumer::close);
        Runtime.getRuntime().addShutdownHook(haltedHook);
    }

    public void subscribe() {
        consumer.subscribe(Collections.singletonList("automation.test.topic"));
    }

    private KafkaConsumer<String, String> setConsumerConfig() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "automation-consumer");

        return new KafkaConsumer<>(properties);
    }

    private static Map<String, String> processRecord(ConsumerRecord record) {
        Map<String, String> kafkaObjectMap = new HashMap<>();
        kafkaObjectMap.put("kafkaMessage", String.valueOf(record.value()));

        for (Header header : record.headers()) {
            kafkaObjectMap.put(header.key(), Arrays.toString(header.value()));
        }
        log.info("Received msg with key: " + record.key() + " and value: " + record.value());

        return kafkaObjectMap;
    }
}
