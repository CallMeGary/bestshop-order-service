package io.gary.bestshop.order.messaging;


import io.gary.bestshop.messaging.event.order.OrderCancelledEvent;
import io.gary.bestshop.messaging.event.order.OrderCompletedEvent;
import io.gary.bestshop.messaging.event.order.OrderCreatedEvent;
import io.gary.bestshop.messaging.event.order.OrderDeliveredEvent;
import io.gary.bestshop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Executors;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final static long ONE_MINUTE = 60L * 1000;

    private final TaskScheduler scheduler = new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());

    private final OrderService orderService;

    @StreamListener(MessagingChannels.ORDER_CREATED_INPUT)
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {

        log.info("Processing event: {}", event);

        Date inOneMinute = new Date(System.currentTimeMillis() + ONE_MINUTE);

        scheduler.schedule(() -> orderService.deliverOrder(event.getOrder().getId()), inOneMinute);
    }

    @StreamListener(MessagingChannels.ORDER_DELIVERED_INPUT)
    public void handleOrderDeliveredEvent(OrderDeliveredEvent event) {

        log.info("Processing event: {}", event);

        Date inOneMinute = new Date(System.currentTimeMillis() + ONE_MINUTE);

        scheduler.schedule(() -> orderService.completeOrder(event.getOrder().getId()), inOneMinute);
    }

    @StreamListener(MessagingChannels.ORDER_CANCELLED_INPUT)
    public void handleOrderCancelledEvent(OrderCancelledEvent event) {
        log.info("Processing event: {}", event);
    }

    @StreamListener(MessagingChannels.ORDER_COMPLETED_INPUT)
    public void handleOrderCompletedEvent(OrderCompletedEvent event) {
        log.info("Processing event: {}", event);
    }
}
