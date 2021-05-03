package com.natay.ecomm.bakery.checkout.payment.paypal;

import com.natay.ecomm.bakery.checkout.order.OrderDetails;
import com.natay.ecomm.bakery.checkout.payment.OrderCaptured;
import com.natay.ecomm.bakery.checkout.payment.OrderCreated;

/**
 * @author natayeung
 */
public interface PayPalCheckoutPort {

    OrderCreated createOrder(OrderDetails orderDetails);

    OrderCaptured captureOrder(String orderId);
}
