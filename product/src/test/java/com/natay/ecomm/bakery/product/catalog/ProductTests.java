package com.natay.ecomm.bakery.product.catalog;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static com.natay.ecomm.bakery.product.catalog.Product.Type.WHOLE_CAKE;
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
        Product constructed = Product.builder()
                .id(productId)
                .productType(WHOLE_CAKE)
                .title(title)
                .description(description)
                .price(price)
                .build();

        softly.assertThat(constructed.id()).as("product ID").isEqualTo(productId);
        softly.assertThat(constructed.title()).as("title").isEqualTo(title);
        softly.assertThat(constructed.description()).as("description").isEqualTo(description);
        softly.assertThat(constructed.price()).as("price").isEqualTo(price);
    }

    @Test
    public void productIdIsMandatory() {
        assertThatThrownBy(() -> Product.builder()
                .productType(WHOLE_CAKE)
                .title(title)
                .description(description)
                .price(price)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void productTypeIsMandatory() {
        assertThatThrownBy(() -> Product.builder()
                .id(productId)
                .title(title)
                .description(description)
                .price(price)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void titleIsMandatory() {
        assertThatThrownBy(() -> Product.builder()
                .id(productId)
                .productType(WHOLE_CAKE)
                .description(description)
                .price(price)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void priceIsMandatory() {
        assertThatThrownBy(() -> Product.builder()
                .id(productId)
                .productType(WHOLE_CAKE)
                .title(title)
                .description(description)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void priceCannotBeNegative() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Product.builder()
                        .id(productId)
                        .productType(WHOLE_CAKE)
                        .title(title)
                        .description(description)
                        .price(BigDecimal.valueOf(-1.5))
                        .build());
    }

    @Test
    public void descriptionIsOptional() {
        Product constructed = Product.builder()
                .id(productId)
                .productType(WHOLE_CAKE)
                .title(title)
                .price(price)
                .build();

        assertThat(constructed).isInstanceOf(Product.class);
    }
}