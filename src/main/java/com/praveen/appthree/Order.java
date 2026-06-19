package com.praveen.appthree;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String message;
    private String timestamp;
}
