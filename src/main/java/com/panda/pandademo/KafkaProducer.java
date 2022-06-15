package com.panda.pandademo;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Component
public record KafkaProducer(
        KafkaTemplate<String, String> kafkaTemplate) {

    public void sendMessage(String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("william", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message +
                        "] due to :" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
        });
    }

    @PostConstruct
    public void send() {
        IntStream.range(0, 1000).forEach(id -> sendMessage("oh my god: " + id));
    }

}
