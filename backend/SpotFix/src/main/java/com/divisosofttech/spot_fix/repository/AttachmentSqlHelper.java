package com.divisosofttech.spot_fix.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class AttachmentSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("attachment_type", table, columnPrefix + "_attachment_type"));
        columns.add(Column.aliased("file_name", table, columnPrefix + "_file_name"));
        columns.add(Column.aliased("file_path", table, columnPrefix + "_file_path"));
        columns.add(Column.aliased("file_type", table, columnPrefix + "_file_type"));
        columns.add(Column.aliased("file_size", table, columnPrefix + "_file_size"));
        columns.add(Column.aliased("checksum", table, columnPrefix + "_checksum"));
        columns.add(Column.aliased("uploaded_date", table, columnPrefix + "_uploaded_date"));
        columns.add(Column.aliased("transcript", table, columnPrefix + "_transcript"));
        columns.add(Column.aliased("duration_seconds", table, columnPrefix + "_duration_seconds"));
        columns.add(Column.aliased("language", table, columnPrefix + "_language"));
        columns.add(Column.aliased("deleted", table, columnPrefix + "_deleted"));
        columns.add(Column.aliased("updated_date", table, columnPrefix + "_updated_date"));
        columns.add(Column.aliased("deleted_date", table, columnPrefix + "_deleted_date"));

        columns.add(Column.aliased("ticket_id", table, columnPrefix + "_ticket_id"));
        columns.add(Column.aliased("uploaded_by_id", table, columnPrefix + "_uploaded_by_id"));
        return columns;
    }
}
