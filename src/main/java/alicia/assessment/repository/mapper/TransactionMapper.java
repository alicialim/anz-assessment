package alicia.assessment.repository.mapper;

import alicia.assessment.model.Currency;
import alicia.assessment.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static alicia.assessment.config.CommonConstants.DATE_FORMAT_WITH_MMM;

public class TransactionMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(resultSet.getString("account_number"));
        transaction.setAccountName(resultSet.getString("account_name"));

        DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_WITH_MMM);
        transaction.setValueDate(dateFormatter.format(resultSet.getDate("create_datetime")));
        transaction.setCurrency(Currency.valueOf(resultSet.getString("currency")));
        transaction.setDebitFlag(resultSet.getBoolean("debit_flag"));
        transaction.setAmount(resultSet.getBigDecimal("amount"));
        transaction.setNotes(resultSet.getString("notes"));

        return transaction;
    }
}
