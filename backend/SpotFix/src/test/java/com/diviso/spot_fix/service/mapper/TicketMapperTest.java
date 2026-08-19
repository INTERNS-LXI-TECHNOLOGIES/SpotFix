package com.diviso.spot_fix.service.mapper;

import static com.diviso.spot_fix.domain.TicketAsserts.*;
import static com.diviso.spot_fix.domain.TicketTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketMapperTest {

    private TicketMapper ticketMapper;

    @BeforeEach
    void setUp() {
        ticketMapper = new TicketMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTicketSample1();
        var actual = ticketMapper.toEntity(ticketMapper.toDto(expected));
        assertTicketAllPropertiesEquals(expected, actual);
    }
}
