package kr.npost.cms.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import kr.npost.cms.configure.KafkaConsumerConfig;
import kr.npost.cms.configure.KafkaProducerConfig;
//import kr.npost.cms.services.KafkaConsumer;
import kr.npost.cms.services.KafkaProducer;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(
    {KafkaConsumerConfig.class,
    KafkaProducerConfig.class,
    //KafkaConsumer.class,
    KafkaProducer.class}
    )
public @interface EnableKafka {
}
