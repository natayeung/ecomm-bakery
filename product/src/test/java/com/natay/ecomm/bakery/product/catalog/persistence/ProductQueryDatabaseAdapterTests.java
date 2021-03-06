package com.natay.ecomm.bakery.product.catalog.persistence;

import com.natay.ecomm.bakery.product.catalog.Product;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.atIndex;

/**
 * @author natayeung
 */
@JdbcTest
@ContextConfiguration(classes = {ProductQueryDatabaseAdapter.class})
@ActiveProfiles("dev")
@ExtendWith(SoftAssertionsExtension.class)
public class ProductQueryDatabaseAdapterTests {

    @InjectSoftAssertions
    private SoftAssertions softly;

    @Autowired
    private ProductQueryDatabaseAdapter databaseAdapter;

    @Test
    public void shouldRetrieveAllProducts() {
        List<Product> retrieved = databaseAdapter.findAll();

        softly.assertThat(retrieved)
                .extracting(Product::title)
                .contains("Black Forest Cake", atIndex(0))
                .contains("Carrot Cake", atIndex(1))
                .contains("Rainbow Sprinkles Cake", atIndex(2));

        softly.assertThat(retrieved)
                .extracting(Product::price)
                .contains(BigDecimal.valueOf(27.95), atIndex(0))
                .contains(BigDecimal.valueOf(21.95), atIndex(1))
                .contains(BigDecimal.valueOf(24.95), atIndex(2));
    }
}
