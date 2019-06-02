package com.travisca.app.locationfinder.service;

public interface ISearchService<T> {
    ISearchResponse search(String city, String category);
}
