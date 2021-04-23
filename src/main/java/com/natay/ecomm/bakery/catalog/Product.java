package com.natay.ecomm.bakery.catalog;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Optional;

import static com.natay.ecomm.bakery.utils.Arguments.*;

/**
 * @author natayeung
 */
@Data
@Builder
@Accessors(fluent = true)
public class Product {

    private final String id;
    private final Type productType;
    private final String title;
    private final String description;
    private final BigDecimal price;

    private Product(String id, Type productType, String title, String description, BigDecimal price) {
        this.id = requireNonBlank(id, "Product ID cannot be blank");
        this.productType = requireNonNull(productType, "Product type must be specified");
        this.title = requireNonBlank(title, "Title cannot be blank");
        this.description = Optional.ofNullable(description).orElse("");
        this.price = validatePrice(price);
    }

    private BigDecimal validatePrice(BigDecimal price) {
        requireNonNull(price, "Price must be specified");
        requireArgument(price.compareTo(BigDecimal.ZERO) >= 0, "Price cannot be negative");
        return price;
    }

    public enum Type {
        WHOLE_CAKE,
        CUPCAKE
    }
}
