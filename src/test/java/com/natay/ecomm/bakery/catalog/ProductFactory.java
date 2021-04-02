package com.natay.ecomm.bakery.catalog;

import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;

/**
 * @author natayeung
 */
public class ProductFactory {

    private static final IdGenerator idGenerator = new SimpleIdGenerator();

    public static Product createProductWithTitle(String title) {
        return createProductWithTitleAndPrice(title, Price.of(22.95));
    }

    public static Product createProductWithTitleAndPrice(String title, Price price) {
        return Product.newBuilder()
                .withProductId(nextProductId())
                .withTitle(title)
                .withPrice(price)
                .build();
    }

    private static String nextProductId() {
        return idGenerator.generateId().toString();

    }

    private ProductFactory() {
    }
}