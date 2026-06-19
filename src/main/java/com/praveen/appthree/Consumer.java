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
    public void consume( String string){

        log.info("consuming msg : {}",string);
        System.out.println();
        System.out.println("consuming : "+objectMapper.readValue(string,Order.class));
    }


//    @KafkaListener(topics = "pt-topic",groupId = "mygroupid")
//    public void consume( String string){
//        log.info("consuming msg : {}",string);
//        System.out.println();
//        System.out.println("consuming : "+objectMapper.readValue(string,Order.class));
////        Order response = objectMapper.readValue(string, Order.class);
////        log.info("response is : {}",response);
//    }
}
