package com.pranjal.asset_service.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloudinaryResponse {
    private String imageUrl;
    private String publicId;
}
