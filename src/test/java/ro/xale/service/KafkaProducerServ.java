package ro.xale.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;


import java.util.Arrays;
import java.util.Properties;


public class KafkaProducerServ {

    private final KafkaProducer<byte[], byte[]> producer;
    private String outputTopic = "default.topic";

    public KafkaProducerServ(String outputTopic) {
        this.producer = setProducerConfig();
        this.outputTopic = outputTopic;
    }

    public KafkaProducerServ() {
        this.producer = setProducerConfig();
    }


    public KafkaProducer<byte[], byte[]> setProducerConfig() {
        String hostname = System.getenv().getOrDefault("REMOTE_ADDRESS", "localhost");
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, hostname + ":9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        return new KafkaProducer<>(properties);

    }

    public void produceMessage(String message) {
        ProducerRecord<byte[], byte[]> producerRecord =
                new ProducerRecord<>(outputTopic, message.getBytes());

        producer.send(producerRecord, (recordMetadata, e) -> {
            if (null != recordMetadata) {
                System.out.println(Arrays.toString(producerRecord.key()));
                System.out.println(Arrays.toString(producerRecord.value()));
                System.out.println(recordMetadata);
            }
        });
    }
}
