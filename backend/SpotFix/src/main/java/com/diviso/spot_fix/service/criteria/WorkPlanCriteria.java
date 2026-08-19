package com.diviso.spot_fix.service.criteria;

import com.diviso.spot_fix.domain.enumeration.WorkStatus;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.diviso.spot_fix.domain.WorkPlan} entity. This class is used
 * in {@link com.diviso.spot_fix.web.rest.WorkPlanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /work-plans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkPlanCriteria implements Serializable, Criteria {

    /**
     * Class for filtering WorkStatus
     */
    public static class WorkStatusFilter extends Filter<WorkStatus> {

        public WorkStatusFilter() {}

        public WorkStatusFilter(WorkStatusFilter filter) {
            super(filter);
        }

        @Override
        public WorkStatusFilter copy() {
            return new WorkStatusFilter(this);
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter estimatedCost;

    private InstantFilter startedDate;

    private InstantFilter expectedCompletionDate;

    private InstantFilter actualCompletionDate;

    private IntegerFilter completionPercentage;

    private WorkStatusFilter status;

    private BooleanFilter deleted;

    private InstantFilter deletedDate;

    private LongFilter ticketId;

    private LongFilter departmentId;

    private Boolean distinct;

    public WorkPlanCriteria() {}

    public WorkPlanCriteria(WorkPlanCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.estimatedCost = other.optionalEstimatedCost().map(BigDecimalFilter::copy).orElse(null);
        this.startedDate = other.optionalStartedDate().map(InstantFilter::copy).orElse(null);
        this.expectedCompletionDate = other.optionalExpectedCompletionDate().map(InstantFilter::copy).orElse(null);
        this.actualCompletionDate = other.optionalActualCompletionDate().map(InstantFilter::copy).orElse(null);
        this.completionPercentage = other.optionalCompletionPercentage().map(IntegerFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(WorkStatusFilter::copy).orElse(null);
        this.deleted = other.optionalDeleted().map(BooleanFilter::copy).orElse(null);
        this.deletedDate = other.optionalDeletedDate().map(InstantFilter::copy).orElse(null);
        this.ticketId = other.optionalTicketId().map(LongFilter::copy).orElse(null);
        this.departmentId = other.optionalDepartmentId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public WorkPlanCriteria copy() {
        return new WorkPlanCriteria(this);
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

    public BigDecimalFilter getEstimatedCost() {
        return estimatedCost;
    }

    public Optional<BigDecimalFilter> optionalEstimatedCost() {
        return Optional.ofNullable(estimatedCost);
    }

    public BigDecimalFilter estimatedCost() {
        if (estimatedCost == null) {
            setEstimatedCost(new BigDecimalFilter());
        }
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimalFilter estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public InstantFilter getStartedDate() {
        return startedDate;
    }

    public Optional<InstantFilter> optionalStartedDate() {
        return Optional.ofNullable(startedDate);
    }

    public InstantFilter startedDate() {
        if (startedDate == null) {
            setStartedDate(new InstantFilter());
        }
        return startedDate;
    }

    public void setStartedDate(InstantFilter startedDate) {
        this.startedDate = startedDate;
    }

    public InstantFilter getExpectedCompletionDate() {
        return expectedCompletionDate;
    }

    public Optional<InstantFilter> optionalExpectedCompletionDate() {
        return Optional.ofNullable(expectedCompletionDate);
    }

    public InstantFilter expectedCompletionDate() {
        if (expectedCompletionDate == null) {
            setExpectedCompletionDate(new InstantFilter());
        }
        return expectedCompletionDate;
    }

    public void setExpectedCompletionDate(InstantFilter expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }

    public InstantFilter getActualCompletionDate() {
        return actualCompletionDate;
    }

    public Optional<InstantFilter> optionalActualCompletionDate() {
        return Optional.ofNullable(actualCompletionDate);
    }

    public InstantFilter actualCompletionDate() {
        if (actualCompletionDate == null) {
            setActualCompletionDate(new InstantFilter());
        }
        return actualCompletionDate;
    }

    public void setActualCompletionDate(InstantFilter actualCompletionDate) {
        this.actualCompletionDate = actualCompletionDate;
    }

    public IntegerFilter getCompletionPercentage() {
        return completionPercentage;
    }

    public Optional<IntegerFilter> optionalCompletionPercentage() {
        return Optional.ofNullable(completionPercentage);
    }

    public IntegerFilter completionPercentage() {
        if (completionPercentage == null) {
            setCompletionPercentage(new IntegerFilter());
        }
        return completionPercentage;
    }

    public void setCompletionPercentage(IntegerFilter completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public WorkStatusFilter getStatus() {
        return status;
    }

    public Optional<WorkStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public WorkStatusFilter status() {
        if (status == null) {
            setStatus(new WorkStatusFilter());
        }
        return status;
    }

    public void setStatus(WorkStatusFilter status) {
        this.status = status;
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

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public Optional<LongFilter> optionalDepartmentId() {
        return Optional.ofNullable(departmentId);
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            setDepartmentId(new LongFilter());
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
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
        final WorkPlanCriteria that = (WorkPlanCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(estimatedCost, that.estimatedCost) &&
            Objects.equals(startedDate, that.startedDate) &&
            Objects.equals(expectedCompletionDate, that.expectedCompletionDate) &&
            Objects.equals(actualCompletionDate, that.actualCompletionDate) &&
            Objects.equals(completionPercentage, that.completionPercentage) &&
            Objects.equals(status, that.status) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(deletedDate, that.deletedDate) &&
            Objects.equals(ticketId, that.ticketId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            estimatedCost,
            startedDate,
            expectedCompletionDate,
            actualCompletionDate,
            completionPercentage,
            status,
            deleted,
            deletedDate,
            ticketId,
            departmentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkPlanCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalEstimatedCost().map(f -> "estimatedCost=" + f + ", ").orElse("") +
            optionalStartedDate().map(f -> "startedDate=" + f + ", ").orElse("") +
            optionalExpectedCompletionDate().map(f -> "expectedCompletionDate=" + f + ", ").orElse("") +
            optionalActualCompletionDate().map(f -> "actualCompletionDate=" + f + ", ").orElse("") +
            optionalCompletionPercentage().map(f -> "completionPercentage=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalDeleted().map(f -> "deleted=" + f + ", ").orElse("") +
            optionalDeletedDate().map(f -> "deletedDate=" + f + ", ").orElse("") +
            optionalTicketId().map(f -> "ticketId=" + f + ", ").orElse("") +
            optionalDepartmentId().map(f -> "departmentId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
