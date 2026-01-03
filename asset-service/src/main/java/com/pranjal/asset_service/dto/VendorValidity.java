package com.pranjal.asset_service.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorValidity {
    private String vendorId;
    private boolean valid;
}
