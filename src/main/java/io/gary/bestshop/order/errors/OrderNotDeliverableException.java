package io.gary.bestshop.order.errors;

import io.gary.bestshop.order.domain.OrderStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class OrderNotDeliverableException extends RuntimeException {

    public OrderNotDeliverableException(OrderStatus status) {
        super(String.format("Cannot deliver order with status=%s", status));
    }
}
