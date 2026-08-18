package com.diviso.spot_fix.service;

import com.diviso.spot_fix.service.dto.TicketStatusHistoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.diviso.spot_fix.domain.TicketStatusHistory}.
 */
public interface TicketStatusHistoryService {
    /**
     * Save a ticketStatusHistory.
     *
     * @param ticketStatusHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    TicketStatusHistoryDTO save(TicketStatusHistoryDTO ticketStatusHistoryDTO);

    /**
     * Updates a ticketStatusHistory.
     *
     * @param ticketStatusHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    TicketStatusHistoryDTO update(TicketStatusHistoryDTO ticketStatusHistoryDTO);

    /**
     * Partially updates a ticketStatusHistory.
     *
     * @param ticketStatusHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TicketStatusHistoryDTO> partialUpdate(TicketStatusHistoryDTO ticketStatusHistoryDTO);

    /**
     * Get all the ticketStatusHistories with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TicketStatusHistoryDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ticketStatusHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketStatusHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" ticketStatusHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
