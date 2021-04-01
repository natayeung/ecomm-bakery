package com.natay.ecomm.bakery.catalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogTests {

    private final Product redVelvet = ProductFactory.createProductWithTitle("Red Velvet");
    private final Product rainbowSprinkles = ProductFactory.createProductWithTitle("Rainbow Sprinkles");
    private Catalog catalog;

    @BeforeEach
    public void setUp() {
        ProductQueryFakeAdapter productQueryAdapter = new ProductQueryFakeAdapter();
        productQueryAdapter.load(redVelvet, rainbowSprinkles);
        catalog = new Catalog(productQueryAdapter);
    }

    @Test
    public void canRetrieveAllProducts() {
        List<Product> retrieved = catalog.findAllProducts();

        assertThat(retrieved).as("Retrieved products").containsExactlyInAnyOrder(redVelvet, rainbowSprinkles);
    }
}