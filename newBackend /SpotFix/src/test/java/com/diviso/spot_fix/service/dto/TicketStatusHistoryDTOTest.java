package com.diviso.spot_fix.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.diviso.spot_fix.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketStatusHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketStatusHistoryDTO.class);
        TicketStatusHistoryDTO ticketStatusHistoryDTO1 = new TicketStatusHistoryDTO();
        ticketStatusHistoryDTO1.setId(1L);
        TicketStatusHistoryDTO ticketStatusHistoryDTO2 = new TicketStatusHistoryDTO();
        assertThat(ticketStatusHistoryDTO1).isNotEqualTo(ticketStatusHistoryDTO2);
        ticketStatusHistoryDTO2.setId(ticketStatusHistoryDTO1.getId());
        assertThat(ticketStatusHistoryDTO1).isEqualTo(ticketStatusHistoryDTO2);
        ticketStatusHistoryDTO2.setId(2L);
        assertThat(ticketStatusHistoryDTO1).isNotEqualTo(ticketStatusHistoryDTO2);
        ticketStatusHistoryDTO1.setId(null);
        assertThat(ticketStatusHistoryDTO1).isNotEqualTo(ticketStatusHistoryDTO2);
    }
}
