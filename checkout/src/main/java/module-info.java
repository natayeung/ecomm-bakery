module natay.bakery.checkout {
    requires natay.bakery.common;
    requires natay.bakery.user;
    requires natay.bakery.product;

    requires java.validation;
    requires spring.context;
    requires spring.boot;
    requires org.slf4j;
    requires lombok;
    requires paypalhttp;
    requires checkout.sdk;

    opens com.natay.ecomm.bakery.checkout.payment;
    opens com.natay.ecomm.bakery.checkout.payment.paypal;
    opens com.natay.ecomm.bakery.checkout.order;

    exports com.natay.ecomm.bakery.checkout.payment;
    exports com.natay.ecomm.bakery.checkout.order;
    exports com.natay.ecomm.bakery.checkout;
}