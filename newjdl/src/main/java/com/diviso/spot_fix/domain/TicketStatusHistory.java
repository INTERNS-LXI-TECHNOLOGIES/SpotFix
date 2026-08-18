package com.diviso.spot_fix.domain;

import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TicketStatusHistory.
 */
@Entity
@Table(name = "ticket_status_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketStatusHistory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", nullable = false)
    private TicketStatus oldStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private TicketStatus newStatus;

    @NotNull
    @Column(name = "changed_date", nullable = false)
    private Instant changedDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "reportedBy", "location", "ward", "assignedDepartment" }, allowSetters = true)
    private Ticket ticket;

    @ManyToOne(optional = false)
    @NotNull
    private User changedBy;

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
    }

    public TicketStatusHistory changedBy(User user) {
        this.setChangedBy(user);
        return this;
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
