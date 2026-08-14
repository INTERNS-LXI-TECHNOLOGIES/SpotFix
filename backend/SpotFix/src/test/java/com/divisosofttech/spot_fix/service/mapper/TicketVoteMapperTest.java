package com.divisosofttech.spot_fix.service.mapper;

import static com.divisosofttech.spot_fix.domain.TicketVoteAsserts.*;
import static com.divisosofttech.spot_fix.domain.TicketVoteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketVoteMapperTest {

    private TicketVoteMapper ticketVoteMapper;

    @BeforeEach
    void setUp() {
        ticketVoteMapper = new TicketVoteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTicketVoteSample1();
        var actual = ticketVoteMapper.toEntity(ticketVoteMapper.toDto(expected));
        assertTicketVoteAllPropertiesEquals(expected, actual);
    }
}
