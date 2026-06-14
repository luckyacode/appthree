package com.praveen.appthree;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class Consumer {
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "pt-topic",groupId = "mygroupid")
    public void consume(@Payload String string){
        log.info("consuming msg : {}",string);
        ErrorResponse response = objectMapper.readValue(string, ErrorResponse.class);
        log.info("response is : {}",response);
    }
}
