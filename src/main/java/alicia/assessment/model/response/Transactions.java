package alicia.assessment.model.response;

import alicia.assessment.model.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class Transactions {
    private List<Transaction> transactions;
}
