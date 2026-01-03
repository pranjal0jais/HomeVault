package com.pranjal.notification_service.service;

import com.pranjal.notification_service.dto.ExpiryNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private String subject = "Asset Expiry Notification – {{ASSET_NAME}}";

    private String body = """
                            Dear User,
            
                            This is to inform you that the following asset has expired.
            
                            Asset Details:
                            -------------------------
                            Asset Name     : {{ASSET_NAME}}
                            Company        : {{COMPANY}}
                            Serial Number  : {{SERIAL_NUMBER}}
                            Purchased On  : {{PURCHASED_ON}}
                            Expired On    : {{EXPIRY_ON}}
                            -------------------------
            
                            Please take the necessary action to renew or replace this asset as required.
            
                            If you have any questions, contact the support team.
            
                            Regards,
                            HomeVault
            """;

    public void sendMail(ExpiryNotification expiryNotification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(expiryNotification.getReceiver());
        message.setSubject(subject.replace("{{ASSET_NAME}}", expiryNotification.getAssetName()));
        message.setText(body
                .replace("{{ASSET_NAME}}", expiryNotification.getAssetName())
                .replace("{{COMPANY}}", expiryNotification.getCompany())
                .replace("{{SERIAL_NUMBER}}", expiryNotification.getSerialNumber())
                .replace("{{PURCHASED_ON}}", expiryNotification.getPurchaseOn().toString())
                .replace("{{EXPIRY_ON}}", expiryNotification.getExpiryOn().toString()));

        javaMailSender.send(message);
        log.info("Sent mail to {}", expiryNotification.getReceiver());
    }
}
