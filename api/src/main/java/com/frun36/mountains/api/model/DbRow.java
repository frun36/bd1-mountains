package com.frun36.mountains.api.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface DbRow {
    Map<String, Object> asMap();

    public static DbRow parseResultSet(ResultSet rs, String table) throws SQLException {
        return switch (table) {
            case "point" -> new Point(rs);
            case "app_user" -> new AppUser(rs);
            case "route" -> new Route(rs);
            case "route_trail" -> new RouteTrail(rs);
            case "trail" -> new Trail(rs);
            default -> throw new IllegalArgumentException("Unknown table: " + table);
        };
    }
}
