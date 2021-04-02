package com.natay.ecomm.bakery.catalog;

import java.util.Objects;

import static com.natay.ecomm.bakery.utils.Arguments.requireNonBlank;
import static com.natay.ecomm.bakery.utils.Arguments.requireNonNull;

/**
 * @author natayeung
 */
public class Product {

    private final String id;
    private final String title;
    private final String description;
    private final Price price;

    private Product(Builder builder) {
        id = requireNonBlank(builder.productId, "Product ID cannot be blank");
        title = requireNonBlank(builder.title, "Title cannot be blank");
        description = builder.description == null ? "" : builder.description;
        price = requireNonNull(builder.price, "Price must be specified");
    }

    public String productId() {
        return id;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public Price price() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + id + '\'' +
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
        return id.equals(product.id) && title.equals(product.title) && description.equals(product.description) && price.equals(product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String productId;
        private String title;
        private String description;
        private Price price;

        private Builder() {
        }

        public Builder withProductId(String productId) {
            this.productId = productId;
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

        public Builder withPrice(Price price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
