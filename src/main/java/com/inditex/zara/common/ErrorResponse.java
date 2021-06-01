package com.inditex.zara.common;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * This class parse Ws Zara error response
 *
 */
public class ErrorResponse {

	/**
	 * HTTP Response Status Code
	 */
    private final HttpStatus status;

    /**
     * General Error message
     */
    private final String message;

    /**
     * Error Code
     */
    private final ErrorCode errorCode;


	/**
	 * List of String of error related info
	 */
    private List<String> errorList;

    /**
     * Timestamp of the Error
     */
    private final Date timestamp;

    protected ErrorResponse(final String message, final ErrorCode errorCode, HttpStatus status, final List<String> errorList) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.errorList = errorList;
        this.timestamp = new java.util.Date();
    };


    protected ErrorResponse(final String message, final ErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = new java.util.Date();
    }


    protected ErrorResponse(final String message, final ErrorCode errorCode, final List<String> errorList) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorList = errorList;
        this.status = HttpStatus.BAD_REQUEST;
        this.timestamp = new java.util.Date();
    }

    /**
     * Creates new generic ErrorResponse object with error message, error code, timestamp of the error and a Bad Request http status.
     * @param message - General description of the message
     * @param errorCode - Code related to  the error
     * @param errorList - List of related info to the error
     * @return - new ErrorResponse object
     */
    public static ErrorResponse of(final String message, final ErrorCode errorCode, List<String> errorList) {
        return new ErrorResponse(message, errorCode, errorList);
    }
    
    /**
     * Creates new ErrorResponse object with error message, error code and http status of the error.
     * @param message - General description of the message
     * @param errorCode - Code related to  the error
     * @param status - {@link HttpStatus} Http status of the code
     * @return - new ErrorResponse object
     */
    public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }
    
    /**
     * Creates new ErrorResponse object with error message, error code, http status, asociated messages details and timestam of the ErrorResponse
     * @param message - General description of the message
     * @param errorCode - Code related to  the error
     * @param status - {@link HttpStatus} Http status of the code
     * @param errorList - List of related info to the error
     * @return new ErrorResponse object
     */
    public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status, List<String> errorList) {
        return new ErrorResponse(message, errorCode, status, errorList);
    }
        

    
    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    
    public List<String> getErrorList() {
    	return this.errorList;
    }


	@Override
	public String toString() {
		return String.format("%s[status: %s, message: %s, errorCode: %s, errorList: %s, timestamp: %s]", super.toString(), status.toString(), message, errorCode, errorList, timestamp.toString());
	}
}
