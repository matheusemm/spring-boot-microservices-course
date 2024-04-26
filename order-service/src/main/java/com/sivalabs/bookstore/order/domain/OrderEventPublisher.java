package com.sivalabs.bookstore.order.domain;

import com.sivalabs.bookstore.order.ApplicationProperties;
import com.sivalabs.bookstore.order.domain.model.OrderCancelledEvent;
import com.sivalabs.bookstore.order.domain.model.OrderCreatedEvent;
import com.sivalabs.bookstore.order.domain.model.OrderDeliveredEvent;
import com.sivalabs.bookstore.order.domain.model.OrderErrorEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    OrderEventPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void publish(OrderCreatedEvent event) {
        send(properties.newOrdersQueue(), event);
    }

    public void publish(OrderDeliveredEvent event) {
        send(properties.deliveredOrdersQueue(), event);
    }

    public void publish(OrderCancelledEvent event) {
        send(properties.cancelledOrdersQueue(), event);
    }

    public void publish(OrderErrorEvent event) {
        send(properties.errorOrdersQueue(), event);
    }

    private void send(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(properties.ordersEventsExchange(), routingKey, payload);
    }
}
