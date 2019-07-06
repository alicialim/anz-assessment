package anz.assessment.repository;

import anz.assessment.model.Account;
import anz.assessment.repository.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static anz.assessment.config.CommonConstants.COMMA_SEPARATED_VALUE;
import static anz.assessment.config.CommonConstants.SINGLE_BLANK_SPACE;

@Repository
@Slf4j
public class AccountRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<List<Account>> findAccountsByUserId(String userId){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("userId", userId);

        try {
            final String sql = new StringJoiner(SINGLE_BLANK_SPACE)
                    .add("SELECT " + getAllFieldsWithoutUserId())
                    .add("FROM account")
                    .add("WHERE user_id = :userId")
                    .toString();
            return Optional.of(namedParameterJdbcTemplate.query(sql, namedParameters, new AccountMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    private String getAllFieldsWithoutUserId() {
        return new StringJoiner(COMMA_SEPARATED_VALUE)
                .add("number")
                .add("name")
                .add("type")
                .add("balance")
                .add("currency")
                .add("updated_datetime")
                .toString();
    }
}
