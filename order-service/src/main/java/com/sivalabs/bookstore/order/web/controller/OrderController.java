package com.sivalabs.bookstore.order.web.controller;

import com.sivalabs.bookstore.order.domain.OrderService;
import com.sivalabs.bookstore.order.domain.SecurityService;
import com.sivalabs.bookstore.order.domain.model.CreateOrderRequest;
import com.sivalabs.bookstore.order.domain.model.CreateOrderResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLoggedInUserName();
        log.info("Creating order for user: {}", userName);

        return orderService.createOrder(userName, request);
    }
}