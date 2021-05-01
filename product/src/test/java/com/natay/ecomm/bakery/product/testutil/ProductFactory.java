package com.natay.ecomm.bakery.product.testutil;

import com.natay.ecomm.bakery.product.catalog.Product;
import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;

import java.math.BigDecimal;

import static com.natay.ecomm.bakery.product.catalog.Product.Type.WHOLE_CAKE;

/**
 * @author natayeung
 */
public class ProductFactory {

    private static final IdGenerator idGenerator = new SimpleIdGenerator();

    public static Product createProductWithTitle(String title) {
        return createProductWithTitleAndPrice(title, BigDecimal.valueOf(22.95));
    }

    public static Product createProductWithTitleAndPrice(String title, BigDecimal price) {
        return Product.builder()
                .id(nextProductId())
                .productType(WHOLE_CAKE)
                .title(title)
                .price(price)
                .build();
    }

    private static String nextProductId() {
        return idGenerator.generateId().toString();

    }

    private ProductFactory() {
    }
}