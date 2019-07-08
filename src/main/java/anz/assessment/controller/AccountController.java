package anz.assessment.controller;

import anz.assessment.model.Account;
import anz.assessment.model.response.AccountAdded;
import anz.assessment.model.response.Accounts;
import anz.assessment.model.response.Transactions;
import anz.assessment.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;


@Controller
@Slf4j
public class AccountController {

    @Autowired
    private HttpHeaders httpHeaders;
    @Autowired
    private AccountService accountService;

    /**
     * An endpoint to get user's list of accounts
     *
     * @param userId  User ID
     * @return JSON
     */
    @GetMapping(value = "/v1/accounts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Accounts> getListOfAccounts(
            @RequestHeader String userId
    ) {
        return new ResponseEntity<>(
                accountService.getListOfAccounts(userId),
                httpHeaders,
                HttpStatus.OK
        );
    }

    /**
     * An endpoint to get an account's list of transactions
     *
     * @param userId  User ID
     * @param accountNumber  Account number in which the transactions the user expects to see
     * @return JSON
     */
    @GetMapping(value = "/v1/account/transactions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Transactions> getListOfTransactions(
            @RequestHeader String userId,
            @RequestHeader String accountNumber
    ) {
        return new ResponseEntity<>(
                accountService.getListOfTransactions(accountNumber),
                httpHeaders,
                HttpStatus.OK
        );
    }

    /**
     * An endpoint to add an account for the user
     *
     * @param userId  User ID
     * @param account  New account to be added for user
     * @return JSON
     */
    @PostMapping(value = "/v1/account", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AccountAdded> addNewAccount(
            @RequestHeader String userId,
            @RequestBody @Valid Account account
    ) {
        return new ResponseEntity<>(
                accountService.addNewAccount(userId, account),
                httpHeaders,
                HttpStatus.OK
        );
    }
}
