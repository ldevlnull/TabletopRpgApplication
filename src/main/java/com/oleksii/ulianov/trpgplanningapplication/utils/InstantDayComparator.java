package com.oleksii.ulianov.trpgplanningapplication.utils;

import java.time.Instant;
import java.util.Comparator;

public class InstantDayComparator implements Comparator<Instant> {

    private static final long DAY_SECONDS = 23 * 60 * 60;

    @Override
    public int compare(Instant i1, Instant i2) {
        final long difference = Long.sum(i1.getEpochSecond(), -i2.getEpochSecond());
        return (Math.abs(difference) < DAY_SECONDS)
            ? 0
            : (difference > 0) ? 1 : -1;
    }
}
