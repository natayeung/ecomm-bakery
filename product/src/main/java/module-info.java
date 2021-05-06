module natay.bakery.product {
    requires natay.bakery.common;

    requires java.sql;
    requires java.annotation;
    requires spring.context;
    requires spring.web;
    requires spring.tx;
    requires spring.jdbc;
    requires org.slf4j;
    requires lombok;

    opens com.natay.ecomm.bakery.product.db.migration;
    opens com.natay.ecomm.bakery.product.catalog;
    opens com.natay.ecomm.bakery.product.catalog.persistence;
    opens com.natay.ecomm.bakery.product.basket;

    exports com.natay.ecomm.bakery.product.basket;
    exports com.natay.ecomm.bakery.product.catalog;
}