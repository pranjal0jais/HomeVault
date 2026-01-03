package com.pranjal.vendor_service.service;

import com.pranjal.vendor_service.dto.VendorListResponse;
import com.pranjal.vendor_service.dto.VendorRequest;
import com.pranjal.vendor_service.dto.VendorResponse;
import com.pranjal.vendor_service.dto.VendorValidity;
import com.pranjal.vendor_service.entity.Vendor;
import com.pranjal.vendor_service.exception.UnauthorizedUserException;
import com.pranjal.vendor_service.exception.VendorNotFoundException;
import com.pranjal.vendor_service.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorResponse addNewVendor(VendorRequest vendorRequest, String userId) {
        Vendor vendor = Vendor.builder()
                .id(UUID.randomUUID().toString())
                .name(vendorRequest.getName())
                .address(vendorRequest.getAddress())
                .phoneNumber(vendorRequest.getPhoneNumber())
                .userId(userId)
                .createdOn(LocalDateTime.now())
                .build();

        vendor = vendorRepository.save(vendor);

        return toResponse(vendor);
    }

    public List<VendorResponse> getAllVendorByUser(String userId){
        return vendorRepository.findAllByUserId(userId)
                .stream().map(this::toResponse).toList();
    }

    public VendorResponse getVendorById(String vendorId, String userId){
        Vendor vendor =  vendorRepository.findById(vendorId)
                .orElseThrow(()->new VendorNotFoundException("Vendor Not Found with id: " + vendorId));

        if(!vendor.getUserId().equals(userId)){
            throw new UnauthorizedUserException("The user is unauthorized.");
        }
        return toResponse(vendor);
    }

    public List<VendorListResponse> getVendorNames(String userId){
        List<Vendor> vendors = vendorRepository.findAllByUserId(userId);
        List<VendorListResponse> vendorList =  new ArrayList<>();
        for(Vendor vendor : vendors){
            vendorList.add(new VendorListResponse(vendor.getId(),vendor.getName()));
        }
        return vendorList;
    }

    private VendorResponse toResponse(Vendor vendor){
        return VendorResponse.builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .address(vendor.getAddress())
                .phoneNumber(vendor.getPhoneNumber())
                .userId(vendor.getUserId())
                .createdOn(vendor.getCreatedOn())
                .build();
    }

    public VendorValidity isValidVendor(String vendorId){
        return VendorValidity.builder()
                .vendorId(vendorId)
                .valid(vendorRepository.existsById(vendorId))
                .build();
    }
}
