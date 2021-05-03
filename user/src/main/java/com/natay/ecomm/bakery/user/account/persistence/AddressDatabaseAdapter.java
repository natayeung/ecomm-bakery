package com.natay.ecomm.bakery.user.account.persistence;

import com.natay.ecomm.bakery.user.account.Address;
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
    private static final String INSERT_QUERY = "INSERT INTO addresses (email, address_line_1, address_line_2, town_or_city, postcode) VALUES (:email, :addressLine1, :addressLine2, :townOrCity,:postcode)";
    private static final String UPDATE_QUERY = "UPDATE addresses SET address_line_1 = :addressLine1, address_line_2 = :addressLine2, town_or_city = :townOrCity, postcode = :postcode WHERE email = :email";

    private final RowMapper<Address> rowMapper = userAddressRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AddressDatabaseAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(Address address) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = parameters(address);

        jdbcTemplate.update(INSERT_QUERY, parameters, keyHolder, keyColumnNames());

        return (int) Optional.ofNullable(keyHolder.getKey()).orElse(-1);
    }

    @Override
    public Optional<Address> findByEmail(String email) {
        Map<String, String> parameters = Map.of("email", email);

        List<Address> retrievedAddresses = jdbcTemplate.query(FIND_BY_EMAIL_QUERY, parameters, rowMapper);

        return retrievedAddresses.stream().findFirst();
    }

    @Override
    public void update(Address address) {
        SqlParameterSource parameters = parameters(address);

        jdbcTemplate.update(UPDATE_QUERY, parameters);
    }

    private RowMapper<Address> userAddressRowMapper() {
        return (rs, row) ->
                Address.builder()
                        .email(rs.getString("email"))
                        .addressLine1(rs.getString("address_line_1"))
                        .addressLine2(rs.getString("address_line_2"))
                        .townOrCity(rs.getString("town_or_city"))
                        .postcode(rs.getString("postcode"))
                        .build();
    }

    private String[] keyColumnNames() {
        return new String[]{"address_id"};
    }

    private MapSqlParameterSource parameters(Address address) {
        return new MapSqlParameterSource()
                .addValue("email", address.email())
                .addValue("addressLine1", address.addressLine1())
                .addValue("addressLine2", address.addressLine2())
                .addValue("townOrCity", address.townOrCity())
                .addValue("postcode", address.postcode());
    }
}
