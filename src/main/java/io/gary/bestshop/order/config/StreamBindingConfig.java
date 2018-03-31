package io.gary.bestshop.order.config;

import io.gary.bestshop.order.messaging.MessagingChannels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(MessagingChannels.class)
public class StreamBindingConfig {

}
