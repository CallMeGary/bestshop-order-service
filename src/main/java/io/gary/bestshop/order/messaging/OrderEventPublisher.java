package io.gary.bestshop.order.messaging;

import io.gary.bestshop.messaging.dto.DeliveryAddressDto;
import io.gary.bestshop.messaging.dto.OrderDto;
import io.gary.bestshop.messaging.event.order.OrderCancelledEvent;
import io.gary.bestshop.messaging.event.order.OrderCompletedEvent;
import io.gary.bestshop.messaging.event.order.OrderCreatedEvent;
import io.gary.bestshop.messaging.event.order.OrderDeliveredEvent;
import io.gary.bestshop.order.domain.DeliveryAddress;
import io.gary.bestshop.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.springframework.integration.support.MessageBuilder.withPayload;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final MessagingChannels messagingChannels;

    public Order publishOrderCreatedEvent(Order order) {
        messagingChannels.orderCreatedOutput().send(
                withPayload(new OrderCreatedEvent(toDto(order))).build()
        );
        return order;
    }

    public Order publishOrderDeliveredEvent(Order order) {
        messagingChannels.orderDeliveredOutput().send(
                withPayload(new OrderDeliveredEvent(toDto(order))).build()
        );
        return order;
    }

    public Order publishOrderCancelledEvent(Order order) {
        messagingChannels.orderCancelledOutput().send(
                withPayload(new OrderCancelledEvent(toDto(order))).build()
        );
        return order;
    }

    public Order publishOrderCompletedEvent(Order order) {
        messagingChannels.orderCompletedOutput().send(
                withPayload(new OrderCompletedEvent(toDto(order))).build()
        );
        return order;
    }

    private OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .productId(order.getProduct().getId())
                .price(order.getProduct().getPrice())
                .purchasedBy(order.getPurchasedBy().getUsername())
                .deliveryAddress(toDto(order.getDeliveryAddress()))
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .deliveredAt(order.getDeliveredAt())
                .completedAt(order.getCompletedAt())
                .build();
    }

    private DeliveryAddressDto toDto(DeliveryAddress address) {
        return DeliveryAddressDto.builder()
                .address(address.getAddress())
                .postCode(address.getPostCode())
                .receiverName(address.getReceiverName())
                .build();
    }
}
