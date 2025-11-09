package com.jk.labs.kafka_oauth.service;

import com.jk.labs.kafka_oauth.constants.AppKafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FxTradeEventsPublisherImpl implements FxTradeEventsPublisher {


    @Override
    public void publishFxTradeEvent(int noOfEvents, String authProvidersCsv, String kafkaTopicsCsv) {
        List<String> authProviders = null;
        if(ObjectUtils.isEmpty(authProvidersCsv)) {
            authProviders = AppKafkaConstants.AUTH_PROVIDERS;
        } else {
            authProviders = List.of(authProvidersCsv.trim().toLowerCase().split(","));
        }

        List<String> kafaTopics = null;
        if(ObjectUtils.isEmpty(kafkaTopicsCsv)) {
            kafaTopics = AppKafkaConstants.KAFKA_TOPIC_NAMES;
        } else {
            kafaTopics = List.of(kafkaTopicsCsv.trim().toLowerCase().split(","));
        }

        for (int i = 1; i <= noOfEvents; i++) {
            log.info("STARTED Publish FX Trade Event " + i + " with auth providers: " + authProvidersCsv);

            log.info("COMPLETED Publish FX Trade Event " + i + " with auth providers: " + authProvidersCsv);
        }

    }
}
