package com.divisosofttech.spot_fix.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.divisosofttech.spot_fix.domain.Comment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String title;

    @Lob
    private String content;

    @NotNull(message = "must not be null")
    private Instant createdDate;

    private Instant updatedDate;

    @NotNull(message = "must not be null")
    private Boolean deleted;

    @NotNull(message = "must not be null")
    private Instant deletedDate;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
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
        if (!(o instanceof CommentDTO)) {
            return false;
        }

        CommentDTO commentDTO = (CommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            ", ticket=" + getTicket() +
            ", user=" + getUser() +
            "}";
    }
}
