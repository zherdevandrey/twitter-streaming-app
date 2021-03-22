package com.example.twitter.to.kafka.service.init.impl;

import com.example.kafka.admin.client.KafkaAdminClient;
import com.example.twitter.to.kafka.service.init.StreamInitializer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StreamInitializerImpl implements StreamInitializer {

    private KafkaAdminClient kafkaAdminClient;

    @Override
    public void init() {
        kafkaAdminClient.createTopic();
        kafkaAdminClient.checkSchemaRegistry();
    }

}
