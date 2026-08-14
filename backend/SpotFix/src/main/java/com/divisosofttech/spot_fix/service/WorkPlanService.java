package com.divisosofttech.spot_fix.service;

import com.divisosofttech.spot_fix.domain.criteria.WorkPlanCriteria;
import com.divisosofttech.spot_fix.service.dto.WorkPlanDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.divisosofttech.spot_fix.domain.WorkPlan}.
 */
public interface WorkPlanService {
    /**
     * Save a workPlan.
     *
     * @param workPlanDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<WorkPlanDTO> save(WorkPlanDTO workPlanDTO);

    /**
     * Updates a workPlan.
     *
     * @param workPlanDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<WorkPlanDTO> update(WorkPlanDTO workPlanDTO);

    /**
     * Partially updates a workPlan.
     *
     * @param workPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<WorkPlanDTO> partialUpdate(WorkPlanDTO workPlanDTO);
    /**
     * Find workPlans by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<WorkPlanDTO> findByCriteria(WorkPlanCriteria criteria, Pageable pageable);

    /**
     * Find the count of workPlans by criteria.
     * @param criteria filtering criteria
     * @return the count of workPlans
     */
    public Mono<Long> countByCriteria(WorkPlanCriteria criteria);

    /**
     * Returns the number of workPlans available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" workPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<WorkPlanDTO> findOne(Long id);

    /**
     * Delete the "id" workPlan.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
