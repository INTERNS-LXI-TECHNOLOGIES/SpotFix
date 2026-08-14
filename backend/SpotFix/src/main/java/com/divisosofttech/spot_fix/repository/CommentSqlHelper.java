package com.divisosofttech.spot_fix.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class CommentSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("title", table, columnPrefix + "_title"));
        columns.add(Column.aliased("content", table, columnPrefix + "_content"));
        columns.add(Column.aliased("created_date", table, columnPrefix + "_created_date"));
        columns.add(Column.aliased("updated_date", table, columnPrefix + "_updated_date"));
        columns.add(Column.aliased("deleted", table, columnPrefix + "_deleted"));
        columns.add(Column.aliased("deleted_date", table, columnPrefix + "_deleted_date"));

        columns.add(Column.aliased("ticket_id", table, columnPrefix + "_ticket_id"));
        columns.add(Column.aliased("user_id", table, columnPrefix + "_user_id"));
        return columns;
    }
}
