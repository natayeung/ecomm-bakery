package com.natay.ecomm.bakery.catalog;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * @author natayeung
 */
@ExtendWith(SoftAssertionsExtension.class)
public class ProductTests {

    private final String productId = "C-RV";
    private final String title = "Red Velvet";
    private final String description = "Classic red velvet sponge with layers of cream cheese frosting.";
    private final BigDecimal price = BigDecimal.valueOf(22.59);

    @InjectSoftAssertions
    private SoftAssertions softly;

    @Test
    public void productCanBeConstructed() {
        Product constructed = Product.newBuilder()
                .withProductId(productId)
                .withTitle(title)
                .withDescription(description)
                .withPrice(price)
                .build();

        softly.assertThat(constructed.productId()).as("product ID").isEqualTo(productId);
        softly.assertThat(constructed.title()).as("title").isEqualTo(title);
        softly.assertThat(constructed.description()).as("description").isEqualTo(description);
        softly.assertThat(constructed.price()).as("price").isEqualTo(price);
    }

    @Test
    public void productIdIsMandatory() {
        assertThatThrownBy(() -> {
            Product.newBuilder()
                    .withTitle(title)
                    .withDescription(description)
                    .withPrice(price)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void titleIsMandatory() {
        assertThatThrownBy(() -> {
            Product.newBuilder()
                    .withProductId(productId)
                    .withDescription(description)
                    .withPrice(price)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void priceIsMandatory() {
        assertThatThrownBy(() -> {
            Product.newBuilder()
                    .withProductId(productId)
                    .withTitle(title)
                    .withDescription(description)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void priceCannotBeNegative() {
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        Product.newBuilder()
                                .withProductId(productId)
                                .withTitle(title)
                                .withDescription(description)
                                .withPrice(BigDecimal.valueOf(-1.5))
                                .build());
    }

    @Test
    public void descriptionIsOptional() {
        Product constructed = Product.newBuilder()
                .withProductId(productId)
                .withTitle(title)
                .withPrice(price)
                .build();

        assertThat(constructed).isInstanceOf(Product.class);
    }
}