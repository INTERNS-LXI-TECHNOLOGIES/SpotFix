package com.diviso.spot_fix.service;

import com.diviso.spot_fix.domain.*; // for static metamodels
import com.diviso.spot_fix.domain.Ticket;
import com.diviso.spot_fix.repository.TicketRepository;
import com.diviso.spot_fix.service.criteria.TicketCriteria;
import com.diviso.spot_fix.service.dto.TicketDTO;
import com.diviso.spot_fix.service.mapper.TicketMapper;
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
 * Service for executing complex queries for {@link Ticket} entities in the database.
 * The main input is a {@link TicketCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TicketDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TicketQueryService extends QueryService<Ticket> {

    private static final Logger LOG = LoggerFactory.getLogger(TicketQueryService.class);

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    public TicketQueryService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    /**
     * Return a {@link Page} of {@link TicketDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TicketDTO> findByCriteria(TicketCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ticket> specification = createSpecification(criteria);
        return ticketRepository.findAll(specification, page).map(ticketMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TicketCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Ticket> specification = createSpecification(criteria);
        return ticketRepository.count(specification);
    }

    /**
     * Function to convert {@link TicketCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ticket> createSpecification(TicketCriteria criteria) {
        Specification<Ticket> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Ticket_.id),
                buildStringSpecification(criteria.getTitle(), Ticket_.title),
                buildSpecification(criteria.getStatus(), Ticket_.status),
                buildSpecification(criteria.getPriority(), Ticket_.priority),
                buildSpecification(criteria.getVisibility(), Ticket_.visibility),
                buildSpecification(criteria.getCategory(), Ticket_.category),
                buildRangeSpecification(criteria.getCreatedDate(), Ticket_.createdDate),
                buildRangeSpecification(criteria.getUpdatedDate(), Ticket_.updatedDate),
                buildRangeSpecification(criteria.getExpectedResolutionDate(), Ticket_.expectedResolutionDate),
                buildRangeSpecification(criteria.getResolvedDate(), Ticket_.resolvedDate),
                buildSpecification(criteria.getAiDuplicate(), Ticket_.aiDuplicate),
                buildRangeSpecification(criteria.getDuplicateScore(), Ticket_.duplicateScore),
                buildRangeSpecification(criteria.getAiConfidence(), Ticket_.aiConfidence),
                buildRangeSpecification(criteria.getDuplicateTicketId(), Ticket_.duplicateTicketId),
                buildSpecification(criteria.getDeleted(), Ticket_.deleted),
                buildRangeSpecification(criteria.getDeletedDate(), Ticket_.deletedDate),
                buildSpecification(criteria.getReportedById(), root -> root.join(Ticket_.reportedBy, JoinType.LEFT).get(User_.id)),
                buildSpecification(criteria.getLocationId(), root -> root.join(Ticket_.location, JoinType.LEFT).get(Location_.id)),
                buildSpecification(criteria.getWardId(), root -> root.join(Ticket_.ward, JoinType.LEFT).get(Ward_.id)),
                buildSpecification(criteria.getAssignedDepartmentId(), root ->
                    root.join(Ticket_.assignedDepartment, JoinType.LEFT).get(Department_.id)
                )
            );
        }
        return specification;
    }
}
