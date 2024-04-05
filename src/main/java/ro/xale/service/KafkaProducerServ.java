package ro.xale.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.Properties;


public class KafkaProducerServ {

    Properties properties = new Properties();


    @Test
    public void producerConfig(){

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String,String> producerRecord =
                new ProducerRecord<>("automation.test.topic","Name","Gicu");

        producer.send(producerRecord, (recordMetadata, e) -> {
            if (recordMetadata != null) {
                System.out.println(producerRecord.key());
                System.out.println(producerRecord.value());
                System.out.println(recordMetadata.toString());
            }
        });

    }
}
