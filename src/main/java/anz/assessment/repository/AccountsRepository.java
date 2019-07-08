package anz.assessment.repository;

import anz.assessment.model.Account;
import anz.assessment.repository.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static anz.assessment.config.CommonConstants.*;

@Repository
@Slf4j
public class AccountsRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<List<Account>> findAccountsByUserId(String userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("userId", userId);

        try {
            final String sql = new StringJoiner(SINGLE_BLANK_SPACE)
                    .add("SELECT " + getAllFieldsWithoutUserId())
                    .add("FROM accounts")
                    .add("WHERE user_id = :userId")
                    .toString();
            return Optional.ofNullable(namedParameterJdbcTemplate.query(sql, namedParameters, new AccountMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public Optional<Account> findAccountsByAccountNumber(String accountNumber) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("accountNumber", accountNumber);

        try {
            final String sql = new StringJoiner(SINGLE_BLANK_SPACE)
                    .add("SELECT " + getAllFieldsWithoutUserId())
                    .add("FROM accounts")
                    .add("WHERE account_number = :accountNumber")
                    .toString();
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new AccountMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public int addAccount(String userId, Account account, String createdDatetime) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("accountNumber", account.getAccountNumber());
        namedParameters.addValue("accountName", account.getAccountName());
        namedParameters.addValue("userId", userId);
        namedParameters.addValue("type", account.getAccountType());
        namedParameters.addValue("balance", account.getBalance());
        namedParameters.addValue("currency", account.getCurrency().name());
        namedParameters.addValue("updatedDatetime", createdDatetime);
        namedParameters.addValue("createdDatetime", createdDatetime);

        try {
            final String sql = new StringJoiner(SINGLE_BLANK_SPACE)
                    .add("INSERT INTO accounts (" + getAllFields() + ")")
                    .add("VALUES (:accountNumber, :accountName, :userId, :type, :balance, :currency, :updatedDatetime, :createdDatetime)")
                    .toString();
            return namedParameterJdbcTemplate.update(sql, namedParameters);
        } catch (Exception ex) {
            log.error("Failed to add account into database : {}", ex);
            return 0;
        }
    }

    private String getAllFields() {
        return new StringJoiner(COMMA_SEPARATED_VALUE)
                .add("account_number")
                .add("account_name")
                .add("user_id")
                .add("type")
                .add("balance")
                .add("currency")
                .add("updated_datetime")
                .add("created_datetime")
                .toString();
    }

    private String getAllFieldsWithoutUserId() {
        return new StringJoiner(COMMA_SEPARATED_VALUE)
                .add("account_number")
                .add("account_name")
                .add("type")
                .add("balance")
                .add("currency")
                .add("updated_datetime")
                .toString();
    }
}
