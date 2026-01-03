package com.pranjal.notification_service.rabbitmq;

import com.pranjal.notification_service.dto.ExpiryNotification;
import com.pranjal.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(
            queues = RabbitMQConfig.QUEUE
    )
    public void consumerNotification(ExpiryNotification notification){
        log.info("Received Notification Event: {}", notification.toString());
        notificationService.sendMail(notification);
    }
}
