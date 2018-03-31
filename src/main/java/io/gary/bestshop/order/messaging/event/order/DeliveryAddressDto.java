package io.gary.bestshop.order.messaging.event.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@Wither
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressDto {

    private String address;

    private String postCode;

    private String receiverName;
}
