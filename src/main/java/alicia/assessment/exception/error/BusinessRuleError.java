package alicia.assessment.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Errors caused by business rule violation.
 */
@Getter
@AllArgsConstructor
public enum BusinessRuleError {

    NO_BANK_ACCOUNT("BE000", "No accounts found", EnterpriseExceptionSeverity.ERROR, "The user does not have any bank accounts"),
    INVALID_ACCOUNT_NO("BE001", "Invalid account number", EnterpriseExceptionSeverity.ERROR, "The account number is invalid"),
    NO_TRANSACTIONS_FOUND("BE002", "No transactions found", EnterpriseExceptionSeverity.WARN, "There are no transactions for this account"),
    DUPLICATED_ACCOUNT("BE003", "Duplicated account found", EnterpriseExceptionSeverity.ERROR, "This account has been previously added"),
    DATABASE_TRANSACTION_ERROR("BE004", "Database transaction failed", EnterpriseExceptionSeverity.ERROR, "Unable to complete the database transaction");

    private final String code;
    private final String message;
    private final EnterpriseExceptionSeverity severityLevel;
    private final String description;
}