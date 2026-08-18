package com.diviso.spot_fix.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class WorkPlanTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static WorkPlan getWorkPlanSample1() {
        return new WorkPlan().id(1L).completionPercentage(1);
    }

    public static WorkPlan getWorkPlanSample2() {
        return new WorkPlan().id(2L).completionPercentage(2);
    }

    public static WorkPlan getWorkPlanRandomSampleGenerator() {
        return new WorkPlan().id(longCount.incrementAndGet()).completionPercentage(intCount.incrementAndGet());
    }
}
