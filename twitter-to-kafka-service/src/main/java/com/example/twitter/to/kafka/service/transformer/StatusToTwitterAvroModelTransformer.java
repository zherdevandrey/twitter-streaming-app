package com.example.twitter.to.kafka.service.transformer;

import example.kafka.model.TwitterAvroModel;
import org.springframework.stereotype.Service;
import twitter4j.Status;

@Service
public class StatusToTwitterAvroModelTransformer {

    public TwitterAvroModel transform(Status status) {
        return TwitterAvroModel
                .newBuilder()
                .setId(status.getId())
                .setUserId(status.getUser().getId())
                .setText(status.getText())
                .setCreatedAt(status.getCreatedAt().getTime())
                .build();
    }

}
