package com.pranjal.asset_service.rabbitmq;

import com.pranjal.asset_service.dto.ExpiryNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;
    public void sendNotification(ExpiryNotification  notification) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                notification
        );
    }
}

