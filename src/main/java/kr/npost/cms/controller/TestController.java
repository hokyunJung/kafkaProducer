package kr.npost.cms.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import kr.npost.cms.services.KafkaConsumer;
import kr.npost.cms.services.KafkaProducer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

  @Autowired
  private KafkaProducer kafkaProducer;

  @Autowired
  private KafkaConsumer kafkaConsumer;

  @GetMapping(value = "/alive")
  public String alive() throws Exception {

    JsonObject json = new JsonObject();
    json.addProperty("sendEaddr", "ABC");
    json.addProperty("sendDate", "2021-03-11");

    String message = json.toString();
    String reqUUID = kafkaProducer.send("askToAds", message);

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.SECOND, 1);
    boolean isDone = false;
    while(!isDone && new Date().before(cal.getTime())) {
      isDone = kafkaConsumer.getRes().containsKey(reqUUID);
    }

    if (isDone) {
      log.info("del!!");
      kafkaConsumer.getRes().remove(reqUUID);
    }

    

    return "is alive : " + UUID.randomUUID().toString() + " size : " + kafkaConsumer.getRes().size();
  }

}
