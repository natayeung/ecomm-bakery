package com.natay.ecomm.bakery.product.catalog;

import com.natay.ecomm.bakery.product.catalog.persistence.ProductQueryFakeAdapter;
import com.natay.ecomm.bakery.product.catalog.persistence.ProductQueryPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.natay.ecomm.bakery.product.testutil.ProductFactory.createProductWithTitle;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
@SpringBootTest(classes = ProductCatalog.class)
@Import(ProductCatalogTests.TestConfig.class)
public class ProductCatalogTests {

    private static final Product redVelvet = createProductWithTitle("Red Velvet");
    private static final Product rainbowSprinkles = createProductWithTitle("Rainbow Sprinkles");

    @Autowired
    private ProductQueryPort productQueryPort;

    @Autowired
    private ProductCatalog productCatalog;

    @TestConfiguration
    static class TestConfig {
        @Bean
        ProductQueryPort productQueryAdapter() {
            ProductQueryFakeAdapter productQueryAdapter = new ProductQueryFakeAdapter();
            productQueryAdapter.load(redVelvet, rainbowSprinkles);
            return productQueryAdapter;
        }
    }

    @Test
    public void canRetrieveAllProducts() throws ProductAccessException {
        List<Product> retrieved = productCatalog.findAllProducts();

        assertThat(retrieved).as("Retrieved products").containsExactlyInAnyOrder(redVelvet, rainbowSprinkles);
    }
}