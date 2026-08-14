package com.divisosofttech.spot_fix.service;

import com.divisosofttech.spot_fix.domain.criteria.LocationCriteria;
import com.divisosofttech.spot_fix.service.dto.LocationDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.divisosofttech.spot_fix.domain.Location}.
 */
public interface LocationService {
    /**
     * Save a location.
     *
     * @param locationDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<LocationDTO> save(LocationDTO locationDTO);

    /**
     * Updates a location.
     *
     * @param locationDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<LocationDTO> update(LocationDTO locationDTO);

    /**
     * Partially updates a location.
     *
     * @param locationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<LocationDTO> partialUpdate(LocationDTO locationDTO);
    /**
     * Find locations by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<LocationDTO> findByCriteria(LocationCriteria criteria, Pageable pageable);

    /**
     * Find the count of locations by criteria.
     * @param criteria filtering criteria
     * @return the count of locations
     */
    public Mono<Long> countByCriteria(LocationCriteria criteria);

    /**
     * Returns the number of locations available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" location.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<LocationDTO> findOne(Long id);

    /**
     * Delete the "id" location.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
