package com.diviso.spot_fix.service;

import com.diviso.spot_fix.domain.*; // for static metamodels
import com.diviso.spot_fix.domain.Attachment;
import com.diviso.spot_fix.repository.AttachmentRepository;
import com.diviso.spot_fix.service.criteria.AttachmentCriteria;
import com.diviso.spot_fix.service.dto.AttachmentDTO;
import com.diviso.spot_fix.service.mapper.AttachmentMapper;
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
 * Service for executing complex queries for {@link Attachment} entities in the database.
 * The main input is a {@link AttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttachmentQueryService extends QueryService<Attachment> {

    private static final Logger LOG = LoggerFactory.getLogger(AttachmentQueryService.class);

    private final AttachmentRepository attachmentRepository;

    private final AttachmentMapper attachmentMapper;

    public AttachmentQueryService(AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
    }

    /**
     * Return a {@link Page} of {@link AttachmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttachmentDTO> findByCriteria(AttachmentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Attachment> specification = createSpecification(criteria);
        return attachmentRepository.findAll(specification, page).map(attachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttachmentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Attachment> specification = createSpecification(criteria);
        return attachmentRepository.count(specification);
    }

    /**
     * Function to convert {@link AttachmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Attachment> createSpecification(AttachmentCriteria criteria) {
        Specification<Attachment> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Attachment_.id),
                buildSpecification(criteria.getAttachmentType(), Attachment_.attachmentType),
                buildStringSpecification(criteria.getFileName(), Attachment_.fileName),
                buildStringSpecification(criteria.getFilePath(), Attachment_.filePath),
                buildStringSpecification(criteria.getFileType(), Attachment_.fileType),
                buildRangeSpecification(criteria.getFileSize(), Attachment_.fileSize),
                buildStringSpecification(criteria.getChecksum(), Attachment_.checksum),
                buildRangeSpecification(criteria.getUploadedDate(), Attachment_.uploadedDate),
                buildRangeSpecification(criteria.getDurationSeconds(), Attachment_.durationSeconds),
                buildStringSpecification(criteria.getLanguage(), Attachment_.language),
                buildSpecification(criteria.getDeleted(), Attachment_.deleted),
                buildRangeSpecification(criteria.getUpdatedDate(), Attachment_.updatedDate),
                buildRangeSpecification(criteria.getDeletedDate(), Attachment_.deletedDate),
                buildSpecification(criteria.getTicketId(), root -> root.join(Attachment_.ticket, JoinType.LEFT).get(Ticket_.id)),
                buildSpecification(criteria.getUploadedById(), root -> root.join(Attachment_.uploadedBy, JoinType.LEFT).get(User_.id))
            );
        }
        return specification;
    }
}
