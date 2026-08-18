package com.diviso.spot_fix.service;

import com.diviso.spot_fix.service.dto.WorkPlanDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.diviso.spot_fix.domain.WorkPlan}.
 */
public interface WorkPlanService {
    /**
     * Save a workPlan.
     *
     * @param workPlanDTO the entity to save.
     * @return the persisted entity.
     */
    WorkPlanDTO save(WorkPlanDTO workPlanDTO);

    /**
     * Updates a workPlan.
     *
     * @param workPlanDTO the entity to update.
     * @return the persisted entity.
     */
    WorkPlanDTO update(WorkPlanDTO workPlanDTO);

    /**
     * Partially updates a workPlan.
     *
     * @param workPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkPlanDTO> partialUpdate(WorkPlanDTO workPlanDTO);

    /**
     * Get the "id" workPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkPlanDTO> findOne(Long id);

    /**
     * Delete the "id" workPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
