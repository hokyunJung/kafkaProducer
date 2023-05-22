package kr.npost.cms.services;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String send(String topic, String payload) {

        /*
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, "askToAds")
                .setHeader(KafkaHeaders.MESSAGE_KEY, payload)
                //.setHeader(KafkaHeaders.PARTITION_ID, 0)
                .setHeader("X-Custom-Header", "Sending Custom Header with Spring Kafka")
                .build();
        log.info("sending message='{}' to topic='{}'", payload, topic);
 */
        String reqUUID =  UUID.randomUUID().toString();
        ProducerRecord<String, String> message = new ProducerRecord<>(topic, payload);
        message.headers().add(new RecordHeader("REQ-UUID", reqUUID.getBytes(StandardCharsets.UTF_8)));
        kafkaTemplate.send(message);
        return reqUUID;
        //kafkaTemplate.send(topic, payload);
    }
}