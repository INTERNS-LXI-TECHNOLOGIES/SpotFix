package com.divisosofttech.spot_fix.service.dto;

import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.divisosofttech.spot_fix.domain.TicketStatusHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketStatusHistoryDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private TicketStatus oldStatus;

    @NotNull(message = "must not be null")
    private TicketStatus newStatus;

    @NotNull(message = "must not be null")
    private Instant changedDate;

    @NotNull
    private TicketDTO ticket;

    @NotNull
    private UserDTO changedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketStatus getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(TicketStatus oldStatus) {
        this.oldStatus = oldStatus;
    }

    public TicketStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(TicketStatus newStatus) {
        this.newStatus = newStatus;
    }

    public Instant getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(Instant changedDate) {
        this.changedDate = changedDate;
    }

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
    }

    public UserDTO getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(UserDTO changedBy) {
        this.changedBy = changedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketStatusHistoryDTO)) {
            return false;
        }

        TicketStatusHistoryDTO ticketStatusHistoryDTO = (TicketStatusHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketStatusHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketStatusHistoryDTO{" +
            "id=" + getId() +
            ", oldStatus='" + getOldStatus() + "'" +
            ", newStatus='" + getNewStatus() + "'" +
            ", changedDate='" + getChangedDate() + "'" +
            ", ticket=" + getTicket() +
            ", changedBy=" + getChangedBy() +
            "}";
    }
}
