package com.pranjal.vendor_service.controller;

import com.pranjal.vendor_service.dto.VendorListResponse;
import com.pranjal.vendor_service.dto.VendorRequest;
import com.pranjal.vendor_service.dto.VendorResponse;
import com.pranjal.vendor_service.dto.VendorValidity;
import com.pranjal.vendor_service.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<VendorResponse> addVendor(@Valid @RequestBody VendorRequest vendorRequest,
                                                    @AuthenticationPrincipal Jwt jwt) {

        String userId = jwt.getClaimAsString("userId");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vendorService.addNewVendor(vendorRequest, userId));
    }

    @GetMapping
    public ResponseEntity<List<VendorResponse>> getVendor(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(vendorService.getAllVendorByUser(jwt.getClaimAsString("userId")));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<VendorResponse> getVendorById(@PathVariable("vendorId") String vendorId,
                                                        @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(vendorService.getVendorById(vendorId, jwt.getClaimAsString("userId")));
    }

    @GetMapping("/list")
    public ResponseEntity<List<VendorListResponse>> getVendorList(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(vendorService.getVendorNames(jwt.getClaimAsString("userId")));
    }

    @GetMapping("/vendor/validity/{vendorId}")
    public ResponseEntity<VendorValidity> getVendorValidity(@PathVariable("vendorId") String vendorId){
        return ResponseEntity.ok(vendorService.isValidVendor(vendorId));
    }
}
