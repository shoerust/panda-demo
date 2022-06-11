package com.panda.pandademo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "william", groupId = "foo")
    public void listenGroupFoo(String message) {
        System.out.println("Received message in group foo: " + message);
    }
}
