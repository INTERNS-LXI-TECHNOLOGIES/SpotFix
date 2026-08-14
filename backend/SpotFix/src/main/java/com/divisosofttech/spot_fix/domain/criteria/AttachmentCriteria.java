package com.divisosofttech.spot_fix.domain.criteria;

import com.divisosofttech.spot_fix.domain.enumeration.AttachmentType;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.divisosofttech.spot_fix.domain.Attachment} entity. This class is used
 * in {@link com.divisosofttech.spot_fix.web.rest.AttachmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attachments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachmentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AttachmentType
     */
    public static class AttachmentTypeFilter extends Filter<AttachmentType> {

        public AttachmentTypeFilter() {}

        public AttachmentTypeFilter(AttachmentTypeFilter filter) {
            super(filter);
        }

        @Override
        public AttachmentTypeFilter copy() {
            return new AttachmentTypeFilter(this);
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AttachmentTypeFilter attachmentType;

    private StringFilter fileName;

    private StringFilter filePath;

    private StringFilter fileType;

    private LongFilter fileSize;

    private StringFilter checksum;

    private InstantFilter uploadedDate;

    private IntegerFilter durationSeconds;

    private StringFilter language;

    private BooleanFilter deleted;

    private InstantFilter updatedDate;

    private InstantFilter deletedDate;

    private LongFilter ticketId;

    private LongFilter uploadedById;

    private Boolean distinct;

    public AttachmentCriteria() {}

    public AttachmentCriteria(AttachmentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.attachmentType = other.optionalAttachmentType().map(AttachmentTypeFilter::copy).orElse(null);
        this.fileName = other.optionalFileName().map(StringFilter::copy).orElse(null);
        this.filePath = other.optionalFilePath().map(StringFilter::copy).orElse(null);
        this.fileType = other.optionalFileType().map(StringFilter::copy).orElse(null);
        this.fileSize = other.optionalFileSize().map(LongFilter::copy).orElse(null);
        this.checksum = other.optionalChecksum().map(StringFilter::copy).orElse(null);
        this.uploadedDate = other.optionalUploadedDate().map(InstantFilter::copy).orElse(null);
        this.durationSeconds = other.optionalDurationSeconds().map(IntegerFilter::copy).orElse(null);
        this.language = other.optionalLanguage().map(StringFilter::copy).orElse(null);
        this.deleted = other.optionalDeleted().map(BooleanFilter::copy).orElse(null);
        this.updatedDate = other.optionalUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.deletedDate = other.optionalDeletedDate().map(InstantFilter::copy).orElse(null);
        this.ticketId = other.optionalTicketId().map(LongFilter::copy).orElse(null);
        this.uploadedById = other.optionalUploadedById().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AttachmentCriteria copy() {
        return new AttachmentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public AttachmentTypeFilter getAttachmentType() {
        return attachmentType;
    }

    public Optional<AttachmentTypeFilter> optionalAttachmentType() {
        return Optional.ofNullable(attachmentType);
    }

    public AttachmentTypeFilter attachmentType() {
        if (attachmentType == null) {
            setAttachmentType(new AttachmentTypeFilter());
        }
        return attachmentType;
    }

    public void setAttachmentType(AttachmentTypeFilter attachmentType) {
        this.attachmentType = attachmentType;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public Optional<StringFilter> optionalFileName() {
        return Optional.ofNullable(fileName);
    }

    public StringFilter fileName() {
        if (fileName == null) {
            setFileName(new StringFilter());
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getFilePath() {
        return filePath;
    }

    public Optional<StringFilter> optionalFilePath() {
        return Optional.ofNullable(filePath);
    }

    public StringFilter filePath() {
        if (filePath == null) {
            setFilePath(new StringFilter());
        }
        return filePath;
    }

    public void setFilePath(StringFilter filePath) {
        this.filePath = filePath;
    }

    public StringFilter getFileType() {
        return fileType;
    }

    public Optional<StringFilter> optionalFileType() {
        return Optional.ofNullable(fileType);
    }

    public StringFilter fileType() {
        if (fileType == null) {
            setFileType(new StringFilter());
        }
        return fileType;
    }

    public void setFileType(StringFilter fileType) {
        this.fileType = fileType;
    }

    public LongFilter getFileSize() {
        return fileSize;
    }

    public Optional<LongFilter> optionalFileSize() {
        return Optional.ofNullable(fileSize);
    }

    public LongFilter fileSize() {
        if (fileSize == null) {
            setFileSize(new LongFilter());
        }
        return fileSize;
    }

    public void setFileSize(LongFilter fileSize) {
        this.fileSize = fileSize;
    }

    public StringFilter getChecksum() {
        return checksum;
    }

    public Optional<StringFilter> optionalChecksum() {
        return Optional.ofNullable(checksum);
    }

    public StringFilter checksum() {
        if (checksum == null) {
            setChecksum(new StringFilter());
        }
        return checksum;
    }

    public void setChecksum(StringFilter checksum) {
        this.checksum = checksum;
    }

    public InstantFilter getUploadedDate() {
        return uploadedDate;
    }

    public Optional<InstantFilter> optionalUploadedDate() {
        return Optional.ofNullable(uploadedDate);
    }

    public InstantFilter uploadedDate() {
        if (uploadedDate == null) {
            setUploadedDate(new InstantFilter());
        }
        return uploadedDate;
    }

    public void setUploadedDate(InstantFilter uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public IntegerFilter getDurationSeconds() {
        return durationSeconds;
    }

    public Optional<IntegerFilter> optionalDurationSeconds() {
        return Optional.ofNullable(durationSeconds);
    }

    public IntegerFilter durationSeconds() {
        if (durationSeconds == null) {
            setDurationSeconds(new IntegerFilter());
        }
        return durationSeconds;
    }

    public void setDurationSeconds(IntegerFilter durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public StringFilter getLanguage() {
        return language;
    }

    public Optional<StringFilter> optionalLanguage() {
        return Optional.ofNullable(language);
    }

    public StringFilter language() {
        if (language == null) {
            setLanguage(new StringFilter());
        }
        return language;
    }

    public void setLanguage(StringFilter language) {
        this.language = language;
    }

    public BooleanFilter getDeleted() {
        return deleted;
    }

    public Optional<BooleanFilter> optionalDeleted() {
        return Optional.ofNullable(deleted);
    }

    public BooleanFilter deleted() {
        if (deleted == null) {
            setDeleted(new BooleanFilter());
        }
        return deleted;
    }

    public void setDeleted(BooleanFilter deleted) {
        this.deleted = deleted;
    }

    public InstantFilter getUpdatedDate() {
        return updatedDate;
    }

    public Optional<InstantFilter> optionalUpdatedDate() {
        return Optional.ofNullable(updatedDate);
    }

    public InstantFilter updatedDate() {
        if (updatedDate == null) {
            setUpdatedDate(new InstantFilter());
        }
        return updatedDate;
    }

    public void setUpdatedDate(InstantFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public InstantFilter getDeletedDate() {
        return deletedDate;
    }

    public Optional<InstantFilter> optionalDeletedDate() {
        return Optional.ofNullable(deletedDate);
    }

    public InstantFilter deletedDate() {
        if (deletedDate == null) {
            setDeletedDate(new InstantFilter());
        }
        return deletedDate;
    }

    public void setDeletedDate(InstantFilter deletedDate) {
        this.deletedDate = deletedDate;
    }

    public LongFilter getTicketId() {
        return ticketId;
    }

    public Optional<LongFilter> optionalTicketId() {
        return Optional.ofNullable(ticketId);
    }

    public LongFilter ticketId() {
        if (ticketId == null) {
            setTicketId(new LongFilter());
        }
        return ticketId;
    }

    public void setTicketId(LongFilter ticketId) {
        this.ticketId = ticketId;
    }

    public LongFilter getUploadedById() {
        return uploadedById;
    }

    public Optional<LongFilter> optionalUploadedById() {
        return Optional.ofNullable(uploadedById);
    }

    public LongFilter uploadedById() {
        if (uploadedById == null) {
            setUploadedById(new LongFilter());
        }
        return uploadedById;
    }

    public void setUploadedById(LongFilter uploadedById) {
        this.uploadedById = uploadedById;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttachmentCriteria that = (AttachmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(attachmentType, that.attachmentType) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(filePath, that.filePath) &&
            Objects.equals(fileType, that.fileType) &&
            Objects.equals(fileSize, that.fileSize) &&
            Objects.equals(checksum, that.checksum) &&
            Objects.equals(uploadedDate, that.uploadedDate) &&
            Objects.equals(durationSeconds, that.durationSeconds) &&
            Objects.equals(language, that.language) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(deletedDate, that.deletedDate) &&
            Objects.equals(ticketId, that.ticketId) &&
            Objects.equals(uploadedById, that.uploadedById) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            attachmentType,
            fileName,
            filePath,
            fileType,
            fileSize,
            checksum,
            uploadedDate,
            durationSeconds,
            language,
            deleted,
            updatedDate,
            deletedDate,
            ticketId,
            uploadedById,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalAttachmentType().map(f -> "attachmentType=" + f + ", ").orElse("") +
            optionalFileName().map(f -> "fileName=" + f + ", ").orElse("") +
            optionalFilePath().map(f -> "filePath=" + f + ", ").orElse("") +
            optionalFileType().map(f -> "fileType=" + f + ", ").orElse("") +
            optionalFileSize().map(f -> "fileSize=" + f + ", ").orElse("") +
            optionalChecksum().map(f -> "checksum=" + f + ", ").orElse("") +
            optionalUploadedDate().map(f -> "uploadedDate=" + f + ", ").orElse("") +
            optionalDurationSeconds().map(f -> "durationSeconds=" + f + ", ").orElse("") +
            optionalLanguage().map(f -> "language=" + f + ", ").orElse("") +
            optionalDeleted().map(f -> "deleted=" + f + ", ").orElse("") +
            optionalUpdatedDate().map(f -> "updatedDate=" + f + ", ").orElse("") +
            optionalDeletedDate().map(f -> "deletedDate=" + f + ", ").orElse("") +
            optionalTicketId().map(f -> "ticketId=" + f + ", ").orElse("") +
            optionalUploadedById().map(f -> "uploadedById=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
