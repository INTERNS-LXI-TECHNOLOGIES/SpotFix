package com.divisosofttech.spot_fix.service;

import com.divisosofttech.spot_fix.domain.criteria.TicketCriteria;
import com.divisosofttech.spot_fix.service.dto.TicketDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.divisosofttech.spot_fix.domain.Ticket}.
 */
public interface TicketService {
    /**
     * Save a ticket.
     *
     * @param ticketDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<TicketDTO> save(TicketDTO ticketDTO);

    /**
     * Updates a ticket.
     *
     * @param ticketDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<TicketDTO> update(TicketDTO ticketDTO);

    /**
     * Partially updates a ticket.
     *
     * @param ticketDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<TicketDTO> partialUpdate(TicketDTO ticketDTO);
    /**
     * Find tickets by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<TicketDTO> findByCriteria(TicketCriteria criteria, Pageable pageable);

    /**
     * Find the count of tickets by criteria.
     * @param criteria filtering criteria
     * @return the count of tickets
     */
    public Mono<Long> countByCriteria(TicketCriteria criteria);

    /**
     * Get all the tickets with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<TicketDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Returns the number of tickets available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" ticket.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<TicketDTO> findOne(Long id);

    /**
     * Delete the "id" ticket.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
