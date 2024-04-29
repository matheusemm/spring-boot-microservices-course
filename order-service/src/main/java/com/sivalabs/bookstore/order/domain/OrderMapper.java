package com.sivalabs.bookstore.order.domain;

import com.sivalabs.bookstore.order.domain.model.CreateOrderRequest;
import com.sivalabs.bookstore.order.domain.model.OrderDTO;
import com.sivalabs.bookstore.order.domain.model.OrderItem;
import com.sivalabs.bookstore.order.domain.model.OrderStatus;
import java.util.UUID;
import java.util.stream.Collectors;

class OrderMapper {

    static OrderEntity convertToEntity(CreateOrderRequest request) {
        OrderEntity order = new OrderEntity();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.NEW);
        order.setCustomer(request.customer());
        order.setDeliveryAddress(request.deliveryAddress());
        order.setItems(request.items().stream()
                .map(i -> {
                    OrderItemEntity item = new OrderItemEntity();
                    item.setCode(i.code());
                    item.setName(i.name());
                    item.setPrice(i.price());
                    item.setQuantity(i.quantity());
                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toSet()));

        return order;
    }

    static OrderDTO convertToDTO(OrderEntity order) {
        var items = order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderDTO(
                order.getOrderNumber(),
                order.getUserName(),
                items,
                order.getCustomer(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getComments(),
                order.getCreatedAt());
    }
}
