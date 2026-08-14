package com.divisosofttech.spot_fix.service.impl;

import com.divisosofttech.spot_fix.domain.criteria.WardCriteria;
import com.divisosofttech.spot_fix.repository.WardRepository;
import com.divisosofttech.spot_fix.service.WardService;
import com.divisosofttech.spot_fix.service.dto.WardDTO;
import com.divisosofttech.spot_fix.service.mapper.WardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.divisosofttech.spot_fix.domain.Ward}.
 */
@Service
@Transactional
public class WardServiceImpl implements WardService {

    private static final Logger LOG = LoggerFactory.getLogger(WardServiceImpl.class);

    private final WardRepository wardRepository;

    private final WardMapper wardMapper;

    public WardServiceImpl(WardRepository wardRepository, WardMapper wardMapper) {
        this.wardRepository = wardRepository;
        this.wardMapper = wardMapper;
    }

    @Override
    public Mono<WardDTO> save(WardDTO wardDTO) {
        LOG.debug("Request to save Ward : {}", wardDTO);
        return wardRepository.save(wardMapper.toEntity(wardDTO)).map(wardMapper::toDto);
    }

    @Override
    public Mono<WardDTO> update(WardDTO wardDTO) {
        LOG.debug("Request to update Ward : {}", wardDTO);
        return wardRepository.save(wardMapper.toEntity(wardDTO)).map(wardMapper::toDto);
    }

    @Override
    public Mono<WardDTO> partialUpdate(WardDTO wardDTO) {
        LOG.debug("Request to partially update Ward : {}", wardDTO);

        return wardRepository
            .findById(wardDTO.getId())
            .map(existingWard -> {
                wardMapper.partialUpdate(existingWard, wardDTO);

                return existingWard;
            })
            .flatMap(wardRepository::save)
            .map(wardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<WardDTO> findByCriteria(WardCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Wards by Criteria");
        return wardRepository.findByCriteria(criteria, pageable).map(wardMapper::toDto);
    }

    /**
     * Find the count of wards by criteria.
     * @param criteria filtering criteria
     * @return the count of wards
     */
    public Mono<Long> countByCriteria(WardCriteria criteria) {
        LOG.debug("Request to get the count of all Wards by Criteria");
        return wardRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return wardRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<WardDTO> findOne(Long id) {
        LOG.debug("Request to get Ward : {}", id);
        return wardRepository.findById(id).map(wardMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Ward : {}", id);
        return wardRepository.deleteById(id);
    }
}
