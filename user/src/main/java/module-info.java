module natay.bakery.user {
    requires natay.bakery.common;

    requires java.validation;
    requires spring.context;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.web;
    requires spring.security.oauth2.core;
    requires spring.jdbc;
    requires org.apache.tomcat.embed.core;
    requires org.slf4j;
    requires lombok;

    opens com.natay.ecomm.bakery.user.db.migration;
    opens com.natay.ecomm.bakery.user.authentication;
    opens com.natay.ecomm.bakery.user.account;
    opens com.natay.ecomm.bakery.user.account.persistence;
    opens com.natay.ecomm.bakery.user.registration;

    exports com.natay.ecomm.bakery.user.authentication;
    exports com.natay.ecomm.bakery.user.account;
    exports com.natay.ecomm.bakery.user.registration;
}