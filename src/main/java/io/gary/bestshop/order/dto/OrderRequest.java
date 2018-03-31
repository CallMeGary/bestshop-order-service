package io.gary.bestshop.order.dto;

import io.gary.bestshop.order.domain.DeliveryAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Wither
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank
    private String productId;

    @NotBlank
    private String purchasedBy;

    @Valid
    @NotNull
    private DeliveryAddress deliveryAddress;
}
