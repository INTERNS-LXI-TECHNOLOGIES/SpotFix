package com.diviso.spot_fix.domain;

import com.diviso.spot_fix.domain.enumeration.Priority;
import com.diviso.spot_fix.domain.enumeration.TicketCategory;
import com.diviso.spot_fix.domain.enumeration.TicketStatus;
import com.diviso.spot_fix.domain.enumeration.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ticket implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private Visibility visibility;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private TicketCategory category;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "expected_resolution_date")
    private Instant expectedResolutionDate;

    @Column(name = "resolved_date")
    private Instant resolvedDate;

    @Lob
    @Column(name = "ai_summary")
    private String aiSummary;

    @Column(name = "ai_duplicate")
    private Boolean aiDuplicate;

    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    @Column(name = "duplicate_score")
    private Double duplicateScore;

    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    @Column(name = "ai_confidence")
    private Double aiConfidence;

    @Column(name = "duplicate_ticket_id")
    private Long duplicateTicketId;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "deleted_date")
    private Instant deletedDate;

    @ManyToOne(optional = false)
    @NotNull
    private User reportedBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "ward" }, allowSetters = true)
    private Location location;

    @ManyToOne(optional = false)
    @NotNull
    private Ward ward;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department assignedDepartment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ticket id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Ticket title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Ticket description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return this.status;
    }

    public Ticket status(TicketStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public Ticket priority(Priority priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public Ticket visibility(Visibility visibility) {
        this.setVisibility(visibility);
        return this;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public TicketCategory getCategory() {
        return this.category;
    }

    public Ticket category(TicketCategory category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(TicketCategory category) {
        this.category = category;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Ticket createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Ticket updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Instant getExpectedResolutionDate() {
        return this.expectedResolutionDate;
    }

    public Ticket expectedResolutionDate(Instant expectedResolutionDate) {
        this.setExpectedResolutionDate(expectedResolutionDate);
        return this;
    }

    public void setExpectedResolutionDate(Instant expectedResolutionDate) {
        this.expectedResolutionDate = expectedResolutionDate;
    }

    public Instant getResolvedDate() {
        return this.resolvedDate;
    }

    public Ticket resolvedDate(Instant resolvedDate) {
        this.setResolvedDate(resolvedDate);
        return this;
    }

    public void setResolvedDate(Instant resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getAiSummary() {
        return this.aiSummary;
    }

    public Ticket aiSummary(String aiSummary) {
        this.setAiSummary(aiSummary);
        return this;
    }

    public void setAiSummary(String aiSummary) {
        this.aiSummary = aiSummary;
    }

    public Boolean getAiDuplicate() {
        return this.aiDuplicate;
    }

    public Ticket aiDuplicate(Boolean aiDuplicate) {
        this.setAiDuplicate(aiDuplicate);
        return this;
    }

    public void setAiDuplicate(Boolean aiDuplicate) {
        this.aiDuplicate = aiDuplicate;
    }

    public Double getDuplicateScore() {
        return this.duplicateScore;
    }

    public Ticket duplicateScore(Double duplicateScore) {
        this.setDuplicateScore(duplicateScore);
        return this;
    }

    public void setDuplicateScore(Double duplicateScore) {
        this.duplicateScore = duplicateScore;
    }

    public Double getAiConfidence() {
        return this.aiConfidence;
    }

    public Ticket aiConfidence(Double aiConfidence) {
        this.setAiConfidence(aiConfidence);
        return this;
    }

    public void setAiConfidence(Double aiConfidence) {
        this.aiConfidence = aiConfidence;
    }

    public Long getDuplicateTicketId() {
        return this.duplicateTicketId;
    }

    public Ticket duplicateTicketId(Long duplicateTicketId) {
        this.setDuplicateTicketId(duplicateTicketId);
        return this;
    }

    public void setDuplicateTicketId(Long duplicateTicketId) {
        this.duplicateTicketId = duplicateTicketId;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public Ticket deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getDeletedDate() {
        return this.deletedDate;
    }

    public Ticket deletedDate(Instant deletedDate) {
        this.setDeletedDate(deletedDate);
        return this;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
    }

    public User getReportedBy() {
        return this.reportedBy;
    }

    public void setReportedBy(User user) {
        this.reportedBy = user;
    }

    public Ticket reportedBy(User user) {
        this.setReportedBy(user);
        return this;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Ticket location(Location location) {
        this.setLocation(location);
        return this;
    }

    public Ward getWard() {
        return this.ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public Ticket ward(Ward ward) {
        this.setWard(ward);
        return this;
    }

    public Department getAssignedDepartment() {
        return this.assignedDepartment;
    }

    public void setAssignedDepartment(Department department) {
        this.assignedDepartment = department;
    }

    public Ticket assignedDepartment(Department department) {
        this.setAssignedDepartment(department);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return getId() != null && getId().equals(((Ticket) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ticket{" +
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
            "}";
    }
}
