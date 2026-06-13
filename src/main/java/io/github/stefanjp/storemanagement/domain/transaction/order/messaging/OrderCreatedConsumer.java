package io.github.stefanjp.storemanagement.domain.transaction.order.messaging;

import io.github.stefanjp.storemanagement.config.RabbitMqOrderEventConfig;
import io.github.stefanjp.storemanagement.domain.transaction.order.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedConsumer.class);

    @RabbitListener(queues = RabbitMqOrderEventConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent:");
        log.info("orderId={}", event.orderId());
        log.info("customerId={}", event.customerId());
        log.info("totalAmount={}", event.totalAmount());
    }
}

