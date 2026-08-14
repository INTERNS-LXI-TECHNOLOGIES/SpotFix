package com.divisosofttech.spot_fix.service.impl;

import com.divisosofttech.spot_fix.domain.criteria.LocationCriteria;
import com.divisosofttech.spot_fix.repository.LocationRepository;
import com.divisosofttech.spot_fix.service.LocationService;
import com.divisosofttech.spot_fix.service.dto.LocationDTO;
import com.divisosofttech.spot_fix.service.mapper.LocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.divisosofttech.spot_fix.domain.Location}.
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private static final Logger LOG = LoggerFactory.getLogger(LocationServiceImpl.class);

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public Mono<LocationDTO> save(LocationDTO locationDTO) {
        LOG.debug("Request to save Location : {}", locationDTO);
        return locationRepository.save(locationMapper.toEntity(locationDTO)).map(locationMapper::toDto);
    }

    @Override
    public Mono<LocationDTO> update(LocationDTO locationDTO) {
        LOG.debug("Request to update Location : {}", locationDTO);
        return locationRepository.save(locationMapper.toEntity(locationDTO)).map(locationMapper::toDto);
    }

    @Override
    public Mono<LocationDTO> partialUpdate(LocationDTO locationDTO) {
        LOG.debug("Request to partially update Location : {}", locationDTO);

        return locationRepository
            .findById(locationDTO.getId())
            .map(existingLocation -> {
                locationMapper.partialUpdate(existingLocation, locationDTO);

                return existingLocation;
            })
            .flatMap(locationRepository::save)
            .map(locationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<LocationDTO> findByCriteria(LocationCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Locations by Criteria");
        return locationRepository.findByCriteria(criteria, pageable).map(locationMapper::toDto);
    }

    /**
     * Find the count of locations by criteria.
     * @param criteria filtering criteria
     * @return the count of locations
     */
    public Mono<Long> countByCriteria(LocationCriteria criteria) {
        LOG.debug("Request to get the count of all Locations by Criteria");
        return locationRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return locationRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<LocationDTO> findOne(Long id) {
        LOG.debug("Request to get Location : {}", id);
        return locationRepository.findById(id).map(locationMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Location : {}", id);
        return locationRepository.deleteById(id);
    }
}
