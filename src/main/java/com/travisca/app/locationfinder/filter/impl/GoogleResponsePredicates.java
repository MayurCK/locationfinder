package com.travisca.app.locationfinder.filter.impl;

import com.travisca.app.locationfinder.dto.foursquare.Category;

import java.util.function.Predicate;

/**
 * Class for handling the predicates for google api service and we can add new predicates in here and
 * maintain the predicates at this common place.
 */
public class GoogleResponsePredicates {

    public static Predicate<Category> isInCategory(String categoryName) {
        return category -> categoryName.equals(category.getName());
    }
}
