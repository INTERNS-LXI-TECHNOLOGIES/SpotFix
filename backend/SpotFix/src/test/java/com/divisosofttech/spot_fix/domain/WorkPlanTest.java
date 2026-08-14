package com.divisosofttech.spot_fix.domain;

import static com.divisosofttech.spot_fix.domain.DepartmentTestSamples.*;
import static com.divisosofttech.spot_fix.domain.TicketTestSamples.*;
import static com.divisosofttech.spot_fix.domain.WorkPlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.divisosofttech.spot_fix.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkPlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkPlan.class);
        WorkPlan workPlan1 = getWorkPlanSample1();
        WorkPlan workPlan2 = new WorkPlan();
        assertThat(workPlan1).isNotEqualTo(workPlan2);

        workPlan2.setId(workPlan1.getId());
        assertThat(workPlan1).isEqualTo(workPlan2);

        workPlan2 = getWorkPlanSample2();
        assertThat(workPlan1).isNotEqualTo(workPlan2);
    }

    @Test
    void ticketTest() {
        WorkPlan workPlan = getWorkPlanRandomSampleGenerator();
        Ticket ticketBack = getTicketRandomSampleGenerator();

        workPlan.setTicket(ticketBack);
        assertThat(workPlan.getTicket()).isEqualTo(ticketBack);

        workPlan.ticket(null);
        assertThat(workPlan.getTicket()).isNull();
    }

    @Test
    void departmentTest() {
        WorkPlan workPlan = getWorkPlanRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        workPlan.setDepartment(departmentBack);
        assertThat(workPlan.getDepartment()).isEqualTo(departmentBack);

        workPlan.department(null);
        assertThat(workPlan.getDepartment()).isNull();
    }
}
