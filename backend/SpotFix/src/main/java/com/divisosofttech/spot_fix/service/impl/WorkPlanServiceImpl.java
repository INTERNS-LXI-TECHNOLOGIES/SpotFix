package com.divisosofttech.spot_fix.service.impl;

import com.divisosofttech.spot_fix.domain.criteria.WorkPlanCriteria;
import com.divisosofttech.spot_fix.repository.WorkPlanRepository;
import com.divisosofttech.spot_fix.service.WorkPlanService;
import com.divisosofttech.spot_fix.service.dto.WorkPlanDTO;
import com.divisosofttech.spot_fix.service.mapper.WorkPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.divisosofttech.spot_fix.domain.WorkPlan}.
 */
@Service
@Transactional
public class WorkPlanServiceImpl implements WorkPlanService {

    private static final Logger LOG = LoggerFactory.getLogger(WorkPlanServiceImpl.class);

    private final WorkPlanRepository workPlanRepository;

    private final WorkPlanMapper workPlanMapper;

    public WorkPlanServiceImpl(WorkPlanRepository workPlanRepository, WorkPlanMapper workPlanMapper) {
        this.workPlanRepository = workPlanRepository;
        this.workPlanMapper = workPlanMapper;
    }

    @Override
    public Mono<WorkPlanDTO> save(WorkPlanDTO workPlanDTO) {
        LOG.debug("Request to save WorkPlan : {}", workPlanDTO);
        return workPlanRepository.save(workPlanMapper.toEntity(workPlanDTO)).map(workPlanMapper::toDto);
    }

    @Override
    public Mono<WorkPlanDTO> update(WorkPlanDTO workPlanDTO) {
        LOG.debug("Request to update WorkPlan : {}", workPlanDTO);
        return workPlanRepository.save(workPlanMapper.toEntity(workPlanDTO)).map(workPlanMapper::toDto);
    }

    @Override
    public Mono<WorkPlanDTO> partialUpdate(WorkPlanDTO workPlanDTO) {
        LOG.debug("Request to partially update WorkPlan : {}", workPlanDTO);

        return workPlanRepository
            .findById(workPlanDTO.getId())
            .map(existingWorkPlan -> {
                workPlanMapper.partialUpdate(existingWorkPlan, workPlanDTO);

                return existingWorkPlan;
            })
            .flatMap(workPlanRepository::save)
            .map(workPlanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<WorkPlanDTO> findByCriteria(WorkPlanCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all WorkPlans by Criteria");
        return workPlanRepository.findByCriteria(criteria, pageable).map(workPlanMapper::toDto);
    }

    /**
     * Find the count of workPlans by criteria.
     * @param criteria filtering criteria
     * @return the count of workPlans
     */
    public Mono<Long> countByCriteria(WorkPlanCriteria criteria) {
        LOG.debug("Request to get the count of all WorkPlans by Criteria");
        return workPlanRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return workPlanRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<WorkPlanDTO> findOne(Long id) {
        LOG.debug("Request to get WorkPlan : {}", id);
        return workPlanRepository.findById(id).map(workPlanMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete WorkPlan : {}", id);
        return workPlanRepository.deleteById(id);
    }
}
