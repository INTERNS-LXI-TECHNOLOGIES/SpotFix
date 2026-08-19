package com.diviso.spot_fix.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Location getLocationSample1() {
        return new Location().id(1L).addressText("addressText1").landmark("landmark1");
    }

    public static Location getLocationSample2() {
        return new Location().id(2L).addressText("addressText2").landmark("landmark2");
    }

    public static Location getLocationRandomSampleGenerator() {
        return new Location()
            .id(longCount.incrementAndGet())
            .addressText(UUID.randomUUID().toString())
            .landmark(UUID.randomUUID().toString());
    }
}
