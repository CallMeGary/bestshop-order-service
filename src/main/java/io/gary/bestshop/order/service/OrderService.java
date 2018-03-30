package io.gary.bestshop.order.service;

import feign.FeignException;
import io.gary.bestshop.order.client.ProductClient;
import io.gary.bestshop.order.client.ProfileClient;
import io.gary.bestshop.order.domain.Order;
import io.gary.bestshop.order.domain.OrderStatus;
import io.gary.bestshop.order.domain.Product;
import io.gary.bestshop.order.domain.Profile;
import io.gary.bestshop.order.dto.OrderRequest;
import io.gary.bestshop.order.errors.InvalidOrderRequestException;
import io.gary.bestshop.order.errors.OrderNotFoundException;
import io.gary.bestshop.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    private ProductClient productClient;

    private ProfileClient profileClient;

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
        Profile createdBy = findProfileOrThrow(request.getCreatedBy());

        Order toCreate = Order.builder()
                .product(product)
                .createdBy(createdBy)
                .deliveryAddress(request.getDeliveryAddress())
                .status(OrderStatus.Created)
                .createdAt(LocalDateTime.now())
                .build();

        return orderRepository.save(toCreate);
    }

    public void deleteOrder(@NotNull String id) {

        log.info("Deleting order: id={}", id);

        Order toDelete = findOrderOrThrow(id);

        orderRepository.delete(toDelete);
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
