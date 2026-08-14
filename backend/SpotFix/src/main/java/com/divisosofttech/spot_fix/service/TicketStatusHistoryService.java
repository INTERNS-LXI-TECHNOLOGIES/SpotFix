package com.divisosofttech.spot_fix.service;

import com.divisosofttech.spot_fix.domain.criteria.TicketStatusHistoryCriteria;
import com.divisosofttech.spot_fix.service.dto.TicketStatusHistoryDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.divisosofttech.spot_fix.domain.TicketStatusHistory}.
 */
public interface TicketStatusHistoryService {
    /**
     * Save a ticketStatusHistory.
     *
     * @param ticketStatusHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<TicketStatusHistoryDTO> save(TicketStatusHistoryDTO ticketStatusHistoryDTO);

    /**
     * Updates a ticketStatusHistory.
     *
     * @param ticketStatusHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<TicketStatusHistoryDTO> update(TicketStatusHistoryDTO ticketStatusHistoryDTO);

    /**
     * Partially updates a ticketStatusHistory.
     *
     * @param ticketStatusHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<TicketStatusHistoryDTO> partialUpdate(TicketStatusHistoryDTO ticketStatusHistoryDTO);
    /**
     * Find ticketStatusHistories by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<TicketStatusHistoryDTO> findByCriteria(TicketStatusHistoryCriteria criteria, Pageable pageable);

    /**
     * Find the count of ticketStatusHistories by criteria.
     * @param criteria filtering criteria
     * @return the count of ticketStatusHistories
     */
    public Mono<Long> countByCriteria(TicketStatusHistoryCriteria criteria);

    /**
     * Get all the ticketStatusHistories with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<TicketStatusHistoryDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Returns the number of ticketStatusHistories available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" ticketStatusHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<TicketStatusHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" ticketStatusHistory.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
