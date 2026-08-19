package com.diviso.spot_fix.service.criteria;

import com.diviso.spot_fix.domain.enumeration.VoteType;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.diviso.spot_fix.domain.TicketVote} entity. This class is used
 * in {@link com.diviso.spot_fix.web.rest.TicketVoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ticket-votes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketVoteCriteria implements Serializable, Criteria {

    /**
     * Class for filtering VoteType
     */
    public static class VoteTypeFilter extends Filter<VoteType> {

        public VoteTypeFilter() {}

        public VoteTypeFilter(VoteTypeFilter filter) {
            super(filter);
        }

        @Override
        public VoteTypeFilter copy() {
            return new VoteTypeFilter(this);
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private VoteTypeFilter voteType;

    private InstantFilter createdDate;

    private LongFilter ticketId;

    private LongFilter userId;

    private Boolean distinct;

    public TicketVoteCriteria() {}

    public TicketVoteCriteria(TicketVoteCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.voteType = other.optionalVoteType().map(VoteTypeFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.ticketId = other.optionalTicketId().map(LongFilter::copy).orElse(null);
        this.userId = other.optionalUserId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TicketVoteCriteria copy() {
        return new TicketVoteCriteria(this);
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

    public VoteTypeFilter getVoteType() {
        return voteType;
    }

    public Optional<VoteTypeFilter> optionalVoteType() {
        return Optional.ofNullable(voteType);
    }

    public VoteTypeFilter voteType() {
        if (voteType == null) {
            setVoteType(new VoteTypeFilter());
        }
        return voteType;
    }

    public void setVoteType(VoteTypeFilter voteType) {
        this.voteType = voteType;
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

    public LongFilter getUserId() {
        return userId;
    }

    public Optional<LongFilter> optionalUserId() {
        return Optional.ofNullable(userId);
    }

    public LongFilter userId() {
        if (userId == null) {
            setUserId(new LongFilter());
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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
        final TicketVoteCriteria that = (TicketVoteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(voteType, that.voteType) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(ticketId, that.ticketId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, voteType, createdDate, ticketId, userId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketVoteCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalVoteType().map(f -> "voteType=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalTicketId().map(f -> "ticketId=" + f + ", ").orElse("") +
            optionalUserId().map(f -> "userId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
