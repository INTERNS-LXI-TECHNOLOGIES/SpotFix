package com.divisosofttech.spot_fix.service.dto;

import com.divisosofttech.spot_fix.domain.enumeration.AttachmentType;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.divisosofttech.spot_fix.domain.Attachment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttachmentDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private AttachmentType attachmentType;

    @NotNull(message = "must not be null")
    @Size(max = 255)
    private String fileName;

    @NotNull(message = "must not be null")
    @Size(max = 500)
    private String filePath;

    @Size(max = 50)
    private String fileType;

    @Min(value = 0L)
    private Long fileSize;

    @Size(max = 64)
    private String checksum;

    @NotNull(message = "must not be null")
    private Instant uploadedDate;

    @Lob
    private String transcript;

    @Min(value = 0)
    private Integer durationSeconds;

    @Size(max = 10)
    private String language;

    @NotNull(message = "must not be null")
    private Boolean deleted;

    private Instant updatedDate;

    private Instant deletedDate;

    @NotNull
    private TicketDTO ticket;

    @NotNull
    private UserDTO uploadedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Instant getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Instant uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
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

    public UserDTO getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(UserDTO uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentDTO)) {
            return false;
        }

        AttachmentDTO attachmentDTO = (AttachmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attachmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentDTO{" +
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
            ", ticket=" + getTicket() +
            ", uploadedBy=" + getUploadedBy() +
            "}";
    }
}
