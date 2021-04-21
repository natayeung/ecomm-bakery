package com.natay.ecomm.bakery.catalog;

import com.natay.ecomm.bakery.testutils.ProductFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
public class ProductCatalogTests {

    private final Product redVelvet = ProductFactory.createProductWithTitle("Red Velvet");
    private final Product rainbowSprinkles = ProductFactory.createProductWithTitle("Rainbow Sprinkles");
    private ProductCatalog productCatalog;

    @BeforeEach
    public void setUp() {
        ProductQueryFakeAdapter productQueryAdapter = new ProductQueryFakeAdapter();
        productQueryAdapter.load(redVelvet, rainbowSprinkles);
        productCatalog = new ProductCatalog(productQueryAdapter);
    }

    @Test
    public void canRetrieveAllProducts() throws ProductAccessException {
        List<Product> retrieved = productCatalog.findAllProducts();

        assertThat(retrieved).as("Retrieved products").containsExactlyInAnyOrder(redVelvet, rainbowSprinkles);
    }
}