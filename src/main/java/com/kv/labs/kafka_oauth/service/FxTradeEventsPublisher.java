package com.kv.labs.kafka_oauth.service;

public interface FxTradeEventsPublisher {

    public void publishFxTradeEvent(int noOfEvents, String authProvidersCsv, String kafkaTopicsCsv);
}
