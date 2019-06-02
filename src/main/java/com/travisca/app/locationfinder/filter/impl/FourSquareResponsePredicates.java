package com.travisca.app.locationfinder.filter.impl;

import com.travisca.app.locationfinder.dto.foursquare.Category;

import java.util.function.Predicate;

public class FourSquareResponsePredicates {

    public static Predicate<Category> isInCategory(String categoryName) {
        return category -> categoryName.equals(category.getName());
    }
}
