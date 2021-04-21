package com.natay.ecomm.bakery.testutils;

import com.natay.ecomm.bakery.catalog.Product;
import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;

import java.math.BigDecimal;

import static com.natay.ecomm.bakery.catalog.Product.Type.WHOLE_CAKE;

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
                .withProductId(nextProductId())
                .withProductType(WHOLE_CAKE)
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