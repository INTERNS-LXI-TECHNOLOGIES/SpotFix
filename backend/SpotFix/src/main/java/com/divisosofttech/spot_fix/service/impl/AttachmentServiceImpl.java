package com.divisosofttech.spot_fix.service.impl;

import com.divisosofttech.spot_fix.domain.criteria.AttachmentCriteria;
import com.divisosofttech.spot_fix.repository.AttachmentRepository;
import com.divisosofttech.spot_fix.service.AttachmentService;
import com.divisosofttech.spot_fix.service.dto.AttachmentDTO;
import com.divisosofttech.spot_fix.service.mapper.AttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.divisosofttech.spot_fix.domain.Attachment}.
 */
@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    private final AttachmentRepository attachmentRepository;

    private final AttachmentMapper attachmentMapper;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
    }

    @Override
    public Mono<AttachmentDTO> save(AttachmentDTO attachmentDTO) {
        LOG.debug("Request to save Attachment : {}", attachmentDTO);
        return attachmentRepository.save(attachmentMapper.toEntity(attachmentDTO)).map(attachmentMapper::toDto);
    }

    @Override
    public Mono<AttachmentDTO> update(AttachmentDTO attachmentDTO) {
        LOG.debug("Request to update Attachment : {}", attachmentDTO);
        return attachmentRepository.save(attachmentMapper.toEntity(attachmentDTO)).map(attachmentMapper::toDto);
    }

    @Override
    public Mono<AttachmentDTO> partialUpdate(AttachmentDTO attachmentDTO) {
        LOG.debug("Request to partially update Attachment : {}", attachmentDTO);

        return attachmentRepository
            .findById(attachmentDTO.getId())
            .map(existingAttachment -> {
                attachmentMapper.partialUpdate(existingAttachment, attachmentDTO);

                return existingAttachment;
            })
            .flatMap(attachmentRepository::save)
            .map(attachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<AttachmentDTO> findByCriteria(AttachmentCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Attachments by Criteria");
        return attachmentRepository.findByCriteria(criteria, pageable).map(attachmentMapper::toDto);
    }

    /**
     * Find the count of attachments by criteria.
     * @param criteria filtering criteria
     * @return the count of attachments
     */
    public Mono<Long> countByCriteria(AttachmentCriteria criteria) {
        LOG.debug("Request to get the count of all Attachments by Criteria");
        return attachmentRepository.countByCriteria(criteria);
    }

    public Flux<AttachmentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return attachmentRepository.findAllWithEagerRelationships(pageable).map(attachmentMapper::toDto);
    }

    public Mono<Long> countAll() {
        return attachmentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<AttachmentDTO> findOne(Long id) {
        LOG.debug("Request to get Attachment : {}", id);
        return attachmentRepository.findOneWithEagerRelationships(id).map(attachmentMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Attachment : {}", id);
        return attachmentRepository.deleteById(id);
    }
}
