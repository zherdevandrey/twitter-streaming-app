package com.example.kafka.to.elastic.service.consumer.impl;

import com.example.app.conf.data.KafkaConfigData;
import com.example.app.conf.data.KafkaConsumerConfigData;
import com.example.elastic.index.client.service.ElasticIndexClient;
import com.example.elastic.index.client.service.TwitterElasticRepositoryIndexClient;
import com.example.elastic.model.impl.TwitterIndexModel;
import com.example.kafka.admin.client.KafkaAdminClient;
import com.example.kafka.to.elastic.service.consumer.KafkaConsumer;
import com.example.kafka.to.elastic.service.transformer.AvroToElasticModelTransformer;
import example.kafka.model.TwitterAvroModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumerImpl implements KafkaConsumer<Long, TwitterAvroModel> {

    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private KafkaAdminClient kafkaAdminClient;
    private KafkaConsumerConfigData kafkaConsumerConfigData;
    private KafkaConfigData kafkaConfigData;
    private ElasticIndexClient twitterElasticClient;
    private AvroToElasticModelTransformer transformer;

    @EventListener
    public void onStartUp(ApplicationStartedEvent event){
        kafkaAdminClient.checkTopicsCreated();
        log.info("Topics with names {} are ready", kafkaConfigData.getTopicNamesToCreate().toArray());
        kafkaListenerEndpointRegistry.getListenerContainer("twitterTopicListener").start();
    }

    @KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
    public void receive(@Payload List<TwitterAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Integer> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        List<TwitterIndexModel> elasticModels = transformer.getElasticModels(messages);
        twitterElasticClient.save(elasticModels);
    }
}
