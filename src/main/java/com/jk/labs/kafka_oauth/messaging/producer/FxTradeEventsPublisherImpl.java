package com.jk.labs.kafka_oauth.messaging.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FxTradeEventsPublisherImpl implements FxTradeEventsPublisher {


    @Override
    public void publishFxTradeEvent(int noOfEvents, String authProvidersCsv) {
        for (int i = 1; i <= noOfEvents; i++) {
            log.info("STARTED Publish FX Trade Event " + i + " with auth providers: " + authProvidersCsv);

            log.info("COMPLETED Publish FX Trade Event " + i + " with auth providers: " + authProvidersCsv);
        }

    }
}
