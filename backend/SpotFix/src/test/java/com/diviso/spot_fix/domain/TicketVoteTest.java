package com.diviso.spot_fix.domain;

import static com.diviso.spot_fix.domain.TicketTestSamples.*;
import static com.diviso.spot_fix.domain.TicketVoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.diviso.spot_fix.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketVoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketVote.class);
        TicketVote ticketVote1 = getTicketVoteSample1();
        TicketVote ticketVote2 = new TicketVote();
        assertThat(ticketVote1).isNotEqualTo(ticketVote2);

        ticketVote2.setId(ticketVote1.getId());
        assertThat(ticketVote1).isEqualTo(ticketVote2);

        ticketVote2 = getTicketVoteSample2();
        assertThat(ticketVote1).isNotEqualTo(ticketVote2);
    }

    @Test
    void ticketTest() {
        TicketVote ticketVote = getTicketVoteRandomSampleGenerator();
        Ticket ticketBack = getTicketRandomSampleGenerator();

        ticketVote.setTicket(ticketBack);
        assertThat(ticketVote.getTicket()).isEqualTo(ticketBack);

        ticketVote.ticket(null);
        assertThat(ticketVote.getTicket()).isNull();
    }
}
