package com.divisosofttech.spot_fix.repository.rowmapper;

import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.enumeration.Priority;
import com.divisosofttech.spot_fix.domain.enumeration.TicketCategory;
import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import com.divisosofttech.spot_fix.domain.enumeration.Visibility;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Ticket}, with proper type conversions.
 */
@Service
public class TicketRowMapper implements BiFunction<Row, String, Ticket> {

    private final ColumnConverter converter;

    public TicketRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Ticket} stored in the database.
     */
    @Override
    public Ticket apply(Row row, String prefix) {
        Ticket entity = new Ticket();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setTitle(converter.fromRow(row, prefix + "_title", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", TicketStatus.class));
        entity.setPriority(converter.fromRow(row, prefix + "_priority", Priority.class));
        entity.setVisibility(converter.fromRow(row, prefix + "_visibility", Visibility.class));
        entity.setCategory(converter.fromRow(row, prefix + "_category", TicketCategory.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setUpdatedDate(converter.fromRow(row, prefix + "_updated_date", Instant.class));
        entity.setExpectedResolutionDate(converter.fromRow(row, prefix + "_expected_resolution_date", Instant.class));
        entity.setResolvedDate(converter.fromRow(row, prefix + "_resolved_date", Instant.class));
        entity.setAiSummary(converter.fromRow(row, prefix + "_ai_summary", String.class));
        entity.setAiDuplicate(converter.fromRow(row, prefix + "_ai_duplicate", Boolean.class));
        entity.setDuplicateScore(converter.fromRow(row, prefix + "_duplicate_score", Double.class));
        entity.setAiConfidence(converter.fromRow(row, prefix + "_ai_confidence", Double.class));
        entity.setDuplicateTicketId(converter.fromRow(row, prefix + "_duplicate_ticket_id", Long.class));
        entity.setDeleted(converter.fromRow(row, prefix + "_deleted", Boolean.class));
        entity.setDeletedDate(converter.fromRow(row, prefix + "_deleted_date", Instant.class));
        entity.setReportedById(converter.fromRow(row, prefix + "_reported_by_id", Long.class));
        entity.setLocationId(converter.fromRow(row, prefix + "_location_id", Long.class));
        entity.setWardId(converter.fromRow(row, prefix + "_ward_id", Long.class));
        entity.setAssignedDepartmentId(converter.fromRow(row, prefix + "_assigned_department_id", Long.class));
        return entity;
    }
}
