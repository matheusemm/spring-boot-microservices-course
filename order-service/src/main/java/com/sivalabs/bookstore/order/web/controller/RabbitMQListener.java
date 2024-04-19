package com.sivalabs.bookstore.order.web.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    @RabbitListener(queues = "${order.new-orders-queue}")
    public void handleNewOrder(MyPayload payload) {
        System.out.println("New Order: " + payload.content());
    }

    @RabbitListener(queues = "${order.delivered-orders-queue}")
    public void handleDeliveredOrder(MyPayload payload) {
        System.out.println("Delivered Order: " + payload.content());
    }
}
