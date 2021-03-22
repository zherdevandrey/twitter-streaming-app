package com.example.kafkaproducer.impl;


import com.example.kafkaproducer.KafkaProducer;
import example.kafka.model.TwitterAvroModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@AllArgsConstructor
public class TwitterKafkaProducer implements KafkaProducer<Long, TwitterAvroModel> {

    private KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;

    @Override
    public void send(String topicName, Long key, TwitterAvroModel message) {
        ListenableFuture<SendResult<Long, TwitterAvroModel>> kafkaResultFuture
                = kafkaTemplate.send(topicName, key, message);

        kafkaResultFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error while sending message {} to topic {}", message.toString(), topicName, throwable);
            }

            @Override
            public void onSuccess(SendResult<Long, TwitterAvroModel> result) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info("Message successfully sent. Topic {}, Partition {}, Offset {}",
                        recordMetadata.topic(),
                        recordMetadata.partition(),
                        recordMetadata.offset()
                );
            }
        });
    }

}
