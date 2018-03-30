package io.gary.bestshop.order.client;

import io.gary.bestshop.order.domain.Profile;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("bestshop-profile-service")
public interface ProfileClient {

    @GetMapping("/profiles/{username}")
    Profile getProfile(@PathVariable("username") String username);

}
