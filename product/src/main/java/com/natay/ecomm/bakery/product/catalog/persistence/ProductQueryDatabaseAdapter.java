package com.natay.ecomm.bakery.product.catalog.persistence;

import com.natay.ecomm.bakery.product.catalog.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.natay.ecomm.bakery.common.Arguments.requireNonNull;

/**
 * @author natayeung
 */
@Repository()
public class ProductQueryDatabaseAdapter implements ProductQueryPort {

    private static final String FIND_ALL_QUERY = "SELECT product_id, product_type, title, description, price FROM products";
    private static final String FIND_BY_TYPE_QUERY = "SELECT * FROM products WHERE product_type = :productType";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM products WHERE product_id = :productId";

    private final RowMapper<Product> rowMapper = productRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductQueryDatabaseAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, rowMapper);
    }

    @Override
    public List<Product> findByType(Product.Type type) {
        requireNonNull(type, "Product type must be specified");

        Map<String, String> parameters = Map.of("productType", type.toString());

        return jdbcTemplate.query(FIND_BY_TYPE_QUERY, parameters, rowMapper);
    }

    @Override
    public Optional<Product> findById(String id) {
        requireNonNull(id, "Product ID must be specified");

        Map<String, String> parameters = Map.of("productId", id);
        List<Product> retrievedProducts = jdbcTemplate.query(FIND_BY_ID_QUERY, parameters, rowMapper);

        return retrievedProducts.stream().findFirst();
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, row) -> Product.builder()
                .id(rs.getString("product_id"))
                .productType(Product.Type.valueOf(rs.getString("product_type")))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .build();
    }
}
