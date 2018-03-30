package io.gary.bestshop.order.errors;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String id) {
        super(String.format("Order not found with id=%s", id));
    }
}
