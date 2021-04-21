package com.natay.ecomm.bakery.checkout;

/**
 * @author natayeung
 */
public interface PayPalCheckoutPort {

    OrderCreated createOrder(OrderDetails orderDetails);

    OrderCaptured captureOrder(String orderId);
}
