package com.sivalabs.bookstore.webapp.client.order;

public record OrderConfirmationDTO(String orderNumber, OrderStatus status) {}
