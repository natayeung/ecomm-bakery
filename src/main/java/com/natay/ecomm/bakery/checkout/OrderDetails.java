package com.natay.ecomm.bakery.checkout;

import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author natayeung
 */
@ToString
public class OrderDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final CustomerDetails customerDetails;
    private final ShippingDetails shippingDetails;
    private final List<Item> items;
    private final BigDecimal totalPrice;

    private OrderDetails(Builder builder) {
        customerDetails = builder.customerDetails;
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

    public record Item(String title, int quantity, BigDecimal unitPrice) implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        public static Item of(String title, int quantity, BigDecimal unitPrice) {
            return new Item(title, quantity, unitPrice);
        }
    }

    public static final class Builder {
        private CustomerDetails customerDetails;
        private ShippingDetails shippingDetails;
        private List<Item> items;
        private BigDecimal totalPrice;

        private Builder() {
        }

        public Builder withCustomerDetails(CustomerDetails customerDetails) {
            this.customerDetails = customerDetails;
            return this;
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
