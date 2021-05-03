package com.natay.ecomm.bakery.checkout.order;

import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author natayeung
 */
@ToString
public class OrderReceivedEvent extends ApplicationEvent {
    private final OrderDetails orderDetails;

    public OrderReceivedEvent(Object source, OrderDetails orderDetails) {
        super(source);
        this.orderDetails = orderDetails;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }
}
