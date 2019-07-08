package alicia.assessment.exception;

import alicia.assessment.exception.error.BusinessRuleError;
import alicia.assessment.exception.error.EnterpriseExceptionSeverity;
import org.springframework.http.HttpStatus;

/**
 * Exception to be thrown when a business rule is violated.
 */
public class BusinessRuleException extends EnterpriseException {

    /**
     * Create {@link BusinessRuleException} from {@link BusinessRuleError}.
     * Default to HTTP 409.
     * @param error BusinessRuleError
     */
    public BusinessRuleException(BusinessRuleError error) {
        this(error, null);
    }

    /**
     * Create {@link BusinessRuleException} from {@link BusinessRuleError}.
     * Default to HTTP 409.
     * @param error BusinessRuleError
     * @param moreInfo The original error message from other system.
     */
    public BusinessRuleException(BusinessRuleError error, String moreInfo) {
        super(HttpStatus.CONFLICT, error.getCode(), error.getMessage(), error.getSeverityLevel(), error.getDescription(), moreInfo);
    }

    /**
     * Create {@link BusinessRuleException} from scratch.
     */
    public BusinessRuleException(HttpStatus httpStatus, String code, String message, EnterpriseExceptionSeverity severityLevel, String description, String moreInfo) {
        super(httpStatus, code, message, severityLevel, description, moreInfo);
    }
}
