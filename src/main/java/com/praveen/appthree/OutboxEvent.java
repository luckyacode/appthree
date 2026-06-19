package com.praveen.appthree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutboxEvent {
    private String id;

    private String aggregateType;

    private String aggregateId;

    private String type;
    private String payload;
    private Instant createdDateTime;
    private String status;
}