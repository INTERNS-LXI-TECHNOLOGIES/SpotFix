package com.divisosofttech.spot_fix.domain;

import static com.divisosofttech.spot_fix.domain.TicketStatusHistoryTestSamples.*;
import static com.divisosofttech.spot_fix.domain.TicketTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.divisosofttech.spot_fix.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketStatusHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketStatusHistory.class);
        TicketStatusHistory ticketStatusHistory1 = getTicketStatusHistorySample1();
        TicketStatusHistory ticketStatusHistory2 = new TicketStatusHistory();
        assertThat(ticketStatusHistory1).isNotEqualTo(ticketStatusHistory2);

        ticketStatusHistory2.setId(ticketStatusHistory1.getId());
        assertThat(ticketStatusHistory1).isEqualTo(ticketStatusHistory2);

        ticketStatusHistory2 = getTicketStatusHistorySample2();
        assertThat(ticketStatusHistory1).isNotEqualTo(ticketStatusHistory2);
    }

    @Test
    void ticketTest() {
        TicketStatusHistory ticketStatusHistory = getTicketStatusHistoryRandomSampleGenerator();
        Ticket ticketBack = getTicketRandomSampleGenerator();

        ticketStatusHistory.setTicket(ticketBack);
        assertThat(ticketStatusHistory.getTicket()).isEqualTo(ticketBack);

        ticketStatusHistory.ticket(null);
        assertThat(ticketStatusHistory.getTicket()).isNull();
    }
}
