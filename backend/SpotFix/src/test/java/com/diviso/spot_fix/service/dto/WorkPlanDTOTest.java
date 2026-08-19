package com.diviso.spot_fix.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.diviso.spot_fix.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkPlanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkPlanDTO.class);
        WorkPlanDTO workPlanDTO1 = new WorkPlanDTO();
        workPlanDTO1.setId(1L);
        WorkPlanDTO workPlanDTO2 = new WorkPlanDTO();
        assertThat(workPlanDTO1).isNotEqualTo(workPlanDTO2);
        workPlanDTO2.setId(workPlanDTO1.getId());
        assertThat(workPlanDTO1).isEqualTo(workPlanDTO2);
        workPlanDTO2.setId(2L);
        assertThat(workPlanDTO1).isNotEqualTo(workPlanDTO2);
        workPlanDTO1.setId(null);
        assertThat(workPlanDTO1).isNotEqualTo(workPlanDTO2);
    }
}
