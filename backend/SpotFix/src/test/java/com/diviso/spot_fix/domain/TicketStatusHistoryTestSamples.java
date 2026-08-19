package com.diviso.spot_fix.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TicketStatusHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static TicketStatusHistory getTicketStatusHistorySample1() {
        return new TicketStatusHistory().id(1L);
    }

    public static TicketStatusHistory getTicketStatusHistorySample2() {
        return new TicketStatusHistory().id(2L);
    }

    public static TicketStatusHistory getTicketStatusHistoryRandomSampleGenerator() {
        return new TicketStatusHistory().id(longCount.incrementAndGet());
    }
}
