package com.travisca.app.locationfinder.filter;

import com.travisca.app.locationfinder.dto.foursquare.Response;

import java.util.function.Predicate;

public interface IFourSquareFilter {
    Response filter(Response response, Predicate predicate);
}
