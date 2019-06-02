package com.travisca.app.locationfinder.service.impl;

import com.travisca.app.locationfinder.exception.GenericException;

public class DataAggregatorServiceException extends GenericException {
    public DataAggregatorServiceException(long errorCode) {
        super(errorCode);
    }

    public DataAggregatorServiceException(String s, long errorCode) {
        super(s, errorCode);
    }

    public DataAggregatorServiceException(String s, Throwable throwable, long errorCode) {
        super(s, throwable, errorCode);
    }

}
