package com.divisosofttech.spot_fix.service.mapper;

import static com.divisosofttech.spot_fix.domain.TicketStatusHistoryAsserts.*;
import static com.divisosofttech.spot_fix.domain.TicketStatusHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketStatusHistoryMapperTest {

    private TicketStatusHistoryMapper ticketStatusHistoryMapper;

    @BeforeEach
    void setUp() {
        ticketStatusHistoryMapper = new TicketStatusHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTicketStatusHistorySample1();
        var actual = ticketStatusHistoryMapper.toEntity(ticketStatusHistoryMapper.toDto(expected));
        assertTicketStatusHistoryAllPropertiesEquals(expected, actual);
    }
}
