package anz.assessment.model.response;

import anz.assessment.model.Account;
import lombok.Data;

import java.util.List;

@Data
public class Accounts {
    private List<Account> accounts;
}
