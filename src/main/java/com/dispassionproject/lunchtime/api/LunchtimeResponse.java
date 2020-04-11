package com.dispassionproject.lunchtime.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class LunchtimeResponse {

    private Map<String, String> criteria;
    private List<LunchOption> options;

}
