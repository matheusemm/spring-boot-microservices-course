package com.sivalabs.bookstore.order.domain;

import com.sivalabs.bookstore.order.domain.model.OrderStatus;
import com.sivalabs.bookstore.order.domain.model.OrderSummary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(
            """
        select o
        from
            OrderEntity o
            left join fetch o.items
        where
            o.userName = :userName
            and o.orderNumber = :orderNumber
    """)
    Optional<OrderEntity> findByUserNameAndOrderNumber(String userName, String orderNumber);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    List<OrderEntity> findByStatus(OrderStatus status);

    @Query(
            """
        select new com.sivalabs.bookstore.order.domain.model.OrderSummary(o.orderNumber, o.status)
        from OrderEntity o
        where o.userName = :userName
    """)
    List<OrderSummary> findByUserName(String userName);

    default void updateOrderStatus(String orderNumber, OrderStatus status) {
        OrderEntity order = findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(status);
        save(order);
    }
}
