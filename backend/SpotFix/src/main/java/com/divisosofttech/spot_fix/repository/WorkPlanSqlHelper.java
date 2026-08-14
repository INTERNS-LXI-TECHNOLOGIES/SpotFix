package com.divisosofttech.spot_fix.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class WorkPlanSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("estimated_cost", table, columnPrefix + "_estimated_cost"));
        columns.add(Column.aliased("started_date", table, columnPrefix + "_started_date"));
        columns.add(Column.aliased("expected_completion_date", table, columnPrefix + "_expected_completion_date"));
        columns.add(Column.aliased("actual_completion_date", table, columnPrefix + "_actual_completion_date"));
        columns.add(Column.aliased("completion_percentage", table, columnPrefix + "_completion_percentage"));
        columns.add(Column.aliased("status", table, columnPrefix + "_status"));
        columns.add(Column.aliased("remarks", table, columnPrefix + "_remarks"));
        columns.add(Column.aliased("deleted", table, columnPrefix + "_deleted"));
        columns.add(Column.aliased("deleted_date", table, columnPrefix + "_deleted_date"));

        columns.add(Column.aliased("ticket_id", table, columnPrefix + "_ticket_id"));
        columns.add(Column.aliased("department_id", table, columnPrefix + "_department_id"));
        return columns;
    }
}
