package com.diviso.spot_fix.service;

import com.diviso.spot_fix.service.dto.TicketVoteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.diviso.spot_fix.domain.TicketVote}.
 */
public interface TicketVoteService {
    /**
     * Save a ticketVote.
     *
     * @param ticketVoteDTO the entity to save.
     * @return the persisted entity.
     */
    TicketVoteDTO save(TicketVoteDTO ticketVoteDTO);

    /**
     * Updates a ticketVote.
     *
     * @param ticketVoteDTO the entity to update.
     * @return the persisted entity.
     */
    TicketVoteDTO update(TicketVoteDTO ticketVoteDTO);

    /**
     * Partially updates a ticketVote.
     *
     * @param ticketVoteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TicketVoteDTO> partialUpdate(TicketVoteDTO ticketVoteDTO);

    /**
     * Get all the ticketVotes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TicketVoteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ticketVote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketVoteDTO> findOne(Long id);

    /**
     * Delete the "id" ticketVote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
