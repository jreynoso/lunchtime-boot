package com.dispassionproject.lunchtime.service;

import com.dispassionproject.lunchtime.api.Accessibility;
import com.dispassionproject.lunchtime.api.LunchtimeResponse;

public interface LunchtimeService {

    LunchtimeResponse getLunchtimeOptions(final String loc, final Accessibility mode);

}
