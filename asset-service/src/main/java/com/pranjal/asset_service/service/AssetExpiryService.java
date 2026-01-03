package com.pranjal.asset_service.service;

import com.pranjal.asset_service.dto.Status;
import com.pranjal.asset_service.entity.Asset;
import com.pranjal.asset_service.rabbitmq.NotificationProducer;
import com.pranjal.asset_service.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AssetExpiryService {

    private static final int BATCH_SIZE = 500;

    private final AssetRepository assetRepository;

    private final NotificationService notificationService;

    @Scheduled(cron = "0 */1 * * * *", zone = "UTC")
    public void markAssetsAsExpired(){
        LocalDate today = LocalDate.now();
        Pageable pageable = PageRequest.of(0, BATCH_SIZE);

        Page<Asset> page;

        do {
            page = assetRepository.findExpiredAssets(today, pageable);
            if (page.hasContent()) {
                page.getContent()
                        .forEach(asset -> asset.setStatus(Status.WARRANTY_EXPIRED));
                page.forEach(notificationService::createNotificationBodyAndSend);
                assetRepository.saveAll(page.getContent());
            }

            pageable = page.nextPageable();
        } while(page.hasNext());
    }

}
