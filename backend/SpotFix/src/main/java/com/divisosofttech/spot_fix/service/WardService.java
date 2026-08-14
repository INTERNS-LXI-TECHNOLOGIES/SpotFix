package com.divisosofttech.spot_fix.service;

import com.divisosofttech.spot_fix.domain.criteria.WardCriteria;
import com.divisosofttech.spot_fix.service.dto.WardDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.divisosofttech.spot_fix.domain.Ward}.
 */
public interface WardService {
    /**
     * Save a ward.
     *
     * @param wardDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<WardDTO> save(WardDTO wardDTO);

    /**
     * Updates a ward.
     *
     * @param wardDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<WardDTO> update(WardDTO wardDTO);

    /**
     * Partially updates a ward.
     *
     * @param wardDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<WardDTO> partialUpdate(WardDTO wardDTO);
    /**
     * Find wards by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<WardDTO> findByCriteria(WardCriteria criteria, Pageable pageable);

    /**
     * Find the count of wards by criteria.
     * @param criteria filtering criteria
     * @return the count of wards
     */
    public Mono<Long> countByCriteria(WardCriteria criteria);

    /**
     * Returns the number of wards available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" ward.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<WardDTO> findOne(Long id);

    /**
     * Delete the "id" ward.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
