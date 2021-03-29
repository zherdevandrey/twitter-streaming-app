package com.example.elastic.query.client.service.impl;

import com.example.elastic.model.IndexModel;
import com.example.elastic.model.impl.TwitterIndexModel;
import com.example.elastic.query.client.exception.ElasticQueryClientException;
import com.example.elastic.query.client.repository.TwitterElasticsearchQueryRepository;
import com.example.elastic.query.client.service.ElasticQueryClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TwitterElasticRepositoryQueryClient implements ElasticQueryClient<IndexModel> {

    private TwitterElasticsearchQueryRepository repository;

    @Override
    public IndexModel getIndexModelById(String id) {

        TwitterIndexModel model = repository.findById(id)
                .orElseThrow(() -> new ElasticQueryClientException("No records found by id " + id));

        log.debug("Document with id {} retrieved successfully", model.getId());
        return model;
    }

    @Override
    public List<IndexModel> getIndexModelByText(String text) {
        List<IndexModel> searchResult = repository.findByText(text);
        log.info("{} of documents with text {} retrieved successfully", searchResult.size(), text);
        return searchResult;
    }

    @Override
    public List<IndexModel> getAllIndexModels() {
        List<IndexModel> indexModels = new ArrayList<>();
        repository.findAll()
                .forEach(indexModels::add);
        return indexModels;
    }
}
