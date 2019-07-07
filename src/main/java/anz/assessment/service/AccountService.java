package anz.assessment.service;

import anz.assessment.exception.BusinessRuleException;
import anz.assessment.exception.error.BusinessRuleError;
import anz.assessment.model.Account;
import anz.assessment.model.Transaction;
import anz.assessment.model.response.Accounts;
import anz.assessment.model.response.Transactions;
import anz.assessment.repository.AccountsRepository;
import anz.assessment.repository.TransactionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        String accountRegex = "^([\\d+]{3}\\-)([\\d]{4}\\-)([\\d]{3})$";

        if (!accountNumber.matches(accountRegex)) {
            throw new BusinessRuleException(BusinessRuleError.INVALID_ACCOUNT_NO, "Invalid account number. Unable to obtain transactions for this acccount.");
        }

        Optional<List<Transaction>> optionalTransactionList = transactionsRepository.findTransactionsByAccountNumber(accountNumber);

        if (!optionalTransactionList.isPresent() || optionalTransactionList.get().isEmpty()) {
            throw new BusinessRuleException(BusinessRuleError.NO_TRANSACTIONS_FOUND, "No transactions found for this account in the database.");
        }

        Transactions transactions = new Transactions();
        transactions.setTransactions(optionalTransactionList.get());

        return transactions;
    }
}
