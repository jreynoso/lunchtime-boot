package com.dispassionproject.lunchtime.service;

import com.dispassionproject.lunchtime.api.LunchtimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

import static com.dispassionproject.lunchtime.controller.LunchtimeController.LOC;
import static com.dispassionproject.lunchtime.util.GeoLocationUtil.toGeoLocation;

@Profile("integration-test")
@Service
@RequiredArgsConstructor
public class DummyLunchtimeService implements LunchtimeService {

    @Override
    public LunchtimeResponse getLunchtimeOptions(final String loc) {
        Map<String, Object> criteria = Collections.singletonMap(LOC, toGeoLocation(loc));
        return LunchtimeResponse.builder()
                .criteria(criteria)
                .options(Collections.emptyList())
                .build();
    }

}
