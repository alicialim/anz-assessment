package alicia.assessment.model;

import alicia.assessment.config.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String accountName;
    @NotBlank
    private String accountType;
    private String balanceDate;
    @NotNull
    private Currency currency;
    @JsonSerialize(using = MoneySerializer.class)
    @NotNull
    private BigDecimal balance;
    private String createdDate;
}
