package com.divisosofttech.spot_fix.repository.rowmapper;

import com.divisosofttech.spot_fix.domain.Comment;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Comment}, with proper type conversions.
 */
@Service
public class CommentRowMapper implements BiFunction<Row, String, Comment> {

    private final ColumnConverter converter;

    public CommentRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Comment} stored in the database.
     */
    @Override
    public Comment apply(Row row, String prefix) {
        Comment entity = new Comment();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setTitle(converter.fromRow(row, prefix + "_title", String.class));
        entity.setContent(converter.fromRow(row, prefix + "_content", String.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setUpdatedDate(converter.fromRow(row, prefix + "_updated_date", Instant.class));
        entity.setDeleted(converter.fromRow(row, prefix + "_deleted", Boolean.class));
        entity.setDeletedDate(converter.fromRow(row, prefix + "_deleted_date", Instant.class));
        entity.setTicketId(converter.fromRow(row, prefix + "_ticket_id", Long.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", Long.class));
        return entity;
    }
}
