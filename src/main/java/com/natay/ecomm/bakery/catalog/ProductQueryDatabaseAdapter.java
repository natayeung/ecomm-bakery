package com.natay.ecomm.bakery.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component()
public class ProductQueryDatabaseAdapter implements ProductQueryPort {

    private static final String queryForAllProducts = "SELECT product_id, title, description, price FROM products";
    private final JdbcTemplate jdbc;

    @Autowired
    public ProductQueryDatabaseAdapter(DataSource ds) {
        this.jdbc = new JdbcTemplate(ds);
    }

    @Override
    public List<Product> findAll() {
        return jdbc.query(queryForAllProducts,
                (rs, row) -> Product.newBuilder()
                        .withProductId(rs.getString("product_id"))
                        .withTitle(rs.getString("title"))
                        .withDescription(rs.getString("description"))
                        .withPrice(Price.of(rs.getDouble("price")))
                        .build()
        );
    }
}
