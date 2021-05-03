package com.natay.ecomm.bakery.user.account.persistence;

import com.natay.ecomm.bakery.user.account.Account;
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
    private static final String INSERT_QUERY = "INSERT INTO accounts (email, password, first_name, last_name) VALUES (:email, :password, :firstName, :lastName)";

    private final RowMapper<Account> rowMapper = userAccountRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AccountDatabaseAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("email", account.email())
                .addValue("password", account.password())
                .addValue("firstName", account.firstName())
                .addValue("lastName", account.lastName());

        jdbcTemplate.update(INSERT_QUERY, parameters, keyHolder, keyColumnNames());

        return (int) Optional.ofNullable(keyHolder.getKey()).orElse(-1);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        Map<String, String> parameters = Map.of("email", email);

        List<Account> retrievedAccounts = jdbcTemplate.query(FIND_BY_EMAIL_QUERY, parameters, rowMapper);

        return retrievedAccounts.stream().findFirst();
    }

    private RowMapper<Account> userAccountRowMapper() {
        return (rs, row) -> Account.builder()
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .build();
    }

    private String[] keyColumnNames() {
        return new String[]{"account_id"};
    }
}
