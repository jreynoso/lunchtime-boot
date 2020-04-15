package com.dispassionproject.lunchtime.service;

import com.dispassionproject.lunchtime.api.Accessibility;
import com.dispassionproject.lunchtime.api.GeoLocation;
import com.dispassionproject.lunchtime.api.LunchOption;
import com.dispassionproject.lunchtime.api.LunchtimeResponse;
import com.dispassionproject.lunchtime.exception.LunchtimeServiceException;
import com.google.common.collect.ImmutableMap;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static com.dispassionproject.lunchtime.controller.LunchtimeController.LOC;
import static com.dispassionproject.lunchtime.controller.LunchtimeController.MODE;
import static com.dispassionproject.lunchtime.util.GeoLocationUtil.toGeoLocation;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultLunchtimeService implements LunchtimeService {

    private final GooglePlacesQueryService queryService;

    @Override
    public LunchtimeResponse getLunchtimeOptions(final String loc, final Accessibility mode) {
        final GeoLocation geoLocation = toGeoLocation(loc);
        Map<String, Object> criteria = ImmutableMap.of(
                LOC, geoLocation,
                MODE, mode
        );

        List<LunchOption> lunchOptions;
        try {
            final PlacesSearchResponse response = queryService.getPlaces(toLatLng(geoLocation), mode.radius);
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
                .suggestion(getSuggestion(lunchOptions))
                .build();
    }

    private LunchOption getSuggestion(final List<LunchOption> lunchOptions) {
        int size = lunchOptions.size();
        return size > 0 ? lunchOptions.get(new Random().nextInt(size)) : null;
    }

    private LatLng toLatLng(final GeoLocation geoLocation) {
        return new LatLng(geoLocation.getLatitude(), geoLocation.getLongitude());
    }

    private LunchOption toLunchOption(final PlacesSearchResult place) {
        return LunchOption.builder()
                .id(place.placeId)
                .name(place.name)
                .imageUrl(place.icon)
                .address(place.vicinity)
                .rating(place.rating)
                .build();
    }

}
