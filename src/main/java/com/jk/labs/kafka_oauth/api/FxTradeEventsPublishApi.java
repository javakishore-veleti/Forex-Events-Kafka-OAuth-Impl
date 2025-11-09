package com.jk.labs.kafka_oauth.api;

import com.jk.labs.kafka_oauth.service.FxTradeEventsPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/fx-trade-events", produces = "application/json")
public class FxTradeEventsPublishApi {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private FxTradeEventsPublisher fxTradeEventsPublisher;

    @GetMapping(path = "/publish", produces = "application/json")
    public ResponseEntity<String> publish(
            @RequestParam(value = "auth-providers-csv", required = false) String authProvidersCsv,
            @RequestParam(value = "kafka-topic-names-csv", required = false) String kafkaTopicsCsv,
            @RequestParam(value = "no-of-events", required = false, defaultValue = "1") int noOfEvents) {
        fxTradeEventsPublisher.publishFxTradeEvent(noOfEvents, authProvidersCsv, kafkaTopicsCsv);
        return ResponseEntity.ok("FX Trade Event Published");
    }
}
