package kr.npost.cms.services;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

@Slf4j
@Component
@RequiredArgsConstructor
@Getter
public class KafkaConsumer {
    private final ObjectMapper objectMapper;
    //private List<RegisteredPostEvent> eventRepo = new ArrayList<>();
    @Autowired
    private KafkaProducer kafkaProducer;

    private Map<String, String> res = ExpiringMap.builder()
    .maxSize(5)
    .expirationPolicy(ExpirationPolicy.CREATED)
    .expiration(30, TimeUnit.SECONDS)
    .build();

    @KafkaListener(topics = "resFromAds", groupId = "resGroup")
    public void batchListener(@Payload String payload, @Header(value = "RES-UUID") String uuid) {
        log.info("received message='{}', RES-UUID='{}'", payload, uuid);
        res.put(uuid, payload);
        //kafkaProducer.send("payload", payload);
    }
    

/*
    @KafkaListener(topics = "testTopic", groupId = "testGroup")
    public void batchListener(ConsumerRecords<String, String> records) {
        records.forEach(record -> log.info("batchListener : " + record.toString()));
    }
    


    
    @KafkaListener(topics = "testTopic", groupId = "testGroup")
    protected void consume(@Payload String payload) throws Exception {
        log.info("recive event : {}", payload);
        //RegisteredPostEvent event = objectMapper.readValue(payload, RegisteredPostEvent.class);
        //eventRepo.add(event);

        // Process
        //acknowledgment.acknowledge();
    }
    */
/*
    @KafkaListener(topics = "testTopic", groupId = "testGroup")
    public void messageListener(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        System.out.println("testEEEEEEEEEEEEEEEEE");
        log.info("### record: " + record.toString());
        log.info("### topic: " + record.topic() + ", value: " + record.value() + ", offset: " + record.offset());

        // kafka 메시지 읽어온 곳까지 commit. (이 부분을 하지 않으면 메시지를 소비했다고 commit 된 것이 아니므로 계속 메시지를 읽어온다)
        //enable.auto.commit=false 이면서 ack-mode: MANUAL_IMMEDIATE 일때 Acknowledgment을 통해 커밋전략을 가진다..
        acknowledgment.acknowledge();
    }
    */
    //public List<RegisteredPostEvent> getEventRepo() {
    //    return eventRepo;
    //}
}