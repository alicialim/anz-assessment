package alicia.assessment.repository.mapper;

import alicia.assessment.model.Account;
import alicia.assessment.model.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static alicia.assessment.config.CommonConstants.DEFAULT_DATE_FORMAT;

public class AccountMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        account.setAccountNumber(resultSet.getString("account_number"));
        account.setAccountName(resultSet.getString("account_name"));
        account.setAccountType(resultSet.getString("type"));

        DateFormat dateFormatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        account.setBalanceDate(dateFormatter.format(resultSet.getDate("updated_datetime")));

        account.setCurrency(Currency.valueOf(resultSet.getString("currency")));
        account.setBalance(resultSet.getBigDecimal("balance"));
        return account;
    }
}
