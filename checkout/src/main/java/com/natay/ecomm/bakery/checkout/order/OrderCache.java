package com.natay.ecomm.bakery.checkout.order;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author natayeung
 */
@Component
@CacheConfig(cacheNames = {"orders"})
public class OrderCache {

    @CachePut(key = "#orderId")
    public OrderDetails put(String orderId, OrderDetails order) {
        return order;
    }

    @Cacheable(key = "#orderId")
    public Optional<OrderDetails> get(String orderId) {
        return Optional.empty();
    }
}
