package io.gary.bestshop.order.messaging.event.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Wither
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String id;

    private String productId;

    private BigDecimal price;

    private String purchasedBy;

    private DeliveryAddressDto deliveryAddress;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime deliveredAt;

    private LocalDateTime cancelledAt;

    private LocalDateTime completedAt;
}
