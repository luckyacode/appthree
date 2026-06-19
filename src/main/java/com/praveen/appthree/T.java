package com.praveen.appthree;

import tools.jackson.databind.ObjectMapper;

public class T {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"message\": \"praveendid137ab\", \"timestamp\": \"2026-06-18T23:07:57.523915700\"}";
        System.out.println(objectMapper.readValue(json,Order.class));
    }
}
