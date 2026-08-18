package com.diviso.spot_fix.service;

import com.diviso.spot_fix.domain.*; // for static metamodels
import com.diviso.spot_fix.domain.Department;
import com.diviso.spot_fix.repository.DepartmentRepository;
import com.diviso.spot_fix.service.criteria.DepartmentCriteria;
import com.diviso.spot_fix.service.dto.DepartmentDTO;
import com.diviso.spot_fix.service.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Department} entities in the database.
 * The main input is a {@link DepartmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DepartmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepartmentQueryService extends QueryService<Department> {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentQueryService.class);

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentQueryService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Return a {@link Page} of {@link DepartmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartmentDTO> findByCriteria(DepartmentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.findAll(specification, page).map(departmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepartmentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.count(specification);
    }

    /**
     * Function to convert {@link DepartmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Department> createSpecification(DepartmentCriteria criteria) {
        Specification<Department> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Department_.id),
                buildStringSpecification(criteria.getName(), Department_.name),
                buildStringSpecification(criteria.getContactEmail(), Department_.contactEmail),
                buildStringSpecification(criteria.getContactPhone(), Department_.contactPhone),
                buildSpecification(criteria.getActive(), Department_.active)
            );
        }
        return specification;
    }
}
