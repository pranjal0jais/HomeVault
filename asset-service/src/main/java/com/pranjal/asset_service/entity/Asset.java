package com.pranjal.asset_service.entity;

import com.pranjal.asset_service.dto.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asset {
    @Id
    @Column(nullable = false, unique = true,  updatable = false)
    private String id;
    @Column(nullable = false)
    private String company;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String serialNumber;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String invoice;

    @Column(nullable = false, updatable = false)
    private LocalDate purchasedOn;
    @Column(nullable = false,  updatable = false)
    private LocalDate expiryOn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String vendorId;
}
