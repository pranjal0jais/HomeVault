package com.pranjal.asset_service.service;

import com.pranjal.asset_service.client.VendorClient;
import com.pranjal.asset_service.dto.*;
import com.pranjal.asset_service.entity.Asset;
import com.pranjal.asset_service.exception.AssetNotFoundException;
import com.pranjal.asset_service.exception.InvalidVendorException;
import com.pranjal.asset_service.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final VendorClient vendorClient;

    private final AssetRepository assetRepository;

    private final CloudinaryService cloudinaryService;

    public AssetResponse createAsset(AssetRequest assetRequest, MultipartFile image,
                                        String userId, String email) {
        VendorValidity vendorValidity = vendorClient
                .getVendorValidity(assetRequest.getVendorId()).getBody();

        if(vendorValidity!= null && !vendorValidity.isValid()) {
            throw new InvalidVendorException("The given vendor id is invalid.");
        }

        CloudinaryResponse imageResponse = cloudinaryService.uploadFile(image);

        Asset asset = Asset.builder()
                .id(UUID.randomUUID().toString())
                .name(assetRequest.getName())
                .company(assetRequest.getCompany())
                .category(assetRequest.getCategory())
                .serialNumber(assetRequest.getSerialNumber())
                .invoice(imageResponse.getImageUrl())
                .price(assetRequest.getPrice())
                .vendorId(assetRequest.getVendorId())
                .purchasedOn(assetRequest.getPurchaseOn())
                .expiryOn(assetRequest.getExpiryOn())
                .status(Status.ACTIVE)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .userId(userId)
                .userEmail(email)
                .build();

        asset = assetRepository.save(asset);

        return toResponse(asset);
    }

    private AssetResponse toResponse(Asset asset) {
        return AssetResponse.builder()
                .assetId(asset.getId())
                .name(asset.getName())
                .company(asset.getCompany())
                .category(asset.getCategory())
                .serialNumber(asset.getSerialNumber())
                .invoice(asset.getInvoice())
                .price(asset.getPrice())
                .vendorId(asset.getVendorId())
                .userId(asset.getUserId())
                .status(asset.getStatus())
                .createdOn(LocalDate.from(asset.getCreatedOn()))
                .purchaseOn(asset.getPurchasedOn())
                .expiryOn(asset.getExpiryOn())
                .build();
    }

    public List<AssetResponse> getAllByUserId(String userId) {
        return assetRepository.findAllByUserId(userId).stream()
                .map(this :: toResponse).toList();
    }

    public List<AssetResponse> getAllByVendorId(String company) {
        return assetRepository.findAllByVendorId(company).stream()
                .map(this :: toResponse).toList();
    }

    public void deleteAsset(String assetId, String userId){
        Asset asset = assetRepository.findByIdAndUserId(assetId, userId)
                .orElseThrow(()->new AssetNotFoundException("Asset not found with id: " + assetId));

        cloudinaryService.delete(asset.getInvoice());
        assetRepository.deleteById(assetId);
    }
}
