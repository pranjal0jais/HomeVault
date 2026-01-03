package com.pranjal.asset_service.repository;

import com.pranjal.asset_service.entity.Asset;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {

    List<Asset> findAllByUserId(String userId);

    List<Asset> findAllByVendorId(String company);

    Optional<Asset> findByIdAndUserId(String id, String userId);

    @Query("""
    SELECT a FROM Asset a
    WHERE a.expiryOn < :today
      AND a.status <> 'WARRANTY_EXPIRED'
    """)
    Page<Asset> findExpiredAssets(@Param("today") LocalDate today, Pageable pageable);
}
