package com.travisca.app.locationfinder.exception;

/**
 * Generic Exception to maintain the error catalogue for the application.
 * ErrorCode will be the code used to fetch the correct message for catalogue.
 */
public class GenericException extends RuntimeException {
    private final long errorCode;

    public GenericException(long errorCode) {
        this.errorCode = errorCode;
    }

    public GenericException(long errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;

    }

    public GenericException(String s, long errorCode) {
        super(s);
        this.errorCode = errorCode;
    }

    public GenericException(String s, Throwable throwable, long errorCode) {
        super(s, throwable);
        this.errorCode = errorCode;
    }

    public long getErrorCode() {
        return errorCode;
    }
}
