package com.dispassionproject.lunchtime.controller;

import com.dispassionproject.lunchtime.api.Accessibility;
import com.dispassionproject.lunchtime.api.LunchtimeResponse;
import com.dispassionproject.lunchtime.service.LunchtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LunchtimeController {

    public static final String ANY = "*";
    public static final String LOC = "loc";
    public static final String MODE = "mode";

    private final LunchtimeService lunchtimeService;

    @GetMapping("/lunchtime")
    public LunchtimeResponse handleLunchtimeRequest(
            @RequestParam(value = LOC, defaultValue = ANY) final String loc,
            @RequestParam(value = MODE, defaultValue = ANY, required = false) final String mode
    ) {
        return lunchtimeService.getLunchtimeOptions(loc, Accessibility.fromString(mode));
    }

}
