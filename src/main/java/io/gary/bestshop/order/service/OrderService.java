package io.gary.bestshop.order.service;

import feign.FeignException;
import io.gary.bestshop.order.client.ProductClient;
import io.gary.bestshop.order.client.ProfileClient;
import io.gary.bestshop.order.domain.Order;
import io.gary.bestshop.order.domain.Product;
import io.gary.bestshop.order.domain.Profile;
import io.gary.bestshop.order.dto.OrderRequest;
import io.gary.bestshop.order.errors.*;
import io.gary.bestshop.order.messaging.OrderEventPublisher;
import io.gary.bestshop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static io.gary.bestshop.order.domain.OrderStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductClient productClient;

    private final ProfileClient profileClient;

    private final OrderEventPublisher orderEventPublisher;


    public List<Order> getOrders() {

        log.info("Getting orders");

        return orderRepository.findAll();
    }

    public Order getOrder(@NotNull String id) {

        log.info("Getting order: id={}", id);

        return findOrderOrThrow(id);
    }

    public Order createOrder(@NotNull @Valid OrderRequest request) {

        log.info("Creating order: request={}", request);

        Product product = findProductOrThrow(request.getProductId());
        Profile purchasedBy = findProfileOrThrow(request.getPurchasedBy());

        Order toCreate = Order.builder()
                .product(product)
                .price(product.getPrice())
                .purchasedBy(purchasedBy)
                .deliveryAddress(request.getDeliveryAddress())
                .status(Created)
                .createdAt(LocalDateTime.now())
                .build();

        Order created = orderRepository.save(toCreate);

        return orderEventPublisher.publishOrderCreatedEvent(created);
    }

    public Order cancelOrder(@NotNull String id) {

        log.info("Cancelling order: id={}", id);

        Order order = findOrderOrThrow(id);

        if (order.getStatus() != Created) {
            throw new OrderNotCancellableException(order.getStatus());
        }

        Order saved = orderRepository.save(order.withStatus(Cancelled));

        return orderEventPublisher.publishOrderCancelledEvent(saved);
    }

    public Order deliverOrder(@NotNull String id) {

        log.info("Delivered order: id={}", id);

        Order order = findOrderOrThrow(id);

        if (order.getStatus() != Created) {
            throw new OrderNotDeliverableException(order.getStatus());
        }

        Order saved = orderRepository.save(order.withStatus(Delivered));

        return orderEventPublisher.publishOrderDeliveredEvent(saved);
    }

    public Order completeOrder(@NotNull String id) {

        log.info("Completing order: id={}", id);

        Order order = findOrderOrThrow(id);

        if (order.getStatus() != Delivered) {
            throw new OrderNotCompletableException(order.getStatus());
        }

        Order saved = orderRepository.save(order.withStatus(Completed));

        return orderEventPublisher.publishOrderCompletedEvent(saved);
    }

    private Order findOrderOrThrow(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    private Product findProductOrThrow(String productId) {
        try {
            return productClient.getProduct(productId);
        } catch (FeignException e) {
            log.warn("Error when trying to find product with id={}, reason={}", productId, e.getMessage());
            throw new InvalidOrderRequestException("Cannot find product with id: " + productId);
        }
    }

    private Profile findProfileOrThrow(String username) {
        try {
            return profileClient.getProfile(username);
        } catch (FeignException e) {
            log.warn("Error when trying to find profile with username={}, reason={}", username, e.getMessage());
            throw new InvalidOrderRequestException("Cannot find profile with username: " + username);
        }
    }
}
