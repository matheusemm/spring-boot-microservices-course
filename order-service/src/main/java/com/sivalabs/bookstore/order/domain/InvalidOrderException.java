package com.sivalabs.bookstore.order.domain;

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String message) {
        super(message);
    }
}
