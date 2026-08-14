package com.divisosofttech.spot_fix.repository.rowmapper;

import com.divisosofttech.spot_fix.domain.WorkPlan;
import com.divisosofttech.spot_fix.domain.enumeration.WorkStatus;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link WorkPlan}, with proper type conversions.
 */
@Service
public class WorkPlanRowMapper implements BiFunction<Row, String, WorkPlan> {

    private final ColumnConverter converter;

    public WorkPlanRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link WorkPlan} stored in the database.
     */
    @Override
    public WorkPlan apply(Row row, String prefix) {
        WorkPlan entity = new WorkPlan();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setEstimatedCost(converter.fromRow(row, prefix + "_estimated_cost", BigDecimal.class));
        entity.setStartedDate(converter.fromRow(row, prefix + "_started_date", Instant.class));
        entity.setExpectedCompletionDate(converter.fromRow(row, prefix + "_expected_completion_date", Instant.class));
        entity.setActualCompletionDate(converter.fromRow(row, prefix + "_actual_completion_date", Instant.class));
        entity.setCompletionPercentage(converter.fromRow(row, prefix + "_completion_percentage", Integer.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", WorkStatus.class));
        entity.setRemarks(converter.fromRow(row, prefix + "_remarks", String.class));
        entity.setDeleted(converter.fromRow(row, prefix + "_deleted", Boolean.class));
        entity.setDeletedDate(converter.fromRow(row, prefix + "_deleted_date", Instant.class));
        entity.setTicketId(converter.fromRow(row, prefix + "_ticket_id", Long.class));
        entity.setDepartmentId(converter.fromRow(row, prefix + "_department_id", Long.class));
        return entity;
    }
}
