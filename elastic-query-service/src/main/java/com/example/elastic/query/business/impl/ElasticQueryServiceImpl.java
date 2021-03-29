package com.example.elastic.query.business.impl;

import com.example.elastic.model.IndexModel;
import com.example.elastic.model.impl.TwitterIndexModel;
import com.example.elastic.query.business.ElasticQueryService;
import com.example.elastic.query.client.service.ElasticQueryClient;
import com.example.elastic.query.model.ElasticQueryServiceResponseModel;
import com.example.elastic.query.transformer.ElasticToResponseModelTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ElasticQueryServiceImpl implements ElasticQueryService {

    private ElasticQueryClient<TwitterIndexModel> elasticQueryClient;
    private ElasticToResponseModelTransformer transformer;

    @Override
    public ElasticQueryServiceResponseModel getDocumentById(String id) {
        log.info("Querying elasticsearch by id {}", id);
        return transformer.getResponseModel(elasticQueryClient.getIndexModelById(id));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        log.info("Querying elasticsearch by text {}", text);
        return transformer.getResponseModels(elasticQueryClient.getIndexModelByText(text));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getAllDocuments() {
        log.info("Querying all documents in elasticsearch");
        return transformer.getResponseModels(elasticQueryClient.getAllIndexModels());
    }
}
