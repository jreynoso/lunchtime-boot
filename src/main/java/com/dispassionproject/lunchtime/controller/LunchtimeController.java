package com.dispassionproject.lunchtime.controller;

import com.dispassionproject.lunchtime.api.LunchtimeResponse;
import com.dispassionproject.lunchtime.service.LunchtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LunchtimeController {

    private final LunchtimeService lunchtimeService;

    @GetMapping("/lunchtime")
    public LunchtimeResponse handleLunchtimeRequest(
            @RequestParam(value = "loc", defaultValue = "*") String loc
    ) {
        return lunchtimeService.getLunchtimeOptions(loc);
    }

}
