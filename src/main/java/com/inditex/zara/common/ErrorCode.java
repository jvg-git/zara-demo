package com.inditex.zara.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Errors Code  that can be used by the application uses
 * 
 */
public enum ErrorCode {
    GLOBAL(2), BAD_CREDENTIALS(8),
    ARGUMENT_NOT_VALID(9), BIND_EXCEPTION(10), TYPE_MISMATCH(11),
    MISSING_PART(12), MISSING_PARAMETER(13), ARG_TYPE_MISSMATCH(14),
    CONSTRAIN_VIOLATION(15), NO_HANDLER(16), REQ_METHOD_NOT_SUPPORTED(17),
    MEDIA_TYPE_NOT_SUPPORTED(18), SERVER_ERROR(19), PARAMS_NOT_ALLOWED(20), 
    PARAM_NOT_FOUND(21), PRICES_OBS_ERROR(22), PRICE_UPDATE(23), BAD_PRICE_REQUEST(24), 
    ASYNC_ERROR(25), TOO_MANY_REQUESTS(429), SOAP_ERROR(503);
    
    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }

}