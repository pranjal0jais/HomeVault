package com.pranjal.notification_service.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExpiryNotification {
    private String receiver;
    private String assetName;
    private String company;
    private String serialNumber;
    private LocalDate purchaseOn;
    private LocalDate expiryOn;
}
