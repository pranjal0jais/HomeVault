package com.pranjal.asset_service.controller;

import com.pranjal.asset_service.dto.AssetRequest;
import com.pranjal.asset_service.dto.AssetResponse;
import com.pranjal.asset_service.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AssetResponse> addAsset(@RequestPart("request") String requestString,
                                                @RequestPart("image") MultipartFile image,
                                                @AuthenticationPrincipal Jwt jwt) {
        AssetRequest assetRequest = new ObjectMapper().readValue(requestString,
                AssetRequest.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetService.createAsset(assetRequest, image,
                        jwt.getClaimAsString("userId"), jwt.getSubject()));
    }

    @GetMapping
    public ResponseEntity<List<AssetResponse>> getAsset(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(assetService.getAllByUserId(jwt.getClaimAsString("userId")));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<AssetResponse>> getAssetByVendor(@PathVariable String vendorId) {
        return ResponseEntity.ok(assetService.getAllByVendorId(vendorId));
    }

    @DeleteMapping("/{assetId}")
    public ResponseEntity<?> deleteAsset(@PathVariable("assetId") String assetId,
                                        @AuthenticationPrincipal Jwt jwt) {
        assetService.deleteAsset(assetId, jwt.getClaimAsString("userId"));
        return ResponseEntity.noContent().build();
    }
}
