package com.pranjal.vendor_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponse {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdOn;

    private String userId;
}
