package com.diviso.spot_fix.repository;

import com.diviso.spot_fix.domain.WorkPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkPlanRepository extends JpaRepository<WorkPlan, Long>, JpaSpecificationExecutor<WorkPlan> {}
