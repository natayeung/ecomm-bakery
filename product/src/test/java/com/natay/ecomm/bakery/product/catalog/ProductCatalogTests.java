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
import java.util.Optional;

import static com.natay.ecomm.bakery.product.testutil.ProductFactory.createCupcakeWithTitle;
import static com.natay.ecomm.bakery.product.testutil.ProductFactory.createWholeCakeWithTitle;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
@SpringBootTest(classes = ProductCatalog.class)
@Import(ProductCatalogTests.TestConfig.class)
public class ProductCatalogTests {

    private static final Product redVelvet = createWholeCakeWithTitle("Red Velvet Cake");
    private static final Product rainbowSprinkles = createWholeCakeWithTitle("Rainbow Sprinkles Cake");
    private static final Product chocolateCupcake = createCupcakeWithTitle("Chocolate Cupcake");

    @Autowired
    private ProductQueryPort productQueryPort;

    @Autowired
    private ProductCatalog productCatalog;

    @TestConfiguration
    static class TestConfig {
        @Bean
        ProductQueryPort productQueryAdapter() {
            ProductQueryFakeAdapter productQueryAdapter = new ProductQueryFakeAdapter();
            productQueryAdapter.load(redVelvet, rainbowSprinkles, chocolateCupcake);
            return productQueryAdapter;
        }
    }

    @Test
    public void canRetrieveAllProducts() throws ProductAccessException {
        List<Product> retrieved = productCatalog.findAllProducts();

        assertThat(retrieved).as("Retrieved products")
                .containsExactly(rainbowSprinkles, redVelvet, chocolateCupcake);
    }

    @Test
    public void canRetrieveWholeCakesOnly() {
        List<Product> retrieved = productCatalog.findProductByType(Product.Type.WHOLE_CAKE);

        assertThat(retrieved).as("Retrieved whole cakes")
                .containsExactly(rainbowSprinkles, redVelvet)
                .doesNotContain(chocolateCupcake);
    }

    @Test
    public void canRetrieveCupcakesOnly() {
        List<Product> retrieved = productCatalog.findProductByType(Product.Type.CUPCAKE);

        assertThat(retrieved).as("Retrieved cupcakes")
                .containsExactly(chocolateCupcake)
                .doesNotContain(rainbowSprinkles, redVelvet);
    }

    @Test
    public void canRetrieveProductById() {
        String productId = redVelvet.id();

        Optional<Product> retrieved = productCatalog.findProductById(productId);

        assertThat(retrieved).contains(redVelvet);
    }

    @Test
    public void returnsEmptyIfProductDoesNotExist() {
        String productId = "does.not.exist";

        Optional<Product> retrieved = productCatalog.findProductById(productId);

        assertThat(retrieved).isEmpty();
    }
}