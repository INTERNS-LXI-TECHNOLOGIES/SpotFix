package com.diviso.spot_fix.service;

import com.diviso.spot_fix.domain.*; // for static metamodels
import com.diviso.spot_fix.domain.WorkPlan;
import com.diviso.spot_fix.repository.WorkPlanRepository;
import com.diviso.spot_fix.service.criteria.WorkPlanCriteria;
import com.diviso.spot_fix.service.dto.WorkPlanDTO;
import com.diviso.spot_fix.service.mapper.WorkPlanMapper;
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
 * Service for executing complex queries for {@link WorkPlan} entities in the database.
 * The main input is a {@link WorkPlanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link WorkPlanDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkPlanQueryService extends QueryService<WorkPlan> {

    private static final Logger LOG = LoggerFactory.getLogger(WorkPlanQueryService.class);

    private final WorkPlanRepository workPlanRepository;

    private final WorkPlanMapper workPlanMapper;

    public WorkPlanQueryService(WorkPlanRepository workPlanRepository, WorkPlanMapper workPlanMapper) {
        this.workPlanRepository = workPlanRepository;
        this.workPlanMapper = workPlanMapper;
    }

    /**
     * Return a {@link Page} of {@link WorkPlanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkPlanDTO> findByCriteria(WorkPlanCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkPlan> specification = createSpecification(criteria);
        return workPlanRepository.findAll(specification, page).map(workPlanMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkPlanCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<WorkPlan> specification = createSpecification(criteria);
        return workPlanRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkPlanCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkPlan> createSpecification(WorkPlanCriteria criteria) {
        Specification<WorkPlan> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), WorkPlan_.id),
                buildRangeSpecification(criteria.getEstimatedCost(), WorkPlan_.estimatedCost),
                buildRangeSpecification(criteria.getStartedDate(), WorkPlan_.startedDate),
                buildRangeSpecification(criteria.getExpectedCompletionDate(), WorkPlan_.expectedCompletionDate),
                buildRangeSpecification(criteria.getActualCompletionDate(), WorkPlan_.actualCompletionDate),
                buildRangeSpecification(criteria.getCompletionPercentage(), WorkPlan_.completionPercentage),
                buildSpecification(criteria.getStatus(), WorkPlan_.status),
                buildSpecification(criteria.getDeleted(), WorkPlan_.deleted),
                buildRangeSpecification(criteria.getDeletedDate(), WorkPlan_.deletedDate),
                buildSpecification(criteria.getTicketId(), root -> root.join(WorkPlan_.ticket, JoinType.LEFT).get(Ticket_.id)),
                buildSpecification(criteria.getDepartmentId(), root -> root.join(WorkPlan_.department, JoinType.LEFT).get(Department_.id))
            );
        }
        return specification;
    }
}
