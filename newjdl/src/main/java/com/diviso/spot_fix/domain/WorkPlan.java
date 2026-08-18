package com.diviso.spot_fix.domain;

import com.diviso.spot_fix.domain.enumeration.WorkStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkPlan.
 */
@Entity
@Table(name = "work_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkPlan implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @DecimalMin(value = "0")
    @Column(name = "estimated_cost", precision = 21, scale = 2)
    private BigDecimal estimatedCost;

    @Column(name = "started_date")
    private Instant startedDate;

    @Column(name = "expected_completion_date")
    private Instant expectedCompletionDate;

    @Column(name = "actual_completion_date")
    private Instant actualCompletionDate;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "completion_percentage")
    private Integer completionPercentage;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WorkStatus status;

    @Lob
    @Column(name = "remarks")
    private String remarks;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "deleted_date")
    private Instant deletedDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "reportedBy", "location", "ward", "assignedDepartment" }, allowSetters = true)
    private Ticket ticket;

    @ManyToOne(optional = false)
    @NotNull
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkPlan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getEstimatedCost() {
        return this.estimatedCost;
    }

    public WorkPlan estimatedCost(BigDecimal estimatedCost) {
        this.setEstimatedCost(estimatedCost);
        return this;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Instant getStartedDate() {
        return this.startedDate;
    }

    public WorkPlan startedDate(Instant startedDate) {
        this.setStartedDate(startedDate);
        return this;
    }

    public void setStartedDate(Instant startedDate) {
        this.startedDate = startedDate;
    }

    public Instant getExpectedCompletionDate() {
        return this.expectedCompletionDate;
    }

    public WorkPlan expectedCompletionDate(Instant expectedCompletionDate) {
        this.setExpectedCompletionDate(expectedCompletionDate);
        return this;
    }

    public void setExpectedCompletionDate(Instant expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }

    public Instant getActualCompletionDate() {
        return this.actualCompletionDate;
    }

    public WorkPlan actualCompletionDate(Instant actualCompletionDate) {
        this.setActualCompletionDate(actualCompletionDate);
        return this;
    }

    public void setActualCompletionDate(Instant actualCompletionDate) {
        this.actualCompletionDate = actualCompletionDate;
    }

    public Integer getCompletionPercentage() {
        return this.completionPercentage;
    }

    public WorkPlan completionPercentage(Integer completionPercentage) {
        this.setCompletionPercentage(completionPercentage);
        return this;
    }

    public void setCompletionPercentage(Integer completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public WorkStatus getStatus() {
        return this.status;
    }

    public WorkPlan status(WorkStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(WorkStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public WorkPlan remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public WorkPlan deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getDeletedDate() {
        return this.deletedDate;
    }

    public WorkPlan deletedDate(Instant deletedDate) {
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
    }

    public WorkPlan ticket(Ticket ticket) {
        this.setTicket(ticket);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public WorkPlan department(Department department) {
        this.setDepartment(department);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkPlan)) {
            return false;
        }
        return getId() != null && getId().equals(((WorkPlan) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkPlan{" +
            "id=" + getId() +
            ", estimatedCost=" + getEstimatedCost() +
            ", startedDate='" + getStartedDate() + "'" +
            ", expectedCompletionDate='" + getExpectedCompletionDate() + "'" +
            ", actualCompletionDate='" + getActualCompletionDate() + "'" +
            ", completionPercentage=" + getCompletionPercentage() +
            ", status='" + getStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", deletedDate='" + getDeletedDate() + "'" +
            "}";
    }
}
