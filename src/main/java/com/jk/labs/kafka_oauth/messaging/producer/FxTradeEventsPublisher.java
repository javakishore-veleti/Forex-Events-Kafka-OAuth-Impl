package com.jk.labs.kafka_oauth.messaging.producer;

public interface FxTradeEventsPublisher {

    public void publishFxTradeEvent(int noOfEvents, String authProvidersCsv);
}
