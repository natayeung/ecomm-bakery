package com.natay.ecomm.bakery.checkout;

import com.natay.ecomm.bakery.account.Address;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author natayeung
 */
public class OrderDetails {

    private final List<Item> items;
    private final Address shippingAddress;
    private final BigDecimal amount;

    private OrderDetails(Builder builder) {
        items = builder.items;
        shippingAddress = builder.shippingAddress;
        amount = builder.amount;
    }

    public List<Item> items() {
        return items;
    }

    public Address shippingAddress() {
        return shippingAddress;
    }

    public BigDecimal amount() {
        return amount;
    }

    public static Builder builder() {
        return new Builder();
    }

    static class Item {
        private final String title;
        private final int quantity;
        private final BigDecimal unitPrice;

        private Item(String title, int quantity, BigDecimal unitPrice) {
            this.title = title;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        static Item of(String title, int quantity, BigDecimal unitPrice) {
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
        private List<Item> items;
        private Address shippingAddress;
        private BigDecimal amount;

        private Builder() {
        }

        public Builder withItems(List<Item> items) {
            this.items = items;
            return this;
        }

        public Builder withShippingAddress(Address shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public OrderDetails build() {
            return new OrderDetails(this);
        }
    }
}
