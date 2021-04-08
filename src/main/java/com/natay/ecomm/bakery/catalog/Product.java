package com.natay.ecomm.bakery.catalog;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import static com.natay.ecomm.bakery.utils.Arguments.*;

/**
 * @author natayeung
 */
public class Product {

    private final String id;
    private final Type productType;
    private final String title;
    private final String description;
    private final BigDecimal price;

    private Product(Builder builder) {
        id = requireNonBlank(builder.productId, "Product ID cannot be blank");
        productType = requireNonNull(builder.productType, "Product type must be specified");
        title = requireNonBlank(builder.title, "Title cannot be blank");
        description = Optional.ofNullable(builder.description).orElse("");
        price = validatePrice(builder.price);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String productId() {
        return id;
    }

    public Type productType() {
        return productType;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public BigDecimal price() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", productType=" + productType +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && productType == product.productType && title.equals(product.title) && Objects.equals(description, product.description) && price.equals(product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productType, title, description, price);
    }

    private BigDecimal validatePrice(BigDecimal price) {
        requireNonNull(price, "Price must be specified");
        requireArgument(price.compareTo(BigDecimal.ZERO) >= 0, "Price cannot be negative");
        return price;
    }

    public static final class Builder {
        private String productId;
        private Type productType;
        private String title;
        private String description;
        private BigDecimal price;

        private Builder() {
        }

        public Builder withProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder withProductType(Type productType) {
            this.productType = productType;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    public enum Type {
        WHOLE_CAKE,
        CUPCAKE
    }
}
