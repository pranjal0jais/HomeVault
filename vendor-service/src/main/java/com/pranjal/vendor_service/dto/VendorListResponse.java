package com.pranjal.vendor_service.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorListResponse {
    private String id;
    private String name;
}
