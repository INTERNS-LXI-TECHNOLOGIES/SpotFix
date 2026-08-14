package com.divisosofttech.spot_fix.domain.criteria;

import com.divisosofttech.spot_fix.domain.enumeration.Priority;
import com.divisosofttech.spot_fix.domain.enumeration.TicketCategory;
import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import com.divisosofttech.spot_fix.domain.enumeration.Visibility;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.divisosofttech.spot_fix.domain.Ticket} entity. This class is used
 * in {@link com.divisosofttech.spot_fix.web.rest.TicketResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tickets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TicketStatus
     */
    public static class TicketStatusFilter extends Filter<TicketStatus> {

        public TicketStatusFilter() {}

        public TicketStatusFilter(TicketStatusFilter filter) {
            super(filter);
        }

        @Override
        public TicketStatusFilter copy() {
            return new TicketStatusFilter(this);
        }
    }

    /**
     * Class for filtering Priority
     */
    public static class PriorityFilter extends Filter<Priority> {

        public PriorityFilter() {}

        public PriorityFilter(PriorityFilter filter) {
            super(filter);
        }

        @Override
        public PriorityFilter copy() {
            return new PriorityFilter(this);
        }
    }

    /**
     * Class for filtering Visibility
     */
    public static class VisibilityFilter extends Filter<Visibility> {

        public VisibilityFilter() {}

        public VisibilityFilter(VisibilityFilter filter) {
            super(filter);
        }

        @Override
        public VisibilityFilter copy() {
            return new VisibilityFilter(this);
        }
    }

    /**
     * Class for filtering TicketCategory
     */
    public static class TicketCategoryFilter extends Filter<TicketCategory> {

        public TicketCategoryFilter() {}

        public TicketCategoryFilter(TicketCategoryFilter filter) {
            super(filter);
        }

        @Override
        public TicketCategoryFilter copy() {
            return new TicketCategoryFilter(this);
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private TicketStatusFilter status;

    private PriorityFilter priority;

    private VisibilityFilter visibility;

    private TicketCategoryFilter category;

    private InstantFilter createdDate;

    private InstantFilter updatedDate;

    private InstantFilter expectedResolutionDate;

    private InstantFilter resolvedDate;

    private BooleanFilter aiDuplicate;

    private DoubleFilter duplicateScore;

    private DoubleFilter aiConfidence;

    private LongFilter duplicateTicketId;

    private BooleanFilter deleted;

    private InstantFilter deletedDate;

    private LongFilter reportedById;

    private LongFilter locationId;

    private LongFilter wardId;

    private LongFilter assignedDepartmentId;

    private Boolean distinct;

    public TicketCriteria() {}

    public TicketCriteria(TicketCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.title = other.optionalTitle().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(TicketStatusFilter::copy).orElse(null);
        this.priority = other.optionalPriority().map(PriorityFilter::copy).orElse(null);
        this.visibility = other.optionalVisibility().map(VisibilityFilter::copy).orElse(null);
        this.category = other.optionalCategory().map(TicketCategoryFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.updatedDate = other.optionalUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.expectedResolutionDate = other.optionalExpectedResolutionDate().map(InstantFilter::copy).orElse(null);
        this.resolvedDate = other.optionalResolvedDate().map(InstantFilter::copy).orElse(null);
        this.aiDuplicate = other.optionalAiDuplicate().map(BooleanFilter::copy).orElse(null);
        this.duplicateScore = other.optionalDuplicateScore().map(DoubleFilter::copy).orElse(null);
        this.aiConfidence = other.optionalAiConfidence().map(DoubleFilter::copy).orElse(null);
        this.duplicateTicketId = other.optionalDuplicateTicketId().map(LongFilter::copy).orElse(null);
        this.deleted = other.optionalDeleted().map(BooleanFilter::copy).orElse(null);
        this.deletedDate = other.optionalDeletedDate().map(InstantFilter::copy).orElse(null);
        this.reportedById = other.optionalReportedById().map(LongFilter::copy).orElse(null);
        this.locationId = other.optionalLocationId().map(LongFilter::copy).orElse(null);
        this.wardId = other.optionalWardId().map(LongFilter::copy).orElse(null);
        this.assignedDepartmentId = other.optionalAssignedDepartmentId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TicketCriteria copy() {
        return new TicketCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public Optional<StringFilter> optionalTitle() {
        return Optional.ofNullable(title);
    }

    public StringFilter title() {
        if (title == null) {
            setTitle(new StringFilter());
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public TicketStatusFilter getStatus() {
        return status;
    }

    public Optional<TicketStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public TicketStatusFilter status() {
        if (status == null) {
            setStatus(new TicketStatusFilter());
        }
        return status;
    }

    public void setStatus(TicketStatusFilter status) {
        this.status = status;
    }

    public PriorityFilter getPriority() {
        return priority;
    }

    public Optional<PriorityFilter> optionalPriority() {
        return Optional.ofNullable(priority);
    }

    public PriorityFilter priority() {
        if (priority == null) {
            setPriority(new PriorityFilter());
        }
        return priority;
    }

    public void setPriority(PriorityFilter priority) {
        this.priority = priority;
    }

    public VisibilityFilter getVisibility() {
        return visibility;
    }

    public Optional<VisibilityFilter> optionalVisibility() {
        return Optional.ofNullable(visibility);
    }

    public VisibilityFilter visibility() {
        if (visibility == null) {
            setVisibility(new VisibilityFilter());
        }
        return visibility;
    }

    public void setVisibility(VisibilityFilter visibility) {
        this.visibility = visibility;
    }

    public TicketCategoryFilter getCategory() {
        return category;
    }

    public Optional<TicketCategoryFilter> optionalCategory() {
        return Optional.ofNullable(category);
    }

    public TicketCategoryFilter category() {
        if (category == null) {
            setCategory(new TicketCategoryFilter());
        }
        return category;
    }

    public void setCategory(TicketCategoryFilter category) {
        this.category = category;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<InstantFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new InstantFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
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

    public InstantFilter getExpectedResolutionDate() {
        return expectedResolutionDate;
    }

    public Optional<InstantFilter> optionalExpectedResolutionDate() {
        return Optional.ofNullable(expectedResolutionDate);
    }

    public InstantFilter expectedResolutionDate() {
        if (expectedResolutionDate == null) {
            setExpectedResolutionDate(new InstantFilter());
        }
        return expectedResolutionDate;
    }

    public void setExpectedResolutionDate(InstantFilter expectedResolutionDate) {
        this.expectedResolutionDate = expectedResolutionDate;
    }

    public InstantFilter getResolvedDate() {
        return resolvedDate;
    }

    public Optional<InstantFilter> optionalResolvedDate() {
        return Optional.ofNullable(resolvedDate);
    }

    public InstantFilter resolvedDate() {
        if (resolvedDate == null) {
            setResolvedDate(new InstantFilter());
        }
        return resolvedDate;
    }

    public void setResolvedDate(InstantFilter resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public BooleanFilter getAiDuplicate() {
        return aiDuplicate;
    }

    public Optional<BooleanFilter> optionalAiDuplicate() {
        return Optional.ofNullable(aiDuplicate);
    }

    public BooleanFilter aiDuplicate() {
        if (aiDuplicate == null) {
            setAiDuplicate(new BooleanFilter());
        }
        return aiDuplicate;
    }

    public void setAiDuplicate(BooleanFilter aiDuplicate) {
        this.aiDuplicate = aiDuplicate;
    }

    public DoubleFilter getDuplicateScore() {
        return duplicateScore;
    }

    public Optional<DoubleFilter> optionalDuplicateScore() {
        return Optional.ofNullable(duplicateScore);
    }

    public DoubleFilter duplicateScore() {
        if (duplicateScore == null) {
            setDuplicateScore(new DoubleFilter());
        }
        return duplicateScore;
    }

    public void setDuplicateScore(DoubleFilter duplicateScore) {
        this.duplicateScore = duplicateScore;
    }

    public DoubleFilter getAiConfidence() {
        return aiConfidence;
    }

    public Optional<DoubleFilter> optionalAiConfidence() {
        return Optional.ofNullable(aiConfidence);
    }

    public DoubleFilter aiConfidence() {
        if (aiConfidence == null) {
            setAiConfidence(new DoubleFilter());
        }
        return aiConfidence;
    }

    public void setAiConfidence(DoubleFilter aiConfidence) {
        this.aiConfidence = aiConfidence;
    }

    public LongFilter getDuplicateTicketId() {
        return duplicateTicketId;
    }

    public Optional<LongFilter> optionalDuplicateTicketId() {
        return Optional.ofNullable(duplicateTicketId);
    }

    public LongFilter duplicateTicketId() {
        if (duplicateTicketId == null) {
            setDuplicateTicketId(new LongFilter());
        }
        return duplicateTicketId;
    }

    public void setDuplicateTicketId(LongFilter duplicateTicketId) {
        this.duplicateTicketId = duplicateTicketId;
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

    public LongFilter getReportedById() {
        return reportedById;
    }

    public Optional<LongFilter> optionalReportedById() {
        return Optional.ofNullable(reportedById);
    }

    public LongFilter reportedById() {
        if (reportedById == null) {
            setReportedById(new LongFilter());
        }
        return reportedById;
    }

    public void setReportedById(LongFilter reportedById) {
        this.reportedById = reportedById;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public Optional<LongFilter> optionalLocationId() {
        return Optional.ofNullable(locationId);
    }

    public LongFilter locationId() {
        if (locationId == null) {
            setLocationId(new LongFilter());
        }
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getWardId() {
        return wardId;
    }

    public Optional<LongFilter> optionalWardId() {
        return Optional.ofNullable(wardId);
    }

    public LongFilter wardId() {
        if (wardId == null) {
            setWardId(new LongFilter());
        }
        return wardId;
    }

    public void setWardId(LongFilter wardId) {
        this.wardId = wardId;
    }

    public LongFilter getAssignedDepartmentId() {
        return assignedDepartmentId;
    }

    public Optional<LongFilter> optionalAssignedDepartmentId() {
        return Optional.ofNullable(assignedDepartmentId);
    }

    public LongFilter assignedDepartmentId() {
        if (assignedDepartmentId == null) {
            setAssignedDepartmentId(new LongFilter());
        }
        return assignedDepartmentId;
    }

    public void setAssignedDepartmentId(LongFilter assignedDepartmentId) {
        this.assignedDepartmentId = assignedDepartmentId;
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
        final TicketCriteria that = (TicketCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(status, that.status) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(visibility, that.visibility) &&
            Objects.equals(category, that.category) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(expectedResolutionDate, that.expectedResolutionDate) &&
            Objects.equals(resolvedDate, that.resolvedDate) &&
            Objects.equals(aiDuplicate, that.aiDuplicate) &&
            Objects.equals(duplicateScore, that.duplicateScore) &&
            Objects.equals(aiConfidence, that.aiConfidence) &&
            Objects.equals(duplicateTicketId, that.duplicateTicketId) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(deletedDate, that.deletedDate) &&
            Objects.equals(reportedById, that.reportedById) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(wardId, that.wardId) &&
            Objects.equals(assignedDepartmentId, that.assignedDepartmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            status,
            priority,
            visibility,
            category,
            createdDate,
            updatedDate,
            expectedResolutionDate,
            resolvedDate,
            aiDuplicate,
            duplicateScore,
            aiConfidence,
            duplicateTicketId,
            deleted,
            deletedDate,
            reportedById,
            locationId,
            wardId,
            assignedDepartmentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalTitle().map(f -> "title=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalPriority().map(f -> "priority=" + f + ", ").orElse("") +
            optionalVisibility().map(f -> "visibility=" + f + ", ").orElse("") +
            optionalCategory().map(f -> "category=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalUpdatedDate().map(f -> "updatedDate=" + f + ", ").orElse("") +
            optionalExpectedResolutionDate().map(f -> "expectedResolutionDate=" + f + ", ").orElse("") +
            optionalResolvedDate().map(f -> "resolvedDate=" + f + ", ").orElse("") +
            optionalAiDuplicate().map(f -> "aiDuplicate=" + f + ", ").orElse("") +
            optionalDuplicateScore().map(f -> "duplicateScore=" + f + ", ").orElse("") +
            optionalAiConfidence().map(f -> "aiConfidence=" + f + ", ").orElse("") +
            optionalDuplicateTicketId().map(f -> "duplicateTicketId=" + f + ", ").orElse("") +
            optionalDeleted().map(f -> "deleted=" + f + ", ").orElse("") +
            optionalDeletedDate().map(f -> "deletedDate=" + f + ", ").orElse("") +
            optionalReportedById().map(f -> "reportedById=" + f + ", ").orElse("") +
            optionalLocationId().map(f -> "locationId=" + f + ", ").orElse("") +
            optionalWardId().map(f -> "wardId=" + f + ", ").orElse("") +
            optionalAssignedDepartmentId().map(f -> "assignedDepartmentId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
