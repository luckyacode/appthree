package com.praveen.appthree;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;


@RestController
@RequestMapping("/three")
@RequiredArgsConstructor
@Slf4j
public class MyController {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/api/{str}")
    public String call(@PathVariable String str) {
        // 2. Add log statements inside your endpoints
        log.info("New Received request in 'call' endpoint with string: {}", str);
        return "calling now ..." + str;
    }

    @GetMapping("/")
    public String call2() {
        log.info("New Received request in 'call2' endpoint");
        ErrorResponse errorResponse = ErrorResponse.builder().message("praveen did "+ UUID.randomUUID().toString().replace("-","").substring(0,5)).timestamp(LocalDateTime.now().toString()).build();
        String json = objectMapper.writeValueAsString(errorResponse);
        log.info("new send to appthree : response {}",json);
        kafkaTemplate.send("pt-topic2",json);
        return "hello app2";
    }
}