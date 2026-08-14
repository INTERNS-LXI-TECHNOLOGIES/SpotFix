package com.divisosofttech.spot_fix.service.dto;

import com.divisosofttech.spot_fix.domain.enumeration.VoteType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.divisosofttech.spot_fix.domain.TicketVote} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketVoteDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private VoteType voteType;

    @NotNull(message = "must not be null")
    private Instant createdDate;

    @NotNull
    private TicketDTO ticket;

    @NotNull
    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketVoteDTO)) {
            return false;
        }

        TicketVoteDTO ticketVoteDTO = (TicketVoteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketVoteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketVoteDTO{" +
            "id=" + getId() +
            ", voteType='" + getVoteType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", ticket=" + getTicket() +
            ", user=" + getUser() +
            "}";
    }
}
