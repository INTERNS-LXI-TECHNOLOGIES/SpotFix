package com.diviso.spot_fix.service.dto;

import com.diviso.spot_fix.domain.enumeration.WorkStatus;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.diviso.spot_fix.domain.WorkPlan} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkPlanDTO implements Serializable {

    private Long id;

    @DecimalMin(value = "0")
    private BigDecimal estimatedCost;

    private Instant startedDate;

    private Instant expectedCompletionDate;

    private Instant actualCompletionDate;

    @Min(value = 0)
    @Max(value = 100)
    private Integer completionPercentage;

    @NotNull
    private WorkStatus status;

    @Lob
    private String remarks;

    @NotNull
    private Boolean deleted;

    private Instant deletedDate;

    @NotNull
    private TicketDTO ticket;

    @NotNull
    private DepartmentDTO department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Instant getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Instant startedDate) {
        this.startedDate = startedDate;
    }

    public Instant getExpectedCompletionDate() {
        return expectedCompletionDate;
    }

    public void setExpectedCompletionDate(Instant expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }

    public Instant getActualCompletionDate() {
        return actualCompletionDate;
    }

    public void setActualCompletionDate(Instant actualCompletionDate) {
        this.actualCompletionDate = actualCompletionDate;
    }

    public Integer getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Integer completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public WorkStatus getStatus() {
        return status;
    }

    public void setStatus(WorkStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkPlanDTO)) {
            return false;
        }

        WorkPlanDTO workPlanDTO = (WorkPlanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workPlanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkPlanDTO{" +
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
            ", ticket=" + getTicket() +
            ", department=" + getDepartment() +
            "}";
    }
}
