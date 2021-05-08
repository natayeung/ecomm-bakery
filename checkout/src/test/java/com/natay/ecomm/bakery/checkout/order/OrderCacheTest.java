package com.natay.ecomm.bakery.checkout.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.natay.ecomm.bakery.checkout.testutil.OrderDetailsFactory.createOrderDetails;
import static com.natay.ecomm.bakery.checkout.testutil.OrderDetailsFactory.randomOrderId;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
@SpringBootTest(classes = {
        CaffeineCacheManager.class,
        CacheConfig.class,
        OrderCache.class})
@ActiveProfiles("dev")
public class OrderCacheTest {

    @Autowired
    private OrderCache orderCache;

    @Test
    public void canSaveOrderIntoCache() {
        String orderId = randomOrderId();
        OrderDetails orderDetails = createOrderDetails();

        orderCache.put(orderId, orderDetails);

        Optional<OrderDetails> retrieved = orderCache.get(orderId);
        assertThat(retrieved).contains(orderDetails);
    }

    @Test
    public void canRemoveOrderFromCache() {
        String orderId = randomOrderId();
        OrderDetails orderDetails = createOrderDetails();
        orderCache.put(orderId, orderDetails);

        orderCache.remove(orderId);

        assertThat(orderCache.get(orderId)).isEmpty();
    }
}
