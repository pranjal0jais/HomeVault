package com.pranjal.vendor_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorRequest {
    @NotBlank(message = "The vendor name should not be blank.")
    private String name;
    @Size(min = 10, max = 10, message = "The phone number is invalid.")
    private String phoneNumber;
    @NotBlank(message = "The address should be blank.")
    @Size(min = 20, message = "The address must contain at least 20 characters.")
    private String address;
}
