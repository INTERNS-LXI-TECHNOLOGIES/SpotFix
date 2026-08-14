package com.divisosofttech.spot_fix.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class TicketStatusHistorySqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("old_status", table, columnPrefix + "_old_status"));
        columns.add(Column.aliased("new_status", table, columnPrefix + "_new_status"));
        columns.add(Column.aliased("changed_date", table, columnPrefix + "_changed_date"));

        columns.add(Column.aliased("ticket_id", table, columnPrefix + "_ticket_id"));
        columns.add(Column.aliased("changed_by_id", table, columnPrefix + "_changed_by_id"));
        return columns;
    }
}
