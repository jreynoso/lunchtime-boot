package com.dispassionproject.lunchtime.service;

import com.dispassionproject.lunchtime.api.LunchtimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SimpleLunchtimeService implements LunchtimeService {

    @Override
    public LunchtimeResponse getLunchtimeOptions(String loc) {
        Map<String, String> criteria = Collections.singletonMap("loc", loc);
        return LunchtimeResponse.builder()
                .criteria(criteria)
                .options(Collections.emptyList())
                .build();
    }

}
