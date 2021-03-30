package com.example.elastic.query.web.client;

import com.example.app.conf.data.ElasticQueryWebClientConfigData;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class ElasticQueryWebClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticQueryWebClientApplication.class, args);
    }

}
