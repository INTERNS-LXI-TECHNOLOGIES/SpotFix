package com.divisosofttech.spot_fix.repository.rowmapper;

import com.divisosofttech.spot_fix.domain.Department;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Department}, with proper type conversions.
 */
@Service
public class DepartmentRowMapper implements BiFunction<Row, String, Department> {

    private final ColumnConverter converter;

    public DepartmentRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Department} stored in the database.
     */
    @Override
    public Department apply(Row row, String prefix) {
        Department entity = new Department();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setContactEmail(converter.fromRow(row, prefix + "_contact_email", String.class));
        entity.setContactPhone(converter.fromRow(row, prefix + "_contact_phone", String.class));
        entity.setActive(converter.fromRow(row, prefix + "_active", Boolean.class));
        return entity;
    }
}
