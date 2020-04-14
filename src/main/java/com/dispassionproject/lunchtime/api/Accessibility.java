package com.dispassionproject.lunchtime.api;

import java.util.Locale;

public enum Accessibility {

    WALK(1000),
    SCOOT(3000),
    DRIVE(10000);

    public final int radius;

    Accessibility(final int radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.US);
    }

    public static Accessibility fromString(final String modeString) {
        Accessibility mode = DRIVE;
        try {
            mode = valueOf(modeString.toUpperCase(Locale.US));
        } catch (IllegalArgumentException ignored) {}
        return mode;
    }

}
