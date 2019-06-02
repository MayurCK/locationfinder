package com.travisca.app.locationfinder.service;

import java.util.List;

public interface IDataAggregatorService {

    List<ISearchResponse> aggregate(String city, String category);

}
