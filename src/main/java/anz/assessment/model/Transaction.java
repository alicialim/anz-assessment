package anz.assessment.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {
    private String accountNumber;
    private String accountName;
    private String valueDate;
    private Currency currency;
    private boolean debitFlag;
    private BigDecimal amount;
    private String notes;
}
