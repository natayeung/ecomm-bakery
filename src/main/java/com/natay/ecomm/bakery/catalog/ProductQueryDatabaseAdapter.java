package com.natay.ecomm.bakery.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * @author natayeung
 */
@Repository()
public class ProductQueryDatabaseAdapter implements ProductQueryPort {

    private static final String findAllQuery = "SELECT product_id, title, description, price FROM products";
    private static final String findByIdQuery = "SELECT * FROM products WHERE product_id = ?";
    private final RowMapper<Product> rowMapper = productRowMapper();
    private final JdbcTemplate jdbc;

    @Autowired
    public ProductQueryDatabaseAdapter(DataSource ds) {
        this.jdbc = new JdbcTemplate(ds);
    }

    @Override
    public List<Product> findAll() {
        return jdbc.query(findAllQuery, rowMapper);
    }

    @Override
    public Optional<Product> findById(String id) {
        Product product = jdbc.queryForObject(findByIdQuery, rowMapper, id);

        return Optional.ofNullable(product);
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, row) -> Product.newBuilder()
                .withProductId(rs.getString("product_id"))
                .withTitle(rs.getString("title"))
                .withDescription(rs.getString("description"))
                .withPrice(rs.getBigDecimal("price"))
                .build();
    }
}
