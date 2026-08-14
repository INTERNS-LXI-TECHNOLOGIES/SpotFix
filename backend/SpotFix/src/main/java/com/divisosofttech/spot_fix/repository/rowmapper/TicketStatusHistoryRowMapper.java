package com.divisosofttech.spot_fix.repository.rowmapper;

import com.divisosofttech.spot_fix.domain.TicketStatusHistory;
import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import com.divisosofttech.spot_fix.domain.enumeration.TicketStatus;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link TicketStatusHistory}, with proper type conversions.
 */
@Service
public class TicketStatusHistoryRowMapper implements BiFunction<Row, String, TicketStatusHistory> {

    private final ColumnConverter converter;

    public TicketStatusHistoryRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link TicketStatusHistory} stored in the database.
     */
    @Override
    public TicketStatusHistory apply(Row row, String prefix) {
        TicketStatusHistory entity = new TicketStatusHistory();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setOldStatus(converter.fromRow(row, prefix + "_old_status", TicketStatus.class));
        entity.setNewStatus(converter.fromRow(row, prefix + "_new_status", TicketStatus.class));
        entity.setChangedDate(converter.fromRow(row, prefix + "_changed_date", Instant.class));
        entity.setTicketId(converter.fromRow(row, prefix + "_ticket_id", Long.class));
        entity.setChangedById(converter.fromRow(row, prefix + "_changed_by_id", Long.class));
        return entity;
    }
}
