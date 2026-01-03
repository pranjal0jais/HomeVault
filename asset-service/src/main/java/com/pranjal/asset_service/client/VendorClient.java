package com.pranjal.asset_service.client;

import com.pranjal.asset_service.dto.VendorValidity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vendor-service", url = "http://localhost:8082")
public interface VendorClient {

    @GetMapping("api/vendors/vendor/validity/{vendorId}")
    public ResponseEntity<VendorValidity> getVendorValidity(@PathVariable("vendorId") String vendorId);
}
