package com.divisosofttech.spot_fix.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class UserProfileSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("phone", table, columnPrefix + "_phone"));
        columns.add(Column.aliased("address", table, columnPrefix + "_address"));
        columns.add(Column.aliased("avatar_url", table, columnPrefix + "_avatar_url"));
        columns.add(Column.aliased("bio", table, columnPrefix + "_bio"));

        columns.add(Column.aliased("user_id", table, columnPrefix + "_user_id"));
        return columns;
    }
}
