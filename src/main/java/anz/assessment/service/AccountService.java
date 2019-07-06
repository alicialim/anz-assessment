package anz.assessment.service;

import anz.assessment.exception.BusinessRuleException;
import anz.assessment.exception.error.BusinessRuleError;
import anz.assessment.model.Account;
import anz.assessment.model.response.Accounts;
import anz.assessment.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Gets user's list of accounts by userId
     * @param userId
     * @return List of account(s)
     */
    public Accounts getListOfAccounts(String userId) {
        Optional<List<Account>> optionalAccountList = accountRepository.findAccountsByUserId(userId);
        if (!optionalAccountList.isPresent()) {
            throw new BusinessRuleException(BusinessRuleError.NO_BANK_ACCOUNT, "No accounts found for this user in the database.");
        }

        Accounts accounts = new Accounts();
        accounts.setAccounts(optionalAccountList.get());

        return accounts;
    }
}
