package com.divisosofttech.spot_fix.service;

import com.divisosofttech.spot_fix.service.dto.UserProfileDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.divisosofttech.spot_fix.domain.UserProfile}.
 */
public interface UserProfileService {
    /**
     * Save a userProfile.
     *
     * @param userProfileDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<UserProfileDTO> save(UserProfileDTO userProfileDTO);

    /**
     * Updates a userProfile.
     *
     * @param userProfileDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<UserProfileDTO> update(UserProfileDTO userProfileDTO);

    /**
     * Partially updates a userProfile.
     *
     * @param userProfileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<UserProfileDTO> partialUpdate(UserProfileDTO userProfileDTO);

    /**
     * Get all the userProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<UserProfileDTO> findAll(Pageable pageable);

    /**
     * Get all the userProfiles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<UserProfileDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Returns the number of userProfiles available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" userProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<UserProfileDTO> findOne(Long id);

    /**
     * Delete the "id" userProfile.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
