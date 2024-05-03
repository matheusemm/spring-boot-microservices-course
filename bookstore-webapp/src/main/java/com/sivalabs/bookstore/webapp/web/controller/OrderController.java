package com.sivalabs.bookstore.webapp.web.controller;

import com.sivalabs.bookstore.webapp.client.order.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

    private final OrderServiceClient orderServiceClient;

    public OrderController(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    @GetMapping("/cart")
    String cart() {
        return "cart";
    }

    @GetMapping("/orders")
    String displayOrders() {
        return "orders";
    }

    @GetMapping("/orders/{orderNumber}")
    String displayOrderDetails(@PathVariable String orderNumber, Model model) {
        model.addAttribute("orderNumber", orderNumber);
        return "orderDetails";
    }

    @PostMapping("/api/orders")
    @ResponseBody
    OrderConfirmationDTO createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
        return orderServiceClient.createOrder(getHeaders(), orderRequest);
    }

    @GetMapping("/api/orders/{orderNumber}")
    @ResponseBody
    OrderDTO getOrder(@PathVariable String orderNumber) {
        return orderServiceClient.getOrder(getHeaders(), orderNumber);
    }

    @GetMapping("/api/orders")
    @ResponseBody
    List<OrderSummary> getOrders() {
        return orderServiceClient.getOrders(getHeaders());
    }

    private Map<String, ?> getHeaders() {
        return Map.of();
    }
}
