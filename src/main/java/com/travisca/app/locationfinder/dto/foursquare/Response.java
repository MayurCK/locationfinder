
package com.travisca.app.locationfinder.dto.foursquare;

import com.fasterxml.jackson.annotation.*;
import com.travisca.app.locationfinder.service.ISearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "venues"
})
public class Response implements ISearchResponse {

    @JsonProperty("venues")
    private List<Venue> venues = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("venues")
    public List<Venue> getVenues() {
        return venues;
    }

    @JsonProperty("venues")
    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
