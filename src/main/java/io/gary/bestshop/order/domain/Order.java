package io.gary.bestshop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Wither
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private Product product;

    private Profile createdBy;

    private DeliveryAddress deliveryAddress;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;
}
