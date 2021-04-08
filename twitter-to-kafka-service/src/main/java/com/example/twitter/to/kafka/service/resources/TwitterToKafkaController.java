package com.example.twitter.to.kafka.service.resources;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TwitterToKafkaController {


    @Value("${twitter-to-kafka-service.welcome-message}")
    private String message;

    @GetMapping
    public String message() {
        log.info("TwitterToKafkaController call!!!");
        return message;
    }

}