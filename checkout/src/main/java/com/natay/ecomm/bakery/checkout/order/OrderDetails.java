package com.natay.ecomm.bakery.checkout.order;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static com.natay.ecomm.bakery.common.Arguments.*;

/**
 * @author natayeung
 */
@Data
@Builder
@Accessors(fluent = true)
public class OrderDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ShippingDetails shippingDetails;
    private final List<Item> items;
    private final BigDecimal totalPrice;

    private OrderDetails(ShippingDetails shippingDetails, List<Item> items, BigDecimal totalPrice) {
        this.shippingDetails = requireNonNull(shippingDetails, "Shipping details cannot be null");
        this.items = requireNonEmpty(items, "Items cannot be empty");
        this.totalPrice = requireNonNegative(totalPrice, "Total price cannot be null or negative");
    }

    public record Item(String title, int quantity, BigDecimal unitPrice) implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        public static Item of(String title, int quantity, BigDecimal unitPrice) {
            return new Item(title, quantity, unitPrice);
        }
    }
}
