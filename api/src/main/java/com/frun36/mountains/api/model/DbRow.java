package com.frun36.mountains.api.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface DbRow {
    Map<String, Object> asMap();

    public static DbRow parseResultSet(ResultSet rs, String table) throws SQLException {
        return switch (table) {
            case "point" -> new Point(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("altitude"),
                    rs.getString("type"));

            case "app_user" -> new AppUser(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("total_got_points"));

            case "route" -> new Route(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("time_modified"));

            case "route_trail" -> new RouteTrail(
                    rs.getInt("id"),
                    rs.getInt("route_id"),
                    rs.getInt("trail_id"),
                    rs.getInt("prev_id"),
                    rs.getInt("next_id"));

            case "trail" -> new Trail(
                    rs.getInt("id"),
                    rs.getInt("start_point_id"),
                    rs.getInt("end_point_id"),
                    rs.getInt("got_points"),
                    rs.getString("color"));
            
            default -> null;
        };
    }
}
