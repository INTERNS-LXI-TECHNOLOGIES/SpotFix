package com.divisosofttech.spot_fix.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.divisosofttech.spot_fix.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketVoteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketVoteDTO.class);
        TicketVoteDTO ticketVoteDTO1 = new TicketVoteDTO();
        ticketVoteDTO1.setId(1L);
        TicketVoteDTO ticketVoteDTO2 = new TicketVoteDTO();
        assertThat(ticketVoteDTO1).isNotEqualTo(ticketVoteDTO2);
        ticketVoteDTO2.setId(ticketVoteDTO1.getId());
        assertThat(ticketVoteDTO1).isEqualTo(ticketVoteDTO2);
        ticketVoteDTO2.setId(2L);
        assertThat(ticketVoteDTO1).isNotEqualTo(ticketVoteDTO2);
        ticketVoteDTO1.setId(null);
        assertThat(ticketVoteDTO1).isNotEqualTo(ticketVoteDTO2);
    }
}
