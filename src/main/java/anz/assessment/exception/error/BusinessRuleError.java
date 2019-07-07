package anz.assessment.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Errors caused by business rule violation.
 */
@Getter
@AllArgsConstructor
public enum BusinessRuleError {

    NO_BANK_ACCOUNT("BE000", "No accounts found", EnterpriseExceptionSeverity.ERROR, "The user does not have any bank accounts");

    private final String code;
    private final String message;
    private final EnterpriseExceptionSeverity severityLevel;
    private final String description;
}