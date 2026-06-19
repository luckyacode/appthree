package com.praveen.appthree;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/three")
@RequiredArgsConstructor
@Slf4j
public class MyController {

    @GetMapping("/api/{str}")
    public String call(@PathVariable String str) {
        // 2. Add log statements inside your endpoints
        log.info("New Received request in 'call' endpoint with string: {}", str);
        return "calling now ..." + str;
    }

    @GetMapping("/")
    public String call2() {
        log.info("New Received request in 'call2' endpointok");
        return "hello app2";
    }
}