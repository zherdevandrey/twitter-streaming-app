package com.example.elastic.query.client.service.impl;


import com.example.elastic.model.impl.TwitterIndexModel;
import com.example.elastic.query.client.exception.ElasticQueryClientException;
import com.example.elastic.query.client.repository.TwitterElasticsearchQueryRepository;
import com.example.elastic.query.client.service.ElasticQueryClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@AllArgsConstructor
public class TwitterElasticRepositoryQueryClient implements ElasticQueryClient<TwitterIndexModel> {

    private TwitterElasticsearchQueryRepository twitterElasticsearchQueryRepository;

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Optional<TwitterIndexModel> searchResult = twitterElasticsearchQueryRepository.findById(id);
        log.info("Document with id {} retrieved successfully",
                searchResult.orElseThrow(() ->
                        new ElasticQueryClientException("No document found at elasticsearch with id " + id)).getId());
        return searchResult.get();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        List<TwitterIndexModel> searchResult = twitterElasticsearchQueryRepository.findByText(text);
        log.info("{} of documents with text {} retrieved successfully", searchResult.size(), text);
        return searchResult;
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        List<TwitterIndexModel> searchResult = new ArrayList<>();
        twitterElasticsearchQueryRepository.findAll()
                .forEach(searchResult::add);

        log.info("{} number of documents retrieved successfully", searchResult.size());
        return searchResult;
    }
}
