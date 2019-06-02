package com.travisca.app.locationfinder.filter.impl;

import com.travisca.app.locationfinder.exception.GenericException;

public class GoogleSearchCategoryFilterException extends GenericException {
    public GoogleSearchCategoryFilterException(long errorCode) {
        super(errorCode);
    }

    public GoogleSearchCategoryFilterException(String s, long errorCode) {
        super(s, errorCode);
    }

    public GoogleSearchCategoryFilterException(String s, Throwable throwable, long errorCode) {
        super(s, throwable, errorCode);
    }
}
