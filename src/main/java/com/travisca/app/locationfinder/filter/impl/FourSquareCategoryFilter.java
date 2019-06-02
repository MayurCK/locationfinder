package com.travisca.app.locationfinder.filter.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travisca.app.locationfinder.dto.foursquare.Category;
import com.travisca.app.locationfinder.dto.foursquare.Response;
import com.travisca.app.locationfinder.dto.foursquare.Venue;
import com.travisca.app.locationfinder.filter.IFourSquareFilter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class FourSquareCategoryFilter implements IFourSquareFilter {
    private final ObjectMapper objectMapper;

    public FourSquareCategoryFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Response filter(Response response, Predicate predicate) {

        List<Venue> searchResponses = new ArrayList<>();
        try {
            Map<String, LinkedHashMap> responseAdditional = (Map<String, LinkedHashMap>) response.getAdditionalProperties().get("response");
            List<Venue> venues = objectMapper.convertValue(responseAdditional.get("venues"), new TypeReference<List<Venue>>() {
            });
            for (Venue v : venues) {
                if (checkCategory(predicate, v)) {
                    searchResponses.add(v);
                }
            }
            response.setVenues(searchResponses);
        } catch (Exception ex) {
            throw new FourSquareCategoryFilterException(1003L);
        }

        return response;
    }

    private boolean checkCategory(Predicate predicate, Venue v) {
        for (Category c : v.getCategories())
            if (predicate.test(c)) {
                return true;
            }
        return false;
    }
}
