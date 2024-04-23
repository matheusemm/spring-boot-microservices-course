package com.sivalabs.bookstore.order.web.controller;

import static com.sivalabs.bookstore.order.testdata.TestDataFactory.createCreateOrderRequestWithInvalidCustomer;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.sivalabs.bookstore.order.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTest extends AbstractIntegrationTest {

    @Nested
    class CreateOrderTests {

        @Test
        void shouldCreateOrderSuccessfully() {
            mockGetProductByCode("P100", "Product 1", new BigDecimal("25.50"));
            var payload =
                    """
                    {
                        "customer": {
                            "name": "Matheus",
                            "email": "matheus@example.com",
                            "phone": "1234567890"
                        },
                        "deliveryAddress": {
                            "line1": "Kantstraße 20",
                            "city": "Bad Salzungen",
                            "state": "Freistaat Thüringen",
                            "zipCode": "36422",
                            "country": "Germany"
                        },
                        "items": [
                            {
                                "code": "P100",
                                "name": "Product 1",
                                "price": 25.50,
                                "quantity": 1
                            }
                        ]
                    }
                    """;

            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = createCreateOrderRequestWithInvalidCustomer();
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
