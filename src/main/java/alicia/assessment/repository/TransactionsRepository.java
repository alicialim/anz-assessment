package alicia.assessment.repository;

import alicia.assessment.model.Transaction;
import alicia.assessment.repository.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static alicia.assessment.config.CommonConstants.COMMA_SEPARATED_VALUE;
import static alicia.assessment.config.CommonConstants.SINGLE_BLANK_SPACE;

@Repository
@Slf4j
public class TransactionsRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<List<Transaction>> findTransactionsByAccountNumber(String accountNumber) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("accountNumber", accountNumber);

        try {
            final String sql = new StringJoiner(SINGLE_BLANK_SPACE)
                    .add("SELECT " + getAllFieldsFromTransactionsAndAccounts())
                    .add("FROM transactions t")
                    .add("INNER JOIN accounts a")
                    .add("ON t.account_number = a.account_number")
                    .add("WHERE t.account_number = :accountNumber")
                    .toString();
            return Optional.ofNullable(namedParameterJdbcTemplate.query(sql, namedParameters, new TransactionMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    private String getAllFieldsFromTransactionsAndAccounts() {
        return new StringJoiner(COMMA_SEPARATED_VALUE)
                .add("t.account_number")
                .add("a.account_name")
                .add("t.create_datetime")
                .add("t.currency")
                .add("t.amount")
                .add("t.debit_flag")
                .add("t.notes")
                .toString();
    }
}
