package anz.assessment.model;

import anz.assessment.config.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private String accountNumber;
    private String accountName;
    private String accountType;
    private String balanceDate;
    private Currency currency;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal balance;
}
