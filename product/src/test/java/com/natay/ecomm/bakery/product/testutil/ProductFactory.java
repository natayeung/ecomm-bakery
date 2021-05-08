package com.natay.ecomm.bakery.product.testutil;

import com.natay.ecomm.bakery.product.catalog.Product;
import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;

import java.math.BigDecimal;

import static com.natay.ecomm.bakery.product.catalog.Product.Type.CUPCAKE;
import static com.natay.ecomm.bakery.product.catalog.Product.Type.WHOLE_CAKE;

/**
 * @author natayeung
 */
public class ProductFactory {

    private static final IdGenerator idGenerator = new SimpleIdGenerator();

    public static Product createWholeCakeWithTitle(String title) {
        return createProduct(WHOLE_CAKE, title, BigDecimal.valueOf(22.95));
    }

    public static Product createCupcakeWithTitle(String title) {
        return createProduct(CUPCAKE, title, BigDecimal.valueOf(1.75));
    }

    public static Product createProduct(Product.Type productType, String title, BigDecimal price) {
        return Product.builder()
                .id(nextProductId())
                .productType(productType)
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