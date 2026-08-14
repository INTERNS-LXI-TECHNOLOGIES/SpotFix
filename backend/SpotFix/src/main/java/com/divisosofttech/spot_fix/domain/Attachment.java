package com.divisosofttech.spot_fix.domain;

import com.divisosofttech.spot_fix.domain.enumeration.AttachmentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Attachment.
 */
@Table("attachment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Attachment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("attachment_type")
    private AttachmentType attachmentType;

    @NotNull(message = "must not be null")
    @Size(max = 255)
    @Column("file_name")
    private String fileName;

    @NotNull(message = "must not be null")
    @Size(max = 500)
    @Column("file_path")
    private String filePath;

    @Size(max = 50)
    @Column("file_type")
    private String fileType;

    @Min(value = 0L)
    @Column("file_size")
    private Long fileSize;

    @Size(max = 64)
    @Column("checksum")
    private String checksum;

    @NotNull(message = "must not be null")
    @Column("uploaded_date")
    private Instant uploadedDate;

    @Column("transcript")
    private String transcript;

    @Min(value = 0)
    @Column("duration_seconds")
    private Integer durationSeconds;

    @Size(max = 10)
    @Column("language")
    private String language;

    @NotNull(message = "must not be null")
    @Column("deleted")
    private Boolean deleted;

    @Column("updated_date")
    private Instant updatedDate;

    @Column("deleted_date")
    private Instant deletedDate;

    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "reportedBy", "location", "ward", "assignedDepartment" }, allowSetters = true)
    private Ticket ticket;

    @org.springframework.data.annotation.Transient
    private User uploadedBy;

    @Column("ticket_id")
    private Long ticketId;

    @Column("uploaded_by_id")
    private Long uploadedById;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Attachment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttachmentType getAttachmentType() {
        return this.attachmentType;
    }

    public Attachment attachmentType(AttachmentType attachmentType) {
        this.setAttachmentType(attachmentType);
        return this;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Attachment fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Attachment filePath(String filePath) {
        this.setFilePath(filePath);
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return this.fileType;
    }

    public Attachment fileType(String fileType) {
        this.setFileType(fileType);
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public Attachment fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getChecksum() {
        return this.checksum;
    }

    public Attachment checksum(String checksum) {
        this.setChecksum(checksum);
        return this;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Instant getUploadedDate() {
        return this.uploadedDate;
    }

    public Attachment uploadedDate(Instant uploadedDate) {
        this.setUploadedDate(uploadedDate);
        return this;
    }

    public void setUploadedDate(Instant uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getTranscript() {
        return this.transcript;
    }

    public Attachment transcript(String transcript) {
        this.setTranscript(transcript);
        return this;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public Integer getDurationSeconds() {
        return this.durationSeconds;
    }

    public Attachment durationSeconds(Integer durationSeconds) {
        this.setDurationSeconds(durationSeconds);
        return this;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getLanguage() {
        return this.language;
    }

    public Attachment language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Attachment deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Attachment updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Instant getDeletedDate() {
        return this.deletedDate;
    }

    public Attachment deletedDate(Instant deletedDate) {
        this.setDeletedDate(deletedDate);
        return this;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
        this.ticketId = ticket != null ? ticket.getId() : null;
    }

    public Attachment ticket(Ticket ticket) {
        this.setTicket(ticket);
        return this;
    }

    public User getUploadedBy() {
        return this.uploadedBy;
    }

    public void setUploadedBy(User user) {
        this.uploadedBy = user;
        this.uploadedById = user != null ? user.getId() : null;
    }

    public Attachment uploadedBy(User user) {
        this.setUploadedBy(user);
        return this;
    }

    public Long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(Long ticket) {
        this.ticketId = ticket;
    }

    public Long getUploadedById() {
        return this.uploadedById;
    }

    public void setUploadedById(Long user) {
        this.uploadedById = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        return getId() != null && getId().equals(((Attachment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + getId() +
            ", attachmentType='" + getAttachmentType() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", fileSize=" + getFileSize() +
            ", checksum='" + getChecksum() + "'" +
            ", uploadedDate='" + getUploadedDate() + "'" +
            ", transcript='" + getTranscript() + "'" +
            ", durationSeconds=" + getDurationSeconds() +
            ", language='" + getLanguage() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
