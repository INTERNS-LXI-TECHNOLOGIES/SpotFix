package com.divisosofttech.spot_fix.service.impl;

import com.divisosofttech.spot_fix.repository.UserProfileRepository;
import com.divisosofttech.spot_fix.service.UserProfileService;
import com.divisosofttech.spot_fix.service.dto.UserProfileDTO;
import com.divisosofttech.spot_fix.service.mapper.UserProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.divisosofttech.spot_fix.domain.UserProfile}.
 */
@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger LOG = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public Mono<UserProfileDTO> save(UserProfileDTO userProfileDTO) {
        LOG.debug("Request to save UserProfile : {}", userProfileDTO);
        return userProfileRepository.save(userProfileMapper.toEntity(userProfileDTO)).map(userProfileMapper::toDto);
    }

    @Override
    public Mono<UserProfileDTO> update(UserProfileDTO userProfileDTO) {
        LOG.debug("Request to update UserProfile : {}", userProfileDTO);
        return userProfileRepository.save(userProfileMapper.toEntity(userProfileDTO)).map(userProfileMapper::toDto);
    }

    @Override
    public Mono<UserProfileDTO> partialUpdate(UserProfileDTO userProfileDTO) {
        LOG.debug("Request to partially update UserProfile : {}", userProfileDTO);

        return userProfileRepository
            .findById(userProfileDTO.getId())
            .map(existingUserProfile -> {
                userProfileMapper.partialUpdate(existingUserProfile, userProfileDTO);

                return existingUserProfile;
            })
            .flatMap(userProfileRepository::save)
            .map(userProfileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<UserProfileDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all UserProfiles");
        return userProfileRepository.findAllBy(pageable).map(userProfileMapper::toDto);
    }

    public Flux<UserProfileDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userProfileRepository.findAllWithEagerRelationships(pageable).map(userProfileMapper::toDto);
    }

    public Mono<Long> countAll() {
        return userProfileRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<UserProfileDTO> findOne(Long id) {
        LOG.debug("Request to get UserProfile : {}", id);
        return userProfileRepository.findOneWithEagerRelationships(id).map(userProfileMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete UserProfile : {}", id);
        return userProfileRepository.deleteById(id);
    }
}
