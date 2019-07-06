package anz.assessment.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Errors caused by business rule violation.
 */
@Getter
@AllArgsConstructor
public enum BusinessRuleError {

//    INVALID_BANK_ACCOUNT_FORMAT("BU006","Invalid bank account",EnterpriseExceptionSeverity.ERROR,"The back account's format is incorrect"),
    NO_BANK_ACCOUNT("BE000", "No accounts found", EnterpriseExceptionSeverity.ERROR, "The user does not have any bank accounts");
//    BANK_ACCOUNT_STATUS_NOT_VALID("BU008", "Invalid bank account", EnterpriseExceptionSeverity.ERROR, "The bank account status is invalid");

    private final String code;
    private final String message;
    private final EnterpriseExceptionSeverity severityLevel;
    private final String description;
}