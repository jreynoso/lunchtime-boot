package com.dispassionproject.lunchtime.api;

import lombok.Builder;
import lombok.Data;

import java.util.Locale;

@Data
@Builder
public class GeoLocation {

    private final Double latitude;
    private final Double longitude;

    public String toUrlValue() {
        return String.format(Locale.ENGLISH, "%.8f,%.8f", latitude, longitude);
    }

}
