package anz.assessment.exception;

import anz.assessment.exception.error.EnterpriseExceptionSeverity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Accessors(chain = true)
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseException extends RuntimeException {

    /**
     * HTTP ResponseStatus Code
     */
    @JsonIgnore
    private final HttpStatus httpStatus;

    /**
     * Error code
     */
    private final String code;

    /**
     * A brief message that describes the error.
     */
    private final String message;

    /**
     * Severity level
     */
    @JsonProperty("severitylevel")
    private final EnterpriseExceptionSeverity severityLevel;

    /**
     * A more detailed text that describes the error.
     */
    private final String description;

    /**
     * To store original error message from other system, or finer details of the error.
     */
    private final String moreInfo;

    public EnterpriseException(HttpStatus httpStatus, String code, String message, EnterpriseExceptionSeverity severityLevel, String description, String moreInfo) {
        super(code + ": " + message);
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.severityLevel = severityLevel;
        this.description = description;
        this.moreInfo = moreInfo;
    }

    public EnterpriseException(HttpStatus httpStatus, String code, String message, EnterpriseExceptionSeverity severityLevel, String description, String moreInfo, Throwable cause) {
        super(code + ": " + message, cause);
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.severityLevel = severityLevel;
        this.description = description;
        this.moreInfo = moreInfo;
    }
}
