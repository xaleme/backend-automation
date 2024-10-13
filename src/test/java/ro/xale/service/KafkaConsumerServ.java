package ro.xale.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class KafkaConsumerServ {

    private final KafkaConsumer<byte[], byte[]> consumer;

    public KafkaConsumerServ() {
        this.consumer = setConsumerConfig();
    }

    public Map<String, String> consumeMessages() {
        long stopTime = System.currentTimeMillis() + 5000;

        while (System.currentTimeMillis() < stopTime) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<byte[], byte[]> consumerRecord : records) {
                return processRecord(consumerRecord);
            }
        }
        return Collections.emptyMap();
    }

    public void closeConsumer() {
        Thread haltedHook = new Thread(consumer::close);
        Runtime.getRuntime().addShutdownHook(haltedHook);
    }

    public void subscribe() {
        consumer.subscribe(Collections.singletonList("default.topic"));
    }

    private KafkaConsumer<byte[], byte[]> setConsumerConfig() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "automation-consumer");

        return new KafkaConsumer<>(properties);
    }

    private static Map<String, String> processRecord(ConsumerRecord<byte[], byte[]> consumerRecord) {
        Map<String, String> kafkaObjectMap = new HashMap<>();
        kafkaObjectMap.put("kafkaMessage", new String(consumerRecord.value()));

        for (Header header : consumerRecord.headers()) {
            kafkaObjectMap.put(header.key(), new String(header.value()));
        }
        log.info("Received msg with key: {} and value: {}", consumerRecord.key(), new String(consumerRecord.value()));

        return kafkaObjectMap;
    }
}
