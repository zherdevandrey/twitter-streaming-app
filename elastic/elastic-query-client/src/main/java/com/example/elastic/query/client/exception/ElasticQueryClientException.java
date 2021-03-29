package com.example.elastic.query.client.exception;

public class ElasticQueryClientException extends RuntimeException{

    public ElasticQueryClientException() {
    }

    public ElasticQueryClientException(String message) {
        super(message);
    }

    public ElasticQueryClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElasticQueryClientException(Throwable cause) {
        super(cause);
    }

    public ElasticQueryClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
