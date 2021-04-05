package com.natay.ecomm.bakery.user;

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
public class AccountDatabaseAdapter implements AccountPersistencePort {

    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM accounts WHERE email = :email";
    private static final String INSERT_QUERY = "INSERT INTO accounts (email, password) VALUES (:email, :password)";

    private final RowMapper<UserAccount> rowMapper = userAccountRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AccountDatabaseAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(UserAccount userAccount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("email", userAccount.email())
                .addValue("password", userAccount.password());

        jdbcTemplate.update(INSERT_QUERY, parameters, keyHolder, keyColumnNames());

        return (int) Optional.ofNullable(keyHolder.getKey()).orElse(-1);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        Map<String, String> parameters = Map.of("email", email);

        List<UserAccount> retrievedAccounts = jdbcTemplate.query(FIND_BY_EMAIL_QUERY, parameters, rowMapper);

        return retrievedAccounts.stream().findFirst();
    }

    private RowMapper<UserAccount> userAccountRowMapper() {
        return (rs, rowNum) -> new UserAccount(
                rs.getString("email"),
                rs.getString("password"));
    }

    private String[] keyColumnNames() {
        return new String[]{"account_id"};
    }
}
