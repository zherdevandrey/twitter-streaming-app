package com.example.twitter.to.kafka.service.listener;

import com.example.app.conf.data.KafkaConfigData;
import com.example.kafkaproducer.impl.TwitterKafkaProducer;
import com.example.twitter.to.kafka.service.transformer.StatusToTwitterAvroModelTransformer;
import example.kafka.model.TwitterAvroModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Slf4j
@Component
@AllArgsConstructor
public class TwitterKafkaStatusListener extends StatusAdapter {

    private StatusToTwitterAvroModelTransformer transformer;
    private TwitterKafkaProducer twitterKafkaProducer;
    private final KafkaConfigData kafkaConfigData;

    @Override
    public void onStatus(Status status) {
        log.info("Twitter status with text {}", status.getText());
        TwitterAvroModel twitterAvroModel = transformer.transform(status);
        twitterKafkaProducer.send(kafkaConfigData.getTopicName(), status.getId(), twitterAvroModel);

    }
}
