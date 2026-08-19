package com.diviso.spot_fix.service;

import com.diviso.spot_fix.domain.*; // for static metamodels
import com.diviso.spot_fix.domain.TicketStatusHistory;
import com.diviso.spot_fix.repository.TicketStatusHistoryRepository;
import com.diviso.spot_fix.service.criteria.TicketStatusHistoryCriteria;
import com.diviso.spot_fix.service.dto.TicketStatusHistoryDTO;
import com.diviso.spot_fix.service.mapper.TicketStatusHistoryMapper;
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
 * Service for executing complex queries for {@link TicketStatusHistory} entities in the database.
 * The main input is a {@link TicketStatusHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TicketStatusHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TicketStatusHistoryQueryService extends QueryService<TicketStatusHistory> {

    private static final Logger LOG = LoggerFactory.getLogger(TicketStatusHistoryQueryService.class);

    private final TicketStatusHistoryRepository ticketStatusHistoryRepository;

    private final TicketStatusHistoryMapper ticketStatusHistoryMapper;

    public TicketStatusHistoryQueryService(
        TicketStatusHistoryRepository ticketStatusHistoryRepository,
        TicketStatusHistoryMapper ticketStatusHistoryMapper
    ) {
        this.ticketStatusHistoryRepository = ticketStatusHistoryRepository;
        this.ticketStatusHistoryMapper = ticketStatusHistoryMapper;
    }

    /**
     * Return a {@link Page} of {@link TicketStatusHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TicketStatusHistoryDTO> findByCriteria(TicketStatusHistoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TicketStatusHistory> specification = createSpecification(criteria);
        return ticketStatusHistoryRepository.findAll(specification, page).map(ticketStatusHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TicketStatusHistoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<TicketStatusHistory> specification = createSpecification(criteria);
        return ticketStatusHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link TicketStatusHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TicketStatusHistory> createSpecification(TicketStatusHistoryCriteria criteria) {
        Specification<TicketStatusHistory> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), TicketStatusHistory_.id),
                buildSpecification(criteria.getOldStatus(), TicketStatusHistory_.oldStatus),
                buildSpecification(criteria.getNewStatus(), TicketStatusHistory_.newStatus),
                buildRangeSpecification(criteria.getChangedDate(), TicketStatusHistory_.changedDate),
                buildSpecification(criteria.getTicketId(), root -> root.join(TicketStatusHistory_.ticket, JoinType.LEFT).get(Ticket_.id)),
                buildSpecification(criteria.getChangedById(), root ->
                    root.join(TicketStatusHistory_.changedBy, JoinType.LEFT).get(User_.id)
                )
            );
        }
        return specification;
    }
}
