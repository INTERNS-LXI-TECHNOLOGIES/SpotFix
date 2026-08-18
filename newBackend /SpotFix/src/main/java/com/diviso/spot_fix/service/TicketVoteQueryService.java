package com.diviso.spot_fix.service;

import com.diviso.spot_fix.domain.*; // for static metamodels
import com.diviso.spot_fix.domain.TicketVote;
import com.diviso.spot_fix.repository.TicketVoteRepository;
import com.diviso.spot_fix.service.criteria.TicketVoteCriteria;
import com.diviso.spot_fix.service.dto.TicketVoteDTO;
import com.diviso.spot_fix.service.mapper.TicketVoteMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TicketVote} entities in the database.
 * The main input is a {@link TicketVoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TicketVoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TicketVoteQueryService extends QueryService<TicketVote> {

    private static final Logger LOG = LoggerFactory.getLogger(TicketVoteQueryService.class);

    private final TicketVoteRepository ticketVoteRepository;

    private final TicketVoteMapper ticketVoteMapper;

    public TicketVoteQueryService(TicketVoteRepository ticketVoteRepository, TicketVoteMapper ticketVoteMapper) {
        this.ticketVoteRepository = ticketVoteRepository;
        this.ticketVoteMapper = ticketVoteMapper;
    }

    /**
     * Return a {@link Page} of {@link TicketVoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TicketVoteDTO> findByCriteria(TicketVoteCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TicketVote> specification = createSpecification(criteria);
        return ticketVoteRepository.findAll(specification, page).map(ticketVoteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TicketVoteCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<TicketVote> specification = createSpecification(criteria);
        return ticketVoteRepository.count(specification);
    }

    /**
     * Function to convert {@link TicketVoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TicketVote> createSpecification(TicketVoteCriteria criteria) {
        Specification<TicketVote> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), TicketVote_.id),
                buildSpecification(criteria.getVoteType(), TicketVote_.voteType),
                buildRangeSpecification(criteria.getCreatedDate(), TicketVote_.createdDate),
                buildSpecification(criteria.getTicketId(), root -> root.join(TicketVote_.ticket, JoinType.LEFT).get(Ticket_.id)),
                buildSpecification(criteria.getUserId(), root -> root.join(TicketVote_.user, JoinType.LEFT).get(User_.id))
            );
        }
        return specification;
    }
}
