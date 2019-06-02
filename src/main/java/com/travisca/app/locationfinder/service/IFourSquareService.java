package com.travisca.app.locationfinder.service;

import com.travisca.app.locationfinder.dto.foursquare.Response;

public interface IFourSquareService extends ISearchService{
    Response callService(String city, String category );
}
