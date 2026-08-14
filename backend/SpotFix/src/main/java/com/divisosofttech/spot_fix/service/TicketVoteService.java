package com.divisosofttech.spot_fix.service;

import com.divisosofttech.spot_fix.domain.criteria.TicketVoteCriteria;
import com.divisosofttech.spot_fix.service.dto.TicketVoteDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.divisosofttech.spot_fix.domain.TicketVote}.
 */
public interface TicketVoteService {
    /**
     * Save a ticketVote.
     *
     * @param ticketVoteDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<TicketVoteDTO> save(TicketVoteDTO ticketVoteDTO);

    /**
     * Updates a ticketVote.
     *
     * @param ticketVoteDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<TicketVoteDTO> update(TicketVoteDTO ticketVoteDTO);

    /**
     * Partially updates a ticketVote.
     *
     * @param ticketVoteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<TicketVoteDTO> partialUpdate(TicketVoteDTO ticketVoteDTO);
    /**
     * Find ticketVotes by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<TicketVoteDTO> findByCriteria(TicketVoteCriteria criteria, Pageable pageable);

    /**
     * Find the count of ticketVotes by criteria.
     * @param criteria filtering criteria
     * @return the count of ticketVotes
     */
    public Mono<Long> countByCriteria(TicketVoteCriteria criteria);

    /**
     * Get all the ticketVotes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<TicketVoteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Returns the number of ticketVotes available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" ticketVote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<TicketVoteDTO> findOne(Long id);

    /**
     * Delete the "id" ticketVote.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
