package alicia.assessment.model.response;

import alicia.assessment.model.Account;
import lombok.Data;

import java.util.List;

@Data
public class Accounts {
    private List<Account> accounts;
}
