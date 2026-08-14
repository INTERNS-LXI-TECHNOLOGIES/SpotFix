package com.divisosofttech.spot_fix.domain;

import com.divisosofttech.spot_fix.domain.enumeration.VoteType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A TicketVote.
 */
@Table("ticket_vote")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketVote implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("vote_type")
    private VoteType voteType;

    @NotNull(message = "must not be null")
    @Column("created_date")
    private Instant createdDate;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "reportedBy", "location", "ward", "assignedDepartment" }, allowSetters = true)
    private Ticket ticket;

    @org.springframework.data.annotation.Transient
    private User user;

    @Column("ticket_id")
    private Long ticketId;

    @Column("user_id")
    private Long userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TicketVote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoteType getVoteType() {
        return this.voteType;
    }

    public TicketVote voteType(VoteType voteType) {
        this.setVoteType(voteType);
        return this;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public TicketVote createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        this.ticketId = ticket != null ? ticket.getId() : null;
    }

    public TicketVote ticket(Ticket ticket) {
        this.setTicket(ticket);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userId = user != null ? user.getId() : null;
    }

    public TicketVote user(User user) {
        this.setUser(user);
        return this;
    }

    public Long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(Long ticket) {
        this.ticketId = ticket;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long user) {
        this.userId = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketVote)) {
            return false;
        }
        return getId() != null && getId().equals(((TicketVote) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketVote{" +
            "id=" + getId() +
            ", voteType='" + getVoteType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
