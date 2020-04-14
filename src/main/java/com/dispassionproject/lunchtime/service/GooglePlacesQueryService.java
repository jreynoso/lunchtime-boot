package com.dispassionproject.lunchtime.service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PriceLevel;
import com.google.maps.model.RankBy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Profile("!integration-test")
@Service
@RequiredArgsConstructor
public class GooglePlacesQueryService {

    private final GeoApiContext geoApiContext;

    public PlacesSearchResponse getPlaces(
            final LatLng loc,
            final int radius
    ) throws InterruptedException, ApiException, IOException {
        return PlacesApi.nearbySearchQuery(geoApiContext, loc)
                .radius(radius)
                .rankby(RankBy.PROMINENCE)
                .language("en")
                .minPrice(PriceLevel.INEXPENSIVE)
                .maxPrice(PriceLevel.EXPENSIVE)
                .openNow(true)
                .type(PlaceType.RESTAURANT)
                .await();
    }

}
