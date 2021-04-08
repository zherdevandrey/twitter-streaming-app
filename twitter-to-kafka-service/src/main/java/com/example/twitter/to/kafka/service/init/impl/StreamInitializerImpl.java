package com.example.twitter.to.kafka.service.init.impl;

import com.example.kafka.admin.client.KafkaAdminClient;
import com.example.twitter.to.kafka.service.init.StreamInitializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class StreamInitializerImpl implements StreamInitializer {

    private KafkaAdminClient kafkaAdminClient;

    @Override
    public void init() {
        log.info("INITIALIZATION STARTED");
        kafkaAdminClient.createTopic();
        kafkaAdminClient.checkSchemaRegistry();
        log.info("INITIALIZATION FINISHED");
    }

}
