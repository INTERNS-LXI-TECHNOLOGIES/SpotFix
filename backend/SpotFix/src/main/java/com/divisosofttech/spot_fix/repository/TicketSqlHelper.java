package com.divisosofttech.spot_fix.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class TicketSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("title", table, columnPrefix + "_title"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("status", table, columnPrefix + "_status"));
        columns.add(Column.aliased("priority", table, columnPrefix + "_priority"));
        columns.add(Column.aliased("visibility", table, columnPrefix + "_visibility"));
        columns.add(Column.aliased("category", table, columnPrefix + "_category"));
        columns.add(Column.aliased("created_date", table, columnPrefix + "_created_date"));
        columns.add(Column.aliased("updated_date", table, columnPrefix + "_updated_date"));
        columns.add(Column.aliased("expected_resolution_date", table, columnPrefix + "_expected_resolution_date"));
        columns.add(Column.aliased("resolved_date", table, columnPrefix + "_resolved_date"));
        columns.add(Column.aliased("ai_summary", table, columnPrefix + "_ai_summary"));
        columns.add(Column.aliased("ai_duplicate", table, columnPrefix + "_ai_duplicate"));
        columns.add(Column.aliased("duplicate_score", table, columnPrefix + "_duplicate_score"));
        columns.add(Column.aliased("ai_confidence", table, columnPrefix + "_ai_confidence"));
        columns.add(Column.aliased("duplicate_ticket_id", table, columnPrefix + "_duplicate_ticket_id"));
        columns.add(Column.aliased("deleted", table, columnPrefix + "_deleted"));
        columns.add(Column.aliased("deleted_date", table, columnPrefix + "_deleted_date"));

        columns.add(Column.aliased("reported_by_id", table, columnPrefix + "_reported_by_id"));
        columns.add(Column.aliased("location_id", table, columnPrefix + "_location_id"));
        columns.add(Column.aliased("ward_id", table, columnPrefix + "_ward_id"));
        columns.add(Column.aliased("assigned_department_id", table, columnPrefix + "_assigned_department_id"));
        return columns;
    }
}
