package com.diviso.spot_fix.domain;

import static com.diviso.spot_fix.domain.DepartmentTestSamples.*;
import static com.diviso.spot_fix.domain.LocationTestSamples.*;
import static com.diviso.spot_fix.domain.TicketTestSamples.*;
import static com.diviso.spot_fix.domain.WardTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.diviso.spot_fix.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ticket.class);
        Ticket ticket1 = getTicketSample1();
        Ticket ticket2 = new Ticket();
        assertThat(ticket1).isNotEqualTo(ticket2);

        ticket2.setId(ticket1.getId());
        assertThat(ticket1).isEqualTo(ticket2);

        ticket2 = getTicketSample2();
        assertThat(ticket1).isNotEqualTo(ticket2);
    }

    @Test
    void locationTest() {
        Ticket ticket = getTicketRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        ticket.setLocation(locationBack);
        assertThat(ticket.getLocation()).isEqualTo(locationBack);

        ticket.location(null);
        assertThat(ticket.getLocation()).isNull();
    }

    @Test
    void wardTest() {
        Ticket ticket = getTicketRandomSampleGenerator();
        Ward wardBack = getWardRandomSampleGenerator();

        ticket.setWard(wardBack);
        assertThat(ticket.getWard()).isEqualTo(wardBack);

        ticket.ward(null);
        assertThat(ticket.getWard()).isNull();
    }

    @Test
    void assignedDepartmentTest() {
        Ticket ticket = getTicketRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        ticket.setAssignedDepartment(departmentBack);
        assertThat(ticket.getAssignedDepartment()).isEqualTo(departmentBack);

        ticket.assignedDepartment(null);
        assertThat(ticket.getAssignedDepartment()).isNull();
    }
}
