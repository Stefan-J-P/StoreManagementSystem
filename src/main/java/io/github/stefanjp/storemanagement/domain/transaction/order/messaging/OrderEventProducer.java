package io.github.stefanjp.storemanagement.domain.transaction.order.messaging;

import io.github.stefanjp.storemanagement.config.RabbitMqOrderEventConfig;
import io.github.stefanjp.storemanagement.domain.transaction.order.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishOrderCreated(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMqOrderEventConfig.ORDER_CREATED_EXCHANGE,
                RabbitMqOrderEventConfig.ORDER_CREATED_ROUTING_KEY,
                event
        );
    }
}

