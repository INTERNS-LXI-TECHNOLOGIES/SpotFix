package com.diviso.spot_fix.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TicketVoteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static TicketVote getTicketVoteSample1() {
        return new TicketVote().id(1L);
    }

    public static TicketVote getTicketVoteSample2() {
        return new TicketVote().id(2L);
    }

    public static TicketVote getTicketVoteRandomSampleGenerator() {
        return new TicketVote().id(longCount.incrementAndGet());
    }
}
