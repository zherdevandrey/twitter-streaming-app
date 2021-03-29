package com.example.elastic.index.client.service;

import com.example.elastic.model.IndexModel;

import java.util.List;

public interface ElasticIndexClient <T extends IndexModel> {
    List<String> save(List<T> documents);
}
