package anz.assessment.controller;

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
import org.springframework.web.bind.annotation.RequestHeader;


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
}
