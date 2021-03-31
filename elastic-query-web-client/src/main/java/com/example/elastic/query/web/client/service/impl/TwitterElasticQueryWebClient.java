package com.example.elastic.query.web.client.service.impl;

import com.example.elastic.query.web.client.config.ElasticWebClientDataConfig;
import com.example.elastic.query.web.client.model.ElasticQueryWebClientRequestModel;
import com.example.elastic.query.web.client.model.ElasticQueryWebClientResponseModel;
import com.example.elastic.query.web.client.service.ElasticQueryWebClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TwitterElasticQueryWebClient implements ElasticQueryWebClient {

    private WebClient.Builder webClientBuilder;
    private ElasticWebClientDataConfig dataConfig;

    @Override
    public List<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel requestModel) {
        return webClientBuilder
                .build()
                .method(HttpMethod.valueOf(dataConfig.getQueryByText().getMethod()))
                .uri(dataConfig.getQueryByText().getUri())
                .accept(MediaType.valueOf(dataConfig.getQueryByText().getAccept()))
                .body(BodyInserters.fromPublisher(Mono.just(requestModel), ElasticQueryWebClientRequestModel.class))
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        cr -> Mono.just(new RuntimeException(cr.statusCode().getReasonPhrase())))
                .onStatus(
                        HttpStatus::is5xxServerError,
                        cr -> Mono.just(new Exception(cr.statusCode().getReasonPhrase())))
                .bodyToFlux(ElasticQueryWebClientResponseModel.class)
                .collectList()
                .block();
    }
}
