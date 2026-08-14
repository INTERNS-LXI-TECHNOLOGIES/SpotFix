package com.divisosofttech.spot_fix.repository.rowmapper;

import com.divisosofttech.spot_fix.domain.Ward;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Ward}, with proper type conversions.
 */
@Service
public class WardRowMapper implements BiFunction<Row, String, Ward> {

    private final ColumnConverter converter;

    public WardRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Ward} stored in the database.
     */
    @Override
    public Ward apply(Row row, String prefix) {
        Ward entity = new Ward();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setMunicipality(converter.fromRow(row, prefix + "_municipality", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        return entity;
    }
}
