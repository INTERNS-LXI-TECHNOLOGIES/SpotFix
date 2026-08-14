package com.divisosofttech.spot_fix.repository.rowmapper;

import com.divisosofttech.spot_fix.domain.Attachment;
import com.divisosofttech.spot_fix.domain.enumeration.AttachmentType;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Attachment}, with proper type conversions.
 */
@Service
public class AttachmentRowMapper implements BiFunction<Row, String, Attachment> {

    private final ColumnConverter converter;

    public AttachmentRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Attachment} stored in the database.
     */
    @Override
    public Attachment apply(Row row, String prefix) {
        Attachment entity = new Attachment();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setAttachmentType(converter.fromRow(row, prefix + "_attachment_type", AttachmentType.class));
        entity.setFileName(converter.fromRow(row, prefix + "_file_name", String.class));
        entity.setFilePath(converter.fromRow(row, prefix + "_file_path", String.class));
        entity.setFileType(converter.fromRow(row, prefix + "_file_type", String.class));
        entity.setFileSize(converter.fromRow(row, prefix + "_file_size", Long.class));
        entity.setChecksum(converter.fromRow(row, prefix + "_checksum", String.class));
        entity.setUploadedDate(converter.fromRow(row, prefix + "_uploaded_date", Instant.class));
        entity.setTranscript(converter.fromRow(row, prefix + "_transcript", String.class));
        entity.setDurationSeconds(converter.fromRow(row, prefix + "_duration_seconds", Integer.class));
        entity.setLanguage(converter.fromRow(row, prefix + "_language", String.class));
        entity.setDeleted(converter.fromRow(row, prefix + "_deleted", Boolean.class));
        entity.setUpdatedDate(converter.fromRow(row, prefix + "_updated_date", Instant.class));
        entity.setDeletedDate(converter.fromRow(row, prefix + "_deleted_date", Instant.class));
        entity.setTicketId(converter.fromRow(row, prefix + "_ticket_id", Long.class));
        entity.setUploadedById(converter.fromRow(row, prefix + "_uploaded_by_id", Long.class));
        return entity;
    }
}
