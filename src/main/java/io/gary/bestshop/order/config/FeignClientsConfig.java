package io.gary.bestshop.order.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "io.gary.bestshop")
public class FeignClientsConfig {

}
