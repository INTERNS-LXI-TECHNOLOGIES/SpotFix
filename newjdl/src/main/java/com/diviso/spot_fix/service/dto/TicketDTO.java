package com.diviso.spot_fix.service.dto;

import com.diviso.spot_fix.domain.enumeration.Priority;
import com.diviso.spot_fix.domain.enumeration.TicketCategory;
import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import com.diviso.spot_fix.domain.enumeration.Visibility;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.spot_fix.domain.Ticket} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String title;

    @Lob
    private String description;

    @NotNull
    private TicketStatus status;

    @NotNull
    private Priority priority;

    @NotNull
    private Visibility visibility;

    @NotNull
    private TicketCategory category;

    @NotNull
    private Instant createdDate;

    private Instant updatedDate;

    private Instant expectedResolutionDate;

    private Instant resolvedDate;

    @Lob
    private String aiSummary;

    private Boolean aiDuplicate;

    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    private Double duplicateScore;

    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    private Double aiConfidence;

    private Long duplicateTicketId;

    @NotNull
    private Boolean deleted;

    private Instant deletedDate;

    @NotNull
    private UserDTO reportedBy;

    @NotNull
    private LocationDTO location;

    @NotNull
    private WardDTO ward;

    private DepartmentDTO assignedDepartment;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public void setCategory(TicketCategory category) {
        this.category = category;
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

    public Instant getExpectedResolutionDate() {
        return expectedResolutionDate;
    }

    public void setExpectedResolutionDate(Instant expectedResolutionDate) {
        this.expectedResolutionDate = expectedResolutionDate;
    }

    public Instant getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Instant resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getAiSummary() {
        return aiSummary;
    }

    public void setAiSummary(String aiSummary) {
        this.aiSummary = aiSummary;
    }

    public Boolean getAiDuplicate() {
        return aiDuplicate;
    }

    public void setAiDuplicate(Boolean aiDuplicate) {
        this.aiDuplicate = aiDuplicate;
    }

    public Double getDuplicateScore() {
        return duplicateScore;
    }

    public void setDuplicateScore(Double duplicateScore) {
        this.duplicateScore = duplicateScore;
    }

    public Double getAiConfidence() {
        return aiConfidence;
    }

    public void setAiConfidence(Double aiConfidence) {
        this.aiConfidence = aiConfidence;
    }

    public Long getDuplicateTicketId() {
        return duplicateTicketId;
    }

    public void setDuplicateTicketId(Long duplicateTicketId) {
        this.duplicateTicketId = duplicateTicketId;
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

    public UserDTO getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(UserDTO reportedBy) {
        this.reportedBy = reportedBy;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public WardDTO getWard() {
        return ward;
    }

    public void setWard(WardDTO ward) {
        this.ward = ward;
    }

    public DepartmentDTO getAssignedDepartment() {
        return assignedDepartment;
    }

    public void setAssignedDepartment(DepartmentDTO assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketDTO)) {
            return false;
        }

        TicketDTO ticketDTO = (TicketDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", visibility='" + getVisibility() + "'" +
            ", category='" + getCategory() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", expectedResolutionDate='" + getExpectedResolutionDate() + "'" +
            ", resolvedDate='" + getResolvedDate() + "'" +
            ", aiSummary='" + getAiSummary() + "'" +
            ", aiDuplicate='" + getAiDuplicate() + "'" +
            ", duplicateScore=" + getDuplicateScore() +
            ", aiConfidence=" + getAiConfidence() +
            ", duplicateTicketId=" + getDuplicateTicketId() +
            ", deleted='" + getDeleted() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            ", reportedBy=" + getReportedBy() +
            ", location=" + getLocation() +
            ", ward=" + getWard() +
            ", assignedDepartment=" + getAssignedDepartment() +
            "}";
    }
}
