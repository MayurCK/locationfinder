package com.travisca.app.locationfinder.filter.impl;

import com.travisca.app.locationfinder.exception.GenericException;

public class FourSquareCategoryFilterException extends GenericException {
    public FourSquareCategoryFilterException(long errorCode) {
        super(errorCode);
    }

    public FourSquareCategoryFilterException(String s, long errorCode) {
        super(s, errorCode);
    }

    public FourSquareCategoryFilterException(String s, Throwable throwable, long errorCode) {
        super(s, throwable, errorCode);
    }
}
