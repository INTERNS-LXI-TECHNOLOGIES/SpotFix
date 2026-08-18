package com.diviso.spot_fix.service.mapper;

import static com.diviso.spot_fix.domain.WorkPlanAsserts.*;
import static com.diviso.spot_fix.domain.WorkPlanTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkPlanMapperTest {

    private WorkPlanMapper workPlanMapper;

    @BeforeEach
    void setUp() {
        workPlanMapper = new WorkPlanMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWorkPlanSample1();
        var actual = workPlanMapper.toEntity(workPlanMapper.toDto(expected));
        assertWorkPlanAllPropertiesEquals(expected, actual);
    }
}
