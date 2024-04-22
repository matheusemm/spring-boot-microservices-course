package com.sivalabs.bookstore.order.domain;

import com.sivalabs.bookstore.order.domain.model.CreateOrderRequest;
import com.sivalabs.bookstore.order.domain.model.CreateOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        OrderEntity newOrder = OrderMapper.convertToEntity(request);
        newOrder.setUserName(userName);

        OrderEntity savedOrder = this.orderRepository.save(newOrder);
        log.info("Created order with order number={}", savedOrder.getOrderNumber());

        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}
