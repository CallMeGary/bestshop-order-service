package io.gary.bestshop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Wither
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddress {

    @NotBlank
    private String address;

    @NotBlank
    private String postCode;

    @NotBlank
    private String receiverName;
}
