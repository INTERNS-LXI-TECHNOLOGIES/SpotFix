package com.diviso.spot_fix.service.criteria;

import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.diviso.spot_fix.domain.TicketStatusHistory} entity. This class is used
 * in {@link com.diviso.spot_fix.web.rest.TicketStatusHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ticket-status-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketStatusHistoryCriteria implements Serializable, Criteria {

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

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TicketStatusFilter oldStatus;

    private TicketStatusFilter newStatus;

    private InstantFilter changedDate;

    private LongFilter ticketId;

    private LongFilter changedById;

    private Boolean distinct;

    public TicketStatusHistoryCriteria() {}

    public TicketStatusHistoryCriteria(TicketStatusHistoryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.oldStatus = other.optionalOldStatus().map(TicketStatusFilter::copy).orElse(null);
        this.newStatus = other.optionalNewStatus().map(TicketStatusFilter::copy).orElse(null);
        this.changedDate = other.optionalChangedDate().map(InstantFilter::copy).orElse(null);
        this.ticketId = other.optionalTicketId().map(LongFilter::copy).orElse(null);
        this.changedById = other.optionalChangedById().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TicketStatusHistoryCriteria copy() {
        return new TicketStatusHistoryCriteria(this);
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

    public TicketStatusFilter getOldStatus() {
        return oldStatus;
    }

    public Optional<TicketStatusFilter> optionalOldStatus() {
        return Optional.ofNullable(oldStatus);
    }

    public TicketStatusFilter oldStatus() {
        if (oldStatus == null) {
            setOldStatus(new TicketStatusFilter());
        }
        return oldStatus;
    }

    public void setOldStatus(TicketStatusFilter oldStatus) {
        this.oldStatus = oldStatus;
    }

    public TicketStatusFilter getNewStatus() {
        return newStatus;
    }

    public Optional<TicketStatusFilter> optionalNewStatus() {
        return Optional.ofNullable(newStatus);
    }

    public TicketStatusFilter newStatus() {
        if (newStatus == null) {
            setNewStatus(new TicketStatusFilter());
        }
        return newStatus;
    }

    public void setNewStatus(TicketStatusFilter newStatus) {
        this.newStatus = newStatus;
    }

    public InstantFilter getChangedDate() {
        return changedDate;
    }

    public Optional<InstantFilter> optionalChangedDate() {
        return Optional.ofNullable(changedDate);
    }

    public InstantFilter changedDate() {
        if (changedDate == null) {
            setChangedDate(new InstantFilter());
        }
        return changedDate;
    }

    public void setChangedDate(InstantFilter changedDate) {
        this.changedDate = changedDate;
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

    public LongFilter getChangedById() {
        return changedById;
    }

    public Optional<LongFilter> optionalChangedById() {
        return Optional.ofNullable(changedById);
    }

    public LongFilter changedById() {
        if (changedById == null) {
            setChangedById(new LongFilter());
        }
        return changedById;
    }

    public void setChangedById(LongFilter changedById) {
        this.changedById = changedById;
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
        final TicketStatusHistoryCriteria that = (TicketStatusHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(oldStatus, that.oldStatus) &&
            Objects.equals(newStatus, that.newStatus) &&
            Objects.equals(changedDate, that.changedDate) &&
            Objects.equals(ticketId, that.ticketId) &&
            Objects.equals(changedById, that.changedById) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oldStatus, newStatus, changedDate, ticketId, changedById, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketStatusHistoryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalOldStatus().map(f -> "oldStatus=" + f + ", ").orElse("") +
            optionalNewStatus().map(f -> "newStatus=" + f + ", ").orElse("") +
            optionalChangedDate().map(f -> "changedDate=" + f + ", ").orElse("") +
            optionalTicketId().map(f -> "ticketId=" + f + ", ").orElse("") +
            optionalChangedById().map(f -> "changedById=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
