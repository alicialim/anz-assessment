package anz.assessment.model.response;

import anz.assessment.model.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class Transactions {
    private List<Transaction> transactions;
}
