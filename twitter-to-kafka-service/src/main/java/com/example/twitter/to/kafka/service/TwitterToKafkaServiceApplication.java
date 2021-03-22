package com.example.twitter.to.kafka.service;

import com.example.twitter.to.kafka.service.init.StreamInitializer;
import com.example.twitter.to.kafka.service.runner.StreamRunner;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

@Slf4j
@SpringBootApplication
@AllArgsConstructor
@ComponentScan(basePackages = {"com.example.*"})
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private StreamRunner streamRunner;
    private RetryTemplate retryTemplate;
    private StreamInitializer streamInitializer;

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @SneakyThrows
    @Override
    public void run(String... args) throws Exception {
        streamInitializer.init();
        streamRunner.start();
    }


}
