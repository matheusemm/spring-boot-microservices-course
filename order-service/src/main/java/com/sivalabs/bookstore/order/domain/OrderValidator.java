package com.sivalabs.bookstore.order.domain;

import com.sivalabs.bookstore.order.client.catalog.ProductServiceClient;
import com.sivalabs.bookstore.order.domain.model.CreateOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class OrderValidator {

    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);

    private final ProductServiceClient client;

    OrderValidator(ProductServiceClient client) {
        this.client = client;
    }

    public void validate(CreateOrderRequest request) {
        request.items().forEach(item -> {
            var product = client.getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Invalid product code: " + item.code()));
            if (item.price().compareTo(product.price()) != 0) {
                log.error(
                        "Product price not matching. Actual price: {}, received price: {}",
                        product.price(),
                        item.price());

                throw new InvalidOrderException("Product price not matching");
            }
        });
    }
}
