package com.pranjal.asset_service.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponse {
    private String assetId;
    private String name;
    private String company;
    private String serialNumber;
    private String category;
    private LocalDate purchaseOn;
    private LocalDate expiryOn;
    private double price;
    private Status status;
    private String invoice;

    private String vendorId;
    private String userId;

    private LocalDate createdOn;
    private LocalDate updatedOn;
}
