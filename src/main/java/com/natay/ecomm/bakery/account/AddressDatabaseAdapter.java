package com.natay.ecomm.bakery.account;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author natayeung
 */
@Repository
public class AddressDatabaseAdapter implements AddressPersistencePort {

    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM addresses WHERE email = :email";
    private static final String INSERT_QUERY = "INSERT INTO addresses (email, address_line_1, address_line_2, postcode) VALUES (:email, :addressLine1, :addressLine2, :postcode)";
    private static final String UPDATE_QUERY = "UPDATE addresses SET address_line_1 = :addressLine1, address_line_2 = :addressLine2, postcode = :postcode WHERE email = :email";

    private final RowMapper<UserAddress> rowMapper = userAddressRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AddressDatabaseAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(UserAddress userAddress) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = parameters(userAddress);

        jdbcTemplate.update(INSERT_QUERY, parameters, keyHolder, keyColumnNames());

        return (int) Optional.ofNullable(keyHolder.getKey()).orElse(-1);
    }

    @Override
    public Optional<UserAddress> findByEmail(String email) {
        Map<String, String> parameters = Map.of("email", email);

        List<UserAddress> retrievedAddresses = jdbcTemplate.query(FIND_BY_EMAIL_QUERY, parameters, rowMapper);

        return retrievedAddresses.stream().findFirst();
    }

    @Override
    public void update(UserAddress userAddress) {
        SqlParameterSource parameters = parameters(userAddress);

        jdbcTemplate.update(UPDATE_QUERY, parameters);
    }

    private RowMapper<UserAddress> userAddressRowMapper() {
        return (rs, rowNum) ->
                UserAddress.create()
                        .withEmail(rs.getString("email"))
                        .withAddressLine1(rs.getString("address_line_1"))
                        .withAddressLine2(rs.getString("address_line_2"))
                        .withPostcode(rs.getString("postcode"))
                        .build();
    }

    private String[] keyColumnNames() {
        return new String[]{"address_id"};
    }

    private MapSqlParameterSource parameters(UserAddress userAddress) {
        return new MapSqlParameterSource()
                .addValue("email", userAddress.email())
                .addValue("addressLine1", userAddress.addressLine1())
                .addValue("addressLine2", userAddress.addressLine2())
                .addValue("postcode", userAddress.postcode());
    }
}
