package com.sivalabs.bookstore.notification.event;

import com.sivalabs.bookstore.notification.domain.NotificationService;
import com.sivalabs.bookstore.notification.domain.OrderEventEntity;
import com.sivalabs.bookstore.notification.domain.OrderEventRepository;
import com.sivalabs.bookstore.notification.domain.model.OrderCancelledEvent;
import com.sivalabs.bookstore.notification.domain.model.OrderCreatedEvent;
import com.sivalabs.bookstore.notification.domain.model.OrderDeliveredEvent;
import com.sivalabs.bookstore.notification.domain.model.OrderErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    private final NotificationService notificationService;
    private final OrderEventRepository orderEventRepository;

    public OrderEventHandler(NotificationService notificationService, OrderEventRepository orderEventRepository) {
        this.notificationService = notificationService;
        this.orderEventRepository = orderEventRepository;
    }

    @RabbitListener(queues = "${notification.new-orders-queue}")
    void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Order created event: " + event);

        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", event.eventId());
        } else {
            notificationService.sendOrderCreatedNotification(event);
            saveOrderEvent(event.eventId());
        }
    }

    @RabbitListener(queues = "${notification.delivered-orders-queue}")
    void handleOrderDeliveredEvent(OrderDeliveredEvent event) {
        log.info("Order delivered event: " + event);

        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received duplicate OrderDeliveredEvent with eventId: {}", event.eventId());
        } else {
            notificationService.sendOrderDeliveredNotification(event);
            saveOrderEvent(event.eventId());
        }
    }

    @RabbitListener(queues = "${notification.cancelled-orders-queue}")
    void handleOrderCancelledEvent(OrderCancelledEvent event) {
        log.info("Order cancelled event: " + event);

        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received duplicate OrderCancelledEvent with eventId: {}", event.eventId());
        } else {
            notificationService.sendOrderCancelledNotification(event);
            saveOrderEvent(event.eventId());
        }
    }

    @RabbitListener(queues = "${notification.error-orders-queue}")
    void handleOrderErrorEvent(OrderErrorEvent event) {
        log.info("Order error event: " + event);

        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received duplicate OrderErrorEvent with eventId: {}", event.eventId());
        } else {
            notificationService.sendOrderErrorNotification(event);
            saveOrderEvent(event.eventId());
        }
    }

    private void saveOrderEvent(String eventId) {
        orderEventRepository.save(new OrderEventEntity(eventId));
    }
}
