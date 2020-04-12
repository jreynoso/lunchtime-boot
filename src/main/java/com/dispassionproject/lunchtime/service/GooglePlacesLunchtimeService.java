package com.dispassionproject.lunchtime.service;

import com.dispassionproject.lunchtime.api.GeoLocation;
import com.dispassionproject.lunchtime.api.LunchOption;
import com.dispassionproject.lunchtime.api.LunchtimeResponse;
import com.dispassionproject.lunchtime.exception.LunchtimeServiceException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dispassionproject.lunchtime.controller.LunchtimeController.LOC;
import static com.dispassionproject.lunchtime.util.GeoLocationUtil.toGeoLocation;

@Slf4j
@Service
@RequiredArgsConstructor
public class GooglePlacesLunchtimeService implements LunchtimeService {

    @Autowired
    private final GooglePlacesLookupService googlePlacesLookupService;

    @Override
    public LunchtimeResponse getLunchtimeOptions(final String loc) {
        final GeoLocation geoLocation = toGeoLocation(loc);
        Map<String, Object> criteria = Collections.singletonMap(LOC, geoLocation);

        List<LunchOption> lunchOptions;
        try {
            final PlacesSearchResponse response = googlePlacesLookupService.getPlaces(toLatLng(geoLocation));
            lunchOptions = Arrays.stream(response.results)
                    .map(this::toLunchOption)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("API call failed: {}", e.getMessage());
            throw new LunchtimeServiceException("Could not list lunch options");
        }

        return LunchtimeResponse.builder()
                .criteria(criteria)
                .options(lunchOptions)
                .build();
    }

    private LatLng toLatLng(final GeoLocation geoLocation) {
        return new LatLng(geoLocation.getLatitude(), geoLocation.getLongitude());
    }

    private LunchOption toLunchOption(final PlacesSearchResult place) {
        return LunchOption.builder()
                .id(place.placeId)
                .name(place.name)
                .imageUrl(place.icon)
                .address(place.formattedAddress)
                .vicinity(place.vicinity)
                .rating(place.rating)
                .build();
    }

}
