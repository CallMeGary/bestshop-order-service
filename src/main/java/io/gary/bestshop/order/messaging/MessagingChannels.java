package io.gary.bestshop.order.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessagingChannels {

    String ORDER_CREATED_OUTPUT = "orderCreatedOutput";
    @Output(ORDER_CREATED_OUTPUT)
    MessageChannel orderCreatedOutput();


    String ORDER_CANCELLED_OUTPUT = "orderCancelledOutput";
    @Output(ORDER_CANCELLED_OUTPUT)
    MessageChannel orderCancelledOutput();


    String ORDER_DELIVERED_OUTPUT = "orderDeliveredOutput";
    @Output(ORDER_DELIVERED_OUTPUT)
    MessageChannel orderDeliveredOutput();


    String ORDER_COMPLETED_OUTPUT = "orderCompletedOutput";
    @Output(ORDER_COMPLETED_OUTPUT)
    MessageChannel orderCompletedOutput();


    String ORDER_CREATED_INPUT = "orderCreatedInput";
    @Input(ORDER_CREATED_INPUT)
    MessageChannel orderCreatedInput();


    String ORDER_CANCELLED_INPUT = "orderCancelledInput";
    @Input(ORDER_CANCELLED_INPUT)
    MessageChannel orderCancelledInput();


    String ORDER_DELIVERED_INPUT = "orderDeliveredInput";
    @Input(ORDER_DELIVERED_INPUT)
    MessageChannel orderDeliveredInput();


    String ORDER_COMPLETED_INPUT = "orderCompletedInput";
    @Input(ORDER_COMPLETED_INPUT)
    MessageChannel orderCompletedInput();
}
