package anz.assessment.model;

import anz.assessment.config.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {
    private String accountNumber;
    private String accountName;
    private String valueDate;
    private Currency currency;
    private boolean debitFlag;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount;
    private String notes;
}
