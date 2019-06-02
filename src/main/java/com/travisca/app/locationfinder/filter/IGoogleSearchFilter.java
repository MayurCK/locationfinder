package com.travisca.app.locationfinder.filter;

import com.travisca.app.locationfinder.dto.google.Result;

import java.util.function.Predicate;

public interface IGoogleSearchFilter {
    Result filter(Result result, Predicate predicate);
}
