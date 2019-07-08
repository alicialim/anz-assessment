package alicia.assessment.service;

import alicia.assessment.exception.BusinessRuleException;
import alicia.assessment.exception.error.BusinessRuleError;
import alicia.assessment.model.Account;
import alicia.assessment.model.Transaction;
import alicia.assessment.model.response.AccountAdded;
import alicia.assessment.model.response.Accounts;
import alicia.assessment.model.response.Transactions;
import alicia.assessment.repository.AccountsRepository;
import alicia.assessment.repository.TransactionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static alicia.assessment.config.CommonConstants.*;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;

    /**
     * Gets user's list of accounts by userId
     * @param userId
     * @return List of account(s)
     */
    public Accounts getListOfAccounts(String userId) {
        Optional<List<Account>> optionalAccountList = accountsRepository.findAccountsByUserId(userId);
        if (!optionalAccountList.isPresent() || optionalAccountList.get().isEmpty()) {
            throw new BusinessRuleException(BusinessRuleError.NO_BANK_ACCOUNT, "No accounts found for this user in the database.");
        }

        Accounts accounts = new Accounts();
        accounts.setAccounts(optionalAccountList.get());

        return accounts;
    }

    /**
     * Gets list of transactions for the user's selected account by account number
     * @param accountNumber
     * @return List of transaction(s)
     */
    public Transactions getListOfTransactions(String accountNumber) {

        if (!accountNumber.matches(ACCOUNT_NUMBER_REGEX)) {
            throw new BusinessRuleException(BusinessRuleError.INVALID_ACCOUNT_NO, "Invalid account number. Unable to obtain transactions for this account.");
        }

        Optional<List<Transaction>> optionalTransactionList = transactionsRepository.findTransactionsByAccountNumber(accountNumber);

        if (!optionalTransactionList.isPresent() || optionalTransactionList.get().isEmpty()) {
            throw new BusinessRuleException(BusinessRuleError.NO_TRANSACTIONS_FOUND, "No transactions found for this account in the database.");
        }

        Transactions transactions = new Transactions();
        transactions.setTransactions(optionalTransactionList.get());

        return transactions;
    }

    /**
     * Adds a new account for the user
     * @param account
     * @return Details of account added
     */
    public AccountAdded addNewAccount(String userId, Account account) {

        String accountNumber = account.getAccountNumber();

        if (!accountNumber.matches(ACCOUNT_NUMBER_REGEX)) {
            throw new BusinessRuleException(BusinessRuleError.INVALID_ACCOUNT_NO, "Invalid account number. Unable to add account into the database.");
        }

        Optional<Account> optionalAccount = accountsRepository.findAccountsByAccountNumber(accountNumber);

        if (optionalAccount.isPresent()) {
            throw new BusinessRuleException(BusinessRuleError.DUPLICATED_ACCOUNT, "Account already exists in the database.");
        }

        Date currentDateTime = new Date();
        DateFormat defaultSqlDateFormatter = new SimpleDateFormat(DEFAULT_SQL_DATETIME_FORMAT);
        String createdDatetime = defaultSqlDateFormatter.format(currentDateTime);
        int result = accountsRepository.addAccount(userId, account, createdDatetime);

        if (result == 0) {
            throw new BusinessRuleException(BusinessRuleError.DATABASE_TRANSACTION_ERROR, "Failed to add the new account into the database.");
        }

        DateFormat defaultDateFormatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        account.setBalanceDate(defaultDateFormatter.format(currentDateTime));

        account.setCreatedDate(createdDatetime);

        AccountAdded accountAdded = new AccountAdded();
        accountAdded.setAccountAdded(account);
        return accountAdded;
    }
}
