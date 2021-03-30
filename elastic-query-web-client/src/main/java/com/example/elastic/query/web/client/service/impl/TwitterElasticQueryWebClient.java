package com.example.elastic.query.web.client.service.impl;

import com.example.app.conf.data.ElasticQueryWebClientConfigData;
import com.example.elastic.query.web.client.model.ElasticQueryWebClientRequestModel;
import com.example.elastic.query.web.client.model.ElasticQueryWebClientResponseModel;
import com.example.elastic.query.web.client.service.ElasticQueryWebClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TwitterElasticQueryWebClient implements ElasticQueryWebClient {

    @Override
    public List<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel requestModel) {
        return WebClient
                .create("http://localhost:8183")
                .post()
                .uri("elastic-query-service/documents/get-document-by-text")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(requestModel), ElasticQueryWebClientRequestModel.class)
                .retrieve()
                .bodyToFlux(ElasticQueryWebClientResponseModel.class)
                .collectList()
                .block();
    }
}
