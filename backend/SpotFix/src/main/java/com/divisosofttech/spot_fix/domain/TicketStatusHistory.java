package com.divisosofttech.spot_fix.domain;

import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A TicketStatusHistory.
 */
@Table("ticket_status_history")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketStatusHistory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("old_status")
    private TicketStatus oldStatus;

    @NotNull(message = "must not be null")
    @Column("new_status")
    private TicketStatus newStatus;

    @NotNull(message = "must not be null")
    @Column("changed_date")
    private Instant changedDate;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "reportedBy", "location", "ward", "assignedDepartment" }, allowSetters = true)
    private Ticket ticket;

    @org.springframework.data.annotation.Transient
    private User changedBy;

    @Column("ticket_id")
    private Long ticketId;

    @Column("changed_by_id")
    private Long changedById;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TicketStatusHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketStatus getOldStatus() {
        return this.oldStatus;
    }

    public TicketStatusHistory oldStatus(TicketStatus oldStatus) {
        this.setOldStatus(oldStatus);
        return this;
    }

    public void setOldStatus(TicketStatus oldStatus) {
        this.oldStatus = oldStatus;
    }

    public TicketStatus getNewStatus() {
        return this.newStatus;
    }

    public TicketStatusHistory newStatus(TicketStatus newStatus) {
        this.setNewStatus(newStatus);
        return this;
    }

    public void setNewStatus(TicketStatus newStatus) {
        this.newStatus = newStatus;
    }

    public Instant getChangedDate() {
        return this.changedDate;
    }

    public TicketStatusHistory changedDate(Instant changedDate) {
        this.setChangedDate(changedDate);
        return this;
    }

    public void setChangedDate(Instant changedDate) {
        this.changedDate = changedDate;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        this.ticketId = ticket != null ? ticket.getId() : null;
    }

    public TicketStatusHistory ticket(Ticket ticket) {
        this.setTicket(ticket);
        return this;
    }

    public User getChangedBy() {
        return this.changedBy;
    }

    public void setChangedBy(User user) {
        this.changedBy = user;
        this.changedById = user != null ? user.getId() : null;
    }

    public TicketStatusHistory changedBy(User user) {
        this.setChangedBy(user);
        return this;
    }

    public Long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(Long ticket) {
        this.ticketId = ticket;
    }

    public Long getChangedById() {
        return this.changedById;
    }

    public void setChangedById(Long user) {
        this.changedById = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketStatusHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((TicketStatusHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketStatusHistory{" +
            "id=" + getId() +
            ", oldStatus='" + getOldStatus() + "'" +
            ", newStatus='" + getNewStatus() + "'" +
            ", changedDate='" + getChangedDate() + "'" +
            "}";
    }
}
