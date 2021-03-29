package com.example.elastic.query.client.repository;

import com.example.elastic.model.IndexModel;
import com.example.elastic.model.impl.TwitterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TwitterElasticsearchQueryRepository extends ElasticsearchRepository<TwitterIndexModel, String> {

    List<IndexModel> findByText(String text);

}
