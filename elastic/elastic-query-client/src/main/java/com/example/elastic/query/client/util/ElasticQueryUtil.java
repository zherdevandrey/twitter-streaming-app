package com.example.elastic.query.client.util;

import com.example.elastic.model.IndexModel;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.Collections;

public class ElasticQueryUtil<T extends IndexModel> {

    public Query getSearchQueryById(String id){
        return new NativeSearchQueryBuilder()
                .withIds(Collections.singleton(id))
                .build();
    }

    public Query getSearchQueryByFieldText(String field, String text){
        return new NativeSearchQueryBuilder()
                .withQuery(new BoolQueryBuilder()
                        .must(QueryBuilders.matchQuery(field, text))
                ).build();
    }

    public Query getSearchQueryForAll(){
        return new NativeSearchQueryBuilder()
                .withQuery(new BoolQueryBuilder()
                    .must(QueryBuilders.matchAllQuery())
                ).build();
    }

}
