package com.pranjal.asset_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequest {
    @NotBlank(message = "Name cannot be blank.")
    private String name;
    @NotBlank(message = "Company cannot be blank.")
    private String company;
    @NotBlank(message = "Serial Number cannot be blank.")
    private String serialNumber;
    @NotBlank(message = "Category of asset cannot be blank.")
    private String category;
    @NotBlank(message = "Must enter a purchase date.")
    private LocalDate purchaseOn;
    @NotBlank(message = "Must enter a expiry date.")
    private LocalDate expiryOn;
    @NotBlank(message = "Price cannot be blank.")
    @Min(value = 0, message = "Price cannot be 0.")
    private double price;
    @NotBlank(message = "Vendor cannot be blank.")
    private String vendorId;
}
