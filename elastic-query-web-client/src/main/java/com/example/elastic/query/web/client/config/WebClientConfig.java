package com.example.elastic.query.web.client.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AllArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@AllArgsConstructor
public class WebClientConfig {

    private ElasticWebClientDataConfig elasticWebClientDataConfig;

    @Bean("webClientBuilder")
    WebClient.Builder webClientBuilder(){
        return WebClient.builder()
                .baseUrl(elasticWebClientDataConfig.getWebClient().getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(getTcpClient())));
    }

    TcpClient getTcpClient() {
        ElasticWebClientDataConfig.WebClient webClientData = elasticWebClientDataConfig.getWebClient();
        return TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientData.getConnectTimeoutMs())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(
                            new ReadTimeoutHandler(webClientData.getReadTimeoutMs(),
                                    TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(
                            new WriteTimeoutHandler(webClientData.getWriteTimeoutMs(),
                                    TimeUnit.MILLISECONDS));
                });
    }
}
