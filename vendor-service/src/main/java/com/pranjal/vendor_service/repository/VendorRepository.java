package com.pranjal.vendor_service.repository;

import com.pranjal.vendor_service.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, String> {
    List<Vendor> findAllByUserId(String userId);

    Vendor getById(String id);


}
