package io.gary.bestshop.order.domain;

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
public class Profile {

    private String username;

    private String email;

    private String nickname;

    private String firstName;

    private String lastName;

    private String mobilePhone;
}
