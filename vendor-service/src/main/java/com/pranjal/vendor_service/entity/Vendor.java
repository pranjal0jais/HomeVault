package com.pranjal.vendor_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vendor {
    @Id
    @Column(nullable = false, unique = true,  updatable = false)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false,  unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private String userId;
}
