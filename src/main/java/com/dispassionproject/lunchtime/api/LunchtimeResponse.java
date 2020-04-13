package com.dispassionproject.lunchtime.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class LunchtimeResponse {

    private Map<String, Object> criteria;
    private List<LunchOption> options;
    private LunchOption suggestion;

}
