package com.travisca.app.locationfinder.service.impl;

import com.travisca.app.locationfinder.exception.GenericException;

public class FourSquareServiceException extends GenericException {
    public FourSquareServiceException(long errorCode) {
        super(errorCode);
    }

    public FourSquareServiceException(String s, long errorCode) {
        super(s, errorCode);
    }

    public FourSquareServiceException(long errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

    public FourSquareServiceException(String s, Throwable throwable, long errorCode) {
        super(s, throwable, errorCode);
    }
}
