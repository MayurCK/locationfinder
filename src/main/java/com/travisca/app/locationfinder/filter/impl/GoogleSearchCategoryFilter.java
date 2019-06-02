package com.travisca.app.locationfinder.filter.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travisca.app.locationfinder.dto.google.Result;
import com.travisca.app.locationfinder.filter.IGoogleSearchFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class is stateless and sole purpose of this class is to provide Category based filtering.
 */
public class GoogleSearchCategoryFilter implements IGoogleSearchFilter {
    private final ObjectMapper objectMapper;

    public GoogleSearchCategoryFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Result filter(Result result, Predicate predicate) {
        List<Result> searchResponses = new ArrayList<>();

        try {
            List<Result> results = objectMapper.convertValue(result.getAdditionalProperties().get("results"), new TypeReference<List<Result>>() {
            });
            for (Result v : results) {
                if (checkCategory(predicate, v)) {
                    searchResponses.add(v);
                }
            }
            result.getAdditionalProperties().put("results", searchResponses);
        } catch (Exception ex) {
            throw new GoogleSearchCategoryFilterException(1004L);
        }

        return result;
    }

    private boolean checkCategory(Predicate predicate, Result v) {
        for (String c : v.getTypes())
            if (predicate.test(c)) {
                return true;
            }
        return false;
    }
}
