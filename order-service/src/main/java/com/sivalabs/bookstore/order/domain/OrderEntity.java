package com.sivalabs.bookstore.order.domain;

import com.sivalabs.bookstore.order.domain.model.Address;
import com.sivalabs.bookstore.order.domain.model.Customer;
import com.sivalabs.bookstore.order.domain.model.OrderStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_generator")
    @SequenceGenerator(name = "order_id_generator", sequenceName = "order_id_seq")
    private Long id;

    @Column(nullable = false)
    private String orderNumber;

    @Column(name = "username", nullable = false)
    private String userName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItemEntity> items;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "customer_name")),
        @AttributeOverride(name = "email", column = @Column(name = "customer_email")),
        @AttributeOverride(name = "phone", column = @Column(name = "customer_phone"))
    })
    private Customer customer;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "line1", column = @Column(name = "delivery_address_line1")),
        @AttributeOverride(name = "line2", column = @Column(name = "delivery_address_line2")),
        @AttributeOverride(name = "city", column = @Column(name = "delivery_address_city")),
        @AttributeOverride(name = "state", column = @Column(name = "delivery_address_state")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "delivery_address_zip_code")),
        @AttributeOverride(name = "country", column = @Column(name = "delivery_address_country"))
    })
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String comments;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<OrderItemEntity> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
