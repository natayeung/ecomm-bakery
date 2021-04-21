package com.natay.ecomm.bakery.checkout;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author natayeung
 */
public class OrderDetails {

    private final ShippingDetails shippingDetails;
    private final List<Item> items;
    private final BigDecimal totalPrice;

    private OrderDetails(Builder builder) {
        shippingDetails = builder.shippingDetails;
        items = builder.items;
        totalPrice = builder.totalPrice;
    }

    public List<Item> items() {
        return items;
    }

    public ShippingDetails shippingDetails() {
        return shippingDetails;
    }

    public BigDecimal totalPrice() {
        return totalPrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Item {
        private final String title;
        private final int quantity;
        private final BigDecimal unitPrice;

        private Item(String title, int quantity, BigDecimal unitPrice) {
            this.title = title;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public static Item of(String title, int quantity, BigDecimal unitPrice) {
            return new Item(title, quantity, unitPrice);
        }

        public String title() {
            return title;
        }

        public int quantity() {
            return quantity;
        }

        public BigDecimal unitPrice() {
            return unitPrice;
        }
    }

    public static final class Builder {
        private ShippingDetails shippingDetails;
        private List<Item> items;
        private BigDecimal totalPrice;

        private Builder() {
        }

        public Builder withShippingDetails(ShippingDetails shippingDetails) {
            this.shippingDetails = shippingDetails;
            return this;
        }

        public Builder withItems(List<Item> items) {
            this.items = items;
            return this;
        }

        public Builder withTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public OrderDetails build() {
            return new OrderDetails(this);
        }
    }
}
