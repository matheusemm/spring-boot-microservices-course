package com.sivalabs.bookstore.order.testdata;

import static org.instancio.Select.field;

import com.sivalabs.bookstore.order.domain.model.Address;
import com.sivalabs.bookstore.order.domain.model.CreateOrderRequest;
import com.sivalabs.bookstore.order.domain.model.Customer;
import com.sivalabs.bookstore.order.domain.model.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.instancio.Instancio;

public class TestDataFactory {
    static final List<String> VALID_COUNTRIES = List.of("India", "Germany");

    static final Set<OrderItem> VALID_ORDER_ITEMS =
            Set.of(new OrderItem("P100", "Product 1", new BigDecimal("25.50"), 1));

    static final Set<OrderItem> INVALID_ORDER_ITEMS =
            Set.of(new OrderItem("ABCD", "Product 1", new BigDecimal("0.00"), 1));

    public static CreateOrderRequest createValidCreateOrderRequest() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#a#a#a#a#a#a@gmail.com"))
                .set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .create();
    }

    public static CreateOrderRequest createCreateOrderRequestWithInvalidCustomer() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@gmail.com"))
                .set(field(Customer::phone), "")
                .set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .create();
    }

    public static CreateOrderRequest createCreateOrderRequestWithInvalidDeliveryAddress() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@gmail.com"))
                .set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
                .set(field(Address::country), "")
                .create();
    }

    public static CreateOrderRequest createCreateOrderRequestWithNoItems() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@gmail.com"))
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
                .set(field(CreateOrderRequest::items), Set.of())
                .create();
    }
}
