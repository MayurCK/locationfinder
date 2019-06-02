package com.travisca.app.locationfinder.service.impl;

import com.travisca.app.locationfinder.exception.GenericException;
import com.travisca.app.locationfinder.service.IDataAggregatorService;
import com.travisca.app.locationfinder.service.ISearchResponse;
import com.travisca.app.locationfinder.service.ISearchService;

import java.util.ArrayList;
import java.util.List;

public class DataAggregatorService implements IDataAggregatorService {

    private final List<ISearchService> searchServices;

    public DataAggregatorService(List<ISearchService> searchServices) {
        this.searchServices = searchServices;
    }


    public List<ISearchResponse> aggregate(String city, String category) {

        List<ISearchResponse> mergedSearches = new ArrayList<>();
        try {
            for (ISearchService service : searchServices) {
                mergedSearches.add(service.search(city, category));
            }
        } catch (GenericException ex) {
            throw new DataAggregatorServiceException(ex.getErrorCode());
        }

        return mergedSearches;
    }

}
