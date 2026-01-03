package com.pranjal.asset_service.service;

import com.pranjal.asset_service.dto.ExpiryNotification;
import com.pranjal.asset_service.entity.Asset;
import com.pranjal.asset_service.rabbitmq.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationProducer notificationProducer;

    public void createNotificationBodyAndSend(Asset asset) {

        ExpiryNotification notification = ExpiryNotification.builder()
                .assetName(asset.getName())
                .serialNumber(asset.getSerialNumber())
                .company(asset.getCompany())
                .purchaseOn(asset.getPurchasedOn())
                .expiryOn(asset.getExpiryOn())
                .receiver(asset.getUserEmail())
                .build();

        notificationProducer.sendNotification(notification);
    }
}
