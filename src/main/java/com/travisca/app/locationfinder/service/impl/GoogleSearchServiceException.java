package com.travisca.app.locationfinder.service.impl;

import com.travisca.app.locationfinder.exception.GenericException;

public class GoogleSearchServiceException extends GenericException {
    public GoogleSearchServiceException(long errorCode) {
        super(errorCode);
    }

    public GoogleSearchServiceException(long errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
