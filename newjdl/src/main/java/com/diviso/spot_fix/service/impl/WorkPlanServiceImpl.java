package com.diviso.spot_fix.service.impl;

import com.diviso.spot_fix.domain.WorkPlan;
import com.diviso.spot_fix.repository.WorkPlanRepository;
import com.diviso.spot_fix.service.WorkPlanService;
import com.diviso.spot_fix.service.dto.WorkPlanDTO;
import com.diviso.spot_fix.service.mapper.WorkPlanMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.diviso.spot_fix.domain.WorkPlan}.
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
    public WorkPlanDTO save(WorkPlanDTO workPlanDTO) {
        LOG.debug("Request to save WorkPlan : {}", workPlanDTO);
        WorkPlan workPlan = workPlanMapper.toEntity(workPlanDTO);
        workPlan = workPlanRepository.save(workPlan);
        return workPlanMapper.toDto(workPlan);
    }

    @Override
    public WorkPlanDTO update(WorkPlanDTO workPlanDTO) {
        LOG.debug("Request to update WorkPlan : {}", workPlanDTO);
        WorkPlan workPlan = workPlanMapper.toEntity(workPlanDTO);
        workPlan = workPlanRepository.save(workPlan);
        return workPlanMapper.toDto(workPlan);
    }

    @Override
    public Optional<WorkPlanDTO> partialUpdate(WorkPlanDTO workPlanDTO) {
        LOG.debug("Request to partially update WorkPlan : {}", workPlanDTO);

        return workPlanRepository
            .findById(workPlanDTO.getId())
            .map(existingWorkPlan -> {
                workPlanMapper.partialUpdate(existingWorkPlan, workPlanDTO);

                return existingWorkPlan;
            })
            .map(workPlanRepository::save)
            .map(workPlanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkPlanDTO> findOne(Long id) {
        LOG.debug("Request to get WorkPlan : {}", id);
        return workPlanRepository.findById(id).map(workPlanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete WorkPlan : {}", id);
        workPlanRepository.deleteById(id);
    }
}
