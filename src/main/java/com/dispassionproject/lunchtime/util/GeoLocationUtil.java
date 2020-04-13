package com.dispassionproject.lunchtime.util;

import com.dispassionproject.lunchtime.api.GeoLocation;
import com.dispassionproject.lunchtime.exception.LunchtimeServiceException;
import groovy.util.logging.Slf4j;

import static com.dispassionproject.lunchtime.controller.LunchtimeController.ANY;

@Slf4j
public class GeoLocationUtil {

    public static GeoLocation toGeoLocation(final String loc) throws LunchtimeServiceException {
        if (ANY.equals(loc)) {
            return getDefaultLocation();
        }
        try {
            final String[] coordinates = loc.split(",");
            if (coordinates.length >= 2) {
                return GeoLocation.builder()
                        .latitude(getLatitude(coordinates[0]))
                        .longitude(getLongitude(coordinates[1]))
                        .build();
            }
        } catch (Exception ignored) {}

        throw new LunchtimeServiceException(String.format("Unable to parse geo location from: %s", loc));
    }

    private static GeoLocation getDefaultLocation() {
        return GeoLocation.builder()
                .latitude(30.2773768)
                .longitude(-97.7513579)
                .build();
    }

    private static Double getLatitude(String value) {
        double lat = Double.parseDouble(value);
        if (Math.abs(lat) > 90d) {
            throw new LunchtimeServiceException(String.format("Unable to parse latitude from: %s", value));
        }
        return lat;
    }

    private static Double getLongitude(String value) {
        double lat = Double.parseDouble(value);
        if (Math.abs(lat) > 180d) {
            throw new LunchtimeServiceException(String.format("Unable to parse longitude from: %s", value));
        }
        return lat;
    }

}