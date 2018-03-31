package io.gary.bestshop.order.messaging.event.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCompletedEvent {

    private OrderDto order;
}
