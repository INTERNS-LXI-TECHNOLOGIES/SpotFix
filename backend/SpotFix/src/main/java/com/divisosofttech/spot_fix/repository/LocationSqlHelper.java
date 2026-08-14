package com.divisosofttech.spot_fix.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class LocationSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("address_text", table, columnPrefix + "_address_text"));
        columns.add(Column.aliased("landmark", table, columnPrefix + "_landmark"));
        columns.add(Column.aliased("latitude", table, columnPrefix + "_latitude"));
        columns.add(Column.aliased("longitude", table, columnPrefix + "_longitude"));

        columns.add(Column.aliased("ward_id", table, columnPrefix + "_ward_id"));
        return columns;
    }
}
