package com.divisosofttech.spot_fix.service;

import com.divisosofttech.spot_fix.domain.criteria.CommentCriteria;
import com.divisosofttech.spot_fix.service.dto.CommentDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.divisosofttech.spot_fix.domain.Comment}.
 */
public interface CommentService {
    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<CommentDTO> save(CommentDTO commentDTO);

    /**
     * Updates a comment.
     *
     * @param commentDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<CommentDTO> update(CommentDTO commentDTO);

    /**
     * Partially updates a comment.
     *
     * @param commentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<CommentDTO> partialUpdate(CommentDTO commentDTO);
    /**
     * Find comments by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<CommentDTO> findByCriteria(CommentCriteria criteria, Pageable pageable);

    /**
     * Find the count of comments by criteria.
     * @param criteria filtering criteria
     * @return the count of comments
     */
    public Mono<Long> countByCriteria(CommentCriteria criteria);

    /**
     * Get all the comments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<CommentDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Returns the number of comments available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<CommentDTO> findOne(Long id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
