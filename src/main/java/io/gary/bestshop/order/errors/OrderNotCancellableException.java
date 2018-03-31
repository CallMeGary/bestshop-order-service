package io.gary.bestshop.order.errors;

import io.gary.bestshop.order.domain.OrderStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class OrderNotCancellableException extends RuntimeException {

    public OrderNotCancellableException(OrderStatus status) {
        super(String.format("Cannot cancel order with status=%s", status));
    }
}
