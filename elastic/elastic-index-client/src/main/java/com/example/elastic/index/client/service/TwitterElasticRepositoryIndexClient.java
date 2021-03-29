package com.example.elastic.index.client.service;

import com.example.app.conf.data.ElasticConfigData;
import com.example.elastic.index.client.repository.TwitterElasticsearchIndexRepository;
import com.example.elastic.model.impl.TwitterIndexModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel> {

    private ElasticConfigData elasticConfigData;
    private TwitterElasticsearchIndexRepository repository;

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<TwitterIndexModel> savedTwitterIndexModels = (List<TwitterIndexModel>) repository.saveAll(documents);
        List<String> ids = savedTwitterIndexModels.stream().map(TwitterIndexModel::getId)
                .collect(Collectors.toList());
       // log.info("Documents indexed successfully with type: {} and ids: {}", TwitterIndexModel.class.getName(), ids);
        return ids;
    }
}
