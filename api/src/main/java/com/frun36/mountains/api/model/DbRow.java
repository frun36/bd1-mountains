package com.frun36.mountains.api.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface DbRow {
        Map<String, Object> asMap();

        public static DbRow parseResultSet(ResultSet rs, String table) throws SQLException {
                return switch (table) {
                        case "point" -> new Point(
                                        rs.getObject("id", Integer.class),
                                        rs.getString("name"),
                                        rs.getObject("altitude", Integer.class),
                                        rs.getString("type"));

                        case "app_user" -> new AppUser(
                                        rs.getObject("id", Integer.class),
                                        rs.getString("username"),
                                        rs.getString("password"),
                                        rs.getObject("total_got_points", Integer.class));

                        case "route" -> new Route(
                                        rs.getObject("id", Integer.class),
                                        rs.getString("name"),
                                        rs.getObject("user_id", Integer.class),
                                        rs.getTimestamp("time_modified"));

                        case "route_trail" -> new RouteTrail(
                                        rs.getObject("id", Integer.class),
                                        rs.getObject("route_id", Integer.class),
                                        rs.getObject("trail_id", Integer.class),
                                        rs.getObject("prev_id", Integer.class),
                                        rs.getObject("next_id", Integer.class));

                        case "trail" -> new Trail(
                                        rs.getObject("id", Integer.class),
                                        rs.getObject("start_point_id", Integer.class),
                                        rs.getObject("end_point_id", Integer.class),
                                        rs.getObject("got_points", Integer.class),
                                        rs.getString("color"));

                        default -> null;
                };
        }
}
